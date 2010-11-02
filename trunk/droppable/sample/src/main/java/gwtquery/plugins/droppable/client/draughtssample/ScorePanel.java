package gwtquery.plugins.droppable.client.draughtssample;

import static gwtquery.plugins.droppable.client.draughtssample.CheckerBoard.PIECE_NUMBER;
import static gwtquery.plugins.droppable.client.draughtssample.DraughtsSample.EVENT_BUS;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.droppable.client.draughtssample.GameController.Player;
import gwtquery.plugins.droppable.client.draughtssample.events.PieceEatedEvent;
import gwtquery.plugins.droppable.client.draughtssample.events.PieceKingedEvent;
import gwtquery.plugins.droppable.client.draughtssample.events.PlayerChangeEvent;
import gwtquery.plugins.droppable.client.draughtssample.events.PlayerLostEvent;
import gwtquery.plugins.droppable.client.draughtssample.events.PieceEatedEvent.PieceEatedEventHandler;
import gwtquery.plugins.droppable.client.draughtssample.events.PieceKingedEvent.PieceKingedEventHandler;
import gwtquery.plugins.droppable.client.draughtssample.events.PlayerChangeEvent.PlayerChangeEventHandler;
import gwtquery.plugins.droppable.client.draughtssample.events.PlayerLostEvent.PlayerLostEventHandler;

public class ScorePanel extends Composite implements PlayerChangeEventHandler, PieceEatedEventHandler, PieceKingedEventHandler, PlayerLostEventHandler{
 
  @UiField
  Label infoMessage;
  
  @UiField
  Label whitePieceNbrLabel;
  @UiField
  Label redPieceNbrLabel;
  @UiField
  Label whiteKingNbrLabel;
  @UiField
  Label redKingNbrLabel;
  @UiField
  Button restartButton;
  
  private int whitePieceNbr;
  private int redPieceNbr;
  private int whiteKingNbr;
  private int redKingNbr;
  
  
	private static ScorePanelUiBinder uiBinder = GWT
			.create(ScorePanelUiBinder.class);

	interface ScorePanelUiBinder extends UiBinder<Widget, ScorePanel> {
	}

	public ScorePanel() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		bind();
		
	}

  private void bind() {
    EVENT_BUS.addHandler(PlayerChangeEvent.TYPE, this);
    EVENT_BUS.addHandler(PieceEatedEvent.TYPE, this);
    EVENT_BUS.addHandler(PieceKingedEvent.TYPE, this);
    EVENT_BUS.addHandler(PlayerLostEvent.TYPE, this);
  }

  private void init() {
    whitePieceNbr = PIECE_NUMBER;
    redPieceNbr = PIECE_NUMBER;
    whiteKingNbr = 0;
    redKingNbr = 0;
    redKingNbrLabel.setText(""+redKingNbr);
    redPieceNbrLabel.setText(""+redPieceNbr);
    whiteKingNbrLabel.setText(""+whiteKingNbr);
    whitePieceNbrLabel.setText(""+whitePieceNbr);
    
  }

  public void onPlayerChange(PlayerChangeEvent event) {
    infoMessage.setText(event.getPlayer()+" player is playing");
  }

  public void onPieceEated(PieceEatedEvent event) {
   Piece eatedPiece=event.getPiece();
   if (eatedPiece.getPlayer() == Player.RED){
     if (eatedPiece.isKing()){
       redKingNbr --;
       redKingNbrLabel.setText(""+redKingNbr);
     }else{
       redPieceNbr --;
       redPieceNbrLabel.setText(""+redPieceNbr);
     }
   }else{
     if (eatedPiece.isKing()){
       whiteKingNbr --;
       whiteKingNbrLabel.setText(""+whiteKingNbr);
     }else{
       whitePieceNbr --;
       whitePieceNbrLabel.setText(""+whitePieceNbr);
     }
     
   }
    
  }

  public void onPieceKinged(PieceKingedEvent event) {
    if (event.getPiece().getPlayer() == Player.RED){
      redKingNbr++;
      redPieceNbr--;
      redKingNbrLabel.setText(""+redKingNbr);
      redPieceNbrLabel.setText(""+redPieceNbr);
    }else{
      whiteKingNbr++;
      whitePieceNbr--;
      whiteKingNbrLabel.setText(""+whiteKingNbr);
      whitePieceNbrLabel.setText(""+whitePieceNbr);
    }
    
  }

  public void onPlayerLost(PlayerLostEvent event) {
    infoMessage.setText("GAME OVER. Player "+event.getPlayer()+" lost.");
    
  }
  
  @UiHandler(value = { "restartButton" })
  public void onRestartButtonClicked(ClickEvent e){
    GameController.getInstance().restartGame();
    init();
  }
}
