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

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery.Offset;

import gwtquery.plugins.draggable.client.DraggableHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

/**
 * Object containing useful information on the drag operation.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class DragContext {

  private static final String VALUE_KEY = "__dragAndDropCellAssociatedValue";

  private Element draggable;
  private Element helper;
  private Offset helperPosition;

  /**
   * Constructor
   * 
   * @param draggable
   *          the draggable element
   */
  public DragContext(Element draggable) {
    this.draggable = draggable;
    init();
  }

  /**
   * 
   * @return the draggable DOM element
   */
  public Element getDraggable() {
    return draggable;
  }

  /**
   * This method allows getting the data object linked to the draggable element
   * (a cell) in the context of CellWidget.It return the data object being
   * rendered by the dragging cell. Return null if we are not in the context of
   * an drag and drop cell widget.
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
   * This method return the widget associated to the draggable DOM element if it
   * exist. It returns null otherwise.
   * 
   */
  public DraggableWidget<?> getDraggableWidget() {
    if (getDraggable() != null) {
      return DraggableWidget.get(getDraggable());
    }
    return null;
  }

  /**
   * 
   * @return the DOM element used for dragging display
   */
  public Element getHelper() {
    return helper;
  }

  /**
   * 
   * @return the {@link Offset} of the helper element.
   */
  public Offset getHelperPosition() {
    return helperPosition;
  }

  private void init() {
    DraggableHandler handler = DraggableHandler.getInstance(draggable);
    if (handler.getHelper() != null) {
      helper = handler.getHelper().get(0);
    }
    helperPosition = handler.getPosition();
  }

}
