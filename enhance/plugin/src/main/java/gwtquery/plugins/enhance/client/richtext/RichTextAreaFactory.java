package gwtquery.plugins.enhance.client.richtext;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.InitializeEvent;
import com.google.gwt.event.logical.shared.InitializeHandler;
import com.google.gwt.query.client.plugins.widgets.Attachable;
import com.google.gwt.query.client.plugins.widgets.WidgetFactory;
import com.google.gwt.query.client.plugins.widgets.WidgetsUtils;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Factory used to create a {@link RichTextArea} widget with a toolbar. 
 * A {@link Button} is created if the element is a <i>textarea</i>, <i>div></i> or <i>span</i>.
 * The content of the element will be copied to the rich text area.
 */
public class RichTextAreaFactory implements WidgetFactory<RichTextArea> {
  
  public static class RichTextWithToolbar extends VerticalPanel implements Attachable {

    RichTextArea area = new RichTextArea();

    public RichTextWithToolbar(RichTextArea a) {
      area = a != null ?  a : new RichTextArea();
      
      area.ensureDebugId("editorArea");
      area.setSize("100%", "100%");

      Toolbar toolbar = new Toolbar(area);
      toolbar.ensureDebugId("editorToolbar");

      setStyleName("gq-richtext");
      add(toolbar);
      add(area);
      setWidth("100%");
      
      // Inject styles from parent document
      area.addInitializeHandler(new InitializeHandler() {
        public void onInitialize(InitializeEvent event) {
          $(area).contents().find("head").append($("style, link[rel='stylesheet']").clone());
          $(area).contents().find("body").css("fontFamily", "arial").css("fontSize", "80%");
        }
      });
    }
    
    public void attach() {
      onAttach();
      RootPanel.detachOnWindowClose(this);
    }
  }

  public RichTextArea create(Element e) {
    String v = null;
    RichTextArea a = null;
    if ($(e).widget(0) != null && $(e).widget(0) instanceof RichTextArea) {
      a = $(e).widget(0);
      v = a.getHTML();
    } else if ("textarea".equalsIgnoreCase(e.getTagName())) {
      v = $(e).val();
    } else if (WidgetsUtils.matchesTags(e, "div", "span")) {
      v = $(e).html();
    }
    if (v != null) {
      RichTextWithToolbar b = new RichTextWithToolbar(a);
      a = b.area;
      a.setHTML(v);
      WidgetsUtils.replace(e, b);
      return a;
    }
    return null;
  }

}