package gwtquery.plugins.enhance.client.richtext;

import gwtquery.plugins.enhance.client.colorpicker.ColorPicker;
import gwtquery.plugins.enhance.client.fontpicker.FontPicker;
import gwtquery.plugins.enhance.client.fontpicker.FontPicker.FontPickerType;
import gwtquery.plugins.enhance.client.richtext.bundles.ToolbarImages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

public class Toolbar extends Composite {

  private class EventHandler implements ClickHandler, KeyUpHandler,
      KeyDownHandler {

    public void onClick(ClickEvent event) {
      Widget sender = (Widget) event.getSource();

      if (sender == bold) {
        basic.toggleBold();
      } else if (sender == italic) {
        basic.toggleItalic();
      } else if (sender == underline) {
        basic.toggleUnderline();
      } else if (sender == subscript) {
        basic.toggleSubscript();
      } else if (sender == superscript) {
        basic.toggleSuperscript();
      } else if (sender == strikethrough) {
        basic.toggleStrikethrough();
      } else if (sender == indent) {
        basic.rightIndent();
      } else if (sender == outdent) {
        basic.leftIndent();
      } else if (sender == justifyLeft) {
        basic.setJustification(RichTextArea.Justification.LEFT);
      } else if (sender == justifyCenter) {
        basic.setJustification(RichTextArea.Justification.CENTER);
      } else if (sender == justifyRight) {
        basic.setJustification(RichTextArea.Justification.RIGHT);
      } else if (sender == insertImage) {
        String url = Window.prompt("Enter an image URL:", "http://");
        if (url != null) {
          basic.insertImage(url);
        }
      } else if (sender == createLink) {
        String url = Window.prompt("Enter a link URL:", "http://");
        if (url != null) {
          basic.createLink(url);
        }
      } else if (sender == removeLink) {
        basic.removeLink();
      } else if (sender == hr) {
        basic.insertHorizontalRule();
      } else if (sender == ol) {
        basic.insertOrderedList();
      } else if (sender == ul) {
        basic.insertUnorderedList();
      } else if (sender == removeFormat) {
        basic.removeFormat();
      } else if (sender == richText) {
        updateStatus();
      } else if (sender == backColors) {
        backColorsPicker.setPopupPosition(sender.getAbsoluteLeft(),
            sender.getAbsoluteTop() + 20);
        backColorsPicker.show();
      } else if (sender == foreColors) {
        foreColorsPicker.setPopupPosition(sender.getAbsoluteLeft(),
            sender.getAbsoluteTop() + 20);
        foreColorsPicker.show();
      } else if (sender == fontFamily) {
        fontFamilyPicker.setPopupPosition(sender.getAbsoluteLeft(),
            sender.getAbsoluteTop() + 20);
        fontFamilyPicker.show();
      } else if (sender == fontSize) {
        fontSizePicker.setPopupPosition(sender.getAbsoluteLeft(),
            sender.getAbsoluteTop() + 20);
        fontSizePicker.show();
      }
    }

    public void onKeyDown(KeyDownEvent event) {
    }

    public void onKeyUp(KeyUpEvent event) {
      Widget sender = (Widget) event.getSource();
      if (sender == richText) {
        updateStatus();
      }
    }

  }

  private ValueChangeHandler<String> bgColorHandler = new ValueChangeHandler<String>() {
    public void onValueChange(ValueChangeEvent<String> event) {
      basic.setBackColor(event.getValue());
      backColorsPicker.hide();
    }
  };
  private ValueChangeHandler<String> fgColorHandler = new ValueChangeHandler<String>() {
    public void onValueChange(ValueChangeEvent<String> event) {
      basic.setForeColor(event.getValue());
      foreColorsPicker.hide();
    }
  };
  
  private ValueChangeHandler<String> fontFamilyHandler = new ValueChangeHandler<String>() {
    public void onValueChange(ValueChangeEvent<String> event) {
      basic.setFontName(event.getValue());
      fontFamilyPicker.hide();
    }
  };
  
  private ValueChangeHandler<String> fontSizeHandler = new ValueChangeHandler<String>() {
    public void onValueChange(ValueChangeEvent<String> event) {
      basic.setFontSize(fontSizePicker.getFontSize());
      fontSizePicker.hide();
    }
  };

  private static final ToolbarImages images = (ToolbarImages) GWT.create(ToolbarImages.class);
  private EventHandler handler = new EventHandler();

  private RichTextArea richText;
  private RichTextArea.Formatter basic;

  private HorizontalPanel topPanel = new HorizontalPanel();
  private ToggleButton bold;
  private ToggleButton italic;
  private ToggleButton underline;
  private ToggleButton subscript;
  private ToggleButton superscript;
  private ToggleButton strikethrough;
  private PushButton indent;
  private PushButton outdent;
  private PushButton justifyLeft;
  private PushButton justifyCenter;
  private PushButton justifyRight;
  private PushButton hr;
  private PushButton ol;
  private PushButton ul;
  private PushButton insertImage;
  private PushButton createLink;
  private PushButton removeLink;
  private PushButton removeFormat;

