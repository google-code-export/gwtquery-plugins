package gwtquery.plugins.droppable.client.gwtportletsample;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.ArrayList;
import java.util.List;

public class GwtPortletSample implements EntryPoint {

  private static List<String> portletContents;
  private static List<String> portletHeader;

  static {
    portletContents = new ArrayList<String>();
    portletHeader = new ArrayList<String>();
    portletHeader.add("News");
    portletHeader.add("Feeds");
    portletHeader.add("Links");
    portletHeader.add("Lorem Ipsum");
    portletHeader.add("Home");
    portletHeader.add("Help");
    portletContents
        .add("Sed lobortis massa id massa mattis ut bibendum risus adipiscing. Vestibulum vel bibendum lacus. Nulla molestie convallis enim, tempus ullamcorper.");
    portletContents
        .add("Donec egestas diam eget metus varius dapibus. In consectetur mauris erat, eget euismod augue. Morbi vitae purus sit amet ligula.");
    portletContents
        .add("Suspendisse lacus mi, dictum sed tempus vel, porta vitae ante. Vestibulum ut lacus tortor. Etiam eget metus tortor, in aliquet.");
    portletContents
        .add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum ultricies urna vitae turpis scelerisque sed commodo urna consectetur. Donec gravida.");
    portletContents
        .add("Vestibulum ut erat sem. Vestibulum ultrices fermentum magna, non porttitor felis viverra vitae. Duis luctus facilisis magna eu hendrerit. Nunc.");
    portletContents
        .add("Proin dignissim urna hendrerit nisl mollis id tempor magna laoreet. Vestibulum orci mauris, iaculis non commodo sit amet, rhoncus sit. ");
  }

  public void onModuleLoad() {
    int i = 0;
    // create 3 DroppablePanel and add it 2 portlets
    DroppablePanel panel = new DroppablePanel();
    panel.add(new Portlet(portletHeader.get(i), portletContents.get(i++)));
    panel.add(new Portlet(portletHeader.get(i), portletContents.get(i++)));
    RootPanel.get("droppablePanel").add(panel);
    
    panel = new DroppablePanel();
    panel.add(new Portlet(portletHeader.get(i), portletContents.get(i++)));
    panel.add(new Portlet(portletHeader.get(i), portletContents.get(i++)));
    RootPanel.get("droppablePanel").add(panel);
    
    panel = new DroppablePanel();
    panel.add(new Portlet(portletHeader.get(i), portletContents.get(i++)));
    panel.add(new Portlet(portletHeader.get(i), portletContents.get(i++)));
    RootPanel.get("droppablePanel").add(panel);
    

  }

}
