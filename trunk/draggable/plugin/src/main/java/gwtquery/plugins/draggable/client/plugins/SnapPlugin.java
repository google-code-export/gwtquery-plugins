package gwtquery.plugins.draggable.client.plugins;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQUtils;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.GQuery.Offset;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.draggable.client.DraggableHandler;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableHandler.LeftTopDimension;
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
    
    int helperLeft = handler.getAbsPosition().getLeft();
    int helperRight = helperLeft + handler.getHelperDimension().getWidth();
    int helperTop = handler.getAbsPosition().getTop();
    int helperBottom = helperTop + handler.getHelperDimension().getHeight();
    
    for (SnapElement snapElement: snapElements){
      int snapElementLeft = snapElement.getOffset().left;
      int snapElementRight = snapElementLeft + snapElement.getWidth();
      int snapElementTop = snapElement.getOffset().top;
      int snapElementBottom = snapElementTop + snapElement.getHeight();
        
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
      LeftTopDimension newTopDimension = null;
      LeftTopDimension newLeftDimension = null;
      if (SnapMode.INNER != snapMode){
        boolean snapTop = Math.abs(snapElementTop - helperBottom) <= snapTolerance;
        boolean snapBottom = Math.abs(snapElementBottom - helperTop) <= snapTolerance;
        boolean snapLeft = Math.abs(snapElementLeft - helperRight) <= snapTolerance;
        boolean snapRight = Math.abs(snapElementRight - helperLeft) <= snapTolerance;
        
        
        if (snapTop){
          newTopDimension = handler.convertPositionTo(false, new LeftTopDimension(0, snapElementTop - handler.getHelperDimension().getHeight()));
        }else if (snapBottom){
          newTopDimension = handler.convertPositionTo(false, new LeftTopDimension(0, snapElementBottom));
        }
       
        if(snapLeft){
          newLeftDimension = handler.convertPositionTo(false, new LeftTopDimension(snapElementLeft - handler.getHelperDimension().getWidth(), 0)); 
        }else if (snapRight){
          newLeftDimension = handler.convertPositionTo(false, new LeftTopDimension(snapElementRight, 0));
        }
      }
      
      if (SnapMode.OUTER != snapMode){
        boolean snapTop = Math.abs(snapElementTop - helperTop) <= snapTolerance;
        boolean snapBottom = Math.abs(snapElementBottom - helperBottom) <= snapTolerance;
        boolean snapLeft = Math.abs(snapElementLeft - helperLeft) <= snapTolerance;
        boolean snapRight = Math.abs(snapElementRight - helperRight) <= snapTolerance;
        
        if (snapTop){
          newTopDimension = handler.convertPositionTo(false, new LeftTopDimension(0, snapElementTop));
        }else if (snapBottom){
          newTopDimension = handler.convertPositionTo(false, new LeftTopDimension(0, snapElementBottom - handler.getHelperDimension().getHeight()));
        }
        
        if(snapLeft){
          newLeftDimension = handler.convertPositionTo(false, new LeftTopDimension(snapElementLeft, 0));
         }else if (snapRight){
           newLeftDimension = handler.convertPositionTo(false, new LeftTopDimension(snapElementRight - handler.getHelperDimension().getWidth(), 0));
        }
      }
      
      if (newTopDimension != null){
        int newTop = newTopDimension.getTop() - handler.getMargin().getTop();
        int newLeft = handler.getPosition().getLeft();
        handler.setPosition(new LeftTopDimension(newLeft, newTop));

      }
      
      if (newLeftDimension != null){
        int newTop = handler.getPosition().getTop();
        int newLeft = newLeftDimension.getLeft() - handler.getMargin().getLeft();
        handler.setPosition(new LeftTopDimension(newLeft, newTop));

      }
      
    }
  }

  public void onStart(DraggableHandler handler, Element draggableElement, Event e) {
    List<SnapElement> snapElements = new ArrayList<SnapElement>();
    GQuery snap = (handler.getOptions().getSnap_$() != null ? handler.getOptions().getSnap_$() : $(handler.getOptions().getSnap()));
 
    for (Element element : snap.elements()){
      if (element != draggableElement){
        //TODO outerWidth and outerHeight don't exist in GQuery ... Add an issue
        int outerWidth = element.getClientWidth()+(int)GQUtils.cur(element, "borderLeftWidth", true) + (int)GQUtils.cur(element, "borderRightWidth", true);
        int outerHeight = element.getClientHeight()+(int)GQUtils.cur(element, "borderTopWidth", true) + (int)GQUtils.cur(element, "borderBottomWidth", true);
        GWT.log("snapelement data :outerWidth("+outerWidth+") outerHeight("+outerHeight+") element.getClientHeight()("+element.getClientHeight()+") element.getClientWidth()("+element.getClientWidth()+")");
        snapElements.add(new SnapElement($(element).offset(), outerWidth, outerHeight));
      }
    }
    $(draggableElement).data(SNAP_ELEMENTS_KEY, snapElements);

  }

  public void onStop(DraggableHandler handler, Element draggableElement, Event e) {
    //nothing to do
  }

}
