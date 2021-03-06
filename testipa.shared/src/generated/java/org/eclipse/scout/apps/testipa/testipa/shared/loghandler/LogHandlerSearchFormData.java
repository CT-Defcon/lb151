package org.eclipse.scout.apps.testipa.testipa.shared.loghandler;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@Generated(value = "org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerSearchForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class LogHandlerSearchFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public SearchCheckBox getSearchCheckBox() {
		return getFieldByClass(SearchCheckBox.class);
	}

	public SearchLogMessage getSearchLogMessage() {
		return getFieldByClass(SearchLogMessage.class);
	}

	public SearchProject getSearchProject() {
		return getFieldByClass(SearchProject.class);
	}

	public SearchServer getSearchServer() {
		return getFieldByClass(SearchServer.class);
	}

	public static class SearchCheckBox extends AbstractValueFieldData<Boolean> {
		private static final long serialVersionUID = 1L;
	}

	public static class SearchLogMessage extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;
	}

	public static class SearchProject extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;
	}

	public static class SearchServer extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;
	}
}
