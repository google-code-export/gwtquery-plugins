package gwtquery.plugins.draggable.client.events;


import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.query.client.GQuery;

import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

public abstract class AbstractDraggableEvent<H extends EventHandler> extends
		GwtEvent<H> {
  
  private DragContext context;


	public AbstractDraggableEvent() {
	
	}
	
	public AbstractDraggableEvent(Element draggable) {
		context = new DragContext(draggable); 
	}
	
  /**
   * Return the data value associated to the current draggable element if and
   * only if this one is coming from a {@link DraggableCell} This method returns
   * null if the current drag operation doesn't concerns a
   * {@link DraggableCell}
   * 
   * @param <T>
   *          the class of the data
   * @return
   */
  public <T> T getDraggableData() {
    assert context != null : "Drag context cannot be null";
    return context.getDraggableData();
  }
  
  public DraggableWidget<?> getDraggableWidget(){
    assert context != null : "Drag context cannot be null";
    return context.getDraggableWidget();
  }
	
	 public Element getDraggable() {
	    assert context != null : "Drag context cannot be null";
	    return context.getDraggable();
	  }
	  
	  public Element getHelper() {
	    assert context != null : "Drag context cannot be null";
      return context.getHelper();
	  }
}
