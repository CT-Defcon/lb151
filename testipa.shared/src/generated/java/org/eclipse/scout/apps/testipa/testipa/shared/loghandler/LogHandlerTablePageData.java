package org.eclipse.scout.apps.testipa.testipa.shared.loghandler;

import java.util.Date;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@Generated(value = "org.eclipse.scout.apps.testipa.testipa.client.loghandler.LogHandlerTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class LogHandlerTablePageData extends AbstractTablePageData {
	private static final long serialVersionUID = 1L;

	@Override
	public LogHandlerTableRowData addRow() {
		return (LogHandlerTableRowData) super.addRow();
	}

	@Override
	public LogHandlerTableRowData addRow(int rowState) {
		return (LogHandlerTableRowData) super.addRow(rowState);
	}

	@Override
	public LogHandlerTableRowData createRow() {
		return new LogHandlerTableRowData();
	}

	@Override
	public Class<? extends AbstractTableRowData> getRowType() {
		return LogHandlerTableRowData.class;
	}

	@Override
	public LogHandlerTableRowData[] getRows() {
		return (LogHandlerTableRowData[]) super.getRows();
	}

	@Override
	public LogHandlerTableRowData rowAt(int index) {
		return (LogHandlerTableRowData) super.rowAt(index);
	}

	public void setRows(LogHandlerTableRowData[] rows) {
		super.setRows(rows);
	}

	public static class LogHandlerTableRowData extends AbstractTableRowData {
		private static final long serialVersionUID = 1L;
		public static final String logId = "logId";
		public static final String timeStamp = "timeStamp";
		public static final String project = "project";
		public static final String server = "server";
		public static final String logMessage = "logMessage";
		public static final String checkBox = "checkBox";
		private Long m_logId;
		private Date m_timeStamp;
		private String m_project;
		private String m_server;
		private String m_logMessage;
		private Boolean m_checkBox;

		public Long getLogId() {
			return m_logId;
		}

		public void setLogId(Long newLogId) {
			m_logId = newLogId;
		}

		public Date getTimeStamp() {
			return m_timeStamp;
		}

		public void setTimeStamp(Date newTimeStamp) {
			m_timeStamp = newTimeStamp;
		}

		public String getProject() {
			return m_project;
		}

		public void setProject(String newProject) {
			m_project = newProject;
		}

		public String getServer() {
			return m_server;
		}

		public void setServer(String newServer) {
			m_server = newServer;
		}

		public String getLogMessage() {
			return m_logMessage;
		}

		public void setLogMessage(String newLogMessage) {
			m_logMessage = newLogMessage;
		}

		public Boolean getCheckBox() {
			return m_checkBox;
		}

		public void setCheckBox(Boolean newCheckBox) {
			m_checkBox = newCheckBox;
		}
	}
}
