package gwtquery.plugins.draggable.client.plugins;                                
                                                                                  
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQUtils;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.Draggable.DragOperationInfo;

import java.util.Arrays;
import java.util.Comparator;
                                                                                  
/**                                                                               
 * This add-on manage the z-index for the helper while being dragged.         
 *                                                                                
 * @author Julien Dramaix (julien.dramaix@gmail.com)                              
 *                                                                                
 */                                                                               
public class StackPlugin implements DraggablePlugin {                            
                                                                                  

	private class ZIndexComparator implements Comparator<Element>{
		
		public int compare(Element element1, Element element2) {
			int zIndex1 = (int)GQUtils.cur(element1, ZINDEX_CSS, false);
			int zIndex2 = (int) GQUtils.cur(element2, ZINDEX_CSS, false);
			return (zIndex1 - zIndex2);
			
		}
	}
	                            
  private static String ZINDEX_CSS = "zIndex";                        
                                                                                  
  public String getName() {                                                       
    return "stack";                                                              
  }                                                                               
                                                                                  
  public void onDrag(DragOperationInfo info, Element draggableElement, Event e) { 
  }                                                                               
                                                                                  
  public void onStart(DragOperationInfo info, Element draggableElement, Event e) {
		
		GQuery stackElements  = info.getOptions().getStack();
		Element[] sortedElementArray = stackElements.elements();
		Arrays.sort(sortedElementArray, new ZIndexComparator());
		
		if (sortedElementArray.length == 0){
			return;
		}
		
		int zIndexMin = (int)GQUtils.cur(sortedElementArray[0], "zIndex", false);
		int i = 0;
		for (Element el : sortedElementArray){
			el.getStyle().setZIndex(zIndexMin+i);
			i++;
		}
		
		info.getHelper().get(0).getStyle().setZIndex(zIndexMin+sortedElementArray.length);
    
  }                                                                               
                                                                                  
  public void onStop(DragOperationInfo info, Element draggableElement, Event e) { 
  }                                                                               
                                                                                  
  public boolean hasToBeExecuted(DraggableOptions options) {                      
    return options.getStack() != null && options.getStack().length() != 0;                                           
  }                                                                               
                                                                                  
}                                                                                 
                                                                                  