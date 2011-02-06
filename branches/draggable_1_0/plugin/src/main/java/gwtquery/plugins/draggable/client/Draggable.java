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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.query.client.Plugin;

import gwtquery.plugins.commonui.client.Event;
import gwtquery.plugins.commonui.client.MouseHandler;
import gwtquery.plugins.draggable.client.DraggableOptions.DragFunction;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent;
import gwtquery.plugins.draggable.client.events.DragContext;
import gwtquery.plugins.draggable.client.events.DragEvent;
import gwtquery.plugins.draggable.client.events.DragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
import gwtquery.plugins.draggable.client.plugins.CursorPlugin;
import gwtquery.plugins.draggable.client.plugins.DraggablePlugin;
import gwtquery.plugins.draggable.client.plugins.OpacityPlugin;
import gwtquery.plugins.draggable.client.plugins.ScrollPlugin;
import gwtquery.plugins.draggable.client.plugins.SnapPlugin;
import gwtquery.plugins.draggable.client.plugins.StackPlugin;
import gwtquery.plugins.draggable.client.plugins.ZIndexPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Draggable plugin for GwtQuery
 */
public class Draggable extends MouseHandler {

  /**
   * Interface containing all css classes used in this plug-in
   * 
   */
  public static interface CssClassNames {
    String GWT_DRAGGABLE = "gwtQuery-draggable";
    String GWT_DRAGGABLE_DISABLED = "gwtQuery-draggable-disabled";
    String GWT_DRAGGABLE_DRAGGING = "gwtQuery-draggable-dragging";

  }

  private class DragCaller extends StartCaller {

    public DragCaller(Element draggable, Event e) {
      super(draggable, e);
    }

    public void call(DraggablePlugin plugin) {
      plugin.onDrag(getHandler(draggable), draggable, e);
    }
  }

  private static interface PluginCaller {
    void call(DraggablePlugin plugin);
  }

  private class StartCaller implements PluginCaller {
    protected Element draggable;
    protected Event e;

    public StartCaller(Element draggable, Event e) {
      this.draggable = draggable;
      this.e = e;
    }

    public void call(DraggablePlugin plugin) {
      plugin.onStart(getHandler(draggable), draggable, e);
    }
  }

  private class StopCaller extends StartCaller {

    public StopCaller(Element draggable, Event e) {
      super(draggable, e);
    }

    public void call(DraggablePlugin plugin) {
      plugin.onStop(getHandler(draggable), draggable, e);
    }
  }

  public static final Class<Draggable> Draggable = Draggable.class;

  public static final String DRAGGABLE_HANDLER_KEY = "draggableHandler";

  private static Map<String, DraggablePlugin> draggablePlugins;

  // Register the plugin in GQuery
  static {
    GQuery.registerPlugin(Draggable.class, new Plugin<Draggable>() {
      public Draggable init(GQuery gq) {
        return new Draggable(gq);
      }
    });

    // register the different draggable plugins
    registerDraggablePlugin(new OpacityPlugin());
    registerDraggablePlugin(new ScrollPlugin());
    registerDraggablePlugin(new CursorPlugin());
    registerDraggablePlugin(new ZIndexPlugin());
    registerDraggablePlugin(new StackPlugin());
    registerDraggablePlugin(new SnapPlugin());
  }

  /**
   * Register a draggable plugin that will be called during the drag operation
   * 
   * @param plugin
   */
  public static void registerDraggablePlugin(DraggablePlugin plugin) {
    if (draggablePlugins == null) {
      draggablePlugins = new HashMap<String, DraggablePlugin>();
    }
    draggablePlugins.put(plugin.getName(), plugin);
  }

  // for performance purpose cache the current drag handler.
  private DraggableHandler currentDragHandler;

  /**
   * Constructor
   * 
   * @param element
   */
  public Draggable(Element element) {
    super(element);
  }

  /**
   * Constructor
   * 
   * @param gq
   */
  public Draggable(GQuery gq) {
    super(gq);
  }

  /**
   * Constructor
   * 
   * @param elements
   */
  public Draggable(JSArray elements) {
    super(elements);
  }

  /**
   * Constructor
   * 
   * @param list
   */
  public Draggable(NodeList<Element> list) {
    super(list);
  }

  /**
   * Remove the draggable behavior to the selected elements. This method releases
   * resources used by the plugin and should be called when an element is
   * removed of the DOM.
   * 
   * @return
   */
  public Draggable destroy() {
    for (Element e : elements()) {
      $(e).removeData(DRAGGABLE_HANDLER_KEY).removeClass(
          CssClassNames.GWT_DRAGGABLE, CssClassNames.GWT_DRAGGABLE_DISABLED,
          CssClassNames.GWT_DRAGGABLE_DRAGGING);
    }
    destroyMouseHandler();
    return this;
  }

