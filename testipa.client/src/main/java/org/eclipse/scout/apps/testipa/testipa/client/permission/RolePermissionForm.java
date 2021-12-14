package org.eclipse.scout.apps.testipa.testipa.client.permission;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Set;

import org.eclipse.scout.apps.testipa.testipa.client.permission.RolePermissionForm.MainBox.CancelButton;
import org.eclipse.scout.apps.testipa.testipa.client.permission.RolePermissionForm.MainBox.GroupBox;
import org.eclipse.scout.apps.testipa.testipa.client.permission.RolePermissionForm.MainBox.GroupBox.AllPermissionsField;
import org.eclipse.scout.apps.testipa.testipa.client.permission.RolePermissionForm.MainBox.GroupBox.PermissionsField;
import org.eclipse.scout.apps.testipa.testipa.client.permission.RolePermissionForm.MainBox.OkButton;
import org.eclipse.scout.apps.testipa.testipa.shared.Icons;
import org.eclipse.scout.apps.testipa.testipa.shared.permission.IRolePermissionService;
import org.eclipse.scout.apps.testipa.testipa.shared.permission.RolePermissionFormData;
import org.eclipse.scout.apps.testipa.testipa.shared.permission.UpdateRolePermissionPermission;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.security.IPermissionService;

@FormData(value = RolePermissionFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class RolePermissionForm extends AbstractForm {

	private Long roleUid;

	@FormData
	public Long getRoleUid() {
		return roleUid;
	}

	@FormData
	public void setRoleUid(Long roleUid) {
		this.roleUid = roleUid;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("CreateRole");
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public GroupBox getGroupBox() {
		return getFieldByClass(GroupBox.class);
	}

	public PermissionsField getPermissionsField() {
		return getFieldByClass(PermissionsField.class);
	}

	public AllPermissionsField getAllPermissionsField() {
		return getFieldByClass(AllPermissionsField.class);
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

			@Override
			protected int getConfiguredGridColumnCount() {
				return 3;
			}

			@Order(1000)
			public class PermissionsField extends AbstractTableField<PermissionsField.Table> {

				@Override
				protected boolean getConfiguredLabelVisible() {
					return false;
				}

				@Override
				protected int getConfiguredGridH() {
					return 10;
				}

				@Override
				protected int getConfiguredGridW() {
					return 1;
				}

				public class Table extends AbstractTable {

					@Override
					protected void execContentChanged() {
						touch();
					}

					@Override
					protected Class<? extends IMenu> getConfiguredDefaultMenu() {
						return RemoveMenu.class;
					}

					@Override
					protected boolean getConfiguredAutoResizeColumns() {
						return true;
					}

					public PermissionsColumn getPermissionColumn() {
						return getColumnSet().getColumnByClass(PermissionsColumn.class);
					}

					@Order(1000)
					public class PermissionsColumn extends AbstractStringColumn {
						@Override
						protected String getConfiguredHeaderText() {
							return TEXTS.get("Permissions");
						}

						@Override
						protected int getConfiguredWidth() {
							return 350;
						}

						@Override
						protected int getConfiguredSortIndex() {
							return 0;
						}
					}

					@Order(1000)
					public class RemoveMenu extends AbstractMenu {
						@Override
						protected String getConfiguredText() {
							return TEXTS.get("Remove0");
						}

						@Override
						protected String getConfiguredIconId() {
							return Icons.Minus;
						}

						@Override
						protected Set<? extends IMenuType> getConfiguredMenuTypes() {
							return CollectionUtility.hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
						}

						@Override
						protected void execAction() {

							getAllPermissionsField().getTable().addRows(
									getPermissionsField().getTable().getSelectedRows(),
									true);
							getPermissionsField().getTable().deleteRows(
									getPermissionsField().getTable().getSelectedRows());
							getPermissionsField().getTable().discardAllDeletedRows();

						}
					}

					@Order(2000)
					public class RemoveAllMenu extends AbstractMenu {
						@Override
						protected String getConfiguredText() {
							return TEXTS.get("RemoveAll");
						}

						@Override
						protected String getConfiguredIconId() {
							return Icons.List;
						}

						@Override
						protected Set<? extends IMenuType> getConfiguredMenuTypes() {
							return CollectionUtility.hashSet(TableMenuType.EmptySpace);
						}

						@Override
						protected void execAction() {

							getAllPermissionsField().getTable().addRows(
									getPermissionsField().getTable().getRows(),
									true);
							getPermissionsField().getTable().deleteRows(
									getPermissionsField().getTable().getRows());
							getPermissionsField().getTable().discardAllDeletedRows();

						}

					}

				}

			}

			@Order(2000)
			public class AllPermissionsField extends AbstractTableField<AllPermissionsField.Table> {

				@Override
				protected boolean getConfiguredLabelVisible() {
					return false;
				}

				@Override
				protected int getConfiguredGridH() {
					return 10;
				}

				@Override
				protected int getConfiguredGridW() {
					return 1;
				}

				public class Table extends AbstractTable {

					@Override
					protected void execContentChanged() {
						touch();
					}

					@Override
					protected Class<? extends IMenu> getConfiguredDefaultMenu() {
						return AddMenu.class;
					}

					@Override
					protected boolean getConfiguredAutoResizeColumns() {
						return true;
					}

					public PermissionColumn getPermissionColumn() {
						return getColumnSet().getColumnByClass(PermissionColumn.class);
					}

					@Order(1000)
					public class PermissionColumn extends AbstractStringColumn {
						@Override
						protected String getConfiguredHeaderText() {
							return TEXTS.get("AvailablePermission");
						}

						@Override
						protected int getConfiguredWidth() {
							return 350;
						}

						@Override
						protected int getConfiguredSortIndex() {
							return 0;
						}
					}

					@Order(1000)
					public class AddMenu extends AbstractMenu {
						@Override
						protected String getConfiguredText() {
							return TEXTS.get("Add");
						}

						@Override
						protected String getConfiguredIconId() {
							return Icons.Plus;
						}

						@Override
						protected Set<? extends IMenuType> getConfiguredMenuTypes() {
							return CollectionUtility.hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
						}

						@Override
						protected void execAction() {

							getPermissionsField().getTable().addRows(
									getAllPermissionsField().getTable().getSelectedRows(),
									true);
							getAllPermissionsField().getTable().deleteRows(
									getAllPermissionsField().getTable().getSelectedRows());
							getAllPermissionsField().getTable().discardAllDeletedRows();

						}
					}

					@Order(2000)
					public class AddAllMenu extends AbstractMenu {
						@Override
						protected String getConfiguredText() {
							return TEXTS.get("AddAll");
						}

						@Override
						protected String getConfiguredIconId() {
							return Icons.List;
						}

						@Override
						protected Set<? extends IMenuType> getConfiguredMenuTypes() {
							return CollectionUtility.hashSet(TableMenuType.EmptySpace);
						}

						@Override
						protected void execAction() {

							getPermissionsField().getTable().addRows(
									getAllPermissionsField().getTable().getRows(),
									true);
							getAllPermissionsField().getTable().deleteRows(
									getAllPermissionsField().getTable().getRows());
							getAllPermissionsField().getTable().discardAllDeletedRows();
						}
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
			IRolePermissionService service = BEANS.get(IRolePermissionService.class);
			RolePermissionFormData formData = new RolePermissionFormData();
			exportFormData(formData);
			formData = service.load(formData);
			importFormData(formData);

			setEnabledPermission(new UpdateRolePermissionPermission());
		}

		@Override
		protected void execPostLoad() {
			ArrayList<String> allPermissions = new ArrayList<String>();
			Set<Class<? extends Permission>> permissions = BEANS.get(IPermissionService.class).getAllPermissionClasses();
			for (Class<? extends Permission> permission : permissions) {
				allPermissions.add(permission.getSimpleName());
			}

			// Remove not functional permissions
			allPermissions.remove("RemoteServiceAccessPermission");

			// Remove existing Roles from all permissions
			for (ITableRow row : getPermissionsField().getTable().getRows()) {
				if (allPermissions.contains((String) row.getCellValue(0))) {
					allPermissions.remove((String) row.getCellValue(0));
				}
			}

			getAllPermissionsField().getTable().addRowsByArray(allPermissions.toArray());
		}

		@Override
		protected void execStore() {
			IRolePermissionService service = BEANS.get(IRolePermissionService.class);
			RolePermissionFormData formData = new RolePermissionFormData();
			exportFormData(formData);
			service.store(formData);
		}
	}
}
