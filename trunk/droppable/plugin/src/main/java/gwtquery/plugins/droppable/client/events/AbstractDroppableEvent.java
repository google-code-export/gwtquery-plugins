package gwtquery.plugins.droppable.client.events;


import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public abstract class AbstractDroppableEvent<H extends EventHandler> extends
		GwtEvent<H> {

	private DragDropInfo draggableInfo;

	public AbstractDroppableEvent() {
	
	}
	
	public AbstractDroppableEvent(Element droppable, Element draggable) {
		this.draggableInfo = new DragDropInfo(draggable, droppable);
	}
	
	
	public DragDropInfo getDragDropInfo() {
		return draggableInfo;
	}

	public Element getDroppable() {
		if (draggableInfo != null) {
			return draggableInfo.getDroppable();
		}
		return null;
	}

	public void setDragDropInfo(DragDropInfo draggableInfo) {
		this.draggableInfo = draggableInfo;
	}

	
	public Element getDraggable(){
		if (draggableInfo != null) {
			return draggableInfo.getDraggable();
		}
		return null;
	}
}
