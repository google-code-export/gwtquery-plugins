package gwtquery.plugins.droppable.client.greddysample;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;
import static gwtquery.plugins.droppable.client.Droppable.Droppable;

import com.google.gwt.core.client.EntryPoint;

import gwtquery.plugins.droppable.client.DroppableOptions;
import gwtquery.plugins.droppable.client.DroppableOptions.DroppableFunction;
import gwtquery.plugins.droppable.client.events.DragAndDropContext;

public class GreddySample implements EntryPoint {

  public void onModuleLoad() {
    
    $("#draggable").as(Draggable).draggable();
    
    
    $("#mainDroppable1").as(Droppable).droppable(createDroppableOptions(false));
    $("#innerDroppable1").as(Droppable).droppable(createDroppableOptions(false));
    $("#mainDroppable2").as(Droppable).droppable(createDroppableOptions(false));
    $("#innerDroppable2").as(Droppable).droppable(createDroppableOptions(true));
    
  }
  
  /**
   * Create droppable options having some visual feedback on drop operation 
   * @return
   */
  private DroppableOptions createDroppableOptions(final boolean greedy){
    DroppableOptions options = new DroppableOptions();
    options.setActiveClass("white-background");
    options.setHoverClass("yellow-background");
    options.setGreedy(greedy);
    options.setOnDrop(new DroppableFunction() {
      public void f(DragAndDropContext context) {
        $("#"+context.getDroppable().getId()+" > p").html("The draggable was dropped on me");
        context.getDroppable().addClassName("orange-background");
      }
    });
    options.setOnActivate(new DroppableFunction() {
      public void f(DragAndDropContext context) {
        $("#"+context.getDroppable().getId()+" > p").html("I'm the "+context.getDroppable().getId()+ " and I'm "+(greedy?"greddy":"not greddy"));
        context.getDroppable().removeClassName("orange-background");
      }
    });
    return options;
  }

}
