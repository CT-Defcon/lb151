package org.eclipse.scout.apps.testipa.testipa.server.code;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.apps.testipa.testipa.shared.code.CodeLookupCall;
import org.eclipse.scout.apps.testipa.testipa.shared.code.ICodeLookupService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

public class CodeLookupService extends AbstractLookupService<Long> implements ICodeLookupService {

	@Override
	public List<? extends ILookupRow<Long>> getDataByKey(ILookupCall<Long> call) {

		CodeLookupCall c = (CodeLookupCall) call;

		String sql = computeSelect(c);

		sql += "and 		uc_uid = :key ";

		Object[][] data = SQL.select(
				sql,
				new NVPair("codeTypeId", c.getCodeTypeId()),
				new NVPair("key", c.getKey()));

		return convertData(data, c);
	}

	@Override
	public List<? extends ILookupRow<Long>> getDataByText(ILookupCall<Long> call) {

		CodeLookupCall c = (CodeLookupCall) call;
		String text = StringUtility.replace(c.getText(), c.getWildcard(), "%");
		String sql = computeSelect(c);

		sql += "and 		lower(code_name) like lower('%' || :text || '%') ";

		if (c.isSortByUcNo()) {
			sql += " 		order by uc_no ";
		}

		else {
			sql += " 		order by code_name ";
		}

		Object[][] data = SQL.select(
				sql.toString(),
				new NVPair("codeTypeId", c.getCodeTypeId()),
				new NVPair("text", text)
		);

		return convertData(data, c);
	}

	@Override
	public List<? extends ILookupRow<Long>> getDataByAll(ILookupCall<Long> call) {
		CodeLookupCall c = (CodeLookupCall) call;
		String sql = computeSelect(c);

		if (c.isSortByUcNo()) {
			sql += " 		order by uc_no ";
		}
		else {
			sql += " 		order by code_name ";
		}

		Object[][] data = SQL.select(sql,
				new NVPair("codeTypeId", c.getCodeTypeId())
		);

		return convertData(data, c);
	}

	@Override
	public List<? extends ILookupRow<Long>> getDataByRec(ILookupCall<Long> call) {
		return null;
	}

	private String computeSelect(CodeLookupCall call) {
		String sql = "" +
				"select 		uc_uid, " +
				" 				code_name " +
				"from   		ipatest_uc " +
				"where  		code_type = :codeTypeId ";

		return sql;
	}

	private List<? extends ILookupRow<Long>> convertData(Object[][] data, CodeLookupCall call) {
		List<LookupRow<Long>> retVal = new ArrayList<LookupRow<Long>>();
		for (int i = 0; i < data.length; i++) {
			LookupRow<Long> row = new LookupRow<Long>((Long) data[i][0], (String) data[i][1]);
			// row.withParentKey((Long) data[i][2]);
			retVal.add(row);
		}

		return retVal;
	}
}
