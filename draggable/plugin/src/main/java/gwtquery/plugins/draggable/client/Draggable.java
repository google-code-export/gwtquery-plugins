package gwtquery.plugins.draggable.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.query.client.GQUtils;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.query.client.Plugin;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.commonui.client.MouseHandler;
import gwtquery.plugins.draggable.client.DraggableOptions.AxisOption;
import gwtquery.plugins.draggable.client.DraggableOptions.CursorAt;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.draggable.client.events.DragEvent;
import gwtquery.plugins.draggable.client.events.DragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
import gwtquery.plugins.draggable.client.impl.DraggableImpl;
import gwtquery.plugins.draggable.client.plugins.CursorPlugin;
import gwtquery.plugins.draggable.client.plugins.DraggablePlugin;
import gwtquery.plugins.draggable.client.plugins.OpacityPlugin;
import gwtquery.plugins.draggable.client.plugins.ScrollPlugin;
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
  static interface CssClassNames {
    String UI_DRAGGABLE = "ui-draggable";
    String UI_DRAGGABLE_DISABLED = "ui-draggable-disabled";
    String UI_DRAGGABLE_DRAGGING = "ui-draggable-dragging";

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
      plugin.onStart(dragOperationInfo, draggable, e);
    }
  }

  private class StopCaller extends StartCaller {

    public StopCaller(Element draggable, Event e) {
      super(draggable, e);
    }

    public void call(DraggablePlugin plugin) {
      plugin.onStop(dragOperationInfo, draggable, e);
    }
  }

  private class DragCaller extends StartCaller {

    public DragCaller(Element draggable, Event e) {
      super(draggable, e);
    }

    public void call(DraggablePlugin plugin) {
      plugin.onDrag(dragOperationInfo, draggable, e);
    }
  }

  /**
   * A POJO used to store all values we need to keep during drag operation
   */
  public class DragOperationInfo {

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
    private HelperDimension helperDimension;
    
    //can be instantiate only by Draggable plugin
    DragOperationInfo(GQuery helper, DraggableOptions options) {
      this.options = options;
      this.helper = helper;
    }

    public void generateAbsPosition() {
      GQuery scroll = getScrollParent();
      boolean scrollIsRootNode = isRootNode(scroll.get(0));

      int top = position.top
          + relativeOffset.top
          + parentOffset.top
          + ("fixed".equals(helperCssPosition) ? -helperScrollParent
              .scrollTop() : scrollIsRootNode ? 0 : scroll.scrollTop());

      int left = position.left
          + relativeOffset.left
          + parentOffset.left
          + ("fixed".equals(helperCssPosition) ? -helperScrollParent
              .scrollLeft() : scrollIsRootNode ? 0 : scroll.scrollLeft());

      absPosition = new LeftTopDimension(left, top);

    }

    public void initialize(Element element, Event e) {
      helperCssPosition = helper.css("position");
      helperScrollParent = helper.as(GQueryUi).scrollParent();
      helperOffsetParent = helper.offsetParent();

      setMarginCache(element);

      absPosition = new LeftTopDimension(element.getOffsetLeft(), element
          .getOffsetTop());

      offset = new LeftTopDimension(absPosition.getLeft() - margin.getLeft(),
          absPosition.getTop() - margin.getTop());

      offsetClick = new LeftTopDimension(pageX(e) - offset.left, pageY(e)
          - offset.top);

      parentOffset = calculateParentOffset(element);
      relativeOffset = calculateRelativeHelperOffset(element);

      originalEventPageX = pageX(e);
      originalEventPageY = pageY(e);

      position = generatePosition(e);
      originalPosition = new LeftTopDimension(position.left, position.top);
      
      if (options.getCursorAt() != null){
        adjustOffsetFromHelper(options.getCursorAt());
      }
      calculateContainment();

      //log();
    }
    
   /* private void log(){
      GWT.log("helper :"+helper);
      GWT.log("margin :"+ margin);
      GWT.log("offset :"+ offset);
      GWT.log("offsetClick :"+ offsetClick);
      GWT.log("helperCssPosition :"+ helperCssPosition);
      GWT.log("helperDimension :"+ helperDimension);
      GWT.log("helperOffsetParent :"+ helperOffsetParent);
      GWT.log("relativeOffset :"+ relativeOffset);
     // GWT.log("helperScrollParent :"+ helperScrollParent);
      GWT.log("position :"+ position);
      GWT.log("originalPosition :"+ originalPosition);
      GWT.log("originalEventPageX :"+ originalEventPageX);
      GWT.log("originalEventPageY :"+ originalEventPageY);
      
    }*/


    public void regeneratePosition(Event e) {
      position = generatePosition(e);

    }

    public void setMarginCache(Element element) {
      int marginLeft = (int) GQUtils.cur(element, "marginLeft", true);
      int marginTop = (int) GQUtils.cur(element, "marginTop", true);

      margin = new LeftTopDimension(marginLeft, marginTop);

    }
    
    private void adjustOffsetFromHelper(CursorAt cursorAt) {
      
      if (cursorAt.getLeft() != null){
        offsetClick.left = cursorAt.getLeft().intValue() + margin.getLeft();
      }
      
      if (cursorAt.getRight() != null){
        offsetClick.left = helperDimension.getWidth() - cursorAt.getRight().intValue() + margin.getLeft();
      }
      
      if (cursorAt.getTop() != null){
        offsetClick.top = cursorAt.getTop().intValue() + margin.getTop();
      }
      
      if (cursorAt.getBottom() != null){
        offsetClick.top = helperDimension.getHeight() - cursorAt.getBottom().intValue() + margin.getTop();
      }
    }

    private void calculateContainment() {
      DraggableContainment dc = options.getContainment();
      if (dc == null) {
        return;
      }

      if (dc.getContainmentAsArray() != null) {
        containment = dc.getContainmentAsArray();
        return;
      }

      if ("document".equals(dc.getContainment())
          || "window".equals(dc.getContainment())) {
        GQuery $containement = "document".equals(dc.getContainment()) ? $(GQuery.document)
            : $(GQuery.window);
        containment = new int[] {
            0 - relativeOffset.left - parentOffset.left,
            0 - relativeOffset.top - parentOffset.top,
            $containement.width() - helperDimension.getWidth() - margin.left,
            ($containement.height() != 0 ? $containement.height() : body
                .getParentElement().getScrollHeight())
                - helperDimension.height - margin.top };
        return;
      }

      GQuery $containement;
      if ("parent".equals(dc.getContainment())) {
        $containement = $(helper.get(0).getParentElement());
      } else {
        $containement = $(dc.getContainment());
      }

      Element ce = $containement.get(0);
      if (ce == null) {
        return;
      }
      Offset co = $containement.offset();
      boolean overflow = !$containement.css("overflow").equals("hidden");

      containment = new int[] {
          co.left + (int) GQUtils.cur(ce, "borderLeftWidth", false)
              + (int) GQUtils.cur(ce, "paddingLeft", false) - margin.left,
          co.top + (int) GQUtils.cur(ce, "borderTopWidth", false)
              + (int) GQUtils.cur(ce, "paddingTop", false) - margin.top,
          co.left
              + (overflow ? Math.max(ce.getScrollWidth(), ce.getOffsetWidth())
                  : ce.getOffsetWidth())
              - (int) GQUtils.cur(ce, "borderLeftWidth", false)
              - (int) GQUtils.cur(ce, "paddingRight", false)
              - helperDimension.width - margin.left,
          co.top
              + (overflow ? Math
                  .max(ce.getScrollHeight(), ce.getOffsetHeight()) : ce
                  .getOffsetHeight())
              - (int) GQUtils.cur(ce, "borderTopWidth", false)
              - (int) GQUtils.cur(ce, "paddingBottom", false)
              - helperDimension.height - margin.top };

    }

    private LeftTopDimension calculateParentOffset(Element element) {
      Offset position = helperOffsetParent.offset();

      if ("absolute".equals(helperCssPosition)
          && isOffsetParentIncludedInScrollParent()) {
        position.add(helperScrollParent.scrollLeft(), helperScrollParent
            .scrollTop());
      }

      if (impl.resetParentOffsetPosition(helperOffsetParent)) {
        position.left = 0;
        position.top = 0;
      }

      position.add((int) GQUtils.cur(helperOffsetParent.get(0),
          "borderLeftWidth", false), (int) GQUtils.cur(helperOffsetParent
          .get(0), "borderTopWidth", false));
      return new LeftTopDimension(position.left, position.top);

    }

    /*
     * This is a relative to absolute position minus the actual position
     * calculation - only used for relative positioned helper
     */
    private LeftTopDimension calculateRelativeHelperOffset(Element element) {
      if ("relative".equals(helperCssPosition)) {
        Offset position = $(element).position();
        //TODO : bug in JQuery if scroll parent is document.... (in firefox and cie) ...
        //This fix seems to work... investigate on IE
        GQuery scroll = (helperScrollParent.get(0) != document.cast() ? helperScrollParent : $(body));
        int top = (int) (position.top
            - GQUtils.cur(helper.get(0), "top", true) + scroll
            .scrollTop());
        int left = (int) (position.left
            - GQUtils.cur(helper.get(0), "left", true) + scroll
            .scrollLeft());
        return new LeftTopDimension(left, top);
      }
      return new LeftTopDimension(0, 0);
    }

    private LeftTopDimension generatePosition(Event e) {
      GQuery scroll = getScrollParent();
      boolean scrollIsRootNode = isRootNode(scroll.get(0));

      int pageX = pageX(e);
      int pageY = pageY(e);

      // test if calculate the initial position, if it's the case we don't have
      // to check the options
      if (originalPosition != null) {
        if (containment != null && containment.length == 4) {
          if (pageX(e) - offsetClick.left < containment[0]) {
            pageX = containment[0] + offsetClick.left;
          }
          if (pageY(e) - offsetClick.top < containment[1]) {
            pageY = containment[1] + offsetClick.top;
          }
          if (pageX(e) - offsetClick.left > containment[2]) {
            pageX = containment[2] + offsetClick.left;
          }
          if (pageY(e) - offsetClick.top > containment[3]) {
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
            pageY = !(isOutOfContainment0 || isOutOfContainment2) ? roundedLeft
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
          + ("fixed".equals(helperCssPosition) ? -helperScrollParent
              .scrollTop() : scrollIsRootNode ? 0 : scroll.scrollTop());
      int left = pageX
          - offsetClick.left
          - relativeOffset.left
          - parentOffset.left
          + ("fixed".equals(helperCssPosition) ? -helperScrollParent
              .scrollLeft() : scrollIsRootNode ? 0 : scroll.scrollLeft());

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

    private boolean isOffsetParentIncludedInScrollParent() {
      assert helperOffsetParent != null && helperScrollParent != null;
      return helperScrollParent.get(0) != document.cast()
          && contains(helperScrollParent.get(0), helperOffsetParent.get(0));
    }

    public boolean isRootNode(Element e) {
      return e == document.cast() || e == body;
    }

    public LeftTopDimension getMargin() {
      return margin;
    }

    public LeftTopDimension getOffset() {
      return offset;
    }

    public LeftTopDimension getAbsPosition() {
      return absPosition;
    }

    public LeftTopDimension getOffsetClick() {
      return offsetClick;
    }

    public LeftTopDimension getParentOffset() {
      return parentOffset;
    }

    public LeftTopDimension getRelativeOffset() {
      return relativeOffset;
    }

    public int getOriginalEventPageX() {
      return originalEventPageX;
    }

    public int getOriginalEventPageY() {
      return originalEventPageY;
    }

    public LeftTopDimension getPosition() {
      return position;
    }

    public LeftTopDimension getOriginalPosition() {
      return originalPosition;
    }

    public String getHelperCssPosition() {
      return helperCssPosition;
    }

    public GQuery getHelperScrollParent() {
      return helperScrollParent;
    }

    public GQuery getHelperOffsetParent() {
      return helperOffsetParent;
    }

    public int[] getContainment() {
      return containment;
    }

    public GQuery getHelper() {
      return helper;
    }

    public DraggableOptions getOptions() {
      return options;
    }

    public HelperDimension getHelperDimension() {
      return helperDimension;
    }
    
    public void setHelperDimension(HelperDimension helperDimension) {
      this.helperDimension = helperDimension;
    }
    
    public Draggable getDraggable() {
      return Draggable.this;
    }
  }

  /**
   * A POJO used to store the width/height values of an helper.
   */
   public static class HelperDimension {
    private int width = 0;
    private int height = 0;

    public HelperDimension(GQuery helper) {
      // TODO : check if border are really included in these dimensions -> ok for firefox and cie -> check on IE
      width = helper.get(0).getOffsetWidth();
      height = helper.get(0).getOffsetHeight();

      GWT.log("Width of the helper :" + width);
      GWT.log("Height of the helper :" + height);
    }

    public int getHeight() {
      return height;
    }

    public int getWidth() {
      return width;
    }

  }

  /**
   * A POJO used to store the top/left.
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

  public static final Class<Draggable> Draggable = Draggable.class;

  private static final String DRAGGABLE_KEY = "draggable";

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
  }

  public static void registerDraggablePlugin(DraggablePlugin plugin) {
    if (draggablePlugins == null) {
      draggablePlugins = new HashMap<String, DraggablePlugin>();
    }
    draggablePlugins.put(plugin.getName(), plugin);
  }

  DraggableOptions options;
  GQuery helper;
  DragOperationInfo dragOperationInfo;
  DraggableImpl impl = GWT.create(DraggableImpl.class);
  boolean cancelHelperRemoval = false;

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

  public Draggable draggable() {
    return draggable(new DraggableOptions(), null);
  }

  public Draggable draggable(DraggableOptions options) {
    return draggable(options, null);
  }

  public Draggable draggable(DraggableOptions options, HandlerManager eventBus) {
    this.options = options;
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
    }

    initMouseHandler(options);

    return this;
  }

  @Override
  protected String getPluginName() {
    return "draggable";
  }

  @Override
  protected boolean mouseCapture(Element draggable, Event event) {
    return helper == null && !options.isDisabled()
        && isHandleClicked(draggable, event);
  }

  @Override
  protected boolean mouseDrag(Element element, Event event) {
    return mouseDragImpl(element, event, false);
  }

  @Override
  protected boolean mouseStart(Element draggable, Event event) {
    createHelper(draggable, event);
    dragOperationInfo = new DragOperationInfo(helper, options);
    
    cacheHelperSize();
    
    if (getDragAndDropManager().isHandleDroppable()){
      getDragAndDropManager().setCurrentDraggable(draggable);
    }
    
    dragOperationInfo.initialize(draggable, event);

    callPlugins(new StartCaller(draggable, event));

    try {
      trigger(new DragStartEvent(draggable), options.getOnDragStart(),
          draggable);
    } catch (StopDragException e) {
      clear(draggable);
      return false;
    }

    cacheHelperSize();
    
    if (getDragAndDropManager().isHandleDroppable()){
      getDragAndDropManager().prepareOffset(draggable, event);
    }
    
    helper.addClass(CssClassNames.UI_DRAGGABLE_DRAGGING);

    mouseDragImpl(draggable, event, true);

    return true;
  }


  @Override
  protected boolean mouseStop(Element draggable, Event event) {
    
    boolean dropped = false;
    if (getDragAndDropManager().isHandleDroppable()){
      dropped = getDragAndDropManager().drop(draggable, event);
    }
    
    // TODO implement revert options
    callPlugins(new StopCaller(draggable, event));
    
    trigger(new DragStopEvent(draggable), options.getOnDragStop(), draggable);

    clear(draggable);

    return false;
  }

  private void cacheHelperSize() {
    if (helper != null) {
      dragOperationInfo.setHelperDimension(new HelperDimension(helper));
    }

  }

  private void callPlugins(PluginCaller caller) {
    for (DraggablePlugin plugin : draggablePlugins.values()){
      if (plugin.hasToBeExecuted(options)){
        caller.call(plugin);
      }
    }
  }
  
  private void clear(Element draggable) {
    helper.removeClass(CssClassNames.UI_DRAGGABLE_DRAGGING);
    if (helper.get(0) != draggable && !cancelHelperRemoval) {
      this.helper.remove();
    }
    helper = null;
    cancelHelperRemoval = false;

  }

  private void createHelper(Element draggable, Event e) {
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
  
  
  private boolean isHelperAttached(){
    //normally this test helper.parents().filter("body").length() == 0 is sufficient but they are a bug in gwtquery in filter function
    //return helper.parents().filter("body").length() == 0;
    GQuery parents = helper.parents();
    for (Element parent : parents.elements()){
      if (parent == body){
        return true;
      }
    }
    return false;
  }
  
  private DraggableDroppableManager getDragAndDropManager(){
    return DraggableDroppableManager.getInstance();
  }

  private boolean isHandleClicked(Element draggable, final Event event) {
    // if no handle or if specified handle is not inside the draggable element,
    // continue
    if (options.getHandle() == null
        || $(options.getHandle(), draggable).length() == 0) {
      return true;
    }

    // OK, we have a valid handle, check if we are clicking on the handle object
    // or one
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

  /**
   * implementation of mouse drag
   */
  private boolean mouseDragImpl(Element draggable, Event event,
      boolean noPropagation) {

    dragOperationInfo.regeneratePosition(event);
    dragOperationInfo.generateAbsPosition();

    if (!noPropagation) {
      
      callPlugins(new DragCaller(draggable, event));
      
      try {
        trigger(new DragEvent(draggable), options.getOnDrag(), draggable);
      } catch (StopDragException e) {
        mouseStop(draggable, event);
        return false;
      }
    }

    moveHelper(noPropagation);

    if (getDragAndDropManager().isHandleDroppable()){
      getDragAndDropManager().drag(draggable, event);
    }
    
    return false;
  }

  /**
   * 
   * @param firstTime if true, the helper has to be positionned without take care to the axis options
   */
  private void moveHelper(boolean firstTime) {
    AxisOption axis = options.getAxis();
    if (AxisOption.NONE == axis || AxisOption.X_AXIS == axis || firstTime) {
      helper.get(0).getStyle().setLeft(dragOperationInfo.position.getLeft(),
          Unit.PX);
    }
    if (AxisOption.NONE == axis || AxisOption.Y_AXIS == axis || firstTime) {
      helper.get(0).getStyle().setTop(dragOperationInfo.position.getTop(),
          Unit.PX);
    }
  }

  private native boolean positionIsFixedAbsoluteOrRelative(String position) /*-{
    return (/^(?:r|a|f)/).test(position);
  }-*/;

}
