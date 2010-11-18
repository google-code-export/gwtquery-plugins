package gwtquery.plugins.droppable.client.testoptionssample;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.droppable.client.Droppable.Droppable;
import static gwtquery.plugins.droppable.client.testoptionssample.TestOptionsSample.EVENT_BUS;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.droppable.client.DroppableOptions;
import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;
import gwtquery.plugins.droppable.client.DroppableOptions.DroppableTolerance;
import gwtquery.plugins.droppable.client.testoptionssample.ResetOptionEvent.ResetOptionEventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DroppableOptionsPanel extends Composite implements ResetOptionEventHandler{

  @UiTemplate(value = "DroppableOptionsPanel.ui.xml")
  interface DroppableOptionsPanelUiBinder extends
      UiBinder<Widget, DroppableOptionsPanel> {
  }

  private static DroppableOptionsPanelUiBinder uiBinder = GWT
      .create(DroppableOptionsPanelUiBinder.class);

  private static Map<String, AcceptFunction> acceptFunctions;
  private static String NONE_CSS_CLASS="none";
  private static List<String> classNames;

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
    classNames = new ArrayList<String>();
    classNames.add(NONE_CSS_CLASS);
    classNames.add("orange-background");
    classNames.add("yellow-background");
    classNames.add("white-background");
  }

  private Element droppable;

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
  
  @UiField
  ListBox activeClassListBox;
  
  @UiField
  ListBox hoverClassListBox;
  
  @UiField
  ListBox draggableHoverClassListBox;

  public DroppableOptionsPanel(Element droppable) {
    this.droppable = droppable;
    initWidget(uiBinder.createAndBindUi(this));
    EVENT_BUS.addHandler(ResetOptionEvent.TYPE, this);
    //use a deferred command to ensure to init the object when the element is droppable
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      public void execute() {
        init();
      }
    });

  }

  @UiHandler(value = "toleranceListBox")
  public void onToleranceChange(ChangeEvent e) {
    DroppableTolerance tolerance = DroppableTolerance.valueOf(toleranceListBox
        .getValue(toleranceListBox.getSelectedIndex()));
    getOptions().setTolerance(tolerance);
  }

  @UiHandler(value = "disabledCheckBox")
  public void onDisabledChange(ValueChangeEvent<Boolean> e) {
    getOptions().setDisabled(e.getValue());
  }

  @UiHandler(value = "greedyCheckBox")
  public void onGreedyChange(ValueChangeEvent<Boolean> e) {
    getOptions().setGreedy(e.getValue());
  }

  @UiHandler(value = "scopeBox")
  public void onScopeChange(ValueChangeEvent<String> e) {
    $("#droppable").as(Droppable).changeScope(e.getValue());
  }

  @UiHandler(value = "acceptFunctionListBox")
  public void onAcceptFunctionChange(ChangeEvent e) {
    String key = acceptFunctionListBox.getValue(acceptFunctionListBox
        .getSelectedIndex());
    getOptions().setAccept(acceptFunctions.get(key));
  }

  @UiHandler(value = "activeClassListBox")
  public void onActiveClassChange(ChangeEvent e) {
    String activeClass = activeClassListBox.getValue(activeClassListBox
        .getSelectedIndex());
    getOptions().setActiveClass(activeClass);
  }

  
  @UiHandler(value = "hoverClassListBox")
  public void onHoverClassChange(ChangeEvent e) {
    String hoverClass = hoverClassListBox.getValue(hoverClassListBox
        .getSelectedIndex());
    getOptions().setDroppableHoverClass(hoverClass);
  }
  
  @UiHandler(value = "draggableHoverClassListBox")
  public void onDraggableHoverClassChange(ChangeEvent e) {
    String draggablehoverClass = draggableHoverClassListBox.getValue(draggableHoverClassListBox
        .getSelectedIndex());
    getOptions().setDraggableHoverClass(draggablehoverClass);
  }

  
  private DroppableOptions getOptions() {
    return $(droppable).as(Droppable).options();
  }

  private void init() {
    DroppableOptions options = getOptions();
    
    //toleranceListBox
    toleranceListBox.addItem("FIT (draggable overlaps the droppable entirely)", DroppableTolerance.FIT.name());
    toleranceListBox.addItem("INTERSECT (draggable overlaps the droppable at least 50%)", DroppableTolerance.INTERSECT.name());
    toleranceListBox.addItem("POINTER (mouse pointer overlaps the droppable)", DroppableTolerance.POINTER.name());
    toleranceListBox.addItem("TOUCH (draggable touch the droppable)", DroppableTolerance.TOUCH.name());
    toleranceListBox.setSelectedIndex(1);

    scopeBox.setValue(options.getScope(), false);

    disabledCheckBox.setValue(options.isDisabled(), false);

    greedyCheckBox.setValue(options.isGreedy(), false);

    acceptFunctionListBox.addItem("Accept all", "AcceptAll");
    acceptFunctionListBox.addItem("Accept Draggable1", "AcceptDraggable1");
    acceptFunctionListBox.addItem("Accept Draggable2", "AcceptDraggable2");
    
    initClassNames(hoverClassListBox, options.getDroppableHoverClass(), "hover-");
    initClassNames(activeClassListBox, options.getActiveClass(),  "activate-");
    
    //green-background
    draggableHoverClassListBox.addItem(NONE_CSS_CLASS);
    draggableHoverClassListBox.addItem("green-background");
    
    
  }

  private void initClassNames(ListBox classListBox, String value, String prefix) {
    for (int i=0; i < classNames.size(); i++){
      String cssClass = classNames.get(i);
      String realCssClass = prefix+cssClass;
      classListBox.addItem(cssClass, realCssClass);
      if (realCssClass.equals(value)){
        classListBox.setSelectedIndex(i);
      }
    }
    
  }

  public void onResetOption(ResetOptionEvent event) {
    if(event.getOptionsPanel() == this){
      $(droppable).as(Droppable).options(new DroppableOptions());
      init();
    }
    
  }
}