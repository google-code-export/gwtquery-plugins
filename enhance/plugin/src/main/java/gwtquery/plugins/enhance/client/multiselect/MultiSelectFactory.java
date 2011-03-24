package gwtquery.plugins.enhance.client.multiselect;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.asArray;

import java.util.ArrayList;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.widgets.WidgetFactory;
import com.google.gwt.query.client.plugins.widgets.WidgetsUtils;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class MultiSelectFactory implements WidgetFactory<MultiSelect> {
  
  public MultiSelect create(Element e) {
    SelectElement se = null;
    final ArrayList<String> selected = new ArrayList<String>();
    final ArrayList<String> unselected = new ArrayList<String>();
    
    if ($(e).filter("select[multiple]").get(0) != null) {
      se = SelectElement.as(e);
      for (OptionElement oe : asArray(se.getOptions())) {
        if (oe.getValue().isEmpty()) {
          oe.setValue(oe.getText().trim());
        }
        if (oe.isSelected()) {
          selected.add(oe.getValue());
        } else {
          unselected.add(oe.getValue());
        }
      }
    } else if ($(e).children().size() > 0) {
      $(e).children().each(new Function(){
        public void f(Element e) {
          unselected.add($(e).text());
        }
      });
    }

    if (se != null || unselected.size() + selected.size() > 0) {
      MultiSelect multiselect = new MultiSelect(unselected, selected, se);
      WidgetsUtils.hideAndAfter(e, multiselect);
      int w = $(e).width();
      int h = $(e).height();
      if ( w > 0) {
        $(multiselect).width(w * 2);  
      }
      if (h > 0) {
        $(multiselect).height(h * 2);  
      }
      return multiselect;
    }
    return null;
  }
}