package gwtquery.plugins.draggable.client.plugins;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQUtils;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.Draggable.DragOperationInfo;

/**
 * This add-on handle opacity of the helper
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 *
 */
public class OpacityPlugin implements DraggablePlugin {
  
  private static String OLD_OPACITY_KEY="oldOpacity";
  private static String OPACITY_CSS_KEY="opacity";

  public String getName() {
    return "opacity";
  }

  public void onDrag(DragOperationInfo info, Element draggableElement, Event e) {
    //do nothing
  }

  public void onStart(DragOperationInfo info, Element draggableElement, Event e) {
   float opacity = info.getOptions().getOpacity();
    
    GQuery $helper = info.getHelper();
  
    double oldOpacity = GQUtils.cur($helper.get(0), OPACITY_CSS_KEY, true);
    $helper.data(OLD_OPACITY_KEY, oldOpacity);
    
    $helper.css(OPACITY_CSS_KEY, ""+opacity);
    
  }

  public void onStop(DragOperationInfo info, Element draggableElement, Event e) {
    GQuery $helper = info.getHelper();
    
    if ($helper.data(OLD_OPACITY_KEY) == null){
      return;
    }
    double oldOpacity = $helper.data(OLD_OPACITY_KEY,Double.class);
    $helper.css(OPACITY_CSS_KEY, ""+oldOpacity);
    $helper.removeData(OLD_OPACITY_KEY);

  }

  public boolean hasToBeExecuted(DraggableOptions options) {
    float opacity = options.getOpacity();
    return opacity >= 0;
  }

}
