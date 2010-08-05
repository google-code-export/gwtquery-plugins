package gwtquery.plugins.selectable.client.event;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SelectionStartEvent extends GwtEvent<SelectionStartEvent.SelectionStartEventHandler> {

  public interface SelectionStartEventHandler extends EventHandler{
    public void onSelectionStart(SelectionStartEvent event);
  }
  
  public static Type<SelectionStartEventHandler> TYPE = new Type<SelectionStartEventHandler>();
  
  private Element selectable;
  
  public SelectionStartEvent(Element e) {
    selectable = e;
  }
  
  @Override
  protected void dispatch(SelectionStartEventHandler handler) {
    handler.onSelectionStart(this);
  }

  @Override
  public Type<SelectionStartEventHandler> getAssociatedType() {
    return TYPE;
  }
  
  public Element getSelectable() {
    return selectable;
  }

}
