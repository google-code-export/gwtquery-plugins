package gwtquery.plugins.ratings.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.document;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.query.client.GQuery;

import static gwtquery.plugins.ratings.client.Ratings.Ratings;
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
