package gwtquery.plugins.enhance.client;

import gwtquery.plugins.enhance.client.colorpicker.ColorPicker;
import gwtquery.plugins.enhance.client.colorpicker.ColorPickerFactory;
import gwtquery.plugins.enhance.client.colorpicker.ColorPickerFactory.ColorPickerType;
import gwtquery.plugins.enhance.client.colorpicker.ColorPickerFull;
import gwtquery.plugins.enhance.client.fontpicker.FontPicker;
import gwtquery.plugins.enhance.client.fontpicker.FontPicker.FontPickerType;
import gwtquery.plugins.enhance.client.fontpicker.FontPickerFactory;
import gwtquery.plugins.enhance.client.gwt.ButtonWidgetFactory;
import gwtquery.plugins.enhance.client.gwt.CheckBoxWidgetFactory;
import gwtquery.plugins.enhance.client.gwt.DateBoxWidgetFactory;
import gwtquery.plugins.enhance.client.gwt.DecoratorPanelWidgetFactory;
import gwtquery.plugins.enhance.client.gwt.DisclosurePanelWidgetFactory;
import gwtquery.plugins.enhance.client.gwt.DisclosurePanelWidgetFactory.DisclosurePanelOptions;
import gwtquery.plugins.enhance.client.gwt.ListBoxWidgetFactory;
import gwtquery.plugins.enhance.client.gwt.ListBoxWidgetFactory.ListBoxOptions;
import gwtquery.plugins.enhance.client.gwt.PasswordTextBoxWidgetFactory;
import gwtquery.plugins.enhance.client.gwt.RadioButtonWidgetFactory;
import gwtquery.plugins.enhance.client.gwt.RadioButtonWidgetFactory.RadioButtonOption;
import gwtquery.plugins.enhance.client.gwt.StackPanelWidgetFactory;
import gwtquery.plugins.enhance.client.gwt.StackPanelWidgetFactory.StackPanelOptions;
import gwtquery.plugins.enhance.client.gwt.SuggestBoxWidgetFactory;
import gwtquery.plugins.enhance.client.gwt.SuggestBoxWidgetFactory.SuggestBoxOptions;
import gwtquery.plugins.enhance.client.gwt.TabPanelWidgetFactory;
import gwtquery.plugins.enhance.client.gwt.TabPanelWidgetFactory.TabPanelOptions;
import gwtquery.plugins.enhance.client.gwt.TextAreaWidgetFactory;
import gwtquery.plugins.enhance.client.gwt.TextBoxWidgetFactory;
import gwtquery.plugins.enhance.client.multiselect.MultiSelect;
import gwtquery.plugins.enhance.client.multiselect.MultiSelectFactory;
import gwtquery.plugins.enhance.client.richtext.RichTextAreaFactory;
import gwtquery.plugins.enhance.client.slider.SliderBar;
import gwtquery.plugins.enhance.client.slider.SliderFactory;

import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.Plugin;
import com.google.gwt.query.client.plugins.Widgets;
import com.google.gwt.query.client.plugins.widgets.WidgetInitializer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * Enhance plugin for GwtQuery
 * 
 * @author Manuel Carrasco Moñino
 */
public class Enhance extends Widgets {

  // A shortcut to the class 
  public static final Class<Enhance> Enhance = Enhance.class;

  // Register the plugin in GQuery
  static {
    GQuery.registerPlugin(Enhance.class, new Plugin<Enhance>() {
      public Enhance init(GQuery gq) {
        return new Enhance(gq);
      }
    });
  }

  // Initialization
  public Enhance(GQuery gq) {
    super(gq);
  }

  public Widgets richText() {
    return richText(null);
  }
  
  public Widgets richText(WidgetInitializer<RichTextArea> initializer) {
    return widgets(new RichTextAreaFactory(), initializer);
  }

  public Widgets colorBox() {
    return colorBox(ColorPickerType.SIMPLE, null);
  }
  
  public Widgets colorBox(ColorPickerType type) {
    return colorBox(type, null);
  }
  
  public Widgets colorBox(ColorPickerType type, WidgetInitializer<TextBox> initializer) {
    return widgets(new ColorPickerFactory(type), initializer);
  }

  public Widgets fontFamilyBox() {
    return fontFamilyBox(null);
  }
  
