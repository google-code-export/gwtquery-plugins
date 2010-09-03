package gwtquery.plugins.draggable.client;

import static com.google.gwt.query.client.GQuery.*;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;

import com.google.gwt.core.client.EntryPoint;

/**
 * Example code BasePlugin plugin for GwtQuery
 */
public class DraggableSample1 implements EntryPoint {

  public void onModuleLoad() {
    
    $("#iWantTobeDraggable").as(Draggable).draggable();
    
  }
}
