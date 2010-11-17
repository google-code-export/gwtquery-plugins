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

/**
 * This class is used to configure the drag operation.
 * 
 * See <a href=
 * "http://code.google.com/p/gwtquery-plugins/wiki/DraggablePluginGettingStarted"
 * >the wiki page</a> for more documentation and samples
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class DraggableOptions extends MouseOptions {

  public static enum AxisOption {
    Y_AXIS, X_AXIS, NONE;
  }

  /**
   * Object used to specify the cursorAt options.
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
     * than two argument, the bottom parameter has priority over the top
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

  /**
   * 
   * Define what the draggable do a the end of the drag operation.
   * 
   * ALWAYS : the draggable will return to its start position when dragging
   * stops.
   * 
   * NEVER : the draggable will not return to its start position when dragging
   * stops.
   * 
   * ON_VALID_DROP :revert will only occur if the draggable has been dropped
   * (useful with droppable plug-in)
   * 
   * ON_INVALID_DROP :revert will only occur if the draggable has not been
   * dropped (useful with droppable plug-in)
   */
  public static enum RevertOption {
    NEVER, ALWAYS, ON_VALID_DROP, ON_INVALID_DROP;

    public boolean doRevert(boolean dropped) {
      return this == ALWAYS || (this == ON_INVALID_DROP && !dropped)
          || (this == ON_VALID_DROP && dropped);
    }
  }

  public static enum SnapMode {
    INNER, OUTER, BOTH;
  }

  public static final String DEFAULT_SCOPE = "default";

  private String appendTo;
  private AxisOption axis;
  // private boolean connectToSortable;
  private int[] containmentAsArray;
  private String containment;
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
  private RevertOption revert;
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
  private Function onBeforeDragStart;
  private Function onDragStart;
  private Function onDragStop;
  private Function onDrag;
  private GQuery $containment;

  public String getAppendTo() {
    return appendTo;
  }

  public AxisOption getAxis() {
    if (axis == null) {
      return AxisOption.NONE;
    }
    return axis;
  }

  public String getContainment() {
    return containment;
  }

  public int[] getContainmentAsArray() {
    return containmentAsArray;
  }

  public GQuery getContainmentAsGQuery() {
    return $containment;
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

  public Function getOnBeforeDragStart() {
    return onBeforeDragStart;
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

  public RevertOption getRevert() {
    return revert;
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


  public boolean isDisabled() {
    return disabled;
  }

  public boolean isIframeFix() {
    return iframeFix;
  }

  public boolean isRefreshPositions() {
    return refreshPositions;
  }

  public boolean isScroll() {
    return scroll;
  }

  public boolean isSnap() {
    return snap != null;
  }


  /**
   * The element selected by the appendTo option will be used as the draggable
   * helper's container during dragging. By default, the helper is appended to
   * the same container as the draggable.
   * 
   * @param appendTo
   *          the selector defining the element where the helper will be added
   */
  public void setAppendTo(String appendTo) {
    this.appendTo = appendTo;
  }

  /**
   * Constrains dragging to either the horizontal (X_AXIS) or vertical (Y_AXIS)
   * axis.
   * 
   * @param axis
   */
  public void setAxis(AxisOption axis) {
    this.axis = axis;
  }

  /*
   * public void setConnectToSortable(boolean connectToSortable) {
   * this.connectToSortable = connectToSortable; }
   */

  /**
   * Constrains dragging to within the bounds of the specified region. The
   * region is defined by a array of integer : {left, top, right, bottom}
   * 
   * @param containment
   *          array of int defining the region.
   * 
   *          e.g. options.setContainment(new int[] { 300, 500, 600, 800 })
   *          Constrains the dragging in the following region : 300px from the
   *          left of the document 500px from the top of the document 600px from
   *          the right of the document 800px from the bottom of the document
   */
  public void setContainment(int[] containment) {
    this.containmentAsArray = containment;
    // mutually exclusive
    this.containment = null;
  }

  /**
   * Constrains dragging to within the bounds of the specified element (called
   * the container) defining by the selector.
   * 
   * @param containment
   *          selector defining the container element. Possible string values: a
   *          css selector 'parent' the container will be the parent element of
   *          the element, 'document' the container will the document, 'window'
   *          the container will be the browser area
   * @see sample at
   *      http://gwtquery-plugins.googlecode.com/svn/trunk/draggable/demo
   *      /DraggableSample2/DraggableSample2.html
   * 
   */
  public void setContainment(String containment) {
    this.containment = containment;
    // mutually exclusive
    this.containmentAsArray = null;
  }

  /**
   * Constrains dragging to within the bounds of the specified element (called
   * the container) defining by the selector.
   * 
   * @param containment
   *          selector defining the container element. Possible string values: a
   *          css selector 'parent' the container will be the parent element of
   *          the element, 'document' the container will the document, 'window'
   *          the container will be the browser area
   * @see sample at
   *      http://gwtquery-plugins.googlecode.com/svn/trunk/draggable/demo
   *      /DraggableSample2/DraggableSample2.html
   * 
   */
  public void setContainment(GQuery containment) {
    this.$containment = containment;
    // mutually exclusive
    this.containmentAsArray = null;
    this.containment = null;
  }

  /**
   * Specify the css cursor to use during the drag operation.
   * 
   * @param cursor
   */
  public void setCursor(Cursor cursor) {
    this.cursor = cursor;
  }

  /**
   * Moves the dragging helper so the cursor always appears to drag from the
   * same position.
   * 
   * @param cursorAt
   */
  public void setCursorAt(CursorAt cursorAt) {
    this.cursorAt = cursorAt;
  }

  /**
   * Disables (true) or enables (false) the drag operation.
   * 
   * @param disabled
   */
  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  /**
   * Snaps the dragging helper to a grid defining by the <code>grid</code>
   * parameter.
   * 
   * @param grid
   *          array of int defining the dimension of the cell of the snapped
   *          grid.
   * 
   *          e.g. options.setGrid(new int[]{40, 60}); The draggable will be
   *          moved by 40 pixel horizontally and by 60px vertically
   * 
   */
  public void setGrid(int[] grid) {
    this.grid = grid;
  }

  /**
   * If specified, restricts drag start when the user clicks on the specified
   * element(s).
   * 
   * @param selector
   *          css selector defining element(s) allowing starting of the drag
   */
  public void setHandle(String selector) {
    this.handle = selector;
  }

  /**
   * Set an element as helper. This element will be used as dragging display
   * during the drag operation instead of the original draggable.
   * 
   * If the element is not attached to the DOM, it will be attached at the start
   * of the drag and removed after.
   * 
   * @param helper
   *          element used as helper during the drag
   */
  public void setHelper(Element helper) {
    this.helper = $(helper);
    this.helperType = HelperType.ELEMENT;
  }

  /**
   * Set the first element of the GQuery object as helper. This element will be
   * used as dragging display during the drag operation instead of the original
   * draggable.
   * 
   * If the element is not attached to the DOM, it will be attached at the start
   * of the drag and removed after.
   * 
   * @param helper
   *          GQuery object containing the element that will be used as helper
   *          during the drag
   */
  public void setHelper(GQuery helper) {
    this.helper = helper;
    this.helperType = HelperType.ELEMENT;
  }

  /**
   * Set the helper type.
   * 
   * ORIGINAL : the original draggable will be used as dragging display
   * 
   * CLONE : a clone of the original draggable will be used as dragging display
   * 
   * ELEMENT : an other element will be used. If you set this helper type, you
   * have to call <code>setHelper(Element helper)</code> or
   * <code>setHelper(GQuery helper)</code> to specify which element to use.
   * Please note that if you call directly these methods, the helper type will
   * be set automatically to ELEMENT
   * 
   * CLONE and ELEMENT options don't move the original widget at the end of the
   * drag operation. They are only useful with droppable plugin
   * 
   * @param helperType
   *          the helper type
   */
  public void setHelper(HelperType helperType) {
    this.helperType = helperType;
  }

  /**
   * Specify a css selector defining the element that will be used as helper.
   * This element will be used as dragging display during the drag operation
   * instead of the original draggable.
   * 
   * If the element is not attached to the DOM, it will be attached at the start
   * of the drag and removed after.
   * 
   * @param helper
   *          css selector defining the element that will be used as helper
   *          during the drag
   */
  public void setHelper(String selector) {
    this.helper = $(helper);
    this.helperType = HelperType.ELEMENT;
  }

  /**
   * Give a callback function called before the initialization of the drag
   * operation.
   * 
   * @param onDragStart
   *          function called before the initialization of the drag operation.
   *          The draggable plugin will invoke the f(Element e) method on this
   *          function object with e being the draggable (not the helper)
   */
  public void setOnBeforeDragStart(Function onBeforeDragStart) {
    this.onBeforeDragStart = onBeforeDragStart;
  }

  /**
   * Give a callback function called when the drag operation is dragging.
   * 
   * @param onDragStart
   *          function called when the drag operation is dragging. The draggable
   *          plugin will invoke the f(Element e) method on this function object
   *          with e being the draggable (not the helper)
   */
  public void setOnDrag(Function onDrag) {
    this.onDrag = onDrag;
  }

  /**
   * Give a callback function called when the drag operation starts.
   * 
   * @param onDragStart
   *          function called when the drag operation starts. The draggable
   *          plugin will invoke the f(Element e) method on this function object
   *          with e being the draggable (not the helper)
   */
  public void setOnDragStart(Function onDragStart) {
    this.onDragStart = onDragStart;
  }

  /**
   * Give a callback function called when the drag operation ends.
   * 
   * @param onDragStart
   *          function called when the drag operation ends. The draggable plugin
   *          will invoke the f(Element e) method on this function object with e
   *          being the draggable (not the helper)
   */
  public void setOnDragStop(Function onDragStop) {
    this.onDragStop = onDragStop;
  }

  /**
   * Set the opacity of the helper during the drag.
   * 
   * @param opacity
   *          a float between 0 and 1
   */
  public void setOpacity(Float opacity) {
    this.opacity = opacity;
  }

  /**
   * Set the revert options
   * 
   * ALWAYS : the element will return to its start position when dragging stops.
   * 
   * NEVER : the element will not return to its start position when dragging
   * stops.
   * 
   * ON_VALID_DROP :revert will only occur if the draggable has been dropped
   * (useful with droppable plug-in)
   * 
   * ON_INVALID_DROP :revert will only occur if the draggable has not been
   * dropped (useful with droppable plug-in)
   * 
   * @param revert
   */
  public void setRevert(RevertOption revert) {
    this.revert = revert;
  }

  /**
   * The duration of the revert animation, in milliseconds.
   * 
   * @param revertDuration
   */
  public void setRevertDuration(int revertDuration) {
    this.revertDuration = revertDuration;
  }

  /**
   * Used to group sets of draggable and droppable items, in addition to
   * droppable's accept option. A draggable with the same scope value as a
   * droppable will be accepted by the droppable.
   * 
   * @param scope
   */
  public void setScope(String scope) {
    this.scope = scope;
  }

  /**
   * Define if the container scroll while dragging
   * 
   * @param scroll
   */
  public void setScroll(boolean scroll) {
    this.scroll = scroll;
  }

  /**
   * Distance in pixels from the edge of the viewport after which the viewport
   * should scroll. Distance is relative to pointer, not to the draggable.
   * 
   * @param scrollSensitivity
   */
  public void setScrollSensitivity(int scrollSensitivity) {
    this.scrollSensitivity = scrollSensitivity;
  }

  /**
   * The speed at which the window should scroll once the mouse pointer gets
   * within the scrollSensitivity distance.
   * 
   * @param scrollSpeed
   */
  public void setScrollSpeed(int scrollSpeed) {
    this.scrollSpeed = scrollSpeed;
  }

  /**
   * Define if the draggable will snap to the edges of the other draggable
   * elements when it is near an edge of these elements.
   * 
   * @param snap
   */
  public void setSnap(boolean snap) {
    if (snap) {
      this.snap = "." + CssClassNames.GWT_DRAGGABLE;
    } else {
      this.snap = null;
    }
  }

  /**
   * Define snap elements by a GQuery object. The draggable will snap to the
   * edges of the selected elements when near an edge of the element.
   * 
   * @param $
   */
  public void setSnap(GQuery $) {
    $snap = $;
    snap = null;

  }

  /**
   * Define snap element as being the selected elements that match the selector.
   * The draggable will snap to the edges of the selected elements when near an
   * edge of the element.
   * 
   * @param snapSelector
   */
  public void setSnap(String snapSelector) {
    this.snap = snapSelector;
    $snap = null;
  }

  /**
   * Determines which edges of snap elements the draggable will snap to.
   * Possible values: INNER, OUTER, BOTH
   * 
   * @param snapMode
   */
  public void setSnapMode(SnapMode snapMode) {
    this.snapMode = snapMode;
  }

  /**
   * The distance in pixels from the snap element edges at which snapping should
   * occur.
   * 
   * @param snapTolerance
   */
  public void setSnapTolerance(int snapTolerance) {
    this.snapTolerance = snapTolerance;
  }

  /**
   * Controls the z-Index of the selected elements, always brings to front the
   * dragged item. Very useful in things like window managers.
   * 
   * @param stack
   */
  public void setStack(GQuery stack) {
    this.stack = stack;
  }

  /**
   * Controls the z-Index of the selected elements that match the selector,
   * always brings to front the dragged item. Very useful in things like window
   * managers.
   * 
   * @param stack
   */
  public void setStack(String selector) {
    this.stack = $(selector);
  }

  /**
   * z-index for the helper while being dragged.
   * 
   * @param zIndex
   */
  public void setZIndex(Integer zIndex) {
    this.zIndex = zIndex;
  }

  @Override
  protected void initDefault() {
    super.initDefault();
    appendTo = "parent";
    axis = AxisOption.NONE;
    iframeFix = false;
    refreshPositions = false;
    revert = RevertOption.NEVER;
    cursor = Cursor.AUTO;
    helperType = HelperType.ORIGINAL;
    scope = DEFAULT_SCOPE;
    revertDuration = 500;
    scroll = true;
    scrollSensitivity = 20;
    scrollSpeed = 20;
    snapMode = SnapMode.BOTH;
    snapTolerance = 20;
  }

}
