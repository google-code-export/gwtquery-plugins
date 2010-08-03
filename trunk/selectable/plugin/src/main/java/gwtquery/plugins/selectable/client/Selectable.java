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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.query.client.Plugin;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.selectable.client.SelectableOptions.Tolerance;

/**
 * Class implementing the JQuery-ui Selectable plugin.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class Selectable extends GQuery {

  private class SelectableItem {

    private Element element;
    private GQuery $element;
    private int left;
    private int top;
    private int right;
    private int bottom;
    private boolean startSelected = false;
    boolean selected = false;
    boolean selecting = false;
    boolean unselecting = false;

    public SelectableItem(Element e) {
      element = e;
      $element = $(e);
    }

    public Element getElement() {
      return element;
    }

    public GQuery $element() {
      return $element;
    }

    public int getLeft() {
      return left;
    }

    public void setLeft(int left) {
      this.left = left;
    }

    public int getTop() {
      return top;
    }

    public void setTop(int top) {
      this.top = top;
    }

    public int getRight() {
      return right;
    }

    public void setRight(int right) {
      this.right = right;
    }

    public int getBottom() {
      return bottom;
    }

    public void setBottom(int bottom) {
      this.bottom = bottom;
    }

    public boolean isStartSelected() {
      return startSelected;
    }

    public void setStartSelected(boolean startSelected) {
      this.startSelected = startSelected;
    }

    public boolean isSelected() {
      return selected;
    }

    public void setSelected(boolean selected) {
      this.selected = selected;
    }

    public boolean isSelecting() {
      return selecting;
    }

    public void setSelecting(boolean selecting) {
      this.selecting = selecting;
    }

    public boolean isUnselecting() {
      return unselecting;
    }

    public void setUnselecting(boolean unselecting) {
      this.unselecting = unselecting;
    }

    public void unselecting() {
      $element.removeClass(CssClassNames.UI_SELECTED);
      $element.addClass(CssClassNames.UI_UNSELECTING);
      setSelected(false);
      setSelecting(false);
      setUnselecting(true);
    }

    public void selecting() {
      $element.removeClass(CssClassNames.UI_UNSELECTING);
      $element.addClass(CssClassNames.UI_SELECTING);
      setSelected(false);
      setSelecting(true);
      setUnselecting(false);
    }

    public void unselect() {
      $element.removeClass(CssClassNames.UI_UNSELECTING);
      setSelected(false);
      setUnselecting(false);
      setSelecting(false);
    }

    public void select() {
      $element.removeClass(CssClassNames.UI_SELECTING);
      $element.addClass(CssClassNames.UI_SELECTED);
      setSelected(true);
      setSelecting(false);
      setUnselecting(false);

    }

  }

  /**
   * Class handling the lasso helper used for the selection.
   * 
   */
  private static class Lasso {

    private Element element;
    private int startingLeft = -1;
    private int startingTop = -1;

    public Lasso() {
      element = Document.get().createDivElement();
      element.addClassName("ui-selectable-lasso");
    }

    public void show(int leftPosition, int topPosition, String appendToSelector) {
      this.startingLeft = leftPosition;
      this.startingTop = topPosition;
      // Todo use options ! not append directly to the body
      $(appendToSelector).append(element);
      element.getStyle().setZIndex(100);
      element.getStyle().setPosition(Position.ABSOLUTE);
      element.getStyle().setLeft(leftPosition, Unit.PX);
      element.getStyle().setTop(topPosition, Unit.PX);
      element.getStyle().setWidth(0, Unit.PX);
      element.getStyle().setHeight(0, Unit.PX);
    }

    public void draw(int x, int y) {

      int left = startingLeft;
      int top = startingTop;

      if (left > x) {
        int tmp = x;
        x = left;
        left = tmp;
      }

      if (top > y) {
        int tmp = y;
        y = top;
        top = tmp;
      }

      element.getStyle().setLeft(left, Unit.PX);
      element.getStyle().setTop(top, Unit.PX);
      element.getStyle().setWidth(x - left, Unit.PX);
      element.getStyle().setHeight(y - top, Unit.PX);

    }

    public void hide() {
      element.removeFromParent();
    }
  }

  /**
   * Interface containing all css classes used in this plug-in
   * 
   */
  private static interface CssClassNames {
    String UI_SELECTABLE = "ui-selectable";
    String UI_SELECTED = "ui-selected";
    String UI_SELECTING = "ui-selecting";
    String UI_UNSELECTING = "ui-unselecting";
    String UI_UNSELECTEE = "ui-unselectee";
  }

  public static Class<Selectable> Selectable = Selectable.class;
  private static String SELECTABLE_ITEM_KEY = "selectable-item";
  private static String SELECTEES_KEY = "selectees";
  private static final String SELECTABLE_KEY = "selectable";
  private static final String CURRENT_FILTER_KEY = "currentFilter";

  static {
    GQuery.registerPlugin(Selectable.class, new Plugin<Selectable>() {
      public Selectable init(GQuery gq) {
        Selectable s = new Selectable(gq);
        return s;
      }
    });
  }

  private Lasso lasso;

  // TODO maybe put that on selectable element
  private int[] opos;

  private SelectableOptions options;
  
  private HandlerManager eventBus;

  public Selectable(GQuery gq) {
    super(gq);

  }

  public Selectable(Element element) {
    super(element);
  }

  public Selectable(JSArray elements) {
    super(elements);
  }

  public Selectable(NodeList<Element> list) {
    super(list);
  }
  
  /**
   * Give the possibility of each children of each element to be selectable.
   * 
   * @return
   */
  public Selectable selectable() {
    return selectable(new SelectableOptions(), null);
  }
  
  /**
   * Give the possibility of each children of each element to be selectable.
   * 
   * @return
   */
  public Selectable selectable(SelectableOptions options) {
    return selectable(options, null);
  }
  
  /**
   * Give the possibility of each children of each element to be selectable.
   * 
   * @return
   */
  public Selectable selectable(HandlerManager eventBus) {
    return selectable(new SelectableOptions(), eventBus);
  }

  /**
   * Give the possibility of each children (depending of the filter option) of
   * each element to be selectable.
   * 
   * @return
   */
  public Selectable selectable(SelectableOptions options, HandlerManager eventBus) {
    this.options = options;
    this.eventBus = eventBus;
    lasso = new Lasso();

    for (Element e : elements()) {
      e.addClassName(CssClassNames.UI_SELECTABLE);
      $(e).data(CURRENT_FILTER_KEY, options.getFilter());
      refreshSelectees(e);

      initMouseHandler(e);
    }

    return this;
  }
  

  /**
   * Remove "selectable" feature of elements
   * 
   * @return
   */
  public Selectable unSelectable() {
    for (Element e : elements()) {
      GQuery $this = $(e);
      GQuery selectees = $this.data(SELECTEES_KEY, GQuery.class);
      if (selectees != null) {
        selectees.removeClass(CssClassNames.UI_UNSELECTEE).removeData(
            SELECTABLE_ITEM_KEY);
      }
      $this.removeClass(CssClassNames.UI_SELECTABLE, "ui-selectable-disabled");
      $this.removeData(SELECTEES_KEY).removeData(SELECTABLE_KEY);
      // TODO use a namespace for the handler
      $this.unbind(Event.ONMOUSEDOWN);

    }
    return this;
  }

  protected void onSelection(Element selectable, Event event) {

    if (options.isDisabled()) {
      return;
    }

    lasso.draw(getPageX(event), getPageY(event));

    int x1 = lasso.element.getAbsoluteLeft(), y1 = lasso.element
        .getAbsoluteTop();
    int x2 = lasso.element.getAbsoluteRight(), y2 = lasso.element
        .getAbsoluteBottom();

    GQuery selectees = $(selectable).data(SELECTEES_KEY, GQuery.class);

    for (Element e : selectees.elements()) {
      SelectableItem si = $(e).data(SELECTABLE_ITEM_KEY, SelectableItem.class);
      if (si == null || si.getElement() == selectable) {
        continue;
      }

      boolean hit = false;
      if (Tolerance.TOUCH == options.getTolerance()) {
        hit = !(si.getLeft() > x2 || si.getRight() < x1 
            || si.getTop() > y2 || si.getBottom() < y1);
      } else if (Tolerance.FIT == options.getTolerance()) {
        hit = si.getLeft() > x1 && si.getRight() < x2 && si.getTop() > y1
            && si.getBottom() < y2;
      }

      if (hit) {
        if (si.isSelected()) {
          si.$element().removeClass(CssClassNames.UI_SELECTED);
          si.setSelected(false);
        }
        if (si.isUnselecting()) {
          si.$element().removeClass(CssClassNames.UI_UNSELECTING);
          si.setUnselecting(false);
        }
        if (!si.isSelecting()) {
          si.selecting();
          trigger(null, options.getOnSelecting(), e);
        }
      } else {
        if (si.isSelecting()) {
          if ((event.getMetaKey() || event.getCtrlKey())
              && si.isStartSelected()) {
            // keep previously selected element
            si.select();
          } else {
            si.$element().removeClass(CssClassNames.UI_SELECTING);
            si.setSelecting(false);
            if (si.isStartSelected()) { // previously selected ->
              // flag it as unselecting
              si.$element().addClass(CssClassNames.UI_UNSELECTING);
              si.setUnselecting(true);
            }
            trigger(null, options.getOnUnselecting(), e);
          }
        }

        if (si.isSelected() && !(event.getMetaKey() || event.getCtrlKey())
            && !si.isStartSelected()) {
          si.unselecting();
          trigger(null, options.getOnUnselecting(), e);

        }
      }

    }

  }

  protected void onSelectionStart(Element selectable, Event event) {
    bindMouseEvent(selectable);

    if (options.isDisabled()) {
      return;
    }

    trigger(null, options.getOnStartSelection(), selectable);

    opos = new int[] { getPageX(event), getPageY(event) };
    lasso.show(opos[0], opos[1], options.getAppendTo());

    if (options.isAutoRefresh()) {
      refreshSelectees(selectable);
    }

    GQuery selectedSelectees = $(selectable).data(SELECTEES_KEY, GQuery.class)
        .filter("." + CssClassNames.UI_SELECTED);

    for (Element e : selectedSelectees.elements()) {
      SelectableItem si = $(e).data(SELECTABLE_ITEM_KEY, SelectableItem.class);
      si.setStartSelected(true);
      // if not meta-key or ctrl-keypressed, unselect elements
      if (!(event.getMetaKey() || event.getCtrlKey())) {
        si.unselecting();
        trigger(null, options.getOnUnselecting(), e);
      }
    }

    // remove old selected (case where filter was change before this
    // selection) if no meta-key was pressed
    if (!(event.getMetaKey() || event.getCtrlKey())) {
      GQuery oldSelected = $('.' + CssClassNames.UI_SELECTED, selectable);
      for (Element e : oldSelected.elements()) {
        SelectableItem si = $(e)
            .data(SELECTABLE_ITEM_KEY, SelectableItem.class);
        if (si == null) { // old selectee
          e.removeClassName(CssClassNames.UI_SELECTED);
          trigger(null, options.getOnUnselected(), e);
        }
      }
    }

    // retrieve the element that was the target of the event and its parents
    // (maybe one of the parent is the selectee)
    GQuery eventTarget = $((Element) event.getEventTarget().cast());
    GQuery parentsAndSelf = eventTarget.parents().add(eventTarget);
    for (Element e : parentsAndSelf.elements()) {
      SelectableItem si = $(e).data(SELECTABLE_ITEM_KEY, SelectableItem.class);
      if (si != null) {
        // ok a selectable-item exists on the element...
        // it is a selectable element
        boolean doSelection = !(event.getMetaKey() || event.getCtrlKey())
            || !si.isSelected();
        if (doSelection) {
          si.selecting();
          trigger(null, options.getOnSelecting(), e);
        } else {
          si.unselecting();
          trigger(null, options.getOnUnselecting(), e);
        }

      }
    }
  }

  protected void onSelectionStop(Element selectable, Event event) {
    unbindMouseEvent();

    GQuery unselecting = $('.' + CssClassNames.UI_UNSELECTING, selectable);
    for (Element e : unselecting.elements()) {
      SelectableItem si = $(e).data(SELECTABLE_ITEM_KEY, SelectableItem.class);
      si.unselect();
      si.setStartSelected(false);
      trigger(new UnselectedEvent(e), options.getOnUnselected(), e);
    }

    GQuery selecting = $('.' + CssClassNames.UI_SELECTING, selectable);
    for (Element e : selecting.elements()) {
      SelectableItem si = $(e).data(SELECTABLE_ITEM_KEY, SelectableItem.class);
      si.select();
      si.setStartSelected(true);
      trigger(new SelectedEvent(e), options.getOnSelected(), e);
    }

    trigger(null, options.getOnStopSelection(), selectable);

    lasso.hide();

  }

  private void bindMouseEvent(final Element selectable) {
    $(document).mousemove(new Function() {
      @Override
      public boolean f(Event e) {
        onSelection(selectable, e);
        return false;
      }
    });
    $(document).mouseup(new Function() {
      @Override
      public boolean f(Event e) {
        onSelectionStop(selectable, e);
        return false;
      }
    });

  }

  private int getPageX(Event mouseEvent) {
    int pageX = mouseEvent.getClientX() + document.getScrollLeft();
    return pageX;
  }

  private int getPageY(Event mouseEvent) {
    int pageY = mouseEvent.getClientY() + document.getScrollTop();
    return pageY;
  }

  private void initMouseHandler(final Element e) {
    $(e).bind(Event.ONMOUSEDOWN, null, new Function() {
      @Override
      public boolean f(Event event) {
        onSelectionStart(e, event);
        return false;
      }
    });

  }

  private GQuery refreshSelectees(Element e) {
    GQuery $e = $(e);

    String currentOptionsFilter = $e.data(CURRENT_FILTER_KEY, String.class);
    String newOptionsFilter = options.getFilter();

    if ((currentOptionsFilter == null && newOptionsFilter != null)
        || !currentOptionsFilter.equals(newOptionsFilter)) {
      // filter change since last selection... clean old selectee
      GQuery oldSelectees = $e.data(SELECTEES_KEY, GQuery.class);
      for (Element element : oldSelectees.elements()) {
        $(element).removeData(null).removeClass(CssClassNames.UI_UNSELECTEE);
      }
      $e.data(CURRENT_FILTER_KEY, newOptionsFilter);
    }

    GQuery selectees = $(newOptionsFilter, e);
    for (Element selectee : selectees.elements()) {
      SelectableItem si = new SelectableItem(selectee);
      Offset pos = si.$element().offset();
      si.setLeft(pos.left);
      si.setTop(pos.top);
      si.setRight(pos.left + selectee.getOffsetWidth());
      si.setBottom(pos.top + selectee.getOffsetHeight());
      si.setStartSelected(false);
      si.setSelected(si.$element().hasClass("ui-selected"));
      si.setSelecting(si.$element().hasClass("ui-selecting"));
      si.setUnselecting(si.$element().hasClass("ui-unselecting"));

      si.$element().data(SELECTABLE_ITEM_KEY, si);
      si.$element().addClass(CssClassNames.UI_UNSELECTEE);
    }
    $e.data(SELECTEES_KEY, selectees);
    return selectees;
  }

  private void trigger(GwtEvent<?> e, Function callback, Element element) {
    if (eventBus != null){
      eventBus.fireEvent(e);
    }
    if (callback != null) {
      callback.f(element);
    }
  }

  private void unbindMouseEvent() {
    $(document).unbind(Event.ONMOUSEUP);
    $(document).unbind(Event.ONMOUSEMOVE);
  }

}
