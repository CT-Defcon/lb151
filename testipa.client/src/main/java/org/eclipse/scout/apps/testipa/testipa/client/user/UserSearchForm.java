package org.eclipse.scout.apps.testipa.testipa.client.user;

import org.eclipse.scout.apps.testipa.testipa.client.user.UserSearchForm.MainBox.CancelButton;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserSearchForm.MainBox.GroupBox;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserSearchForm.MainBox.OkButton;
import org.eclipse.scout.apps.testipa.testipa.shared.user.UserSearchFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = UserSearchFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class UserSearchForm extends AbstractSearchForm {
	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("UserSearch");
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

	@Order(1000)
	public class MainBox extends AbstractGroupBox {
		@Order(1000)
		public class GroupBox extends AbstractGroupBox {

		}

		@Order(2000)
		public class OkButton extends AbstractSearchButton {

		}

		@Order(3000)
		public class CancelButton extends AbstractResetButton {

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
		}

		@Override
		protected void execStore() {
		}
	}

	public class ModifyHandler extends AbstractFormHandler {
		@Override
		protected void execLoad() {
		}

		@Override
		protected void execStore() {
		}
	}
}