  public Widgets fontFamilyBox(WidgetInitializer<TextBox> initializer) {
    return widgets(new FontPickerFactory(FontPickerType.FONT_FAMILY), initializer);
  }

  public Widgets fontSizeBox() {
    return fontSizeBox(null);
  }
  
  public Widgets fontSizeBox(WidgetInitializer<TextBox> initializer) {
    return widgets(new FontPickerFactory(FontPickerType.FONT_SIZE), initializer);
  }
  
  public Widgets slider(double min, double max) {
    return slider(min, max, 10, null);
  }
  
  public Widgets slider(double min, double max, int steps, WidgetInitializer<SliderBar> initializer) {
    return widgets(new SliderFactory(min, max, steps), initializer);
  }
  
  public Widgets multiselect() {
    return multiselect(null);
  }
  
  public Widgets multiselect(WidgetInitializer<MultiSelect> initializer) {
    return widgets(new MultiSelectFactory(), initializer);
  }
  
  public String[] vals() {
    String[] ret = new String[0];
    Widget w = widget();
    if (w != null) {
      if (w instanceof MultiSelect) {
        ret = ((MultiSelect) w).getSelectedItems().toArray(ret);
      } else if (w instanceof ColorPickerFull) {
        ret = new String[]{((ColorPickerFull) w).getValue()};
      } else if (w instanceof ColorPicker) {
        ret = new String[]{((ColorPicker) w).getValue()};
      } else if (w instanceof FontPicker) {
        ret = new String[]{((FontPicker) w).getValue()};
      } else if (w instanceof SliderBar) {
        ret = new String[]{"" + ((SliderBar) w).getCurrentValue()};
      } else if (w instanceof RichTextArea) {
        ret = new String[]{"" + ((RichTextArea) w).getHTML()};
      } else {
        ret = super.vals();
      }
    } else {
      ret = super.vals();
    }
    return ret == null ? new String[0] : ret;
  }
  
  public Enhance val(String...vals) {
    Widget w = widget();
    if (w != null) {
      if (w instanceof MultiSelect) {
        ((MultiSelect) w).setValues(vals);
      } else if (w instanceof ColorPickerFull) {
        ((ColorPickerFull) w).setValue(vals[0]);
      } else if (w instanceof ColorPicker) {
        ((ColorPicker) w).setValue(vals[0]);
      } else if (w instanceof FontPicker) {
        ((FontPicker) w).setValue(vals[0]);
      } else if (w instanceof SliderBar) {
        ((SliderBar) w).setCurrentValue(Double.valueOf(vals[0]));
      } else if (w instanceof RichTextArea) {
        ((RichTextArea) w).setHTML(vals[0]);
      } else {
        super.val(vals);
      }
    } else {
      super.val(vals);
    }
    return this;
  }
  
  
  /**
   * Create a {@link Button} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link Button} created
   * by passing them in parameter.
   * 
   */
  public Widgets button() {
    return widgets(new ButtonWidgetFactory(), null);
  }

  /**
   * Create a {@link Button} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link Button} created
   * by passing them in parameter.
   * 
   */
  public Widgets button(WidgetInitializer<Button> initializers) {
    return widgets(new ButtonWidgetFactory(), initializers);
  }

  /**
   * Create a {@link DateBox} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link Button} created
   * by passing them in parameter.
   */
  public Widgets datebox() {
    return widgets(new DateBoxWidgetFactory(), null);
  }

  /**
   * Create a {@link DateBox} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link Button} created
   * by passing them in parameter.
   */
  public Widgets datebox(WidgetInitializer<DateBox> initializers) {
    return widgets(new DateBoxWidgetFactory(), initializers);
  }

  /**
   * Create a {@link DisclosurePanel} widget for each selected elements.
   */
  public Widgets disclosurePanel() {
    return disclosurePanel(new DisclosurePanelOptions(), null);
  }

  /**
   * Create a {@link DisclosurePanel} widget for each selected elements.
   */
  public Widgets disclosurePanel(DisclosurePanelOptions o,
      WidgetInitializer<DisclosurePanel> initializers) {
    return widgets(new DisclosurePanelWidgetFactory(o), initializers);
  }

  /**
   * Create a {@link DisclosurePanel} widget for each selected elements.
   */
  public Widgets disclosurePanel(DisclosurePanelOptions o) {
    return widgets(new DisclosurePanelWidgetFactory(o), null);
  }

