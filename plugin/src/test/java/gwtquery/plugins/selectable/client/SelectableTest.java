/*
 * Copyright 2010 Google Inc.
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
package gwtquery.plugins.selectable.client;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * Test class for Selectable plugin
 */
public class SelectableTest extends GWTTestCase {

  public String getModuleName() {
    return "gwtquery.plugins.selectable.Selectable";
  }

  public void testSelectableApply() {

    // execute the plugin method
  /*  final GQuery ul = $(
        "<ul><li id=\"1\">item 1</li><li id=\"2\">item 2</li><li id=\"3\">item 3</li><li id=\"4\">item 4</li></ul>")
        .appendTo(document).as(Selectable).selectable();
    final GQuery liId1 = $("#1");

    // delay the test
    delayTestFinish(Speed.DEFAULT * 3);

    // trigger mouse down event
    liId1.trigger(Event.ONMOUSEDOWN);

    new Timer() {
      public void run() {

        assertTrue(liId1.hasClass("ui-selecting"));

        // trigger mouse out event
        liId1.trigger(Event.ONMOUSEUP);
        new Timer() {
          public void run() {

            assertTrue(liId1.hasClass("ui-selected"));
            ul.remove();

            // finish the test
            finishTest();
          }
        }.schedule(Speed.DEFAULT);
      }
    }.schedule(Speed.DEFAULT);*/
  }

}
