package gwtquery.plugins.droppable.client;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;
import static gwtquery.plugins.droppable.client.Droppable.Droppable;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.draggable.client.DraggableOptions.AxisOption;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.DraggableSample2.DraggableOptionsPanel;
import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;
import gwtquery.plugins.droppable.client.DroppableOptions.DroppableFunction;
import gwtquery.plugins.droppable.client.DroppableOptions.DroppableTolerance;
import gwtquery.plugins.droppable.client.events.DragDropInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Example code BasePlugin plugin for GwtQuery
 */
public class DroppableSample implements EntryPoint {
  
  static class DroppableOptionsPanel extends Composite{
    
    @UiTemplate(value = "DroppableOptionsPanel.ui.xml")
    interface DroppableOptionsPanelUiBinder extends
        UiBinder<Widget, DroppableOptionsPanel> {
    }

    private static DroppableOptionsPanelUiBinder uiBinder = GWT
        .create(DroppableOptionsPanelUiBinder.class);

    private static Map<String, AcceptFunction> acceptFunctions;
   // private static Map<String, CursorAt> cursorAtOptions;

    static {
      acceptFunctions = new HashMap<String, AcceptFunction>();
      acceptFunctions.put("AcceptAll", null);
      acceptFunctions.put("AcceptDraggable1", new AcceptFunction() {
        public boolean acceptDrop(Element droppable, Element draggable) {
          return ("draggable1".equals(draggable.getId()));
        }
      });
      acceptFunctions.put("AcceptDraggable2", new AcceptFunction() {
        public boolean acceptDrop(Element droppable, Element draggable) {
          return ("draggable2".equals(draggable.getId()));
        }
      });
    }

    private DroppableOptions options;
    
    @UiField
    CheckBox disabledCheckBox;
    @UiField
    CheckBox greedyCheckBox;
    @UiField
    TextBox scopeBox;
    @UiField
    ListBox toleranceListBox;
    @UiField
    ListBox acceptFunctionListBox;
    
    

    public DroppableOptionsPanel(DroppableOptions o) {
      options = o;
      initWidget(uiBinder.createAndBindUi(this));
      init();

    }

    @UiHandler(value = "toleranceListBox")
    public void onToleranceChange(ChangeEvent e) {
      DroppableTolerance tolerance = DroppableTolerance.valueOf(toleranceListBox.getValue(toleranceListBox
          .getSelectedIndex()));
      options.setTolerance(tolerance);
    }
    
    @UiHandler(value = "disabledCheckBox")
    public void onDisabledChange(ValueChangeEvent<Boolean> e) {
      options.setDisabled(e.getValue());
    }
    
    @UiHandler(value = "greedyCheckBox")
    public void onGreedyChange(ValueChangeEvent<Boolean> e) {
      options.setGreedy(e.getValue());
    }
    
    @UiHandler(value = "scopeBox")
    public void onScopeChange(ValueChangeEvent<String> e) {
      options.setScope(e.getValue());
    }
    
    @UiHandler(value = "acceptFunctionListBox")
    public void onAcceptFunctionChange(ChangeEvent e) {
      String key = acceptFunctionListBox.getValue(acceptFunctionListBox
          .getSelectedIndex());
      options.setAccept(acceptFunctions.get(key));
    }
    


    private void init() {
      int i = 0;
      for (DroppableTolerance t : DroppableTolerance.values()) {
        toleranceListBox.addItem(t.name());
        if (t == options.getTolerance()) {
          toleranceListBox.setSelectedIndex(i);
        }
        i++;
      }

      scopeBox.setValue(options.getScope(), false);
      
      disabledCheckBox.setValue(options.isDisabled(), false);
      
      greedyCheckBox.setValue(options.isGreedy(), false);
      
      acceptFunctionListBox.addItem("Accept all", "AcceptAll");
      acceptFunctionListBox.addItem("Accept Draggable1", "AcceptDraggable1");
      acceptFunctionListBox.addItem("Accept Draggable2", "AcceptDraggable2");
    }

  }

  public void onModuleLoad() {
    
    
    $("#draggable").as(Draggable).draggable();
    
    DroppableOptions options = new DroppableOptions();
    options.setOnDrop(new DroppableFunction() {
      public void f(Element droppable, DragDropInfo info) {
        $(droppable).addClass("ui-state-highlight");
        $("p", droppable).html("Dropped!");
      }
    });
    $("#droppable").as(Droppable).droppable(options);
    
    RootPanel.get("droppable-options").add(new DroppableOptionsPanel(options));
  }
}
