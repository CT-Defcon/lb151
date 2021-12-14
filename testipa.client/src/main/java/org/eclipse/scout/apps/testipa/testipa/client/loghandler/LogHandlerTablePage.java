package org.eclipse.scout.apps.testipa.testipa.client.loghandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.scout.apps.testipa.testipa.client.helper.NotificationHelper;
import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerTablePage.Table;
import org.eclipse.scout.apps.testipa.testipa.shared.Icons;
import org.eclipse.scout.apps.testipa.testipa.shared.loghandler.ILogHandlerService;
import org.eclipse.scout.apps.testipa.testipa.shared.loghandler.LogHandlerTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(LogHandlerTablePageData.class)
public class LogHandlerTablePage extends AbstractPageWithTable<Table> {
	@Override
	protected boolean getConfiguredLeaf() {
		return true;
	}

	@Override
	protected void execLoadData(SearchFilter filter) {
		importPageData(BEANS.get(ILogHandlerService.class).getLogHandlerTableData(filter));
	}

	@Override
	protected Class<? extends ISearchForm> getConfiguredSearchForm() {
		return LogHandlerSearchForm.class;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Logs");
	}

	public class Table extends AbstractTable {

		public ServerColumn getServerColumn() {
			return getColumnSet().getColumnByClass(ServerColumn.class);
		}

		public LogMessageColumn getMessageColumn() {
			return getColumnSet().getColumnByClass(LogMessageColumn.class);
		}

		public CheckBoxColumn getCheckBoxColumn() {
			return getColumnSet().getColumnByClass(CheckBoxColumn.class);
		}

		public ProjectColumn getProjectColumn() {
			return getColumnSet().getColumnByClass(ProjectColumn.class);
		}

		public TimeStampColumn getTimeStampColumn() {
			return getColumnSet().getColumnByClass(TimeStampColumn.class);
		}

		public LogIdColumn getLogIdColumn() {
			return getColumnSet().getColumnByClass(LogIdColumn.class);
		}

		@Override
		protected Class<? extends IMenu> getConfiguredDefaultMenu() {
			return OpenLogMenu.class;
		}

		@Order(1000)
		public class LogIdColumn extends AbstractLongColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("ID");
			}

			@Override
			protected int getConfiguredWidth() {
				return 80;
			}
		}

		@Order(2000)
		public class TimeStampColumn extends AbstractDateTimeColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Timestamp");
			}

			@Override
			protected int getConfiguredWidth() {
				return 200;
			}

		}

		@Order(3000)
		public class ProjectColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Project");
			}

			@Override
			protected int getConfiguredWidth() {
				return 80;
			}
		}

		@Order(4000)
		public class ServerColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Server");
			}

			@Override
			protected int getConfiguredWidth() {
				return 250;
			}
		}

		@Order(5000)
		public class LogMessageColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Message");
			}

			@Override
			protected int getConfiguredWidth() {
				return 700;
			}
		}

		@Order(6000)
		public class CheckBoxColumn extends AbstractBooleanColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Checked");
			}

			@Override
			protected int getConfiguredWidth() {
				return 80;
			}

			@Override
			protected boolean getConfiguredEditable() {
				return true;
			}

			@Override
			protected void execCompleteEdit(ITableRow row, IFormField editingField) {
				BEANS.get(ILogHandlerService.class).checkLogs(getLogIdColumn().getValue(row), !getCheckBoxColumn().getValue(row));
				reloadPage();
			}
		}

		@Order(7000)
		public class OpenLogMenu extends AbstractMenu {
			@Override
			protected String getConfiguredText() {
				return TEXTS.get("OpenLog");
			}

			@Override
			protected String getConfiguredIconId() {
				return Icons.Chart;
			}

			@Override
			protected String getConfiguredTooltipText() {
				return TEXTS.get("OpenToViewFullLog");
			}

			@Override
			protected Set<? extends IMenuType> getConfiguredMenuTypes() {
				return CollectionUtility.hashSet(TableMenuType.SingleSelection);
			}

			@Override
			protected void execAction() {
				LogHandlerForm form = new LogHandlerForm();
				form.getLogIdField().setValue(getLogIdColumn().getSelectedValue());
				form.startModify();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

		@Order(8000)
		public class CheckMenu extends AbstractMenu {
			@Override
			protected String getConfiguredText() {
				return TEXTS.get("CheckSelection");
			}

			@Override
			protected String getConfiguredTooltipText() {
				return TEXTS.get("MarkSelectedRowsAsChecked");
			}

			@Override
			protected String getConfiguredIconId() {
				return Icons.CheckedBold;
			}

			@Override
			protected Set<? extends IMenuType> getConfiguredMenuTypes() {
				return CollectionUtility.hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
			}

			@Override
			protected void execAction() {

				List<Long> alreadyChecked = new ArrayList<>();

				for (ITableRow row : getSelectedRows()) {

					Long id = getLogIdColumn().getValue(row);
					Boolean isChecked = getCheckBoxColumn().getValue(row);

					if (isChecked) {
						alreadyChecked.add(id);
					}
					else {
						BEANS.get(ILogHandlerService.class).checkLogs(id, true);
					}

				}

				NotificationHelper.showWarn(
						TEXTS.get("LogIDIsAlreadyChecked0",
								alreadyChecked.stream()
										.map(x -> String.valueOf(x))
										.collect(Collectors.joining("\n"))));

				if (alreadyChecked.size() == 0) {
					NotificationHelper.showOk(TEXTS.get("AllLogsChecked"));
				}

				reloadPage();
			}
		}
	}
}
