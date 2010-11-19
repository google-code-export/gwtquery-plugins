package gwtquery.plugins.droppable.client.gwt;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.query.client.Function;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.CellList.Resources;
import com.google.gwt.view.client.ProvidesKey;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent;
import gwtquery.plugins.draggable.client.events.DragEvent;
import gwtquery.plugins.draggable.client.events.DragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent.BeforeDragStartEventHandler;
import gwtquery.plugins.draggable.client.events.DragEvent.DragEventHandler;
import gwtquery.plugins.draggable.client.events.DragStartEvent.DragStartEventHandler;
import gwtquery.plugins.draggable.client.events.DragStopEvent.DragStopEventHandler;
import gwtquery.plugins.droppable.client.DroppableOptions;
import gwtquery.plugins.droppable.client.events.ActivateDroppableEvent;
import gwtquery.plugins.droppable.client.events.DeactivateDroppableEvent;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent;
import gwtquery.plugins.droppable.client.events.ActivateDroppableEvent.ActivateDroppableEventHandler;
import gwtquery.plugins.droppable.client.events.DeactivateDroppableEvent.DeactivateDroppableEventHandler;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent.OutDroppableEventHandler;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent.OverDroppableEventHandler;
import gwtquery.plugins.droppable.client.gwt.extend.com.google.gwt.user.cellview.client.CellList;

import java.util.List;

@SuppressWarnings("deprecation")
public class DragAndDropCellList<T> extends CellList<T> {

  private CellDragAndDropBehaviour<T> cellDragAndDropBehaviour;

  private static Resources DEFAULT_RESOURCES = GWT.create(Resources.class);

  private DraggableOptions draggableOptions;

  private DroppableOptions droppableOptions;

  private EventBus dragAndDropHandlerManager;

  /**
   * Construct a new {@link DragAndDropCellList}.
   * 
   * @param cell
   *          the cell used to render each item
   */
  public DragAndDropCellList(final Cell<T> cell) {
    this(cell, null, null, null);

  }

  /**
   * Construct a new {@link DragAndDropCellList}.
   * 
   * @param cell
   *          the cell used to render each item
   * @param cellDragAndDropBehaviour
   *          an instance of {@link CellDragAndDropBehaviour} or null if you
   *          want that all cell are draggable and droppable.
   */
  public DragAndDropCellList(final Cell<T> cell,
      CellDragAndDropBehaviour<T> cellDragAndDropBehaviour) {
    this(cell, DEFAULT_RESOURCES, null, cellDragAndDropBehaviour);

  }

  /**
   * Construct a new {@link DragAndDropCellList} with the specified
   * {@link Resources}.
   * 
   * @param cell
   *          the cell used to render each item
   * @param resources
   *          the resources used for this widget
   */
  public DragAndDropCellList(final Cell<T> cell, Resources resources) {
    this(cell, resources, null, null);
  }

  /**
   * Construct a new {@link DragAndDropCellList} with the specified
   * {@link Resources}.
   * 
   * @param cell
   *          the cell used to render each item
   * @param resources
   *          the resources used for this widget
   * @param cellDragAndDropBehaviour
   *          an instance of {@link CellDragAndDropBehaviour} or null if you
   *          want that all cell are draggable and droppable.
   */
  public DragAndDropCellList(final Cell<T> cell, Resources resources,
      CellDragAndDropBehaviour<T> cellDragAndDropBehaviour) {
    this(cell, resources, null, cellDragAndDropBehaviour);
  }

  /**
   * Construct a new {@link DragAndDropCellList} with the specified
   * {@link ProvidesKey key provider}.
   * 
   * @param cell
   *          the cell used to render each item
   * @param keyProvider
   *          an instance of ProvidesKey<T>, or null if the record object should
   *          act as its own key
   */
  public DragAndDropCellList(final Cell<T> cell, ProvidesKey<T> keyProvider) {
    this(cell, DEFAULT_RESOURCES, keyProvider, null);
  }

  /**
   * Construct a new {@link DragAndDropCellList} with the specified
   * {@link ProvidesKey key provider}.
   * 
   * @param cell
   *          the cell used to render each item
   * @param keyProvider
   *          an instance of ProvidesKey<T>, or null if the record object should
   *          act as its own key
   * @param cellDragAndDropBehaviour
   *          an instance of {@link CellDragAndDropBehaviour} or null if you
   *          want that all cell are draggable and droppable.
   */
  public DragAndDropCellList(final Cell<T> cell, ProvidesKey<T> keyProvider,
      CellDragAndDropBehaviour<T> cellDragAndDropBehaviour) {
    this(cell, DEFAULT_RESOURCES, keyProvider, cellDragAndDropBehaviour);
  }

  /**
   * Construct a new {@link DragAndDropCellList} with the specified
   * {@link Resources} and {@link ProvidesKey key provider}.
   * 
   * @param cell
   *          the cell used to render each item
   * @param resources
   *          the resources used for this widget
   * @param keyProvider
   *          an instance of ProvidesKey<T>, or null if the record object should
   *          act as its own key
   */
  public DragAndDropCellList(final Cell<T> cell, Resources resources,
      ProvidesKey<T> keyProvider) {
    this(cell, resources, keyProvider, null);
  }

  /**
   * Construct a new {@link DragAndDropCellList} with the specified
   * {@link Resources} and {@link ProvidesKey key provider}.
   * 
   * @param cell
   *          the cell used to render each item
   * @param resources
   *          the resources used for this widget
   * @param keyProvider
   *          an instance of ProvidesKey<T>, or null if the record object should
   *          act as its own key
   * @param cellDragAndDropBehaviour
   *          an instance of {@link CellDragAndDropBehaviour} or null if you
   *          want that all cell are draggable and droppable.
   */
  public DragAndDropCellList(final Cell<T> cell, Resources resources,
      ProvidesKey<T> keyProvider,
      CellDragAndDropBehaviour<T> cellDragAndDropBehaviour) {
    super(cell, resources, keyProvider);
    this.cellDragAndDropBehaviour = cellDragAndDropBehaviour;
    this.draggableOptions = new DraggableOptions();
    this.droppableOptions = new DroppableOptions();
  }

