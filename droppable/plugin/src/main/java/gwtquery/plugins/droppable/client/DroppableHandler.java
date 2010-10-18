package gwtquery.plugins.droppable.client;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.droppable.client.Droppable.DROPPABLE_HANDLER_KEY;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.GQuery.Offset;

import gwtquery.plugins.commonui.client.Event;
import gwtquery.plugins.commonui.client.GQueryUi.Dimension;
import gwtquery.plugins.draggable.client.DraggableDroppableManager;
import gwtquery.plugins.draggable.client.DraggableHandler;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.droppable.client.Droppable.CssClassNames;
import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;
import gwtquery.plugins.droppable.client.DroppableOptions.DroppableFunction;
import gwtquery.plugins.droppable.client.DroppableOptions.DroppableTolerance;
import gwtquery.plugins.droppable.client.events.AbstractDroppableEvent;
import gwtquery.plugins.droppable.client.events.ActivateDroppableEvent;
import gwtquery.plugins.droppable.client.events.DeactivateDroppableEvent;
import gwtquery.plugins.droppable.client.events.DragDropInfo;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DroppableOutEvent;
import gwtquery.plugins.droppable.client.events.DroppableOverEvent;

/**
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class DroppableHandler {
  
  private static enum PositionStatus{
    IS_OVER, IS_OUT;
  }
  public static DroppableHandler getInstance(Element droppable) {
    return $(droppable).data(DROPPABLE_HANDLER_KEY, DroppableHandler.class);
  }

  private boolean isOver = false;
  private boolean isOut = true;
  private boolean greedyChild = false;
  private DroppableOptions options;
  private Dimension droppableDimension;
  private HandlerManager eventBus;
  private boolean visible = false;

  private Offset droppableOffset;

  public DroppableHandler(DroppableOptions options, HandlerManager eventBus) {
    this.options = options;
    this.eventBus = eventBus;

  }

  public void activate(Element droppable, Event e) {
    if (options.getActiveClass() != null) {
      droppable.addClassName(options.getActiveClass());
    }
    Element draggable = DraggableDroppableManager.getInstance()
        .getCurrentDraggable();
    if (draggable != null) {
      trigger(new ActivateDroppableEvent(), options.getOnActivate(), droppable,
          draggable);
    }
  }

  public void deactivate(Element droppable, Event e) {

    if (options.getActiveClass() != null) {
      droppable.removeClassName(options.getActiveClass());
    }
    Element draggable = DraggableDroppableManager.getInstance()
        .getCurrentDraggable();
    if (draggable != null) {
      trigger(new DeactivateDroppableEvent(), options.getOnDeactivate(),
          droppable, draggable);
    }

  }

  public boolean drop(Element droppable, Element draggable, Event e,
      boolean alreadyDrop) {
    if (options == null) {
      return false;
    }

    boolean drop = false;

    if (!options.isDisabled() && visible)
      if (!alreadyDrop && intersect(draggable)) {
        drop = doDrop(droppable, draggable, e);
      }
    if (isDraggableAccepted(droppable, draggable)) {
      isOut = true;
      isOver = false;
      deactivate(droppable, e);
    }
    return drop;
  }

  public Dimension getDroppableDimension() {
    return droppableDimension;
  }

  public Offset getDroppableOffset() {
    return droppableOffset;
  }

  public DroppableOptions getOptions() {
    return options;
  }

  public boolean isOut() {
    return isOut;
  }

  public boolean isOver() {
    return isOver;
  }

  public boolean isVisible() {
    return visible;
  }

  public void out(Element droppable, Event e) {
    Element currentDraggable = DraggableDroppableManager.getInstance()
        .getCurrentDraggable();

    if (currentDraggable == null || currentDraggable == droppable) {
      return;
    }

    if (isDraggableAccepted(droppable, currentDraggable)) {
      if (options.getHoverClass() != null) {
        droppable.removeClassName(options.getHoverClass());
      }
      trigger(new DroppableOutEvent(), options.getOnOut(), droppable,
          currentDraggable);
    }
  }

  public void over(Element droppable, Event e) {
    Element currentDraggable = DraggableDroppableManager.getInstance()
        .getCurrentDraggable();

    if (currentDraggable == null || currentDraggable == droppable) {
      return;
    }

    if (isDraggableAccepted(droppable, currentDraggable)) {
      if (options.getHoverClass() != null) {
        droppable.addClassName(options.getHoverClass());
      }
      trigger(new DroppableOverEvent(), options.getOnOver(), droppable,
          currentDraggable);
    }

  }

  public void setDroppableDimension(Dimension droppableDimension) {
    this.droppableDimension = droppableDimension;
  }

  public void setDroppableOffset(Offset offset) {
    this.droppableOffset = offset;

  }

  public void setOptions(DroppableOptions options) {
    this.options = options;
  }

  public void setOut(boolean isOut) {
    this.isOut = isOut;
  }

  public void setOver(boolean isOver) {
    this.isOver = isOver;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;

  }

  private boolean checkChildrenIntersection(Element droppable, Element draggable) {

    for (Element e : $(droppable).find("*").not(".ui-draggable-dragging")
        .elements()) {
      DroppableHandler handler = getInstance(e);
      if (handler == null) {
        continue;
      }
      handler.setDroppableOffset($(e).offset());
      DraggableHandler draggableHandler = DraggableHandler
          .getInstance(draggable);
      DroppableOptions dropOpt = handler.getOptions();
      DraggableOptions dragOpt = draggableHandler.getOptions();
      if (dropOpt.isGreedy() && !dropOpt.isDisabled()
          && dropOpt.getScope().equals(dragOpt.getScope())
          && handler.isDraggableAccepted(droppable, draggable)
          && handler.intersect(draggable)) {
        return true;
      }

    }
    return false;
  }

  private boolean doDrop(Element droppable, Element draggable, Event e) {

    if (draggable == null || draggable == droppable) {
      return false;
    }

    if (checkChildrenIntersection(droppable, draggable)) {
      // This droppable is greddy, a children of this droppable is also
      // droppable and intersect the draggable !
      // the drop will be done on this child
      return false;
    }

    if (isDraggableAccepted(droppable, draggable)) {
      if (options.getActiveClass() != null) {
        droppable.removeClassName(options.getActiveClass());
      }
      if (options.getHoverClass() != null) {
        droppable.removeClassName(options.getHoverClass());
      }
      trigger(new DropEvent(), options.getOnDrop(), droppable, draggable);

    }

    return true;

  }

  private boolean intersect(Element draggable) {
    if (droppableOffset == null || droppableDimension == null) {
      return false;
    }
    DraggableHandler dragHandler = DraggableHandler.getInstance(draggable);

    int draggableLeft = dragHandler.getAbsPosition().getLeft();
    int draggableRight = draggableLeft
        + dragHandler.getHelperDimension().getWidth();
    int draggableTop = dragHandler.getAbsPosition().getTop();
    int draggableBottom = draggableTop
        + dragHandler.getHelperDimension().getHeight();

    int droppableLeft = droppableOffset.left;
    int droppableRight = droppableLeft + droppableDimension.getWidth();
    int droppableTop = droppableOffset.top;
    int droppableBottom = droppableTop + droppableDimension.getHeight();

    DroppableTolerance tolerance = options.getTolerance();
    // FIT, INTERSECT, POINTER, TOUCH;
    switch (tolerance) {
    case FIT:
      return droppableLeft < draggableLeft && draggableRight < droppableRight
          && droppableTop < draggableTop && draggableBottom < droppableBottom;
    case INTERSECT:
      float dragHelperHalfWidth = dragHandler.getHelperDimension().getWidth() / 2;
      float dragHelperHalfHeight = dragHandler.getHelperDimension().getHeight() / 2;
      return droppableLeft < draggableLeft + dragHelperHalfWidth
          && droppableRight > draggableLeft + dragHelperHalfWidth
          && droppableTop < draggableTop + dragHelperHalfHeight
          && droppableBottom > draggableTop + dragHelperHalfHeight;
    case POINTER:
      int pointerLeft = draggableLeft + dragHandler.getOffsetClick().getLeft();
      int pointerTop = draggableTop + dragHandler.getOffsetClick().getTop();
      return pointerLeft > droppableLeft && pointerLeft < droppableRight
          && pointerTop > droppableTop && pointerTop < droppableBottom;

    case TOUCH:
      return ((draggableTop >= droppableTop && draggableTop <= droppableBottom)
          || (draggableBottom >= droppableTop && draggableBottom <= droppableBottom) || (draggableTop < droppableTop && draggableBottom > droppableBottom))
          && ((draggableLeft >= droppableLeft && draggableLeft <= droppableRight)
              || (draggableRight >= droppableLeft && draggableRight <= droppableRight) || (draggableLeft < droppableLeft && draggableRight > droppableRight));

    default:
      break;
    }
    return true;
  }

  private boolean isDraggableAccepted(Element droppable, Element draggable) {
    AcceptFunction accept = options.getAccept();
    return accept != null && accept.acceptDrop(droppable, draggable);
  }

  private void trigger(AbstractDroppableEvent<?> e, DroppableFunction callback,
      Element droppable, Element draggable) {
    DragDropInfo draggableInfo = new DragDropInfo(draggable, droppable);

    if (eventBus != null && e != null) {
      e.setDragDropInfo(draggableInfo);
      eventBus.fireEvent(e);
    }
    if (callback != null) {
      // TODO use a proper callback function for droppable ?
      callback.f(droppable, draggableInfo);
    }
  }

  public void reset() {
    droppableDimension = null;
    droppableOffset = null;

  }

  public void drag(Element droppable, Element draggable, Event e) {
    if (options.isDisabled() || greedyChild || !visible){
      return;
    }
    
    boolean isIntersect = intersect(draggable);
    PositionStatus c = null;
    
    if (!isIntersect && isOver){
      c = PositionStatus.IS_OUT;
    }else if (isIntersect && !isOver){
      c =PositionStatus.IS_OVER;
    }
    
    if (c == null){
      return;
    }
    
    DroppableHandler parentDroppableHandler = null;
    GQuery droppableParents = null;
    if (options.isGreedy()){
      //TODO maybe filter the parent with droppable data instead of test on css class name
      droppableParents =$(droppable).parents("."+CssClassNames.UI_DROPPABLE);
      if (droppableParents.length() > 0){
        parentDroppableHandler = DroppableHandler.getInstance(droppableParents.get(0));
        parentDroppableHandler.greedyChild = (c == PositionStatus.IS_OVER); 
      }
    }
    
    if (parentDroppableHandler != null && c == PositionStatus.IS_OVER){
      parentDroppableHandler.isOver = false;
      parentDroppableHandler.isOut = true;
      parentDroppableHandler.out(droppableParents.get(0), e);
    }
    
    if (c == PositionStatus.IS_OUT){
      isOut = true;
      isOver = false;
      out(droppable,e);
    }else {
      isOver = true;
      isOut = false;
      over(droppable, e);
    }
    
    if (parentDroppableHandler != null && c == PositionStatus.IS_OUT){
      parentDroppableHandler.isOut = false;
      parentDroppableHandler.isOver = true;
      parentDroppableHandler.over(droppableParents.get(0), e);
    }
    
    
  }

}