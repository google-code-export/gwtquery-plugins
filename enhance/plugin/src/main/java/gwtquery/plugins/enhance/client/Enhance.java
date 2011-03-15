package gwtquery.plugins.enhance.client;

import gwtquery.plugins.enhance.client.colorpicker.ColorPickerFactory;
import gwtquery.plugins.enhance.client.colorpicker.ColorPickerFactory.ColorPickerType;
import gwtquery.plugins.enhance.client.fontpicker.FontPicker.FontPickerType;
import gwtquery.plugins.enhance.client.fontpicker.FontPickerFactory;
import gwtquery.plugins.enhance.client.richtext.RichTextAreaFactory;
import gwtquery.plugins.enhance.client.slider.SliderBar;
import gwtquery.plugins.enhance.client.slider.SliderFactory;

import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.Plugin;
import com.google.gwt.query.client.plugins.Widgets;
import com.google.gwt.query.client.plugins.widgets.WidgetInitializer;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Enhance plugin for GwtQuery
 */
public class Enhance extends Widgets {


  // A shortcut to the class 
  public static final Class<Enhance> Enhance = Enhance.class;

  // Register the plugin in GQuery
  static {
    GQuery.registerPlugin(Enhance.class, new Plugin<Enhance>() {
      public Enhance init(GQuery gq) {
        return new Enhance(gq);
      }
    });
  }

  // Initialization
  public Enhance(GQuery gq) {
    super(gq);
  }

  public Widgets richText() {
    return richText(null);
  }
  
  public Widgets richText(WidgetInitializer<RichTextArea> initializer) {
    return widgets(new RichTextAreaFactory(), initializer);
  }

  public Widgets colorBox() {
    return colorBox(ColorPickerType.SIMPLE, null);
  }
  
  public Widgets colorBox(ColorPickerType type) {
    return colorBox(type, null);
  }
  
  public Widgets colorBox(ColorPickerType type, WidgetInitializer<TextBox> initializer) {
    return widgets(new ColorPickerFactory(type), initializer);
  }

  public Widgets fontFamilyBox() {
    return fontFamilyBox(null);
  }
  
  public Widgets fontFamilyBox(WidgetInitializer<TextBox> initializer) {
    return widgets(new FontPickerFactory(FontPickerType.FONT_FAMILY), initializer);
  }

  public Widgets fontSizeBox() {
    return fontSizeBox(null);
  }
  
  public Widgets fontSizeBox(WidgetInitializer<TextBox> initializer) {
    return widgets(new FontPickerFactory(FontPickerType.FONT_SIZE), initializer);
  }
  
  public Widgets slider(double min, double max) {
    return slider(min, max, 10, null);
  }
  
  public Widgets slider(double min, double max, int steps, WidgetInitializer<SliderBar> initializer) {
    return widgets(new SliderFactory(min, max, steps), initializer);
  }
}
