package gwtquery.plugins.commonui.client;


public class GQueryUiImplIE extends GQueryUiImpl{
  
  @Override
  protected boolean scrollParentPositionTest(GQueryUi gQueryUi){
    return gQueryUi.css("position").matches("(absolute|relative|static)");
  }

}
