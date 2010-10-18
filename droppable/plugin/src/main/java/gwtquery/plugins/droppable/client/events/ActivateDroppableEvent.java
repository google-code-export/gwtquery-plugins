package gwtquery.plugins.droppable.client.events;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;

public class ActivateDroppableEvent extends AbstractDroppableEvent<ActivateDroppableEvent.DropActivateEventHandler> {
	
	public interface DropActivateEventHandler extends EventHandler {
		public void onActivateDroppable(ActivateDroppableEvent event);
	}

	public static Type<DropActivateEventHandler> TYPE = new Type<DropActivateEventHandler>();

	public ActivateDroppableEvent() {	
	}
	
	public ActivateDroppableEvent(Element droppable, Element draggable) {
		super(droppable, draggable);
	}


	@Override
	public Type<DropActivateEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DropActivateEventHandler handler) {
		handler.onActivateDroppable(this);
	}
}
