package gwtquery.plugins.draggable.client.plugins;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.document;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.GQuery.Offset;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;

import gwtquery.plugins.commonui.client.GQueryUi;
import gwtquery.plugins.draggable.client.DraggableDroppableManager;
import gwtquery.plugins.draggable.client.DraggableHandler;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.AxisOption;

/**
 * This add-on handle scrolling of parent element.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 *
 */
public class ScrollPlugin implements DraggablePlugin {
  
  private static String OVERFLOW_OFFSET_KEY="overflowOffset";

  public String getName() {
    return "scroll";
  }

  public void onDrag(DraggableHandler handler, Element draggableElement, Event e) {
    DraggableOptions options = handler.getOptions();
    GQuery scrollParent = handler.getHelperScrollParent();
    Element scrollParentElement = scrollParent.get(0);
    if (scrollParentElement == null){
      return;
    }
    
    AxisOption axis = options.getAxis();
    Offset overflowOffset = $(draggableElement).data(OVERFLOW_OFFSET_KEY, Offset.class);
    int scrollSensitivity = options.getScrollSensitivity();
    int scrollSpeed = options.getScrollSpeed();
    
    boolean scrolled = false;
    
    if (scrollParentElement != null  && scrollParentElement != $(GQuery.document).get(0) && !"html".equalsIgnoreCase(scrollParentElement.getTagName())){
      if (AxisOption.NONE == axis || AxisOption.Y_AXIS == axis) {
        // test if we have to scroll down...
        if ((overflowOffset.top + scrollParentElement.getOffsetHeight()) - GQueryUi.pageY(e) < scrollSensitivity){ 
          scrollParentElement.setScrollTop(scrollParentElement.getScrollTop() + scrollSpeed);
          scrolled = true;
        //test if we have to scroll up...
        }else if (GQueryUi.pageY(e) - overflowOffset.top < scrollSensitivity){
          scrollParentElement.setScrollTop(scrollParentElement.getScrollTop() - scrollSpeed);
          scrolled = true;
        }
      }
      
      
      if (AxisOption.NONE == axis || AxisOption.X_AXIS == axis) {
        // test if we have to scroll left...
        if ((overflowOffset.left + scrollParentElement.getOffsetWidth()) - GQueryUi.pageX(e) < scrollSensitivity){ 
          scrollParentElement.setScrollLeft(scrollParentElement.getScrollLeft() + scrollSpeed);
          scrolled = true;
        //test if we have to scroll right...
        }else if (GQueryUi.pageX(e) - overflowOffset.left < scrollSensitivity){
          scrollParentElement.setScrollLeft(scrollParentElement.getScrollLeft() - scrollSpeed);
          scrolled = true;
        }
      }
      
    }else{
          if (AxisOption.NONE == axis || AxisOption.Y_AXIS == axis) {
        if (GQueryUi.pageY(e) -document.getScrollTop() <scrollSensitivity){
        	document.setScrollTop(document.getScrollTop() -scrollSpeed);
          scrolled = true;
        }else if (Window.getClientHeight()-(GQueryUi.pageY(e)-document.getScrollTop()) <scrollSensitivity){
          document.setScrollTop(document.getScrollTop() +scrollSpeed);
          scrolled = true;
        }
      }
      
      if (AxisOption.NONE == axis || AxisOption.X_AXIS == axis) {
        if (GQueryUi.pageX(e) -document.getScrollLeft() <scrollSensitivity){
          document.setScrollLeft(document.getScrollLeft() -scrollSpeed);
          scrolled = true;
        }else if (Window.getClientWidth() - (GQueryUi.pageX(e)-document.getScrollLeft()) <scrollSensitivity){
          document.setScrollLeft(document.getScrollLeft() +scrollSpeed);
          scrolled = true;
        }
      }
      
    }
    
   if(scrolled  && DraggableDroppableManager.getInstance().isHandleDroppable()){
	   DraggableDroppableManager.getInstance().prepareOffset(draggableElement, options, e);
   }
    
  }

  public void onStart(DraggableHandler handler, Element draggableElement, Event e) {
    
    GQuery scrollParent = handler.getHelperScrollParent();
    Element scrollParentElement = scrollParent.get(0);
    if (scrollParentElement != null  && scrollParentElement != $(GQuery.document).get(0) && !"html".equalsIgnoreCase(scrollParentElement.getTagName())){
      Offset scrollParentOffset = scrollParent.offset();
      $(draggableElement).data(OVERFLOW_OFFSET_KEY, scrollParentOffset);
    }
  }

  public void onStop(DraggableHandler handler, Element draggableElement, Event e) {
    $(draggableElement).removeData(OVERFLOW_OFFSET_KEY);

  }

  public boolean hasToBeExecuted(DraggableOptions options) {
    return options.isScroll();
  }

}
