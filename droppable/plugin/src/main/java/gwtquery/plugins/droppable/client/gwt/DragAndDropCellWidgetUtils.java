package gwtquery.plugins.droppable.client.gwt;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;
import static gwtquery.plugins.droppable.client.Droppable.Droppable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.query.client.GQuery;

import gwtquery.plugins.draggable.client.DraggableHandler;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.droppable.client.DroppableHandler;
import gwtquery.plugins.droppable.client.DroppableOptions;

/**
 * Util class allowing us to externalize all code used in CellWidget to manage the drag and drop behavior of cells
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 *
 */
public class DragAndDropCellWidgetUtils {
  
  public static final String VALUE_KEY = "__dragAndDropCellAssociatedValue"; 

  private static final DragAndDropCellWidgetUtils INSTANCE = new DragAndDropCellWidgetUtils();

  private DragAndDropCellWidgetUtils() {
  }

  static DragAndDropCellWidgetUtils get() {
    return INSTANCE;
  }

  void cleanCell(Element cell) {
    
    if (cell == null) {
      return;
    }
    
    GQuery $cell = $(cell);

    //GWT.log("clean old cell" + cell.hashCode());

    if (DraggableHandler.getInstance(cell) != null) {
      // GWT.log("clean darggable cell");
      $cell.as(Draggable).destroy();
    }

    if (DroppableHandler.getInstance(cell) != null) {
      // GWT.log("clean droppable cell");
      $cell.as(Droppable).destroy();
    }
    // GWT.log("clean cell by removing data " + $(e).data(VALUE_KEY));
    $cell.removeData(VALUE_KEY);
  }

  <C> void maybeMakeDraggableOrDroppable(Element cell, C value,
      CellDragAndDropBehaviour<C> cellDragAndDropBehaviour,
      DraggableOptions draggableOptions, DroppableOptions droppableOptions,
      EventBus eventBus) {
    

    GQuery $cell = $(cell);

    if ((cellDragAndDropBehaviour == null || cellDragAndDropBehaviour
        .isDraggable(value))
        && DraggableHandler.getInstance(cell) == null) {
      //GWT.log("make new cell " + cell.hashCode() + " draggable");
      //GWT.log("make dragable new cell " + cell.hashCode()+ " with value "+value);
      $cell.as(Draggable).draggable(draggableOptions, eventBus);
    }

    if ((cellDragAndDropBehaviour == null || cellDragAndDropBehaviour
        .isDroppable(value))
        && DroppableHandler.getInstance(cell) == null) {
      //GWT.log("make cell " + cell.hashCode() + " droppable");
      //GWT.log("make droppable new cell " + cell.hashCode()+ " with value "+value);
      $cell.as(Droppable).droppable(droppableOptions,eventBus);
    }

    $cell.data(VALUE_KEY, value);
  }

}
