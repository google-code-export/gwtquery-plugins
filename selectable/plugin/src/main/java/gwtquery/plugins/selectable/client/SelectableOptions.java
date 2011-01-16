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
package gwtquery.plugins.selectable.client;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.MouseOptions;


/**
 * Object containing all options for the Selectable plugin
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com, @jdramaix)
 * 
 */
public class SelectableOptions extends MouseOptions {

  public static enum Tolerance {
    TOUCH, FIT;
  }

  /**
   * Disables (true) or enables (false) the selectable. Can be set when
   * initialising (first creating) the selectable. Default : 'false'
   */
  private boolean disabled;

  /**
   * This determine whether the selection can select many element or only one.
   */
  private boolean multiSelect;

  /**
   * This determines whether to refresh (recalculate) the position and size of
   * each selectee at the beginning of each select operation. If you have many
   * many items, you may want to set this to false and call the refresh method
   * manually. Default : 'true'
   */
  private boolean autoRefresh;

  /**
   * Selector used to append the 'lasso' div Default : 'body'
   */
  private String appendTo; // maybe GQuery instead

  /**
   * The matching child elements will be made selectees (able to be selected)
   * Default : '*'
   */
  private String filter;

  /**
   * Tolerance to select an selectee. Default : TOUCH
   */
  private Tolerance tolerance;

  /**
   * This callback function is called at the end of the select operation, on
   * each element added to the selection.
   */
  private Function onSelected;

  /**
   * This callback function is called during the select operation, on each
   * element added to the selection.
   */
  private Function onSelecting;

  /**
   * This callback function is called at the end of the select operation, on
   * each element added to the selection.
   */
  private Function onUnselected;

  /**
   * This callback function is called during the select operation, on each
   * element removed from the selection.
   */
  private Function onUnselecting;

  /**
   * This callback function is called at the end of the select operation, on
   * each element removed from the selection.
   */
  private Function onStartSelection;

  /**
   * This callback function is called at the end of the select operation.
   */
  private Function onStopSelection;
  
  private boolean lassoOnMetaKey;

  public SelectableOptions() {
    initDefault();
  }

  public String getAppendTo() {
    return appendTo;
  }

  public String getFilter() {
    return filter;
  }

  public Function getOnSelected() {
    return onSelected;
  }

  public Function getOnSelecting() {
    return onSelecting;
  }

  public Function getOnStartSelection() {
    return onStartSelection;
  }

  public Function getOnStopSelection() {
    return onStopSelection;
  }

  public Function getOnUnselected() {
    return onUnselected;
  }

  public Function getOnUnselecting() {
    return onUnselecting;
  }

  public Tolerance getTolerance() {
    return tolerance;
  }

  public boolean isAutoRefresh() {
    return autoRefresh;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public boolean isMultiSelect() {
    return multiSelect;
  }

  public void setAppendTo(String appendTo) {
    this.appendTo = appendTo;
  }

  public void setAutoRefresh(boolean autoRefresh) {
    this.autoRefresh = autoRefresh;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  public void setMultiSelect(boolean multiSelect) {
    this.multiSelect = multiSelect;
  }

  public void setOnSelected(Function onSelected) {
    this.onSelected = onSelected;
  }

  public void setOnSelecting(Function onSelecting) {
    this.onSelecting = onSelecting;
  }

  public void setOnStartSelection(Function onStartSelection) {
    this.onStartSelection = onStartSelection;
  }

  public void setOnStopSelection(Function onStopSelection) {
    this.onStopSelection = onStopSelection;
  }

  public void setOnUnselected(Function onUnselected) {
    this.onUnselected = onUnselected;
  }

  public void setOnUnselecting(Function onUnselecting) {
    this.onUnselecting = onUnselecting;
  }

  public void setTolerance(Tolerance tolerance) {
    this.tolerance = tolerance;
  }
  
  public boolean isLassoOnMetaKey() {
    return lassoOnMetaKey;
  }
  
  public void setLassoOnMetaKey(boolean lassoOnMetaKey) {
    this.lassoOnMetaKey = lassoOnMetaKey;
  }

  protected void initDefault() {
    disabled = false;
    autoRefresh = true;
    filter = "*";
    tolerance = Tolerance.TOUCH;
    appendTo = GQuery.body.getTagName();
    multiSelect = true;
    lassoOnMetaKey = false;
    super.initDefault();
  }

}
