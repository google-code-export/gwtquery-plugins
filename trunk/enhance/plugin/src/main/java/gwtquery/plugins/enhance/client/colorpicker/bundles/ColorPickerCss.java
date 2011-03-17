package gwtquery.plugins.enhance.client.colorpicker.bundles;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface ColorPickerCss extends ClientBundle {
    public static final ColorPickerCss INSTANCE =  GWT.create(ColorPickerCss.class);

    @Source("colorpicker.css")
    public CssResource css();
    
    
}