package gwtquery.plugins.enhance.client;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.enhance.client.Enhance.Enhance;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.Element;

/**
 * Example code for the GwtQuery Enhance plugin.
 */
public class EnhanceSample implements EntryPoint {

  public void onModuleLoad() {
    
    $("button").click(new Function(){
      public void f(Element e) {
        $(".editable").as(Enhance).richText();
        $(".color").as(Enhance).colorBox();
        $(".colorfull").as(Enhance).colorBox(true);
        $(".ffamily").as(Enhance).fontFamilyBox();
        $(".fsize").as(Enhance).fontSizeBox();
        $(e).hide();
      }
    });
    
  }
}
