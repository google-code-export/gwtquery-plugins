package gwtquery.plugins.commonui.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.user.client.Event;

/**
 * GWT clone of jQueryUi-core
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 *
 */
public class GQueryUi extends GQuery {

  public static Class<GQueryUi> GQueryUi = GQueryUi.class;
  
  private GQueryUiImpl impl = GWT.create(GQueryUiImpl.class);
  
  public native static boolean contains(Node parent, Node descendant)/*-{
    return (typeof parent.compareDocumentPosition != 'undefined') ? parent.compareDocumentPosition(descendant) & 16
      : parent !== descendant && parent.contains(descendant);
    
  }-*/;
  
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
   */
  public int pageX(Event mouseEvent) {
    int pageX = mouseEvent.getClientX() + document.getScrollLeft();
    return pageX;
  }

  /**
   * Simulate event.pageY from jQuery
   * 
   * @param mouseEvent
   * @return the mouse y-position in the page
   */
  public int pageY(Event mouseEvent) {
    int pageY = mouseEvent.getClientY() + document.getScrollTop();
    return pageY;
  }
  
  /**
   * this function returns the immediate scrolling parent.
   *  
   * @return the immediate scrolling parent
   */
  public GQuery scrollParent(){
   return impl.scrollParent(this);
  }

}
