package org.eclipse.scout.apps.testipa.testipa.shared.permission;

import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class RolePermissionCodeType extends AbstractCodeType<Long, String> {

	private static final long serialVersionUID = 1L;
	public static final long ID = 20L;

	@Override
	public Long getId() {
		return ID;
	}

}
