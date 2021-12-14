package org.eclipse.scout.apps.testipa.testipa.client.abstractobject.column;

import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class AbstractIdColumn extends AbstractLongColumn {

	@Override
	protected String getConfiguredHeaderText() {
		return TEXTS.get("ID");
	}

	@Override
	protected boolean getConfiguredDisplayable() {
		return false;
	}

	@Override
	protected boolean getConfiguredVisible() {
		return false;
	}

	@Override
	protected boolean getConfiguredPrimaryKey() {
		return true;
	}

	@Override
	protected boolean getConfiguredAutoOptimizeWidth() {
		return true;
	}

	@Override
	protected boolean getConfiguredGroupingUsed() {
		return false;
	}

}
