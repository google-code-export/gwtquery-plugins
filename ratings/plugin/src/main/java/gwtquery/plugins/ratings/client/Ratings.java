package gwtquery.plugins.ratings.client;


import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQUtils;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.query.client.Plugin;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.Regexp;

/**
 * Ratings plugin for GwtQuery.
 * It is based on the Star Rating Plugin
 */
public class Ratings extends GQuery {

  public static class Control {

    public GQuery cancelButton;

    private String cancel = "Cancel Rating";

    private String cancelValue = "";

    private int count;

    private GQuery current;

    private boolean half;

    private JsArray<Element> inputs = JsArray.createArray().cast();

    private GQuery rater;

    private boolean readOnly;

    private int serial;

    private int split = 0;

    private JsArray<Element> stars = JsArray.createArray().cast();

    private int starWidth = 16;
    
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void addInput(Element element) {
      inputs.set(inputs.length(), element);
    }

    public void addStar(Element element) {
      stars.set(stars.length(), element);
    }

    public int bumpCount() {
      return count++;
    }

    public int getCount() {
      return count;
    }

    public Object getCurrent() {
      return current;
    }

    public NodeList<Element> getInputs() {
      return inputs.cast();
    }

    public int getSerial() {
      return serial;
    }

    public int getSplit() {
      return split;
    }

    public Element getStar(int value) {
      return stars.get(value);
    }

    public NodeList<Element> getStars() {
      return stars.cast();
    }

    public int getStarWidth() {
      return starWidth;
    }

    public boolean isHalf() {
      return half;
    }

    public boolean isReadOnly() {
      return readOnly;
    }

    public void setCurrent(GQuery current) {
      this.current = current;
    }

    public void setRater(GQuery rater) {
      this.rater = rater;
    }

    public void setReadOnly(boolean readOnly) {
      this.readOnly = readOnly;
    }

    public void setSerial(int serial) {
      this.serial = serial;
    }

    public void setSplit(int split) {
      this.split = split;
    }
  }

  public static class Raters {

    private GQuery.DataCache cache = GQuery.DataCache.createObject().cast();

    private int calls;

    private int count;

    public Raters(int count, int calls) {
      this.count = count;
      this.calls = calls;
    }

    public int bumpCount() {
      return count++;
    }

    public GQuery get(String eid) {
      return (GQuery) cache.getObject(eid);
    }

    public int getCalls() {
      return calls;
    }

    public void put(String eid, GQuery q) {
      cache.put(eid, q);
    }
  }

  // A shortcut to the class 
  public static final Class<Ratings> Ratings = Ratings.class;

  private static int calls;

  // Register the plugin in GQuery
  static {
    GQuery.registerPlugin(Ratings, new Plugin<Ratings>() {
      public Ratings init(GQuery gq) {
        return new Ratings(gq);
      }
    });
  }
  
  private static native Element getContext(Element e) /*-{
    return this.form || $doc.body;
  }-*/;
  
  // Initialization
  public Ratings(GQuery gq) {
    super(gq);
  }
  
  public Ratings blurStar() {
    return this;
  }

  public Ratings drain() {
    for (Element e : elements()) {
      GQuery self = $(e);
      Control control = (Control) self.data("rating");
      control.rater.children().filter(".rater-" + control.getSerial())
          .removeClass("star-rating-on").removeClass("star-rating-hover");
    }
    return this;
  }

  public Ratings draw() {
    for (Element e : elements()) {
      GQuery self = $(e);
      Control control = (Control) self.data("rating");
      if (control == null) {
        continue;
      }

      // Clear all stars
      self.as(Ratings).drain();
      
      // Set control value
      if (control.getCurrent() != null) {
        control.current.prevAll().andSelf().filter(".rater-" + control.serial).addClass("star-rating-on");
      }
      
      self.siblings().toggleClass("star-rating-readonly", control.isReadOnly());
    }

    return this;
  }

  public Ratings fill() {
    for (Element e : elements()) {
      GQuery self = $(e);
      Control control = (Control) self.data("rating");
      if (control == null) {
        continue;
      }

      // Reset all stars and highlight them up to this element
      self.as(Ratings).drain();
      self.prevAll().andSelf().filter(".rater-" + control.getSerial())
          .addClass("star-rating-hover");
    }

    return this;
  }

  public Ratings focusStar() {
    Control control = (Control) this.data("rating");
    if (control == null) {
      return this;
    }
    return this;
  }
  
  public Ratings unrate() {
    $("input.star").show();
    $("span.star-rating-control").remove();
    return this;
  }
  
