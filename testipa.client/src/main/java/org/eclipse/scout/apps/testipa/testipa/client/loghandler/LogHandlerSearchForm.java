package org.eclipse.scout.apps.testipa.testipa.client.loghandler;

import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerSearchForm.MainBox.CancelButton;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerSearchForm.MainBox.GroupBox;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerSearchForm.MainBox.GroupBox.SearchCheckBoxField;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerSearchForm.MainBox.GroupBox.SearchLogMessageField;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerSearchForm.MainBox.GroupBox.SearchProjectField;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerSearchForm.MainBox.GroupBox.SearchServerField;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerSearchForm.MainBox.OkButton;
import org.eclipse.scout.apps.testipa.testipa.shared.loghandler.LogHandlerSearchFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = LogHandlerSearchFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class LogHandlerSearchForm extends AbstractSearchForm {
	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("LogSearchForm");
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public GroupBox getGroupBox() {
		return getFieldByClass(GroupBox.class);
	}

	public SearchProjectField getSearchProjectField() {
		return getFieldByClass(SearchProjectField.class);
	}

	public SearchServerField getSearchServerField() {
		return getFieldByClass(SearchServerField.class);
	}

	public SearchLogMessageField getSearchLogMessageField() {
		return getFieldByClass(SearchLogMessageField.class);
	}

	public SearchCheckBoxField getSearchCheckBoxField() {
		return getFieldByClass(SearchCheckBoxField.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	public CancelButton getCancelButton() {
		return getFieldByClass(CancelButton.class);
	}

	@Order(1000)
	public class MainBox extends AbstractGroupBox {
		@Order(1000)
		public class GroupBox extends AbstractGroupBox {

			@Order(1000)
			public class SearchProjectField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Project");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(2000)
			public class SearchServerField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Server");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(3000)
			public class SearchLogMessageField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Message");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(4000)
			public class SearchCheckBoxField extends AbstractBooleanField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("CheckedStatus");
				}

				@Override
				protected boolean getConfiguredTriStateEnabled() {
					return true;
				}

				@Override
				protected void execInitField() {
					setValue(null);
				}
			}

		}

		@Order(2000)
		public class OkButton extends AbstractSearchButton {

		}

		@Order(3000)
		public class CancelButton extends AbstractResetButton {

		}
	}

}
