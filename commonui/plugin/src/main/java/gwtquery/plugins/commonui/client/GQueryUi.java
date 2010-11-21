package gwtquery.plugins.commonui.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.query.client.Plugin;
import com.google.gwt.user.client.Event;

/**
 * GWT clone of jQueryUi-core
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 *
 */
public class GQueryUi extends GQuery {

  public static class Dimension{
      private int width = 0;
      private int height = 0;

      public Dimension(Element e) {
        width = e.getOffsetWidth();
        height = e.getOffsetHeight();
      }
      
      public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
      }

      public int getHeight() {
        return height;
      }

      public int getWidth() {
        return width;
      }
  }
  
  public static Class<GQueryUi> GQueryUi = GQueryUi.class;
  
  //Register the plugin in GQuery
  static {
    GQuery.registerPlugin(GQueryUi.class, new Plugin<GQueryUi>() {
      public GQueryUi init(GQuery gq) {
        return new GQueryUi(gq);
      }
    });
  }
  
  protected HasHandlers eventBus;
  
  private GQueryUiImpl impl = GWT.create(GQueryUiImpl.class);
 
  public static boolean contains(Element parent, Element descendant){    
    return parent != descendant && parent.isOrHasChild(descendant);
  }
  
  public GQueryUi() {
    super();
  }

  public GQueryUi(Element element) {
    super(element);
  }

  public GQueryUi(GQuery gq) {
    super(gq);
  }

  public GQueryUi(JSArray elements) {
    super(elements);
  }

  public GQueryUi(NodeList<Element> list) {
    super(list);
  }
  
  /**
   * Simulate event.pageX from jQuery
   * 
   * @param mouseEvent
   * @return the mouse x-position in the page
   * @deprecated use {@link gwtquery.plugins.commonui.client.Event.pageX}
   */
  public static int pageX(Event mouseEvent) {
	  if (mouseEvent instanceof gwtquery.plugins.commonui.client.Event){
		  return ((gwtquery.plugins.commonui.client.Event)mouseEvent).pageX();
	  }
    
	  return gwtquery.plugins.commonui.client.Event.create(mouseEvent).pageX();
  }

  /**
   * Simulate event.pageY from jQuery
   * 
   * @param mouseEvent
   * @return the mouse y-position in the page
   * @deprecated use {@link gwtquery.plugins.commonui.client.Event.pageY}
   */
  public static int pageY(Event mouseEvent) {
	  if (mouseEvent instanceof gwtquery.plugins.commonui.client.Event){
		  return ((gwtquery.plugins.commonui.client.Event)mouseEvent).pageY();
	  }
    
	  return gwtquery.plugins.commonui.client.Event.create(mouseEvent).pageY();
  }
  
  /**
   * this function returns the immediate scrolling parent.
   *  
   * @return the immediate scrolling parent
   */
  public GQuery scrollParent(){
   return impl.scrollParent(this);
  }
  
  /**
   * fire event and call callback function.
   * 
   * @param e
   * @param callback
   * @param element
   */
  protected void trigger(GwtEvent<?> e, Function callback, Element element) {
    trigger(e, callback, element, eventBus);
  }
  
  protected static void trigger(GwtEvent<?> e, Function callback, Element element, HasHandlers handlerManager){
    if (handlerManager != null && e != null) {
      handlerManager.fireEvent(e);
    }
    if (callback != null) {
      callback.f(element);
    }
  }

}
