package org.eclipse.scout.apps.testipa.testipa.client.permission;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.scout.apps.testipa.testipa.shared.code.CodeLookupCall;
import org.eclipse.scout.apps.testipa.testipa.shared.permission.RolePermissionCodeType;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

public class RolePermissionNodePage extends AbstractPageWithNodes {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("AssignPermissionsToRoles");
	}

	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		super.execCreateChildPages(pageList);

		CodeLookupCall c = BEANS.get(CodeLookupCall.class);
		c.setCodeTypeId(RolePermissionCodeType.ID);

		List<? extends ILookupRow<Long>> roles = (List<? extends ILookupRow<Long>>) c.getDataByAll();

		Collections.sort(roles, new Comparator<ILookupRow<Long>>() {

			@Override
			public int compare(ILookupRow<Long> row1, ILookupRow<Long> row2) {
				return row1.getText()
						.compareTo(row2.getText());
			}
		});

		for (Iterator<? extends ILookupRow<Long>> iterator = roles.iterator(); iterator.hasNext();) {
			ILookupRow<Long> role = iterator.next();

			RolePermissionTablePage tablePage = new RolePermissionTablePage();
			tablePage.setRoleUid(role.getKey());
			tablePage.setTitle(role.getText());
			pageList.add(tablePage);
		}
	}
}
