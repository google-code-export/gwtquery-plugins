package gwtquery.plugins.droppable.client.events;

import gwtquery.plugins.draggable.client.DraggableHandler;
import gwtquery.plugins.draggable.client.DraggableHandler.LeftTopDimension;

import com.google.gwt.dom.client.Element;

public class DragDropInfo {
	
	private Element draggable;
	private Element helper;
	private LeftTopDimension helperPosition;
	private LeftTopDimension draggableOffset;
	private Element droppable;
	
	public DragDropInfo(Element draggable, Element droppable) {
		this.draggable = draggable;
		this.droppable = droppable;
		init();
	}
	
	private void init(){
		DraggableHandler handler = DraggableHandler.getInstance(draggable);
		helper = handler.getHelper().get(0);
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
