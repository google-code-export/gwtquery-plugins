package gwtquery.plugins.draggable.client;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;

import gwtquery.plugins.commonui.client.MouseOptions;

public class DraggableOptions extends MouseOptions {

  public static enum HelperType {
    ORIGINAL {
      @Override
      public GQuery createHelper(Element original, Element helper) {
        return $(original);
      }
    },
    CLONE {
      @Override
      public GQuery createHelper(Element original, Element helper) {
        return $(original).clone();
      }
    },
    ELEMENT {
      @Override
      public GQuery createHelper(Element original, Element helper) {
        return $(helper);
      }
    };

    public abstract GQuery createHelper(Element original, Element helper);
  }

  
  
  public static final DraggableContainment PARENT = new DraggableContainment("parent");

  private boolean addClasses;
  private String appendTo;
  private boolean axis;
  // private boolean connectToSortable;
  private DraggableContainment containment;
  private String cursor;
  private boolean cursorAt;
  private boolean disabled;
  private boolean grid;
  private String handle;
  private Element helper;
  private HelperType helperType;
  private boolean iframeFix;
  private boolean opacity;
  private boolean refreshPositions;
  private boolean revert;
  private int revertDuration;
  private String scope;
  private boolean scroll;
  private int scrollSensitivity;
  private int scrollSpeed;
  private boolean snap;
  private String snapMode;
  private int snapTolerance;
  private boolean stack;
  private boolean zIndex;

  public String getAppendTo() {
    return appendTo;
  }

  public DraggableContainment getContainment() {
    return containment;
  }

  public String getCursor() {
    return cursor;
  }

  public String getHandle() {
    return handle;
  }

  public Element getHelper() {
    return helper;
  }

  public HelperType getHelperType() {
    return helperType;
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

  public String getSnapMode() {
    return snapMode;
  }

  public int getSnapTolerance() {
    return snapTolerance;
  }

  public boolean isAddClasses() {
    return addClasses;
  }

  public boolean isAxis() {
    return axis;
  }

  /*
   * public boolean isConnectToSortable() { return connectToSortable; }
   */

  public boolean isCursorAt() {
    return cursorAt;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public boolean isGrid() {
    return grid;
  }

  public boolean isIframeFix() {
    return iframeFix;
  }

  public boolean isOpacity() {
    return opacity;
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
    return snap;
  }

  public boolean isStack() {
    return stack;
  }

  public boolean iszIndex() {
    return zIndex;
  }

  public void setAddClasses(boolean addClasses) {
    this.addClasses = addClasses;
  }

  public void setAppendTo(String appendTo) {
    this.appendTo = appendTo;
  }

  public void setAxis(boolean axis) {
    this.axis = axis;
  }

  /*
   * public void setConnectToSortable(boolean connectToSortable) {
   * this.connectToSortable = connectToSortable; }
   */

  public void setContainment(DraggableContainment containment) {
    this.containment = containment;
  }
  

  public void setCursor(String cursor) {
    this.cursor = cursor;
  }

  public void setCursorAt(boolean cursorAt) {
    this.cursorAt = cursorAt;
  }

  public void setGrid(boolean grid) {
    this.grid = grid;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public void setHandle(String selector) {
    this.handle = selector;
  }

  public void setHelper(Element helper) {
    this.helper = helper;
    this.helperType = HelperType.ELEMENT;
  }

  public void setHelper(HelperType helperType) {
    this.helperType = helperType;
  }

  public void setIframeFix(boolean iframeFix) {
    this.iframeFix = iframeFix;
  }

  public void setOpacity(boolean opacity) {
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
    this.snap = snap;
  }

  public void setSnapMode(String snapMode) {
    this.snapMode = snapMode;
  }

  public void setSnapTolerance(int snapTolerance) {
    this.snapTolerance = snapTolerance;
  }

  public void setStack(boolean stack) {
    this.stack = stack;
  }

  public void setzIndex(boolean zIndex) {
    this.zIndex = zIndex;
  }

  @Override
  protected void initDefault() {
    super.initDefault();
    addClasses = true;
    appendTo = "parent";
    axis = false;
    // connectToSortable = false;
    containment = null;
    cursorAt = false;
    grid = false;
    handle = null;
    iframeFix = false;
    opacity = false;
    refreshPositions = false;
    revert = false;
    scroll = true;
    snap = false;
    stack = false;
    zIndex = false;
    cursor = "auto";
    helper = null;
    helperType = HelperType.ORIGINAL;
    scope = "default";
    snapMode = "both";
    revertDuration = 500;
    scrollSensitivity = 20;
    scrollSpeed = 20;
    snapTolerance = 20;
  }

}
