package gwtquery.plugins.enhance.client.multiselect;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;
import gwtquery.plugins.droppable.client.events.DragAndDropContext;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.gwt.DragAndDropCellList;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;
import gwtquery.plugins.enhance.client.multiselect.bundles.MultiSelectCss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Predicate;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

/**
 * 
 * TODO: implement double click, multiple mouse selection, filter
 * 
 * @author Manuel Carrasco Moñino
 *
 */
public class MultiSelect extends FlexTable {
  
  static class SimpleCell extends AbstractCell<String> {
    public void render(Context context,
        String value, SafeHtmlBuilder sb) {
      sb.appendHtmlConstant(value);
    }
  }
  
  public static interface Validator {
    boolean validate(String value);
  }
  
  private Anchor add = new Anchor();
  
  private TextBox box = new TextBox();
  
  @SuppressWarnings("unchecked")
  private List<ListDataProvider<String>> providers = Arrays.asList(new ListDataProvider<String>(), new ListDataProvider<String>());
  @SuppressWarnings("unchecked")
  private List<DragAndDropCellList<String>> celllists = Arrays.asList(new DragAndDropCellList<String>(new SimpleCell()), new DragAndDropCellList<String>(new SimpleCell()));
  
  private SelectElement select;
  
  private String selectedText = "Selected";
  
  private Label selectedLabel = new Label(selectedText);
  
  private boolean sortItems = false;

  private Validator validator = null;
 
  private int visibleItems = 20;

  private boolean addItemsEnabled = true;
  
  public MultiSelect(List<String> unselected, List<String> selected, SelectElement se) {
    
    setStyleName("gq-MultiSelect");
    selectedLabel.setStyleName("gq-MultiSelect-Label");
    
    MultiSelectCss.INSTANCE.css().ensureInjected();
    
    providers.get(0).setList(new ArrayList<String>());
    providers.get(1).setList(new ArrayList<String>());
    if (unselected != null) {
      getUnselectedItems().addAll(unselected);
    }
    if (selected != null) {
      getSelectedItems().addAll(selected);
    }
    
    setWidget(0, 0, selectedLabel);
    setWidget(0, 1, createBox());
    setWidget(1, 0, createList(0));
    getCellFormatter().setWidth(0, 0, "50%");
    setWidget(1, 1, createList(1));
    getCellFormatter().setWidth(0, 1, "50%");

    getCellFormatter().getElement(0, 1).getStyle().setPaddingLeft(10, Unit.PX);
    getCellFormatter().getElement(1, 1).getStyle().setPaddingLeft(10, Unit.PX);
    
    if (se != null) {
      setVisibleItems(se.getSize());
      select = se;
    }
    update();
  }
  
  protected void onAttach() {
    super.onAttach();
    int w = getElement().getOffsetWidth();
    if (w > 0) {
      getCellFormatter().setWidth(0, 0, w / 2 + "px");
      getCellFormatter().setWidth(0, 1, w / 2 + "px");
    }
  }

  public List<String> getSelectedItems() {
    return providers.get(1).getList();
  }
  
  public List<String> getUnselectedItems() {
    return providers.get(0).getList();
  }

  public void seNewItemsEnabled(boolean b) {
    addItemsEnabled  = b;
  }
  
  public void setAddText(String text) {
    add.setText(text);
  }

  public void setNewItemsValidator(Validator validator) {
    this.validator = validator;
  }
  
  public void setSelectedText(String text) {
    selectedText = text;
    int selected = getSelectedItems().size();
    int total = selected + getUnselectedItems().size();
    selectedLabel.setText(selectedText + " [" + selected + "/" + total + "]");;
  }
  
  public void setSortItems(boolean b){
    sortItems  = b;
    update();
  }
  
