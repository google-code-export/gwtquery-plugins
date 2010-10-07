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

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.query.client.Plugin;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.commonui.client.MouseHandler;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
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
 * Draggable for GwtQuery
 */
public class Draggable extends MouseHandler {

  /**
   * Interface containing all css classes used in this plug-in
   * 
   */
  public static interface CssClassNames {
    String UI_DRAGGABLE = "ui-draggable";
    String UI_DRAGGABLE_DISABLED = "ui-draggable-disabled";
    String UI_DRAGGABLE_DRAGGING = "ui-draggable-dragging";

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

  protected static final String DRAGGABLE_HANDLER_KEY = "draggableHandler";

  private static Map<String, DraggablePlugin> draggablePlugins;

  // Register the plugin in GQuery
  static {
    GQuery.registerPlugin(Draggable.class, new Plugin<Draggable>() {
      public Draggable init(GQuery gq) {
        return new Draggable(gq);
      }
    });

    registerDraggablePlugin(new OpacityPlugin());
    registerDraggablePlugin(new ScrollPlugin());
    registerDraggablePlugin(new CursorPlugin());
    registerDraggablePlugin(new ZIndexPlugin());
    registerDraggablePlugin(new StackPlugin());
    registerDraggablePlugin(new SnapPlugin());
  }

  public static void registerDraggablePlugin(DraggablePlugin plugin) {
    if (draggablePlugins == null) {
      draggablePlugins = new HashMap<String, DraggablePlugin>();
    }
    draggablePlugins.put(plugin.getName(), plugin);
  }

  // for performance purpose cache the current drag handler.
  private DraggableHandler currentDragHandler;

  public Draggable(Element element) {
    super(element);
  }

  public Draggable(GQuery gq) {
    super(gq);
  }

  public Draggable(JSArray elements) {
    super(elements);
  }

  public Draggable(NodeList<Element> list) {
    super(list);
  }

  public Draggable destroy() {
    for (Element e : elements()) {
      $(e).removeData(DRAGGABLE_HANDLER_KEY).removeClass(
          CssClassNames.UI_DRAGGABLE, CssClassNames.UI_DRAGGABLE_DISABLED,
          CssClassNames.UI_DRAGGABLE_DRAGGING);
    }
    destroyMouseHandler();
    return this;
  }

  public Draggable draggable() {
    return draggable(new DraggableOptions(), null);
  }

  public Draggable draggable(DraggableOptions options) {
    return draggable(options, null);
  }

  public Draggable draggable(DraggableOptions options, HandlerManager eventBus) {

    this.eventBus = eventBus;

    initMouseHandler(options);

    for (Element e : elements()) {
      if (options.getHelperType() == HelperType.ORIGINAL
          && !positionIsFixedAbsoluteOrRelative(e.getStyle().getPosition())) {
        e.getStyle().setPosition(Position.RELATIVE);
      }
      if (options.isAddClasses()) {
        e.addClassName(CssClassNames.UI_DRAGGABLE);
      }
      if (options.isDisabled()) {
        e.addClassName(CssClassNames.UI_DRAGGABLE_DISABLED);
      }
      DraggableHandler handler = new DraggableHandler(options);
      $(e).data(DRAGGABLE_HANDLER_KEY, handler);
    }

    initMouseHandler(options);

    return this;
  }

  /**
   * Get the options for the first element.
   * 
   * @return
   */
  public DraggableOptions getOptions() {

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
  public Draggable setOptions(DraggableOptions options) {

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
    DraggableOptions options = getOptions(draggable);

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
    } catch (StopDragException e) {
      dragHandler.clear(draggable);
      return false;
    }

    dragHandler.cacheHelperSize();

    if (getDragAndDropManager().isHandleDroppable()) {
      getDragAndDropManager().prepareOffset(draggable, options, event);
    }

    getHelper(draggable).addClass(CssClassNames.UI_DRAGGABLE_DRAGGING);

    mouseDragImpl(draggable, dragHandler, event, true);

    return true;
  }

  @Override
  protected boolean mouseStop(final Element draggable, final Event event) {
    final DraggableOptions options = getOptions(draggable);

    boolean dropped = false;
    if (getDragAndDropManager().isHandleDroppable()) {
      dropped = getDragAndDropManager().drop(draggable, options, event);
    }

    if (draggable == null || draggable.getParentNode() == null) {
      // original element is removed...
      return false;
    }

    RevertOption revertOption = options.getRevert();
    if (revertOption.doRevert(dropped)) {
      getHandler(draggable).revertToOriginalPosition(new Function() {
        @Override
        public void f(Element e) {
          callPlugins(new StopCaller(draggable, event), options);
          trigger(new DragStopEvent(draggable), options.getOnDragStop(), draggable);

          getHandler(draggable).clear(draggable);
        }
      });
      return false;
    }

    callPlugins(new StopCaller(draggable, event), options);
    trigger(new DragStopEvent(draggable), options.getOnDragStop(), draggable);

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

  private DraggableDroppableManager getDragAndDropManager() {
    return DraggableDroppableManager.getInstance();
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
      } catch (StopDragException e) {
        mouseStop(draggable, event);
        return false;
      }
    }

    dragHandler.moveHelper(noPropagation);

    if (getDragAndDropManager().isHandleDroppable()) {
      getDragAndDropManager().drag(draggable, dragHandler.getOptions(), event);
    }

    return false;
  }

  private native boolean positionIsFixedAbsoluteOrRelative(String position) /*-{
    return (/^(?:r|a|f)/).test(position);
  }-*/;

  private void reset() {
    currentDragHandler = null;
  }

}
