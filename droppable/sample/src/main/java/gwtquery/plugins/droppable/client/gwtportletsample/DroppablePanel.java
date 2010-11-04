package gwtquery.plugins.droppable.client.gwtportletsample;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.user.client.ui.FlowPanel;

import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

public class DroppablePanel extends DroppableWidget<FlowPanel> {

  private FlowPanel innerPanel;

  public DroppablePanel() {
    init();
    initWidget(innerPanel);
    setupDrop();
  }

  public void add(Portlet p) {
    innerPanel.add(p);
  }

  private void init() {
    innerPanel = new FlowPanel();
    // init style with GQuery
    $(innerPanel).css("paddingBottom", "100px").css("width", "200px").css(
        "float", "left").css("marginLeft", "10px");

  }

  private void setupDrop() {
    DragAndDropHandler handler = new DragAndDropHandler(innerPanel);
    addDropHandler(handler);
    addOutDroppableHandler(handler);
    addOverDroppableHandler(handler);
  }

}
