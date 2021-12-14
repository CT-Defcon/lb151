package org.eclipse.scout.apps.testipa.testipa.client.user;

import org.eclipse.scout.apps.testipa.testipa.client.abstractobject.column.AbstractIdColumn;
import org.eclipse.scout.apps.testipa.testipa.client.abstractobject.column.AbstractSortingNameColumn;
import org.eclipse.scout.apps.testipa.testipa.client.abstractobject.menu.AbstractEditMenu;
import org.eclipse.scout.apps.testipa.testipa.client.abstractobject.menu.AbstractNewMenu;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserTablePage.Table;
import org.eclipse.scout.apps.testipa.testipa.shared.Icons;
import org.eclipse.scout.apps.testipa.testipa.shared.user.IUserService;
import org.eclipse.scout.apps.testipa.testipa.shared.user.UserTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(UserTablePageData.class)
public class UserTablePage extends AbstractPageWithTable<Table> {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Users");
	}

	@Override
	protected void execLoadData(SearchFilter filter) {
		importPageData(BEANS.get(IUserService.class).getUserTableData(filter));
	}

	@Override
	protected Class<? extends ISearchForm> getConfiguredSearchForm() {
		return UserSearchForm.class;
	}

	@Override
	protected boolean getConfiguredLeaf() {
		return true;
	}

	public class Table extends AbstractTable {

		public UserIdColumn getUserIdColumn() {
			return getColumnSet().getColumnByClass(UserIdColumn.class);
		}

		public UserNameColumn getUserNameColumn() {
			return getColumnSet().getColumnByClass(UserNameColumn.class);
		}

		public LocalUserColumn getLocalUserColumn() {
			return getColumnSet().getColumnByClass(LocalUserColumn.class);
		}

		public TokenColumn getTokenColumn() {
			return getColumnSet().getColumnByClass(TokenColumn.class);
		}

		@Override
		protected Class<? extends IMenu> getConfiguredDefaultMenu() {
			return EditMenu.class;
		}

		@Order(1000)
		public class UserIdColumn extends AbstractIdColumn {
		}

		@Order(2000)
		public class UserNameColumn extends AbstractSortingNameColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Username");
			}
		}

		@Order(3000)
		public class LogInColumn extends AbstractStringColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Log-InName");
			}

			@Override
			protected int getConfiguredWidth() {
				return 150;
			}
		}

		@Order(4000)
		public class TokenColumn extends AbstractStringColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Token");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(5000)
		public class EmailColumn extends AbstractStringColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Email");
			}

			@Override
			protected int getConfiguredWidth() {
				return 300;
			}
		}

		@Order(7000)
		public class LocalUserColumn extends AbstractBooleanColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("LocalUser");
			}

			@Override
			protected boolean getConfiguredAutoOptimizeWidth() {
				return true;
			}
		}

		@Order(8000)
		public class NewMenu extends AbstractNewMenu {

			@Override
			protected void execAction() {
				UserForm form = new UserForm();
				form.startNew();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}

			}
		}

		@Order(9000)
		public class EditMenu extends AbstractEditMenu {

			@Override
			protected void execAction() {
				UserForm form = new UserForm();
				form.setUserNr(getUserIdColumn().getSelectedValue());
				form.startModify();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

		@Order(10000)
		public class DeleteMenu extends AbstractMenu {
			@Override
			protected String getConfiguredText() {
				return TEXTS.get("DeleteMenu0");
			}

			@Override
			protected String getConfiguredIconId() {
				return Icons.Minus;
			}

			@Override
			protected void execAction() {

				if (MessageBoxes.createYesNo()
						.withBody(TEXTS.get("AreYouSureYouWantToDeleteUser0",
								getUserNameColumn().getSelectedValue()))
						.show() == MessageBox.YES_OPTION) {

					BEANS.get(IUserService.class).delete(getUserIdColumn().getSelectedValue());
					reloadPage();
				}
			}
		}
	}
}
