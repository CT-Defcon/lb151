package org.eclipse.scout.apps.testipa.testipa.client.abstractobject.field;

import org.eclipse.scout.apps.testipa.testipa.shared.abstractobject.field.AbstractPasswordFieldData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = AbstractPasswordFieldData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class AbstractPasswordField extends AbstractStringField {

	@Override
	protected boolean getConfiguredInputMasked() {
		return true;
	}

	@Override
	protected String getConfiguredLabel() {
		return TEXTS.get("Password0");
	}

	@Override
	protected boolean getConfiguredMandatory() {
		return true;
	}

	@Override
	protected int getConfiguredMaxLength() {
		return 60;
	}
}