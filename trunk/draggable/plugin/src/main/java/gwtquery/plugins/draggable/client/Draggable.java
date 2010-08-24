package gwtquery.plugins.draggable.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.query.client.Plugin;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.commonui.client.MouseHandler;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;

/**
 * Draggable for GwtQuery
 */
public class Draggable extends MouseHandler {

  /**
   * Interface containing all css classes used in this plug-in
   * 
   */
  private static interface CssClassNames {
    String UI_DRAGGABLE = "ui-draggable";
    String UI_DRAGGABLE_DISABLED = "ui-draggable-disabled";
    String UI_DRAGGABLE_DRAGGING = "ui-draggable-dragging";
  }

  public static final Class<Draggable> Draggable = Draggable.class;

  private static final String DRAGGABLE_KEY = "draggable";

  // Register the plugin in GQuery
  static {
    GQuery.registerPlugin(Draggable.class, new Plugin<Draggable>() {
      public Draggable init(GQuery gq) {
        return new Draggable(gq);
      }
    });
  }

  private DraggableOptions options;
  private GQuery helper;

  public Draggable(GQuery gq) {
    super(gq);
  }

  public Draggable(Element element) {
    super(element);
  }

  public Draggable(JSArray elements) {
    super(elements);
  }

  public Draggable(NodeList<Element> list) {
    super(list);
  }

  public Draggable draggable() {
    return draggable(new DraggableOptions());
  }

  public Draggable draggable(DraggableOptions options) {
    this.options = options;
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
    }

    initMouseHandler(options);

    return this;
  }

  public Draggable destroy() {
    for (Element e : elements()) {
      GQuery $e = $(e);
      if ($e.data(DRAGGABLE_KEY) == null) {
        continue;
      }
      $e.removeData(DRAGGABLE_KEY).removeClass(CssClassNames.UI_DRAGGABLE,
          CssClassNames.UI_DRAGGABLE_DISABLED,
          CssClassNames.UI_DRAGGABLE_DRAGGING);
    }
    destroyMouseHandler();
    return this;
  }

  @Override
  protected String getPluginName() {
    return "draggable";
  }

  @Override
  protected boolean mouseCapture(Element draggable, Event event) {
    // TODO : we can manage resizable object here
    return helper == null && !options.isDisabled()
        && isHandleClicked(draggable, event);
  }

  @Override
  protected boolean mouseDrag(Element elemen, Event event) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected boolean mouseStart(Element draggable, Event event) {
    createHelper(draggable, event);
    return false;
  }

  @Override
  protected boolean mouseStop(Element draggable, Event event) {
    // TODO Auto-generated method stub
    return false;
  }

  private void createHelper(Element draggable, Event e) {
    helper = options.getHelperType().createHelper(draggable,
        options.getHelper());

    if (helper.parents("body").length() == 0) {
      // TODO continue
      // helper.appendT
    }

    if (options.getHelperType() != HelperType.ORIGINAL
        && !helper.css("position").matches("(fixed|absolute)")) {
      helper.css("position", Position.ABSOLUTE.getCssName());
    }

  }

  private boolean isHandleClicked(Element draggable, final Event event) {
    // if no handle or if specified handle is not inside the draggable element,
    // continue
    if (options.getHandle() == null
        || $(options.getHandle(), draggable).length() == 0) {
      return true;
    }

    // OK, we have a valid handle, check if we click on the handle object or one
    // of its descendant
    GQuery handleAndDescendant = $(options.getHandle(), draggable).find("*")
        .andSelf();
    for (Element e : handleAndDescendant.elements()) {
      if (e == event.getEventTarget().cast()) {
        return true;
      }
    }
    return false;
  }

  private native boolean positionIsFixedAbsoluteOrRelative(String position) /*-
                                                                            return (/^(?:r|a|f)/).test(position);
                                                                            -*/;

}