  public void setVisibleItems(int i) {
    if (i > 0) {
      getWidget(1, 0).setHeight(i + "em");
      getWidget(1, 1).setHeight(i + "em");
    }
  }
  private Widget createBox() {
    HorizontalPanel p = new HorizontalPanel();
    p.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    p.add(box);
    box.setStyleName("gq-MultiSelect-Input");
    add.setStyleName("gq-MultiSelect-Add");
    p.add(add);
    setAddText("Add");
    box.addKeyUpHandler(new KeyUpHandler() {
      public void onKeyUp(KeyUpEvent event) {
        new Timer(){
          public void run() {
            filter(box.getValue().trim());
          }
        }.schedule(10);
      }
    });
    add.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        String v = box.getValue().trim();
        if (addItemsEnabled && (validator == null || validator.validate(v)) && !v.isEmpty() && !getSelectedItems().contains(v)) {
          if (select != null && !getUnselectedItems().contains(v)) {
            OptionElement oe = DOM.createOption().cast();
            oe.setValue(v);
            select.appendChild(oe);
          }
          selectItem(v);
          update();
        }
      }
    });
    return p;
  }
  
  private DraggableOptions createDraggableOptions() {
    DraggableOptions options = new DraggableOptions();
    options.setHelper(HelperType.CLONE);
    options.setOpacity((float) 0.9);
    options.setAppendTo("body");
    return options;
  }
  
  private Widget createList(final int ord) {
    DragAndDropCellList<String> cellList = celllists.get(ord % 2);
    providers.get(ord % 2).addDataDisplay(cellList);
    
    cellList.setCellDraggableOnly();
    cellList.setDraggableOptions(createDraggableOptions());
    DroppableWidget<Widget> dropable = new DroppableWidget<Widget>(cellList);
    GQuery.$(cellList).css("overflow-y", "auto").css("overflow-x", "hidden");
    
    dropable.setStyleName("gq-MultiSelect-Panel");
    dropable.setDroppableHoverClass("gq-MultiSelect-DropableHover");
    dropable.setDraggableHoverClass("gq-MultiSelect-DraggableHover");
    dropable.setActiveClass("gq-MultiSelect-Active");
    
    dropable.addDropHandler(new DropEventHandler(){
      public void onDrop(DropEvent event) {
        String row = event.getDraggableData();
        if (providers.get((ord+1) % 2).getList().contains(row)) {
          providers.get(ord % 2).getList().add(row);
          providers.get((ord+1) % 2).getList().remove(row);
          update();
        }
      }
    });

    dropable.setAccept(new AcceptFunction() {
      public boolean acceptDrop(DragAndDropContext ctx) {
        return !providers.get(ord % 2).getList().contains(ctx.getDraggable());
      }
    });
    
    cellList.setHeight(visibleItems + "em");
    return dropable;
  }
  
  private void filter(final String prefix) {
    GQuery g = GQuery.$(".gwtQuery-draggable", getWidget(1, 0)).show();
    if (!prefix.isEmpty()) {
      g.filter(new Predicate() {
        public boolean f(Element e, int index) {
          String t = GQuery.$(e).text();
          return !t.toLowerCase().startsWith(prefix.toLowerCase());
        }
      }).hide();
    }
  }
    
  private void update() {
    box.setValue("");
    filter("");
    setSelectedText(selectedText);
    if (sortItems) {
      Collections.sort(getSelectedItems());
      Collections.sort(getUnselectedItems());
    }
    int page = getSelectedItems().size() + getUnselectedItems().size();
    celllists.get(0).setPageSize(page);
    celllists.get(1).setPageSize(page);
    if (select != null) {
      NodeList<OptionElement> n = select.getOptions();
      for (int i = 0; i < n.getLength(); i++) {
        OptionElement oe = n.getItem(i);
        oe.setSelected(getSelectedItems().contains(oe.getValue()) ? true : false);
      }
    }
  }
  
  public void setAllValues(Collection<String> vals) {
    getUnselectedItems().clear();
    if (vals != null) for (String v : vals) {
      if (!v.isEmpty() && !getSelectedItems().contains(v)) {
        getUnselectedItems().add(v);
      }
    }
    update();
  }

  public void setSelectedValues(Collection<String> vals) {
    getSelectedItems().clear();
    if (vals != null) for (String v : vals) {
      selectItem(v);
    }
    update();
  }
  
  public void selectItem(String v) {
    if (v == null || v.isEmpty() || getSelectedItems().contains(v)) {
      return;
    }
    if (!addItemsEnabled && !getUnselectedItems().contains(v)) {
      return;
    }
    getUnselectedItems().remove(v);
    getSelectedItems().add(v);
  }
  
  public void setValues(String... vals) {
    setSelectedValues(Arrays.asList(vals));
  }
  
  public void setValidator(Validator validator) {
    this.validator = validator;
  }

  public void setAddItemsEnabled(boolean addItemsEnabled) {
    this.addItemsEnabled = addItemsEnabled;
  }
}
