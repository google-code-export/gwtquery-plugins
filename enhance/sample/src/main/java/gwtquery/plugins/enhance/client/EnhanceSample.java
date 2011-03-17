package gwtquery.plugins.enhance.client;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.enhance.client.Enhance.Enhance;
import gwtquery.plugins.enhance.client.colorpicker.ColorPickerFactory.ColorPickerType;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
/**
 * Example code for the GwtQuery Enhance plugin.
 */
public class EnhanceSample implements EntryPoint {
  
  public void onModuleLoad() {
    
    $(".multiselect").as(Enhance).multiselect();
    
    $("button").click(new Function(){
      public void f(Element e) {
        $(".editable").as(Enhance).richText();
        $(".color").as(Enhance).colorBox();
        $(".colorfull").as(Enhance).colorBox(ColorPickerType.ADVANCED);
        $(".ffamily").as(Enhance).fontFamilyBox();
        $(".fsize").as(Enhance).fontSizeBox();
        $(".slider").as(Enhance).slider(0, 2);
        
        
        $(e).hide();
      }
    });
    
  }
  

}
