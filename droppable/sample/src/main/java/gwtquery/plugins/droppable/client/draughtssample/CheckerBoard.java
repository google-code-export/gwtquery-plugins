package gwtquery.plugins.droppable.client.draughtssample;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.plugins.Effects.Effects;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.droppable.client.draughtssample.GameController.Player;
import gwtquery.plugins.droppable.client.draughtssample.GameController.Position;
import gwtquery.plugins.droppable.client.draughtssample.resources.Css;
import gwtquery.plugins.droppable.client.draughtssample.resources.DraughtsResources;

import java.util.ArrayList;
import java.util.List;

public class CheckerBoard extends Grid {

  public static CheckerBoard getInstance() {
    return INSTANCE;
  }

  private static final Css CSS = DraughtsResources.INSTANCE.css();
  private static final CheckerBoard INSTANCE = new CheckerBoard();

  public static final int SQUARE_NUMBER = 8;
  public static final int PIECE_NUMBER = 12;

  private List<DroppableSquare> droppableSquareList = new ArrayList<DroppableSquare>();

  private CheckerBoard() {
    super(SQUARE_NUMBER, SQUARE_NUMBER);
    initBoard();
  }

  private void initBoard() {
    setCellPadding(0);
    setCellSpacing(0);

    boolean isWhiteCell = true;

    for (int row = 0; row < SQUARE_NUMBER; row++) {
      for (int column = 0; column < SQUARE_NUMBER; column++) {
        Widget cell = null;
        if (!isWhiteCell) {
          // only black cell can contains a man and so is droppable
          cell = new DroppableSquare(new Position(column, row));
          cell.addStyleName(CSS.blackCell());
          droppableSquareList.add((DroppableSquare) cell);
        } else {
          cell = new SimplePanel();
          cell.addStyleName(CSS.whiteCell());
        }
        setWidget(row, column, cell);
        isWhiteCell = !isWhiteCell;
      }

      isWhiteCell = !isWhiteCell;
    }

    addStyleName(CSS.checkerBoard());

  }

  /**
   * reinit the board by putting all pieces.
   */
  public void fillBoard() {
    Player player = Player.RED;
    ArrayList<Piece> pieces = new ArrayList<Piece>();
    for (int row = 0; row < SQUARE_NUMBER;) {
      for (int column = (row + 1) % 2; column < SQUARE_NUMBER; column += 2) {
        Piece m = new Piece(player, new Position(column, row));
        //we will fade in the piece after
        GQuery.$(m.getElement()).hide();
        pieces.add(m);
        getCell(row, column).add(m.makeDraggable());
        GameController.getInstance().pieceMove(m, null,
            new Position(column, row));
       
      }
      row++;
      if (row == (SQUARE_NUMBER / 2) - 1) {
        row += 2;
        player = Player.WHITE;
      }
    }

    fadeIn(pieces);
     
  }
  
  private void fadeIn(final List<Piece> pieces){
    if (pieces.isEmpty()){
      return;
    }
    
    Piece first = pieces.remove(0);
    $(first.getElement()).as(Effects).fadeIn(100, new Function(){
      @Override
      public void f(Element e) {
        fadeIn(pieces);
      }
    });
  }

  @Override
  public void clear() {
    for (DroppableSquare square : droppableSquareList) {
      square.clear();
    }
  }

  public HasWidgets getCell(int row, int column) {
    return (HasWidgets) getWidget(row, column);
  }

  /**
   * disable drop functionnality of all droppable square 
   */
  public void lock() {
    for (DroppableSquare square : droppableSquareList) {
      square.lock();
    }

  }

  public void authorizeMove(final Piece currentPiece, Position p) {
    Widget w = getWidget(p.getY(), p.getX());
    assert w instanceof DroppableSquare;
    final DroppableSquare square = (DroppableSquare) w;

    square.acceptPiece(currentPiece);

  }

}
