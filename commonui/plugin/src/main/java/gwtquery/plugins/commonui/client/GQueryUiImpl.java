package gwtquery.plugins.commonui.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Predicate;

public class GQueryUiImpl {

	private final Element getViewportElement() {
		return GQuery.document.isCSS1Compat() ? GQuery.document
				.getDocumentElement() : GQuery.document.getBody();
	}

	public GQuery scrollParent(final GQueryUi gQueryUi) {
		GQuery scrollParent;

		if ("fixed".equals(gQueryUi.css("position"))) {
			return GQuery.$(getViewportElement());
		}

		if (scrollParentPositionTest(gQueryUi)) {
			scrollParent = gQueryUi.parents().filter(new Predicate() {

				public boolean f(Element e, int index) {
					GQuery $e = GQuery.$(e);
					String position = $e.css("position", true);
					return ("relative".equals(position) || "absolute".equals(position) || "fixed".equals(position))
							&& isOverflowEnabled($e);
				}
			});

		} else {
			scrollParent = gQueryUi.parents().filter(new Predicate() {

				public boolean f(Element e, int index) {
					return isOverflowEnabled(GQuery.$(e));
				}
			});
		}
		return scrollParent.length() > 0 ? new GQuery(scrollParent.get(0))
				: GQuery.$(getViewportElement());

	}

	private boolean isOverflowEnabled(GQuery e) {
		String overflow = e.css("overflow", true) + e.css("overflow-x", true)
				+ e.css("overflow-y", true);
		return overflow.contains("auto") || overflow.contains("scroll");
	}

	protected boolean scrollParentPositionTest(GQueryUi gQueryUi) {
		return "absolute".equals(gQueryUi.css("position"));
	}

}