  /**
   * Create {@link DisclosurePanel} widget for each selected elements.
   */
  public Widgets disclosurePanel(WidgetInitializer<DisclosurePanel> initializers) {
    return disclosurePanel(new DisclosurePanelOptions(), initializers);
  }

  /**
   * Create a {@link ListBox} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link ListBox}
   * created by passing them in parameter.
   * 
   */
  public Widgets listBox(ListBoxOptions options,
      WidgetInitializer<ListBox> initializers) {
    return widgets(new ListBoxWidgetFactory(options), initializers);
  }

  /**
   * Create a {@link ListBox} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link ListBox}
   * created by passing them in parameter.
   * 
   */
  public Widgets listBox(ListBoxOptions options) {
    return widgets(new ListBoxWidgetFactory(options), null);
  }

  /**
   * Create a {@link ListBox} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link ListBox}
   * created by passing them in parameter.
   */
  public Widgets listBox(WidgetInitializer<ListBox> initializers) {
    return listBox(new ListBoxOptions(), initializers);
  }

  /**
   * Create a {@link ListBox} widget for each selected element.
   */
  public Widgets listBox() {
    return listBox(new ListBoxOptions(), null);
  }

  /**
   * Create a {@link PasswordTextBox} widget for each selected element.
   */
  public Widgets passwordBox() {
    return widgets(new PasswordTextBoxWidgetFactory(), null);
  }

  /**
   * Create a {@link CheckBox} widget for each selected element.
   */
  public Widgets checkBox(WidgetInitializer<CheckBox> initializers) {
    return widgets(new CheckBoxWidgetFactory(), initializers);
  }

  /**
   * Create a {@link CheckBox} widget for each selected element.
   */
  public Widgets checkBox() {
    return widgets(new CheckBoxWidgetFactory(), null);
  }

  /**
   * Create a {@link PasswordTextBox} widget for each selected element. The
   * <code>initializers</code> will be called on each new
   * {@link PasswordTextBox} created by passing them in parameter.
   * 
   */
  public Widgets passwordBox(WidgetInitializer<PasswordTextBox> initializers) {
    return widgets(new PasswordTextBoxWidgetFactory(), initializers);
  }

  /*
   * public Widgets richtext(WidgetInitializer initializers) { return
   * widgets(new RichTextWidgetFactory(), initializers); }
   */

  /**
   * Create a {@link StackPanel} widget for each selected elements. Each div
   * element inside a selected element will create a tab and the first h3
   * element inside the div will be used as title
   */
  public Widgets stackPanel(StackPanelOptions o,
      WidgetInitializer<StackPanel> initializers) {
    return widgets(new StackPanelWidgetFactory(o), initializers);
  }

  /**
   * Create a {@link StackPanel} widget for each selected elements. Each div
   * element inside a selected element will create a tab and the first h3
   * element inside the div will be used as title
   */
  public Widgets stackPanel(StackPanelOptions o) {
    return widgets(new StackPanelWidgetFactory(o), null);
  }

  /**
   * Create {@link TabPanel} widget for each selected elements. Each div element
   * will create a tab and the first h3 element inside the div will be used as
   * title
   */
  public Widgets stackPanel(WidgetInitializer<StackPanel> initializers) {
    return stackPanel(new StackPanelOptions(), initializers);
  }

  /**
   * Create {@link TabPanel} widget for each selected elements. Each div element
   * will create a tab and the first h3 element inside the div will be used as
   * title
   */
  public Widgets stackPanel() {
    return stackPanel(new StackPanelOptions(), null);
  }

  /**
   * Create {@link RadioButton} widget for each selected elements. All
   * {@link RadioButton} created will be group under the same name specified in
   * the {@link RadioButtonOption o}
   */
  public Widgets radioButton(RadioButtonOption o,
      WidgetInitializer<RadioButton> initializers) {
    return widgets(new RadioButtonWidgetFactory(o), initializers);
  }

  /**
   * Create {@link RadioButton} widget for each selected elements. All
   * {@link RadioButton} created will be group under the same name specified in
   * the {@link RadioButtonOption o}
   */
  public Widgets radioButton(RadioButtonOption o) {
    return widgets(new RadioButtonWidgetFactory(o), null);
  }

