package org.eclipse.scout.apps.testipa.testipa.client.outline;

import java.util.List;

import org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerTablePage;
import org.eclipse.scout.apps.testipa.testipa.shared.Icons;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

/**
 * @author 12767
 */
@Order(1000)
public class LogHandlerOutline extends AbstractOutline {

	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		super.execCreateChildPages(pageList);
		pageList.add(new LogHandlerTablePage());
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Logs");
	}

	@Override
	protected String getConfiguredIconId() {
		return Icons.List;
	}
}
