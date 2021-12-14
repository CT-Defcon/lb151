package org.eclipse.scout.apps.testipa.testipa.shared.code;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ICodeService extends IService {
	CodeFormData prepareCreate(CodeFormData formData);

	CodeFormData create(CodeFormData formData);

	CodeFormData load(CodeFormData formData);

	CodeFormData store(CodeFormData formData);

	CodeTablePageData getCodeTableData(SearchFilter filter, Long CodeTypeId);
}
