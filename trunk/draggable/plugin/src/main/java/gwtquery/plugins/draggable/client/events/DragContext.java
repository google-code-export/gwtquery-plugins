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
 * Object containing usefull information on the drag operation.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class DragContext {

  private static final String VALUE_KEY = "__dragAndDropCellAssociatedValue";

  private Element draggable;
  private Element helper;
  private Offset helperPosition;

  public DragContext(Element draggable) {
    this.draggable = draggable;
    init();
  }

  private void init() {
    DraggableHandler handler = DraggableHandler.getInstance(draggable);
    if (handler.getHelper() != null) {
      helper = handler.getHelper().get(0);
    }
    helperPosition = handler.getPosition();
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

  public DraggableWidget<?> getDraggableWidget() {
    if (getDraggable() != null) {
      return DraggableWidget.get(getDraggable());
    }
    return null;
  }

}
