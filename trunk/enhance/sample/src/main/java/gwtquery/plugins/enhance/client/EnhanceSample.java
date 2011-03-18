package gwtquery.plugins.enhance.client;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.enhance.client.Enhance.Enhance;

import java.util.Arrays;

import gwtquery.plugins.enhance.client.colorpicker.ColorPickerFactory.ColorPickerType;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.Window;
/**
 * Example code for the GwtQuery Enhance plugin.
 */
public class EnhanceSample implements EntryPoint {
  
  public void onModuleLoad() {
    
    $("#enhance").click(new Function(){
      public void f(Element e) {
        $(".editable").as(Enhance).richText();
        $(".color").as(Enhance).colorBox();
        $(".colorfull").as(Enhance).colorBox(ColorPickerType.ADVANCED);
        $(".ffamily").as(Enhance).fontFamilyBox();
        $(".fsize").as(Enhance).fontSizeBox();
        $(".slider").as(Enhance).slider(0, 2);
        $(".multiselect").as(Enhance).multiselect();
        $(e).hide();
      }
    });
    
    $("#values").click(new Function() {
      String txt = "";
      public void f() {
        txt = "";
        $("*[name]").each(new Function(){
          public void f(Element e) {
            String v[] = $(e).as(Enhance).vals();
            if (v.length > 0 && !v[0].isEmpty()) {
              txt += e.getTagName() + " name=" + $(e).attr("name") + " value=" + Arrays.asList(v) + "\n";
            }
          }
        });
        Window.alert(txt);
      }
    });
    
  }
  

}
