package gwtquery.plugins.droppable.client.draughtssample;

import static gwtquery.plugins.droppable.client.draughtssample.DraughtsSample.EVENT_BUS;
import static gwtquery.plugins.droppable.client.draughtssample.CheckerBoard.SQUARE_NUMBER;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.draggable.client.gwt.DraggableWidget;
import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;
import gwtquery.plugins.droppable.client.DroppableOptions.DroppableTolerance;
import gwtquery.plugins.droppable.client.draughtssample.GameController.Position;
import gwtquery.plugins.droppable.client.draughtssample.events.PieceMoveEvent;
import gwtquery.plugins.droppable.client.draughtssample.resources.DraughtsResources;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * TODO instead of extends a composite, extends directly {@link DroppableWidget}
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class DroppableSquare extends Composite implements HasWidgets,
    DropEventHandler {

  /**
   * We will play with accept function to know if a square can accept a piece or
   * not !
   * 
   * @author Julien Dramaix (julien.dramaix@gmail.com)
   * 
   */
  private static class CellAcceptFunction implements AcceptFunction {

    Collection<String> acceptedManIds = new ArrayList<String>();

    public CellAcceptFunction() {
    }

    public void addPieceId(String... ids) {
      for (String id : ids) {
        acceptedManIds.add(id);
      }
    }

    public void reset() {
      acceptedManIds = new ArrayList<String>();
    }

    public boolean acceptDrop(Element droppable, Element draggable) {
      return acceptedManIds.contains(draggable.getId());
    }

  }

  public Position position;

  public DroppableSquare(Position p) {
    this.position = p;
    init();

  }

  private void init() {
    SimplePanel square = new SimplePanel();
    // make it droppable
    DroppableWidget<SimplePanel> droppableSquare = new DroppableWidget<SimplePanel>(
        square);
    // setup
    droppableSquare.setHoverClass(DraughtsResources.INSTANCE.css().hoverCell());
    droppableSquare.setActiveClass(DraughtsResources.INSTANCE.css()
        .activeCell());
    droppableSquare.setAccept(new CellAcceptFunction());
    droppableSquare.setTolerance(DroppableTolerance.POINTER);
    droppableSquare.addDropHandler(this);
    initWidget(droppableSquare);
  }

  public void enable() {
    getDroppable().setDisabled(false);
  }

  public void lock() {
    getDroppable().setDisabled(true);
    ((CellAcceptFunction) getDroppable().getAccept()).reset();
  }

  public void acceptPiece(Widget piece) {
    enable();
    CellAcceptFunction f = (CellAcceptFunction) getDroppable().getAccept();
    f.addPieceId(piece.getElement().getId());
  }

  @SuppressWarnings("unchecked")
  private DroppableWidget<SimplePanel> getDroppable() {
    return (DroppableWidget<SimplePanel>) getWidget();
  }

  public void add(Widget w) {
    getDroppable().getOriginalWidget().add(w);
  }

  public void clear() {
    getDroppable().getOriginalWidget().clear();

  }

  public Iterator<Widget> iterator() {
    return getDroppable().getOriginalWidget().iterator();
  }

  public boolean remove(Widget w) {
    return getDroppable().getOriginalWidget().remove(w);
  }

  public void onDrop(DropEvent event) {
    final DraggableWidget<?> draggable = event.getDraggableWidget();

    // as we use original widget for drag operation, clear top and left css
    // properties set during the drag operation before adding it
    draggable.getElement().getStyle().setTop(0, Unit.PX);
    draggable.getElement().getStyle().setLeft(0, Unit.PX);

    if (draggable.getParent() != getDroppable().getOriginalWidget()) {
      add(draggable);
      Piece draggingMen = (Piece) draggable.getOriginalWidget();
      EVENT_BUS.fireEvent(new PieceMoveEvent(draggingMen, position, draggingMen
          .getPosition()));
      if (isKingLine()){
        draggingMen.kingMe();
      }

    }

  }

  private boolean isKingLine() {
    return position.getY() == 0 || position.getY() == SQUARE_NUMBER - 1 ;
  }
}
