package gwtquery.plugins.droppable.client.gfindersample;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.droppable.client.DroppableOptions;
import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;
import gwtquery.plugins.droppable.client.events.DragAndDropContext;
import gwtquery.plugins.droppable.client.gfindersample.FileSystem.File;
import gwtquery.plugins.droppable.client.gfindersample.FileSystem.FileType;
import gwtquery.plugins.droppable.client.gwt.CellDragAndDropBehaviour;
import gwtquery.plugins.droppable.client.gwt.DragAndDropNodeInfo;

public class FileTreeViewModel implements TreeViewModel {

  /**
   * The cell used to render files.
   */
  private static class FileCell extends AbstractCell<File> {


    @Override
    public void render(File value, Object key, SafeHtmlBuilder sb) {
      if (value != null) {
        sb.appendHtmlConstant("<div class='"+getCssClassName(value)+"'>");
        sb.appendEscaped(value.getName());
        sb.appendHtmlConstant("</div>");
      }
    }
    
    private String getCssClassName(File value){
      if (value.getType() == FileType.DIRECTORY){
        return Resource.INSTANCE.css().directoryCell();
      }
      
      return Resource.INSTANCE.css().fileCell();
    }
  }

  private final Cell<File> fileCell;
  private final ListDataProvider<File> rootDirectories;

  public FileTreeViewModel() {
    rootDirectories = FileSystem.get().getRootDirectories();
    fileCell = new FileCell();
  }

  public <T> NodeInfo<?> getNodeInfo(T value) {

    if (value == null) {
      // Return root directories
      DragAndDropNodeInfo<File> nodeInfo = new DragAndDropNodeInfo<File>(
          rootDirectories, fileCell);
      //root directory cannot be dragged
      nodeInfo.setCellDroppableOnly();
      nodeInfo.setDroppableOptions(createDroppableOptions());

      return nodeInfo;

    } else if (value instanceof File) {

      File dir = (File) value;
      assert dir.getType() == FileType.DIRECTORY;

      ListDataProvider<File> files = FileSystem.get().getFilesDataProvider(dir);
      DragAndDropNodeInfo<File> nodeInfo = new DragAndDropNodeInfo<File>(files,
          fileCell);
      nodeInfo.setDroppableOptions(createDroppableOptions());
      nodeInfo.setDraggableOptions(createDraggableOptions());
      nodeInfo.setCellDragAndDropBehaviour( new CellDragAndDropBehaviour<File>() {

            public boolean isDraggable(File value) {
              return true;
            }

            public boolean isDroppable(File value) {
              // only directories can be droppable
              return value.getType() == FileType.DIRECTORY;
            }

          });

      return nodeInfo;

    }

    // Unhandled type.
    String type = value.getClass().getName();
    throw new IllegalArgumentException("Unsupported object type: " + type);
  }

  private DroppableOptions createDroppableOptions() {
    DroppableOptions options = new DroppableOptions();
    //css class set to the droppable when an acceptable draggable is over it
    options.setDroppableHoverClass(Resource.INSTANCE.css().droppableHover());
  //css class set to the draggable when it is over a droppable
    options.setDraggableHoverClass(Resource.INSTANCE.css().dragHover());
    
    //a directory cannot be drop on one of these children
    options.setAccept(new AcceptFunction() {
      
      public boolean acceptDrop(DragAndDropContext ctx) {
        File dropDirectory = ctx.getDroppableData();
        File dragFile = ctx.getDraggableData();
        if (dragFile.getType() == FileType.FILE){
          return true;
        }
        
        //check that the dropDirectory is not a child of dragFile
        File parent = dropDirectory.getParent();
        while( parent != null){
          if (parent.equals(dragFile)){
            return false;
          }
          parent = parent.getParent();
        }
        
        return true;
      }
    });
    
    return options;
  }

  private DraggableOptions createDraggableOptions() {
    DraggableOptions options = new DraggableOptions();
    options.setHelper(HelperType.CLONE);
    options.setOpacity((float) 0.8);
    options.setCursor(Cursor.POINTER);
    options.setAppendTo("#fileBrowser");
   // options.setContainment("#fileBrowser");
    //bug in the position helper when cellbrowser is scrolling
    //workaround : force the position of the helper to be on the pointer
    //options.setCursorAt(new CursorAt(5, 5, null, null));
    // draggableFileCell.setDistance(0);
    return options;
  }

  public boolean isLeaf(Object value) {
    File f = (File) value;
    return f.getType() == FileType.FILE;
  }

}
