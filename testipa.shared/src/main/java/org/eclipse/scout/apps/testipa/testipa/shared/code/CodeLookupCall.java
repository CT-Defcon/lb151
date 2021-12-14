package org.eclipse.scout.apps.testipa.testipa.shared.code;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class CodeLookupCall extends LookupCall<Long> {
	private static final long serialVersionUID = 1L;

	private Long codeTypeId;
	private boolean sortByUcNo = false;

	public boolean isSortByUcNo() {
		return sortByUcNo;
	}

	public void setSortByUcNo(boolean sortByUcNo) {
		this.sortByUcNo = sortByUcNo;
	}

	public Long getCodeTypeId() {
		return codeTypeId;
	}

	public void setCodeTypeId(Long codeTypeId) {
		this.codeTypeId = codeTypeId;
	}

	@Override
	protected Class<? extends ILookupService<Long>> getConfiguredService() {
		return ICodeLookupService.class;
	}
}
