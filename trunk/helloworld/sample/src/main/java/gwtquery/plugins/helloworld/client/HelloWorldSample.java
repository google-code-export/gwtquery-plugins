package gwtquery.plugins.helloworld.client;

import static com.google.gwt.query.client.GQuery.*;
import static gwtquery.plugins.helloworld.client.HelloWorld.HelloWorld;

import com.google.gwt.core.client.EntryPoint;

/**
 * Example code BasePlugin plugin for GwtQuery
 */
public class HelloWorldSample implements EntryPoint {

  public void onModuleLoad() {
    
    $("div").as(HelloWorld).apply();
    
  }
}
