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
package gwtquery.plugins.draggable.client.events;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

/**
 * Event fired when the drag operation stops.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class BeforeDragStartEvent extends
    GwtEvent<BeforeDragStartEvent.BeforeDragStartEventHandler> {

  public interface BeforeDragStartEventHandler extends EventHandler {
    public void onBeforeDragStart(BeforeDragStartEvent event);
  }

  public static Type<BeforeDragStartEventHandler> TYPE = new Type<BeforeDragStartEventHandler>();
  private Element draggable;

  public BeforeDragStartEvent(Element draggable) {
    this.draggable = draggable;
  }

  @Override
  public Type<BeforeDragStartEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(BeforeDragStartEventHandler handler) {
    handler.onBeforeDragStart(this);
  }
  
  public DraggableWidget<?> getDraggableWidget(){
    if (getDraggable() != null){
      return DraggableWidget.get(getDraggable());
    }
    return null;
  }
  
   public Element getDraggable() {
      return draggable;
    }

}
