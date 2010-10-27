package gwtquery.plugins.droppable.client.draughtssample.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import gwtquery.plugins.droppable.client.draughtssample.Piece;

public class PieceEatedEvent extends
    GwtEvent<PieceEatedEvent.PieceEatedEventHandler> {

  public interface PieceEatedEventHandler extends EventHandler {
    public void onPieceEated(PieceEatedEvent event);
  }

  public static Type<PieceEatedEventHandler> TYPE = new Type<PieceEatedEventHandler>();

  private Piece piece;

  public PieceEatedEvent(Piece piece) {
    this.piece = piece;
  }

  @Override
  public Type<PieceEatedEventHandler> getAssociatedType() {
    return TYPE;
  }

  public Piece getPiece() {
    return piece;
  }
  

  @Override
  protected void dispatch(PieceEatedEventHandler handler) {
    handler.onPieceEated(this);
  }

}
