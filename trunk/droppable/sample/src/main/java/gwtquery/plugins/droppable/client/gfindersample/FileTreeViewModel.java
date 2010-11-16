package gwtquery.plugins.droppable.client.gfindersample;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
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
   * The cell used to render files.
   */
  private static class FileCell extends AbstractCell<File> {

    /**
     * The html of the image used for file.
     */
    private final String dirImageHtml;

    /**
     * The html of the image used for dir.
     */
    private final String fileImageHtml;

    public FileCell() {
       dirImageHtml = AbstractImagePrototype.create(Resource.INSTANCE.folder()).getHTML();

      fileImageHtml = AbstractImagePrototype.create(Resource.INSTANCE.file()).getHTML();

    }

    @Override
    public void render(File value, Object key, SafeHtmlBuilder sb) {
      if (value != null) {
        sb.appendHtmlConstant("<table><tr><td>");
         if (value.getType() == FileType.FILE) {
          sb.appendHtmlConstant(fileImageHtml);
        } else {
          sb.appendHtmlConstant(dirImageHtml);
        }
        sb.appendHtmlConstant("</td><td valign='center'>");
        sb.appendEscaped(value.getName());
        sb.appendHtmlConstant("</td></tr></table>");
      }
    }
  }

  private final Cell<File> fileCell;
  private final ListDataProvider<File> rootDirectories;

  public FileTreeViewModel(/*SingleSelectionModel<File> model*/) {
    rootDirectories = new ListDataProvider<File>(FileSystem.get()
        .getRootDirectories());
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
    options.setContainment("#fileBrowser");
    //draggableFileCell.setDistance(0);
    return options;
  }

  public boolean isLeaf(Object value) {
    File f = (File) value;
    return f.getType() == FileType.FILE;
  }

}
