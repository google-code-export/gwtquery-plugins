package gwtquery.plugins.draggable.client.plugins;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.draggable.client.DraggableHandler;
import gwtquery.plugins.draggable.client.DraggableOptions;

public interface DraggablePlugin {

  void onStart(DraggableHandler handler, Element draggableElement, Event e);
  void onDrag(DraggableHandler handler, Element draggableElement, Event e);
  void onStop(DraggableHandler handler, Element draggableElement, Event e);
  String getName();
  boolean hasToBeExecuted(DraggableOptions options);
}
