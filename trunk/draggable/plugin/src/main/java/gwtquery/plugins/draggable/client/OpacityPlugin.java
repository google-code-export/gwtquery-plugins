package gwtquery.plugins.draggable.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQUtils;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;

public class OpacityPlugin implements DraggablePlugin {
  
  private static String OLD_OPACITY_KEY="oldOpacity";
  private static String OPACITY_CSS_KEY="opacity";

  public String getName() {
    return "opacity";
  }

  public void onDrag(Draggable draggable, Element draggableElement, Event e) {
    //do nothing
  }

  public void onStart(Draggable draggable, Element draggableElement, Event e) {
   float opacity = draggable.options.getOpacity();
    if ( opacity < 0){
      return;
    }
    
    GQuery $helper = draggable.helper;
  
    double oldOpacity = GQUtils.cur($helper.get(0), OPACITY_CSS_KEY, true);
    $helper.data(OLD_OPACITY_KEY, oldOpacity);
    
    $helper.css(OPACITY_CSS_KEY, ""+opacity);
    
  }

  public void onStop(Draggable draggable, Element draggableElement, Event e) {
    GQuery $helper = draggable.helper;
    
    if ($helper.data(OLD_OPACITY_KEY) == null){
      return;
    }
    double oldOpacity = $helper.data(OLD_OPACITY_KEY,Double.class);
    $helper.css(OPACITY_CSS_KEY, ""+oldOpacity);

  }

}
