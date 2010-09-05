package gwtquery.plugins.draggable.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.draggable.client.Draggable;

public interface DraggablePlugin {

  void onStart(Draggable draggable, Element draggableElement, Event e);
  void onDrag(Draggable draggable, Element draggableElement, Event e);
  void onStop(Draggable draggable, Element draggableElement, Event e);
  String getName();
}
