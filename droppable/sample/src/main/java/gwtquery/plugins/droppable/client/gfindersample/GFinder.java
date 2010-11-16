package gwtquery.plugins.droppable.client.gfindersample;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.gfindersample.FileSystem.File;
import gwtquery.plugins.droppable.client.gwt.DragAndDropCellBrowser;

public class GFinder implements EntryPoint {

  public void onModuleLoad() {
    Resource.INSTANCE.css().ensureInjected();

    
    //SingleSelectionModel<File> model = new SingleSelectionModel<File>(FileSystem.File.KEY_PROVIDER);
    DragAndDropCellBrowser cellBrowser = new DragAndDropCellBrowser(
        new FileTreeViewModel(), null);
    //define an id for the cellBrowser to use it as containment during drag operation
    cellBrowser.getElement().setId("fileBrowser");
    cellBrowser.setAnimationEnabled(true);
    cellBrowser.addStyleName(Resource.INSTANCE.css().finder());
    cellBrowser.setDefaultColumnWidth(250);
    
    cellBrowser.addDropHandler(new DropEventHandler() {
      
      public void onDrop(DropEvent event) {
       GWT.log("drop");
       File draggedFile = event.getDraggableData();
       File destinationDir = event.getDroppableData();
       FileSystem.get().moveFile(draggedFile, destinationDir);
       
      }
    });
    
    RootPanel.get("cellBrowser").add(cellBrowser);
    
  }
  
}
