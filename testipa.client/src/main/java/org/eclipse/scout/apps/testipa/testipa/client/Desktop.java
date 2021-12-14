package org.eclipse.scout.apps.testipa.testipa.client;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.scout.apps.testipa.testipa.client.Desktop.UserProfileMenu.ThemeMenu.DarkThemeMenu;
import org.eclipse.scout.apps.testipa.testipa.client.Desktop.UserProfileMenu.ThemeMenu.DefaultThemeMenu;
import org.eclipse.scout.apps.testipa.testipa.client.outline.ErrorHandlerOutline;
import org.eclipse.scout.apps.testipa.testipa.client.outline.LogHandlerOutline;
import org.eclipse.scout.apps.testipa.testipa.client.settings.SettingsOutline;
import org.eclipse.scout.apps.testipa.testipa.client.user.ChangePasswordForm;
import org.eclipse.scout.apps.testipa.testipa.client.user.UserForm;
import org.eclipse.scout.apps.testipa.testipa.shared.Icons;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;

/**
 * @author 12767
 */
public class Desktop extends AbstractDesktop {

	public Desktop() {
		addPropertyChangeListener(PROP_THEME, this::onThemeChanged);
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("LogErrorHelper");
	}

	@Override
	protected String getConfiguredLogoId() {
		return Icons.AppLogo;
	}

	@Override
	protected List<Class<? extends IOutline>> getConfiguredOutlines() {
		return CollectionUtility.<Class<? extends IOutline>>arrayList(LogHandlerOutline.class,
				ErrorHandlerOutline.class,
				SettingsOutline.class);
	}

	@Override
	protected void execDefaultView() {
		setOutline(LogHandlerOutline.class);
	}

	protected void selectFirstVisibleOutline() {
		for (IOutline outline : getAvailableOutlines()) {
			if (outline.isEnabled() && outline.isVisible()) {
				setOutline(outline.getClass());
				return;
			}
		}
	}

	protected void onThemeChanged(PropertyChangeEvent evt) {
		IMenu darkMenu = getMenuByClass(DarkThemeMenu.class);
		IMenu defaultMenu = getMenuByClass(DefaultThemeMenu.class);
		String newThemeName = (String) evt.getNewValue();
		if (DarkThemeMenu.DARK_THEME.equalsIgnoreCase(newThemeName)) {
			darkMenu.setIconId(Icons.CheckedBold);
			defaultMenu.setIconId(null);
		}
		else {
			darkMenu.setIconId(null);
			defaultMenu.setIconId(Icons.CheckedBold);
		}
	}

	@Order(1000)
	public class UserProfileMenu extends AbstractMenu {

		@Override
		protected String getConfiguredKeyStroke() {
			return IKeyStroke.F10;
		}

		@Override
		protected String getConfiguredIconId() {
			return Icons.PersonSolid;
		}

		@Override
		protected String getConfiguredText() {
			return StringUtility.uppercaseFirst(ClientSession.get().getUserName());
		}

		@Order(1000)
		public class AboutMenu extends AbstractMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("About");
			}

			@Override
			protected void execAction() {
				ScoutInfoForm form = new ScoutInfoForm();
				form.startModify();
			}
		}

		@Order(2000)
		public class ThemeMenu extends AbstractMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Theme");
			}

			@Order(1000)
			public class DefaultThemeMenu extends AbstractMenu {

				private static final String DEFAULT_THEME = "Default";

				@Override
				protected String getConfiguredText() {
					return DEFAULT_THEME;
				}

				@Override
				protected void execAction() {
					setTheme(DEFAULT_THEME.toLowerCase());
				}
			}

			@Order(2000)
			public class DarkThemeMenu extends AbstractMenu {

				private static final String DARK_THEME = "Dark";

				@Override
				protected String getConfiguredText() {
					return DARK_THEME;
				}

				@Override
				protected void execAction() {
					setTheme(DARK_THEME.toLowerCase());
				}
			}
		}

		@Order(2200)
		public class ChangePasswordMenu extends AbstractMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("ChangePassword");
			}

			@Override
			protected void execAction() {
				ChangePasswordForm form = new ChangePasswordForm();
				form.startModify();
			}
		}

		@Order(2400)
		public class AdjustUserMenu extends AbstractMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("AdjustUser");
			}

			@Override
			protected void execAction() {
				UserForm form = new UserForm();
				form.setUserNr(ClientSession.get().getUserNr());
				form.startUser();
			}
		}

		@Order(3000)
		public class LogoutMenu extends AbstractMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Logout");
			}

			@Override
			protected void execAction() {
				ClientSessionProvider.currentSession().stop();
			}
		}
	}

	@Order(1000)
	public class LogOutlineViewButton extends AbstractOutlineViewButton {

		public LogOutlineViewButton() {
			this(LogHandlerOutline.class);
		}

		protected LogOutlineViewButton(Class<? extends LogHandlerOutline> outlineClass) {
			super(Desktop.this, outlineClass);
		}

		@Override
		protected DisplayStyle getConfiguredDisplayStyle() {
			return DisplayStyle.TAB;
		}

	}

	@Order(2000)
	public class ErrorOutlineViewButton extends AbstractOutlineViewButton {

		public ErrorOutlineViewButton() {
			this(ErrorHandlerOutline.class);
		}

		protected ErrorOutlineViewButton(Class<? extends ErrorHandlerOutline> outlineClass) {
			super(Desktop.this, outlineClass);
		}

		@Override
		protected DisplayStyle getConfiguredDisplayStyle() {
			return DisplayStyle.TAB;
		}

	}

	@Order(3000)
	public class SettingsOutlineViewButton extends AbstractOutlineViewButton {

		public SettingsOutlineViewButton() {
			this(SettingsOutline.class);
		}

		protected SettingsOutlineViewButton(Class<? extends SettingsOutline> outlineClass) {
			super(Desktop.this, outlineClass);
		}

		@Override
		protected DisplayStyle getConfiguredDisplayStyle() {
			return DisplayStyle.TAB;
		}

		@Override
		protected String getConfiguredKeyStroke() {
			return IKeyStroke.F10;
		}
	}
}
