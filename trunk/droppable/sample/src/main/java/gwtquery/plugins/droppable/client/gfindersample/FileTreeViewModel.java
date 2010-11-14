package gwtquery.plugins.droppable.client.gfindersample;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.droppable.client.DroppableOptions;
import gwtquery.plugins.droppable.client.gfindersample.FileSystem.File;
import gwtquery.plugins.droppable.client.gfindersample.FileSystem.FileType;
import gwtquery.plugins.droppable.client.gwt.CellDragAndDropBehaviour;
import gwtquery.plugins.droppable.client.gwt.DragAndDropNodeInfo;


public class FileTreeViewModel implements TreeViewModel {

  /**
   * The images used for this example.
   */
  static interface Images extends ClientBundle {
    Images INSTANCE = GWT.create(Images.class);
    // ImageResource directory();
    // ImageResource file();
  }

  /**
   * The cell used to render files.
   */
  private static class FileCell extends AbstractCell<File> {

    /**
     * The html of the image used for file.
     */
    // private final String dirImageHtml;

    /**
     * The html of the image used for dir.
     */
    // private final String fileImageHtml;

    public FileCell() {
      // dirImageHtml =
      // AbstractImagePrototype.create(Images.INSTANCE.directory()).getHTML();

      // fileImageHtml =
      // AbstractImagePrototype.create(Images.INSTANCE.file()).getHTML();

    }

    @Override
    public void render(File value, Object key, SafeHtmlBuilder sb) {
      if (value != null) {
         if (value.getType() == FileType.FILE) {
          // sb.appendHtmlConstant(fileImageHtml);
        } else {
          // sb.appendHtmlConstant(dirImageHtml);
        }
        sb.appendEscaped(" ").appendEscaped(value.getName());
      }
    }
  }

  private Cell<File> fileCell;
  private ListDataProvider<File> rootDirectories;

  public FileTreeViewModel(/*SingleSelectionModel<File> model*/) {
    rootDirectories = new ListDataProvider<File>(FileSystem.get()
        .getRootDirectories());
    initFileCell();
  }

  private void initFileCell() {
    /*
    // first make it draggable
    DraggableCell<FileCell, File> draggableFileCell = new DraggableCell<FileCell, File>(
        new FileCell());
    
    // setup drag
    draggableFileCell.useCloneAsHelper();
    draggableFileCell.setDraggingOpacity((float) 0.5);
    draggableFileCell.setDraggingCursor(Cursor.POINTER);
    draggableFileCell.setAppendTo("body");
    //draggableFileCell.setDistance(0);
    // add a constraint : root directories cannot be move
    draggableFileCell.setIsDraggableCell(new IsDraggableCellFunction<File>() {

      public boolean isDraggable(File value, Object key) {
        List<File> rootDirectories = FileSystem.get().getRootDirectories();
        return !rootDirectories.contains(value);
      }

    });

    // second : make the cell droppable
    DroppableCell<DraggableCell<FileCell, File>, File> droppableFileCell = new DroppableCell<DraggableCell<FileCell, File>, File>(
        draggableFileCell);
  
    // setup drop
    droppableFileCell.setHoverClass(Resource.INSTANCE.css().droppableHover());
    // only directories can be droppable
    droppableFileCell.setIsDroppableCell(new IsDroppableCellFunction<File>() {

      public boolean isDroppable(File value, Object key) {
        return value.getType() == FileType.DIRECTORY;
      }
    });
    
    // manage drop
    droppableFileCell.addDropHandler(new DropEventHandler() {
      
      public void onDrop(DropEvent event) {
       GWT.log("drop");
       File draggedFile = event.getDraggableData();
       File destinationDir = event.getDroppableData();
       FileSystem.get().moveFile(draggedFile, destinationDir);
       
      }
    });
    
    fileCell = droppableFileCell;*/
    fileCell = new FileCell();

  }

  public <T> NodeInfo<?> getNodeInfo(T value) {
    
    if (value == null) {
      // Return root directories
      DragAndDropNodeInfo<File> nodeInfo = new DragAndDropNodeInfo<File>(rootDirectories,fileCell);
      nodeInfo.setCellDroppableOnly();
      nodeInfo.setDroppableOptions(createDroppableOptions());
      
      return nodeInfo;
      
    } else if (value instanceof File) {
      
      File dir = (File) value;
      assert dir.getType() == FileType.DIRECTORY;
      
      ListDataProvider<File> files = FileSystem.get().getFilesDataProvider(dir);
      DragAndDropNodeInfo<File> nodeInfo = new DragAndDropNodeInfo<File>(files,fileCell);
      nodeInfo.setDroppableOptions(createDroppableOptions());
      nodeInfo.setDraggableOptions(createDraggableOptions());
      nodeInfo.setCellDragAndDropBehaviour(new CellDragAndDropBehaviour<File>(){

        public boolean isDraggable(File value) {
          return true;
        }

        public boolean isDroppable(File value) {
          //only directories can be droppable
          return value.getType() == FileType.DIRECTORY;
        }
        
      });
      
     
      return nodeInfo;
          
    } 
    
    // Unhandled type.
    String type = value.getClass().getName();
    throw new IllegalArgumentException("Unsupported object type: " + type);
  }
  
  private DroppableOptions createDroppableOptions(){
    DroppableOptions options = new DroppableOptions();
    options.setHoverClass(Resource.INSTANCE.css().droppableHover());
    return options;
  }
  
  private DraggableOptions createDraggableOptions(){
    DraggableOptions options = new DraggableOptions();
    options.setHelper(HelperType.CLONE);
    options.setOpacity((float) 0.8);
    options.setCursor(Cursor.POINTER);
    options.setAppendTo("#fileBrowser");
    options.setContainment($("#fileBrowser"));
    //draggableFileCell.setDistance(0);
    return options;
  }

  public boolean isLeaf(Object value) {
    File f = (File) value;
    return f.getType() == FileType.FILE;
  }

}
