package gwtquery.plugins.draggable.client;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.draggable.client.DraggableOptions.AxisOption;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;

/**
 * Example code BasePlugin plugin for GwtQuery
 */
public class DraggableSample2 implements EntryPoint {

  public void onModuleLoad() {
    

    DraggableOptions o = new DraggableOptions();
    $("#draggable").as(Draggable).draggable(o);

    RootPanel.get("draggable-options").add(new DraggableOptionsPanel(o));
    
  }
  
  public static class DraggableOptionsPanel extends Composite {

    private static DraggableOptionsPanelUiBinder uiBinder = GWT
        .create(DraggableOptionsPanelUiBinder.class);

    @UiTemplate(value = "DraggableOptionsPanel.ui.xml")
    interface DraggableOptionsPanelUiBinder extends
        UiBinder<Widget, DraggableOptionsPanel> {
    }

    private DraggableOptions options;

    @UiField
    ListBox helperListBox;
    @UiField
    ListBox axisListBox;
    @UiField
    TextBox delayBox;
    @UiField
    TextBox distanceBox;
    @UiField
    CheckBox disabledCheckBox;
    @UiField
    CheckBox handleCheckBox;
    @UiField
    TextBox opacityBox;


    public DraggableOptionsPanel(DraggableOptions o) {
      options = o;
      initWidget(uiBinder.createAndBindUi(this));
      init();

    }

    private void init() {

      helperListBox.addItem(HelperType.ORIGINAL.name());
      helperListBox.addItem(HelperType.CLONE.name());
      helperListBox.addItem(HelperType.ELEMENT.name());
      helperListBox.setSelectedIndex(0);
      
      delayBox.setValue(""+options.getDelay(), false);
      
      distanceBox.setValue(""+options.getDistance(), false);

      disabledCheckBox.setValue(options.isDisabled(), false);

      handleCheckBox.setValue(false, false);

      axisListBox.addItem(AxisOption.NONE.name());
      axisListBox.addItem(AxisOption.X_AXIS.name());
      axisListBox.addItem(AxisOption.Y_AXIS.name());
      axisListBox.setSelectedIndex(0);
      
      opacityBox.setValue(""+options.getOpacity());

    }

    @UiHandler(value = "helperListBox")
    public void onHelperChange(ChangeEvent e) {
      HelperType type = HelperType.valueOf(helperListBox.getValue(helperListBox.getSelectedIndex()));
      
      if (type == HelperType.ELEMENT){
        GQuery helper = $("<div class=\"myHelper\">Helper</div>");
        options.setHelper(helper.get(0));
      }else{
        options.setHelper(type);
      }
    }

    @UiHandler(value = "axisListBox")
    public void onAxisChange(ChangeEvent e) {
      AxisOption axis = AxisOption.valueOf(axisListBox.getValue(axisListBox.getSelectedIndex()));
      options.setAxis(axis);
    }
    
    @UiHandler(value = "delayBox")
    public void onDelayChange(ValueChangeEvent<String> e) {
      options.setDelay(new Integer(e.getValue()));
    }
    
    @UiHandler(value = "distanceBox")
    public void onDistanceChange(ValueChangeEvent<String> e) {
      Integer distance;
      try{
        distance = new Integer(e.getValue());
      }catch (NumberFormatException ex){
        Window.alert("Please specify a correct number for distance");
        return;
      }
      options.setDistance(distance);
    }

    @UiHandler(value = "opacityBox")
    public void onOpacityChange(ValueChangeEvent<String> e) {
      Float opacity;
      try{
        opacity = new Float(e.getValue());
      }catch (NumberFormatException ex){
        Window.alert("Please specify a correct number for opacity");
        return;
      }
      if (opacity >1){
        Window.alert("Opacity must be below than 1.");
        return;
      }
      options.setOpacity(opacity);
    }
    
    @UiHandler(value = "disabledCheckBox")
    public void onDisabledChange(ValueChangeEvent<Boolean> e) {
      options.setDisabled(e.getValue());
    }

    @UiHandler(value = "handleCheckBox")
    public void onMultiSelectChange(ValueChangeEvent<Boolean> e) {
      if (e.getValue()){
        options.setHandle("#handle");
      }else{
        options.setHandle(null);
      }
    }

  }
}
