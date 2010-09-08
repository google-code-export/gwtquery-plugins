package gwtquery.plugins.ratings.client;

import static com.google.gwt.query.client.GQuery.*;
import static com.google.gwt.query.client.GQuery.document;
import static gwtquery.plugins.ratings.client.Ratings.Ratings;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;

/**
 * Example code the GQuery Ratings plugin.
 */
public class RatingsSample implements EntryPoint {

  public void onModuleLoad() {
    // Add a button to enhance the page.
    $("<h2>Try It</h2><button>Enhance</button>").appendTo(document);
    
    $("button").toggle(new Function() {
      public void f(Element e) {
        $("input.star").as(Ratings).rate();
        $(e).text("Un-Enhance");
      }
    }, new Function() {
      public void f(Element e) {
        $("input.star").as(Ratings).unrate();
        $(e).text("Enhance");
      }
    });
    
    $(window).load(new Function(){
      public void f(Element e) {
        System.out.println("addasfsaf");
      }
    });

    
  }
}
