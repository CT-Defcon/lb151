package org.eclipse.scout.apps.testipa.testipa.server.user;

import org.eclipse.scout.apps.testipa.testipa.server.ServerSession;
import org.eclipse.scout.apps.testipa.testipa.server.helper.CommonDataHelper;
import org.eclipse.scout.apps.testipa.testipa.shared.user.ChangePasswordFormData;
import org.eclipse.scout.apps.testipa.testipa.shared.user.DeleteUserPermission;
import org.eclipse.scout.apps.testipa.testipa.shared.user.IUserService;
import org.eclipse.scout.apps.testipa.testipa.shared.user.ReadUserPermission;
import org.eclipse.scout.apps.testipa.testipa.shared.user.UpdateUserPermission;
import org.eclipse.scout.apps.testipa.testipa.shared.user.UserFormData;
import org.eclipse.scout.apps.testipa.testipa.shared.user.UserSearchFormData;
import org.eclipse.scout.apps.testipa.testipa.shared.user.UserTablePageData;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.BooleanHolder;
import org.eclipse.scout.rt.platform.holders.LongHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringHolder;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class UserService implements IUserService {

	@Override
	public UserTablePageData getUserTableData(SearchFilter filter) {
		UserTablePageData pageData = new UserTablePageData();
		StringBuilder sql = new StringBuilder();

		sql.append("" +
				"select 		user_nr, " +
				"       		user_name, " +
				"       		login, " +
				"       		token, " +
				"       		email, " +
				"				local_user " +
				"from   		ipatest_user " +
				"where  		1 = 1 "
		);

		UserSearchFormData searchData = (UserSearchFormData) filter.getFormData();

		if (searchData.getName().getValue() != null) {
			sql.append(" and (upper(user_name) like upper(:name) || '%' or upper(login) like upper(:name) || '%' or upper(token) like upper(:name) || '%') ");
		}

		sql.append("" +
				"INTO   		:{page.userId}, " +
				"       		:{page.userName}, " +
				"       		:{page.logIn}, " +
				"       		:{page.token}, " +
				"       		:{page.email}, " +
				"				:{page.localUser} "
		);

		SQL.selectInto(sql.toString(), new NVPair("page", pageData), searchData);
		return pageData;
	}

	@Override
	public UserFormData prepareCreate(UserFormData formData) {
		formData.setUserNr(CommonDataHelper.getNextVal());
		return formData;
	}

	@Override
	public UserFormData create(UserFormData formData) {

		checkUnique(formData);

		SQL.insert("" +
				"insert 		into ipatest_user ( " +
				"        		user_nr, " +
				"        		user_name, " +
				"       		 password, " +
				"       		 token, " +
				"        		login, " +
				"        		email, " +
				"		 		local_user ) " +
				"VALUES (		:userNr, " +
				"        		:userName, " +
				"        		:password, " +
				"        		upper(:token), " +
				"        		upper(:logIn), " +
				"        		:email, " +
				"		 		:localUser ) ",
				formData
		);

		createRoles(formData);

		return formData;
	}

	@Override
	public UserFormData loadUserByToken(String token, boolean checkPermission) {
		if (checkPermission && !ACCESS.check(new ReadUserPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		UserFormData formData = new UserFormData();

		formData.getToken().setValue(token);

		SQL.selectInto("" +
				"select 		user_nr " +
				"from   		ipatest_user " +
				"where  		token = :token " +
				"into   		:userNr ",
				formData
		);

		return load(formData, checkPermission);
	}

	@Override
	public UserFormData load(UserFormData formData, boolean checkPermission) {
		if (checkPermission && !ACCESS.check(new ReadUserPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		SQL.selectInto("" +
				"select 		user_nr, " +
				"       		user_name, " +
				"       		token, " +
				"       		password, " +
				"      		 	login, " +
				"      		 	email, " +
				"				local_user " +
				"from   		ipatest_user " +
				"where  		user_nr = :userNr " +
				"into   		:userNr, " +
				"       		:userName, " +
				"       		:token, " +
				"       		:password, " +
				"       		:logIn, " +
				"       		:email, " +
				"				:localUser ",
				formData
		);

		SQL.select("" +
				"select 		role_uid " +
				"from   		ipatest_user_role " +
				"where  		user_nr = :userNr " +
				"into   		:{roles} ",
				formData);

		return formData;
	}

	@Override
	public UserFormData store(UserFormData formData) {
		if (!ACCESS.check(new UpdateUserPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		checkUnique(formData);

		SQL.update("" +
				"update 		ipatest_user " +
				"set    		user_name = :userName, " +
				"       		password = :password, " +
				"				local_user = :localUser, " +
				"       		token = upper(:token), " +
				"       		login = upper(:logIn), " +
				"       		email = :email " +
				"where  		user_nr = :userNr ",
				formData
		);

		SQL.delete("" +
				"delete from 	ipatest_user_role " +
				"where 			user_nr = " +
				formData.getUserNr());

		createRoles(formData);

		return formData;
	}

	@Override
	public UserFormData storeUser(UserFormData formData) {

		SQL.update("" +
				"update 		ipatest_user " +
				"set    		user_name = :userName, " +
				"       		email = :email " +
				"where  		user_nr = :userNr ",
				formData
		);

		return formData;
	}

	@Override
	public void delete(Long userNr) {
		if (!ACCESS.check(new DeleteUserPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		SQL.delete("" +
				"delete from 	ipatest_user " +
				"where 			user_nr = :userNr",
				new NVPair("userNr", userNr)
		);
	}

	public boolean dbLogin(String username, String password) {

		StringHolder dbPassword = new StringHolder();
		BooleanHolder localUser = new BooleanHolder();

		SQL.selectInto("" +
				"select 		password, " +
				" 				local_user " +
				"from 			ipatest_user " +
				"where 			upper(login) = upper(:username) " +
				"into 			:password, " +
				" 				:localUser ",
				new NVPair("username", username),
				new NVPair("password", dbPassword),
				new NVPair("localUser", localUser)
		);

		if (localUser.getValue() == null || !localUser.getValue()) {
			return false;
		}

		return ObjectUtility.equals(password, dbPassword.getValue());

	}

	@Override
	public boolean login(String username, String password) {
		return dbLogin(username, password);
	}

	@Override
	public void changePassword(ChangePasswordFormData formData) {

		if (!dbLogin(ServerSession.get().getUserId(), formData.getPassword().getValue())) {
			throw new VetoException(TEXTS.get("WrongPassword"));
		}

		StringHolder newPassword = new StringHolder(formData.getNewPassword().getValue());

		SQL.update("" +
				"update 		ipatest_user " +
				"set    		password = :newPassword " +
				"where  		user_nr = :userNr ",
				new NVPair("newPassword", newPassword)
		);
	}

	private void checkUnique(UserFormData formData) throws ProcessingException {

		LongHolder count = new LongHolder();
		SQL.selectInto("" +
				"select count(*) " +
				"from   		ipatest_user " +
				"where  		upper(login) = upper(:login) " +
				"and    		user_nr != :userNr " +
				"into   		:count ",
				new NVPair("login", formData.getLogIn()),
				new NVPair("userNr", formData.getUserNr()),
				new NVPair("count", count)
		);

		if (count.getValue() > 0) {
			throw new VetoException(TEXTS.get("UserAlreadyExists"));
		}

		SQL.selectInto("" +
				"select 		count(*) " +
				"from   		ipatest_user " +
				"where  		upper(token) = upper(:token) " +
				"and    		user_nr != :userNr " +
				"into   		:count ",
				new NVPair("token", formData.getToken()),
				new NVPair("userNr", formData.getUserNr()),
				new NVPair("count", count)
		);

		if (count.getValue() > 0) {
			throw new VetoException(TEXTS.get("TokenAlreadyExists"));
		}
	}

	private void createRoles(UserFormData formData) {

		SQL.insert("" +
				"insert into 	ipatest_user_role ( " +
				"        		user_nr, " +
				"        		role_uid ) " +
				"values 		(:userNr, " +
				"        		:{roles} )",
				formData);
	}
}
