package org.eclipse.scout.apps.testipa.testipa.shared.permission;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IRolePermissionService extends IService {
	RolePermissionTablePageData getPermissionTableData(SearchFilter filter, Long userId, Long roleUid);

	RolePermissionFormData load(RolePermissionFormData formData);

	RolePermissionFormData store(RolePermissionFormData formData);
}
