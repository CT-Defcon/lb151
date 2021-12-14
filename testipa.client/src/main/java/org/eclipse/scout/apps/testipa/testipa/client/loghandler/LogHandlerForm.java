package org.eclipse.scout.apps.testipa.testipa.client.loghandler;

import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerForm.MainBox.CancelButton;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerForm.MainBox.FullLogBox;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerForm.MainBox.FullLogBox.LogBox;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerForm.MainBox.FullLogBox.LogBox.LogMessageField;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerForm.MainBox.GroupBox;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerForm.MainBox.GroupBox.CheckBoxField;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerForm.MainBox.GroupBox.LogIdField;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerForm.MainBox.GroupBox.ProjectField;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerForm.MainBox.GroupBox.ServerField;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerForm.MainBox.GroupBox.TimeStampField;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerForm.MainBox.OkButton;
import org.eclipse.scout.apps.testipa.testipa.shared.loghandler.ILogHandlerService;
import org.eclipse.scout.apps.testipa.testipa.shared.loghandler.LogHandlerFormData;
import org.eclipse.scout.apps.testipa.testipa.shared.loghandler.UpdateLogHandlerPermission;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = LogHandlerFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class LogHandlerForm extends AbstractForm {
	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("LogForm");
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public GroupBox getGroupBox() {
		return getFieldByClass(GroupBox.class);
	}

	public LogIdField getLogIdField() {
		return getFieldByClass(LogIdField.class);
	}

	public TimeStampField getTimeStampField() {
		return getFieldByClass(TimeStampField.class);
	}

	public ProjectField getProjectField() {
		return getFieldByClass(ProjectField.class);
	}

	public ServerField getServerField() {
		return getFieldByClass(ServerField.class);
	}

	public CheckBoxField getCheckBoxField() {
		return getFieldByClass(CheckBoxField.class);
	}

	public FullLogBox getFullLogBox() {
		return getFieldByClass(FullLogBox.class);
	}

	public LogBox getLogBox() {
		return getFieldByClass(LogBox.class);
	}

	public LogMessageField getLogMessageField() {
		return getFieldByClass(LogMessageField.class);
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
			public class LogIdField extends AbstractLongField {
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
			public class TimeStampField extends AbstractDateTimeField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Timestamp");
				}

				@Override
				protected boolean getConfiguredEnabled() {
					return false;
				}
			}

			@Order(3000)
			public class ProjectField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Project");
				}

				@Override
				protected boolean getConfiguredEnabled() {
					return false;
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(4000)
			public class ServerField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Server");
				}

				@Override
				protected boolean getConfiguredEnabled() {
					return false;
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(5000)
			public class CheckBoxField extends AbstractBooleanField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Checked");
				}

			}

		}

		@Order(1500)
		public class FullLogBox extends AbstractTabBox {

			@Order(1000)
			public class LogBox extends AbstractGroupBox {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("FullLog");
				}

				@Order(1000)
				public class LogMessageField extends AbstractStringField {

					@Override
					protected int getConfiguredWidthInPixel() {
						return 600;
					}

					@Override
					protected boolean getConfiguredEnabled() {
						return false;
					}

					@Override
					protected boolean getConfiguredMultilineText() {
						return true;
					}

					@Override
					protected int getConfiguredHeightInPixel() {
						return 300;
					}

					@Override
					protected boolean getConfiguredWrapText() {
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

	public class ModifyHandler extends AbstractFormHandler {
		@Override
		protected void execLoad() {
			LogHandlerFormData formData = new LogHandlerFormData();
			exportFormData(formData);
			formData = BEANS.get(ILogHandlerService.class).load(formData);
			importFormData(formData);

			setEnabledPermission(new UpdateLogHandlerPermission());
		}

		@Override
		protected void execStore() {
			LogHandlerFormData formData = new LogHandlerFormData();
			exportFormData(formData);
			formData = BEANS.get(ILogHandlerService.class).store(formData);
			importFormData(formData);
		}
	}
}
