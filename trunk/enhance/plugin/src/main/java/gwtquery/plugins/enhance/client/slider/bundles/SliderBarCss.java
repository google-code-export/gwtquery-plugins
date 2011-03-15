package gwtquery.plugins.enhance.client.slider.bundles;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface SliderBarCss extends ClientBundle {
    public static final SliderBarCss INSTANCE =  GWT.create(SliderBarCss.class);

    @Source("slider.css")
    public CssResource css();
}