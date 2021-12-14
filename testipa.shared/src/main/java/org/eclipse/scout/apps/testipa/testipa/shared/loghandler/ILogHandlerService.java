package org.eclipse.scout.apps.testipa.testipa.shared.loghandler;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ILogHandlerService extends IService {
	LogHandlerTablePageData getLogHandlerTableData(SearchFilter filter);

	LogHandlerFormData prepareCreate(LogHandlerFormData formData);

	LogHandlerFormData create(LogHandlerFormData formData);

	LogHandlerFormData load(LogHandlerFormData formData);

	LogHandlerFormData store(LogHandlerFormData formData);

	void checkLogs(Long logId, boolean checked);

	boolean getCheckedState(Long logId);

}
