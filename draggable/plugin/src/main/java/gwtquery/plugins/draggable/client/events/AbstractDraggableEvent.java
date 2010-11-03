package gwtquery.plugins.draggable.client.events;


import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

public abstract class AbstractDraggableEvent<H extends EventHandler> extends
		GwtEvent<H> {

  private Element draggable;
  private Element helper;

	public AbstractDraggableEvent() {
	
	}
	
	public AbstractDraggableEvent(Element draggable, Element helper) {
		this.draggable = draggable;
		this.helper=helper;
	}
	

	
	public DraggableWidget<?> getDraggableWidget(){
    if (getDraggable() != null){
      return DraggableWidget.get(getDraggable());
    }
    return null;
  }
	
	 public Element getDraggable() {
	    return draggable;
	  }
	  
	  public Element getHelper() {
	    return helper;
	  }
}
