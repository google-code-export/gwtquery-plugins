package gwtquery.plugins.draggable.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;

public class DraggableDroppableManager {

  private static DraggableDroppableManager INSTANCE = GWT.create(DraggableDroppableManager.class);
  
  public static DraggableDroppableManager getInstance(){
    return INSTANCE;
  }
  
  public void drag(Element draggable, Event e){};
  public boolean drop(Element draggable, Event e){
    return false;
  };
  public void prepareOffset(Element draggable, Event e){};
  
  
  public boolean isHandleDroppable(){
    return false;
  }

  public void setCurrentDraggable(Element draggable) {}
  public Element getCurrentDraggable() {
    return null;
  }
  
  
}
