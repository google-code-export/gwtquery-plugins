package gwtquery.plugins.droppable.client.events;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;

public class ActivateDroppableEvent extends AbstractDroppableEvent<ActivateDroppableEvent.ActivateDroppableEventHandler> {
	
	public interface ActivateDroppableEventHandler extends EventHandler {
		public void onActivateDroppable(ActivateDroppableEvent event);
	}

	public static Type<ActivateDroppableEventHandler> TYPE = new Type<ActivateDroppableEventHandler>();

	public ActivateDroppableEvent() {	
	}
	
	public ActivateDroppableEvent(Element droppable, Element draggable) {
		super(droppable, draggable);
	}


	@Override
	public Type<ActivateDroppableEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ActivateDroppableEventHandler handler) {
		handler.onActivateDroppable(this);
	}
}