  private PushButton fontFamily;
  private FontPicker fontFamilyPicker = new FontPicker(
      FontPickerType.FONT_FAMILY);
  private PushButton fontSize;
  private FontPicker fontSizePicker = new FontPicker(FontPickerType.FONT_SIZE);

  private PushButton backColors;
  private PushButton foreColors;
  private ColorPicker backColorsPicker = new ColorPicker();
  private ColorPicker foreColorsPicker = new ColorPicker();

  public Toolbar(RichTextArea richText) {
    this.richText = richText;
    this.basic = richText.getFormatter();
    
    ToolbarConstants constants = GWT.create(ToolbarConstants.class);

    topPanel.setWidth("100%");

    initWidget(topPanel);
    setStyleName("gwt-RichTextToolbar");
    richText.addStyleName("hasRichTextToolbar");

    topPanel.add(bold = createToggleButton(images.bold(), constants.editor_bold()));
    topPanel.add(italic = createToggleButton(images.italic(),
        constants.editor_italic()));
    topPanel.add(underline = createToggleButton(images.underline(),
        constants.editor_underline()));
    topPanel.add(strikethrough = createToggleButton(images.strikeThrough(),
        constants.editor_strikeThrough()));
    topPanel.add(backColors = createPushButton(images.backColors(),
        constants.editor_background()));
    topPanel.add(foreColors = createPushButton(images.foreColors(),
        constants.editor_foreground()));
    topPanel.add(fontFamily = createPushButton(images.fonts(),
        constants.editor_font()));
    topPanel.add(fontSize = createPushButton(images.fontSizes(),
        constants.editor_size()));
    topPanel.add(subscript = createToggleButton(images.subscript(),
        constants.editor_subscript()));
    topPanel.add(superscript = createToggleButton(images.superscript(),
        constants.editor_superscript()));
    topPanel.add(justifyLeft = createPushButton(images.justifyLeft(),
        constants.editor_justifyLeft()));
    topPanel.add(justifyCenter = createPushButton(images.justifyCenter(),
        constants.editor_justifyCenter()));
    topPanel.add(justifyRight = createPushButton(images.justifyRight(),
        constants.editor_justifyRight()));
    topPanel.add(indent = createPushButton(images.indent(),
        constants.editor_indent()));
    topPanel.add(outdent = createPushButton(images.outdent(),
        constants.editor_outdent()));
    //    topPanel.add(hr = createPushButton(images.hr(), strings.editor_hr()));
    topPanel.add(ol = createPushButton(images.ol(), constants.editor_ol()));
    topPanel.add(ul = createPushButton(images.ul(), constants.editor_ul()));
    topPanel.add(insertImage = createPushButton(images.insertImage(),
        constants.editor_insertImage()));
    topPanel.add(createLink = createPushButton(images.createLink(),
        constants.editor_createLink()));
    topPanel.add(removeLink = createPushButton(images.removeLink(),
        constants.editor_removeLink()));
    topPanel.add(removeFormat = createPushButton(images.removeFormat(),
        constants.editor_removeFormat()));

    HTML topEmtyCell = new HTML("");
    topPanel.add(topEmtyCell);
    topPanel.setCellWidth(topEmtyCell, "100%");

    richText.addKeyDownHandler(handler);
    richText.addKeyUpHandler(handler);
    richText.addClickHandler(handler);
    backColorsPicker.addValueChangeHandler(bgColorHandler);
    foreColorsPicker.addValueChangeHandler(fgColorHandler);
    fontFamilyPicker.addValueChangeHandler(fontFamilyHandler);
    fontSizePicker.addValueChangeHandler(fontSizeHandler);
  }

  private PushButton createPushButton(ImageResource img, String tip) {
    PushButton pb = new PushButton(new Image(img));
    pb.addClickHandler(handler);
    pb.setTitle(tip);
    return pb;
  }

  private ToggleButton createToggleButton(ImageResource img, String tip) {
    ToggleButton tb = new ToggleButton(new Image(img));
    tb.addClickHandler(handler);
    tb.setTitle(tip);
    return tb;
  }

  private void updateStatus() {
    bold.setDown(basic.isBold());
    italic.setDown(basic.isItalic());
    underline.setDown(basic.isUnderlined());
    strikethrough.setDown(basic.isStrikethrough());
    //    subscript.setDown(basic.isSubscript());
    //    superscript.setDown(basic.isSuperscript());
    foreColorsPicker.hide();
    backColorsPicker.hide();
    fontFamilyPicker.hide();
    fontSizePicker.hide();
  }
}
