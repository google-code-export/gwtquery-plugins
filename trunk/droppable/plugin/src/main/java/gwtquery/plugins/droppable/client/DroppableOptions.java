/*
 * Copyright 2010 The gwtquery plugins team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package gwtquery.plugins.droppable.client;

import static com.google.gwt.query.client.GQuery.$;
import gwtquery.plugins.droppable.client.events.DragAndDropContext;

/**
 * This class is used to configure the drop operation.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class DroppableOptions {

  /**
   * Specifies which mode to use for testing whether a draggable is 'over' a
   * droppable.
   * 
   * FIT: draggable overlaps the droppable entirely 
   * 
   * INTERSECT: draggable overlaps the droppable at least 50% 
   * 
   * POINTER: mouse pointer overlaps the droppable 
   * 
   * TOUCH: draggable overlaps the droppable any amount
   * 
   * @author Julien Dramaix (julien.dramaix@gmail.com)
   * 
   */
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
    public boolean acceptDrop(DragAndDropContext context);

  }

  /**
   * Object use as callback function
   * 
   */
  public static interface DroppableFunction {
    public void f(DragAndDropContext context);
  }

  public static AcceptFunction ACCEPT_ALL = new AcceptFunction() {
    public boolean acceptDrop(DragAndDropContext context) {
      return true;
    }
  };

  private boolean disabled;
  private String activeClass;
  private boolean greedy;
  private String droppableHoverClass;
  private String draggableHoverClass;
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

  public String getDraggableHoverClass() {
    return draggableHoverClass;
  }

  public String getDroppableHoverClass() {
    return droppableHoverClass;
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
      public boolean acceptDrop(DragAndDropContext context) {
        return $(context.getDraggable()).is(selector);
      }
    };
  }

  public void setActiveClass(String activeClass) {
    this.activeClass = activeClass;
  }

  public void setGreedy(boolean greedy) {
    this.greedy = greedy;
  }

  public void setDraggableHoverClass(String draggableHoverClass) {
    this.draggableHoverClass = draggableHoverClass;
  }

  public void setDroppableHoverClass(String hoverClass) {
    this.droppableHoverClass = hoverClass;
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
    droppableHoverClass = null;
    draggableHoverClass = null;
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
