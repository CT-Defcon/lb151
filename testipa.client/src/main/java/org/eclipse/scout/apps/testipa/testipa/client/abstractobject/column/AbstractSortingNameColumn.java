package org.eclipse.scout.apps.testipa.testipa.client.abstractobject.column;

import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.platform.text.TEXTS;

public abstract class AbstractSortingNameColumn extends AbstractStringColumn {

	@Override
	protected String getConfiguredHeaderText() {
		return TEXTS.get("Name");
	}

	@Override
	protected int getConfiguredWidth() {
		return 300;
	}

	@Override
	protected int getConfiguredSortIndex() {
		return 1;
	}

	@Override
	protected boolean getConfiguredSortAscending() {
		return true;
	}
}
