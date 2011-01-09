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
import com.google.gwt.query.client.GQuery.Offset;
import com.google.gwt.query.client.plugins.GQueryUi.Dimension;

import gwtquery.plugins.draggable.client.DraggableHandler;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;

/**
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com, @jdramaix)
 * 
 */
public class DraggableHandlerImpl {

  public boolean resetParentOffsetPosition(GQuery helperOffsetParent) {
    return helperOffsetParent.get(0) == GQuery.body;
  }

  public int[] calculateContainment(DraggableHandler draggableHandler, Offset containerOffset,
      Element containerElement,  boolean overflow) {
	  
	  Offset helperMargin = draggableHandler.getMargin();
      Dimension helperDimension = draggableHandler.getHelperDimension();
      
    return new int[] {
        containerOffset.left
            + (int) GQUtils.cur(containerElement, "borderLeftWidth", true)
            + (int) GQUtils.cur(containerElement, "paddingLeft", true)
            - helperMargin.left,
        containerOffset.top
            + (int) GQUtils.cur(containerElement, "borderTopWidth", true)
            + (int) GQUtils.cur(containerElement, "paddingTop", true)
            - helperMargin.top,
        containerOffset.left
            + (overflow ? Math.max(containerElement.getScrollWidth(),
                containerElement.getOffsetWidth()) : containerElement
                .getOffsetWidth())
            - (int) GQUtils.cur(containerElement, "borderLeftWidth", true)
            - (int) GQUtils.cur(containerElement, "paddingRight", true)
            - helperDimension.getWidth() - helperMargin.left,
        containerOffset.top
            + (overflow ? Math.max(containerElement.getScrollHeight(),
                containerElement.getOffsetHeight()) : containerElement
                .getOffsetHeight())
            - (int) GQUtils.cur(containerElement, "borderTopWidth", true)
            - (int) GQUtils.cur(containerElement, "paddingBottom", true)
            - helperDimension.getHeight() - helperMargin.top };
  }

	public void removeHelper(GQuery helper, HelperType helperType) {		 
		helper.remove();		
	}
	
	public Offset calculateRelativeHelperOffset(Element element,
		      DraggableHandler draggableHandler) {
		    
		    Offset position = new Offset(element.getOffsetLeft(), element.getOffsetTop());
		    Element helperElement = draggableHandler.getHelper().get(0);
		    Offset margin = draggableHandler.getMargin();
		    
		    Offset helperCssPosition = getCssPosition(helperElement);
		    int top = position.top 
		      - helperCssPosition.top
		      - margin.top;
		    int left = position.left
		        - helperCssPosition.left
		        - margin.left;

		    return new Offset(left, top);
		    
	}
	
	public Offset getCssPosition(Element e){
	  int top = 0;
	  int left = 0;
	  if (e.getStyle().getTop() != null && e.getStyle().getTop().length()>0){
      top = (int) GQUtils.cur(e, "top", true);
    }
     //same remark
    if (e.getStyle().getLeft() != null && e.getStyle().getLeft().length()>0){
      left = (int) GQUtils.cur(e, "left", true);
    }
	  return new Offset(left, top);
	}


}


