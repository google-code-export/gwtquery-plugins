package gwtquery.plugins.enhance.client.common;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class TextBoxWrapper extends TextBox {
  public TextBoxWrapper(Element e) {
    super(e);
    onAttach();
    try {
      RootPanel.detachOnWindowClose(this);
    } catch (Throwable e2) {
    }
  }
}
