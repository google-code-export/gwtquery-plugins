package gwtquery.plugins.enhance.client.fontpicker;

import static com.google.gwt.query.client.GQuery.$;
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
      ret = TextBox.wrap(e);
    } else if (WidgetsUtils.matchesTags(e, "div", "span")) {
      ret = new TextBox();
      val = e.getInnerText();
      WidgetsUtils.replace(e, ret);
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
      $(ret).css("backgroundColor", ret.getValue());
      p.addValueChangeHandler(new ValueChangeHandler<FontPicker>() {
        public void onValueChange(ValueChangeEvent<FontPicker> value) {
          if (type == FontPickerType.FONT_FAMILY) {
            ret.setValue(value.getValue().getFontName());
            $(ret).css("font-family", value.getValue().getFontName());
          } else {
            ret.setValue(value.getValue().getFontSizeCssName());
            $(ret).css("font-size", value.getValue().getFontSizeCssName());
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