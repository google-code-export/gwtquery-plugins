package gwtquery.plugins.draggable.client.events;


import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.query.client.GQuery;

import gwtquery.plugins.draggable.client.gwt.DraggableCell;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

public abstract class AbstractDraggableEvent<H extends EventHandler> extends
		GwtEvent<H> {
  public static final String VALUE_KEY = "__dragAndDropCellAssociatedValue"; 

  private Element draggable;
  private Element helper;

	public AbstractDraggableEvent() {
	
	}
	
	public AbstractDraggableEvent(Element draggable, Element helper) {
		this.draggable = draggable;
		this.helper=helper;
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
  @SuppressWarnings("unchecked")
  public <T> T getDraggableValue() {
    return (T) GQuery.$(getDraggable()).data(VALUE_KEY);
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
