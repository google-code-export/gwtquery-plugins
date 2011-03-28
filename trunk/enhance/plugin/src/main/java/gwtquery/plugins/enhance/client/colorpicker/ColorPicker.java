package gwtquery.plugins.enhance.client.colorpicker;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * A ColorPicker panel to select colors from a color table.
 * 
 * This class was originally written by Manuel Carrasco for the
 * Apache Hupa project (http://james.apache.org/hupa)
 */
public class ColorPicker extends PopupPanel implements HasValueChangeHandlers<String>, HasValue<String>, ClickHandler {

  private class ColorCell extends Label {
    String rgbColor;

    public ColorCell(long color) {
      this(Long.toHexString(color));
    }

    public ColorCell(String color) {
      super();
      setColor(color);
      setTitle(rgbColor);
      setSize("14px", "12px");
      DOM.setStyleAttribute(getElement(), "backgroundColor", rgbColor);
      setBorderColor("#cccccc");
      addMouseOverHandler(new MouseOverHandler() {
        public void onMouseOver(MouseOverEvent event) {
          setBorderColor("#ffffff");
        }
      });
      addMouseOutHandler(new MouseOutHandler() {
        public void onMouseOut(MouseOutEvent event) {
          setBorderColor("#cccccc");
        }
      });
    }

    public String getColor() {
      return rgbColor;
    }

    public void setBorderColor(String color) {
      DOM.setStyleAttribute(getElement(), "border", "1px solid " + color);
    }

    void setColor(String s) {
      while (s.length() < 6)
        s = "0" + s;
      rgbColor = "#" + s;
    }
  }

  ValueChangeHandler<String> changeHandler = null;

  private String color = "";
  private FlexTable t = new FlexTable();

  private static final long[] DEFAULT_COLORS = new long[]{
      0xffffff, 0xcccccc, 0xc0c0c0, 0x999999, 0x666666, 0x333333, 0x000000,
      0xffcccc, 0xff6666, 0xff0000, 0xcc0000, 0x990000, 0x660000, 0x330000,
      0xffcc99, 0xff9966, 0xff9900, 0xfd6500, 0xcb6500, 0x983200, 0x653200,
      0xffff99, 0xffff66, 0xffcc66, 0xfdcb32, 0xcb9832, 0x986532, 0x653232,
      0xffffcc, 0xffff33, 0xffff00, 0xfdcb00, 0x989800, 0x656500, 0x323200,
      0x99ff99, 0x66ff99, 0x33ff33, 0x32cb00, 0x009800, 0x006500, 0x003200,
      0x99ffff, 0x33ffff, 0x66cccc, 0x00cbcb, 0x329898, 0x326565, 0x003232,
      0xccffff, 0x66ffff, 0x33ccff, 0x3265fd, 0x3232fd, 0x000098, 0x000065,
      0xccccff, 0x9999ff, 0x6666cc, 0x6532fd, 0x6500cb, 0x323298, 0x320098,
      0xffccff, 0xff99ff, 0xcc66cc, 0xcb32cb, 0x983298, 0x653265, 0x320032,};

  public ColorPicker() {
    super(true);
    t.setCellPadding(0);
    t.setCellSpacing(0);
    DOM.setStyleAttribute(t.getElement(), "border", "1px solid #cccccc");
    add(t);
    setAnimationEnabled(true);
    setStyleName("colorPicker");
    setColors(DEFAULT_COLORS);
  }
  
  public void setColors(long[] colors) {
    int rows = 7;
    for (int i = 7; i < 12; i++){
      if (colors.length % i == 0) {
        rows = i;
        break;
      }
    }
    int i = 0;
    t.clear();
    for (int r = 0; i < colors.length; r++) {
      for (int c = 0; c < rows && i < colors.length; c++, i++) {
        ColorCell cell = new ColorCell(colors[i]);
        cell.addClickHandler(this);
        t.setWidget(r, c, cell);
      }
    }
  }

  public HandlerRegistration addValueChangeHandler(
      ValueChangeHandler<String> handler) {
    changeHandler = handler;
    return new HandlerRegistration() {
      public void removeHandler() {
        changeHandler = null;
      }
    };
  }

  public void onClick(ClickEvent event) {
    ColorCell cell = (ColorCell) event.getSource();
    setValue(cell.getColor(), true);
  }

  public String getValue() {
    return color;
  }

  public void setValue(String value) {
    color = value;
  }

  public void setValue(String value, boolean fireEvents) {
    this.color = value;
    if (fireEvents && changeHandler != null) {
      changeHandler.onValueChange(new ValueChangeEvent<String>(value) {});
    }
  }
}
