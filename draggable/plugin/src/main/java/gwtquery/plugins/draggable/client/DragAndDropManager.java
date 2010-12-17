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
package gwtquery.plugins.draggable.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.plugins.GQueryUi.Event;

import java.util.Collection;

/**
 * The goal of this class is to manage the interactions between draggable and droppable objects.
 * This implementation specifies the interface needed by this kind of manager but do nothing.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 *
 */
public class DragAndDropManager {

  private static DragAndDropManager INSTANCE = GWT
      .create(DragAndDropManager.class);

  /**
   * return the instance of the manager
   * @return
   */
  public static DragAndDropManager getInstance() {
    return INSTANCE;
  }

  /**
   * Method called when the draggable is being dragged
   * @param draggable
   * @param e
   */
  public void drag(Element draggable, Event e) {
  }

  /**
   * Method called when the draggable was dropped
   * @param draggable
   * @param e
   * @return
   */
  public boolean drop(Element draggable, Event e) {
    return false;
  }

  /**
   * 
   * @return the current draggable element or null if no drag operation in progress
   */
  public Element getCurrentDraggable() {
    return null;
  }

  /**
   * Return the list of droppable elements with the scope <code>scope</code>
   * @param scope
   * @return
   */
  public Collection<Element> getDroppablesByScope(String scope) {
    return null;
  }

  /**
   * 
   * @return
   */
  public boolean isHandleDroppable() {
    return false;
  }

  public void initialize(Element draggable, Event e) {
  }

  /**
   * Set the current draggeble element
   * @param draggable
   */
  public void setCurrentDraggable(Element draggable) {
  }

  /**
   * Link a droppable with the specified scope <code>scope</code>
   * @param droppable
   * @param scope
   */
  public void addDroppable(Element droppable, String scope) {
  }

}
