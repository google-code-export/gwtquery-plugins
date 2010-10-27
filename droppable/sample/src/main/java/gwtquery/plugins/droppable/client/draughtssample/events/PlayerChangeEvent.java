package gwtquery.plugins.droppable.client.draughtssample.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import gwtquery.plugins.droppable.client.draughtssample.GameController.Player;

public class PlayerChangeEvent extends
    GwtEvent<PlayerChangeEvent.PlayerChangeEventHandler> {

  public interface PlayerChangeEventHandler extends EventHandler {
    public void onPlayerChange(PlayerChangeEvent event);
  }

  public static Type<PlayerChangeEventHandler> TYPE = new Type<PlayerChangeEventHandler>();

  private Player player;

  public PlayerChangeEvent(Player player) {
    this.player = player;
  }

  @Override
  public Type<PlayerChangeEventHandler> getAssociatedType() {
    return TYPE;
  }

  public Player getPlayer() {
    return player;
  }
  
  public void setPlayer(Player player) {
    this.player = player;
  }
  
  

  @Override
  protected void dispatch(PlayerChangeEventHandler handler) {
    handler.onPlayerChange(this);
  }

}
