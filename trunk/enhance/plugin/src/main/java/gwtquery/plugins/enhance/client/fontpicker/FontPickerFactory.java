package gwtquery.plugins.enhance.client.fontpicker;

import static com.google.gwt.query.client.GQuery.$;
import gwtquery.plugins.enhance.client.common.TextBoxWrapper;
import gwtquery.plugins.enhance.client.fontpicker.FontPicker.FontPickerType;

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
public class FontPickerFactory implements WidgetFactory<TextBox> {
  
  FontPickerType type = FontPickerType.FONT_FAMILY;
  
  public FontPickerFactory(FontPickerType type) {
    this.type = type;
  }
  
  public TextBox create(Element e) {
    final TextBox ret;
    
    String val = "";
    if ($(e).filter("input[type='text']").get(0) != null) {
      val = $(e).attr("value");
      ret = new TextBoxWrapper(e);
    } else if (WidgetsUtils.matchesTags(e, "div", "span")) {
      ret = new TextBox();
      val = e.getInnerText();
      WidgetsUtils.replaceOrAppend(e, ret);
    } else {
      ret = null;
    }
    
    if (ret != null) {
      final FontPicker p = new FontPicker(type);
      ret.setValue(val);
      if (type == FontPickerType.FONT_FAMILY) {
        $(ret).css("font-family", val);
      } else {
        $(ret).css("font-size", val);
      }
      $(ret).css("backgroundColor", "#D8ECFD");
      p.addValueChangeHandler(new ValueChangeHandler<String>() {
        public void onValueChange(ValueChangeEvent<String> value) {
          ret.setValue(value.getValue());
          if (type == FontPickerType.FONT_FAMILY) {
            $(ret).css("font-family", value.getValue());
          } else {
            ret.setValue(value.getValue());
            $(ret).css("font-size", value.getValue());
          }
          p.hide();
        }
      });
      $(ret).unbind(Event.ONCLICK).click(new Function() {
        public void f(Element e) {
          p.setPopupPosition(e.getAbsoluteLeft(), e.getAbsoluteTop() + e.getClientHeight());
          p.show();
        }
      });
    }
    return ret;
  }
}