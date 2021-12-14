package org.eclipse.scout.apps.testipa.testipa.server;

import org.eclipse.scout.rt.platform.holders.LongHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringHolder;
import org.eclipse.scout.rt.server.AbstractServerSession;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.server.session.ServerSessionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 12767
 */
public class ServerSession extends AbstractServerSession {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ServerSession.class);

	public ServerSession() {
		super(true);
	}

	/**
	 * @return The {@link ServerSession} which is associated with the current
	 *         thread, or {@code null} if not found.
	 */
	public static ServerSession get() {
		return ServerSessionProvider.currentSession(ServerSession.class);
	}

	@Override
	protected void execLoadSession() {
		StringHolder userName = new StringHolder();
		LongHolder userNr = new LongHolder();

		SQL.select("" +
				"select 		user_nr, " +
				"    			user_name " +
				"from 			ipatest_user " +
				"where 			lower(login) = lower(:login) " +
				"into 			:userNr, " +
				"    			:userName ",
				new NVPair("login", getUserId()),
				new NVPair("userNr", userNr),
				new NVPair("userName", userName));

		setSharedContextVariable("userName", String.class, userName.getValue());
		setSharedContextVariable("userNr", Long.class, userNr.getValue());
		setSharedContextVariable("loggedInUserName", String.class, userName.getValue());

		LOG.info("created a new session for {}", getUserId());
	}

	public final Long getUserNr() {
		return getSharedContextVariable("userNr", Long.class);
	}

	public final String getUserName() {
		return getSharedContextVariable("userName", String.class);
	}
}
