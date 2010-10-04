package gwtquery.plugins.draggable.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.datepicker.client.DatePicker;

import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

import java.util.Date;

/**
 * Example code BasePlugin plugin for GwtQuery
 */
public class GWTIntegrationSample implements EntryPoint {

	

	public void onModuleLoad() {

	  
		RootPanel.get("gwtIntegrationSampleDiv").add(new DraggableWidget<DecoratorPanel>(createDecoratedForm()));
		
		RootPanel.get("gwtIntegrationSampleDiv").add(new DraggableWidget<Widget>(createDynamicTree()));
		
		RootPanel.get("gwtIntegrationSampleDiv").add(new DraggableWidget<TabPanel>(createTabPanel()));
		
		RootPanel.get("gwtIntegrationSampleDiv").add(new DraggableWidget<VerticalPanel>(createDatePanel()));
		

	}
	
	/**
	 * Create a Dynamic tree.
	 * The code come from the GWT show case : http://gwt.google.com/samples/Showcase/Showcase.html#!CwTree
	 * @return
	 * http://gwt.google.com/samples/Showcase/Showcase.html#!CwDecoratorPanel
	 * @return
	 */
	private DecoratedTabPanel createTabPanel(){
		// Create a tab panel
	    DecoratedTabPanel tabPanel = new DecoratedTabPanel();
	    tabPanel.setWidth("400px");
	    tabPanel.setAnimationEnabled(true);

	    // Add a home tab
	    String[] tabTitles = {"Home", "GWT Logo", "More info"};
	    HTML homeText = new HTML("Click one of the tabs to see more content. <br/> You can drag me now !");
	    tabPanel.add(homeText, tabTitles[0]);

	    // Add a tab with an image
	    /*VerticalPanel vPanel = new VerticalPanel();
	    vPanel.add(new Image(Showcase.images.gwtLogo()));*/
	    //TODO add gwt logo
	    tabPanel.add(new HTML("TODO add GWT LOGO"), tabTitles[1]);

	    // Add a tab
	    HTML moreInfo = new HTML("Tabs are highly customizable using CSS.");
	    tabPanel.add(moreInfo, tabTitles[2]);

	    // Return the content
	    tabPanel.selectTab(0);
	    tabPanel.ensureDebugId("cwTabPanel");
	    return tabPanel;

	}
	/**
	 * Create a Decorated Form
	 * The code come from the GWT show case : http://gwt.google.com/samples/Showcase/Showcase.html#!CwDecoratorPanel
	 * 
	 * @return
	 */
	private DecoratorPanel createDecoratedForm(){
		// Create a table to layout the form options
	    FlexTable layout = new FlexTable();
	    layout.setCellSpacing(6);
	    FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

	    // Add a title to the form
	    layout.setHTML(0, 0, "Enter Search Criteria(You can now drag me !)");
	    cellFormatter.setColSpan(0, 0, 2);
	    cellFormatter.setHorizontalAlignment(0, 0,
	        HasHorizontalAlignment.ALIGN_CENTER);

	    // Add some standard form options
	    layout.setHTML(1, 0, "Name");
	    layout.setWidget(1, 1, new TextBox());
	    layout.setHTML(2, 0, "Description");
	    layout.setWidget(2, 1, new TextBox());

	    // Wrap the content in a DecoratorPanel
	    DecoratorPanel decPanel = new DecoratorPanel();
	    decPanel.setWidget(layout);
	    return decPanel;

	}
	
	private VerticalPanel createDatePanel(){
	// Create a basic date picker
    DatePicker datePicker = new DatePicker();
    final Label text = new Label();

    // Set the value in the text box when the user selects a date
    datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
      public void onValueChange(ValueChangeEvent<Date> event) {
        Date date = event.getValue();
        String dateString = DateTimeFormat.getMediumDateFormat().format(date);
        text.setText(dateString);
      }
    });

    // Set the default value
    datePicker.setValue(new Date(), true);
    
    // Combine the widgets into a panel and return them
    VerticalPanel vPanel = new VerticalPanel();
    vPanel.add(new HTML("Permanent DatePicker:"));
    vPanel.add(text);
    vPanel.add(datePicker);
    return vPanel;

	}

	/**
	 * Create a Dynamic tree.
	 * The code come from the GWT show case : http://gwt.google.com/samples/Showcase/Showcase.html#!CwTree
	 * @return
	 */
	private Widget createDynamicTree() {
		// Create a new tree
		Tree dynamicTree = new Tree();

		// Add some default tree items
		for (int i = 0; i < 5; i++) {
			TreeItem item = dynamicTree.addItem("Item " + i);

			// Temporarily add an item so we can expand this node
			item.addItem("");
		}

		// Add a handler that automatically generates some children
		dynamicTree.addOpenHandler(new OpenHandler<TreeItem>() {
			public void onOpen(OpenEvent<TreeItem> event) {
				TreeItem item = event.getTarget();
				if (item.getChildCount() == 1) {
					// Close the item immediately
					item.setState(false, false);

					// Add a random number of children to the item
					String itemText = item.getText();
					int numChildren = Random.nextInt(5) + 2;
					for (int i = 0; i < numChildren; i++) {
						TreeItem child = item.addItem(itemText + "." + i);
						child.addItem("");
					}

					// Remove the temporary item when we finish loading
					item.getChild(0).remove();

					// Reopen the item
					item.setState(true, false);
				}
			}
		});

		// Return the tree (decorated)
		 DecoratorPanel decPanel = new DecoratorPanel();
		 decPanel.setWidget(dynamicTree);
		return decPanel;
	}

}
