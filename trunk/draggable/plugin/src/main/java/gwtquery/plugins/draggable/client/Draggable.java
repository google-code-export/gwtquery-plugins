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
    private LeftTopDimension click;
    private LeftTopDimension parentOffset;
    private LeftTopDimension relativeOffset;
    
    //info from helper
    private String helperCssPosition; 
    private GQuery helperScrollParent;
    private GQuery helperOffsetParent;

    public void setMarginCache(Element element) {
      int marginLeft = (int) GQUtils.cur(element, "marginLeft", true);
      int marginTop = (int) GQUtils.cur(element, "marginTop", true);

      margin = new LeftTopDimension(marginLeft, marginTop);

    }

    public void registerValues(Element element, Event e) {
      helperCssPosition = helper.css("position");
      //TODO check if it's correct
      helperScrollParent = helper.as(GQueryUi).scrollParent();
      helperOffsetParent = helper.offsetParent();
      
      
      setMarginCache(element);
      
      absPosition = new LeftTopDimension(element.getOffsetLeft(), element
          .getOffsetTop());

      int offsetLeft = absPosition.getLeft() - margin.getLeft();
      int offsetTop = absPosition.getTop() - margin.getTop();
      offset = new LeftTopDimension(offsetLeft, offsetTop);

      click = new LeftTopDimension(pageX(e) - offsetLeft, pageY(e)
          - offsetTop);

      parentOffset = calculateParentOffset(element);
      relativeOffset = calculateRelativeOffset(element);

    }

    // This is a relative to absolute position minus the actual position
    // calculation - only used for relative positioned helper
    private LeftTopDimension calculateRelativeOffset(Element element) {
      if ("relative".equals(helperCssPosition)){
        Offset position = $(element).position();
        //TODO continue this
        /*return {
          top: p.top - (parseInt(this.helper.css("top"),10) || 0) + this.scrollParent.scrollTop(),
          left: p.left - (parseInt(this.helper.css("left"),10) || 0) + this.scrollParent.scrollLeft()
        };*/
      }
      return new LeftTopDimension(0, 0);
    }

    private LeftTopDimension calculateParentOffset(Element element) {
      // TODO Auto-generated method stub
      return null;
    }
    
    /*
     * _getParentOffset: function() {

    //Get the offsetParent and cache its position
    this.offsetParent = this.helper.offsetParent();
    var po = this.offsetParent.offset();

    // This is a special case where we need to modify a offset calculated on start, since the following happened:
    // 1. The position of the helper is absolute, so it's position is calculated based on the next positioned parent
    // 2. The actual offset parent is a child of the scroll parent, and the scroll parent isn't the document, which means that
    //    the scroll is included in the initial calculation of the offset of the parent, and never recalculated upon drag
    if(this.cssPosition == 'absolute' && this.scrollParent[0] != document && $.ui.contains(this.scrollParent[0], this.offsetParent[0])) {
      po.left += this.scrollParent.scrollLeft();
      po.top += this.scrollParent.scrollTop();
    }

    if((this.offsetParent[0] == document.body) //This needs to be actually done for all browsers, since pageX/pageY includes this information
    || (this.offsetParent[0].tagName && this.offsetParent[0].tagName.toLowerCase() == 'html' && $.browser.msie)) //Ugly IE fix
      po = { top: 0, left: 0 };

    return {
      top: po.top + (parseInt(this.offsetParent.css("borderTopWidth"),10) || 0),
      left: po.left + (parseInt(this.offsetParent.css("borderLeftWidth"),10) || 0)
    };

  },

     */
    
    
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
    dragOperationInfo.registerValues(draggable, event);
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
