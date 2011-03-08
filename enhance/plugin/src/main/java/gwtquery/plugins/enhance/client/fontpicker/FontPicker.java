package gwtquery.plugins.enhance.client.fontpicker;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RichTextArea.FontSize;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FontPicker extends PopupPanel implements ClickHandler,
    HasValueChangeHandlers<FontPicker> {

  private class FontCell extends HTML {
    String cellFont;

    public FontCell(String font) {
      super(font);
      this.cellFont = font;
      DOM.setStyleAttribute(getElement(), "backgroundColor", "#D8ECFD");
      DOM.setStyleAttribute(getElement(), "padding", "2px 4px 2px 8px");
      addMouseOverHandler(new MouseOverHandler() {
        public void onMouseOver(MouseOverEvent event) {
          DOM.setStyleAttribute(getElement(), "backgroundColor", "#7FAAFF");
        }
      });
      addMouseOutHandler(new MouseOutHandler() {
        public void onMouseOut(MouseOutEvent event) {
          DOM.setStyleAttribute(getElement(), "backgroundColor", "#D8ECFD");
        }
      });
    }

    public String getFont() {
      return cellFont;
    }

  }

  public enum FontPickerType {
    FONT_FAMILY, FONT_SIZE
  }

  private static final String[] fontFamilies = new String[]{
      "Times New Roman", "Arial", "Courier New", "Georgia", "Trebuchet",
      "Verdana", "Comic Sans MS"};

  private static final String[] fontSizes = new String[]{
      "xx-small", "x-small", "small", "medium", "large", "x-large", "xx-large"};

  private String font = "";

  ValueChangeHandler<FontPicker> changeHandler = null;

  public FontPicker(FontPickerType type) {
    super(true);
    VerticalPanel container = new VerticalPanel();
    DOM.setStyleAttribute(container.getElement(), "border",
        "1px solid  #7FAAFF");
    DOM.setStyleAttribute(container.getElement(), "backgroundColor", "#D8ECFD");
    DOM.setStyleAttribute(container.getElement(), "cursor", "pointer");

    String[] fonts = type == FontPickerType.FONT_SIZE ? fontSizes
        : fontFamilies;

    for (int i = 0; i < fonts.length; i++) {
      FontCell cell;
      if (type == FontPickerType.FONT_SIZE) {
        cell = new FontCell("" + (i + 1));
        DOM.setStyleAttribute(cell.getElement(), "fontSize", fonts[i]);
      } else {
        cell = new FontCell(fonts[i]);
        DOM.setStyleAttribute(cell.getElement(), "fontFamily", fonts[i]);
      }
      cell.addClickHandler(this);
      container.add(cell);
    }

    add(container);
    setAnimationEnabled(true);
    setStyleName("colorPicker");
  }

  public HandlerRegistration addValueChangeHandler(
      ValueChangeHandler<FontPicker> handler) {
    assert changeHandler == null : "Change handler is already defined";
    changeHandler = handler;
    return new HandlerRegistration() {
      public void removeHandler() {
        changeHandler = null;
      }
    };
  }

  public String getFontName() {
    return font;
  }

  public FontSize getFontSize() {
    switch (Integer.valueOf(font).intValue()) {
      case 1:
        return FontSize.XX_SMALL;
      case 2:
        return FontSize.X_SMALL;
      case 4:
        return FontSize.MEDIUM;
      case 5:
        return FontSize.LARGE;
      case 6:
        return FontSize.X_LARGE;
      case 7:
        return FontSize.XX_LARGE;
      case 3:
      default:
        return FontSize.SMALL;
    }
  }
  
  public String getFontSizeCssName() {
    switch (Integer.valueOf(font).intValue()) {
      case 1:
        return com.google.gwt.query.client.css.FontSizeProperty.FontSize.XX_SMALL.getCssName();
      case 2:
        return com.google.gwt.query.client.css.FontSizeProperty.FontSize.X_SMALL.getCssName();
      case 4:
        return com.google.gwt.query.client.css.FontSizeProperty.FontSize.MEDIUM.getCssName();
      case 5:
        return com.google.gwt.query.client.css.FontSizeProperty.FontSize.LARGE.getCssName();
      case 6:
        return com.google.gwt.query.client.css.FontSizeProperty.FontSize.X_LARGE.getCssName();
      case 7:
        return com.google.gwt.query.client.css.FontSizeProperty.FontSize.XX_LARGE.getCssName();
      case 3:
      default:
        return com.google.gwt.query.client.css.FontSizeProperty.FontSize.SMALL.getCssName();
    }
  }

  public void onClick(ClickEvent event) {
    FontCell cell = (FontCell) event.getSource();
    this.font = cell.getFont();
    if (changeHandler != null)
      changeHandler.onValueChange(new ValueChangeEvent<FontPicker>(this) {
      });
  }

}
