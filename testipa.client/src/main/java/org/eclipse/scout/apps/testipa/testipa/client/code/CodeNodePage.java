package org.eclipse.scout.apps.testipa.testipa.client.code;

import java.util.List;

import org.eclipse.scout.apps.testipa.testipa.shared.permission.RolePermissionCodeType;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class CodeNodePage extends AbstractPageWithNodes {
	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		super.execCreateChildPages(pageList);

		CodeTablePage rolePermissionTypePage = new CodeTablePage();
		rolePermissionTypePage.setCodeTypeId(RolePermissionCodeType.ID);
		rolePermissionTypePage.setTitle(TEXTS.get("CreateRole"));
		pageList.add(rolePermissionTypePage);
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Code");
	}

}
