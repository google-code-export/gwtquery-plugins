package gwtquery.plugins.enhance.client.colorpicker;

import gwtquery.plugins.enhance.client.colorpicker.bundles.ColorPickerCss;
import net.auroris.ColorPicker.client.ColorPicker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.PopupPanel;

public class ColorPickerFull extends PopupPanel implements HasValueChangeHandlers<String>, HasValue<String> {
  
  ColorPicker picker;
  FlexTable t = new FlexTable();
  Button ok = new Button("OK");
  Button cancel = new Button("Cancel");
  
  @Override
  protected void onAttach() {
    super.onAttach();
    if (picker == null) {
      GWT.runAsync(new RunAsyncCallback() {
        public void onFailure(Throwable reason) {
        }
        public void onSuccess() {
          System.out.println("Loaded ");
          picker = new ColorPicker();
          t.setWidget(0, 0, picker);
        }
      });
    }
  }
  
  public ColorPickerFull() {
    super(true);
    ColorPickerCss.INSTANCE.css().ensureInjected();
    
    setAnimationEnabled(true);
    t.setCellPadding(5);
    t.setCellSpacing(0);
    DOM.setStyleAttribute(t.getElement(), "backgroundColor", "white");
    DOM.setStyleAttribute(t.getElement(), "border", "1px solid #cccccc");
    DOM.setStyleAttribute(t.getElement(), "fontFamily", "helvetica, arial");
    DOM.setStyleAttribute(t.getElement(), "fontSize", "8px");
    
    t.setWidth("420px");
    t.setText(0, 0, "loading ...");
    t.setWidget(0, 0, picker);
    t.getFlexCellFormatter().setColSpan(0, 0, 3);
    t.setWidget(1, 1, cancel);
    t.setWidget(1, 2, ok);
    t.getCellFormatter().setWidth(1, 0, "100%");
    t.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
    t.getCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_RIGHT);
    
    ok.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        ok();
      }
    });
    cancel.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        cancel();
      }
    });
    
    setStyleName("colorPicker");
    add(t);
  }
  
  private void ok() {
    this.hide();
    if (changeHandler != null) {
      changeHandler.onValueChange(new ValueChangeEvent<String>("#" + picker.getHexColor()){});
    }
  }
  private void cancel() {
    this.hide();
  }
  
  ValueChangeHandler<String> changeHandler;

  public HandlerRegistration addValueChangeHandler(
      ValueChangeHandler<String> handler) {
    changeHandler = handler;
    return new HandlerRegistration() {
      public void removeHandler() {
        changeHandler = null;
      }
    };
  }

  public String getValue() {
    return "#" + picker.getHexColor();
  }

  public void setValue(String value) {
    try {
      picker.setHex(value.replaceFirst("^#", ""));
    } catch (Exception e) {
    }
  }

  public void setValue(String value, boolean fireEvents) {
    setValue(value);
    if (fireEvents) {
      ok();
    }
  }

}
