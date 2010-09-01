package gwtquery.plugins.draggable.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.query.client.GQUtils;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.query.client.Plugin;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.commonui.client.MouseHandler;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.draggable.client.impl.DraggableImpl;

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
  
  /**
   * A POJO used to store the width/height values of an helper.
   */
  private static class HelperDimension{
    private int width = 0;
    private int height = 0;
    
    public HelperDimension(GQuery helper) {
      //TODO : check if border are really included in these dimensions
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
  private static class LeftTopDimension {
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

  /**
   * A POJO used to store all values we need to keep during drag operation
   */
  private class DragOperationInfo {
    
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
    
    //info from helper
    private String helperCssPosition; 
    private GQuery helperScrollParent;
    private GQuery helperOffsetParent;
    private int[] containment;

    public void setMarginCache(Element element) {
      int marginLeft = (int) GQUtils.cur(element, "marginLeft", true);
      int marginTop = (int) GQUtils.cur(element, "marginTop", true);

      margin = new LeftTopDimension(marginLeft, marginTop);

    }

    public void initialize(Element element, Event e) {
      helperCssPosition = helper.css("position");
      helperScrollParent = helper.as(GQueryUi).scrollParent();
      helperOffsetParent = helper.offsetParent();
      
      setMarginCache(element);
      
      absPosition = new LeftTopDimension(element.getOffsetLeft(), element
          .getOffsetTop());

      offset = new LeftTopDimension(absPosition.getLeft() - margin.getLeft(), absPosition.getTop() - margin.getTop());

      offsetClick = new LeftTopDimension(pageX(e) - offset.left, pageY(e)
          - offset.top);

      parentOffset = calculateParentOffset(element);
      relativeOffset = calculateRelativeHelperOffset(element);

      originalEventPageX = pageX(e);
      originalEventPageY = pageY(e);
      
      position =  generatePosition(e);
      originalPosition = new LeftTopDimension(position.left, position.top);
      if (options.getContainment() != null){
        containment = options.getContainment().calculate();
      }
      
    }
    
   

    private boolean isOffsetParentIncludedInScrollParent(){
      assert helperOffsetParent != null && helperScrollParent != null;
      return helperScrollParent.get(0) != $(document).get(0) && contains(helperScrollParent.get(0), helperOffsetParent.get(0));
    }

    public void regeneratePosition(Event e){
      position = generatePosition(e);
      
    }
    
    private LeftTopDimension generatePosition(Event e) {
      GQuery scroll;

      if("absolute".equals(helperCssPosition) && !(isOffsetParentIncludedInScrollParent()) ) {
       scroll = helperOffsetParent;
      }else{
        scroll = helperScrollParent;
      }
      
      String scrollTagName = scroll.get(0).getTagName();
      boolean scrollIsRootNode = "html".equalsIgnoreCase(scrollTagName) || "body".equalsIgnoreCase(scrollTagName);
      int pageX = pageX(e);
      int pageY = pageY(e);
      
      if (originalPosition != null){ // test if don't calculate the initial position
        //TODO check options
      }
      
      int top = pageY
        -offsetClick.top
        -relativeOffset.top
        -parentOffset.top
        + ("fixed".equals(helperCssPosition) ? -helperScrollParent.scrollTop() : scrollIsRootNode ? 0 : scroll.scrollTop()); 
      int left = pageX
        -offsetClick.left
        -relativeOffset.left
        -parentOffset.left
        + ("fixed".equals(helperCssPosition) ? -helperScrollParent.scrollLeft() : scrollIsRootNode ? 0 : scroll.scrollLeft()); 

      return null;
    }
    


    /*
     *  This is a relative to absolute position minus the actual position
     *  calculation - only used for relative positioned helper
     */
    private LeftTopDimension calculateRelativeHelperOffset(Element element) {
      if ("relative".equals(helperCssPosition)){
        Offset position = $(element).position();
        int top = (int) (position.top - GQUtils.cur(helper.get(0), "top", false)+helperScrollParent.scrollTop());
        int left = (int) (position.left - GQUtils.cur(helper.get(0), "left", false)+ helperScrollParent.scrollLeft());
        return new LeftTopDimension(left, top);
      }
      return new LeftTopDimension(0, 0);
    }

    private LeftTopDimension calculateParentOffset(Element element) {
      Offset position = helperOffsetParent.offset();
      
      if("absolute".equals(helperCssPosition) && isOffsetParentIncludedInScrollParent()) {
        position.add(helperScrollParent.scrollLeft(),helperScrollParent.scrollTop());
      }
      
      if  (impl.resetParentOffsetPosition(helperOffsetParent)){
        position.left = 0;
        position.top = 0;
      }
      
      position.add((int)GQUtils.cur(helperOffsetParent.get(0), "borderLeftWidth", false), (int)GQUtils.cur(helperOffsetParent.get(0), "borderTopWidth", false));
      return new LeftTopDimension(position.left, position.top);
          
    }
    
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
  private HelperDimension helperDimension;
  private DragOperationInfo dragOperationInfo;
  private DraggableImpl impl = GWT.create(DraggableImpl.class);

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
    cacheHelperSize();
    
    dragOperationInfo = new DragOperationInfo();
    dragOperationInfo.initialize(draggable, event);
   
    //TODO continue
    
    return false;
  }

 

  @Override
  protected boolean mouseStop(Element draggable, Event event) {
    // TODO Auto-generated method stub
    return false;
  }

  
  private void cacheHelperSize() {
    if (helper != null){
      helperDimension = new HelperDimension(helper);
    }
    
  }
  
  
  private void createHelper(Element draggable, Event e) {
    helper = options.getHelperType().createHelper(draggable,
        options.getHelper());

    if (helper.parents("body").length() == 0) {
      if ("parent".equals(options.getAppendTo()) ){
        helper.appendTo(draggable.getParentNode());
      }else{
        helper.appendTo(options.getAppendTo());
      }
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

    // OK, we have a valid handle, check if we are clicking on the handle object or one
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

  private native boolean positionIsFixedAbsoluteOrRelative(String position) /*-{
    return (/^(?:r|a|f)/).test(position);
  }-*/;

}
