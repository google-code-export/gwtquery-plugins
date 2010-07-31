package gwtquery.plugins.selectable.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.document;
import static gwtquery.plugins.selectable.client.Selectable.Selectable;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.Effects.Speed;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
/**
 * Test class for Selectable plugin
 */
public class SelectableTest extends GWTTestCase {

  public String getModuleName() {
    return "gwtquery.plugins.selectable.Selectable";
  }

 
  public void testSelectableApply() {
    
    // execute the plugin method
   final GQuery ul =  $("<ul><li id=\"1\">item 1</li><li id=\"2\">item 2</li><li id=\"3\">item 3</li><li id=\"4\">item 4</li></ul>").appendTo(document).as(Selectable).selectable();
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
    }.schedule(Speed.DEFAULT);
  }

}
