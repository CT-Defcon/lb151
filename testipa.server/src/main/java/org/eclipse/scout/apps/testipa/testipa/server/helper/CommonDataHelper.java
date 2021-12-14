package org.eclipse.scout.apps.testipa.testipa.server.helper;

import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.holders.LongHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class CommonDataHelper {

	public static Long getNextVal() throws ProcessingException {
		return getNextVal("ipatest_seq");
	}

	public static Long getNextVal(String seqName) throws ProcessingException {
		LongHolder nv = new LongHolder();
		SQL.selectInto("SELECT nextval('" + seqName + "') INTO :nv ", new NVPair("nv", nv));
		return nv.getValue();
	}

	public static String replaceStarWildcard(String value) {
		return value.replace("*", "%");
	}

}
