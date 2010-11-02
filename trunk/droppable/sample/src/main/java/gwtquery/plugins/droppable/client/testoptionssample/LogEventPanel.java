package gwtquery.plugins.droppable.client.testoptionssample;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.droppable.client.testoptionssample.TestOptionsSample.EVENT_BUS;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.draggable.client.events.DragEvent;
import gwtquery.plugins.draggable.client.events.DragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
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

public class LogEventPanel extends Composite implements DragStartEventHandler,
    DragStopEventHandler, DragEventHandler, DropEventHandler,
    DeactivateDroppableEventHandler, ActivateDroppableEventHandler,
    OutDroppableEventHandler, OverDroppableEventHandler {

  @UiTemplate(value = "LogEventPanel.ui.xml")
  interface LogEventPanelUiBinder extends
      UiBinder<Widget, LogEventPanel> {
  }

  private static LogEventPanelUiBinder uiBinder = GWT
      .create(LogEventPanelUiBinder.class);
  
  private static String SHOW_WINDOW_EVENT="Show log window";
  private static String HIDE_WINDOW_EVENT="Hide log window";

  @UiField
  Button windowToogleButton;
  @UiField
  Button clearLogButton;
  @UiField
  CheckBox enableDragMove;
  @UiField
  DivElement logEventWindow;
  
  private boolean windowOpen = true;
      
  public LogEventPanel() {
    initWidget(uiBinder.createAndBindUi(this));
    bind();
    addStyleName("logEventPanel");
  }

  private void toogleWindowLog() {
    if (windowOpen){
      $(logEventWindow).hide();
      windowOpen = false;
      windowToogleButton.setText(SHOW_WINDOW_EVENT);
    }else{
      $(logEventWindow).show();
      windowOpen = true;
      windowToogleButton.setText(HIDE_WINDOW_EVENT);
    }
    
  }

  private void bind() {
    EVENT_BUS.addHandler(DragStartEvent.TYPE, this);
    EVENT_BUS.addHandler(DragStopEvent.TYPE, this);
    EVENT_BUS.addHandler(DragEvent.TYPE, this);
    EVENT_BUS.addHandler(DropEvent.TYPE, this);
    EVENT_BUS.addHandler(DeactivateDroppableEvent.TYPE, this);
    EVENT_BUS.addHandler(ActivateDroppableEvent.TYPE, this);
    EVENT_BUS.addHandler(OutDroppableEvent.TYPE, this);
    EVENT_BUS.addHandler(OverDroppableEvent.TYPE, this);

  }
  
  private boolean isDragMoveLoggingEnable(){
    return  enableDragMove.getValue();
  }
  
  private void log(String log){
    String innerHtml = $(logEventWindow).html();
    innerHtml+=log+"<br />";
    $(logEventWindow).html(innerHtml);
    logEventWindow.setScrollTop(logEventWindow.getScrollHeight());
    
  }
  
  @UiHandler(value = { "windowToogleButton" })
  public void onToogleWindowClicked(ClickEvent e){
    toogleWindowLog();
  }

  @UiHandler(value = { "clearLogButton" })
  public void onClearLogClicked(ClickEvent e){
    $(logEventWindow).html("");
    logEventWindow.setScrollTop(0);
  }
  
  public void onDragStart(DragStartEvent event) {
    log("Drag started for draggable "+event.getDraggable().getId());

  }

  public void onDragStop(DragStopEvent event) {
    log("Drag stopped for draggable "+event.getDraggable().getId());
    log("==========================");

  }

  public void onDrag(DragEvent event) {
    if (isDragMoveLoggingEnable()){
      log("Dragging draggable "+event.getDraggable().getId());
    }

  }

  public void onDrop(DropEvent event) {
    log("The draggable "+event.getDraggable().getId()+ " was drop in the droppable "+event.getDroppable().getId());

  }

  public void onDeactivateDroppable(DeactivateDroppableEvent event) {
    log("Deactivate droppable "+event.getDroppable().getId());

  }

  public void onActivateDroppable(ActivateDroppableEvent event) {
    log("Activate droppable "+event.getDroppable().getId());

  }

  public void onOutDroppable(OutDroppableEvent event) {
    log("The draggable "+event.getDraggable().getId()+" is out the droppable "+event.getDroppable().getId());

  }

  public void onOverDroppable(OverDroppableEvent event) {
    log("The draggable "+event.getDraggable().getId()+" is over the droppable "+event.getDroppable().getId());

  }
}
