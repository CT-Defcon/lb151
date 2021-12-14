package org.eclipse.scout.apps.testipa.testipa.server.sql;

import org.eclipse.scout.apps.testipa.testipa.server.sql.DatabaseProperties.JdbcMappingNameProperty;
import org.eclipse.scout.apps.testipa.testipa.server.sql.DatabaseProperties.JdbcPasswordProperty;
import org.eclipse.scout.apps.testipa.testipa.server.sql.DatabaseProperties.JdbcUsernameProperty;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.server.jdbc.postgresql.AbstractPostgreSqlService;

@Order(1950)
public class PostgresSqlService extends AbstractPostgreSqlService {

	@Override
	protected String getConfiguredJdbcMappingName() {
		return CONFIG.getPropertyValue(JdbcMappingNameProperty.class);
	}

	@Override
	protected String getConfiguredPassword() {
		return CONFIG.getPropertyValue(JdbcPasswordProperty.class);
	}

	@Override
	protected String getConfiguredUsername() {
		return CONFIG.getPropertyValue(JdbcUsernameProperty.class);
	}
}