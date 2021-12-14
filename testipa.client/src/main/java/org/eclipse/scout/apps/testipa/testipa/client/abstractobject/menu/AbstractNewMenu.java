package org.eclipse.scout.apps.testipa.testipa.client.abstractobject.menu;

import java.util.Set;

import org.eclipse.scout.apps.testipa.testipa.shared.Icons;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

public abstract class AbstractNewMenu extends AbstractMenu {
	@Override
	protected String getConfiguredIconId() {
		return Icons.PlusBold;
	}

	@Override
	protected String getConfiguredKeyStroke() {
		return "alt-n";
	}

	@Override
	protected String getConfiguredText() {
		return TEXTS.get("New");
	}

	@Override
	protected Set<? extends IMenuType> getConfiguredMenuTypes() {
		return CollectionUtility.<IMenuType>hashSet(TableMenuType.EmptySpace);
	}
}
