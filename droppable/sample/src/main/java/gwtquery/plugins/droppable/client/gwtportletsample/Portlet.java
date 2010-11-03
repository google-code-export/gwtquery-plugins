package gwtquery.plugins.droppable.client.gwtportletsample;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

public class Portlet extends DraggableWidget<Widget> {

  interface PortletUiBinder extends UiBinder<Widget, Portlet> {
  }

  private static PortletUiBinder uiBinder = GWT.create(PortletUiBinder.class);

  @UiField
  DivElement content;
  @UiField
  DivElement header;

  public Portlet(String header, String content) {
    initWidget(uiBinder.createAndBindUi(this));
    setup();
    setHeader(header);
    setContent(content);
  }

  public void setContent(String content) {
    this.content.setInnerText(content);
  }

  public void setHeader(String header) {
    this.header.setInnerText(header);
  }

  private void setup() {
    useCloneAsHelper();
    setOpacity(new Float(0.8));
    setRevert(RevertOption.ON_INVALID_DROP);

  }
}
