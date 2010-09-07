package gwtquery.plugins.draggable.client.plugins;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.body;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.Draggable.DragOperationInfo;

/**
 * This add-on handles the css cursor to display during drag operation.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 *
 */
public class CursorPlugin implements DraggablePlugin {
  
  private static String OLD_CURSOR_KEY="oldCursor";
  private static String CURSOR_CSS = "cursor";

  public String getName() {
    return "cursor";
  }

  public void onDrag(DragOperationInfo info, Element draggableElement, Event e) {
    //nothing to do
  }

  public void onStart(DragOperationInfo info, Element draggableElement, Event e) {
    GQuery $body = $(body);
    String oldCursor = $body.css(CURSOR_CSS);
    if (oldCursor != null){
      $body.data(OLD_CURSOR_KEY, oldCursor);
    }
    $body.css(CURSOR_CSS, info.getOptions().getCursor().getCssName());
    
    
    
  }

  public void onStop(DragOperationInfo info, Element draggableElement, Event e) {
    GQuery $body = $(body);
    String oldCursor = $body.data(OLD_CURSOR_KEY, String.class);
    $body.css(CURSOR_CSS, oldCursor);
  }

  public boolean hasToBeExecuted(DraggableOptions options) {
    return options.getCursor() != null;
  }

}
