package gwtquery.plugins.droppable.client.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.CellTable.Resources;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;

import java.util.ArrayList;
import java.util.List;

import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent;
import gwtquery.plugins.draggable.client.events.DragEvent;
import gwtquery.plugins.draggable.client.events.DragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent.BeforeDragStartEventHandler;
import gwtquery.plugins.draggable.client.events.DragEvent.DragEventHandler;
import gwtquery.plugins.draggable.client.events.DragStartEvent.DragStartEventHandler;
import gwtquery.plugins.draggable.client.events.DragStopEvent.DragStopEventHandler;
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
import gwtquery.plugins.droppable.client.gwt.extend.com.google.gwt.user.cellview.client.CellTable;

public class DragAndDropCellTable<T> extends CellTable<T> {

  private final List<Column<T, ?>> columns = new ArrayList<Column<T, ?>>();

  private EventBus dragAndDropHandlerManager;

  /**
   * Constructs a table with a default page size of 15.
   */
  public DragAndDropCellTable() {
    super();
  }

  /**
   * Constructs a table with the given page size.
   * 
   * @param pageSize
   *          the page size
   */
  public DragAndDropCellTable(final int pageSize) {
    super(pageSize);
  }

  /**
   * Constructs a table with a default page size of 15, and the given
   * {@link ProvidesKey key provider}.
   * 
   * @param keyProvider
   *          an instance of ProvidesKey<T>, or null if the record object should
   *          act as its own key
   */
  public DragAndDropCellTable(ProvidesKey<T> keyProvider) {
    super(keyProvider);
  }

  /**
   * Constructs a table with the given page size with the specified
   * {@link Resources}.
   * 
   * @param pageSize
   *          the page size
   * @param resources
   *          the resources to use for this widget
   */
  public DragAndDropCellTable(final int pageSize, Resources resources) {
    super(pageSize, resources);
  }

  /**
   * Constructs a table with the given page size and the given
   * {@link ProvidesKey key provider}.
   * 
   * @param pageSize
   *          the page size
   * @param keyProvider
   *          an instance of ProvidesKey<T>, or null if the record object should
   *          act as its own key
   */
  public DragAndDropCellTable(final int pageSize, ProvidesKey<T> keyProvider) {
    super(pageSize, keyProvider);
  }

  /**
   * Constructs a table with the given page size, the specified
   * {@link Resources}, and the given key provider.
   * 
   * @param pageSize
   *          the page size
   * @param resources
   *          the resources to use for this widget
   * @param keyProvider
   *          an instance of ProvidesKey<T>, or null if the record object should
   *          act as its own key
   */
  public DragAndDropCellTable(final int pageSize, Resources resources,
      ProvidesKey<T> keyProvider) {
    super(pageSize, resources, keyProvider);

  }

  public void addColumn(Column<T, ?> col, Header<?> header, Header<?> footer) {
    super.addColumn(col, header, footer);
    columns.add(col);
  }

  @Override
  public void removeColumn(int index) {
    super.removeColumn(index);
    columns.remove(index);
  }

  @Override
  protected void renderRowValues(SafeHtmlBuilder sb, List<T> values, int start,
      SelectionModel<? super T> selectionModel) {
    // Lets the super class do its jobs
    super.renderRowValues(sb, values, start, selectionModel);

    // make all cell draggable and/or droppable
    int end = start + values.size();

    for (int row = start; row < end; row++) {
      final T value = values.get(row - start);
      for (int column = 0; column < columns.size(); column++) {
        addDragAndDropBehaviour(row, column, value);
      }

    }

  }

  protected void addDragAndDropBehaviour(final int rowIndex,
      final int columnIndex, final T value) {

    Column<T, ?> column = columns.get(columnIndex);
    if (!(column instanceof DragAndDropColumn<?, ?>)) {
      return;
    }

    final DragAndDropColumn<T, ?> dndColumn = (DragAndDropColumn<T, ?>) column;

    // retrieve the div surrounding the cell
    final Element oldCellWrapper = getCellWrapperDiv(rowIndex, columnIndex);

    // make the new cell draggable and droppable
    // use a deferred command to be sure that the new cell is attached to the
    // dom
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {

      public void execute() {
        DragAndDropCellWidgetUtils dndUtils = DragAndDropCellWidgetUtils.get();

        dndUtils.cleanCell(oldCellWrapper);

        Element newCell = getCellWrapperDiv(rowIndex, columnIndex);

        dndUtils.maybeMakeDraggableOrDroppable(newCell, value, dndColumn
            .getCellDragAndDropBehaviour(), dndColumn.getDraggableOptions(),
            dndColumn.getDroppableOptions(), ensureDrangAndDropHandlers());

      }
    });

  }

  private Element getCellWrapperDiv(int rowIndex, int columnIndex) {
    TableSectionElement tbody = getChildContainer().cast();
    int rowsNbr = tbody.getRows().getLength();
    if (rowIndex < rowsNbr) {
      TableRowElement row = tbody.getRows().getItem(rowIndex);
      int columnNbr = row.getCells().getLength();
      if (columnIndex < columnNbr) {
        return row.getCells().getItem(columnIndex).getFirstChildElement();
      }
    }
    return null;
  }

  @Override
  protected void onUnload() {

    Element childContainer = getChildContainer();
    DragAndDropCellWidgetUtils dndUtils = DragAndDropCellWidgetUtils.get();
    for (int i = 0; i < childContainer.getChildCount(); i++) {
      dndUtils.cleanCell((Element) childContainer.getChild(i).getFirstChild()
          .cast());
    }
    super.onUnload();
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

}
