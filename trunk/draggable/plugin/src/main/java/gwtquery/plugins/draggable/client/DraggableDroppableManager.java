package gwtquery.plugins.draggable.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;

import java.util.Collection;

public class DraggableDroppableManager {

  private static DraggableDroppableManager INSTANCE = GWT
      .create(DraggableDroppableManager.class);

  public static DraggableDroppableManager getInstance() {
    return INSTANCE;
  }

  public void drag(Element draggable, DraggableOptions options, Event e) {
  };

  public boolean drop(Element draggable, DraggableOptions options, Event e) {
    return false;
  };

  public void prepareOffset(Element draggable, DraggableOptions options, Event e) {
  };

  public boolean isHandleDroppable() {
    return false;
  }

  public void setCurrentDraggable(Element draggable) {
  }

  public Element getCurrentDraggable() {
    return null;
  }
  
  public Collection<Element> getDroppablesByScope(String scope){
    return null;
  }

  public void setDroppable(Element droppable, String scope) {
  }

}