  /**
   * Make the selected elements draggable with default options
   * 
   * @return
   */
  public Draggable draggable() {
    return draggable(new DraggableOptions(), null);
  }

  /**
   * Make the selected elements draggable by using the <code>options</code>
   * 
   * @param options
   *          options to use during the drag operation
   * @return
   */
  public Draggable draggable(DraggableOptions options) {
    return draggable(options, null);
  }

  /**
   * Make the selected elements draggable with default options. All drag events will be fired on the
   * <code>eventBus</code>
   * 
   *@param eventBus
   *          The eventBus to use to fire events.
   * @return
   */
  public Draggable draggable(HasHandlers eventBus) {
    return draggable(new DraggableOptions(), eventBus);
  }

  /**
   * Make the selected elements draggable by using the <code>options</code>. All
   * drag events will be fired on the <code>eventBus</code>
   * 
   * @param options
   *          options to use during the drag operation
   * @param eventBus
   *          The eventBus to use to fire events.
   * @return
   */
  public Draggable draggable(DraggableOptions options, HasHandlers eventBus) {

    this.eventBus = eventBus;

    initMouseHandler(options);

    for (Element e : elements()) {
      if (options.getHelperType() == HelperType.ORIGINAL
          && !positionIsFixedAbsoluteOrRelative(e.getStyle().getPosition())) {
        e.getStyle().setPosition(Position.RELATIVE);
      }
      e.addClassName(CssClassNames.GWT_DRAGGABLE);

      if (options.isDisabled()) {
        e.addClassName(CssClassNames.GWT_DRAGGABLE_DISABLED);
      }
      DraggableHandler handler = new DraggableHandler(options);
      $(e).data(DRAGGABLE_HANDLER_KEY, handler);
    }

    return this;
  }

  /**
   * Get the {@link DraggableOptions} for the first element.
   * 
   * @return
   */
  public DraggableOptions options() {

    DraggableHandler handler = data(DRAGGABLE_HANDLER_KEY,
        DraggableHandler.class);
    if (handler != null) {
      return handler.getOptions();
    }

    return null;
  }

  /**
   * Set the DraggableOptions on each element.
   * 
   * @param options
   * @return
   */
  public Draggable options(DraggableOptions options) {

    for (Element e : elements()) {
      DraggableHandler handler = $(e).data(DRAGGABLE_HANDLER_KEY,
          DraggableHandler.class);
      if (handler != null) {
        handler.setOptions(options);
      }
    }
    return this;
  }

  @Override
  protected String getPluginName() {
    return "draggable";
  }

  @Override
  protected boolean mouseCapture(Element draggable, Event event) {

    DraggableHandler handler = $(draggable).data(DRAGGABLE_HANDLER_KEY,
        DraggableHandler.class);
    return handler != null && handler.getHelper() == null
        && !handler.getOptions().isDisabled()
        && isHandleClicked(draggable, event);
  }

  @Override
  protected boolean mouseDrag(Element draggable, Event event) {
    return mouseDragImpl(draggable, getHandler(draggable), event, false);
  }

  @Override
  protected boolean mouseStart(Element draggable, Event event) {
    reset();
    DraggableHandler dragHandler = getHandler(draggable);
    DraggableOptions options = dragHandler.getOptions();

    try {
      trigger(new BeforeDragStartEvent(draggable), options
          .getOnBeforeDragStart(), draggable);
    } catch (UmbrellaException e) {
      for (Throwable t : e.getCauses()) {
        if (t instanceof StopDragException) {
          return false;
        }
      }

    }

    dragHandler.createHelper(draggable, event);

    dragHandler.cacheHelperSize();

    if (getDragAndDropManager().isHandleDroppable()) {
      getDragAndDropManager().setCurrentDraggable(draggable);
    }

    dragHandler.initialize(draggable, event);

    callPlugins(new StartCaller(draggable, event), options);

    try {
      trigger(new DragStartEvent(draggable), options.getOnDragStart(),
          draggable);
    } catch (UmbrellaException e) {
      for (Throwable t : e.getCauses()) {
        if (t instanceof StopDragException) {
          mouseStop(draggable, event);
          return false;
        }
      }

    }

    dragHandler.cacheHelperSize();

    if (getDragAndDropManager().isHandleDroppable()) {
      getDragAndDropManager().initialize(draggable, event);
    }

    getHelper(draggable).addClass(CssClassNames.GWT_DRAGGABLE_DRAGGING);

    mouseDragImpl(draggable, dragHandler, event, true);

    return true;
  }

