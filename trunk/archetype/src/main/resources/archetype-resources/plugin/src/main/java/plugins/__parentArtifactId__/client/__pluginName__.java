#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.plugins.${parentArtifactId}.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.Plugin;
import static com.google.gwt.query.client.plugins.Effects.Effects;

/**
 * ${pluginName} plugin for GwtQuery
 */
public class ${pluginName} extends GQuery {


  // A shortcut to the class 
  public static final Class<${pluginName}> ${pluginName} = ${pluginName}.class;

  // Register the ${artifactId} in GQuery
  static {
    GQuery.registerPlugin(${pluginName}.class, new Plugin<${pluginName}>() {
      public ${pluginName} init(GQuery gq) {
        return new ${pluginName}(gq);
      }
    });
  }

  // Initialization
  public ${pluginName}(GQuery gq) {
    super(gq);
  }

  // This pluggin adds this to the set of available methods in GQuery
  public ${pluginName} apply() {
    hover(new Function() {
      public void f(Element e) {
        ${symbol_dollar}(e).css("color", "blue").as(Effects).stop().animate("fontSize: '+=10px'");
      }
    }, new Function() {
      public void f(Element e) {
        ${symbol_dollar}(e).css("color", "").as(Effects).stop().animate("fontSize: '-=10px'");
      }
    });
    return this;
  }

}
