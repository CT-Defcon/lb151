package org.eclipse.scout.apps.testipa.testipa.client.user;

import org.eclipse.scout.apps.testipa.testipa.client.abstractobject.field.AbstractPasswordField;
import org.eclipse.scout.apps.testipa.testipa.client.user.ChangePasswordForm.MainBox.CancelButton;
import org.eclipse.scout.apps.testipa.testipa.client.user.ChangePasswordForm.MainBox.GroupBox;
import org.eclipse.scout.apps.testipa.testipa.client.user.ChangePasswordForm.MainBox.GroupBox.CheckPasswordField;
import org.eclipse.scout.apps.testipa.testipa.client.user.ChangePasswordForm.MainBox.GroupBox.NewPasswordField;
import org.eclipse.scout.apps.testipa.testipa.client.user.ChangePasswordForm.MainBox.OkButton;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserForm.MainBox.GroupBox.PasswordField;
import org.eclipse.scout.apps.testipa.testipa.shared.user.ChangePasswordFormData;
import org.eclipse.scout.apps.testipa.testipa.shared.user.IUserService;
import org.eclipse.scout.apps.testipa.testipa.shared.user.UpdateChangePasswordPermission;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;

@FormData(value = ChangePasswordFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ChangePasswordForm extends AbstractForm {
	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("ChangePassword0");
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public GroupBox getGroupBox() {
		return getFieldByClass(GroupBox.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	public CancelButton getCancelButton() {
		return getFieldByClass(CancelButton.class);
	}

	public PasswordField getPasswordField() {
		return getFieldByClass(PasswordField.class);
	}

	public NewPasswordField getNewPasswordField() {
		return getFieldByClass(NewPasswordField.class);
	}

	public CheckPasswordField getCheckPasswordField() {
		return getFieldByClass(CheckPasswordField.class);
	}

	@Override
	protected boolean execValidate() {

		if (ObjectUtility.notEquals(getNewPasswordField().getValue(), getCheckPasswordField().getValue())) {
			throw new VetoException(TEXTS.get("PasswordsDoNotMatch"));
		}

		if (getNewPasswordField().getValue().length() < 8) {
			throw new VetoException(TEXTS.get("PasswordToShort"));
		}

		if (StringUtility.isNullOrEmpty(getNewPasswordField().getValue())) {
			throw new VetoException(TEXTS.get("PasswordToShort"));
		}

		return true;
	}

	@Order(1000)
	public class MainBox extends AbstractGroupBox {

		@Override
		protected int getConfiguredGridColumnCount() {
			return 1;
		}

		@Order(1000)
		public class GroupBox extends AbstractGroupBox {

			@Order(1000)
			public class PasswordField extends AbstractPasswordField {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("OldPassword0");
				}
			}

			@Order(2000)
			public class NewPasswordField extends AbstractPasswordField {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("NewPassword0");
				}
			}

			@Order(3000)
			public class CheckPasswordField extends AbstractPasswordField {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("RepeatPassword0");
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

	public class ModifyHandler extends AbstractFormHandler {
		@Override
		protected void execLoad() {

			setEnabledPermission(new UpdateChangePasswordPermission());
		}

		@Override
		protected void execStore() {
			IUserService service = BEANS.get(IUserService.class);
			ChangePasswordFormData formData = new ChangePasswordFormData();
			exportFormData(formData);
			service.changePassword(formData);
		}
	}
}
