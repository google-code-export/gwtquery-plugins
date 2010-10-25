package gwtquery.plugins.droppable.client.events;


import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import gwtquery.plugins.draggable.client.gwt.DraggableWidget;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

public abstract class AbstractDroppableEvent<H extends EventHandler> extends
		GwtEvent<H> {

	private DragAndDropContext draggableInfo;

	public AbstractDroppableEvent() {
	
	}
	
	public AbstractDroppableEvent(Element droppable, Element draggable) {
		this.draggableInfo = new DragAndDropContext(draggable, droppable);
	}
	
	
	public DragAndDropContext getDragDropInfo() {
		return draggableInfo;
	}

	public Element getDroppable() {
		if (draggableInfo != null) {
			return draggableInfo.getDroppable();
		}
		return null;
	}

	public void setDragDropInfo(DragAndDropContext draggableInfo) {
		this.draggableInfo = draggableInfo;
	}

	
	public Element getDraggable(){
		if (draggableInfo != null) {
			return draggableInfo.getDraggable();
		}
		return null;
	}
	
	public DraggableWidget<?> getDraggableWidget(){
    if (getDraggable() != null){
      return DraggableWidget.get(getDraggable());
    }
    return null;
  }
	
	public DroppableWidget<?> getDroppableWidget(){
    if (getDroppable() != null){
      return DroppableWidget.get(getDroppable());
    }
    return null;
  }
}
