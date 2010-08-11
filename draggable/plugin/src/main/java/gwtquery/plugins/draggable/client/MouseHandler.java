package gwtquery.plugins.draggable.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;

/**
 * Base class for all plug-in who need to handle some mouse interactions.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public abstract class MouseHandler extends GQuery {
  
  private class DelayTimer extends Timer{
    
    @Override
    public void run() {
      mouseDelayMet = true;  
    }
  }

  /*
   * 
   * TODO : see which fields must be associated to the element !!!!
   * 
   * 
   */
	private boolean preventClickEvent = false;
	private boolean mouseStarted = false;
	private boolean mouseDelayMet = false;
	private Event mouseDownEvent;
	private MouseOptions options;

	public MouseHandler(GQuery gq) {
		super(gq);
	}

	public MouseHandler(Element element) {
		super(element);
	}

	public MouseHandler(JSArray elements) {
		super(elements);
	}

	public MouseHandler(NodeList<Element> list) {
		super(list);
	}

	protected void initMouseHandler(MouseOptions options) {
		this.options = options;

		// TODO add namespace when we bind events
		for (final Element e : elements()) {

			$(e).bind(Event.ONMOUSEDOWN, null, new Function() {
				@Override
				public boolean f(Event event) {
					return mouseDown(e, event);

				}
			}).bind(Event.ONCLICK, null, new Function() {
				@Override
				public boolean f(Event event) {
					if (preventClickEvent) {
						preventClickEvent = false;
						event.stopPropagation();
						return false;
					}
					return true;
				}
			});
		}

	}

	protected void destroyMouseHandler() {
		// TODO add namespace when we bind events
		unbind(Event.ONMOUSEDOWN | Event.ONCLICK);
	}
	
	protected abstract String getPluginName();

	/**
	 * Override this method if you want to decide to stop or continue the
	 * plugin.
	 * 
	 * @param elemen
	 * @param event
	 * @return
	 */
	protected boolean mouseCapture(Element draggable, Event event) {
		return true;
	}

	protected abstract boolean mouseDrag(Element draggable, Event event);

	protected abstract boolean mouseStart(Element draggable, Event event);

	protected abstract boolean mouseStop(Element draggable, Event event);

	
	private boolean delayAndDistanceConditionsMet() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean mouseDown(Element element, Event mouseDownEvent) {
		
		if (mouseStarted) { // case where we missed a mouseup
			mouseUp(element, mouseDownEvent);
		}

		//calculate all interesting variables
		setup(mouseDownEvent);
		

		if (notHandleMouseDown(element, mouseDownEvent)) {
			return true;
		}
		
		if (!mouseDelayMet){
			Timer timer = new DelayTimer();
			timer.schedule(options.getDelay());
		}
		
		
		if (delayAndDistanceConditionsMet()){
			mouseStarted = mouseStart(element, mouseDownEvent);
			if (!mouseStarted){
			  mouseDownEvent.preventDefault();
			  return true;
			}
		}
		
		bindOtherMouseEvent(element);

		return true;
	}

	

	private void bindOtherMouseEvent(final Element element) {
	  $(document).mousemove(new Function() {
      @Override
      public boolean f(Event e) {
        mouseDrag(element, e);
        return false;
      }
    }).mouseup(new Function() {
      @Override
      public boolean f(Event e) {
        mouseStop(element, e);
        return false;
      }
    }); 
  }

  private boolean mouseUp(Element element, Event mouseDownEvent) {

		return false;

	}

	private boolean notHandleMouseDown(Element element, Event mouseDownEvent) {
		boolean isNotBoutonLeft = mouseDownEvent.getButton() != NativeEvent.BUTTON_LEFT;
		boolean isElementCancel = $(mouseDownEvent).parents().add(
				$(mouseDownEvent)).filter(options.getCancel()).length() > 1;

		return isNotBoutonLeft || isElementCancel || !mouseCapture(element, mouseDownEvent);

	}
	
	private void setup(Event mouseDownEvent) {
		this.mouseDownEvent = mouseDownEvent;
		this.mouseDelayMet = options.getDelay() > 0;
		
	}

}
