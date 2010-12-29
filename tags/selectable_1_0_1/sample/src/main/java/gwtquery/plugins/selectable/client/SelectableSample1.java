/*
 * Copyright 2010 Google Inc.
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
package gwtquery.plugins.selectable.client;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.selectable.client.Selectable.Selectable;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;

public class SelectableSample1 implements EntryPoint {

  public void onModuleLoad() {

    SelectableOptions options = initOptions();

    $("#selectable").as(Selectable).selectable(options);

  }

  private SelectableOptions initOptions() {
    SelectableOptions options = new SelectableOptions();
    options.setOnSelected(new Function() {
      @Override
      public void f(Element e) {
        fillSpan("selected", e.getInnerHTML());
      }
    });
    options.setOnSelecting(new Function() {
      @Override
      public void f(Element e) {
        fillSpan("selecting", e.getInnerHTML());
      }
    });
    options.setOnUnselected(new Function() {
      @Override
      public void f(Element e) {
        fillSpan("unselected", e.getInnerHTML());
      }
    });
    options.setOnUnselecting(new Function() {
      @Override
      public void f(Element e) {
        fillSpan("unselecting", e.getInnerHTML());
      }
    });
    options.setOnStartSelection(new Function() {
      @Override
      public void f(Element e) {
        $("#message").html("Selection started");
        $("#selecting, #unselecting, #selected, #unselected").empty();
      }
    });
    options.setOnStopSelection(new Function() {
      @Override
      public void f(Element e) {
        final StringBuilder selectedItems = new StringBuilder();
        $(".ui-selected", e).each(new Function() {
          public void f(Element e) {
            if (selectedItems.length() == 0) {
              selectedItems.append(e.getInnerHTML());
            } else {
              selectedItems.append(", ").append(e.getInnerHTML());
            }
          };
        });

        $("#message").html("Selection ended.");
        $("#totalSelected").html(selectedItems.toString());
        $("#selecting, #unselecting").empty();
      }
    });

    return options;
  }

  private void fillSpan(String id, String contentToAppend) {
    String content = $("#" + id).html();
    if (content == null || content.length() == 0) {
      content = contentToAppend;
    } else {
      content += ", ";
      content += contentToAppend;
    }
    $("#" + id).html(content);
  }

}
