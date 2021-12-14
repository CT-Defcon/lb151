package org.eclipse.scout.apps.testipa.testipa.client.user;

import org.eclipse.scout.apps.testipa.testipa.client.user.UserForm.MainBox.CancelButton;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserForm.MainBox.GroupBox;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserForm.MainBox.GroupBox.EmailField;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserForm.MainBox.GroupBox.LocalUserField;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserForm.MainBox.GroupBox.LogInField;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserForm.MainBox.GroupBox.PasswordField;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserForm.MainBox.GroupBox.TokenField;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserForm.MainBox.GroupBox.UserNameField;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserForm.MainBox.OkButton;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserForm.MainBox.RolesBox;
import org.eclipse.scout.apps.testipa.testipa.shared.code.CodeLookupCall;
import org.eclipse.scout.apps.testipa.testipa.shared.permission.RolePermissionCodeType;
import org.eclipse.scout.apps.testipa.testipa.shared.user.CreateUserPermission;
import org.eclipse.scout.apps.testipa.testipa.shared.user.IUserService;
import org.eclipse.scout.apps.testipa.testipa.shared.user.UpdateUserPermission;
import org.eclipse.scout.apps.testipa.testipa.shared.user.UserFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = UserFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class UserForm extends AbstractForm {
	private Long userNr;

	@FormData
	public Long getUserNr() {
		return userNr;
	}

	@FormData
	public void setUserNr(Long userNr) {
		this.userNr = userNr;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("User");
	}

	public void startModify() {
		startInternalExclusive(new ModifyHandler());
	}

	public void startUser() {
		startInternal(new UserHandler());
	}

	public void startNew() {
		startInternal(new NewHandler());
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public GroupBox getGroupBox() {
		return getFieldByClass(GroupBox.class);
	}

	public UserNameField getUserNameField() {
		return getFieldByClass(UserNameField.class);
	}

	public LogInField getLogInField() {
		return getFieldByClass(LogInField.class);
	}

	public PasswordField getPasswordField() {
		return getFieldByClass(PasswordField.class);
	}

	public TokenField getTokenField() {
		return getFieldByClass(TokenField.class);
	}

	public EmailField getEmailField() {
		return getFieldByClass(EmailField.class);
	}

	public LocalUserField getLocalUserField() {
		return getFieldByClass(LocalUserField.class);
	}

	public RolesBox getRolesBox() {
		return getFieldByClass(RolesBox.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	public CancelButton getCancelButton() {
		return getFieldByClass(CancelButton.class);
	}

	@Override
	protected boolean execValidate() {
		if (getPasswordField().getValue().length() < 8) {
			throw new VetoException(TEXTS.get("PasswordToShort"));
		}

		return true;
	}

	@Order(1000)
	public class MainBox extends AbstractGroupBox {
		@Order(1000)
		public class GroupBox extends AbstractGroupBox {
			@Override
			protected int getConfiguredGridColumnCount() {
				return 1;
			}

			@Override
			protected int getConfiguredGridW() {
				return 1;
			}

			@Order(1000)
			public class UserNameField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Username");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 60;
				}

				@Override
				protected boolean getConfiguredMandatory() {
					return true;
				}
			}

			@Order(2000)
			public class LogInField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Login");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 60;
				}

				@Override
				protected boolean getConfiguredMandatory() {
					return true;
				}
			}

			@Order(3000)
			public class PasswordField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Password");
				}

				@Override
				protected boolean getConfiguredInputMasked() {
					return true;
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 166;
				}

				@Override
				protected boolean getConfiguredMandatory() {
					return true;
				}
			}

			@Order(4000)
			public class TokenField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Token");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 60;
				}

				@Override
				protected boolean getConfiguredMandatory() {
					return true;
				}
			}

			@Order(5000)
			public class EmailField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Email");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 60;
				}
			}

			@Order(7000)
			public class LocalUserField extends AbstractBooleanField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("LocalUser");
				}
			}
		}

		@Order(1500)
		public class RolesBox extends AbstractGroupBox {
			@Override
			protected int getConfiguredGridW() {
				return 1;
			}

			@Order(1000)
			public class RolesField extends AbstractListBox<Long> {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("AssignRoles");
				}

				@Override
				protected int getConfiguredGridH() {
					return 6;
				}

				@Override
				protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
					return CodeLookupCall.class;
				}

				@Override
				protected void execPrepareLookup(ILookupCall<Long> call) {
					CodeLookupCall c = (CodeLookupCall) call;
					c.setCodeTypeId(RolePermissionCodeType.ID);
				}

				@Override
				protected byte getConfiguredLabelPosition() {
					return LABEL_POSITION_TOP;
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

	public class NewHandler extends AbstractFormHandler {
		@Override
		protected void execLoad() {
			UserFormData formData = new UserFormData();
			exportFormData(formData);
			formData = BEANS.get(IUserService.class).prepareCreate(formData);
			importFormData(formData);

			setEnabledPermission(new CreateUserPermission());
		}

		@Override
		protected void execStore() {
			UserFormData formData = new UserFormData();
			exportFormData(formData);
			formData = BEANS.get(IUserService.class).create(formData);
			importFormData(formData);
		}
	}

	public class ModifyHandler extends AbstractFormHandler {
		@Override
		protected void execLoad() {
			UserFormData formData = new UserFormData();
			exportFormData(formData);
			formData = BEANS.get(IUserService.class).load(formData, true);
			importFormData(formData);

			setEnabledPermission(new UpdateUserPermission());
		}

		@Override
		protected void execStore() {
			UserFormData formData = new UserFormData();
			exportFormData(formData);
			formData = BEANS.get(IUserService.class).store(formData);
			importFormData(formData);
		}
	}

	public class UserHandler extends AbstractFormHandler {

		@Override
		protected void execLoad() {
			IUserService service = BEANS.get(IUserService.class);
			UserFormData formData = new UserFormData();
			exportFormData(formData);
			formData = service.load(formData, false);
			importFormData(formData);

			getLogInField().setEnabledGranted(false);
			getPasswordField().setEnabledGranted(false);
			getTokenField().setEnabledGranted(false);
			getLocalUserField().setEnabledGranted(false);
			getRolesBox().setEnabledGranted(false);
		}

		@Override
		protected void execStore() {
			IUserService service = BEANS.get(IUserService.class);
			UserFormData formData = new UserFormData();
			exportFormData(formData);
			service.storeUser(formData);
		}
	}
}
