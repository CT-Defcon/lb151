package org.eclipse.scout.apps.testipa.testipa.client.errorhandler;

import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerSearchForm.MainBox.CancelButton;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerSearchForm.MainBox.GroupBox;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerSearchForm.MainBox.GroupBox.SearchErrorMessageField;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerSearchForm.MainBox.GroupBox.SearchErrorTypeField;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerSearchForm.MainBox.GroupBox.SearchProjectField;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerSearchForm.MainBox.OkButton;
import org.eclipse.scout.apps.testipa.testipa.shared.errorhandler.ErrorHandlerSearchFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = ErrorHandlerSearchFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ErrorHandlerSearchForm extends AbstractSearchForm {
	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("ErrorHandlerSearch");
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public GroupBox getGroupBox() {
		return getFieldByClass(GroupBox.class);
	}

	public SearchErrorTypeField getSearchErrorTypeField() {
		return getFieldByClass(SearchErrorTypeField.class);
	}

	public SearchErrorMessageField getSearchErrorMessageField() {
		return getFieldByClass(SearchErrorMessageField.class);
	}

	public SearchProjectField getSearchProjectField() {
		return getFieldByClass(SearchProjectField.class);
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
			public class SearchErrorTypeField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("ErrorType");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(2000)
			public class SearchErrorMessageField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("ErrorMessage");
				}

				@Override
				protected String getConfiguredTooltipText() {
					return TEXTS.get("ConfigureSearchWithStar");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(3000)
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

		}

		@Order(2000)
		public class OkButton extends AbstractSearchButton {

		}

		@Order(3000)
		public class CancelButton extends AbstractResetButton {

		}
	}

}
