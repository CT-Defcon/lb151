package org.eclipse.scout.apps.testipa.testipa.server.security;

import java.security.Permission;
import java.util.HashMap;

import org.eclipse.scout.apps.testipa.testipa.shared.security.AccessControlService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Platform;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringArrayHolder;
import org.eclipse.scout.rt.security.AllPermissionCollection;
import org.eclipse.scout.rt.security.DefaultPermissionCollection;
import org.eclipse.scout.rt.security.IPermission;
import org.eclipse.scout.rt.security.IPermissionCollection;
import org.eclipse.scout.rt.security.PermissionLevel;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.security.RemoteServiceAccessPermission;
import org.eclipse.scout.rt.shared.services.common.security.IPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Replace
public class ServerAccessControlService extends AccessControlService {
	private static final Logger LOG = LoggerFactory.getLogger(ServerAccessControlService.class);

	@Override
	protected IPermissionCollection execLoadPermissions(String userId) {
		IPermissionCollection permissions = BEANS.get(DefaultPermissionCollection.class);

		if (userId.equals("access-check-user") || userId.equals("admin") || Platform.get().inDevelopmentMode()) {
			LOG.warn("All permisssions granted to: " + userId);
			permissions = BEANS.get(AllPermissionCollection.class);
		}
		else {

			StringArrayHolder userPermission = new StringArrayHolder();

			SQL.selectInto("" +
					"select distinct 		permission " +
					"from 					ipatest_role_permission rp " +
					"inner join 			ipatest_user_role ur on ur.role_uid = rp.role_uid " +
					"where 					user_nr = :userNr " +
					"into 					:permission ",
					new NVPair("permission", userPermission)
			);

			HashMap<String, String> map = new HashMap<String, String>();
			for (Class<? extends Permission> descriptor : BEANS.get(IPermissionService.class).getAllPermissionClasses()) {
				map.put(descriptor.getSimpleName(), descriptor.getName());
			}

			permissions.add(new RemoteServiceAccessPermission("*.shared.*", "*"), PermissionLevel.ALL);
			for (String perm : userPermission.getValue()) {
				try {
					permissions.add((IPermission) Class.forName(map.get(perm)).getDeclaredConstructor().newInstance());
				}
				catch (Exception e) {
					LOG.error("cannot find permission " + perm + ": " + e.getMessage());
				}
			}
		}

		return permissions;
	}
}
