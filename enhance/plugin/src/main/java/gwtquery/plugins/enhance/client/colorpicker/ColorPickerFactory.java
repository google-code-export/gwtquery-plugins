package gwtquery.plugins.enhance.client.colorpicker;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.widgets.WidgetFactory;
import com.google.gwt.query.client.plugins.widgets.WidgetsUtils;
import com.google.gwt.user.client.Event;
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
      val = $(e).attr("value");
      ret = TextBox.wrap(e);
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
      ret.setValue("#" + value);
      ((HasValue<String>)p).setValue(value);
      $(ret).css("backgroundColor", ret.getValue());
      ((HasValue<String>)p).addValueChangeHandler(new ValueChangeHandler<String>() {
        public void onValueChange(ValueChangeEvent<String> value) {
          ret.setValue(value.getValue());
          long bg = Integer.valueOf(value.getValue().replaceAll("[^\\d]+", ""));
          String fg = bg > 888888 ? "#000000" : "#ffffff";
          System.out.println(value.getValue() + " " + bg + " " + fg);
          $(ret).css("backgroundColor", value.getValue()).css("color", fg);
          System.out.println($(ret));
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