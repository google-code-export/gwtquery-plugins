package gwtquery.plugins.draggable.client.impl;

import com.google.gwt.query.client.GQuery;

public class DraggableImpl {

  public boolean resetParentOffsetPosition(GQuery helperOffsetParent) {
    return helperOffsetParent.get(0) == GQuery.body;
  }

}
