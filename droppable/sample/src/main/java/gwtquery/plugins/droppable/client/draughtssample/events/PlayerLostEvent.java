package gwtquery.plugins.droppable.client.draughtssample.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import gwtquery.plugins.droppable.client.draughtssample.GameController.Player;

public class PlayerLostEvent extends GwtEvent<PlayerLostEvent.PlayerLostEventHandler> {

  public interface PlayerLostEventHandler extends EventHandler {
    public void onPlayerLost(PlayerLostEvent event);
  }

  public static Type<PlayerLostEventHandler> TYPE = new Type<PlayerLostEventHandler>();

  private Player player;

  public PlayerLostEvent(Player player) {
    this.player = player;
  }

  @Override
  public Type<PlayerLostEventHandler> getAssociatedType() {
    return TYPE;
  }

  public Player getPlayer() {
    return player;
  }
  
  public void setPlayer(Player player) {
    this.player = player;
  }
  
  

  @Override
  protected void dispatch(PlayerLostEventHandler handler) {
    handler.onPlayerLost(this);
  }
}
