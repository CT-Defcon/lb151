package org.eclipse.scout.apps.testipa.testipa.client.errorhandler;

import org.eclipse.scout.apps.testipa.testipa.client.abstractobject.menu.AbstractEditMenu;
import org.eclipse.scout.apps.testipa.testipa.client.abstractobject.menu.AbstractNewMenu;
import org.eclipse.scout.apps.testipa.testipa.client.errorhandler.ErrorHandlerTablePage.Table;
import org.eclipse.scout.apps.testipa.testipa.shared.errorhandler.ErrorHandlerTablePageData;
import org.eclipse.scout.apps.testipa.testipa.shared.errorhandler.IErrorHandlerService;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(ErrorHandlerTablePageData.class)
public class ErrorHandlerTablePage extends AbstractPageWithTable<Table> {
	@Override
	protected boolean getConfiguredLeaf() {
		return true;
	}

	@Override
	protected void execLoadData(SearchFilter filter) {
		importPageData(BEANS.get(IErrorHandlerService.class).getErrorHandlerTableData(filter));
	}

	@Override
	protected Class<? extends ISearchForm> getConfiguredSearchForm() {
		return ErrorHandlerSearchForm.class;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("ErrorSolutions");
	}

	public class Table extends AbstractTable {

		public ErrorTypeColumn getErrorTypeColumn() {
			return getColumnSet().getColumnByClass(ErrorTypeColumn.class);
		}

		public ErrorMessageColumn getErrorMessageColumn() {
			return getColumnSet().getColumnByClass(ErrorMessageColumn.class);
		}

		public SolutionColumn getSolutionColumn() {
			return getColumnSet().getColumnByClass(SolutionColumn.class);
		}

		public ProjectColumn getProjectColumn() {
			return getColumnSet().getColumnByClass(ProjectColumn.class);
		}

		public ErrorIdColumn getErrorIdColumn() {
			return getColumnSet().getColumnByClass(ErrorIdColumn.class);
		}

		@Override
		protected Class<? extends IMenu> getConfiguredDefaultMenu() {
			return EditMenu.class;
		}

		@Order(1000)
		public class ErrorIdColumn extends AbstractLongColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("ID");
			}

			@Override
			protected int getConfiguredWidth() {
				return 60;
			}
		}

		@Order(2000)
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

		@Order(3000)
		public class ErrorTypeColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("ErrorType");
			}

			@Override
			protected int getConfiguredWidth() {
				return 80;
			}
		}

		@Order(4000)
		public class ErrorMessageColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("ErrorMessage");
			}

			@Override
			protected int getConfiguredWidth() {
				return 600;
			}

		}

		@Order(5000)
		public class SolutionColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Solution");
			}

			@Override
			protected int getConfiguredWidth() {
				return 600;
			}
		}

		@Order(6000)
		public class NewMenu extends AbstractNewMenu {

			@Override
			protected String getConfiguredTooltipText() {
				return TEXTS.get("AddErrorSolution");
			}

			@Override
			protected void execAction() {

				ErrorHandlerForm form = new ErrorHandlerForm();
				form.startNew();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

		@Order(7000)
		public class EditMenu extends AbstractEditMenu {

			@Override
			protected void execAction() {

				ErrorHandlerForm form = new ErrorHandlerForm();
				form.getErrorIdField().setValue(getErrorIdColumn().getSelectedValue());
				form.startModify();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

	}
}
