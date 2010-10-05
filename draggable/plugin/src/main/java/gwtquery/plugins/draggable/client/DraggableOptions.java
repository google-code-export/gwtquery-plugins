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

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;

import gwtquery.plugins.commonui.client.MouseOptions;
import gwtquery.plugins.draggable.client.Draggable.CssClassNames;

public class DraggableOptions extends MouseOptions {

  public static enum AxisOption {
    Y_AXIS, X_AXIS, NONE;
  }

  /**
   * Object use to specify the cursorAt options.
   * 
   * @author Julien Dramaix (julien.dramaix@gmail.com)
   * 
   */
  public static class CursorAt {

    private Integer left;
    private Integer right;
    private Integer top;
    private Integer bottom;

    /**
     * Specify coordinates by giving one or two parameters. If you define more
     * than twa argument, the bottom parameter has priority over the top
     * parameter and the right parameter has priority over left parameter
     * 
     * @param top
     * @param left
     * @param bottom
     * @param right
     */
    public CursorAt(Integer top, Integer left, Integer bottom, Integer right) {
      if (bottom != null) {
        this.bottom = bottom;
      } else if (top != null) {
        this.top = top;
      }

      if (right != null) {
        this.right = right;
      } else if (left != null) {
        this.left = left;
      }
    }

    public Integer getBottom() {
      return bottom;
    }

    public Integer getLeft() {
      return left;
    }

    public Integer getRight() {
      return right;
    }

    public Integer getTop() {
      return top;
    }

  }

  public static enum HelperType {
    ORIGINAL {
      @Override
      public GQuery createHelper(Element original, GQuery helperFromOptions) {
        return $(original);
      }
    },
    CLONE {
      @Override
      public GQuery createHelper(Element original, GQuery helperFromOptions) {
        return $(original).clone();
      }
    },
    ELEMENT {
      @Override
      public GQuery createHelper(Element original, GQuery helperFromOptions) {
        return helperFromOptions;
      }
    };

    public abstract GQuery createHelper(Element original,
        GQuery helperFromOptions);
  }

  public static enum SnapMode {
    INNER, OUTER, BOTH;
  }

  public static final DraggableContainment PARENT = new DraggableContainment(
      "parent");

  public static final String DEFAULT_SCOPE = "default";

  private boolean addClasses;
  private String appendTo;
  private AxisOption axis;
  // private boolean connectToSortable;
  private DraggableContainment containment;
  private Cursor cursor;
  private CursorAt cursorAt;
  private boolean disabled;
  private int[] grid;
  private String handle;
  private GQuery helper;
  private HelperType helperType;
  private boolean iframeFix;
  private Float opacity;
  private boolean refreshPositions;
  private boolean revert;
  private int revertDuration;
  private String scope;
  private boolean scroll;
  private int scrollSensitivity;
  private int scrollSpeed;
  private String snap;
  private GQuery $snap;
  private SnapMode snapMode;
  private int snapTolerance;
  private GQuery stack;
  private Integer zIndex;

  /**
   * This callback function is called at the starting of the drag operation
   */
  private Function onDragStart;

  /**
   * This callback function is called at the end of the drag operation.
   */
  private Function onDragStop;

  /**
   * This callback function is called during the drag operation.
   */
  private Function onDrag;

  public String getAppendTo() {
    return appendTo;
  }

  public AxisOption getAxis() {
    if (axis == null) {
      return AxisOption.NONE;
    }
    return axis;
  }

  public DraggableContainment getContainment() {
    return containment;
  }

  public Cursor getCursor() {
    return cursor;
  }

  public CursorAt getCursorAt() {
    return cursorAt;
  }

  public int[] getGrid() {
    return grid;
  }

  public String getHandle() {
    return handle;
  }

  public GQuery getHelper() {
    return helper;
  }

  public HelperType getHelperType() {
    return helperType;
  }

  public Function getOnDrag() {
    return onDrag;
  }

  public Function getOnDragStart() {
    return onDragStart;
  }

  public Function getOnDragStop() {
    return onDragStop;
  }

  public Float getOpacity() {
    return opacity;
  }

  public int getRevertDuration() {
    return revertDuration;
  }

  public String getScope() {
    return scope;
  }

  public int getScrollSensitivity() {
    return scrollSensitivity;
  }

  public int getScrollSpeed() {
    return scrollSpeed;
  }

  public String getSnap() {
    return snap;
  }

  public GQuery getSnap_$() {
    return $snap;
  }

  public SnapMode getSnapMode() {
    return snapMode;
  }

