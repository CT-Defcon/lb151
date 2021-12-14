package org.eclipse.scout.apps.testipa.testipa.client.code;

import org.eclipse.scout.apps.testipa.testipa.client.code.CodeForm.MainBox.CancelButton;
import org.eclipse.scout.apps.testipa.testipa.client.code.CodeForm.MainBox.GroupBox;
import org.eclipse.scout.apps.testipa.testipa.client.code.CodeForm.MainBox.GroupBox.DescriptionField;
import org.eclipse.scout.apps.testipa.testipa.client.code.CodeForm.MainBox.GroupBox.NameField;
import org.eclipse.scout.apps.testipa.testipa.client.code.CodeForm.MainBox.GroupBox.UcNoField;
import org.eclipse.scout.apps.testipa.testipa.client.code.CodeForm.MainBox.OkButton;
import org.eclipse.scout.apps.testipa.testipa.shared.code.CodeFormData;
import org.eclipse.scout.apps.testipa.testipa.shared.code.CreateCodePermission;
import org.eclipse.scout.apps.testipa.testipa.shared.code.ICodeService;
import org.eclipse.scout.apps.testipa.testipa.shared.code.UpdateCodePermission;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = CodeFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class CodeForm extends AbstractForm {
	private Long codeId;
	private Long codeTypeId;

	@FormData
	public Long getCodeId() {
		return codeId;
	}

	@FormData
	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	@FormData
	public Long getCodeTypeId() {
		return codeTypeId;
	}

	@FormData
	public void setCodeTypeId(Long codeTypeId) {
		this.codeTypeId = codeTypeId;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Code");
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public GroupBox getGroupBox() {
		return getFieldByClass(GroupBox.class);
	}

	public UcNoField getUcNoField() {
		return getFieldByClass(UcNoField.class);
	}

	public DescriptionField getDescriptionField() {
		return getFieldByClass(DescriptionField.class);
	}

	public NameField getNameField() {
		return getFieldByClass(NameField.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	public CancelButton getCancelButton() {
		return getFieldByClass(CancelButton.class);
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
			public class NameField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Name0");
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
			public class UcNoField extends AbstractBigDecimalField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("SortingNumber");
				}

				@Override
				protected int getConfiguredMaxIntegerDigits() {
					return 10;
				}

			}

			@Order(3000)
			public class DescriptionField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Description");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 250;
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
			CodeFormData formData = new CodeFormData();
			exportFormData(formData);
			formData = BEANS.get(ICodeService.class).prepareCreate(formData);
			importFormData(formData);

			setEnabledPermission(new CreateCodePermission());
		}

		@Override
		protected void execStore() {
			ICodeService service = BEANS.get(ICodeService.class);
			CodeFormData formData = new CodeFormData();
			exportFormData(formData);
			service.create(formData);
		}
	}

	public class ModifyHandler extends AbstractFormHandler {
		@Override
		protected void execLoad() {
			CodeFormData formData = new CodeFormData();
			exportFormData(formData);
			formData = BEANS.get(ICodeService.class).load(formData);
			importFormData(formData);

			setEnabledPermission(new UpdateCodePermission());
		}

		@Override
		protected void execStore() {
			ICodeService service = BEANS.get(ICodeService.class);
			CodeFormData formData = new CodeFormData();
			exportFormData(formData);
			service.store(formData);
		}
	}
}
