package gwtquery.plugins.helloworld.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Plugin;
import static com.google.gwt.query.client.plugins.Effects.Effects;

/**
 * HelloWorld for GwtQuery
 */
public class HelloWorld extends GQuery {


  // A shortcut to the class 
  public static final Class<HelloWorld> HelloWorld = HelloWorld.class;

  // Register the plugin in GQuery
  static {
    GQuery.registerPlugin(HelloWorld.class, new Plugin<HelloWorld>() {
      public HelloWorld init(GQuery gq) {
        return new HelloWorld(gq);
      }
    });
  }

  // Initialization
  public HelloWorld(GQuery gq) {
    super(gq);
  }

  // This pluggin adds this to the set of available methods in GQuery
  public HelloWorld apply() {
    hover(new Function() {
      public void f(Element e) {
        $(e).css("color", "blue").as(Effects).stop().animate("fontSize: '+=10px'");
      }
    }, new Function() {
      public void f(Element e) {
        $(e).css("color", "").as(Effects).stop().animate("fontSize: '-=10px'");
      }
    });
    return this;
  }

}
