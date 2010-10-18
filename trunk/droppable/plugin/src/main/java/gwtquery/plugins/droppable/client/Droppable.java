package gwtquery.plugins.droppable.client;

import gwtquery.plugins.commonui.client.GQueryUi;
import gwtquery.plugins.draggable.client.DraggableDroppableManager;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.query.client.Plugin;

/**
 * Droppable for GwtQuery
 */
public class Droppable extends GQueryUi {

  // A shortcut to the class 
  public static final Class<Droppable> Droppable = Droppable.class;

  public static final String DROPPABLE_HANDLER_KEY="droppableHandler";
  
  // Register the plugin in GQuery
  static {
    GQuery.registerPlugin(Droppable.class, new Plugin<Droppable>() {
      public Droppable init(GQuery gq) {
        return new Droppable(gq);
      }
    });
  }
  
  public static interface CssClassNames {
    String UI_DROPPABLE = "ui-droppable";
    String UI_DROPPABLE_DISABLED = "ui-droppable-disabled";

  }

  public Droppable(Element element) {
    super(element);
  }

  public Droppable(GQuery gq) {
    super(gq);
  }

  public Droppable(JSArray elements) {
    super(elements);
  }

  public Droppable(NodeList<Element> list) {
    super(list);
  }

  public Droppable droppable(){
    return droppable(new DroppableOptions());
  }
  
  public Droppable droppable(DroppableOptions o){
    return droppable(o, null);
  }
  
  public Droppable droppable(DroppableOptions o, HandlerManager eventBus){

    DraggableDroppableManager ddm = DraggableDroppableManager.getInstance();
 
    for (Element e : elements()){
      DroppableHandler di =  new DroppableHandler(o, eventBus);
      di.setDroppableDimension(new Dimension(e));
      $(e).data(DROPPABLE_HANDLER_KEY, di);
      
      ddm.setDroppable(e, o.getScope());
      
      e.addClassName(CssClassNames.UI_DROPPABLE);
     
    }
    
    
    return this;
  }
  
  public Droppable destroy(){
    DraggableDroppableManager ddm = DraggableDroppableManager.getInstance();
    for (Element e : elements()){
      DroppableHandler infos = DroppableHandler.getInstance(e);
      ddm.getDroppablesByScope(infos.getOptions().getScope()).remove(e);
      $(e).removeClass(CssClassNames.UI_DROPPABLE, CssClassNames.UI_DROPPABLE_DISABLED).removeData(DROPPABLE_HANDLER_KEY);
    }
    return this;
  }
}
