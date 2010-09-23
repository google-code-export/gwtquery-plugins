package gwtquery.plugins.draggable.client.plugins;                                
                                                                                  
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.draggable.client.DraggableHandler;
import gwtquery.plugins.draggable.client.DraggableOptions;
                                                                                  
/**                                                                               
 * This add-on manage the z-index for the helper while being dragged.         
 *                                                                                
 * @author Julien Dramaix (julien.dramaix@gmail.com)                              
 *                                                                                
 */                                                                               
public class ZIndexPlugin implements DraggablePlugin {                            
                                                                                  
  private static String OLD_ZINDEX_KEY="oldZIndex";                               
  private static String ZINDEX_CSS = "zIndex";                                    
                                                                                  
  public String getName() {                                                       
    return "zIndex";                                                              
  }                                                                               
                                                                                  
  public void onDrag(DraggableHandler handler, Element draggableElement, Event e) { 
  }                                                                               
                                                                                  
  public void onStart(DraggableHandler handler, Element draggableElement, Event e) {
    GQuery $helper = handler.getHelper();                                                       
    String oldZIndex = $helper.css(ZINDEX_CSS);                                     
    if (oldZIndex != null){                                                       
      $helper.data(OLD_ZINDEX_KEY, oldZIndex);                                      
    }                                                                             
    $helper.css(ZINDEX_CSS, handler.getOptions().getZIndex().toString());            
                                                                                  
                                                                                  
                                                                                  
  }                                                                               
                                                                                  
  public void onStop(DraggableHandler handler, Element draggableElement, Event e) { 
    GQuery $helper = handler.getHelper();                                                       
    String oldZIndex = $helper.data(OLD_ZINDEX_KEY, String.class);                  
    $helper.css(ZINDEX_CSS, oldZIndex);                                             
  }                                                                               
                                                                                  
  public boolean hasToBeExecuted(DraggableOptions options) {                      
    return options.getZIndex() != null;                                           
  }                                                                               
                                                                                  
}                                                                                 
                                                                                  