package org.eclipse.scout.apps.testipa.testipa.client.settings;

import java.util.List;

import org.eclipse.scout.apps.testipa.testipa.client.code.CodeTablePage;
import org.eclipse.scout.apps.testipa.testipa.client.permission.RolePermissionNodePage;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserTablePage;
import org.eclipse.scout.apps.testipa.testipa.shared.Icons;
import org.eclipse.scout.apps.testipa.testipa.shared.permission.RolePermissionCodeType;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

/***
 * 
 * @author 12767
 */
@Order(3000)
public class SettingsOutline extends AbstractOutline {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Settings");
	}

	@Override
	protected String getConfiguredIconId() {
		return Icons.Gear;
	}

	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		pageList.add(new UserTablePage());

		CodeTablePage rolePermissionTypePage = new CodeTablePage();
		rolePermissionTypePage.setCodeTypeId(RolePermissionCodeType.ID);
		rolePermissionTypePage.setTitle(TEXTS.get("CreateRole"));
		pageList.add(rolePermissionTypePage);
		pageList.add(new RolePermissionNodePage());

	}
}
