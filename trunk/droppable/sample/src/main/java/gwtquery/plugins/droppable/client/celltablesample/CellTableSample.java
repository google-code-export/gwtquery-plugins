package gwtquery.plugins.droppable.client.celltablesample;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.events.DragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStartEvent.DragStartEventHandler;
import gwtquery.plugins.droppable.client.DroppableOptions;
import gwtquery.plugins.droppable.client.celltablesample.ContactDatabase.Category;
import gwtquery.plugins.droppable.client.celltablesample.ContactDatabase.ContactInfo;
import gwtquery.plugins.droppable.client.gwt.DragAndDropCellTable;
import gwtquery.plugins.droppable.client.gwt.DragAndDropColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * Take the cell table example of the GWT showcase and make all cell droppable
 * and draggable
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class CellTableSample implements EntryPoint {

  private static CellTableSampleUiBinder uiBinder = GWT
      .create(CellTableSampleUiBinder.class);

  interface CellTableSampleUiBinder extends UiBinder<Widget, CellTableSample> {
  }
  
  public enum CellType{
    CHECK_BOX,FIRST_NAME,LAST_NAME,CATEGORY,ADDRESS;
  }
  static interface Templates extends SafeHtmlTemplates{
    Templates INSTANCE = GWT.create(Templates.class);

    @Template("<div id='dragHelper' style='border:1px solid black; background-color:#ffffff'></div>")
    SafeHtml outerHelper();
  }

  /**
   * The constants used in this Content Widget.
   */
  public static interface CwConstants extends Constants {
    @DefaultStringValue(value = "Address")
    String cwCellTableColumnAddress();

    @DefaultStringValue(value = "Category")
    String cwCellTableColumnCategory();

    @DefaultStringValue(value = "First Name")
    String cwCellTableColumnFirstName();

    @DefaultStringValue(value = "Last Name")
    String cwCellTableColumnLastName();

  }

  public CellTableSample() {
    constants = GWT.create(CwConstants.class);
  }
  
  private static final String contactImageHtml = AbstractImagePrototype.create(Resource.INSTANCE.contact()).getHTML();
  /**
   * The main CellTable.
   */
  @UiField(provided = true)
  DragAndDropCellTable<ContactInfo> cellTable;

  /**
   * The pager used to change the range of data.
   */
  @UiField(provided = true)
  SimplePager pager;

  /**
   * An instance of the constants.
   */
  private final CwConstants constants;

  /**
   * Initialize this example.
   */
  public void onModuleLoad() {
    Resource.INSTANCE.css().ensureInjected();
    // Create a DragAndDropCellTable.

    // Set a key provider that provides a unique key for each contact. If key is
    // used to identify contacts when fields (such as the name and address)
    // change.
    cellTable = new DragAndDropCellTable<ContactInfo>(
        ContactDatabase.ContactInfo.KEY_PROVIDER);

    // Create a Pager to control the table.
    SimplePager.Resources pagerResources = GWT
        .create(SimplePager.Resources.class);
    pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
    pager.setDisplay(cellTable);

    // Add a selection model so we can select cells.
    final MultiSelectionModel<ContactInfo> selectionModel = new MultiSelectionModel<ContactInfo>(
        ContactDatabase.ContactInfo.KEY_PROVIDER);
    cellTable.setSelectionModel(selectionModel);

    // Initialize the columns.
    initTableColumns(selectionModel);

    // Add the CellList to the adapter in the database.
    ContactDatabase.get().addDataDisplay(cellTable);
    
    // fill the helper when the drag start
    cellTable.addDragStartHandler(new DragStartEventHandler() {
      
      public void onDragStart(DragStartEvent event) {
        ContactInfo contact = event.getDraggableValue();
        Element helper = event.getHelper();
        SafeHtmlBuilder sb = new SafeHtmlBuilder();
        sb.appendHtmlConstant("<table width='200px'><tr><td rowspan='3'>");
        sb.appendHtmlConstant(contactImageHtml);
        sb.appendHtmlConstant("</td><td style='font-size:95%;'>");
        sb.appendEscaped(contact.getFullName());
        sb.appendHtmlConstant("</td></tr><tr><td>");
        sb.appendEscaped(contact.getAddress());
        sb.appendHtmlConstant("</td></tr></table>");
        helper.setInnerHTML(sb.toSafeHtml().asString());
        
      }
    });

    // Create the UiBinder.
    Widget w = uiBinder.createAndBindUi(this);
    RootPanel.get("sample").add(w);
  }

  /**
   * Add the columns to the table.
   */
  private void initTableColumns(final SelectionModel<ContactInfo> selectionModel) {
    
    // Checkbox column. This table will uses a checkbox column for selection.
    // Alternatively, you can call cellTable.setSelectionEnabled(true) to enable
    // mouse selection.
    DragAndDropColumn<ContactInfo, Boolean> checkColumn = new DragAndDropColumn<ContactInfo, Boolean>(
        new CheckboxCell(true)) {
      @Override
      public Boolean getValue(ContactInfo object) {
        // Get the value from the selection model.
        return selectionModel.isSelected(object);
      }
    };
    checkColumn.setFieldUpdater(new FieldUpdater<ContactInfo, Boolean>() {
      public void update(int index, ContactInfo object, Boolean value) {
        // Called when the user clicks on a checkbox.
        selectionModel.setSelected(object, value);
      }
    });
    DroppableOptions droppableOptions = checkColumn.getDroppableOptions();
    initCommonDroppableOptions(droppableOptions);
    DraggableOptions draggableOptions = checkColumn.getDraggableOptions();
    initCommonDraggableOptions(draggableOptions);
    cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br>")); 
    
    
    // First name.
    DragAndDropColumn<ContactInfo, String> firstNameColumn = new DragAndDropColumn<ContactInfo, String>(
        new EditTextCell()) {
      @Override
      public String getValue(ContactInfo object) {
        return object.getFirstName();
      }
    };
    droppableOptions = firstNameColumn.getDroppableOptions();
    initCommonDroppableOptions(droppableOptions);
    draggableOptions = firstNameColumn.getDraggableOptions();
    initCommonDraggableOptions(draggableOptions);
    cellTable
        .addColumn(firstNameColumn, constants.cwCellTableColumnFirstName());
    firstNameColumn.setFieldUpdater(new FieldUpdater<ContactInfo, String>() {
      public void update(int index, ContactInfo object, String value) {
        // Called when the user changes the value.
        object.setFirstName(value);
        ContactDatabase.get().refreshDisplays();
      }
    });

    // Last name.
    DragAndDropColumn<ContactInfo, String> lastNameColumn = new DragAndDropColumn<ContactInfo, String>(
        new EditTextCell()) {
      @Override
      public String getValue(ContactInfo object) {
        return object.getLastName();
      }
    };
    droppableOptions = lastNameColumn.getDroppableOptions();
    initCommonDroppableOptions(droppableOptions);
    draggableOptions = lastNameColumn.getDraggableOptions();
    initCommonDraggableOptions(draggableOptions);
    cellTable.addColumn(lastNameColumn, constants.cwCellTableColumnLastName());
    lastNameColumn.setFieldUpdater(new FieldUpdater<ContactInfo, String>() {
      public void update(int index, ContactInfo object, String value) {
        // Called when the user changes the value.
        object.setLastName(value);
        ContactDatabase.get().refreshDisplays();
      }
    });

    // Category.
    final Category[] categories = ContactDatabase.get().queryCategories();
    List<String> categoryNames = new ArrayList<String>();
    for (Category category : categories) {
      categoryNames.add(category.getDisplayName());
    }
    SelectionCell categoryCell = new SelectionCell(categoryNames);
    DragAndDropColumn<ContactInfo, String> categoryColumn = new DragAndDropColumn<ContactInfo, String>(
        categoryCell) {
      @Override
      public String getValue(ContactInfo object) {
        return object.getCategory().getDisplayName();
      }
    };
    droppableOptions = categoryColumn.getDroppableOptions();
    initCommonDroppableOptions(droppableOptions);
    draggableOptions = categoryColumn.getDraggableOptions();
    initCommonDraggableOptions(draggableOptions);
    cellTable.addColumn(categoryColumn, constants.cwCellTableColumnCategory());
    categoryColumn.setFieldUpdater(new FieldUpdater<ContactInfo, String>() {
      public void update(int index, ContactInfo object, String value) {
        for (Category category : categories) {
          if (category.getDisplayName().equals(value)) {
            object.setCategory(category);
          }
        }
        ContactDatabase.get().refreshDisplays();
      }
    });

    // Address.
    DragAndDropColumn<ContactInfo, String> addressColumn = new DragAndDropColumn<ContactInfo, String>(new TextCell()) {
      @Override
      public String getValue(ContactInfo object) {
        return object.getAddress();
      }
    };
    cellTable.addColumn(addressColumn, constants.cwCellTableColumnAddress());
    droppableOptions = addressColumn.getDroppableOptions();
    initCommonDroppableOptions(droppableOptions);
    draggableOptions = addressColumn.getDraggableOptions();
    initCommonDraggableOptions(draggableOptions);
  }

  private void initCommonDraggableOptions(DraggableOptions draggableOptions) {
     
    draggableOptions.setHelper($(Templates.INSTANCE.outerHelper()));
    draggableOptions.setOpacity((float)0.8);
    draggableOptions.setCursor(Cursor.MOVE);
    draggableOptions.setRevert(RevertOption.ON_INVALID_DROP);
    
    
  }

  private void initCommonDroppableOptions(DroppableOptions droppableOptions) {
    droppableOptions.setHoverClass(Resource.INSTANCE.css().droppableHover());
    
    
  }

}
