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

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.selectable.client.Selectable.Selectable;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SelectableSample3 implements EntryPoint {

  private static final int ROW_NUMBER = 5;
  private static final int COLUMN_NUMBER = 5;

  private static class UnselectedWidget extends Composite {

    String value;
    int row;
    int column;

    public UnselectedWidget() {
    }

    public UnselectedWidget(String value, int row, int column) {
      this.value = value;
      this.row = row;
      this.column = column;
      init(null);
    }

    public UnselectedWidget(SelectedWidget w) {
      value = w.getValue();
      row = w.getRow();
      column = w.getColumn();
      init(w);
    }

    public int getColumn() {
      return column;
    }

    public int getRow() {
      return row;
    }

    public String getValue() {
      return value;
    }

    protected String getInfo() {
      return "I'm an instance of <b>*</b>Un<b>*</b>selected Widget class ";
    }

    protected void init(Widget w) {
      VerticalPanel panel = new VerticalPanel();
      panel.add(new HTML(value));
      panel.add(new HTML(getInfo()));
      panel.getElement().setId(row + "_" + column);
      initWidget(panel);

      if (w != null) {
        addStyleName(w.getStyleName());
      } else {
        addStyleName("can-be-selected");
      }

    }

  }

  private static class SelectedWidget extends UnselectedWidget {

    public SelectedWidget(UnselectedWidget w) {
      value = w.getValue();
      row = w.getRow();
      column = w.getColumn();
      init(w);
    }

    protected String getInfo() {
      return "I'm an instance of Selected Widget class ";
    }

  }

  public void onModuleLoad() {

    final Grid table = new Grid(ROW_NUMBER, COLUMN_NUMBER);
    initTable(table);
    RootPanel.get("table").add(table);

    // the table is now attached to the DOM, we can use Gquery
    // init options
    SelectableOptions o = new SelectableOptions();
    o.setFilter(".can-be-selected");
    o.setOnSelected(new Function() {
      @Override
      public void f(Element e) {
        UnselectedWidget w = (UnselectedWidget) getRelatedWidget(e, table);
        SelectedWidget selected = new SelectedWidget(w);
        table.setWidget(w.getRow(), w.getColumn(), selected);
      }
    });
    o.setOnUnselected(new Function() {
      @Override
      public void f(Element e) {
        SelectedWidget w = (SelectedWidget) getRelatedWidget(e, table);
        UnselectedWidget unselected = new UnselectedWidget(w);
        table.setWidget(w.getRow(), w.getColumn(), unselected);
      }
    });
    // that all folks !
    $(table.getElement()).as(Selectable).selectable(o);

  }

  private Widget getRelatedWidget(Element tableElement, final HTMLTable table) {
    String id = tableElement.getId();
    int row = new Integer(id.substring(0, 1));
    int column = new Integer(id.substring(2, 3));
    return table.getWidget(row, column);
  }

  private void initTable(Grid table) {

    for (int i = 0; i < ROW_NUMBER; i++) {
      for (int j = 0; j < COLUMN_NUMBER; j++) {
        UnselectedWidget w = new UnselectedWidget("" + i + j, i, j);
        table.setWidget(i, j, w);
      }
    }
    
    table.setCellSpacing(5);
    table.setBorderWidth(1);

  }

}
