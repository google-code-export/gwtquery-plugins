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
import static com.google.gwt.query.client.GQuery.body;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQUtils;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.GQuery.Offset;
import com.google.gwt.query.client.plugins.Effects;
import com.google.gwt.query.client.plugins.PropertiesAnimation.Easing;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;

import gwtquery.plugins.commonui.client.GQueryUi;
import gwtquery.plugins.commonui.client.GQueryUi.Dimension;
import gwtquery.plugins.draggable.client.Draggable.CssClassNames;
import gwtquery.plugins.draggable.client.DraggableOptions.AxisOption;
import gwtquery.plugins.draggable.client.DraggableOptions.CursorAt;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.draggable.client.impl.DraggableHandlerImpl;

public class DraggableHandler {

  /**
   * A POJO used to store the top/left. TODO When issue 49 (GQuery) will be
   * resolved, use Offset class instead
   */
  public static class LeftTopDimension {
    private int left;
    private int top;

    public LeftTopDimension(int left, int top) {
      this.left = left;
      this.top = top;
    }

    public int getLeft() {
      return left;
    }

    public int getTop() {
      return top;
    }

    public String toString() {
      return "Top:" + top + "--Left:" + left;
    }
  }

  private DraggableHandlerImpl impl = GWT.create(DraggableHandlerImpl.class);

  private LeftTopDimension margin;

  private LeftTopDimension offset;
  private LeftTopDimension absPosition;
  // from where the click happened relative to the draggable element
  private LeftTopDimension offsetClick;
  private LeftTopDimension parentOffset;
  private LeftTopDimension relativeOffset;
  private int originalEventPageX;
  private int originalEventPageY;
  private LeftTopDimension position;
  private LeftTopDimension originalPosition;

  // info from helper
  private String helperCssPosition;
  private GQuery helperScrollParent;
  private GQuery helperOffsetParent;
  private int[] containment;
  private GQuery helper;
  private DraggableOptions options;
  private Dimension helperDimension;
  private boolean cancelHelperRemoval = false;

  // can be instantiate only by Draggable plugin
  DraggableHandler(DraggableOptions options) {
    this.options = options;
  }

  /**
   * convert a relative position to a absolute position and vice versa.
   * 
   * @param absolute
   *          if true the position is convert to an absolute position, if false
   *          it is convert in a relative position
   * @param aPosition
   *          position to convert
   * @return
   */
  public LeftTopDimension convertPositionTo(boolean absolute,
      LeftTopDimension aPosition) {
    int mod = absolute ? 1 : -1;
    GQuery scroll = getScrollParent();
    boolean scrollIsRootNode = isRootNode(scroll.get(0));

    int top = aPosition.top
        + relativeOffset.top
        * mod
        + parentOffset.top
        * mod
        - ("fixed".equals(helperCssPosition) ? -helperScrollParent.scrollTop()
            : scrollIsRootNode ? 0 : scroll.scrollTop()) * mod;

    int left = aPosition.left
        + relativeOffset.left
        * mod
        + parentOffset.left
        * mod
        - ("fixed".equals(helperCssPosition) ? -helperScrollParent.scrollLeft()
            : scrollIsRootNode ? 0 : scroll.scrollLeft()) * mod;

    return new LeftTopDimension(left, top);

  }

  public LeftTopDimension getAbsPosition() {
    return absPosition;
  }

  public int[] getContainment() {
    return containment;
  }

  public GQuery getHelper() {
    return helper;
  }

  public String getHelperCssPosition() {
    return helperCssPosition;
  }

  public Dimension getHelperDimension() {
    return helperDimension;
  }

  public GQuery getHelperOffsetParent() {
    return helperOffsetParent;
  }

  public GQuery getHelperScrollParent() {
    return helperScrollParent;
  }

  public LeftTopDimension getMargin() {
    return margin;
  }

  public LeftTopDimension getOffset() {
    return offset;
  }

  public LeftTopDimension getOffsetClick() {
    return offsetClick;
  }

  public DraggableOptions getOptions() {
    return options;
  }

  public int getOriginalEventPageX() {
    return originalEventPageX;
  }

  public int getOriginalEventPageY() {
    return originalEventPageY;
  }

  public LeftTopDimension getOriginalPosition() {
    return originalPosition;
  }

  public LeftTopDimension getParentOffset() {
    return parentOffset;
  }

  public LeftTopDimension getPosition() {
    return position;
  }

  public LeftTopDimension getRelativeOffset() {
    return relativeOffset;
  }

