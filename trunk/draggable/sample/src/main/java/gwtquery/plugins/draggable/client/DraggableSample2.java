/*
 * Copyright 2010 The gwtquery plugins team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package gwtquery.plugins.draggable.client;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.draggable.client.DraggableOptions.AxisOption;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;

/**
 * Sample allows testing options
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 *
 */
public class DraggableSample2 implements EntryPoint {

  public static class DraggableOptionsPanel extends Composite {

    @UiTemplate(value = "DraggableOptionsPanel.ui.xml")
    interface DraggableOptionsPanelUiBinder extends
        UiBinder<Widget, DraggableOptionsPanel> {
    }

    private static DraggableOptionsPanelUiBinder uiBinder = GWT
        .create(DraggableOptionsPanelUiBinder.class);

    private DraggableOptions options;

    @UiField
    ListBox helperListBox;
    @UiField
    ListBox axisListBox;
    @UiField
    ListBox cursorListBox;
    @UiField
    TextBox delayBox;
    @UiField
    TextBox distanceBox;
    @UiField
    CheckBox disabledCheckBox;
    @UiField
    CheckBox handleCheckBox;
    @UiField
    TextBox opacityBox;
    @UiField
    CheckBox scrollCheckBox;
    @UiField
    TextBox scrollSensivityBox;
    @UiField
    TextBox scrollSpeedBox;

    public DraggableOptionsPanel(DraggableOptions o) {
      options = o;
      initWidget(uiBinder.createAndBindUi(this));
      init();

    }

    @UiHandler(value = "axisListBox")
    public void onAxisChange(ChangeEvent e) {
      AxisOption axis = AxisOption.valueOf(axisListBox.getValue(axisListBox
          .getSelectedIndex()));
      options.setAxis(axis);
    }

    @UiHandler(value = "cursorListBox")
    public void onCursorChange(ChangeEvent e) {
      Cursor c = Cursor.valueOf(cursorListBox.getValue(cursorListBox
          .getSelectedIndex()));
      options.setCursor(c);

    }

    @UiHandler(value = "delayBox")
    public void onDelayChange(ValueChangeEvent<String> e) {
      options.setDelay(new Integer(e.getValue()));
    }

    @UiHandler(value = "disabledCheckBox")
    public void onDisabledChange(ValueChangeEvent<Boolean> e) {
      options.setDisabled(e.getValue());
    }

    @UiHandler(value = "distanceBox")
    public void onDistanceChange(ValueChangeEvent<String> e) {
      Integer distance;
      try {
        distance = new Integer(e.getValue());
      } catch (NumberFormatException ex) {
        Window.alert("Please specify a correct number for distance");
        return;
      }
      options.setDistance(distance);
    }

    @UiHandler(value = "helperListBox")
    public void onHelperChange(ChangeEvent e) {
      HelperType type = HelperType.valueOf(helperListBox.getValue(helperListBox
          .getSelectedIndex()));

      if (type == HelperType.ELEMENT) {
        GQuery helper = $("<div class=\"myHelper\" style=\"width: 150px;height: 150px;\">I'm a custom helper</div>");
        options.setHelper(helper);
      } else {
        options.setHelper(type);
      }
    }

    @UiHandler(value = "handleCheckBox")
    public void onMultiSelectChange(ValueChangeEvent<Boolean> e) {
      if (e.getValue()) {
        options.setHandle("#handle");
      } else {
        options.setHandle(null);
      }
    }

    @UiHandler(value = "opacityBox")
    public void onOpacityChange(ValueChangeEvent<String> e) {
      String opacityString = e.getValue();

      Float opacity;
      if (opacityString == null || opacityString.length() == 0) {
        opacity = null;
      } else {
        try {
          opacity = new Float(e.getValue());
        } catch (NumberFormatException ex) {
          Window.alert("Please specify a correct number for opacity");
          return;
        }
      }
      if (opacity > 1) {
        Window.alert("Opacity must be below than 1.");
        return;
      }
      options.setOpacity(opacity);
    }

    @UiHandler(value = "scrollCheckBox")
    public void onScrollChange(ValueChangeEvent<Boolean> e) {
      boolean scroll = e.getValue();
      options.setScroll(scroll);
      scrollSensivityBox.setEnabled(scroll);
      scrollSpeedBox.setEnabled(scroll);

    }

    @UiHandler(value = "scrollSensivityBox")
    public void onScrollSensitivityChange(ValueChangeEvent<String> e) {
      Integer scrollSensitivity;
      try {
        scrollSensitivity = new Integer(e.getValue());
      } catch (NumberFormatException ex) {
        Window.alert("Please specify a correct number for scrollSensitivity");
        return;
      }
      options.setScrollSensitivity(scrollSensitivity);
    }

    @UiHandler(value = "scrollSpeedBox")
    public void onScrollSpeedChange(ValueChangeEvent<String> e) {
      Integer scrollSpeed;
      try {
        scrollSpeed = new Integer(e.getValue());
      } catch (NumberFormatException ex) {
        Window.alert("Please specify a correct number for scrollSpeed");
        return;
      }
      options.setScrollSpeed(scrollSpeed);
    }

    private void init() {
      int i = 0;
      for (HelperType h : HelperType.values()) {
        helperListBox.addItem(h.name());
        if (h == options.getHelperType()) {
          helperListBox.setSelectedIndex(i);
        }
        i++;
      }

      delayBox.setValue("" + options.getDelay(), false);

      distanceBox.setValue("" + options.getDistance(), false);

      disabledCheckBox.setValue(options.isDisabled(), false);

      handleCheckBox.setValue(false, false);

      axisListBox.addItem(AxisOption.NONE.name());
      axisListBox.addItem(AxisOption.X_AXIS.name());
      axisListBox.addItem(AxisOption.Y_AXIS.name());
      axisListBox.setSelectedIndex(0);

      if (options.getOpacity() != null) {
        opacityBox.setValue("" + options.getOpacity());
      }

      scrollCheckBox.setValue(options.isScroll());
      scrollSensivityBox.setValue("" + options.getScrollSensitivity());
      scrollSpeedBox.setValue("" + options.getScrollSpeed());

      i = 0;
      for (Cursor c : Cursor.values()) {
        cursorListBox.addItem(c.name());
        if (c == options.getCursor()) {
          cursorListBox.setSelectedIndex(i);
          i++;
        }
      }

    }

  }

  public void onModuleLoad() {

    DraggableOptions o = new DraggableOptions();
    $("#draggable").as(Draggable).draggable(o);

    RootPanel.get("draggable-options").add(new DraggableOptionsPanel(o));

  }
}
