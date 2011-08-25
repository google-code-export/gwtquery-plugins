package gwtquery.plugins.enhance.client.colorpicker;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.widgets.WidgetFactory;
import com.google.gwt.query.client.plugins.widgets.WidgetsUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Factory used to create a {@link TextBox} widget with a picker to select a color
 */
public class ColorPickerFactory implements WidgetFactory<TextBox> {
  
  public enum ColorPickerType {
    SIMPLE, ADVANCED
  }
  
  ColorPickerType type;
  
  public ColorPickerFactory(ColorPickerType type) {
    this.type = type;
  }
  
  @SuppressWarnings("unchecked")
  public TextBox create(Element e) {
    final TextBox ret;
    
    String val = "";
    if ($(e).filter("input[type='text']").get(0) != null) {
      if ($(e).widget() != null) {
        ret = $(e).widget();
      } else {
        ret = TextBox.wrap(e);
      }
      val = ret.getValue();
    } else if (WidgetsUtils.matchesTags(e, "div", "span")) {
      ret = new TextBox();
      val = e.getInnerText();
      WidgetsUtils.replaceOrAppend(e, ret);
    } else {
      ret = null;
    }
    
    if (ret != null) {
      final PopupPanel p = type == ColorPickerType.ADVANCED ? new ColorPickerFull() : new ColorPicker();
      String value = val.replaceAll("[^\\da-fA-F]", "");
      if (!value.isEmpty()) {
        ret.setValue("#" + value);
      }
      ((HasValue<String>)p).setValue(value);
      setColor(ret.getValue(), ret.getElement());
      
      ((HasValue<String>)p).addValueChangeHandler(new ValueChangeHandler<String>() {
        public void onValueChange(ValueChangeEvent<String> value) {
          ret.setValue(value.getValue());
          setColor(value.getValue(), ret.getElement());
          p.hide();
        }
      });
      ret.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          Element e = ret.getElement();
          p.setPopupPosition(e.getAbsoluteLeft(), e.getAbsoluteTop() + e.getClientHeight());
          p.show();
          
          // Move the popup to avoid buttons being not visible.
          GQuery g = $(p);
          int t = g.get(0).getAbsoluteTop();
          int l = g.get(0).getAbsoluteLeft();
          int w = g.width();
          int h = g.height();
          int ww = Window.getClientWidth();
          int wh = Window.getClientHeight();
          int y = wh - (t+h);
          int x = ww - (t+w);
          y = y < 0 ? Math.max(0, y + t) : -1;
          x = x < 0 ? Math.max(0, x + l) : -1;
          if (y >= 0) {
            g.css("top", y + "px");
          }
          if (x >= 0) {
            g.css("left", x + "px");
          }
        }
      });
      ret.addChangeHandler(new ChangeHandler() {
        public void onChange(ChangeEvent event) {
          setColor(ret.getValue(), ret.getElement());
        }
      });
    }
    return ret;
  }
  
  private static void setColor(String color, Element e) {
    try {
      long bg = Long.valueOf(color.replace("#", ""), 16);
      String fg = bg > 0x800000 ? "#000000" : "#ffffff";
      $(e).css("backgroundColor", color).css("color", fg);
    } catch (Exception e2) {
      e2.printStackTrace();
    }
  }
  
}