  public void initialize(Element element, Event e) {

    helperCssPosition = helper.css("position");
    helperScrollParent = helper.as(GQueryUi.GQueryUi).scrollParent();
    helperOffsetParent = helper.offsetParent();

    if ("html".equalsIgnoreCase(helperOffsetParent.get(0).getTagName())) {
      helperOffsetParent = $(body);
    }

    setMarginCache(element);

    absPosition = new LeftTopDimension(element.getAbsoluteLeft(), element
        .getAbsoluteTop());

    offset = new LeftTopDimension(absPosition.getLeft() - margin.getLeft(),
        absPosition.getTop() - margin.getTop());

    offsetClick = new LeftTopDimension(GQueryUi.pageX(e) - offset.left,
        GQueryUi.pageY(e) - offset.top);

    parentOffset = calculateParentOffset(element);
    relativeOffset = calculateRelativeHelperOffset(element);

    originalEventPageX = GQueryUi.pageX(e);
    originalEventPageY = GQueryUi.pageY(e);

    position = generatePosition(e, true);
    originalPosition = new LeftTopDimension(position.left, position.top);

    if (options.getCursorAt() != null) {
      adjustOffsetFromHelper(options.getCursorAt());
    }
    calculateContainment();

  }

  public boolean isRootNode(Element e) {
    return "html".equalsIgnoreCase(e.getTagName()) || e == body;
  }

  /**
   * 
   * @param firstTime
   *          if true, the helper has to be positionned without take care to the
   *          axis options
   */
  public void moveHelper(boolean firstTime) {
    AxisOption axis = options.getAxis();
    if (AxisOption.NONE == axis || AxisOption.X_AXIS == axis || firstTime) {
      helper.get(0).getStyle().setLeft(position.getLeft(), Unit.PX);
    }
    if (AxisOption.NONE == axis || AxisOption.Y_AXIS == axis || firstTime) {
      helper.get(0).getStyle().setTop(position.getTop(), Unit.PX);
    }
  }

  public void regeneratePositions(Event e) {
    position = generatePosition(e, false);
    absPosition = convertPositionTo(true, position);

  }

  public void revertToOriginalPosition(Function function) {
    Properties oldPosition = Properties.create("{top:'"+originalPosition.top+"px',left:'"+originalPosition.left+"px'}");
    helper.as(Effects.Effects).animate(oldPosition, options.getRevertDuration(), Easing.LINEAR, function);
 
    
  }

  public void setHelperDimension(Dimension helperDimension) {
    this.helperDimension = helperDimension;
  }

  public void setMarginCache(Element element) {
    int marginLeft = (int) GQUtils.cur(element, "marginLeft", true);
    int marginTop = (int) GQUtils.cur(element, "marginTop", true);

    margin = new LeftTopDimension(marginLeft, marginTop);

  }

  public void setOptions(DraggableOptions options) {
    this.options = options;

  }

  public void setPosition(LeftTopDimension leftTopDimension) {
    position = leftTopDimension;

  }

  void cacheHelperSize() {
    if (helper != null) {
      setHelperDimension(new Dimension(helper.get(0)));
    }

  }

  void clear(Element draggable) {

    helper.removeClass(CssClassNames.UI_DRAGGABLE_DRAGGING);
    if (helper.get(0) != draggable && !cancelHelperRemoval) {
      impl.removeHelper(helper, options.getHelperType());    	
    }
    helper = null;
    cancelHelperRemoval = false;

  }

  void createHelper(Element draggable, Event e) {
    helper = options.getHelperType().createHelper(draggable,
        options.getHelper());
    if (!isHelperAttached()) {
      if ("parent".equals(options.getAppendTo())) {
        helper.appendTo(draggable.getParentNode());
      } else {
        helper.appendTo(options.getAppendTo());
      }
    }

    if (options.getHelperType() != HelperType.ORIGINAL
        && !helper.css("position").matches("(fixed|absolute)")) {
      helper.css("position", Position.ABSOLUTE.getCssName());     
    }
    
  }

  private void adjustOffsetFromHelper(CursorAt cursorAt) {

    if (cursorAt.getLeft() != null) {
      offsetClick.left = cursorAt.getLeft().intValue() + margin.getLeft();
    }

    if (cursorAt.getRight() != null) {
      offsetClick.left = helperDimension.getWidth()
          - cursorAt.getRight().intValue() + margin.getLeft();
    }

    if (cursorAt.getTop() != null) {
      offsetClick.top = cursorAt.getTop().intValue() + margin.getTop();
    }

    if (cursorAt.getBottom() != null) {
      offsetClick.top = helperDimension.getHeight()
          - cursorAt.getBottom().intValue() + margin.getTop();
    }
  }

  private void calculateContainment() {
    String containmentAsString = options.getContainment();
    int[] containmentAsArray = options.getContainmentAsArray();
    
    if (containmentAsArray == null && containmentAsString == null) {
      containment = null;
      return;
    }

    if (containmentAsArray != null) {
      containment = containmentAsArray;
      return;
    }

    if ( "window".equals(containmentAsString)) {
      containment = new int[] {
          0 /*- relativeOffset.left - parentOffset.left*/,
          0 /*- relativeOffset.top - parentOffset.top*/,
          Window.getClientWidth() - helperDimension.getWidth() - margin.left,
          Window.getClientHeight() - helperDimension.getHeight() - margin.top };

      return;
    }

    GQuery $containement;
    if ("parent".equals(containmentAsString)) {
      $containement = $(helper.get(0).getParentElement());
    } else if ("document".equals(containmentAsString)){
    	$containement =$("body");
    }else {
      $containement = $(containmentAsString);
    }

    Element ce = $containement.get(0);
    if (ce == null) {
      return;
    }
    
    containment = impl.calculateContainment( $containement.offset(), ce, margin, helperDimension, (!"hidden".equals($containement.css("overflow"))) );

  }

