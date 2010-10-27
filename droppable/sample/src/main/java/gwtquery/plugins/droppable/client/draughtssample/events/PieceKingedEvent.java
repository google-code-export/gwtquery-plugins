package gwtquery.plugins.droppable.client.draughtssample.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import gwtquery.plugins.droppable.client.draughtssample.Piece;

public class PieceKingedEvent extends
    GwtEvent<PieceKingedEvent.PieceKingedEventHandler> {

  public interface PieceKingedEventHandler extends EventHandler {
    public void onPieceKinged(PieceKingedEvent event);
  }

  public static Type<PieceKingedEventHandler> TYPE = new Type<PieceKingedEventHandler>();

  private Piece piece;

  public PieceKingedEvent(Piece piece) {
    this.piece = piece;
  }

  @Override
  public Type<PieceKingedEventHandler> getAssociatedType() {
    return TYPE;
  }

  public Piece getPiece() {
    return piece;
  }
  

  @Override
  protected void dispatch(PieceKingedEventHandler handler) {
    handler.onPieceKinged(this);
  }

}
