package gwtquery.plugins.colorpicker.client;

import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.Plugin;
import com.google.gwt.query.client.plugins.Widgets;
import com.google.gwt.query.client.plugins.widgets.WidgetInitializer;

/**
 * ColorPicker plugin for GwtQuery
 */
public class ColorPicker extends Widgets {


  // A shortcut to the class 
  public static final Class<ColorPicker> ColorPicker = ColorPicker.class;

  // Register the plugin in GQuery
  static {
    GQuery.registerPlugin(ColorPicker.class, new Plugin<ColorPicker>() {
      public ColorPicker init(GQuery gq) {
        return new ColorPicker(gq);
      }
    });
  }

  // Initialization
  public ColorPicker(GQuery gq) {
    super(gq);
  }

  
  public Widgets enhance(WidgetInitializer... initializers) {
    return widgets(new ColorPickerFactory(), initializers);
  }

}
