package gwtquery.plugins.droppable.client.gwt;

/**
 * This objet determines if the current rendering cell have to be draggable
 * and/or droppable
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 * @param <C>
 */
public interface CellDragAndDropBehaviour<C> {
  
  public class CellDropOnlyBehaviour<C> implements CellDragAndDropBehaviour<C>{

    public boolean isDraggable(C value) {
      return false;
    }

    public boolean isDroppable(C value) {
      return true;
    }
    
  }
  
  public class CellDragOnlyBehaviour<C> implements CellDragAndDropBehaviour<C>{

    public boolean isDraggable(C value) {
      return true;
    }

    public boolean isDroppable(C value) {
      return false;
    }
    
  }
  /**
   * This method is called during the render of a cell. It decides if the cell
   * is draggable or not.
   * 
   * @param value
   * @param key
   * @return
   */
  boolean isDraggable(C value);

  /**
   * This method is called during the render of a cell. It decides if the cell
   * is draggable or not.
   * 
   * @param value
   * @param key
   * @return
   */
  boolean isDroppable(C value);
}