package gwtquery.plugins.selectable.client.event;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SelectionStopEvent extends GwtEvent<SelectionStopEvent.SelectionStopEventHandler> {

  public interface SelectionStopEventHandler extends EventHandler{
    public void onSelectionStop(SelectionStopEvent event);
  }
  
  public static Type<SelectionStopEventHandler> TYPE = new Type<SelectionStopEventHandler>();
  
  private Element selectable;
  
  public SelectionStopEvent(Element e) {
    selectable = e;
  }
  
  @Override
  protected void dispatch(SelectionStopEventHandler handler) {
    handler.onSelectionStop(this);
  }

  @Override
  public Type<SelectionStopEventHandler> getAssociatedType() {
    return TYPE;
  }
  
  public Element getSelectable() {
    return selectable;
  }

}
