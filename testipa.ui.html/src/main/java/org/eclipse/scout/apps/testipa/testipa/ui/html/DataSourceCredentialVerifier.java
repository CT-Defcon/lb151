package org.eclipse.scout.apps.testipa.testipa.ui.html;

import java.io.IOException;
import java.util.concurrent.Callable;

import javax.security.auth.Subject;

import org.eclipse.scout.apps.testipa.testipa.shared.user.IUserService;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.security.ICredentialVerifier;
import org.eclipse.scout.rt.platform.security.SimplePrincipal;

public class DataSourceCredentialVerifier implements ICredentialVerifier {

	@Override
	public int verify(String username, char[] password) throws IOException {
		final Subject subject = new Subject();
		subject.getPrincipals().add(new SimplePrincipal("access-check-user"));
		subject.setReadOnly();
		String passwordString = String.valueOf(password);

		Integer status = ClientRunContexts.copyCurrent(true)
				.withSubject(subject)
				.call(new Callable<Integer>() {
					@Override
					public Integer call() throws Exception {

						if (BEANS.get(IUserService.class).login(username, passwordString)) {
							return ICredentialVerifier.AUTH_OK;
						}
						return ICredentialVerifier.AUTH_FAILED;
					}
				});

		return status;
	}
}