  @Override
  protected boolean mouseStop(final Element draggable, final Event event) {

    final DraggableHandler handler = getHandler(draggable);
    final DraggableOptions options = handler.getOptions();

    boolean dropped = false;
    if (getDragAndDropManager().isHandleDroppable()) {
      dropped = getDragAndDropManager().drop(draggable, event);
    }

    if (draggable == null) {
      return false;
    }

    RevertOption revertOption = options.getRevert();
    if (revertOption.doRevert(dropped)) {
      getHandler(draggable).revertToOriginalPosition(new Function() {
        @Override
        public void f(Element e) {
          callPlugins(new StopCaller(draggable, event), options);
          triggerDragStop(draggable, handler.getHelper().get(0), options);

          getHandler(draggable).clear(draggable);
        }
      });
      return false;
    }

    callPlugins(new StopCaller(draggable, event), options);
    triggerDragStop(draggable, handler.getHelper().get(0), options);

    getHandler(draggable).clear(draggable);

    return false;
  }

  private void callPlugins(PluginCaller caller, DraggableOptions options) {
    for (DraggablePlugin plugin : draggablePlugins.values()) {
      if (plugin.hasToBeExecuted(options)) {
        caller.call(plugin);
      }
    }
  }

  private DragAndDropManager getDragAndDropManager() {
    return DragAndDropManager.getInstance();
  }

  private DraggableHandler getHandler(Element draggable) {

    if (currentDragHandler == null) {
      currentDragHandler = $(draggable).data(DRAGGABLE_HANDLER_KEY,
          DraggableHandler.class);
    }
    return currentDragHandler;
  }

  private GQuery getHelper(Element draggable) {
    DraggableHandler handler = getHandler(draggable);
    return handler != null ? handler.getHelper() : null;
  }

  private DraggableOptions getOptions(Element draggable) {
    DraggableHandler handler = getHandler(draggable);
    return handler != null ? handler.getOptions() : null;
  }

  private boolean isHandleClicked(Element draggable, final Event event) {
    DraggableOptions options = getOptions(draggable);
    // if no handle or if specified handle is not inside the draggable element,
    // continue
    if (options.getHandle() == null
        || $(options.getHandle(), draggable).length() == 0) {
      return true;
    }

    // OK, we have a valid handle, check if we are clicking on the handle object
    // or one of its descendants
    GQuery handleAndDescendant = $(options.getHandle(), draggable).find("*")
        .andSelf();
    for (Element e : handleAndDescendant.elements()) {
      if (e == event.getEventTarget().cast()) {
        return true;
      }
    }
    return false;
  }

  /**
   * implementation of mouse drag
   */
  private boolean mouseDragImpl(Element draggable,
      DraggableHandler dragHandler, Event event, boolean noPropagation) {

    dragHandler.regeneratePositions(event);

    if (!noPropagation) {

      callPlugins(new DragCaller(draggable, event), dragHandler.getOptions());

      try {
        trigger(new DragEvent(draggable), dragHandler.getOptions().getOnDrag(),
            draggable);
      } catch (UmbrellaException e) {
        for (Throwable t : e.getCauses()) {
          if (t instanceof StopDragException) {
            mouseStop(draggable, event);
            return false;
          }
        }

      }
    }

    dragHandler.moveHelper(noPropagation);

    if (getDragAndDropManager().isHandleDroppable()) {
      getDragAndDropManager().drag(draggable, event);
    }

    return false;
  }

  private native boolean positionIsFixedAbsoluteOrRelative(String position) /*-{
    return (/^(?:r|a|f)/).test(position);
  }-*/;

  private void reset() {
    currentDragHandler = null;
  }

  /**
   * Use a deferred command to be sure that this event is trigger after the
   * possible drop event.
   * 
   * @param draggable
   * @param options
   */
  private void triggerDragStop(final Element draggable, final Element helper,
      final DraggableOptions options) {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {

      public void execute() {
        trigger(new DragStopEvent(draggable), options.getOnDragStop(),
            draggable);

      }
    });
  }

  private void trigger(GwtEvent<?> e, DragFunction callback, Element draggable) {
    trigger(e, callback, draggable, eventBus);
  }

  private static void trigger(GwtEvent<?> e, DragFunction callback,
      Element draggable, HasHandlers handlerManager) {
    if (handlerManager != null && e != null) {
      handlerManager.fireEvent(e);
    }
    if (callback != null) {
      callback.f(new DragContext(draggable));
    }
  }

}
