package gwtquery.plugins.droppable.client.gwt;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList.Resources;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;

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

    Element childContainer = getChildContainer();
    DragAndDropCellWidgetUtils dndUtils = DragAndDropCellWidgetUtils.get();
    for (int i = 0; i < childContainer.getChildCount(); i++) {
      dndUtils.cleanCell((Element) childContainer.getChild(i).cast());
    }
    super.onUnload();
  }

  @Override
  protected void renderRowValues(SafeHtmlBuilder sb, List<T> values, int start,
      SelectionModel<? super T> selectionModel) {
    // GWT.log("Render "+blop+" from "+start+" with a list with size :"+values.size());
    // lets the super class do its job.
    super.renderRowValues(sb, values, start, selectionModel);

    // make all cell draggable and/or droppable
    int end = start + values.size();

    for (int i = start; i < end; i++) {
      final T value = values.get(i - start);
      addDragAndDropBehaviour(i, value);

    }

  }

  protected void addDragAndDropBehaviour(final int rowIndex, final T value) {

    final Element oldCell = getRowElementWithoutRowBoundsCheck(rowIndex);

    // make the new cell draggable and droppable
    // use a deferred command to be sure that the new cell is attached to the
    // dom
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {

      public void execute() {
        DragAndDropCellWidgetUtils dndUtils = DragAndDropCellWidgetUtils.get();

        dndUtils.cleanCell(oldCell);

        Element newCell = getRowElement(rowIndex);

        dndUtils.maybeMakeDraggableOrDroppable(newCell, value,
            cellDragAndDropBehaviour, draggableOptions, droppableOptions,
            ensureDrangAndDropHandlers());

      }
    });

  }

  private Element getRowElementWithoutRowBoundsCheck(int indexOnPage) {
    if (indexOnPage >= 0 && getChildContainer().getChildCount() > indexOnPage) {
      return getChildContainer().getChild(indexOnPage).cast();
    }
    return null;
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
    GWT.log("add handler "+handler+" to eventbus :"+ensureDrangAndDropHandlers());
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

}
