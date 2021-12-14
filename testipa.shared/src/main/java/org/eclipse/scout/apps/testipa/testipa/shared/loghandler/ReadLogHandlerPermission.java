package org.eclipse.scout.apps.testipa.testipa.shared.loghandler;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadLogHandlerPermission extends AbstractPermission {
	private static final long serialVersionUID = 1L;

	public ReadLogHandlerPermission() {
		super("ReadLogHandlerPermission");
	}
}