  public int getSnapTolerance() {
    return snapTolerance;
  }

  public GQuery getStack() {
    return stack;
  }

  /*
   * public boolean isConnectToSortable() { return connectToSortable; }
   */

  public Integer getZIndex() {
    return zIndex;
  }

  public boolean isAddClasses() {
    return addClasses;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public boolean isIframeFix() {
    return iframeFix;
  }

  public boolean isRefreshPositions() {
    return refreshPositions;
  }

  public boolean isRevert() {
    return revert;
  }

  public boolean isScroll() {
    return scroll;
  }

  public boolean isSnap() {
    return snap != null;
  }

  public void setAddClasses(boolean addClasses) {
    this.addClasses = addClasses;
  }

  public void setAppendTo(String appendTo) {
    this.appendTo = appendTo;
  }

  public void setAxis(AxisOption axis) {
    this.axis = axis;
  }

  /*
   * public void setConnectToSortable(boolean connectToSortable) {
   * this.connectToSortable = connectToSortable; }
   */

  public void setContainment(DraggableContainment containment) {
    this.containment = containment;
  }

  public void setCursor(Cursor cursor) {
    this.cursor = cursor;
  }

  public void setCursorAt(CursorAt cursorAt) {
    this.cursorAt = cursorAt;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public void setGrid(int[] grid) {
    this.grid = grid;
  }

  public void setHandle(String selector) {
    this.handle = selector;
  }

  public void setHelper(Element helper) {
    this.helper = $(helper);
    this.helperType = HelperType.ELEMENT;
  }

  public void setHelper(GQuery helper) {
    this.helper = helper;
    this.helperType = HelperType.ELEMENT;
  }

  public void setHelper(HelperType helperType) {
    this.helperType = helperType;
  }

  public void setHelper(String selector) {
    this.helper = $(helper);
    this.helperType = HelperType.ELEMENT;
  }

  public void setIframeFix(boolean iframeFix) {
    this.iframeFix = iframeFix;
  }

  public void setOnDrag(Function onDrag) {
    this.onDrag = onDrag;
  }

  public void setOnDragStart(Function onDragStart) {
    this.onDragStart = onDragStart;
  }

  public void setOnDragStop(Function onDragStop) {
    this.onDragStop = onDragStop;
  }

  public void setOpacity(float opacity) {
    this.opacity = opacity;
  }

  public void setRefreshPositions(boolean refreshPositions) {
    this.refreshPositions = refreshPositions;
  }

  public void setRevert(boolean revert) {
    this.revert = revert;
  }

  public void setRevertDuration(int revertDuration) {
    this.revertDuration = revertDuration;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public void setScroll(boolean scroll) {
    this.scroll = scroll;
  }

  public void setScrollSensitivity(int scrollSensitivity) {
    this.scrollSensitivity = scrollSensitivity;
  }

  public void setScrollSpeed(int scrollSpeed) {
    this.scrollSpeed = scrollSpeed;
  }

  public void setSnap(boolean snap) {
    if (snap) {
      this.snap = "." + CssClassNames.UI_DRAGGABLE;
    } else {
      this.snap = null;
    }
  }

  public void setSnap(GQuery $) {
    $snap = $;
    snap = null;

  }

  public void setSnap(String snapSelector) {
    this.snap = snapSelector;
    $snap = null;
  }

  public void setSnapMode(SnapMode snapMode) {
    this.snapMode = snapMode;
  }

  public void setSnapTolerance(int snapTolerance) {
    this.snapTolerance = snapTolerance;
  }

  public void setStack(GQuery stack) {
    this.stack = stack;
  }

  public void setStack(String selector) {
    this.stack = $(selector);
  }

  public void setZIndex(Integer zIndex) {
    this.zIndex = zIndex;
  }

  @Override
  protected void initDefault() {
    super.initDefault();
    addClasses = true;
    appendTo = "parent";
    axis = AxisOption.NONE;
    // connectToSortable = false;
    containment = null;
    cursorAt = null;
    grid = null;
    handle = null;
    iframeFix = false;
    opacity = null;
    refreshPositions = false;
    revert = false;
    stack = null;
    zIndex = null;
    cursor = Cursor.AUTO;
    helper = null;
    helperType = HelperType.ORIGINAL;
    scope = DEFAULT_SCOPE;
    revertDuration = 500;
    scroll = true;
    scrollSensitivity = 20;
    scrollSpeed = 20;
    snap = null;
    $snap = null;
    snapMode = SnapMode.BOTH;
    snapTolerance = 20;
  }

}
