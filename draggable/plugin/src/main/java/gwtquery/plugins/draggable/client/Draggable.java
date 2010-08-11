package gwtquery.plugins.draggable.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.query.client.Plugin;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;

/**
 * Draggable for GwtQuery
 */
public class Draggable extends MouseHandler {


  // A shortcut to the class 
  public static final Class<Draggable> Draggable = Draggable.class;

  // Register the plugin in GQuery
  static {
    GQuery.registerPlugin(Draggable.class, new Plugin<Draggable>() {
      public Draggable init(GQuery gq) {
        return new Draggable(gq);
      }
    });
  }
  
  private DraggableOptions options;

  public Draggable(GQuery gq) {
    super(gq);
  }
  
  public Draggable(Element element) {
    super(element);
  }

  public Draggable(JSArray elements) {
    super(elements);
  }

  public Draggable(NodeList<Element> list) {
    super(list);
  }

  public Draggable draggable() {
    return draggable(new DraggableOptions());
  }
  
  public Draggable draggable(DraggableOptions options) {
      this.options = options;
      initMouseHandler(options);
      
      for (Element e : elements()){
        if (options.getHelperType() == HelperType.ORIGINAL){
          // TBC
        }
      }
      
      return this;
  }
  
  @Override
  protected String getPluginName() {
    return "draggable";
  }

  @Override
  protected boolean mouseDrag(Element elemen, Event event) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected boolean mouseStart(Element elemen, Event event) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected boolean mouseStop(Element elemen, Event event) {
    // TODO Auto-generated method stub
    return false;
  }

}
