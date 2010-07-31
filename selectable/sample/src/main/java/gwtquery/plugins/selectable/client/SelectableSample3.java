package gwtquery.plugins.selectable.client;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.selectable.client.Selectable.Selectable;
import gwtquery.plugins.selectable.client.SelectableOptions.Tolerance;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SelectableSample3 implements EntryPoint {

    	private static List<String> names;
    	private static List<String> firstNames;
    	
    	static{
    	    names = new ArrayList<String>();
    	    names.add("Cobain");
    	    names.add("Morisson");
    	    names.add("Mercury");
    	    names.add("Jackson");
    	    names.add("Lennon");
    	    names.add("Cash");
            firstNames = new ArrayList<String>();
            firstNames.add("Kurt");
            firstNames.add("Jim");
            firstNames.add("Freddy");
            firstNames.add("Mickael");
            firstNames.add("John");
            firstNames.add("Johnny");
    	}
    	
    	
	public void onModuleLoad() {
	    
	    	FlexTable table = new FlexTable();
	    	
	    	initTable(table);
	    	  
		
		SelectableOptions o = new SelectableOptions();
		o.setFilter("tr");
		$("#selectable").as(Selectable).selectable(o);
		
		RootPanel.get("selectable-options").add(new SelectableOptionsPanel(o));

	}


	private void initTable(FlexTable table) {
	    // TODO Auto-generated method stub
	    
	}


	public static class SelectableOptionsPanel extends Composite {

		private static SelectableoptionsPanelUiBinder uiBinder = GWT
				.create(SelectableoptionsPanelUiBinder.class);
		
		@UiTemplate(value = "SelectableOptionsPanel.ui.xml")
		interface SelectableoptionsPanelUiBinder extends UiBinder<Widget, SelectableOptionsPanel> {
		}
		
		private SelectableOptions options;
		
		@UiField
		ListBox filterListBox;
		@UiField
		TextBox appendToBox;
		@UiField
		CheckBox disabledCheckBox;
		@UiField
		ListBox toleranceListBox;


		public SelectableOptionsPanel(SelectableOptions o) {
			options=o;
			initWidget(uiBinder.createAndBindUi(this));
			init();		
			
		}


		private void init() {
			
			filterListBox.addItem("tr");
			filterListBox.addItem("td");
			filterListBox.setSelectedIndex(0);
			
			appendToBox.setValue(options.getAppendTo(), false);
			
			disabledCheckBox.setValue(options.isDisabled(), false);
			
			toleranceListBox.addItem(Tolerance.TOUCH.name());
			toleranceListBox.addItem(Tolerance.FIT.name());
			toleranceListBox.setSelectedIndex(0);
			
		}
		
		@UiHandler(value = "filterListBox")
		public void onFilterChange(ChangeEvent e){
			options.setFilter(filterListBox.getValue(filterListBox.getSelectedIndex()));
		}
		
		@UiHandler(value = "appendToBox")
		public void onAppendToChange(ValueChangeEvent<String> e){
			options.setAppendTo(e.getValue());
		}
		
		@UiHandler(value = "disabledCheckBox")
		public void onDisabledChange(ValueChangeEvent<Boolean> e){
			options.setDisabled(e.getValue());
		}
		
		@UiHandler(value = "toleranceListBox")
		public void onToleranceChange(ChangeEvent e){
			String tolerance = toleranceListBox.getValue(toleranceListBox.getSelectedIndex());
			options.setTolerance(Tolerance.valueOf(tolerance));
		}
		
		
	}
}
