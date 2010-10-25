package gwtquery.plugins.droppable.client.events;

import gwtquery.plugins.draggable.client.DraggableHandler;
import gwtquery.plugins.draggable.client.DraggableHandler.LeftTopDimension;

import com.google.gwt.dom.client.Element;

/**
 * TODO If we use DraggableWidget and that draggable are detached and attached the DraggableHandler is new ...
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 *
 */
public class DragAndDropContext {
	
	private Element draggable;
	private Element helper;
	private LeftTopDimension helperPosition;
	private LeftTopDimension draggableOffset;
	private Element droppable;
	
	public DragAndDropContext(Element draggable, Element droppable) {
		this.draggable = draggable;
		this.droppable = droppable;
		init();
	}
	
	private void init(){
		DraggableHandler handler = DraggableHandler.getInstance(draggable);
		if (handler.getHelper() != null){
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

	public LeftTopDimension getHelperPosition() {
		return helperPosition;
	}

	public LeftTopDimension getDraggableOffset() {
		return draggableOffset;
	}

	public void setDraggable(Element draggable) {
		this.draggable = draggable;
	}

	public void setHelper(Element helper) {
		this.helper = helper;
	}

	public void setHelperPosition(LeftTopDimension helperPosition) {
		this.helperPosition = helperPosition;
	}

	public void setDraggableOffset(LeftTopDimension draggableOffset) {
		this.draggableOffset = draggableOffset;
	}
	
	public Element getDroppable() {
		return droppable;
	}
	
	public void setDroppable(Element droppable) {
		this.droppable = droppable;
	}

}