  @Override
  protected void onUnload() {

    cleanAllCells();
    super.onUnload();
  }

  protected void addDragAndDropBehaviour(List<T> values, int start) {

    int end = start + values.size();
    
    for (int rowIndex = start; rowIndex < end; rowIndex++) {
      T value = values.get(rowIndex-start);
      Element newCell = getRowElement(rowIndex);

      DragAndDropCellWidgetUtils.get().maybeMakeDraggableOrDroppable(newCell,
          value, cellDragAndDropBehaviour, draggableOptions, droppableOptions,
          ensureDrangAndDropHandlers());
    }

  }

  public CellDragAndDropBehaviour<T> getCellDragAndDropBehaviour() {
    return cellDragAndDropBehaviour;
  }

  public void setCellDragAndDropBehaviour(
      CellDragAndDropBehaviour<T> cellDragAndDropBehaviour) {
    this.cellDragAndDropBehaviour = cellDragAndDropBehaviour;
  }

  public DroppableOptions getDroppableOptions() {
    return droppableOptions;
  }

  public DraggableOptions getDraggableOptions() {
    return draggableOptions;
  }

  public void setDroppableOptions(DroppableOptions droppableOptions) {
    this.droppableOptions = droppableOptions;
  }

  public void setDraggableOptions(DraggableOptions draggableOptions) {
    this.draggableOptions = draggableOptions;
  }

  protected final <H extends EventHandler> HandlerRegistration addDragAndDropHandler(
      H handler, Type<H> type) {
    GWT.log("add handler " + handler + " to eventbus :"
        + ensureDrangAndDropHandlers());
    return ensureDrangAndDropHandlers().addHandler(type, handler);
  }

  protected EventBus ensureDrangAndDropHandlers() {

    return dragAndDropHandlerManager == null ? dragAndDropHandlerManager = new SimpleEventBus()
        : dragAndDropHandlerManager;
  }

  /**
   * Add a handler object that will manage the {@link BeforeDragStartEvent}
   * event. this kind of event is fired before the initialization of the drag
   * operation.
   */
  public HandlerRegistration addBeforeDragHandler(
      BeforeDragStartEventHandler handler) {
    return addDragAndDropHandler(handler, BeforeDragStartEvent.TYPE);
  }

  /**
   * Add a handler object that will manage the {@link DragEvent} event. this
   * kind of event is fired during the move of the widget.
   */
  public HandlerRegistration addDragHandler(DragEventHandler handler) {
    return addDragAndDropHandler(handler, DragEvent.TYPE);
  }

  /**
   * Add a handler object that will manage the {@link DragStartEvent} event.
   * This kind of event is fired when the drag operation starts.
   */
  public HandlerRegistration addDragStartHandler(DragStartEventHandler handler) {
    return addDragAndDropHandler(handler, DragStartEvent.TYPE);
  }

  /**
   * Add a handler object that will manage the {@link DragStopEvent} event. This
   * kind of event is fired when the drag operation stops.
   */
  public HandlerRegistration addDragStopHandler(DragStopEventHandler handler) {
    return addDragAndDropHandler(handler, DragStopEvent.TYPE);
  }

  public HandlerRegistration addActivateDroppableHandler(
      ActivateDroppableEventHandler handler) {
    return addDragAndDropHandler(handler, ActivateDroppableEvent.TYPE);
  }

  public HandlerRegistration addDeactivateDroppableHandler(
      DeactivateDroppableEventHandler handler) {
    return addDragAndDropHandler(handler, DeactivateDroppableEvent.TYPE);
  }

  public HandlerRegistration addDropHandler(DropEventHandler handler) {
    return addDragAndDropHandler(handler, DropEvent.TYPE);
  }

  public HandlerRegistration addOutDroppableHandler(
      OutDroppableEventHandler handler) {
    return addDragAndDropHandler(handler, OutDroppableEvent.TYPE);
  }

  public HandlerRegistration addOverDroppableHandler(
      OverDroppableEventHandler handler) {
    return addDragAndDropHandler(handler, OverDroppableEvent.TYPE);
  }

  void setDragAndDropHandlerManager(EventBus eventBus) {
    this.dragAndDropHandlerManager = eventBus;
  }

  @Override
  protected void replaceAllChildren(List<T> values, SafeHtml html) {
    // first clean old cell before remove it
    cleanAllCells();

    // lets the super class replace all child
    super.replaceAllChildren(values, html);

 
    // make the new cell draggable or droppable
    addDragAndDropBehaviour(values, 0);

  }

  @Override
  protected void replaceChildren(List<T> values, int start, SafeHtml html) {
    // clean cell has being replaced
    int end = start + values.size();
    for (int rowIndex = start; rowIndex < end; rowIndex++) {
      Element oldCell = getRowElement(rowIndex);
      DragAndDropCellWidgetUtils.get().cleanCell(oldCell);
    }

    // lets the super class replace all child
    super.replaceChildren(values, start, html);

    // make the new cell draggable or droppable
    addDragAndDropBehaviour(values, start);
    
    
  }

  protected void cleanAllCells() {
    $(getChildContainer()).children().each(new Function() {
      @Override
      public void f(Element div) {
        DragAndDropCellWidgetUtils.get().cleanCell(div);
      }
    });

  }

}
