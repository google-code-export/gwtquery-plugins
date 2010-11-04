package gwtquery.plugins.droppable.client.simplesample;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;
import static gwtquery.plugins.droppable.client.Droppable.Droppable;

import com.google.gwt.core.client.EntryPoint;

import gwtquery.plugins.droppable.client.DroppableOptions;
import gwtquery.plugins.droppable.client.DroppableOptions.DroppableFunction;
import gwtquery.plugins.droppable.client.events.DragAndDropContext;

public class SimpleSample implements EntryPoint {

  public void onModuleLoad() {

    $("#draggable").as(Draggable).draggable();

    $("#droppable").as(Droppable).droppable(createDroppableOptions());
  }

  private DroppableOptions createDroppableOptions() {
    DroppableOptions options = new DroppableOptions();
    options.setHoverClass("yellow-background");
    options.setActiveClass("white-background");
    options.setOnDrop(new DroppableFunction() {
      public void f(DragAndDropContext context) {
        $("p", context.getDroppable()).html(
            "Element " + context.getDraggable().getId() + " was drop on me");
        context.getDroppable().addClassName("orange-background");

      }
    });
    options.setOnActivate(new DroppableFunction() {

      public void f(DragAndDropContext context) {
        $("p", context.getDroppable()).html("I'm ready to accept an element");
        context.getDroppable().removeClassName("orange-background");

      }
    });
    options.setOnDeactivate(new DroppableFunction() {

      public void f(DragAndDropContext context) {
        $("p", context.getDroppable()).html("I'm a drop target");

      }
    });
    options.setOnOver(new DroppableFunction() {
      
      public void f(DragAndDropContext context) {
        $("p", context.getDroppable()).html(
            "The element " + context.getDraggable().getId() + " is over me and can be dropped");

      }
    });

    options.setOnOut(new DroppableFunction() {
      public void f(DragAndDropContext context) {
        $("p", context.getDroppable()).html("I'm ready to accept an element");

      }

    });

    return options;
  }

}
