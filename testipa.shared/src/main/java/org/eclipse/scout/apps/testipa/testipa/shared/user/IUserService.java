package org.eclipse.scout.apps.testipa.testipa.shared.user;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IUserService extends IService {
	UserFormData prepareCreate(UserFormData formData);

	UserFormData create(UserFormData formData);

	UserFormData loadUserByToken(String token, boolean checkPermission);

	UserFormData load(UserFormData formData, boolean checkPermission);

	UserFormData store(UserFormData formData);

	UserFormData storeUser(UserFormData formData);

	public void delete(Long userNr);

	boolean login(String username, String password);

	UserTablePageData getUserTableData(SearchFilter filter);

	void changePassword(ChangePasswordFormData formData);

}