  /**
   * Create {@link RadioButton} widget for each selected elements. All
   * {@link RadioButton} created will be group under the same name
   */
  public Widgets radioButton(WidgetInitializer<RadioButton> initializers) {
    return radioButton(new RadioButtonOption(), initializers);
  }

  /**
   * Create {@link RadioButton} widget for each selected elements. All
   * {@link RadioButton} created will be group under the same name
   */
  public Widgets radioButton() {
    return radioButton(new RadioButtonOption(), null);
  }

  /**
   * Create a {@link SuggestBox} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link SuggestBox}
   * created by passing them in parameter.
   * 
   */
  public Widgets suggestBox(SuggestBoxOptions options,
      WidgetInitializer<SuggestBox> initializers) {
    return widgets(new SuggestBoxWidgetFactory(options), initializers);
  }

  /**
   * Create a {@link SuggestBox} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link SuggestBox}
   * created by passing them in parameter.
   * 
   */
  public Widgets suggestBox(SuggestBoxOptions options) {
    return widgets(new SuggestBoxWidgetFactory(options), null);
  }

  /**
   * Create a {@link SuggestBox} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link SuggestBox}
   * created by passing them in parameter.
   * 
   */
  public Widgets suggestBox(WidgetInitializer<SuggestBox> initializers) {
    return suggestBox(new SuggestBoxOptions(), initializers);
  }

  /**
   * Create a {@link SuggestBox} widget for each selected element.
   */
  public Widgets suggestBox() {
    return suggestBox(new SuggestBoxOptions(), null);
  }

  /**
   * Create a {@link TabPanel} widget for each selected elements. Each div
   * element inside a selected element will create a tab and the first h3
   * element inside the div will be used as title
   */
  public Widgets tabPanel(TabPanelOptions o,
      WidgetInitializer<TabPanel> initializers) {
    return widgets(new TabPanelWidgetFactory(o), initializers);
  }

  /**
   * Create a {@link TabPanel} widget for each selected elements. Each div
   * element inside a selected element will create a tab and the first h3
   * element inside the div will be used as title
   */
  public Widgets tabPanel(TabPanelOptions o) {
    return widgets(new TabPanelWidgetFactory(o), null);
  }

  /**
   * Create {@link TabPanel} widget for each selected elements. Each div element
   * will create a tab and the first h3 element inside the div will be used as
   * title
   */
  public Widgets tabPanel(WidgetInitializer<TabPanel> initializers) {
    return tabPanel(new TabPanelOptions(), initializers);
  }

  /**
   * Create {@link TabPanel} widget for each selected elements. Each div element
   * will create a tab and the first h3 element inside the div will be used as
   * title
   */
  public Widgets tabPanel() {
    return tabPanel(new TabPanelOptions(), null);
  }

  /**
   * Create a {@link TextBox} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link TextBox}
   * created by passing them in parameter.
   * 
   */
  public Widgets textBox() {
    return widgets(new TextBoxWidgetFactory(), null);
  }

  /**
   * Create a {@link TextBox} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link TextBox}
   * created by passing them in parameter.
   * 
   */
  public Widgets textBox(WidgetInitializer<TextBox> initializers) {
    return widgets(new TextBoxWidgetFactory(), initializers);
  }

  /**
   * Create a {@link TextArea} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link TextBox}
   * created by passing them in parameter.
   * 
   */
  public Widgets textArea() {
    return widgets(new TextAreaWidgetFactory(), null);
  }

  /**
   * Create a {@link TextArea} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link TextBox}
   * created by passing them in parameter.
   * 
   */
  public Widgets textArea(WidgetInitializer<TextArea> initializers) {
    return widgets(new TextAreaWidgetFactory(), initializers);
  }

  /**
   * Create a {@link DecoratorPanel} widget for each selected element. 
   */
  public Widgets decoratorPanel() {
    return widgets(new DecoratorPanelWidgetFactory(), null);
  }

  /**
   * Create a {@link DecoratorPanel} widget for each selected element. The
   * <code>initializers</code> will be called on each new {@link DecoratorPanel}
   * created by passing them in parameter.
   * 
   */
  public Widgets decoratorPanel(WidgetInitializer<DecoratorPanel> initializers) {
    return widgets(new DecoratorPanelWidgetFactory(), initializers);
  }
}
