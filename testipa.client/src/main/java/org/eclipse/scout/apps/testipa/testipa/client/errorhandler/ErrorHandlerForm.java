package org.eclipse.scout.apps.testipa.testipa.client.errorhandler;

import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerForm.MainBox.CancelButton;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerForm.MainBox.ErrorMessageGroupBox;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerForm.MainBox.ErrorMessageGroupBox.ErrorMessageField;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerForm.MainBox.ErrorSolutionTabBox;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerForm.MainBox.ErrorSolutionTabBox.ErrorSolutionGroupBox;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerForm.MainBox.ErrorSolutionTabBox.ErrorSolutionGroupBox.SolutionField;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerForm.MainBox.GroupBox;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerForm.MainBox.GroupBox.ErrorIdField;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerForm.MainBox.GroupBox.ErrorTypeField;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerForm.MainBox.GroupBox.ProjectField;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerForm.MainBox.OkButton;
import org.eclipse.scout.apps.testipa.testipa.shared.errorhandler.CreateErrorHandlerPermission;
import org.eclipse.scout.apps.testipa.testipa.shared.errorhandler.ErrorHandlerFormData;
import org.eclipse.scout.apps.testipa.testipa.shared.errorhandler.IErrorHandlerService;
import org.eclipse.scout.apps.testipa.testipa.shared.errorhandler.UpdateErrorHandlerPermission;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = ErrorHandlerFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ErrorHandlerForm extends AbstractForm {
	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("ErrorHandlerForm");
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public GroupBox getGroupBox() {
		return getFieldByClass(GroupBox.class);
	}

	public ErrorIdField getErrorIdField() {
		return getFieldByClass(ErrorIdField.class);
	}

	public ProjectField getProjectField() {
		return getFieldByClass(ProjectField.class);
	}

	public ErrorTypeField getErrorTypeField() {
		return getFieldByClass(ErrorTypeField.class);
	}

	public ErrorMessageField getErrorMessageField() {
		return getFieldByClass(ErrorMessageField.class);
	}

	public ErrorMessageGroupBox getErrorMessageGroupBox() {
		return getFieldByClass(ErrorMessageGroupBox.class);
	}

	public ErrorSolutionTabBox getErrorSolutionTabBox() {
		return getFieldByClass(ErrorSolutionTabBox.class);
	}

	public ErrorSolutionGroupBox getErrorSolutionGroupBox() {
		return getFieldByClass(ErrorSolutionGroupBox.class);
	}

	public SolutionField getSolutionField() {
		return getFieldByClass(SolutionField.class);
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
			public class ErrorIdField extends AbstractLongField {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("ID");
				}

				@Override
				protected boolean getConfiguredEnabled() {
					return false;
				}
			}

			@Order(2000)
			public class ProjectField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Project");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(3000)
			public class ErrorTypeField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("ErrorType");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

		}

		@Order(1250)
		public class ErrorMessageGroupBox extends AbstractGroupBox {

			@Order(1000)
			public class ErrorMessageField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("ErrorMessage");
				}

				@Override
				protected boolean getConfiguredWrapText() {
					return true;
				}

				@Override
				protected boolean getConfiguredMultilineText() {
					return true;
				}

				@Override
				protected int getConfiguredHeightInPixel() {
					return 200;
				}

			}
		}

		@Order(1625)
		public class ErrorSolutionTabBox extends AbstractTabBox {

			@Order(1000)
			public class ErrorSolutionGroupBox extends AbstractGroupBox {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Solution");
				}

				@Order(1000)
				public class SolutionField extends AbstractStringField {

					@Override
					protected int getConfiguredHeightInPixel() {
						return 200;
					}

					@Override
					protected boolean getConfiguredMultilineText() {
						return true;
					}
				}

			}

		}

		@Order(2000)
		public class OkButton extends AbstractOkButton {

		}

		@Order(3000)
		public class CancelButton extends AbstractCancelButton {

		}
	}

	public void startModify() {
		startInternalExclusive(new ModifyHandler());
	}

	public void startNew() {
		startInternal(new NewHandler());
	}

	public class NewHandler extends AbstractFormHandler {
		@Override
		protected void execLoad() {
			ErrorHandlerFormData formData = new ErrorHandlerFormData();
			exportFormData(formData);
			formData = BEANS.get(IErrorHandlerService.class).prepareCreate(formData);
			importFormData(formData);

			setEnabledPermission(new CreateErrorHandlerPermission());
		}

		@Override
		protected void execStore() {
			ErrorHandlerFormData formData = new ErrorHandlerFormData();
			exportFormData(formData);
			formData = BEANS.get(IErrorHandlerService.class).create(formData);
			importFormData(formData);
		}
	}

	public class ModifyHandler extends AbstractFormHandler {
		@Override
		protected void execLoad() {
			ErrorHandlerFormData formData = new ErrorHandlerFormData();
			exportFormData(formData);
			formData = BEANS.get(IErrorHandlerService.class).load(formData);
			importFormData(formData);

			setEnabledPermission(new UpdateErrorHandlerPermission());
		}

		@Override
		protected void execStore() {
			ErrorHandlerFormData formData = new ErrorHandlerFormData();
			exportFormData(formData);
			formData = BEANS.get(IErrorHandlerService.class).store(formData);
			importFormData(formData);
		}
	}
}
