package gwtquery.plugins.droppable.client.draughtssample;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.droppable.client.draughtssample.CheckerBoard.CHECKERBOARD_CLASS_NAME;
import static gwtquery.plugins.droppable.client.draughtssample.DraughtsSample.EVENT_BUS;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.ui.HTML;

import gwtquery.plugins.draggable.client.DraggableOptions.CursorAt;
import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;
import gwtquery.plugins.droppable.client.draughtssample.GameController.Player;
import gwtquery.plugins.droppable.client.draughtssample.GameController.Position;
import gwtquery.plugins.droppable.client.draughtssample.events.PieceKingedEvent;

import java.util.ArrayList;
import java.util.List;

public class Piece extends HTML {

  private Position position;
  private Player player;
  private boolean isKing = false;

  public Piece(Player player, Position initialPosition) {
    this.player = player;
    this.position = initialPosition;
    init();
  }

  protected void init() {
    setStyleName(player.getPieceClassName());
    // ensure that this div have an unique id because we will use id to
    // determine if a DroppableSquare accept or not a Piece
    getElement().setId("piece_" + getElement().hashCode());
  }

  public DraggableWidget<Piece> makeDraggable() {
    // make it draggable
    DraggableWidget<Piece> draggablePiece = new DraggableWidget<Piece>(this);
    // setup
    // revert the piece if this one is not dropped
    draggablePiece.setRevert(RevertOption.ON_INVALID_DROP);
    // be sure that when the piece is dragging, it is in front
    draggablePiece.setZIndex(100);
    // set the opacity of the piece during the drag
    draggablePiece.setOpacity((float) 0.8);
    // the piece cannot be drag outside the checkerboard
    draggablePiece.setContainment($(CHECKERBOARD_CLASS_NAME).widget());
    // set cursor in the middle of the piece
    draggablePiece.setCursorAt(new CursorAt(25, 25, null, null));
    // set the cursor to use during the drag
    draggablePiece.setCursor(Cursor.MOVE);
    
    draggablePiece.setDistance(0);
    
    //register the GameController for dragStart and drag stop event
    GameController gc = GameController.getInstance();
    draggablePiece.addDragStartHandler(gc);
    draggablePiece.addDragStopHandler(gc);

    return draggablePiece;
  }

  public Player getPlayer() {
    return player;
  }

  /**
   * TODO refactor this code
   * 
   * @param position
   * @return
   */
  public List<Position> getPossibleMove() {
    List<Position> next = new ArrayList<Position>();

    for (int yDirection : getYDirections()) {
      Position leftNextPosition = checkNextPosition(-1, yDirection);
      if (leftNextPosition != null) {
        next.add(leftNextPosition);
      }
      Position rightNextPosition = checkNextPosition(+1, yDirection);
      if (rightNextPosition != null) {
        next.add(rightNextPosition);
      }
    }

    return next;
  }

  private Position checkNextPosition(int xDirection, int yDirection) {

    int currentX = position.getX();
    int currentY = position.getY();

    Position nextPosition = new Position(currentX + xDirection, currentY
        + yDirection);

    if (nextPosition.isValid()) {
      Player playerAtNextPosition = GameController.getInstance().getPlayerAt(
          nextPosition);
      if (playerAtNextPosition == null) {
        return nextPosition;
      } else {
        return checkJump(xDirection, yDirection);
      }
    }
    return null;
  }

  private Position checkJump(int xDirection, int yDirection) {
    int currentX = position.getX();
    int currentY = position.getY();

    Player playerAtNextPosition = GameController.getInstance().getPlayerAt(
        new Position(currentX + xDirection, currentY + yDirection));

    if (playerAtNextPosition != null && playerAtNextPosition != player) {
      Position jumpPosition = new Position(currentX + 2 * xDirection, currentY
          + 2 * yDirection);
      if (jumpPosition.isValid()
          && GameController.getInstance().getPlayerAt(jumpPosition) == null) {
        return jumpPosition;
      }
    }
    return null;
  }

  protected int[] getYDirections() {
    if (isKing) {
      return new int[] { -1, 1 };
    }
    return new int[] { player.getYDirection() };
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public void die() {
    // use GQuery to fade out the piece
    $(this).fadeOut(300, new Function() {
      @Override
      public void f() {
        ((CheckerBoard) $(CHECKERBOARD_CLASS_NAME).widget()).getCell(position.getY(), position.getX())
            .clear();
        position = null;
      }
    });

  }

  public boolean isKing() {
    return isKing;
  }

  public void kingMe() {
    if (isKing){
      return;
    }
    
    isKing = true;
    removeStyleName(player.getPieceClassName());
    addStyleName(player.getKingClassName());
    EVENT_BUS.fireEvent(new PieceKingedEvent(this));
  }

  /**
   * Calculate if jumping is possible
   * 
   * @return
   */
  public List<Position> getNextJumps() {
    List<Position> possibleJumps = new ArrayList<Position>();
    for (int yDirection : getYDirections()){
      Position possibleLeftJump = checkJump(-1, yDirection);
      if (possibleLeftJump != null){
        possibleJumps.add(possibleLeftJump);
      }
      Position possibleRightJump = checkJump(1, yDirection);
      if (possibleRightJump != null){
        possibleJumps.add(possibleRightJump);
      }
    }
    return possibleJumps;
  }

}
