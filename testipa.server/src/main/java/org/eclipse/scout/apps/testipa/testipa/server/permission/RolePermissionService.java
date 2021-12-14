package org.eclipse.scout.apps.testipa.testipa.server.permission;

import org.eclipse.scout.apps.testipa.testipa.shared.permission.IRolePermissionService;
import org.eclipse.scout.apps.testipa.testipa.shared.permission.ReadRolePermissionPermission;
import org.eclipse.scout.apps.testipa.testipa.shared.permission.RolePermissionFormData;
import org.eclipse.scout.apps.testipa.testipa.shared.permission.RolePermissionTablePageData;
import org.eclipse.scout.apps.testipa.testipa.shared.permission.UpdateRolePermissionPermission;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.security.IAccessControlService;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class RolePermissionService implements IRolePermissionService {

	@Override
	public RolePermissionTablePageData getPermissionTableData(SearchFilter filter, Long userId, Long roleUid) {
		RolePermissionTablePageData pageData = new RolePermissionTablePageData();
		StringBuilder sql = new StringBuilder();

		// TODO CHECK IF WORK
		sql.append("" +
				"select 		rp.permission, " +
				"				get_ucname(rp.role_uid) " +
				"from   		ipatest_role_permission rp ");

		if (userId != null) {
			sql.append("" +
					"INNER JOIN ipatest_user_role ur " +
					"ON ur.role_uid = rp.role_uid ");
		}

		sql.append("WHERE 1 = 1 ");

		if (userId != null) {
			sql.append(String.format("AND ur.user_id = %d ", userId));
		}

		if (roleUid != null) {
			sql.append(String.format("AND rp.role_uid = %d ", roleUid));
		}

		sql.append("" +
				"into 				:{page.permission}, " +
				"     				:{page.role} ");

		SQL.selectInto(sql.toString(), new NVPair("page", pageData));

		return pageData;
	}

	@Override
	public RolePermissionFormData load(RolePermissionFormData formData) {
		if (!ACCESS.check(new ReadRolePermissionPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		SQL.selectInto("" +
				"select 			permission " +
				"from 				ipatest_role_permission " +
				"where				role_uid = :roleUid " +
				"into 				:{permissions.permissions} ",
				formData);

		return formData;
	}

	@Override
	public RolePermissionFormData store(RolePermissionFormData formData) {
		if (!ACCESS.check(new UpdateRolePermissionPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		SQL.delete("" +
				"delete from 		ipatest_role_permission where role_uid = " +
				formData.getRoleUid());

		SQL.insert("" +
				"insert into 		ipatest_role_permission ( " +
				"    				role_uid, " +
				"    				permission) " +
				"values ( " +
				"    				:roleUid, " +
				"    				:{permissions.permissions}) ",
				formData);

		BEANS.get(IAccessControlService.class).clearCache();

		return formData;
	}
}
