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
package gwtquery.plugins.droppable.client.simplesample;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;
import static gwtquery.plugins.droppable.client.Droppable.Droppable;

import com.google.gwt.core.client.EntryPoint;

import gwtquery.plugins.droppable.client.DroppableOptions;
import gwtquery.plugins.droppable.client.DroppableOptions.DroppableFunction;
import gwtquery.plugins.droppable.client.events.DragAndDropContext;

/**
 * Make any elements droppable !
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class SimpleSample implements EntryPoint {

  public void onModuleLoad() {

    $("#draggable").as(Draggable).draggable();

    $("#droppable").as(Droppable).droppable(createDroppableOptions());
  }

  private DroppableOptions createDroppableOptions() {
    DroppableOptions options = new DroppableOptions();
    // class added to the droppable when a acceptable draggable is over it
    options.setDroppableHoverClass("yellow-background");
    // class added to the droppable when the droppable is activated
    options.setActiveClass("white-background");

    // function called on when a droppable is activated
    options.setOnActivate(new DroppableFunction() {

      public void f(DragAndDropContext context) {
        $("p", context.getDroppable()).html("I'm ready to accept an element");
        context.getDroppable().removeClassName("orange-background");

      }
    });

    // function called on when a acceptable draggable is dropped on the droppable
    options.setOnDrop(new DroppableFunction() {
      public void f(DragAndDropContext context) {
        $("p", context.getDroppable()).html(
            "Element " + context.getDraggable().getId() + " was drop on me");
        context.getDroppable().addClassName("orange-background");

      }
    });

    // function called on when a droppable is deactivated
    options.setOnDeactivate(new DroppableFunction() {

      public void f(DragAndDropContext context) {
        $("p", context.getDroppable()).html("I'm a drop target");

      }
    });

    // function called on when a acceptable draggable is dragged over the
    // droppable
    options.setOnOver(new DroppableFunction() {

      public void f(DragAndDropContext context) {
        $("p", context.getDroppable()).html(
            "The element " + context.getDraggable().getId()
                + " is over me and can be dropped");

      }
    });

    // function called on when a acceptable draggable is dragged out the droppable
    options.setOnOut(new DroppableFunction() {
      public void f(DragAndDropContext context) {
        $("p", context.getDroppable()).html("I'm ready to accept an element");

      }

    });

    return options;
  }

}
