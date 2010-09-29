package gwtquery.plugins.commonui.client;


public class GQueryUiImplIE extends GQueryUiImpl{
  
  @Override
  protected boolean scrollParentPositionTest(GQueryUi gQueryUi){
	 String position = gQueryUi.css("position");
    return ("absolute".equals(position) || "relative".equals(position) || "static".equals(position));    
  }

}
