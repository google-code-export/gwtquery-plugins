package gwtquery.plugins.commonui.client;



public class MouseOptions {

  private String cancel;
  
  /**
   * Time in milliseconds to define when the selecting should start. It helps
   * preventing unwanted selections when clicking on an element. Default : 0
   * 
   */
  private int delay;

  /**
   * Tolerance, in pixels, for when selecting should start. If specified,
   * selecting will not start until after mouse is dragged beyond distance.
   * Default : 0
   * 
   */
  private int distance;
  
  public MouseOptions() {
   initDefault();
  }
  
  
  public String getCancel() {
    return cancel;
  }
  
  public int getDelay() {
    return delay;
  }

  public int getDistance() {
    return distance;
  }
  
  public void setCancel(String cancel) {
    this.cancel = cancel;
  }
  
  public void setDelay(int delay) {
    this.delay = delay;
  }
  
  public void setDistance(int distance) {
    this.distance = distance;
  }
  
  protected void initDefault() {
    delay = 0;
    distance = 0;
  }
  
}

