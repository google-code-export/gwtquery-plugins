package gwtquery.plugins.selectable.client.event;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class UnselectingEvent extends GwtEvent<UnselectingEvent.UnselectingEventHandler> {

  public interface UnselectingEventHandler extends EventHandler{
    public void onUnselecting(UnselectingEvent event);
  }
  
  public static Type<UnselectingEventHandler> TYPE = new Type<UnselectingEventHandler>();
  
  private Element unselectingElement;
  
  public UnselectingEvent(Element e) {
    unselectingElement = e;
  }
  
  @Override
  protected void dispatch(UnselectingEventHandler handler) {
    handler.onUnselecting(this);
  }

  @Override
  public Type<UnselectingEventHandler> getAssociatedType() {
    return TYPE;
  }
  
  public Element getUnselectingElement() {
    return unselectingElement;
  }

}
