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

import gwtquery.plugins.commonui.client.GQueryUi.Dimension;
import gwtquery.plugins.draggable.client.DraggableHandler.LeftTopDimension;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQUtils;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.GQuery.Offset;

/**
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class DraggableHandlerImpl {

  public boolean resetParentOffsetPosition(GQuery helperOffsetParent) {
    return helperOffsetParent.get(0) == GQuery.body;
  }

  public int[] calculateContainment(Offset containerOffset,
      Element containerElement, LeftTopDimension helperMargin,
      Dimension helperDimension, boolean overflow) {
    return new int[] {
        containerOffset.left
            + (int) GQUtils.cur(containerElement, "borderLeftWidth", true)
            + (int) GQUtils.cur(containerElement, "paddingLeft", true)
            - helperMargin.getLeft(),
        containerOffset.top
            + (int) GQUtils.cur(containerElement, "borderTopWidth", true)
            + (int) GQUtils.cur(containerElement, "paddingTop", true)
            - helperMargin.getTop(),
        containerOffset.left
            + (overflow ? Math.max(containerElement.getScrollWidth(),
                containerElement.getOffsetWidth()) : containerElement
                .getOffsetWidth())
            - (int) GQUtils.cur(containerElement, "borderLeftWidth", true)
            - (int) GQUtils.cur(containerElement, "paddingRight", true)
            - helperDimension.getWidth() - helperMargin.getLeft(),
        containerOffset.top
            + (overflow ? Math.max(containerElement.getScrollHeight(),
                containerElement.getOffsetHeight()) : containerElement
                .getOffsetHeight())
            - (int) GQUtils.cur(containerElement, "borderTopWidth", true)
            - (int) GQUtils.cur(containerElement, "paddingBottom", true)
            - helperDimension.getHeight() - helperMargin.getTop() };
  }

	public void removeHelper(GQuery helper, HelperType helperType) {		 
		helper.remove();		
	}

}

