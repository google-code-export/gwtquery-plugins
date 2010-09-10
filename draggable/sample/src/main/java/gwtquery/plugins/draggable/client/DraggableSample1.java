package gwtquery.plugins.draggable.client;

import static com.google.gwt.query.client.GQuery.*;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;

/**
 * Example code BasePlugin plugin for GwtQuery
 */
public class DraggableSample1 implements EntryPoint {

  private int startCounter = 0;
  private int dragCounter = 0;
  private int stopCounter = 0;
  
  public void onModuleLoad() {
    //simpleDraggable div
    DraggableOptions o = createOptionsForSimpleDraggable(); 
    $("#simpleDraggable").as(Draggable).draggable(o);
    
    //three div inside div with id #set
    DraggableOptions o2 = new DraggableOptions();
    o2.setStack("#setOfDraggable div");
    $("#setOfDraggable div").as(Draggable).draggable(o2);
  }
  
  private DraggableOptions createOptionsForSimpleDraggable() {
    DraggableOptions o = new DraggableOptions();
    o.setOnDragStart(new Function() {
      @Override
      public void f(Element e) {
        startCounter++;
        $("#startCounter").html(""+startCounter);
      }
    });
    
    o.setOnDrag(new Function() {
      @Override
      public void f(Element e) {
        dragCounter++;
        $("#dragCounter").html(""+dragCounter);
      }
    });
    
    o.setOnDragStop(new Function() {
      @Override
      public void f(Element e) {
        stopCounter++;
        $("#stopCounter").html(""+stopCounter);
      }
    });
    return o;
    
  }
}
