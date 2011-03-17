package gwtquery.plugins.enhance.client.multiselect.bundles;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface MultiSelectCss extends ClientBundle {
    public static final MultiSelectCss INSTANCE =  GWT.create(MultiSelectCss.class);

    @Source("multiselect.css")
    public CssResource css();
    
    
}