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
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.selectable.client.event.SelectedEvent;
import gwtquery.plugins.selectable.client.event.UnselectedEvent;
import gwtquery.plugins.selectable.client.event.SelectedEvent.SelectedEventHandler;
import gwtquery.plugins.selectable.client.event.UnselectedEvent.UnselectedEventHandler;

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

  private static class EventHandler implements SelectedEventHandler,
      UnselectedEventHandler {

    private HTMLTable table;

    public EventHandler(HTMLTable table) {
      this.table = table;
    }

    public void onSelected(SelectedEvent event) {
      
      UnselectedWidget w = (UnselectedWidget) getRelatedWidget(event.getSelectedElement());
      SelectedWidget selected = new SelectedWidget(w);
      table.setWidget(w.getRow(), w.getColumn(), selected);

    }

    public void onUnselected(UnselectedEvent event) {
      SelectedWidget w = (SelectedWidget) getRelatedWidget(event.getUnselectedElement());
      UnselectedWidget unselected = new UnselectedWidget(w);
      table.setWidget(w.getRow(), w.getColumn(), unselected);

    }
    
    private Widget getRelatedWidget(Element tableElement) {
      String id = tableElement.getId();
      int row = new Integer(id.substring(0, 1));
      int column = new Integer(id.substring(2, 3));
      return table.getWidget(row, column);
    }

  }

  public void onModuleLoad() {
    // create, init and attach the table
    final Grid table = new Grid(ROW_NUMBER, COLUMN_NUMBER);
    initTable(table);
    RootPanel.get("table").add(table);
    
    // create an instance of eventBus to bind events fired by the selectable plug-in
    EventBus handlerManager = new SimpleEventBus();
    // create an instance of EventHandler who manage the event when they will be fired
    EventHandler handler = new EventHandler(table);
    //bind events who interest us
    handlerManager.addHandler(SelectedEvent.TYPE, handler);
    handlerManager.addHandler(UnselectedEvent.TYPE, handler);
    
    // the table is now attached to the DOM, we can use Gquery
    // init options
    SelectableOptions o = new SelectableOptions();
    o.setFilter(".can-be-selected");

    // that all folks !
    $(table.getElement()).as(Selectable).selectable(o, handlerManager);

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
