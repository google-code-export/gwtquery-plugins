package gwtquery.plugins.droppable.client.gwt;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.view.client.AbstractDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.TreeViewModel.DefaultNodeInfo;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.droppable.client.DroppableOptions;
import gwtquery.plugins.droppable.client.gwt.CellDragAndDropBehaviour.CellDragOnlyBehaviour;
import gwtquery.plugins.droppable.client.gwt.CellDragAndDropBehaviour.CellDropOnlyBehaviour;

public class DragAndDropNodeInfo<T> extends DefaultNodeInfo<T> {

  private CellDragAndDropBehaviour<T> cellDragAndDropBehaviour;
  /**
   * The options used for draggable cells.
   */
  private DraggableOptions draggableOptions;

  /**
   * The options used for droppable cells.
   */
  private DroppableOptions droppableOptions;

  public DragAndDropNodeInfo(AbstractDataProvider<T> dataProvider, Cell<T> cell) {
    this(dataProvider, cell, null, null);
  }

  public DragAndDropNodeInfo(AbstractDataProvider<T> dataProvider,
      Cell<T> cell, SelectionModel<? super T> selectionModel,
      ValueUpdater<T> valueUpdater) {

    this(dataProvider, cell, selectionModel, valueUpdater, null);
  }

  public DragAndDropNodeInfo(AbstractDataProvider<T> dataProvider,
      Cell<T> cell, SelectionModel<? super T> selectionModel,
      ValueUpdater<T> valueUpdater,
      CellDragAndDropBehaviour<T> cellDragAndDropBehaviour) {

    super(dataProvider, cell, selectionModel, valueUpdater);
    this.cellDragAndDropBehaviour = cellDragAndDropBehaviour;
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
