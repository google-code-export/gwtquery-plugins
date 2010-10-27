package gwtquery.plugins.droppable.client.draughtssample.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import gwtquery.plugins.droppable.client.draughtssample.Piece;
import gwtquery.plugins.droppable.client.draughtssample.GameController.Position;

public class PieceMoveEvent extends
    GwtEvent<PieceMoveEvent.PieceMoveEventHandler> {

  public interface PieceMoveEventHandler extends EventHandler {
    public void onPieceMove(PieceMoveEvent event);
  }

  public static Type<PieceMoveEventHandler> TYPE = new Type<PieceMoveEventHandler>();

  private Piece piece;
  private Position newPosition;
  private Position oldPosition;

  public PieceMoveEvent(Piece piece, Position newPosition, Position oldPosition) {
    this.piece = piece;
    this.newPosition = newPosition;
    this.oldPosition = oldPosition;
  }

  @Override
  public Type<PieceMoveEventHandler> getAssociatedType() {
    return TYPE;
  }

  public Piece getPiece() {
    return piece;
  }
  public Position getNewPosition() {
    return newPosition;
  }
  
  public Position getOldPosition() {
    return oldPosition;
  }
  

  @Override
  protected void dispatch(PieceMoveEventHandler handler) {
    handler.onPieceMove(this);
  }

}
