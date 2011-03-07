package gwtquery.plugins.colorpicker.client;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.colorpicker.client.ColorPicker.ColorPicker;

import com.google.gwt.core.client.EntryPoint;

/**
 * Example code for the GwtQuery ColorPicker plugin.
 */
public class ColorPickerSample implements EntryPoint {

  public void onModuleLoad() {
    $(".color").as(ColorPicker).enhance();
  }
}
