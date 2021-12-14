package org.eclipse.scout.apps.testipa.testipa.shared.user;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadUserPermission extends AbstractPermission {
	private static final long serialVersionUID = 1L;

	public ReadUserPermission() {
		super("ReadUserPermission");
	}
}
