/*
 * Copyright 2011, The gwtquery team.
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
package gwtquery.plugins.enhance.client;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.enhance.client.Enhance.Enhance;
import gwtquery.plugins.enhance.client.gwt.ListBoxWidgetFactory.ListBoxOptions;
import gwtquery.plugins.enhance.client.gwt.SuggestBoxWidgetFactory.SuggestBoxOptions;
import gwtquery.plugins.enhance.client.gwt.TabPanelWidgetFactory.TabPanelOptions;

import java.util.Iterator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.plugins.widgets.WidgetInitializer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.GqUi;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class EnhanceGwtSample implements EntryPoint {

  public static class MyButtonInitializer implements WidgetInitializer<Button> {

    public void initialize(Button button, Element e) {

      final String tag = e.getTagName();
      button.addClickHandler(new ClickHandler() {

        public void onClick(ClickEvent event) {
          Label l = new Label("You click on a GWT Button create from a " + tag);
          PopupPanel panel = new PopupPanel(true, true);
          panel.setGlassEnabled(true);
          panel.add(l);
          panel.center();

        }
      });

    }
  }

  public void onModuleLoad() {

    $("#disclosure").as(Enhance).disclosurePanel();

    $(".inputText").as(Enhance).textBox();
    $(".inputPsw").as(Enhance).passwordBox();
    $(".textArea").as(Enhance).textArea();

    $(".btn").as(Enhance).button(new MyButtonInitializer());

    $("#tabs").as(Enhance).tabPanel(
        new TabPanelOptions("div.tab", "h3.tabTitle"));
    //$(".editable").as(Enhance).richtext().widget();

    $(".date").as(Enhance).datebox();

    $(".list").as(Enhance).listBox();
    $("#multiSelect").as(Enhance).listBox(new ListBoxOptions(".item", true));
    $("#suggestBox1").as(Enhance).suggestBox(new SuggestBoxOptions("div"));
    $("#suggestBox2").as(Enhance).suggestBox(new SuggestBoxOptions("li"));
    $(".stack").as(Enhance).stackPanel();
    $(".checkBox").as(Enhance).checkBox();
    $(".radio").as(Enhance).radioButton();
    
    $("body > div").as(Enhance).decoratorPanel();

    GWT.log("Found " + $(".btn").widgets().size() + " buttons widget");

    displayWidgetHierarchy();
  }

  private void displayWidgetHierarchy() {
    DisclosurePanel disclosurePanel = $("#disclosure").widget();
    Tree widgetHierarchy = new Tree();
    TreeItem rootItem = new TreeItem(disclosurePanel.getClass().getName());
    widgetHierarchy.addItem(rootItem);
    widgetHierarchy.setAnimationEnabled(true);
    addChildren(rootItem, disclosurePanel);

    RootPanel.get().add(new Label("Widgets hierarchy :"));
    RootPanel.get().add(widgetHierarchy);
  }

  private void addChildren(TreeItem item, Widget w) {
    Iterator<Widget> children = GqUi.getChildren(w);
    if (children != null) {
      while (children.hasNext()) {
        Widget child = children.next();
        TreeItem subItem = new TreeItem(child.getClass().getName());
        item.addItem(subItem);

        addChildren(subItem, child);

      }
    }

  }

}
