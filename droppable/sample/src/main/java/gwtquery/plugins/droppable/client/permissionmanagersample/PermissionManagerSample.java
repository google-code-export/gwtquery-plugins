package gwtquery.plugins.droppable.client.permissionmanagersample;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTree.Resources;
import com.google.gwt.user.cellview.client.CellTree.Style;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SingleSelectionModel;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.CursorAt;
import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.events.DragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStartEvent.DragStartEventHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableCell;
import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.gwt.CellDragAndDropBehaviour;
import gwtquery.plugins.droppable.client.gwt.DragAndDropCellList;
import gwtquery.plugins.droppable.client.gwt.DragAndDropCellTree;
import gwtquery.plugins.droppable.client.gwt.DragAndDropCellWidgetUtils;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;
import gwtquery.plugins.droppable.client.permissionmanagersample.MemberDatabase.MemberInfo;
import gwtquery.plugins.droppable.client.permissionmanagersample.MemberDatabase.Permission;

/**
 * Show how to make a cell draggable and droppable
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class PermissionManagerSample implements EntryPoint {

  /**
   * Style used for our CellTree. Some style was modified to offer a greater droppable area
   */
  interface DroppableCellTreeResource extends Resources{
    
    DroppableCellTreeResource INSTANCE = GWT.create(DroppableCellTreeResource.class);
    /**
     * The styles used in this widget.
     */
    @Source("DroppableCellTree.css")
    Style cellTreeStyle();
  }
  
  /**
   * The Cell used to render a {@link MemberInfo} with detailled info. Code
   * coming from the GWT showcase
   * 
   */
  static class DetailledMemberCell extends AbstractCell<MemberInfo> {

    /**
     * The html of the image used for contacts.
     * 
     */
    private final String imageHtml;

    public DetailledMemberCell() {
      this.imageHtml = AbstractImagePrototype.create(
          Resource.INSTANCE.contact()).getHTML();
    }

    @Override
    public void render(MemberInfo value, Object key, SafeHtmlBuilder sb) {
      // Value can be null, so do a null check..
      if (value == null) {
        return;
      }

      sb.appendHtmlConstant("<table>");

      // Add the contact image.
      sb.appendHtmlConstant("<tr><td rowspan='3'>");
      sb.appendHtmlConstant(imageHtml);
      sb.appendHtmlConstant("</td>");

      // Add the name and email address.
      sb.appendHtmlConstant("<td style='font-size:95%;'>");
      sb.appendEscaped(value.getFullName());
      sb.appendHtmlConstant("</td></tr><tr><td>");
      sb.appendEscaped(value.getEmailAddress());
      sb.appendHtmlConstant("</td></tr></table>");
    }
  }

  public void onModuleLoad() {
    Resource.INSTANCE.css().ensureInjected();

    RootPanel.get("members").add(new Label("Others members"));
    RootPanel.get("members").add(createAllMemberList());

    RootPanel.get("permission").add(new Label("Project members"));
    RootPanel.get("permission").add(createPermissionTree());
    
    Button b = new Button("cache status");
    b.addClickHandler(new ClickHandler() {
      
      public void onClick(ClickEvent event) {
        HTML l = new HTML();
        l.setHTML(GQuery.cacheStatus());
        RootPanel.get().add(l);
        
      }
    });
    RootPanel.get("permission").add(b);

  }


  /**
   * Create Panel with al members
   * 
   * @param contactForm
   * 
   * @return
   */
  private DroppableWidget<DragAndDropCellList<MemberInfo>> createAllMemberList() {

    // Create a DetailledMemberCell
    DetailledMemberCell detailledMemberCell = new DetailledMemberCell();
    // make it draggable
   /* DraggableCell<DetailledMemberCell, MemberInfo> draggableContactCell = makeCellDraggable(detailledMemberCell);*/
    
    DragAndDropCellList<MemberInfo> cellList = new DragAndDropCellList<MemberInfo>(
        detailledMemberCell, MemberDatabase.MemberInfo.KEY_PROVIDER);
    
    cellList.setPageSize(30);
    cellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
    final SingleSelectionModel<MemberInfo> selectionModel = new SingleSelectionModel<MemberInfo>(
        MemberDatabase.MemberInfo.KEY_PROVIDER);
    cellList.setSelectionModel(selectionModel);
    cellList.addStyleName(Resource.INSTANCE.css().memberList());

    // setup drag
    DraggableOptions options=cellList.getDraggableOptions();
    options.setOpacity((float) 0.9);
    options.setAppendTo("body");
    options.setCursor(Cursor.MOVE);
    //the cell being greater than the helper, force the position oh the helper on the mouse cursor.
    options.setCursorAt(new CursorAt(10, 10, null, null));
    options.setHelper($("<div class='"+Resource.INSTANCE.css().dragHelper()+"'></div>").get(0));
    options.setRevert(RevertOption.ON_INVALID_DROP);
    cellList.setCellDragAndDropBehaviour(new CellDragAndDropBehaviour.CellDragOnlyBehaviour<MemberInfo>());
    //use a DragStartEventHandler to set the value of the helper
    cellList.addDragStartHandler(new DragStartEventHandler() {
     
      public void onDragStart(DragStartEvent event) {
        GWT.log("On drag start : ");
        MemberInfo memberInfo = event.getDraggableValue();
        GWT.log("On drag start : "+memberInfo.getEmailAddress());
        event.getHelper().setInnerHTML(memberInfo.getEmailAddress());
      }
    
    });
    MemberDatabase.get().addDataDisplay(cellList, Permission.NON_MEMBER);

    return makeCellListDroppable(cellList);
  }

  private DroppableWidget<DragAndDropCellList<MemberInfo>> makeCellListDroppable(
      DragAndDropCellList<MemberInfo> cellList) {

    DroppableWidget<DragAndDropCellList<MemberInfo>> droppableList = new DroppableWidget<DragAndDropCellList<MemberInfo>>(
        cellList);
    droppableList.setHoverClass(Resource.INSTANCE.css().droppableHover());

    droppableList.addDropHandler(new DropEventHandler() {

      public void onDrop(DropEvent event) {
        MemberInfo droppedMember = event.getDraggableData();
        GWT.log("onDrop : "+droppedMember.getEmailAddress()+" on NON_MEMBER");
        MemberDatabase.get().permissionChange(droppedMember,
            Permission.NON_MEMBER);
      }

    });

    droppableList.setAccept(new AcceptFunction() {

      public boolean acceptDrop(Element droppable, Element draggable) {
        
        MemberInfo draggedMember = (MemberInfo)$(draggable).data(DragAndDropCellWidgetUtils.VALUE_KEY);
        // don't continue drop if member coming from this list
        return Permission.NON_MEMBER != draggedMember.getPermission();
      }

    });

    return droppableList;
  }

  private DragAndDropCellTree createPermissionTree() {

    DragAndDropCellTree cellTree = new DragAndDropCellTree(new MemberTreeViewModel(), null, DroppableCellTreeResource.INSTANCE);
    cellTree.setAnimationEnabled(true);
    cellTree.addStyleName(Resource.INSTANCE.css().permissionTree());

    return cellTree;

  }

}
