package gwtquery.plugins.droppable.client;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.dom.client.Element;

import gwtquery.plugins.droppable.client.events.DragAndDropContext;

public class DroppableOptions {

  public static enum DroppableTolerance {
    FIT, INTERSECT, POINTER, TOUCH;
  }

  public static interface AcceptFunction {

    /**
     * This method returns true if the drop of the draggable element on the
     * droppable element is accepted
     * 
     * @param droppable
     * @param draggable
     * @return
     */
    public boolean acceptDrop(Element droppable, Element draggable);

  }

  /**
   * Object use as callback function
   * 
   */
  public static interface DroppableFunction {
    public void f(DragAndDropContext context);
  }

  public static AcceptFunction ACCEPT_ALL = new AcceptFunction() {
    public boolean acceptDrop(Element droppable, Element draggable) {
      return true;
    }
  };

  private boolean disabled;
  private String activeClass;
  private boolean greedy;
  private String hoverClass;
  private String scope;
  private DroppableTolerance tolerance;
  private AcceptFunction accept;
  private DroppableFunction onDrop;
  private DroppableFunction onOut;
  private DroppableFunction onOver;
  private DroppableFunction onActivate;
  private DroppableFunction onDeactivate;

  public DroppableOptions() {
    initDefault();
  }

  public boolean isDisabled() {
    return disabled;
  }

  public AcceptFunction getAccept() {
    return accept;
  }

  public String getActiveClass() {
    return activeClass;
  }

  public boolean isGreedy() {
    return greedy;
  }

  public String getHoverClass() {
    return hoverClass;
  }

  public DroppableFunction getOnDrop() {
    return onDrop;
  }

  public DroppableFunction getOnOut() {
    return onOut;
  }

  public DroppableFunction getOnOver() {
    return onOver;
  }

  public String getScope() {
    return scope;
  }

  public DroppableTolerance getTolerance() {
    return tolerance;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public void setAccept(AcceptFunction acceptFunction) {
    this.accept = acceptFunction;
  }

  public void setAccept(final String selector) {
    this.accept = new AcceptFunction() {
      public boolean acceptDrop(Element droppable, Element draggable) {
        return $(draggable).is(selector);
      }
    };
  }

  public void setActiveClass(String activeClass) {
    this.activeClass = activeClass;
  }

  public void setGreedy(boolean greedy) {
    this.greedy = greedy;
  }

  public void setHoverClass(String hoverClass) {
    this.hoverClass = hoverClass;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public void setTolerance(DroppableTolerance tolerance) {
    this.tolerance = tolerance;
  }

  public void setOnDrop(DroppableFunction onDrop) {
    this.onDrop = onDrop;
  }

  public void setOnOut(DroppableFunction onOut) {
    this.onOut = onOut;
  }

  public void setOnOver(DroppableFunction onOver) {
    this.onOver = onOver;
  }

  protected void initDefault() {
    setAccept(ACCEPT_ALL);
    activeClass = null;
    greedy = false;
    hoverClass = null;
    scope = "default";
    tolerance = DroppableTolerance.INTERSECT;

  }

  public DroppableFunction getOnActivate() {
    return onActivate;
  }

  public void setOnActivate(DroppableFunction onActivate) {
    this.onActivate = onActivate;
  }

  public DroppableFunction getOnDeactivate() {
    return onDeactivate;
  }

  public void setOnDeactivate(DroppableFunction onDeactivate) {
    this.onDeactivate = onDeactivate;
  }

}
