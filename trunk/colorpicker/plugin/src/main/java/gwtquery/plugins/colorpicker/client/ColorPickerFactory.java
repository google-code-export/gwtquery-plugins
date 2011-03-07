package gwtquery.plugins.colorpicker.client;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.widgets.WidgetFactory;
import com.google.gwt.query.client.plugins.widgets.WidgetsUtils;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Factory used to create a {@link TextBox} widget with a picker to select a color
 */
public class ColorPickerFactory implements WidgetFactory<TextBox> {
  
  public TextBox create(Element e) {

    final TextBox ret;
    
    String val = "";
    if ($(e).filter("input[type='text']").get(0) != null) {
      val = $(e).attr("value");
      ret = TextBox.wrap(e);
    } else if (WidgetsUtils.matchesTags(e, "div", "span")) {
      ret = new TextBox();
      val = e.getInnerText();
      WidgetsUtils.replace(e, ret);
    } else {
      ret = null;
    }
    
    if (ret != null) {
      final BasicColorPicker p = new BasicColorPicker();
      ret.setValue("#" + val.replaceAll("[^\\da-fA-F]", ""));
      $(ret).css("backgroundColor", ret.getValue());
      p.addValueChangeHandler(new ValueChangeHandler<BasicColorPicker>() {
        public void onValueChange(ValueChangeEvent<BasicColorPicker> event) {
          String color = event.getValue().getColor();
          ret.setValue(color);
          $(ret).css("backgroundColor", color);
          p.hide();
        }
      });
      $(ret).unbind(Event.ONCLICK).click(new Function() {
        public void f(Element e) {
          p.setPopupPosition(e.getAbsoluteLeft(), e.getAbsoluteTop() + e.getClientHeight());
          p.show();
        }
      });
      $(ret).change(new Function(){
        public void f(Element e) {
          $(e).css("backgroundColor", $(e).val());
          p.hide();
        }
      });
    }
    return ret;
  }
}