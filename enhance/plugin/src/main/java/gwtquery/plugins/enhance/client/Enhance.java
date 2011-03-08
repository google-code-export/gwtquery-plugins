package gwtquery.plugins.enhance.client;

import gwtquery.plugins.enhance.client.colorpicker.ColorPickerFactory;
import gwtquery.plugins.enhance.client.fontpicker.FontPicker.FontPickerType;
import gwtquery.plugins.enhance.client.fontpicker.FontPickerFactory;
import gwtquery.plugins.enhance.client.richtext.RichTextAreaFactory;

import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.Plugin;
import com.google.gwt.query.client.plugins.Widgets;
import com.google.gwt.query.client.plugins.widgets.WidgetInitializer;

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

  public Widgets richText(WidgetInitializer... initializers) {
    return widgets(new RichTextAreaFactory(), initializers);
  }

  public Widgets colorBox(WidgetInitializer... initializers) {
    return widgets(new ColorPickerFactory(), initializers);
  }
  
  public Widgets colorBox(boolean advanced, WidgetInitializer... initializers) {
    return widgets(new ColorPickerFactory(advanced), initializers);
  }

  public Widgets fontFamilyBox(WidgetInitializer... initializers) {
    return widgets(new FontPickerFactory(FontPickerType.FONT_FAMILY), initializers);
  }
  public Widgets fontSizeBox(WidgetInitializer... initializers) {
    return widgets(new FontPickerFactory(FontPickerType.FONT_SIZE), initializers);
  }
}
