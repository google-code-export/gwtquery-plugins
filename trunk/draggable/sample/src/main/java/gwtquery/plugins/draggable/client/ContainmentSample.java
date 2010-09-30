package gwtquery.plugins.draggable.client;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;

import com.google.gwt.core.client.EntryPoint;

/**
 * Example code BasePlugin plugin for GwtQuery
 */
public class ContainmentSample implements EntryPoint {

 
  
  public void onModuleLoad() {
   
    DraggableOptions o = new DraggableOptions();
    o.setContainment(new DraggableContainment("#box"));
    $("#draggable").as(Draggable).draggable(o);
    
     
    o = new DraggableOptions();
    o.setContainment(new DraggableContainment("#parentBox"));
    $("#draggable2").as(Draggable).draggable(o);
    
    o = new DraggableOptions();
    o.setContainment(new DraggableContainment("#draggable3"));
    $("#draggable3 p").as(Draggable).draggable(o);
   
   
  }
  
}
