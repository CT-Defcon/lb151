package org.eclipse.scout.apps.testipa.testipa.client.helper;

import java.util.concurrent.TimeUnit;

import org.eclipse.scout.apps.testipa.testipa.client.ClientSession;
import org.eclipse.scout.rt.client.ui.desktop.notification.DesktopNotification;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;

public class NotificationHelper {

	public static void showOk(String message) {
		IStatus status = new Status(message, IStatus.OK);
		DesktopNotification notification = new DesktopNotification(status, TimeUnit.SECONDS.toMillis(2), true);
		ClientSession.get().getDesktop().addNotification(notification);
	}

	public static void showInfo(String message) {
		IStatus status = new Status(message, IStatus.INFO);
		DesktopNotification notification = new DesktopNotification(status, TimeUnit.SECONDS.toMillis(2), true);
		ClientSession.get().getDesktop().addNotification(notification);
	}

	public static void showWarn(String message) {
		IStatus status = new Status(message, IStatus.WARNING);
		DesktopNotification notification = new DesktopNotification(status, TimeUnit.SECONDS.toMillis(3), true);
		ClientSession.get().getDesktop().addNotification(notification);
	}

	public static void showError(String message) {
		IStatus status = new Status(message, IStatus.ERROR);
		DesktopNotification notification = new DesktopNotification(status, TimeUnit.SECONDS.toMillis(3), true);
		ClientSession.get().getDesktop().addNotification(notification);
	}

}
