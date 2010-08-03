package gwtquery.plugins.selectable.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class UnselectedEvent extends GwtEvent<UnselectedEvent.UnselectedEventHandler> {

  public interface UnselectedEventHandler extends EventHandler{
    public void onUnselected(UnselectedEvent event);
  }
  
  public static Type<UnselectedEventHandler> TYPE = new Type<UnselectedEventHandler>();
  
  private Element unselectedElement;
  
  public UnselectedEvent(Element e) {
    unselectedElement = e;
  }
  
  @Override
  protected void dispatch(UnselectedEventHandler handler) {
    handler.onUnselected(this);
  }

  @Override
  public Type<UnselectedEventHandler> getAssociatedType() {
    return TYPE;
  }
  
  public Element getUnselectedElement() {
    return unselectedElement;
  }

}
