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
package gwtquery.plugins.droppable.client.permissionmanagersample;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.query.client.Function;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.CursorAt;
import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.droppable.client.DroppableOptions;
import gwtquery.plugins.droppable.client.DroppableOptions.DroppableFunction;
import gwtquery.plugins.droppable.client.events.DragAndDropContext;
import gwtquery.plugins.droppable.client.gwt.DragAndDropCellWidgetUtils;
import gwtquery.plugins.droppable.client.gwt.DragAndDropNodeInfo;
import gwtquery.plugins.droppable.client.permissionmanagersample.MemberDatabase.MemberInfo;
import gwtquery.plugins.droppable.client.permissionmanagersample.MemberDatabase.Permission;

import java.util.List;

/**
 * The {@link TreeViewModel} used to organize members into a permission
 * hierarchy.
 */
public class MemberTreeViewModel implements TreeViewModel {

  /**
   * The cell used to render permission.
   */
  private static class PermissionCell extends AbstractCell<Permission> {

    public PermissionCell() {
    }

    @Override
    public void render(Permission value, Object key, SafeHtmlBuilder sb) {
      if (value != null) {
        sb.appendHtmlConstant("<div style='padding:15px 3px 3px 3px;' >");
        sb.appendEscaped(value.getDisplayName());
        sb.appendHtmlConstant("</div>");
      }
    }
  }

  /**
   * A Cell used to render the member inside the tree.
   */
  private static class MemberCell extends AbstractCell<MemberInfo> {

    @Override
    public void render(MemberInfo value, Object key, SafeHtmlBuilder sb) {
      if (value != null) {
        sb.appendEscaped(value.getFullName());
      }
    }
  }

  private final ListDataProvider<Permission> permissionDataProvider;
  //private DraggableCell<MemberCell, MemberInfo> memberCell;
  private MemberCell memberCell;
  private PermissionCell permissionCell;

  // private final SelectionModel<MemberInfo> selectionModel;

  public MemberTreeViewModel(/* final SelectionModel<MemberInfo> selectionModel */) {
    // this.selectionModel = selectionModel;

    permissionDataProvider = new ListDataProvider<Permission>();
    List<Permission> permissionList = permissionDataProvider.getList();
    for (Permission permission : MemberDatabase.get().queryPermissions()) {
      if (permission != Permission.NON_MEMBER) {
        permissionList.add(permission);
      }
    }

    initMemberCell();
    initPermissionCell();

  }

  private void initPermissionCell() {
    permissionCell =  new PermissionCell();
   /* permissionCell.setHoverClass(Resource.INSTANCE.css().droppableHover());
    //permissionCell.setTolerance(DroppableTolerance.POINTER);
    permissionCell.addDropHandler(new DropEventHandler() {

      public void onDrop(DropEvent event) {
        MemberInfo droppedMember = event.getDraggableData();
        Permission dropPermission = event.getDroppableData();
        GWT.log("onDrop : "+droppedMember.getEmailAddress()+" on "+dropPermission);
        MemberDatabase.get().permissionChange(droppedMember, dropPermission);
      }
    });*/

  }

  private void initMemberCell() {
    memberCell = new MemberCell();
    /*memberCell = new DraggableCell<MemberCell, MemberInfo>(new MemberCell());
    memberCell.setDraggingOpacity((float) 0.9);
    memberCell.setDraggingCursor(Cursor.MOVE);
    // the cell being greater than the helper, force the position oh the helper
    // on the mouse cursor.
    memberCell.setCursorAt(new CursorAt(10, 10, null, null));
    memberCell.setAppendTo("body");
    memberCell.setHelper($(
        "<div class='" + Resource.INSTANCE.css().dragHelper() + "'></div>")
        .get(0));
    memberCell.setRevert(RevertOption.ON_INVALID_DROP);
    // use a DragStartEventHandler to set the value of the helper
    memberCell.addDragStartHandler(new DragStartEventHandler() {

      public void onDragStart(DragStartEvent event) {
        MemberInfo memberInfo = DraggableCell.getValueAssociatedWith(event
            .getDraggable());
        GWT.log("onDrag: "+memberInfo.getEmailAddress());
        event.getHelper().setInnerHTML(memberInfo.getEmailAddress());
      }

    });*/
  }

  public <T> NodeInfo<?> getNodeInfo(T value) {
    if (value == null) {
      DragAndDropNodeInfo<Permission> permissionNodeInfo = new DragAndDropNodeInfo<Permission>(permissionDataProvider,
          permissionCell);
      DroppableOptions options = permissionNodeInfo.getDroppableOptions();
      options.setHoverClass(Resource.INSTANCE.css().droppableHover());
      options.setOnDrop(new DroppableFunction() {
        
        public void f(DragAndDropContext context) {
          MemberInfo droppedMember = context.getDraggableData();
          Permission dropPermission = context.getDroppableData();
          GWT.log("onDrop : "+droppedMember.getEmailAddress()+" on "+dropPermission);
          MemberDatabase.get().permissionChange(droppedMember, dropPermission);
          
        }
      });
      permissionNodeInfo.setCellDroppableOnly();
      return permissionNodeInfo;
      
    } else if (value instanceof Permission) {
      Permission p = (Permission) value;

      ListDataProvider<MemberInfo> dataProvider = MemberDatabase.get()
          .getDataProvider(p);

      DragAndDropNodeInfo<MemberInfo> memberNodeInfo = new DragAndDropNodeInfo<MemberInfo>(dataProvider,
          memberCell, new SingleSelectionModel<MemberInfo>(), null );
      memberNodeInfo.setCellDraggableOnly();
      DraggableOptions options = memberNodeInfo.getDraggableOptions();
      options.setOpacity((float) 0.9);
      options.setCursor(Cursor.MOVE);
      
      // the cell being greater than the helper, force the position oh the helper
      // on the mouse cursor.
      options.setCursorAt(new CursorAt(10, 10, null, null));
      options.setAppendTo("body");
      options.setHelper($(
          "<div id='dragHelper' class='" + Resource.INSTANCE.css().dragHelper() + "'></div>")
          .get(0));
      options.setRevert(RevertOption.ON_INVALID_DROP);
      // use a DragStartEventHandler to set the value of the helper
      options.setOnDragStart(new Function() {
        @Override
        public void f(Element e) {
          //TODO change that
          MemberInfo memberInfo = (MemberInfo) $(e).data(DragAndDropCellWidgetUtils.VALUE_KEY);
          $("#dragHelper").html(memberInfo.getEmailAddress());
          super.f(e);
        }
      });    
      return memberNodeInfo;
    }

    String type = value.getClass().getName();
    throw new IllegalArgumentException("Unsupported object type: " + type);
  }

  public boolean isLeaf(Object value) {
    return value instanceof MemberInfo;
  }
}
