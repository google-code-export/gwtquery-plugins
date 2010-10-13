package gwtquery.plugins.commonui.client;

import static com.google.gwt.query.client.GQuery.document;


/**
 * This object allows you to have a full copy of the original Event and
 * implements some useful method of the jQuery event model. This is also useful
 * in Internet Explorer because it use the same javascript object to fire
 * MouseDownEvent, MouseMoveEvent or MouseStopEvent on the same element. So, we
 * cannot keep a copy of the MouseDownEvent during a dragginf for example. Now
 * we can !
 * 
 * TOBEFIXED : the method preventDefault() must be called directly on the original event

 * 
 * @author jdramaix
 * 
 */
public class Event extends com.google.gwt.user.client.Event {

	public static Event create(com.google.gwt.user.client.Event originalEvent) {
		Event gQueryEvent = createObject().cast();
		copy(originalEvent, gQueryEvent);		
		return gQueryEvent;
	}

	private static native void copy(com.google.gwt.user.client.Event originalEvent,
			Event gQueryEvent) /*-{
		for ( var field in originalEvent  ) {
			gQueryEvent[field] = originalEvent[field];
		}		
		gQueryEvent.originalEvent =	originalEvent;
		
	}-*/;


	protected Event() {
	}
	
	public final native com.google.gwt.user.client.Event getOriginalEvent()/*-{
		return this.originalEvent;
	}-*/;
	
	

	/**
	 * The mouse position relative to the left edge of the document
	 * 
	 * @param mouseEvent
	 * @return the mouse x-position in the page
	 */
	public final int pageX() {
		return getClientX() + document.getScrollLeft();
	}

	/**
	 * The mouse position relative to the top edge of the document. 
	 * 
	 * @param mouseEvent
	 * @return the mouse y-position in the page
	 */
	public final int pageY() {
		return getClientY() + document.getScrollTop();
	}
	
}
