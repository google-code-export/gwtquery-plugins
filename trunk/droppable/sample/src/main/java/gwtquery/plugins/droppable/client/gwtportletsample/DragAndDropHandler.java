package gwtquery.plugins.droppable.client.gwtportletsample;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.draggable.client.events.DragEvent;
import gwtquery.plugins.draggable.client.events.DragEvent.DragEventHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent.OutDroppableEventHandler;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent.OverDroppableEventHandler;

public class DragAndDropHandler implements DropEventHandler,
    OverDroppableEventHandler, OutDroppableEventHandler, DragEventHandler {

  private HandlerRegistration dragHandlerRegistration;
  private FlowPanel panel;
  private SimplePanel placeHolder;
  private int placeHolderIndex;

  public DragAndDropHandler(FlowPanel panel) {
    this.panel = panel;
    placeHolderIndex = -1;
  }

  public void onDrag(DragEvent event) {
    maybeMovePlaceHolder(event.getHelper());
  }

  public void onDrop(DropEvent event) {
    final DraggableWidget<?> draggable = event.getDraggableWidget();
    $(draggable).fadeOut(200, new Function() {
      @Override
      public void f() {
        panel.insert(draggable, placeHolderIndex);
        reset();
        $(draggable).fadeIn(200);
      }
    });

  }

  public void onOutDroppable(OutDroppableEvent event) {
    reset();
  }

  public void onOverDroppable(OverDroppableEvent event) {
    DraggableWidget<?> draggable = event.getDraggableWidget();
    createPlaceHolder(draggable);
    dragHandlerRegistration = draggable.addDragHandler(this);
  }

  private void createPlaceHolder(Widget draggable) {
    placeHolder = new SimplePanel();
    placeHolder.addStyleName("placeHolder");
    placeHolder.setHeight("" + $(draggable).height() + "px");
    placeHolder.setWidth("" + $(draggable).width() + "px");
  }

  private int getBeforeInsertIndex(Element draggableHelper) {
    if (panel.getWidgetCount() == 0) {
      return -1;
    }

    int draggableAbsoluteTop = draggableHelper.getAbsoluteTop();
    
    for (int i = 0; i < panel.getWidgetCount(); i++) {
      Widget w = panel.getWidget(i);
      int widgetAbsoluteTop = w.getElement().getAbsoluteTop();
      if (widgetAbsoluteTop > draggableAbsoluteTop) {
        return i;
      }
    }

    return -1;
  }

  private void maybeMovePlaceHolder(Element draggableHelper) {
    int beforeInsertIndex = getBeforeInsertIndex(draggableHelper);

    if (placeHolderIndex > 0 && beforeInsertIndex == placeHolderIndex) {
      // placeHolder must not move
      return;
    }

    if (beforeInsertIndex >= 0) {
      panel.insert(placeHolder, beforeInsertIndex);
      placeHolderIndex = beforeInsertIndex;
    } else {
      panel.add(placeHolder);
      placeHolderIndex = panel.getWidgetCount() - 1;
    }
  }

  private void reset() {
    dragHandlerRegistration.removeHandler();
    placeHolder.removeFromParent();
    placeHolder = null;
    dragHandlerRegistration = null;
    placeHolderIndex = -1;
  }
}