package gwtquery.plugins.droppable.client.permissionmanagersample;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface Resource extends ClientBundle {

  public interface Css extends CssResource {
    String memberList();

    String permissionTree();

    String droppableHover();

    String dragHelper();
  }

  public Resource INSTANCE = GWT.create(Resource.class);

  @Source("style.css")
  public Css css();

  ImageResource contact();


}
