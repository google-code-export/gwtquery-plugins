package gwtquery.plugins.selectable.client.event;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SelectingEvent extends GwtEvent<SelectingEvent.SelectingEventHandler> {

  public interface SelectingEventHandler extends EventHandler{
    public void onSelecting(SelectingEvent event);
  }
  
  public static Type<SelectingEventHandler> TYPE = new Type<SelectingEventHandler>();
  
  private Element selectingElement;
  
  public SelectingEvent(Element e) {
    selectingElement = e;
  }
  
  @Override
  protected void dispatch(SelectingEventHandler handler) {
    handler.onSelecting(this);
  }

  @Override
  public Type<SelectingEventHandler> getAssociatedType() {
    return TYPE;
  }
  
  public Element getSelectingElement() {
    return selectingElement;
  }

}
