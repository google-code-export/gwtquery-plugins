package gwtquery.plugins.draggable.client;

/**
 * Throw this exception, in your callback functions or when you handle event fired by the plug-in, to stop the drag operation
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class StopDragException extends RuntimeException {
  
  public StopDragException() {
  }

}
