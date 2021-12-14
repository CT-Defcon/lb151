package org.eclipse.scout.apps.testipa.testipa.server.code;

import org.eclipse.scout.apps.testipa.testipa.server.helper.CommonDataHelper;
import org.eclipse.scout.apps.testipa.testipa.shared.code.CodeFormData;
import org.eclipse.scout.apps.testipa.testipa.shared.code.CodeTablePageData;
import org.eclipse.scout.apps.testipa.testipa.shared.code.CreateCodePermission;
import org.eclipse.scout.apps.testipa.testipa.shared.code.ICodeService;
import org.eclipse.scout.apps.testipa.testipa.shared.code.ReadCodePermission;
import org.eclipse.scout.apps.testipa.testipa.shared.code.UpdateCodePermission;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class CodeService implements ICodeService {

	@Override
	public CodeTablePageData getCodeTableData(SearchFilter filter, Long codeTypeId) {
		CodeTablePageData pageData = new CodeTablePageData();
		StringBuilder sql = new StringBuilder();

		sql.append("" +
				"select 	uc_uid, " +
				"			code_name, " +
				"			uc_no, " +
				"			description " +
				"from 		ipatest_uc " +
				"where		code_type = :codeTypeId " +
				"into 		:{page.codeId}, " +
				"   		:{page.codeName}, " +
				"   		:{page.ucNo}, " +
				"  			:{page.description} ");

		SQL.selectInto(
				sql.toString(),
				new NVPair("page", pageData),
				new NVPair("codeTypeId", codeTypeId)
		);

		return pageData;
	}

	@Override
	public CodeFormData prepareCreate(CodeFormData formData) {
		if (!ACCESS.check(new CreateCodePermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		formData.setCodeId(CommonDataHelper.getNextVal());

		return formData;
	}

	@Override
	public CodeFormData create(CodeFormData formData) {
		if (!ACCESS.check(new CreateCodePermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		SQL.insert("" +
				"insert 	into ipatest_uc ( " +
				"			uc_uid, " +
				"			description, " +
				"			code_name, " +
				"			code_type, " +
				"			uc_no ) " +
				"values		( " +
				"			:codeId, " +
				"			:description, " +
				"			:name, " +
				"			:codeTypeId, " +
				"			:ucNo ) ",
				formData);

		return formData;
	}

	@Override
	public CodeFormData load(CodeFormData formData) {
		if (!ACCESS.check(new ReadCodePermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		SQL.selectInto("" +
				"select 	code_name, " +
				"			description, " +
				"			uc_no " +
				"from 		ipatest_uc " +
				"where 		uc_uid = :codeId " +
				"into 		:name, " +
				"			:description, " +
				"			:UcNo ",
				formData);

		return formData;
	}

	@Override
	public CodeFormData store(CodeFormData formData) {
		if (!ACCESS.check(new UpdateCodePermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		SQL.update("" +
				"update 	ipatest_uc " +
				"set 		code_name = :name, " +
				"			description = :description, " +
				"			uc_no = :ucNo " +
				"where 		uc_uid = :codeId ",
				formData);

		return formData;
	}
}
