package gwtquery.plugins.droppable.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.user.client.Event.ONMOUSEDOWN;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;

import gwtquery.plugins.commonui.client.Event;
import gwtquery.plugins.commonui.client.GQueryUi.Dimension;
import gwtquery.plugins.draggable.client.DraggableDroppableManager;
import gwtquery.plugins.draggable.client.DraggableHandler;
import gwtquery.plugins.droppable.client.Droppable.CssClassNames;
import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DraggableDroppableManagerImpl extends DraggableDroppableManager {

  private Element currentDraggable;
  private Map<String, Collection<Element>> droppablesByScope;

  public DraggableDroppableManagerImpl() {
    // add default context
    droppablesByScope = new HashMap<String, Collection<Element>>();
    droppablesByScope.put("default", new ArrayList<Element>());
  }

  @Override
  public void drag(Element draggable, Event e) {
    DraggableHandler draggableHandler = DraggableHandler.getInstance(draggable);
    Collection<Element> droppables = getDroppablesByScope(draggableHandler.getOptions().getScope());
    if (droppables == null || droppables.size() == 0) {
      return;
    }
    
    for (Element droppable : droppables){
      DroppableHandler dropHandler = DroppableHandler.getInstance(droppable);
      dropHandler.drag(droppable, draggable, e);
      
    }
  }

  @Override
  public boolean drop(Element draggable, Event e) {
    boolean dropped = false;
    DraggableHandler draggableHandler = DraggableHandler.getInstance(draggable);
    Collection<Element> droppables = getDroppablesByScope(draggableHandler.getOptions().getScope());
    if (droppables == null || droppables.size() == 0) {
      return false;
    }
    
    for (Element droppable : droppables){
      DroppableHandler droppableHandler = DroppableHandler.getInstance(droppable);
      dropped |= droppableHandler.drop(droppable, draggable, e, dropped);
    }
    
    return dropped;
  }

  @Override
  public void prepareOffset(Element draggable, Event e) {
    DraggableHandler draggableHandler = DraggableHandler.getInstance(draggable);
    Collection<Element> droppables = getDroppablesByScope(draggableHandler.getOptions().getScope());
    if (droppables == null || droppables.size() == 0) {
      return;
    }
    
    GQuery droppablesInsideDraggable = $(draggable).find("."+CssClassNames.UI_DROPPABLE).andSelf();
    
    for (Element droppable : droppables) {
      GQuery $droppable = $(droppable);
      DroppableHandler droppableHandler = DroppableHandler.getInstance(droppable);
      droppableHandler.reset();
      DroppableOptions droppableOptions = droppableHandler.getOptions();
      AcceptFunction accept = droppableOptions.getAccept();
      if (droppableOptions.isDisabled() || (accept != null && !accept.acceptDrop(droppable, draggable))){
        continue;
      }
      
      boolean mustContinue = false;
      for (Element el : droppablesInsideDraggable.elements()){
        if (el == droppable){
          //droppableHandler.setDroppableDimension(new Dimension(0, 0));
          mustContinue = true;
          break;
        }
      }
      if (mustContinue){
        continue;
      }
      
      droppableHandler.setVisible(!"none".equals(droppable.getStyle().getDisplay()));

      if (droppableHandler.isVisible()){
        droppableHandler.setDroppableOffset($droppable.offset());
        droppableHandler.setDroppableDimension(new Dimension(droppable));
        if (e.getTypeInt() == ONMOUSEDOWN ){
          droppableHandler.activate(droppable,e);
        }
      }
      
      
    }

  }
  

  @Override
  public void setCurrentDraggable(Element draggable) {
    currentDraggable = draggable;
  }

  @Override
  public Element getCurrentDraggable() {
    return currentDraggable;
  }

  @Override
  public boolean isHandleDroppable() {
    return true;
  }

  @Override
  public Collection<Element> getDroppablesByScope(String scope) {
    return droppablesByScope.get(scope);
  }

  @Override
  public void setDroppable(Element droppable, String scope) {
    Collection<Element> droppables = droppablesByScope.get(scope);
    if (droppables == null) {
      droppables = new ArrayList<Element>();
      droppablesByScope.put(scope, droppables);
    }
    droppables.add(droppable);
  }
}

