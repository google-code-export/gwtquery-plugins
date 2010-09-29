package gwtquery.plugins.draggable.client;

import static com.google.gwt.query.client.GQuery.*;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;

import gwtquery.plugins.draggable.client.DraggableOptions.SnapMode;

/**
 * Example code BasePlugin plugin for GwtQuery
 */
public class SnapSample implements EntryPoint {

 
  
  public void onModuleLoad() {
   
    DraggableOptions o = new DraggableOptions();
    o.setSnap(true);
    $("#draggable").as(Draggable).draggable(o);
    
     
    o = new DraggableOptions();
    o.setSnap("#SnapTarget");
    $("#draggable2").as(Draggable).draggable(o);
    
    o = new DraggableOptions();
    o.setSnap("#SnapTarget");
    o.setSnapMode(SnapMode.OUTER);
    $("#draggable3").as(Draggable).draggable(o);
    
    o = new DraggableOptions();
    int[] grid = {20,20};
    o.setGrid(grid);
    $("#draggable4").as(Draggable).draggable(o);
    
    o = new DraggableOptions();
    int[] grid2 = {80,80};
    o.setGrid(grid2);
    $("#draggable5").as(Draggable).draggable(o);
   
  }
  
  /*$("#draggable").draggable({ snap: true });
    $("#draggable2").draggable({ snap: '.ui-widget-header' });
    $("#draggable3").draggable({ snap: '.ui-widget-header', snapMode: 'outer' });
    $("#draggable4").draggable({ grid: [20,20] });
    $("#draggable5").draggable({ grid: [80, 80] });

*/
}