  private LeftTopDimension calculateParentOffset(Element element) {
    Offset position = helperOffsetParent.offset();

    if ("absolute".equals(helperCssPosition)
        && isOffsetParentIncludedInScrollParent()) {
      position = position.add(helperScrollParent.scrollLeft(),
          helperScrollParent.scrollTop());
    }
    
    if (impl.resetParentOffsetPosition(helperOffsetParent)) {
      position.left = 0;
      position.top = 0;
    }

    position = position.add((int) GQUtils.cur(helperOffsetParent.get(0),
        "borderLeftWidth", true), (int) GQUtils.cur(helperOffsetParent.get(0),
        "borderTopWidth", true));

    return new LeftTopDimension(position.left, position.top);

  }

  /*
   * This is a relative to absolute position minus the actual position
   * calculation - only used for relative positioned helper
   */
  private LeftTopDimension calculateRelativeHelperOffset(Element element) {
    if ("relative".equals(helperCssPosition)) {
      return impl.calculateRelativeHelperOffset(element, this);
    }
    return new LeftTopDimension(0, 0);
  }

  private LeftTopDimension generatePosition(Event e, boolean init) {
    GQuery scroll = getScrollParent();
    boolean scrollIsRootNode = isRootNode(scroll.get(0));

    int pageX = GQueryUi.pageX(e);
    int pageY = GQueryUi.pageY(e);

    if (!init) {
      if (containment != null && containment.length == 4) {
        if (GQueryUi.pageX(e) - offsetClick.left < containment[0]) {
          pageX = containment[0] + offsetClick.left;
        }
        if (GQueryUi.pageY(e) - offsetClick.top < containment[1]) {
          pageY = containment[1] + offsetClick.top;
        }
        if (GQueryUi.pageX(e) - offsetClick.left > containment[2]) {
          pageX = containment[2] + offsetClick.left;
        }
        if (GQueryUi.pageY(e) - offsetClick.top > containment[3]) {
          pageY = containment[3] + offsetClick.top;
        }
      }

      if (options.getGrid() != null) {
        int[] grid = options.getGrid();
        int roundedTop = originalEventPageY
            + Math.round((pageY - originalEventPageY) / grid[1]) * grid[1];
        int roundedLeft = originalEventPageX
            + Math.round((pageX - originalEventPageX) / grid[0]) * grid[0];

        if (containment != null && containment.length == 4) {
          boolean isOutOfContainment0 = roundedLeft - offsetClick.left < containment[0];
          boolean isOutOfContainment1 = roundedTop - offsetClick.top < containment[1];
          boolean isOutOfContainment2 = roundedLeft - offsetClick.left > containment[2];
          boolean isOutOfContainment3 = roundedTop - offsetClick.top > containment[3];

          pageY = !(isOutOfContainment1 || isOutOfContainment3) ? roundedTop
              : (!isOutOfContainment1) ? roundedTop - grid[1] : roundedTop
                  + grid[1];
          pageX = !(isOutOfContainment0 || isOutOfContainment2) ? roundedLeft
              : (!isOutOfContainment0) ? roundedLeft - grid[0] : roundedLeft
                  + grid[0];

        } else {
          pageY = roundedTop;
          pageX = roundedLeft;
        }

      }

    }

    int top = pageY
        - offsetClick.top
        - relativeOffset.top
        - parentOffset.top
        + ("fixed".equals(helperCssPosition) ? -helperScrollParent.scrollTop()
            : scrollIsRootNode ? 0 : scroll.scrollTop());

    int left = pageX
        - offsetClick.left
        - relativeOffset.left
        - parentOffset.left
        + ("fixed".equals(helperCssPosition) ? -helperScrollParent.scrollLeft()
            : scrollIsRootNode ? 0 : scroll.scrollLeft());
     return new LeftTopDimension(left, top);
  }

  private GQuery getScrollParent() {
    if ("absolute".equals(helperCssPosition)
        && !(isOffsetParentIncludedInScrollParent())) {
      return helperOffsetParent;
    } else {
      return helperScrollParent;
    }
  }

  private boolean isHelperAttached() {
    // normally this test helper.parents().filter("body").length() == 0 is
    // sufficient but they are a bug in gwtquery in filter function
    // return helper.parents().filter("body").length() == 0;
    GQuery parents = helper.parents();
    for (Element parent : parents.elements()) {
      if (parent == body) {
        return true;
      }
    }
    return false;
  }

  private boolean isOffsetParentIncludedInScrollParent() {
    assert helperOffsetParent != null && helperScrollParent != null;
    return !"html".equalsIgnoreCase(helperScrollParent.get(0).getTagName())
        && GQueryUi.contains(helperScrollParent.get(0), helperOffsetParent
            .get(0));
  }

}

