package gwtquery.plugins.ratings.client;

import static gwtquery.plugins.ratings.client.Ratings.*;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.query.client.GQUtils;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.Effects.Speed;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
/**
 * Test class for Ratings plugin
 */
public class RatingsTest extends GWTTestCase {

  public String getModuleName() {
    return "gwtquery.plugins.ratings.Ratings";
  }

  public void testRatingsApply() {
    // execute the plugin method
    final GQuery g =  $("<div></div>").appendTo(document).as(Ratings).rate();
  }
}
