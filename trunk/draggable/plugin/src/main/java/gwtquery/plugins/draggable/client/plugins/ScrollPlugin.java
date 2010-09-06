package gwtquery.plugins.draggable.client.plugins;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.GQuery.Offset;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;

import gwtquery.plugins.commonui.client.GQueryUi;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.Draggable.DragOperationInfo;
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

  public void onDrag(DragOperationInfo info, Element draggableElement, Event e) {
    DraggableOptions options = info.getOptions();
    GQuery scrollParent = info.getHelperScrollParent();
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
      GQuery $document =  $(GQuery.document);
      
      if (AxisOption.NONE == axis || AxisOption.Y_AXIS == axis) {
        if (GQueryUi.pageY(e) -$document.scrollTop() <scrollSensitivity){
          $document.scrollTop($document.scrollTop() -scrollSpeed);
          scrolled = true;
        }else if (Window.getClientHeight()-(GQueryUi.pageY(e)-$document.scrollTop()) <scrollSensitivity){
          $document.scrollTop($document.scrollTop() +scrollSpeed);
          scrolled = true;
        }
      }
      
      if (AxisOption.NONE == axis || AxisOption.X_AXIS == axis) {
        if (GQueryUi.pageX(e) -$document.scrollLeft() <scrollSensitivity){
          $document.scrollLeft($document.scrollLeft() -scrollSpeed);
          scrolled = true;
        }else if (Window.getClientWidth() - (GQueryUi.pageX(e)-$document.scrollLeft()) <scrollSensitivity){
          $document.scrollLeft($document.scrollLeft() +scrollSpeed);
          scrolled = true;
        }
      }
      
    }
    
    // TODO
    /*if(scrolled  && $.ui.ddmanager && !o.dropBehaviour)
      $.ui.ddmanager.prepareOffsets(i, event);*/
    
  }

  public void onStart(DragOperationInfo info, Element draggableElement, Event e) {
    
    GQuery scrollParent = info.getHelperScrollParent();
    Element scrollParentElement = scrollParent.get(0);
    if (scrollParentElement != null  && scrollParentElement != $(GQuery.document).get(0) && !"html".equalsIgnoreCase(scrollParentElement.getTagName())){
      Offset scrollParentOffset = scrollParent.offset();
      $(draggableElement).data(OVERFLOW_OFFSET_KEY, scrollParentOffset);
    }
  }

  public void onStop(DragOperationInfo info, Element draggableElement, Event e) {
    $(draggableElement).removeData(OVERFLOW_OFFSET_KEY);

  }

  public boolean hasToBeExecuted(DraggableOptions options) {
    return options.isScroll();
  }

}
