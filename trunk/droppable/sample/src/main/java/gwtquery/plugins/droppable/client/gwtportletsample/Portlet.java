package gwtquery.plugins.droppable.client.gwtportletsample;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent.BeforeDragStartEventHandler;
import gwtquery.plugins.draggable.client.events.DragStopEvent.DragStopEventHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

public class Portlet extends DraggableWidget<Widget> {
  
  /**
   * This handler will modify the position css attribute of portlets during the drag
   * @author Julien Dramaix (julien.dramaix@gmail.com)
   *
   */
  private static class DraggablePositionHandler implements BeforeDragStartEventHandler, DragStopEventHandler{

    
    public void onBeforeDragStart(BeforeDragStartEvent event) {
      Element draggableElement = event.getDraggable();
      $(draggableElement).css("position", "absolute");
      
    }

    public void onDragStop(DragStopEvent event) {
      Element draggableElement = event.getDraggable();
      $(draggableElement).css("position", "relative").css("top",null).css("left", null);
      
      
    }
  }

  interface PortletUiBinder extends UiBinder<Widget, Portlet> {
  }

  private static PortletUiBinder uiBinder = GWT.create(PortletUiBinder.class);
  //one instance of this handler is sufficient
  private static DraggablePositionHandler HANDLER = new DraggablePositionHandler();

  @UiField
  DivElement content;
  @UiField
  DivElement header;

  public Portlet(String header, String content) {
    initWidget(uiBinder.createAndBindUi(this));
    setup();
    setHeader(header);
    setContent(content);
  }

  public void setContent(String content) {
    this.content.setInnerText(content);
  }

  public void setHeader(String header) {
    this.header.setInnerText(header);
  }

  private void setup() {
    //useCloneAsHelper();
    setOpacity(new Float(0.8));
    setZIndex(1000);
    setRevert(RevertOption.ON_INVALID_DROP);
    addBeforeDragHandler(HANDLER);
    addDragStopHandler(HANDLER);
    

  }
}
