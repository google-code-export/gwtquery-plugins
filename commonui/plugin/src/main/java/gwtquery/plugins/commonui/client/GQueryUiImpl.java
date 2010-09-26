package gwtquery.plugins.commonui.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Predicate;

public class GQueryUiImpl {

  public GQuery scrollParent(final GQueryUi gQueryUi) {
    GQuery scrollParent;
    
    if (gQueryUi.css("position").matches("(fixed)")){
      return GQuery.$(GQuery.document);
    }
    
    if (scrollParentPositionTest(gQueryUi)){
      scrollParent = gQueryUi.parents().filter(new Predicate() {
        
        public boolean f(Element e, int index) {
          GQuery $e = GQuery.$(e);
          String position = $e.css("position", true);
          //concatenate all overflow css rules
          String overflow = $e.css("overflow", true)+$e.css("overflow-x", true)+$e.css("overflow-y", true);
          return position.matches("(relative|absolute|fixed)") && overflow.matches("(auto|scroll)");
        }
      });
    
    }else{
      scrollParent = gQueryUi.parents().filter(new Predicate() {
        
        public boolean f(Element e, int index) {
          GQuery $e = GQuery.$(e);
          //concatenate all overflow css rules
          String overflow = $e.css("overflow", true)+$e.css("overflow-x", true)+$e.css("overflow-y", true);
          return overflow.contains("auto") || overflow.contains("scroll");
        }
      });
    }
    return scrollParent.length() > 0 ? new GQuery(scrollParent.get(0)) : GQuery.$(GQuery.document);

  }
  
  protected boolean scrollParentPositionTest(GQueryUi gQueryUi){
    return gQueryUi.css("position").matches("(absolute)");
  }

}
