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
package gwtquery.plugins.droppable.client.gwt;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.droppable.client.Droppable.Droppable;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.query.client.plugins.EventsListener;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.droppable.client.Droppable;
import gwtquery.plugins.droppable.client.DroppableOptions;
import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;
import gwtquery.plugins.droppable.client.DroppableOptions.DroppableTolerance;
import gwtquery.plugins.droppable.client.events.ActivateDroppableEvent;
import gwtquery.plugins.droppable.client.events.DeactivateDroppableEvent;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.HasAllDropHandler;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent;
import gwtquery.plugins.droppable.client.events.ActivateDroppableEvent.ActivateDroppableEventHandler;
import gwtquery.plugins.droppable.client.events.DeactivateDroppableEvent.DeactivateDroppableEventHandler;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent.OutDroppableEventHandler;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent.OverDroppableEventHandler;

/**
 * Wrapper widget that wrap an GWT widget and make it a drop target.
 * 
 * 
 * @author jdramaix
 * 
 * @param <T>
 */
public class DroppableWidget<T extends Widget> extends Composite implements
    HasAllDropHandler {

  private final static String DROPPABLE_WIDGET_KEY = "__droppableWidget";
  
  public static DroppableWidget<?> get(Element e){
    return $(e).data(DROPPABLE_WIDGET_KEY,DroppableWidget.class);
  }
  
  
  private DroppableOptions options;
  private String childrenSelector;
  private EventBus dropHandlerManager;

  /**
   * Constructor
   * 
   * @param w
   *          the widget that you want to make it a drop target.
   */
  public DroppableWidget(T w) {
    this(w, null, new DroppableOptions(), DraggableOptions.DEFAULT_SCOPE);
  }

  /**
   * Constructor
   * 
   * @param w
   *          the widget that you want to make it a drop target.
   * @param childrenSelector
   *          css selector used to define children within the widget
   *          <code>w</code> droppable
   * 
   *          <pre>
   * e.g : if you want to pass a {@link Grid} object and 
   *         you want that all cell become drop targets :
   *          Grid myGrid = new Grid(10,10);
   *          DroppableWidget<Grid> dw = new DroppableWidget(myGrid, "td");
   * </pre>
   */
  public DroppableWidget(T w, String childrenSelector) {
    this(w, null, new DroppableOptions(), DraggableOptions.DEFAULT_SCOPE);
  }

  /**
   * Constructor
   * 
   * @param w
   *          the widget that you want to make it a drop target.
   * @param options
   *          options to use during the drop operation
   */
  public DroppableWidget(T w, DroppableOptions options) {
    this(w, null, options, DraggableOptions.DEFAULT_SCOPE);

  }

  /**
   * Constructor
   * 
   * @param w
   *          the widget that you want to make it a drop target.
   * 
   * @param childrenSelector
   *          css selector used to define children within the widget
   *          <code>w</code> droppable
   * @param options
   *          options to use during the drop operation
   */
  public DroppableWidget(T w, String childrenSelector, DroppableOptions options) {
    this(w, childrenSelector, options, DraggableOptions.DEFAULT_SCOPE);

  }

  /**
   * Constructor
   * 
   * @param w
   *          the widget that you want to make it a drop target.
   * @param options
   *          options to use during the drop operation
   * @param scope
   *          Used to group sets of draggable and droppable Widget, in addition
   *          to droppable's accept option. A draggable with the same scope
   *          value as a droppable will be accepted.
   */
  public DroppableWidget(T w, DroppableOptions options, String scope) {
    this(w, null, options, scope);
  }

  /**
   * Constructor
   * 
   * @param w
   *          the widget that you want to make it a drop target.
   * @param childrenSelector
   *          css selector used to define children within the widget
   *          <code>w</code> droppable
   * @param options
   *          options to use during the drop operation
   * @param scope
   *          Used to group sets of draggable and droppable Widget, in addition
   *          to droppable's accept option. A draggable with the same scope
   *          value as a droppable will be accepted.
   */
  public DroppableWidget(T w, String childrenSelector,
      DroppableOptions options, String scope) {
    initWidget(w);
    this.options = options;
    this.options.setScope(scope);
    this.childrenSelector = childrenSelector;
  }
  
  /**
   * Add possibility to extend this widget
   * As DroppableWidget is a {@link Composite}, don't forget to call the initWidget() method
   */
  protected DroppableWidget(){
    options = new DroppableOptions();
  }

  protected final <H extends EventHandler> HandlerRegistration addDropHandler(
      H handler, Type<H> type) {
    return ensureDropHandlers().addHandler(type, handler);
  }

  protected EventBus ensureDropHandlers() {
    return dropHandlerManager == null ? dropHandlerManager = new SimpleEventBus()
        : dropHandlerManager;
  }

  protected EventBus getDropHandlerManager() {
    return dropHandlerManager;
  }

  @Override
  protected void onLoad() {
    super.onLoad();
    // force using of EventListener from GQuery !
    EventsListener gQueryEventListener = EventsListener
        .getInstance(getElement());
    if (DOM.getEventListener(getElement()) != gQueryEventListener) {
      DOM.setEventListener(getElement(), gQueryEventListener);
    }
    getDroppable().droppable(options, ensureDropHandlers()).data(DROPPABLE_WIDGET_KEY, this);
  }

  @Override
  protected void onUnload() {
    super.onUnload();
    getDroppable().destroy().removeData(DROPPABLE_WIDGET_KEY);
  }

  public HandlerRegistration addActivateDroppableHandler(
      ActivateDroppableEventHandler handler) {
    return addDropHandler(handler, ActivateDroppableEvent.TYPE);
  }

  public HandlerRegistration addDeactivateDroppableHandler(
      DeactivateDroppableEventHandler handler) {
    return addDropHandler(handler, DeactivateDroppableEvent.TYPE);
  }

  public HandlerRegistration addDropHandler(DropEventHandler handler) {
    return addDropHandler(handler, DropEvent.TYPE);
  }

  public HandlerRegistration addOutDroppableHandler(
      OutDroppableEventHandler handler) {
    return addDropHandler(handler, OutDroppableEvent.TYPE);
  }
  
  /**
   * Get the wrapped original widget
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public T getOriginalWidget() {
    return (T) getWidget();
  }

  public HandlerRegistration addOverDroppableHandler(
      OverDroppableEventHandler handler) {
    return addDropHandler(handler, OverDroppableEvent.TYPE);
  }

  public AcceptFunction getAccept() {
    return options.getAccept();
  }

  public String getActiveClass() {
    return options.getActiveClass();
  }

  public String getHoverClass() {
    return options.getHoverClass();
  }

  public String getScope() {
    return options.getScope();
  }

  public DroppableTolerance getTolerance() {
    return options.getTolerance();
  }

  public boolean isDisabled() {
    return options.isDisabled();
  }

  public boolean isGreedy() {
    return options.isGreedy();
  }

  public void setAccept(AcceptFunction acceptFunction) {
    if (acceptFunction != null){
      options.setAccept(acceptFunction);
    }else{
      options.setAccept(DroppableOptions.ACCEPT_ALL);
    }
  }

  public void setAccept(String selector) {
    options.setAccept(selector);
  }

  public void setActiveClass(String activeClass) {
    options.setActiveClass(activeClass);
  }

  public void setDisabled(boolean disabled) {
    options.setDisabled(disabled);
  }

  public void setGreedy(boolean greedy) {
    options.setGreedy(greedy);
  }

  public void setHoverClass(String hoverClass) {
    options.setHoverClass(hoverClass);
  }

  public void setScope(String scope) {
    getDroppable().changeScope(scope);
    options.setScope(scope);
  }

  public void setTolerance(DroppableTolerance tolerance) {
    options.setTolerance(tolerance);
  }

  private Droppable getDroppable(){
    if (childrenSelector != null) {
      return $(childrenSelector, getElement()).as(Droppable);
    } else {
      return $(getElement()).as(Droppable);
    }

  }
}
