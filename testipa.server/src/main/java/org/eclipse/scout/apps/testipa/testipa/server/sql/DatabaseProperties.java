package org.eclipse.scout.apps.testipa.testipa.server.sql;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.platform.config.AbstractBooleanConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractSubjectConfigProperty;

public class DatabaseProperties {

	public static class DatabaseAutoCreateProperty extends AbstractBooleanConfigProperty {

		@Override
		public Boolean getDefaultValue() {
			return Boolean.FALSE;
		}

		@Override
		public String getKey() {
			return "ipatest.database.autocreate";
		}

		@Override
		public String description() {
			return "Specifies if the ipatest database should automatically be created if it does not exist yet. The default value is 'false'.";
		}
	}

	public static class DatabaseAutoPopulateProperty extends AbstractBooleanConfigProperty {

		@Override
		public Boolean getDefaultValue() {
			return Boolean.TRUE;
		}

		@Override
		public String getKey() {
			return "ipatest.database.autopopulate";
		}

		@Override
		public String description() {
			return "Populating the Database with Testvalues at startup. The default value is 'true'.";
		}
	}

	public static class JdbcMappingNameProperty extends AbstractStringConfigProperty {

		@Override
		public String getDefaultValue() {
			return "jdbc:postgresql://localhost:5432/ipatest";
		}

		@Override
		public String getKey() {
			return "ipatest.database.jdbc.mapping.name";
		}

		@Override
		public String description() {
			return "JDBC string for Database connection configuration.";
		}
	}

	public static class JdbcUsernameProperty extends AbstractStringConfigProperty {

		@Override
		public String getDefaultValue() {
			return "postgres";
		}

		@Override
		public String getKey() {
			return "ipatest.database.jdbc.username";
		}
		// tag::structure[]

		@Override
		public String description() {
			return "Username for Database connection.";
		}
	}

	public static class JdbcPasswordProperty extends AbstractStringConfigProperty {

		@Override
		public String getDefaultValue() {
			return "admin";
		}

		@Override
		public String getKey() {
			return "ipatest.database.jdbc.password";
		}
		// tag::structure[]

		@Override
		public String description() {
			return "Password for Database connection.";
		}
	}

	public static class SuperUserSubjectProperty extends AbstractSubjectConfigProperty {

		@Override
		public Subject getDefaultValue() {
			return convertToSubject("postgres");
		}

		@Override
		public String getKey() {
			return "ipatest.superuser";
		}

		@Override
		public String description() {
			return "User for initial Database operations.";
		}
	}
}