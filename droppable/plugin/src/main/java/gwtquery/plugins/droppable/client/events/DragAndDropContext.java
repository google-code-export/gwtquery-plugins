package gwtquery.plugins.droppable.client.events;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery.Offset;

import gwtquery.plugins.draggable.client.DraggableHandler;

/**
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class DragAndDropContext {

  private Element draggable;
  private Element helper;
  private Offset helperPosition;
  private Offset draggableOffset;
  private Element droppable;

  public DragAndDropContext(Element draggable, Element droppable) {
    this.draggable = draggable;
    this.droppable = droppable;
    init();
  }

  private void init() {
    DraggableHandler handler = DraggableHandler.getInstance(draggable);
    if (handler.getHelper() != null) {
      helper = handler.getHelper().get(0);
    }
    helperPosition = handler.getPosition();
    draggableOffset = handler.getAbsPosition();
  }

  public Element getDraggable() {
    return draggable;
  }

  public Element getHelper() {
    return helper;
  }

  public Offset getHelperPosition() {
    return helperPosition;
  }

  public Offset getDraggableOffset() {
    return draggableOffset;
  }

  public void setDraggable(Element draggable) {
    this.draggable = draggable;
  }

  public void setHelper(Element helper) {
    this.helper = helper;
  }

  public void setHelperPosition(Offset helperPosition) {
    this.helperPosition = helperPosition;
  }

  public void setDraggableOffset(Offset draggableOffset) {
    this.draggableOffset = draggableOffset;
  }

  public Element getDroppable() {
    return droppable;
  }

  public void setDroppable(Element droppable) {
    this.droppable = droppable;
  }

}
