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
package gwtquery.plugins.draggable.client.impl;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQUtils;
import com.google.gwt.query.client.GQuery;

import gwtquery.plugins.draggable.client.DraggableHandler;
import gwtquery.plugins.draggable.client.DraggableHandler.LeftTopDimension;

/**
 * Specific code for IE8
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class DraggableHandlerImplIE8 extends DraggableHandlerImplIE {

  @Override
  public LeftTopDimension calculateRelativeHelperOffset(Element element,
      DraggableHandler draggableHandler) {
    // With IE8 we have to remove borderTop and borderLeft of the offsetParent (included in offsetLeft and offsetTop of the element)
    LeftTopDimension relativeHelperOffset = super.calculateRelativeHelperOffset(element, draggableHandler);
    Element offsetParent = GQuery.$(element).offsetParent().get(0);
    
    int offsetParentBorderLeft = (int) GQUtils.cur(offsetParent, "borderLeftWidth", true);
    int offsetParentBorderTop = (int) GQUtils.cur(offsetParent, "borderTopWidth", true);
    
    return new LeftTopDimension(relativeHelperOffset.getLeft() - offsetParentBorderLeft, relativeHelperOffset.getTop() - offsetParentBorderTop);
  }
  
}

