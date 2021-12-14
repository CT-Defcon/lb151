package org.eclipse.scout.apps.testipa.testipa.server.loghandler;

import org.eclipse.scout.apps.testipa.testipa.server.helper.CommonDataHelper;
import org.eclipse.scout.apps.testipa.testipa.shared.loghandler.CreateLogHandlerPermission;
import org.eclipse.scout.apps.testipa.testipa.shared.loghandler.ILogHandlerService;
import org.eclipse.scout.apps.testipa.testipa.shared.loghandler.LogHandlerFormData;
import org.eclipse.scout.apps.testipa.testipa.shared.loghandler.LogHandlerSearchFormData;
import org.eclipse.scout.apps.testipa.testipa.shared.loghandler.LogHandlerTablePageData;
import org.eclipse.scout.apps.testipa.testipa.shared.loghandler.ReadLogHandlerPermission;
import org.eclipse.scout.apps.testipa.testipa.shared.loghandler.UpdateLogHandlerPermission;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.BooleanHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class LogHandlerService implements ILogHandlerService {
	@Override
	public LogHandlerTablePageData getLogHandlerTableData(SearchFilter filter) {
		LogHandlerTablePageData pageData = new LogHandlerTablePageData();
		StringBuilder sql = new StringBuilder();

		sql.append("" +
				"select			id, " +
				"				timestamp, " +
				"				project, " +
				"				server, " +
				"				message, " +
				"				checkbox " +
				"from			logs " +
				"where			1 = 1 ");

		LogHandlerSearchFormData searchData = (LogHandlerSearchFormData) filter.getFormData();

		if (searchData.getSearchProject().getValue() != null) {
			sql.append(" and upper (project) like '%' || upper(:searchProject) || '%' ");
		}

		if (searchData.getSearchServer().getValue() != null) {
			sql.append(" and upper (server) like '%' || upper(:searchServer) || '%' ");
		}

		if (searchData.getSearchLogMessage().getValue() != null) {
			sql.append(" and upper (message) like '%' || upper(:searchLogMessage) || '%' ");
		}

		if (searchData.getSearchCheckBox().getValue() != null) {
			if (searchData.getSearchCheckBox().getValue()) {
				sql.append(" and checkbox = :searchCheckBox ");
			}
			else {
				sql.append(" and (checkbox is null or checkbox = :searchCheckBox) ");
			}

		}

		sql.append("" +
				"into 		:{page.logId}, " +
				" 			:{page.timeStamp}, " +
				" 			:{page.project}, " +
				" 			:{page.server}, " +
				" 			:{page.logMessage}, " +
				" 			:{page.checkBox} ");

		SQL.selectInto(sql.toString(),
				new NVPair("page", pageData),
				searchData);

		return pageData;
	}

	@Override
	public LogHandlerFormData prepareCreate(LogHandlerFormData formData) {
		if (!ACCESS.check(new CreateLogHandlerPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		formData.getLogId().setValue(CommonDataHelper.getNextVal());

		return formData;
	}

	@Override
	public LogHandlerFormData create(LogHandlerFormData formData) {
		if (!ACCESS.check(new CreateLogHandlerPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		// TODO Not yet implemented. Problem for future me!

		return formData;
	}

	@Override
	public LogHandlerFormData load(LogHandlerFormData formData) {
		if (!ACCESS.check(new ReadLogHandlerPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		SQL.selectInto("" +
				"select			id, " +
				"				timestamp, " +
				"				project, " +
				"				server, " +
				"				message, " +
				"				checkbox " +
				"from			logs " +
				"where			id = :logId " +
				"into			:logId, " +
				"				:timeStamp, " +
				"				:project, " +
				"				:server, " +
				"				:logMessage, " +
				"				:checkBox ", formData);

		return formData;
	}

	@Override
	public LogHandlerFormData store(LogHandlerFormData formData) {
		if (!ACCESS.check(new UpdateLogHandlerPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		SQL.update("" +
				"update			logs " +
				"set			checkbox = :checkBox " +
				"where			id = :logId ", formData);

		return formData;
	}

	@Override
	public void checkLogs(Long logId, boolean checked) {

		SQL.update("" +
				"update 	logs " +
				"set 		checkbox = :{checkBox} " +
				"where 		id = :{logId} ",
				new NVPair("logId", logId),
				new NVPair("checkBox", checked));

		SQL.commit();
	}

	@Override
	public boolean getCheckedState(Long logId) {

		BooleanHolder checked = new BooleanHolder();

		SQL.selectInto("" +
				"select 		coalesce(checkbox, false) " +
				"from   		logs " +
				"where  		id = :{logId} " +
				"into   		:{checked} ",
				new NVPair("logId", logId),
				new NVPair("checked", checked));

		return checked.getValue();
	}
}
