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
package gwtquery.plugins.draggable.client.gwt;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.AxisOption;
import gwtquery.plugins.draggable.client.DraggableOptions.CursorAt;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.DraggableOptions.SnapMode;
import gwtquery.plugins.draggable.client.events.DragEvent;
import gwtquery.plugins.draggable.client.events.DragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
import gwtquery.plugins.draggable.client.events.HasAllDragHandler;
import gwtquery.plugins.draggable.client.events.DragEvent.DragEventHandler;
import gwtquery.plugins.draggable.client.events.DragStartEvent.DragStartEventHandler;
import gwtquery.plugins.draggable.client.events.DragStopEvent.DragStopEventHandler;

import java.util.List;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * Wrapper widget that wrap an GWT widget and to drag it.
 * 
 * 
 * @author jdramaix
 * 
 * @param <T>
 */
public class DraggableWidget<T extends Widget> extends Composite implements
		HasAllDragHandler {

	private DraggableOptions options;
	private HandlerManager dragHandlerManager;

	/**
	 * Constructor
	 * 
	 * @param w
	 */
	public DraggableWidget(T w) {
		this(w, new DraggableOptions(),DraggableOptions.DEFAULT_SCOPE);
	}

	/**
	 * Constructor
	 * 
	 * @param w
	 * @param options
	 */
	public DraggableWidget(T w, DraggableOptions options) {
		this(w,options, DraggableOptions.DEFAULT_SCOPE);

	}

	/**
	 * Constructor
	 * 
	 * @param w
	 * @param options
	 * @param scope
	 *            Used to group sets of draggable and droppable Widget, in
	 *            addition to droppable's accept option. A draggable with the
	 *            same scope value as a droppable will be accepted.
	 */
	public DraggableWidget(T w, DraggableOptions options, String scope) {
		initWidget(w);
		this.options = options;
		this.options.setScope(scope);
	}

	/**
	 * Constructor
	 * 
	 * @param w
	 * @param scope
	 *            Used to group sets of draggable and droppable Widget, in
	 *            addition to droppable's accept option. A draggable with the
	 *            same scope value as a droppable will be accepted.
	 */
	public DraggableWidget(T w, String scope) {
		this(w, new DraggableOptions(), scope);
	}

	public HandlerRegistration addDragHandler(DragEventHandler handler) {
		return addDragHandler(handler, DragEvent.TYPE);
	}

	public HandlerRegistration addDragStartHandler(DragStartEventHandler handler) {
		return addDragHandler(handler, DragStartEvent.TYPE);
	}

	public HandlerRegistration addDragStopHandler(DragStopEventHandler handler) {
		return addDragHandler(handler, DragStopEvent.TYPE);
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

	public DraggableOptions getDraggableOptions() {
		return options;
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

	public RevertOption getRevert() {
		return options.getRevert();
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

	public void setCancel(String... cancel) {
		options.setCancel(cancel);
	}

	public void setContainment(int[] containment) {
		options.setContainment(containment);
	}
	
	public void setContainment(String containment) {
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

	public void setDraggableOptions(DraggableOptions options) {
		this.options = options;
		if (isAttached()) {
			$(getElement()).as(Draggable).setOptions(options);
		}
	}

	public void setGrid(int[] grid) {
		options.setGrid(grid);
	}

	public void setHandle(String selector) {
		options.setHandle(selector);
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

	public void setRevert(RevertOption revert) {
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
		if (snapWidgets == null) {
			return;
		}
		JSArray snapElements = JSArray.create();
		for (Widget w : snapWidgets) {
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

	public void useCloneAsHelper() {
		options.setHelper(HelperType.CLONE);
	}

	public void useOriginalWidgetAsHelper() {
		options.setHelper(HelperType.ORIGINAL);
	}

	public void useOtherWidgetAsHelper(Widget w) {
		assert w != null;
		options.setHelper(w.getElement());
	}

	protected final <H extends EventHandler> HandlerRegistration addDragHandler(
			H handler, Type<H> type) {
		return ensureDragHandlers().addHandler(type, handler);
	}

	protected HandlerManager ensureDragHandlers() {
		return dragHandlerManager == null ? dragHandlerManager = new HandlerManager(
				this)
				: dragHandlerManager;
	}

	protected HandlerManager getDragHandlerManager() {
		return dragHandlerManager;
	}

	@SuppressWarnings("unchecked")
	protected T getInitialWidget() {
		return (T) getWidget();
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		$(getElement()).as(Draggable).draggable(options, ensureDragHandlers());
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		$(getElement()).as(Draggable).destroy();
	}

}
