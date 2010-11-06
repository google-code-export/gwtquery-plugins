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
import com.google.gwt.query.client.GQuery.Offset;

import gwtquery.plugins.draggable.client.DraggableHandler;

/**
 * Object containing usefull information on the drag and drop operations.
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
