package org.eclipse.scout.apps.testipa.testipa.server.errorhandler;

import org.eclipse.scout.apps.testipa.testipa.server.helper.CommonDataHelper;
import org.eclipse.scout.apps.testipa.testipa.shared.errorhandler.CreateErrorHandlerPermission;
import org.eclipse.scout.apps.testipa.testipa.shared.errorhandler.ErrorHandlerFormData;
import org.eclipse.scout.apps.testipa.testipa.shared.errorhandler.ErrorHandlerSearchFormData;
import org.eclipse.scout.apps.testipa.testipa.shared.errorhandler.ErrorHandlerTablePageData;
import org.eclipse.scout.apps.testipa.testipa.shared.errorhandler.IErrorHandlerService;
import org.eclipse.scout.apps.testipa.testipa.shared.errorhandler.ReadErrorHandlerPermission;
import org.eclipse.scout.apps.testipa.testipa.shared.errorhandler.UpdateErrorHandlerPermission;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class ErrorHandlerService implements IErrorHandlerService {
	@Override
	public ErrorHandlerTablePageData getErrorHandlerTableData(SearchFilter filter) {
		ErrorHandlerTablePageData pageData = new ErrorHandlerTablePageData();
		StringBuilder sql = new StringBuilder();

		sql.append("" +
				"select 		id, " +
				"				project, " +
				"				error_type, " +
				"				error, " +
				"				solution " +
				"from			error_handling " +
				"where			1 = 1 ");

		ErrorHandlerSearchFormData searchData = (ErrorHandlerSearchFormData) filter.getFormData();

		if (searchData.getSearchErrorType().getValue() != null) {
			sql.append(" and upper (error_type) like '%' || upper(:searchErrorType) || '%' ");
		}

		if (searchData.getSearchErrorMessage().getValue() != null) {
			searchData.getSearchErrorMessage().setValue(CommonDataHelper.replaceStarWildcard(searchData.getSearchErrorMessage().getValue()));
			sql.append("and upper(error) like  upper(:searchErrorMessage) ");
		}

		if (searchData.getSearchProject().getValue() != null) {
			sql.append(" and upper (project) like '%' || upper(:searchProject) || '%' ");
		}

		sql.append("" +
				"into			:{page.errorId}, " +
				"				:{page.project}, " +
				"				:{page.errorType}, " +
				"				:{page.errorMessage}, " +
				"				:{page.solution} ");

		SQL.selectInto(sql.toString(),
				new NVPair("page", pageData),
				searchData);

		return pageData;
	}

	@Override
	public ErrorHandlerFormData prepareCreate(ErrorHandlerFormData formData) {
		if (!ACCESS.check(new CreateErrorHandlerPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		formData.getErrorId().setValue(CommonDataHelper.getNextVal());

		return formData;
	}

	@Override
	public ErrorHandlerFormData create(ErrorHandlerFormData formData) {
		if (!ACCESS.check(new CreateErrorHandlerPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		SQL.insert("" +
				"insert into	error_handling ( " +
				"				id, " +
				"				project, " +
				"				error_type, " +
				"				error, " +
				"				solution ) " +
				"values (		:errorId, " +
				"				:project, " +
				"				:errorType, " +
				"				:errorMessage, " +
				"				:solution ) ", formData);

		return formData;
	}

	@Override
	public ErrorHandlerFormData load(ErrorHandlerFormData formData) {
		if (!ACCESS.check(new ReadErrorHandlerPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		SQL.selectInto("" +
				"select			id, " +
				"				project, " +
				"				error_type, " +
				"				error, " +
				"				solution " +
				"from			error_handling " +
				"where			id = :errorId " +
				"into			:errorId, " +
				"				:project, " +
				"				:errorType, " +
				"				:errorMessage, " +
				"				:solution ", formData);

		return formData;
	}

	@Override
	public ErrorHandlerFormData store(ErrorHandlerFormData formData) {
		if (!ACCESS.check(new UpdateErrorHandlerPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		SQL.update("" +
				"update			error_handling " +
				"set			id = :errorId, " +
				"				project = :project, " +
				"				error_type = :errorType, " +
				"				error = :errorMessage, " +
				"				solution = :solution " +
				"where			id = :errorId ", formData);

		return formData;
	}
}
