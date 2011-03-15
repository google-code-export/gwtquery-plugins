package gwtquery.plugins.enhance.client.slider.bundles;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 * An {@link ImageBundle} that provides images for {@link SliderBar}.
 */
public interface SliderBarImages extends ImageBundle {
  /**
   * An image used for the sliding knob.
   * 
   * @return a prototype of this image
   */
  AbstractImagePrototype slider();

  /**
   * An image used for the sliding knob.
   * 
   * @return a prototype of this image
   */
  AbstractImagePrototype sliderDisabled();

  /**
   * An image used for the sliding knob while sliding.
   * 
   * @return a prototype of this image
   */
  AbstractImagePrototype sliderSliding();
}