package gwtquery.plugins.droppable.client.events;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;

public class DropDeactivateEvent extends AbstractDroppableEvent<DropDeactivateEvent.DropDeactivateEventHandler> {
	
	public interface DropDeactivateEventHandler extends EventHandler {
		public void onDropDeactivate(DropDeactivateEvent event);
	}

	public static Type<DropDeactivateEventHandler> TYPE = new Type<DropDeactivateEventHandler>();

	public DropDeactivateEvent() {	
	}
	
	public DropDeactivateEvent(Element droppable, Element draggable) {
		super(droppable, draggable);
	}


	@Override
	public Type<DropDeactivateEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DropDeactivateEventHandler handler) {
		handler.onDropDeactivate(this);
	}
}
