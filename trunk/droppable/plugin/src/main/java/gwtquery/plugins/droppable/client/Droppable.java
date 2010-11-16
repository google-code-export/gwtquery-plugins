/*
 * Copyright 2010 The gwtquery plugins team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package gwtquery.plugins.droppable.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.JSArray;
import com.google.gwt.query.client.Plugin;

import gwtquery.plugins.commonui.client.GQueryUi;
import gwtquery.plugins.draggable.client.DragAndDropManager;

/**
 * Droppable plugin for GwtQuery
 */
public class Droppable extends GQueryUi {

	// A shortcut to the class
	public static final Class<Droppable> Droppable = Droppable.class;

	public static final String DROPPABLE_HANDLER_KEY = "droppableHandler";

	// Register the plugin in GQuery
	static {
		GQuery.registerPlugin(Droppable.class, new Plugin<Droppable>() {
			public Droppable init(GQuery gq) {
				return new Droppable(gq);
			}
		});
	}

	public static interface CssClassNames {
		String UI_DROPPABLE = "ui-droppable";
		String UI_DROPPABLE_DISABLED = "ui-droppable-disabled";

	}

	public Droppable(Element element) {
		super(element);
	}

	public Droppable(GQuery gq) {
		super(gq);
	}

	public Droppable(JSArray elements) {
		super(elements);
	}

	public Droppable(NodeList<Element> list) {
		super(list);
	}

	public Droppable droppable() {
		return droppable(new DroppableOptions());
	}

	public Droppable droppable(DroppableOptions o) {
		return droppable(o, null);
	}

	public Droppable droppable(DroppableOptions o, HasHandlers eventBus) {

		DragAndDropManager ddm = DragAndDropManager.getInstance();

		for (Element e : elements()) {
			DroppableHandler di = new DroppableHandler(o, eventBus);
			di.setDroppableDimension(new Dimension(e));
			$(e).data(DROPPABLE_HANDLER_KEY, di);

			ddm.addDroppable(e, o.getScope());

			e.addClassName(CssClassNames.UI_DROPPABLE);

		}

		return this;
	}

	public Droppable destroy() {
		DragAndDropManager ddm = DragAndDropManager.getInstance();
		for (Element e : elements()) {
			DroppableHandler infos = DroppableHandler.getInstance(e);
			ddm.getDroppablesByScope(infos.getOptions().getScope()).remove(e);
			$(e).removeClass(CssClassNames.UI_DROPPABLE,
					CssClassNames.UI_DROPPABLE_DISABLED).removeData(
					DROPPABLE_HANDLER_KEY);
		}
		return this;
	}

	public DroppableOptions options() {
		if (length() > 0) {
			DroppableHandler handler = DroppableHandler.getInstance(get(0));
			if (handler != null) {
				return handler.getOptions();
			}
		}
		return null;
	}
	
	public Droppable options(DroppableOptions options) {
		if (length() > 0) {
			DroppableHandler handler = DroppableHandler.getInstance(get(0));
			if (handler != null) {
				handler.setOptions(options);
			}
		}
		return this;
	}
	
	public Droppable changeScope(String newScope) {
		for (Element e : elements()) {			
			DroppableHandler handler = DroppableHandler.getInstance(e);
			if (handler != null) {
				String oldScope = handler.getOptions().getScope();
				DragAndDropManager dndManager =DragAndDropManager.getInstance();
				dndManager.getDroppablesByScope(oldScope).remove(e);
				dndManager.getDroppablesByScope(newScope).add(e);
				handler.getOptions().setScope(newScope);
			}
		}
		return this;
	}
	
	
}
