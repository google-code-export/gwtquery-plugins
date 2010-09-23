package gwtquery.plugins.draggable.client.plugins;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery.Offset;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.draggable.client.DraggableHandler;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.SnapMode;

import java.util.ArrayList;
import java.util.List;

public class SnapPlugin implements DraggablePlugin {
  
  private static class SnapElement{
    
    Offset offset;
    int width;
    int height;
   
    
    public SnapElement(Offset o, int width, int height) {
       offset = o;
       this.width = width;
       this.height = height;
    }
    
    public Offset getOffset() {
      return offset;
    }
    
    public int getWidth() {
      return width;
    }
    
    public int getHeight() {
      return height;
    }
    
        
  }

  private static String SNAP_ELEMENTS_KEY="snapElements";
  
  public String getName() {
    return "snap";
  }

  public boolean hasToBeExecuted(DraggableOptions options) {
    return options.isSnap();
  }

  @SuppressWarnings("unchecked")
  public void onDrag(DraggableHandler handler, Element draggableElement, Event e) {
    
    List<SnapElement> snapElements = $(draggableElement).data(SNAP_ELEMENTS_KEY, ArrayList.class);
    
    int snapTolerance = handler.getOptions().getSnapTolerance();
    SnapMode snapMode = handler.getOptions().getSnapMode();
    
    //TODO check if it's not better to use absPosition ... (offset is never updated)
    int helperLeft = handler.getOffset().getLeft();
    int helperRight = helperLeft + handler.getHelperDimension().getWidth();
    int helperTop = handler.getOffset().getTop();
    int helperBottom = helperTop + handler.getHelperDimension().getHeight();
    
    for (SnapElement snapElement: snapElements){
      int snapElementLeft = snapElement.offset.left;
      int snapElementRight = snapElementLeft + snapElement.getWidth();
      int snapElementTop = snapElement.offset.top;
      int snapElementBottom = snapElementLeft + snapElement.getHeight();
      
      if(!( (snapElementLeft-snapTolerance < helperLeft && helperLeft < snapElementRight+snapTolerance && snapElementTop-snapTolerance < helperTop && helperTop < snapElementBottom+snapTolerance) || 
          (snapElementLeft-snapTolerance < helperLeft && helperLeft < snapElementRight+snapTolerance && snapElementTop-snapTolerance < helperBottom && helperBottom < snapElementBottom+snapTolerance) || 
          (snapElementLeft-snapTolerance < helperRight && helperRight < snapElementRight+snapTolerance && snapElementTop-snapTolerance < helperTop && helperTop < snapElementBottom+snapTolerance) || 
          (snapElementLeft-snapTolerance < helperRight && helperRight < snapElementRight+snapTolerance && snapElementTop-snapTolerance < helperBottom && helperBottom < snapElementBottom+snapTolerance))) {
        //no snapping !!
        /*if (snapElement.isSnapping()){
          //TODO trigger event sanpReleaseEvent...
          //handler.triggerEvent()
          snapElement.setSnapping(false);
          continue;
        }*/
        continue;
      }
      if (SnapMode.INNER != snapMode){
        boolean snapTop = Math.abs(snapElementTop - helperBottom) <= snapTolerance;
        boolean snapBottom = Math.abs(snapElementBottom - helperTop) <= snapTolerance;
        boolean snapLeft = Math.abs(snapElementLeft - helperRight) <= snapTolerance;
        boolean snapRight = Math.abs(snapElementRight - helperLeft) <= snapTolerance;
        if (snapTop){
          //ui.position.top = inst._convertPositionTo("relative", { top: t - inst.helperProportions.height, left: 0 }).top - inst.margins.top;
        }else if (snapBottom){
          //ui.position.top = inst._convertPositionTo("relative", { top: b, left: 0 }).top - inst.margins.top;
        }
        
        if(snapLeft){
         //ui.position.left = inst._convertPositionTo("relative", { top: 0, left: l - inst.helperProportions.width }).left - inst.margins.left; 
        }else if (snapRight){
          //ui.position.left = inst._convertPositionTo("relative", { top: 0, left: r }).left - inst.margins.left;
        }
      }
      
      if (SnapMode.OUTER != snapMode){
        boolean snapTop = Math.abs(snapElementTop - helperTop) <= snapTolerance;
        boolean snapBottom = Math.abs(snapElementBottom - helperBottom) <= snapTolerance;
        boolean snapLeft = Math.abs(snapElementLeft - helperLeft) <= snapTolerance;
        boolean snapRight = Math.abs(snapElementRight - helperRight) <= snapTolerance;
        
        if (snapTop){
          //ui.position.top = inst._convertPositionTo("relative", { top: t, left: 0 }).top - inst.margins.top;
        }else if (snapBottom){
          //ui.position.top = inst._convertPositionTo("relative", { top: b - inst.helperProportions.height, left: 0 }).top - inst.margins.top;
        }
        
        if(snapLeft){
            //ui.position.left = inst._convertPositionTo("relative", { top: 0, left: l }).left - inst.margins.left;
         }else if (snapRight){
          //ui.position.left = inst._convertPositionTo("relative", { top: 0, left: r - inst.helperProportions.width }).left - inst.margins.left;
        }
      }
      
    }
  }

  public void onStart(DraggableHandler handler, Element draggableElement, Event e) {
    List<SnapElement> snapElements = new ArrayList<SnapElement>();
    
    for (Element element : handler.getOptions().getSnap().elements()){
      if (element != draggableElement){
        //TODO outerWidth and outerHeight don't exist in GQuery ... use offsetWidth and offsetHeight of GWT
        snapElements.add(new SnapElement($(element).offset(), element.getOffsetWidth(), element.getOffsetHeight()));
      }
    }
    $(draggableElement).data(SNAP_ELEMENTS_KEY, snapElements);

  }

  public void onStop(DraggableHandler handler, Element draggableElement, Event e) {
    //nothing to do
  }

}
