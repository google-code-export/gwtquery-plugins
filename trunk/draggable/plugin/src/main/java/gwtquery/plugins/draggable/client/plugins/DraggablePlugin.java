package gwtquery.plugins.draggable.client.plugins;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.Draggable.DragOperationInfo;

public interface DraggablePlugin {

  void onStart(DragOperationInfo info, Element draggableElement, Event e);
  void onDrag(DragOperationInfo info, Element draggableElement, Event e);
  void onStop(DragOperationInfo info, Element draggableElement, Event e);
  String getName();
  boolean hasToBeExecuted(DraggableOptions options);
}
