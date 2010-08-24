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
package gwtquery.plugins.selectable.client.event;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event fired when an element is unselected.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class UnselectedEvent extends
    GwtEvent<UnselectedEvent.UnselectedEventHandler> {

  public interface UnselectedEventHandler extends EventHandler {
    public void onUnselected(UnselectedEvent event);
  }

  public static Type<UnselectedEventHandler> TYPE = new Type<UnselectedEventHandler>();

  private Element unselectedElement;

  public UnselectedEvent(Element e) {
    unselectedElement = e;
  }

  @Override
  public Type<UnselectedEventHandler> getAssociatedType() {
    return TYPE;
  }

  public Element getUnselectedElement() {
    return unselectedElement;
  }

  @Override
  protected void dispatch(UnselectedEventHandler handler) {
    handler.onUnselected(this);
  }

}
