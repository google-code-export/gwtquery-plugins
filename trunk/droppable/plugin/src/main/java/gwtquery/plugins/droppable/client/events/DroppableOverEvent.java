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

/**
 * Event fired when a draggable element is over a droppable element.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class DroppableOverEvent extends
    AbstractDroppableEvent<DroppableOverEvent.DroppableOverEventHandler> {

  public interface DroppableOverEventHandler extends EventHandler {
    public void onDroppableOver(DroppableOverEvent event);
  }

  public static Type<DroppableOverEventHandler> TYPE = new Type<DroppableOverEventHandler>();

  public DroppableOverEvent() {	
  }

  public DroppableOverEvent(Element droppable, Element draggable) {
	  super(droppable, draggable);
  }

  @Override
  public Type<DroppableOverEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(DroppableOverEventHandler handler) {
    handler.onDroppableOver(this);
  }

}
