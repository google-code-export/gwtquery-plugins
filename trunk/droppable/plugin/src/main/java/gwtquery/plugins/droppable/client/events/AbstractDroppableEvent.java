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

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.droppable.client.gwt.DragAndDropCellWidgetUtils.VALUE_KEY;

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

	private DragAndDropContext dragAndDropContext;


	public AbstractDroppableEvent(Element droppable, Element draggable) {
		this.dragAndDropContext = new DragAndDropContext(draggable, droppable);
	}
	
	public AbstractDroppableEvent(DragAndDropContext context){
	  this.dragAndDropContext = context;
	}
	
	
	public DragAndDropContext getDragDropContext() {
		return dragAndDropContext;
	}

	public Element getDroppable() {
	  assert dragAndDropContext != null : "DragAndDropContext cannot be null";
			return dragAndDropContext.getDroppable();
		
	}


	
	public Element getDraggable(){
		if (dragAndDropContext != null) {
			return dragAndDropContext.getDraggable();
		}
		return null;
	}
	
	public DraggableWidget<?> getDraggableWidget(){
	  if (dragAndDropContext != null) {
      return dragAndDropContext.getDraggableWidget();
    }
    return null;
  }
	
	public DroppableWidget<?> getDroppableWidget(){
	  if (dragAndDropContext != null) {
      return dragAndDropContext.getDroppableWidget();
    }
    return null;
  }


	/**
   * Return the data value associated to the current draggable element if and
   * only if this one is coming from a {@link DraggableCell} This method returns
   * null if the current drag and drop operation doesn't concerns a
   * {@link DraggableCell}
   * 
   * @param <T>
   *          the class of the data
   * @return
   */
  @SuppressWarnings("unchecked")
  public <T> T getDraggableData() {
    return (T) $(getDraggable()).data(VALUE_KEY);
  }

  /**
   * Return the data value associated to the drop element if and only if this
   * one is coming from a {@link DroppableCell} This method returns null if the
   * current drag and drop operation doesn't concerns a {@link DroppableCell}
   * 
   * @param <T>
   *          the class of the data
   * @return
   */
  @SuppressWarnings("unchecked")
  public <T> T getDroppableData() {
    return (T) $(getDroppable()).data(VALUE_KEY);

  }
  }

