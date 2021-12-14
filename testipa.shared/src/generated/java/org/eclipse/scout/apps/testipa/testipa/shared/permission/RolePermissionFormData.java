package org.eclipse.scout.apps.testipa.testipa.shared.permission;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldBeanData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@Generated(value = "org.eclipse.scout.apps.testipa.testipa.client.permission.RolePermissionForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class RolePermissionFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public AllPermissions getAllPermissions() {
		return getFieldByClass(AllPermissions.class);
	}

	public Permissions getPermissions() {
		return getFieldByClass(Permissions.class);
	}

	/**
	 * access method for property RoleUid.
	 */
	public Long getRoleUid() {
		return getRoleUidProperty().getValue();
	}

	/**
	 * access method for property RoleUid.
	 */
	public void setRoleUid(Long roleUid) {
		getRoleUidProperty().setValue(roleUid);
	}

	public RoleUidProperty getRoleUidProperty() {
		return getPropertyByClass(RoleUidProperty.class);
	}

	public static class AllPermissions extends AbstractTableFieldBeanData {
		private static final long serialVersionUID = 1L;

		@Override
		public AllPermissionsRowData addRow() {
			return (AllPermissionsRowData) super.addRow();
		}

		@Override
		public AllPermissionsRowData addRow(int rowState) {
			return (AllPermissionsRowData) super.addRow(rowState);
		}

		@Override
		public AllPermissionsRowData createRow() {
			return new AllPermissionsRowData();
		}

		@Override
		public Class<? extends AbstractTableRowData> getRowType() {
			return AllPermissionsRowData.class;
		}

		@Override
		public AllPermissionsRowData[] getRows() {
			return (AllPermissionsRowData[]) super.getRows();
		}

		@Override
		public AllPermissionsRowData rowAt(int index) {
			return (AllPermissionsRowData) super.rowAt(index);
		}

		public void setRows(AllPermissionsRowData[] rows) {
			super.setRows(rows);
		}

		public static class AllPermissionsRowData extends AbstractTableRowData {
			private static final long serialVersionUID = 1L;
			public static final String permission = "permission";
			private String m_permission;

			public String getPermission() {
				return m_permission;
			}

			public void setPermission(String newPermission) {
				m_permission = newPermission;
			}
		}
	}

	public static class Permissions extends AbstractTableFieldBeanData {
		private static final long serialVersionUID = 1L;

		@Override
		public PermissionsRowData addRow() {
			return (PermissionsRowData) super.addRow();
		}

		@Override
		public PermissionsRowData addRow(int rowState) {
			return (PermissionsRowData) super.addRow(rowState);
		}

		@Override
		public PermissionsRowData createRow() {
			return new PermissionsRowData();
		}

		@Override
		public Class<? extends AbstractTableRowData> getRowType() {
			return PermissionsRowData.class;
		}

		@Override
		public PermissionsRowData[] getRows() {
			return (PermissionsRowData[]) super.getRows();
		}

		@Override
		public PermissionsRowData rowAt(int index) {
			return (PermissionsRowData) super.rowAt(index);
		}

		public void setRows(PermissionsRowData[] rows) {
			super.setRows(rows);
		}

		public static class PermissionsRowData extends AbstractTableRowData {
			private static final long serialVersionUID = 1L;
			public static final String permissions = "permissions";
			private String m_permissions;

			public String getPermissions() {
				return m_permissions;
			}

			public void setPermissions(String newPermissions) {
				m_permissions = newPermissions;
			}
		}
	}

	public static class RoleUidProperty extends AbstractPropertyData<Long> {
		private static final long serialVersionUID = 1L;
	}
}