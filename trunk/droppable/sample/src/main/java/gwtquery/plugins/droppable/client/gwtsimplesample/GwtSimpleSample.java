package gwtquery.plugins.droppable.client.gwtsimplesample;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

public class GwtSimpleSample implements EntryPoint {

  interface Resources extends ClientBundle{
    Resources INSTANCE = GWT.create(Resources.class);
    
    ImageResource gwtLogo();
    
    @Source("style.css")
    Css css();
  }
  
  interface Css extends CssResource{
    String droppableHover();
    String trashBin();
  }
  
  public void onModuleLoad() {
    Resources.INSTANCE.css().ensureInjected();
    
    RootPanel.get("grid").add(createImageGrid());
    RootPanel.get("trashBin").add(createTrashBin());

  }

  private Widget createTrashBin() {

    final Label trashBinLabel = new Label("Give me food !");
    trashBinLabel.addStyleName(Resources.INSTANCE.css().trashBin());
    
    //make the label droppable
    DroppableWidget<Label> trashBin = new DroppableWidget<Label>(trashBinLabel);
    //specify wich class has to be used when a draggable widget is over the droppable
    trashBin.setHoverClass(Resources.INSTANCE.css().droppableHover());
    //add a drop handler
    trashBin.addDropHandler(new DropEventHandler() {
      
      int counter = 0;
      
      public void onDrop(DropEvent event) {
        Widget droppedImage = event.getDraggableWidget();
        droppedImage.removeFromParent();
        counter++;
        trashBinLabel.setText("I ate "+counter+" image"+(counter>1 ? "s":""));
        
      }
    });
    
    //return the droppable widget
    return trashBin;
  }

  /**
   * Create a 5x5 grid. Each cell contains the gwt logo.
   * Code inspired from the gwt showcase
   * @return
   */
  private Widget createImageGrid() {
    
    Grid grid = new Grid(5, 5);
    
    int numRows = grid.getRowCount();
    int numColumns = grid.getColumnCount();
    
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numColumns; col++) {
        grid.setWidget(row, col, createDraggableImage());
      }
    }

    return grid;
  }

  private Widget createDraggableImage() {
    Image gwtLogo = new Image(Resources.INSTANCE.gwtLogo());
    //make the imgae draggable
    DraggableWidget<Image> draggableLogo = new DraggableWidget<Image>(gwtLogo);
    //revert the image to its initial position if no drop 
    draggableLogo.setRevert(RevertOption.ON_INVALID_DROP);
    return draggableLogo;
  }

}
