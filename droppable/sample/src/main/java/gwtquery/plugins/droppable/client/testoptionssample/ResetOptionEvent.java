package gwtquery.plugins.droppable.client.testoptionssample;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Widget;

public class ResetOptionEvent extends
    GwtEvent<ResetOptionEvent.ResetOptionEventHandler> {

  public interface ResetOptionEventHandler extends EventHandler {
    public void onResetOption(ResetOptionEvent event);
  }

  public static Type<ResetOptionEventHandler> TYPE = new Type<ResetOptionEventHandler>();

  private Widget optionsPanel;

  public ResetOptionEvent(Widget optionsPanel) {
    this.optionsPanel = optionsPanel;
  }

  @Override
  public Type<ResetOptionEventHandler> getAssociatedType() {
    return TYPE;
  }
  
  public Widget getOptionsPanel() {
    return optionsPanel;
  }

  @Override
  protected void dispatch(ResetOptionEventHandler handler) {
    handler.onResetOption(this);
  }

}
