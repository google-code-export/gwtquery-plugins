package gwtquery.plugins.enhance.client.slider;

import static com.google.gwt.query.client.GQuery.$;
import gwtquery.plugins.enhance.client.slider.SliderBar.LabelFormatter;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.widgets.WidgetFactory;
import com.google.gwt.query.client.plugins.widgets.WidgetsUtils;

/**
 * Factory used to create a {@link SliderBar} widget 
 */
public class SliderFactory implements WidgetFactory<SliderBar> {
  
  double min, max, steps;
  
  LabelFormatter formatInt = new LabelFormatter() {
    public String formatLabel(SliderBar slider, double value) {
      return "" + (int)value;
    }
  };
  
  public SliderFactory(double min, double max, int steps) {
    assert max >= min;
    this.min = min;
    this.max = max;
    this.steps = steps;
  }
  
  public SliderBar create(Element e) {
    
    String val = null;
    String name = null;
    if ($(e).filter("input[type='text']").get(0) != null) {
      val = $(e).attr("value");
      name = $(e).attr("name");
    } else if (WidgetsUtils.matchesTags(e, "div", "span")) {
      val = e.getInnerText();
    }
    
    if (val != null) {
      double value = Double.parseDouble(val.replaceAll("^.*(\\d+\\.?\\d+).*$", "$1"));
      final SliderBar slider = new SliderBar(min, max);
      slider.setNumLabels((int)steps);
      slider.setStepSize((max - min) / steps);
      slider.setCurrentValue(value);
      if ((min + max) % 1 == 0 && (max - min) >= steps) {
        slider.setLabelFormatter(formatInt);
      }
      WidgetsUtils.replaceOrAppend(e, slider);

      if (name != null) {
        final GQuery g = $("input", slider); 
        g.attr("name", name); 
        slider.addValueChangeHandler(new ValueChangeHandler<Double>() {
          public void onValueChange(ValueChangeEvent<Double> value) {
            g.val(value.getValue() + " "); 
          }
        });
      }
      return slider;
    }
    return null;
  }
}