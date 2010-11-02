package gwtquery.plugins.droppable.client.draughtssample;

import static gwtquery.plugins.droppable.client.draughtssample.DraughtsSample.EVENT_BUS;
import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.droppable.client.draughtssample.CheckerBoard.CHECKERBOARD_CLASS_NAME;

import com.google.gwt.user.client.Timer;

import gwtquery.plugins.draggable.client.StopDragException;
import gwtquery.plugins.draggable.client.events.DragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
import gwtquery.plugins.draggable.client.events.DragStartEvent.DragStartEventHandler;
import gwtquery.plugins.draggable.client.events.DragStopEvent.DragStopEventHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;
import gwtquery.plugins.droppable.client.draughtssample.events.PieceEatedEvent;
import gwtquery.plugins.droppable.client.draughtssample.events.PieceMoveEvent;
import gwtquery.plugins.droppable.client.draughtssample.events.PlayerChangeEvent;
import gwtquery.plugins.droppable.client.draughtssample.events.PlayerLostEvent;
import gwtquery.plugins.droppable.client.draughtssample.events.PieceMoveEvent.PieceMoveEventHandler;
import gwtquery.plugins.droppable.client.draughtssample.resources.DraughtsResources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController implements PieceMoveEventHandler,
    DragStartEventHandler, DragStopEventHandler {

  public static enum Player {
    RED, WHITE;

    public String getPieceClassName() {
      if (this == RED) {
        return DraughtsResources.INSTANCE.css().redPiece();
      } else {
        return DraughtsResources.INSTANCE.css().whitePiece();
      }
    }

    public String getKingClassName() {
      if (this == RED) {
        return DraughtsResources.INSTANCE.css().redKing();
      } else {
        return DraughtsResources.INSTANCE.css().whiteKing();
      }
    }

    public int getYDirection() {
      return this == Player.RED ? 1 : -1;
    }
  }

  public static class Position {
    private int x;
    private int y;

    public Position(int column, int row) {
      x = column;
      y = row;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    public void setX(int x) {
      this.x = x;
    }

    public void setY(int y) {
      this.y = y;
    }

    @Override
    public int hashCode() {
      return x ^ y;
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof Position) || obj == null) {
        return false;
      }
      Position pos2 = (Position) obj;
      return pos2.x == x && pos2.y == y;
    }

    public boolean isValid() {
      return x >= 0 && x < CheckerBoard.SQUARE_NUMBER && y >= 0
          && y < CheckerBoard.SQUARE_NUMBER;

    }

  }

  private Map<Position, Piece> pieceByPosition;
  private Timer currentTimer;
  private boolean timerIsRunning;
  private static GameController INSTANCE = new GameController();

  public static GameController getInstance() {
    return INSTANCE;
  }

  private Player currentPlayer;

  private GameController() {
    EVENT_BUS.addHandler(PieceMoveEvent.TYPE, this);
  }

  public void reset() {
    pieceByPosition = new HashMap<Position, Piece>();
    currentPlayer = Player.RED;
  }

  public void startGame() {
    CheckerBoard checkerBoard = getCheckerBoard();
    checkerBoard.fillBoard();
    checkerBoard.lock();
    EVENT_BUS.fireEvent(new PlayerChangeEvent(currentPlayer));
    calcuteNextMoving();
  }

  private boolean calcuteNextMoving() {
    boolean hasNextMove = false;
    for (Piece currentMen : pieceByPosition.values()) {
      if (currentMen.getPlayer() != currentPlayer) {
        continue;
      }
      hasNextMove |= calcuteNextMoving(currentMen);
    }

    if (!hasNextMove) {
      EVENT_BUS.fireEvent(new PlayerLostEvent(currentPlayer));
    }
    
    return hasNextMove;

  }

  private boolean calcuteNextMoving(Piece currentPiece) {

    List<Position> nextPossibleMove = currentPiece.getPossibleMove();
    for (Position p : nextPossibleMove) {
      getCheckerBoard().authorizeMove(currentPiece, p);
    }
    return nextPossibleMove.size() > 0;
  }

  public Player getPlayerAt(Position position) {
    Piece pieceAtPosition = pieceByPosition.get(position);
    if (pieceAtPosition != null) {
      return pieceAtPosition.getPlayer();
    }
    return null;
  }

  public void pieceMove(Piece piece, Position oldPosition, Position newPosition) {

    pieceByPosition.put(newPosition, piece);

    if (oldPosition != null) { // real move, not init phase
      pieceByPosition.remove(oldPosition);
      piece.setPosition(newPosition);
    }

  }

  public void onPieceMove(PieceMoveEvent event) {
    maybeStopTimer();

    Piece m = event.getPiece();
    Position newPosition = event.getNewPosition();
    Position oldPosition = event.getOldPosition();

    pieceMove(m, oldPosition, newPosition);

    if (maybeJumpingOccurred(m, oldPosition, newPosition) && maybeMultipleJumpingIsPossible(m)) {
      //other jump is possible, let one sec to do it
      startTimer();
    } else {
      playerChange();
    }

    
  }

  private boolean maybeMultipleJumpingIsPossible(final Piece m) {
    List<Position> nextJumps = m.getNextJumps();
    CheckerBoard cb = getCheckerBoard();

    cb.lock();
    if (!nextJumps.isEmpty()) {
      for (Position p : nextJumps) {
        cb.authorizeMove(m, p);
      }
      return true;

    }
    
    return false;

  }

  private void maybeStopTimer() {
    if (currentTimer != null) {
      currentTimer.cancel();
      currentTimer = null;
      timerIsRunning = false;
    }

  }

  private void startTimer() {
    currentTimer = new Timer() {
      @Override
      public void run() {
        playerChange();
        currentTimer = null;
        timerIsRunning = false;
      }
    };
    // Wait 1 sec before to change the player...
    // This allows the multiple jump
    currentTimer.schedule(1000);
    timerIsRunning = true;

  }

  private boolean maybeJumpingOccurred(Piece m, Position oldPosition,
      Position newPosition) {

    int oldPositionX = oldPosition.getX();
    int oldPositionY = oldPosition.getY();
    int newPositionX = newPosition.getX();
    int newPositionY = newPosition.getY();
    int maybeEatedX = newPositionX - (newPositionX > oldPositionX ? 1 : -1);
    int maybeEatedY = newPositionY - (newPositionY > oldPositionY ? 1 : -1);
    Position maybeEated = new Position(maybeEatedX, maybeEatedY);
    Player p = getPlayerAt(maybeEated);

    if (p != null && m.getPlayer() != p) {
      Piece jumpedPiece = pieceByPosition.remove(maybeEated);
      jumpedPiece.die();
      EVENT_BUS.fireEvent(new PieceEatedEvent(jumpedPiece));
      return true;
    }
    return false;

  }

  public void playerChange() {
      tooglePlayer();
      getCheckerBoard().lock();
      if (calcuteNextMoving()){
        EVENT_BUS.fireEvent(new PlayerChangeEvent(currentPlayer));
      }
  }

  private void tooglePlayer() {
    if (currentPlayer == Player.RED) {
      currentPlayer = Player.WHITE;
    } else {
      currentPlayer = Player.RED;
    }

  }

  @SuppressWarnings("unchecked")
  public void onDragStart(DragStartEvent event) {
    DraggableWidget<Piece> dw = (DraggableWidget<Piece>) event
        .getDraggableWidget();
    Piece draggingPiece = dw.getOriginalWidget();

    if (draggingPiece.getPlayer() != currentPlayer) {
      // don't start the drag process it's not the good player !!
      throw new StopDragException();
    }

    if (currentTimer != null) {
      currentTimer.cancel();
      timerIsRunning = false;
    }

  }

  public void onDragStop(DragStopEvent event) {
    
    if (currentTimer != null && !timerIsRunning) {
      // we stopped the timer when the drag operation started and not jump is
      // occurred during this drag operation
      playerChange();
      currentTimer = null;
    }

  }

  public void restartGame() {
    getCheckerBoard().clear();
    reset();
    startGame();

  }
  
  private CheckerBoard getCheckerBoard(){
    return (CheckerBoard) $(CHECKERBOARD_CLASS_NAME).widget();
    
  }

}