  public Ratings rate() {
    if (size() == 0) {
      return this;
    }
    calls++;
    not(".star-rating-applied").addClass("star-rating-applied");
    Control control = null;
    for (Element e : elements()) {
      GQuery input = $(e);
      String eid = GQUtils.or(e.getPropertyString("name"), "unnamed-rating")
          .replaceAll("\\[|\\]", "_").replaceAll("^\\_+|\\_$", "");
      GQuery context = $(getContext(e));
      Raters raters = (Raters) context.data("rating");
      if (raters == null || raters.getCalls() != calls) {
        raters = new Raters(0, calls);
      }
      GQuery rater = raters.get(eid);
      if (rater != null) {
        control = (Control) rater.data("rating");
      }
      if (rater != null && control != null) {
        control.bumpCount();
      } else {
        control = new Control();
        control.setSerial(raters.bumpCount());
        Properties metadata = getMetaData(input.get(0).getClassName());
        if (metadata != null && metadata.defined("split")) {
          control.setSplit(metadata.getInt("split"));
        }
        // create rating element
        rater = $("<span class=\"star-rating-control\" />");
        input.before(rater);

        // Mark element for initialization (once all stars are ready)
        rater.addClass("rating-to-be-drawn");

        // Accept readOnly setting from 'disabled' property
        if (GQUtils.truth(input.attr("disabled"))) {
          control.setReadOnly(true);
        } else {
          // Create 'cancel' button
          control.cancelButton = $(
              "<div class=\"rating-cancel\"><a title=\"" + control.cancel
                  + "\">" + control.cancelValue + "</a></div>")
              .data("rating", control)                  
              .appendTo(rater)
              .hover(new Function() {
                public void f(Element e) {
                  $(e).as(Ratings).drain();
                  $(e).addClass("star-rating-hover");
                }
              }, new Function() {
                public void f(Element e) {
                  $(e).as(Ratings).draw();
                  $(e).removeClass("star-rating-hover");
              }})
              .click(new Function() {
                public void f(Element e) {
                  $(e).as(Ratings).selectStar();
                }
              });
        }
      }
      // insert rating star
      GQuery star = $("<div class=\"star-rating rater-" + control.getSerial()
          + "\"><a title=\""
          + (GQUtils.or(e.getTitle(), e.getPropertyString("value"))) + "\">"
          + e.getPropertyString("value") + "</a></div>")
          .appendTo(rater);

      // inherit attributes from input element
      if (e.getId() != null) {
        star.attr("id", e.getId());
      }

      if (e.getClassName() != null) {
        star.addClass(e.getClassName());
      }

      // Half-stars?
      if (control.isHalf()) {
        control.setSplit(2);
      }

      // Prepare division control
      if (control.getSplit() > 0) {
        int stw = star.width();
        if (stw == 0) {
          stw = control.getStarWidth();
        }

        int spi = (control.getCount() % control.getSplit());
        int spw = (int) Math.floor(stw / control.getSplit());

        star.width(spw).find("a").css("margin-left", "-" + (spi * spw) + "px");
      }

      // readOnly?
      if (control.isReadOnly()) {
        star.addClass("star-rating-readonly");
      } else {
        star.addClass("star-rating-live")
        // Attach mouse events
        .hover(new Function() {
          public void f(Element e) {
            $(e).as(Ratings).fill();
            $(e).as(Ratings).focusStar();
          }
        },new Function() {
          @Override
          public void f(Element e) {
            $(e).as(Ratings).draw();
            $(e).as(Ratings).blurStar();
          }
        }).click(new Function() {
          public void f(Element e) {
            $(e).as(Ratings).selectStar();
          }
        });
      }

      // set current selection
      if (e.getPropertyBoolean("checked")) {
        control.setCurrent(star);
      }

      // hide input element
      input.hide();

      if (control.getName() == null) {
        control.setName(input.attr("name"));
      }

      // backward compatibility, form element to plugin
      input.click(new Function() {
        public void f(Element e) {
          $(e).as(Ratings).selectStar();
        }
      });

      // attach reference to star to input element and vice-versa
      star.data("rating.input", input.data("rating.star", star));

      // store control information in form (or body when form not available)
      control.addStar(star.get(0));
      control.addInput(input.get(0));
      control.setRater(rater);
      raters.put(eid, rater);

      input.data("rating", control);
      rater.data("rating", control);
      star.data("rating", control);
      context.data("rating", raters);
    }

    // Append an end division
    $(".rating-to-be-drawn").append(
        "<div class='start-rating-end'>&nbsp;</div>");

    // Initialize ratings (first draw)
    $(".rating-to-be-drawn").as(Ratings).draw().removeClass(
        "rating-to-be-drawn");

    return this;
  }

  public Ratings selectStar() {
    for (Element e : elements()) {
      GQuery self = $(e);
      Control control = (Control) self.data("rating");
      if (control == null) {
        continue;
      }

      GQuery current = self;
      if (self.get(0).getTagName().equalsIgnoreCase("INPUT")) {
        current = (GQuery) self.data("rating.star");
      } else {
        GQuery input = self.data("rating.input", Ratings);
        $("input[name=" + control.getName() + "]")
          .val(input != null ? input.attr("value") : null);
      }

      control.current = current;
      self.as(Ratings).draw();
    }

    return this;
  }

  public Ratings selectStar(int value) {
    for (Element e : elements()) {
      GQuery self = $(e);
      Control control = (Control) self.data("rating");
      if (control == null) {
        continue;
      }

      control.setCurrent(null);
      $(control.getStar(value)).as(Ratings).selectStar();
    }
    return this;
  }

  public Ratings selectStar(String value) {
    for (Element e : elements()) {
      GQuery self = $(e);
      Control control = (Control) self.data("rating");
      if (control == null) {
        continue;
      }
      
      control.setCurrent(null);
      NodeList<Element> stars = control.getStars();
      for (int i = 0; i < stars.getLength(); i++) {
        String val = ((GQuery) $(stars.getItem(i)).data("rating.input")).val();
        if (val != null && val.equals(value)) {
          $(stars.getItem(i)).as(Ratings).selectStar();
        }
      }
    }
    return this;
  }

  private Properties getMetaData(String className) {
    if (className.indexOf("{") == -1) {
      return Properties.createObject().cast();
    }
    Regexp re = new Regexp("{(.*)}");
    JSArray match = re.exec(className);
    if (match != null && match.size() > 1) {
      return Properties.create(match.getStr(1));
    }
    return Properties.createObject().cast();
  }

}
