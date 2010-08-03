package gwtquery.plugins.selectable.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SelectedEvent extends GwtEvent<SelectedEvent.SelectedEventHandler> {

  public interface SelectedEventHandler extends EventHandler{
    public void onSelected(SelectedEvent event);
  }
  
  public static Type<SelectedEventHandler> TYPE = new Type<SelectedEventHandler>();
  
  private Element selectedElement;
  
  public SelectedEvent(Element e) {
    selectedElement = e;
  }
  
  @Override
  protected void dispatch(SelectedEventHandler handler) {
    handler.onSelected(this);
  }

  @Override
  public Type<SelectedEventHandler> getAssociatedType() {
    return TYPE;
  }
  
  public Element getSelectedElement() {
    return selectedElement;
  }

}
