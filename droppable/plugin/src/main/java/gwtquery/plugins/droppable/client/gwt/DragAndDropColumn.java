package gwtquery.plugins.droppable.client.gwt;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.Column;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.droppable.client.DroppableOptions;
import gwtquery.plugins.droppable.client.gwt.CellDragAndDropBehaviour.CellDragOnlyBehaviour;
import gwtquery.plugins.droppable.client.gwt.CellDragAndDropBehaviour.CellDropOnlyBehaviour;

public abstract class DragAndDropColumn<T,C> extends Column<T, C>{

  private CellDragAndDropBehaviour<T> cellDragAndDropBehaviour;
  /**
   * The options used for draggable cells.
   */
  private DraggableOptions draggableOptions;

  /**
   * The options used for droppable cells.
   */
  private DroppableOptions droppableOptions;
  
  /**
   * Construct a new Column with a given {@link Cell}.
   *
   * @param cell the Cell used by this Column
   */
  public DragAndDropColumn(Cell<C> cell) {
    this(cell,null);
  }
  
  /**
   * Construct a new Column with a given {@link Cell}.
   *
   * @param cell the Cell used by this Column
   */
  public DragAndDropColumn(Cell<C> cell, CellDragAndDropBehaviour<T> cellDragAndDropBehaviour) {
    super(cell);
    this.cellDragAndDropBehaviour=cellDragAndDropBehaviour;
    this.draggableOptions = new DraggableOptions();
    this.droppableOptions = new DroppableOptions();
  }
  
  public CellDragAndDropBehaviour<T> getCellDragAndDropBehaviour() {
    return cellDragAndDropBehaviour;
  }

  public void setCellDragAndDropBehaviour(
      CellDragAndDropBehaviour<T> cellDragAndDropBehaviour) {
    this.cellDragAndDropBehaviour = cellDragAndDropBehaviour;
  }

  public DraggableOptions getDraggableOptions() {
    return draggableOptions;
  }

  public DroppableOptions getDroppableOptions() {
    return droppableOptions;
  }

  public void setDraggableOptions(DraggableOptions draggableOptions) {
    this.draggableOptions = draggableOptions;
  }

  public void setDroppableOptions(DroppableOptions droppableOptions) {
    this.droppableOptions = droppableOptions;
  }

  public void setCellDroppableOnly() {
    cellDragAndDropBehaviour = new CellDropOnlyBehaviour<T>();
  }

  public void setCellDraggableOnly() {
    cellDragAndDropBehaviour = new CellDragOnlyBehaviour<T>();

  }
  
  
  
  
}
