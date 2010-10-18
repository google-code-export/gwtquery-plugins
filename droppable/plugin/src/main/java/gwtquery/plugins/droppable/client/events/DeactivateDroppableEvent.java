package gwtquery.plugins.droppable.client.events;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;

public class DeactivateDroppableEvent extends AbstractDroppableEvent<DeactivateDroppableEvent.DeactivateDroppableEventHandler> {
	
	public interface DeactivateDroppableEventHandler extends EventHandler {
		public void onDeactivateDroppable(DeactivateDroppableEvent event);
	}

	public static Type<DeactivateDroppableEventHandler> TYPE = new Type<DeactivateDroppableEventHandler>();

	public DeactivateDroppableEvent() {	
	}
	
	public DeactivateDroppableEvent(Element droppable, Element draggable) {
		super(droppable, draggable);
	}


	@Override
	public Type<DeactivateDroppableEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DeactivateDroppableEventHandler handler) {
		handler.onDeactivateDroppable(this);
	}
}
