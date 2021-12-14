package org.eclipse.scout.apps.testipa.testipa.client.permission;

import java.util.Set;

import org.eclipse.scout.apps.testipa.testipa.client.permission.RolePermissionTablePage.Table;
import org.eclipse.scout.apps.testipa.testipa.shared.Icons;
import org.eclipse.scout.apps.testipa.testipa.shared.permission.IRolePermissionService;
import org.eclipse.scout.apps.testipa.testipa.shared.permission.RolePermissionTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(RolePermissionTablePageData.class)
public class RolePermissionTablePage extends AbstractPageWithTable<Table> {
	private Long userId;
	private Long roleUid;
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@FormData
	public Long getUserId() {
		return userId;
	}

	@FormData
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleUid() {
		return roleUid;
	}

	public void setRoleUid(Long roleUid) {
		this.roleUid = roleUid;
	}

	@Override
	protected boolean getConfiguredLeaf() {
		return true;
	}

	@Override
	protected void execLoadData(SearchFilter filter) {
		importPageData(BEANS.get(IRolePermissionService.class).getPermissionTableData(filter, getUserId(), getRoleUid()));
	}

	@Override
	protected String getConfiguredTitle() {
		return getTitle();
	}

	public class Table extends AbstractTable {

		@Override
		protected Class<? extends IMenu> getConfiguredDefaultMenu() {
			return EditMenu.class;
		}

		public PermissionColumn getPermissionColumn() {
			return getColumnSet().getColumnByClass(PermissionColumn.class);
		}

		public RoleColumn getRoleColumn() {
			return getColumnSet().getColumnByClass(RoleColumn.class);
		}

		@Order(1000)
		public class RoleColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Role");
			}

			@Override
			protected int getConfiguredWidth() {
				return 200;
			}

			@Override
			protected boolean getConfiguredVisible() {
				return false;
			}
		}

		@Order(2000)
		public class PermissionColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Permission");
			}

			@Override
			protected int getConfiguredWidth() {
				return 400;
			}

			@Override
			protected int getConfiguredSortIndex() {
				return 0;
			}
		}

		@Order(1000)
		public class EditMenu extends AbstractMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("EditPermissions");
			}

			@Override
			protected String getConfiguredIconId() {
				return Icons.Pencil;
			}

			@Override
			protected Set<? extends IMenuType> getConfiguredMenuTypes() {
				return CollectionUtility.hashSet(TableMenuType.EmptySpace, TableMenuType.SingleSelection, TableMenuType.MultiSelection);
			}

			@Override
			protected void execAction() {
				RolePermissionForm form = new RolePermissionForm();
				form.setRoleUid(getRoleUid());
				form.startModify();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

	}
}
