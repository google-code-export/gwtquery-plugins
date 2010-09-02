package gwtquery.plugins.draggable.client;


public class DraggableContainment {

  private String containment;
  private int[] containmentAsArray;

  /**
   * 
   * @param selector possible values : GQuery selector or 'parent''
   */
  public DraggableContainment(String selector) {
      containment = selector;
   
  }
  
  public DraggableContainment(int[] coordinates) {
    containmentAsArray = coordinates;
  }

  public String getContainment() {
    return containment;
  }
  
  public int[] getContainmentAsArray() {
    return containmentAsArray;
  }
  

}