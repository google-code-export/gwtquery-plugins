package gwtquery.plugins.enhance.client.multiselect;

import static com.google.gwt.query.client.GQuery.$;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
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
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Predicate;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AbstractDataProvider;
import com.google.gwt.view.client.ListDataProvider;

/**
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
  
  private Anchor addLink = new Anchor("Add");
  
  private TextBox box = new TextBox();
  
  @SuppressWarnings("unchecked")
  private List<ListDataProvider<String>> providers = Arrays.asList(new ListDataProvider<String>(), new ListDataProvider<String>());
  @SuppressWarnings("unchecked")
  private List<DragAndDropCellList<String>> celllists = Arrays.asList(new DragAndDropCellList<String>(new SimpleCell()), new DragAndDropCellList<String>(new SimpleCell()));
  
  private SelectElement select;
  
  private boolean sortUnselected = true;
  private boolean sortSelected = false;

  private Validator validator = null;
  
  private String selectedText = "Selected {0} of {1}";
  private Label selectedLabel = new Label();
 
  private int visibleItems = 20;

  private boolean addItemsEnabled = true;
  
  public MultiSelect(List<String> unselected, List<String> selected, SelectElement se) {
    
    setStyleName("gq-MultiSelect");
    
    MultiSelectCss.INSTANCE.css().ensureInjected();
    
    providers.get(0).setList(new ArrayList<String>());
    providers.get(1).setList(new ArrayList<String>());
    if (unselected != null) {
      getUnselectedItems().addAll(unselected);
    }
    if (selected != null) {
      getSelectedItems().addAll(selected);
    }
    
    setText(0, 0, "Choose items");
    setWidget(0, 1, createBox());
    setText(1, 0, "Available");
    setWidget(1, 1, selectedLabel);
    setWidget(2, 0, createList(0));
    getCellFormatter().setWidth(0, 0, "50%");
    setWidget(2, 1, createList(1));
    getCellFormatter().setWidth(0, 1, "50%");

    getCellFormatter().getElement(0, 1).getStyle().setPaddingLeft(10, Unit.PX);
    getCellFormatter().getElement(1, 1).getStyle().setPaddingLeft(10, Unit.PX);
    getCellFormatter().getElement(2, 1).getStyle().setPaddingLeft(10, Unit.PX);
    getCellFormatter().getElement(0, 0).setClassName("gq-MultiSelect-Name");
    getCellFormatter().getElement(1, 0).setClassName("gq-MultiSelect-Label");
    getCellFormatter().getElement(1, 1).setClassName("gq-MultiSelect-Label");
    
    if (se != null) {
      setVisibleItems(se.getSize());
      select = se;
      String name = se.getName();
      if (name != null && !name.isEmpty()) {
        setText(0, 0, name);
      }
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
  
  public void setNewItemsValidator(Validator validator) {
    this.validator = validator;
  }
  
  public void updateCounters() {
    Integer selected = getSelectedItems().size();
    Integer total = selected + getUnselectedItems().size();
    selectedLabel.setText(selectedText.replace("{0}", selected.toString()).replace("{1}", total.toString()));
  }
  
  public void setSelectedText(String name) {
    setText(0, 0, name);
  }
  
  public void setLabels(String name, String available, String selected, String addLabel) {
    setText(0, 0, name);
    setText(1, 0, available);
    setText(1, 1, selected);
    addLink.setText(addLabel);
    update();
  }
  
  public void setSortItems(boolean b){
    sortSelected  = b;
    sortUnselected  = b;
    if (b) update();
  }

  public void setSortUnselectedItems(boolean b){
    sortUnselected  = b;
    if (b) update();
  }
  
  public void setSortSelectedItems(boolean b){
    sortSelected  = b;
    if (b) update();
  }
  
  public void setVisibleItems(int i) {
    if (i > 0) {
      getWidget(2, 0).setHeight(i + "em");
      getWidget(2, 1).setHeight(i + "em");
    }
  }
  private Widget createBox() {
    HorizontalPanel p = new HorizontalPanel();
    p.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    p.add(box);
    box.setStyleName("gq-MultiSelect-Input");
    addLink.setStyleName("gq-MultiSelect-Add");
    p.add(addLink);
    box.addKeyUpHandler(new KeyUpHandler() {
      public void onKeyUp(KeyUpEvent event) {
        new Timer(){
          public void run() {
            filter(box.getValue().trim());
          }
        }.schedule(10);
      }
    });
    addLink.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        String v = box.getValue().trim();
        if (addItemsEnabled && (validator == null || validator.validate(v)) && !v.isEmpty() && !getSelectedItems().contains(v)) {
          if (select != null && !getUnselectedItems().contains(v)) {
            OptionElement oe = DOM.createOption().cast();
            oe.setValue(v);
            select.appendChild(oe);
          }
          selectItem(v, 0);
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
    final DroppableWidget<DragAndDropCellList<String>> dropable = new DroppableWidget<DragAndDropCellList<String>>(cellList);
    GQuery.$(cellList).css("overflow-y", "auto").css("overflow-x", "hidden");
    
    dropable.setStyleName("gq-MultiSelect-Panel");
    dropable.setDroppableHoverClass("gq-MultiSelect-DropableHover");
    dropable.setDraggableHoverClass("gq-MultiSelect-DraggableHover");
    dropable.setActiveClass("gq-MultiSelect-Active");

    final List<String> thisList = providers.get((ord) % 2).getList(); 
    final List<String> otherList = providers.get((ord+1) % 2).getList();

    dropable.addDropHandler(new DropEventHandler(){
      public void onDrop(DropEvent event) {
        String row = event.getDraggableData();
        int top = event.getDragDropContext().getHelperPosition().top;
        GQuery rows = $(".gwtQuery-draggable", event.getDroppableWidget());
        int pos = 0;
        for (;pos < rows.size(); pos++ ) {
          if (top < rows.eq(pos).offset().top) {
            break;
          }
        }
        if (pos == thisList.size() && thisList.contains(row)) {
          pos --;
        }
        thisList.remove(row);
        otherList.remove(row);
        thisList.add(pos, row);
        update();
        setCurrent(row);
      }
    });
    
    $(cellList).dblclick(new Function(){
      public boolean f(Event ev) {
        Element e = ev.getEventTarget().cast();
        String row = $(e).text().trim();
        thisList.remove(row);
        otherList.add(row);
        update();
        setCurrent(row);
        return false;
      }
    }).mousedown(new Function() {
      public boolean f(Event ev) {
        Element e = ev.getEventTarget().cast();
        setCurrent($(e).text());
        return false;
      }
    });

    cellList.setHeight(visibleItems + "em");
    return dropable;
  }
  
  private void setCurrent(final String txt) {
    new Timer() {
      public void run() {
        $(".gwtQuery-draggable", MultiSelect.this).each(new Function() {
          public void f(Element e) {
            if (txt.equals($(e).text())) {
              $(".gq-MultiSelect-Item", MultiSelect.this).removeClass("gq-MultiSelect-Item");
              $(e).addClass("gq-MultiSelect-Item").scrollIntoView(true);
              return;
            }
          }
        });
      }
    }.schedule(5);
  }
  
  private void filter(final String prefix) {
    GQuery g = GQuery.$(".gwtQuery-draggable", getWidget(2, 0)).show();
    if (!prefix.isEmpty()) {
      g.filter(new Predicate() {
        public boolean f(Element e, int index) {
          String t = GQuery.$(e).text();
          return !t.toLowerCase().contains(prefix.toLowerCase());
        }
      }).hide();
    }
  }
    
  private void update() {
    box.setValue("");
    filter("");
    updateCounters();
    if (sortSelected) {
      Collections.sort(getSelectedItems());
    }
    if (sortUnselected) {
      Collections.sort(getUnselectedItems());
    }
    int page = getSelectedItems().size() + getUnselectedItems().size();
    celllists.get(0).setPageSize(page);
    celllists.get(1).setPageSize(page);
    if (select != null) {
      select.clear();
      for (String s : getSelectedItems()) {
        OptionElement oe = DOM.createOption().cast();
        oe.setValue(s);
        oe.setSelected(true);
        select.appendChild(oe);
      }
      for (String s : getUnselectedItems()) {
        OptionElement oe = DOM.createOption().cast();
        oe.setValue(s);
        select.appendChild(oe);
      }
    }
  }
  
  public void setAllValues(Collection<String> vals) {
    getUnselectedItems().clear();
    if (vals != null) for (String v : vals) {
      if (!v.isEmpty() && !getSelectedItems().contains(v) && !getUnselectedItems().contains(v)) {
        getUnselectedItems().add(v);
      }
    }
    update();
  }
  
  public void setDataProvider(AbstractDataProvider<String> o) {
    DragAndDropCellList<String> d = $(getWidget(2, 0)).widget();
    o.addDataDisplay(d);
    providers.get(0).removeDataDisplay(d);
  }

  public void setSelectedValues(Collection<String> vals) {
    getSelectedItems().clear();
    if (vals != null) for (String v : vals) {
      selectItem(v);
    }
    update();
  }
  
  public void selectItem(String v) {
    selectItem(v, -1);
  }
  
  public void selectItem(String v, int pos) {
    if (v == null || v.isEmpty() || getSelectedItems().contains(v)) {
      return;
    }
    if (!addItemsEnabled && !getUnselectedItems().contains(v)) {
      return;
    }
    
    getUnselectedItems().remove(v);
    
    int length = getSelectedItems().size();
    if (pos < 0) {
      pos = length - pos + 1;
    }
    pos = Math.max(Math.min(pos, length), 0);
    getSelectedItems().add(pos, v);
    
    setCurrent(v);
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
