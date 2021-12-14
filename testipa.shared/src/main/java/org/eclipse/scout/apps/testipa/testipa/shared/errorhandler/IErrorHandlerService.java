package org.eclipse.scout.apps.testipa.testipa.shared.errorhandler;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IErrorHandlerService extends IService {
	ErrorHandlerTablePageData getErrorHandlerTableData(SearchFilter filter);

	ErrorHandlerFormData prepareCreate(ErrorHandlerFormData formData);

	ErrorHandlerFormData create(ErrorHandlerFormData formData);

	ErrorHandlerFormData load(ErrorHandlerFormData formData);

	ErrorHandlerFormData store(ErrorHandlerFormData formData);
}
