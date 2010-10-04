package gwtquery.plugins.draggable.client.gwt;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.draggable.client.DraggableContainment;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.AxisOption;
import gwtquery.plugins.draggable.client.DraggableOptions.CursorAt;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.draggable.client.DraggableOptions.SnapMode;

import java.util.List;

public class DraggableWidget<T extends Widget> extends Composite {

  private DraggableOptions options;

  public DraggableWidget(T w) {
    this(w, new DraggableOptions());
  }

  public DraggableWidget(T w, DraggableOptions options) {
    initWidget(w);
    this.options = options;

  }

  public DraggableOptions getDraggableOptions() {
    return options;
  }

  public void setDraggableOptions(DraggableOptions options) {
    this.options = options;
    if (isAttached()) {
      $(getElement()).as(Draggable).setOptions(options);
    }
  }

  @SuppressWarnings("unchecked")
  protected T getInitialWidget() {
    return (T) getWidget();
  }

  @Override
  protected void onLoad() {
    super.onLoad();
    $(getElement()).as(Draggable).draggable(options);
  }

  @Override
  protected void onUnload() {
    super.onUnload();
    $(getElement()).as(Draggable).destroy();
  }

  public String getAppendTo() {
    return options.getAppendTo();
  }

  public AxisOption getAxis() {
    return options.getAxis();
  }

  public String[] getCancel() {
    return options.getCancel();
  }

  public DraggableContainment getContainment() {
    return options.getContainment();
  }

  public Cursor getCursor() {
    return options.getCursor();
  }

  public CursorAt getCursorAt() {
    return options.getCursorAt();
  }

  public int getDelay() {
    return options.getDelay();
  }

  public int getDistance() {
    return options.getDistance();
  }

  public int[] getGrid() {
    return options.getGrid();
  }

  public String getHandle() {
    return options.getHandle();
  }

  public GQuery getHelper() {
    return options.getHelper();
  }

  public HelperType getHelperType() {
    return options.getHelperType();
  }

  public Float getOpacity() {
    return options.getOpacity();
  }

  public int getRevertDuration() {
    return options.getRevertDuration();
  }

  public String getScope() {
    return options.getScope();
  }

  public int getScrollSensitivity() {
    return options.getScrollSensitivity();
  }

  public int getScrollSpeed() {
    return options.getScrollSpeed();
  }

  public String getSnap() {
    return options.getSnap();
  }

  public SnapMode getSnapMode() {
    return options.getSnapMode();
  }

  public int getSnapTolerance() {
    return options.getSnapTolerance();
  }

  public GQuery getStack() {
    return options.getStack();
  }

  public Integer getZIndex() {
    return options.getZIndex();
  }

  public boolean isAddClasses() {
    return options.isAddClasses();
  }

  public boolean isDisabled() {
    return options.isDisabled();
  }

  public boolean isIframeFix() {
    return options.isIframeFix();
  }

  public boolean isRefreshPositions() {
    return options.isRefreshPositions();
  }

  public boolean isRevert() {
    return options.isRevert();
  }

  public boolean isScroll() {
    return options.isScroll();
  }

  public boolean isSnap() {
    return options.isSnap();
  }

  public void setAddClasses(boolean addClasses) {
    options.setAddClasses(addClasses);
  }

  public void setAppendTo(String appendTo) {
    options.setAppendTo(appendTo);
  }

  public void setAxis(AxisOption axis) {
    options.setAxis(axis);
  }

  public void setCancel(String... cancelSelector) {
    options.setCancel(cancelSelector);
  }

  public void setContainment(DraggableContainment containment) {
    options.setContainment(containment);
  }

  public void setCursor(Cursor cursor) {
    options.setCursor(cursor);
  }

  public void setCursorAt(CursorAt cursorAt) {
    options.setCursorAt(cursorAt);
  }

  public void setDelay(int delay) {
    options.setDelay(delay);
  }

  public void setDisabled(boolean disabled) {
    options.setDisabled(disabled);
  }

  public void setDistance(int distance) {
    options.setDistance(distance);
  }

  public void setGrid(int[] grid) {
    options.setGrid(grid);
  }

  public void setHandle(String selector) {
    options.setHandle(selector);
  }

  public void useOtherWidgetAsHelper(Widget w) {
    assert w != null;
    options.setHelper(w.getElement());
  }

  public void useOriginalWidgetAsHelper() {
    options.setHelper(HelperType.ORIGINAL);
  }

  public void useCloneAsHelper() {
    options.setHelper(HelperType.CLONE);
  }

  public void setIframeFix(boolean iframeFix) {
    options.setIframeFix(iframeFix);
  }

  public void setOpacity(float opacity) {
    options.setOpacity(opacity);
  }

  public void setRefreshPositions(boolean refreshPositions) {
    options.setRefreshPositions(refreshPositions);
  }

  public void setRevert(boolean revert) {
    options.setRevert(revert);
  }

  public void setRevertDuration(int revertDuration) {
    options.setRevertDuration(revertDuration);
  }

  public void setScope(String scope) {
    options.setScope(scope);
  }

  public void setScroll(boolean scroll) {
    options.setScroll(scroll);
  }

  public void setScrollSensitivity(int scrollSensitivity) {
    options.setScrollSensitivity(scrollSensitivity);
  }

  public void setScrollSpeed(int scrollSpeed) {
    options.setScrollSpeed(scrollSpeed);
  }

  public void setSnap(boolean snap) {
    options.setSnap(snap);
  }

  public void setSnap(List<Widget> snapWidgets) {
    if (snapWidgets == null){
      return;
    }
    JSArray snapElements = JSArray.create();
   for (Widget w : snapWidgets){
     snapElements.addNode(w.getElement());
   }
   options.setSnap($(snapElements));
  }

  public void setSnapMode(SnapMode snapMode) {
    options.setSnapMode(snapMode);
  }

  public void setSnapTolerance(int snapTolerance) {
    options.setSnapTolerance(snapTolerance);
  }

  public void setStack(GQuery stack) {
    options.setStack(stack);
  }

  public void setStack(String selector) {
    options.setStack(selector);
  }

  public void setZIndex(Integer zIndex) {
    options.setZIndex(zIndex);
  }

}