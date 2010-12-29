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
import com.google.gwt.user.client.Event;

import java.util.Collection;

public class DraggableDroppableManager {

  private static DraggableDroppableManager INSTANCE = GWT
      .create(DraggableDroppableManager.class);

  public static DraggableDroppableManager getInstance() {
    return INSTANCE;
  }

  public void drag(Element draggable, DraggableOptions options, Event e) {
  };

  public boolean drop(Element draggable, DraggableOptions options, Event e) {
    return false;
  };

  public Element getCurrentDraggable() {
    return null;
  };

  public Collection<Element> getDroppablesByScope(String scope) {
    return null;
  }

  public boolean isHandleDroppable() {
    return false;
  }

  public void prepareOffset(Element draggable, DraggableOptions options, Event e) {
  }

  public void setCurrentDraggable(Element draggable) {
  }

  public void setDroppable(Element droppable, String scope) {
  }

}
