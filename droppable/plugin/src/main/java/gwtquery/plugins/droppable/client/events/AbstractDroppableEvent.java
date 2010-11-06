/*
 * Copyright 2010 The gwtquery plugins team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package gwtquery.plugins.droppable.client.events;


import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import gwtquery.plugins.draggable.client.gwt.DraggableWidget;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

/**
 * Abstract class for all droppable event
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 *
 * @param <H>
 */
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
