package org.eclipse.scout.apps.testipa.testipa.client.code;

import org.eclipse.scout.apps.testipa.testipa.client.abstractobject.column.AbstractIdColumn;
import org.eclipse.scout.apps.testipa.testipa.client.abstractobject.menu.AbstractEditMenu;
import org.eclipse.scout.apps.testipa.testipa.client.abstractobject.menu.AbstractNewMenu;
import org.eclipse.scout.apps.testipa.testipa.client.code.CodeTablePage.Table;
import org.eclipse.scout.apps.testipa.testipa.shared.code.CodeTablePageData;
import org.eclipse.scout.apps.testipa.testipa.shared.code.ICodeService;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(CodeTablePageData.class)
public class CodeTablePage extends AbstractPageWithTable<Table> {

	private String title;
	private Long codeTypeId;

	public Long getCodeTypeId() {
		return codeTypeId;
	}

	public void setCodeTypeId(Long codeTypeId) {
		this.codeTypeId = codeTypeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	protected boolean getConfiguredLeaf() {
		return true;
	}

	@Override
	protected void execLoadData(SearchFilter filter) {
		importPageData(BEANS.get(ICodeService.class).getCodeTableData(filter, getCodeTypeId()));
	}

	@Override
	protected String getConfiguredTitle() {
		return getTitle();
	}

	public class Table extends AbstractTable {

		public DescriptionColumn getDescriptionColumn() {
			return getColumnSet().getColumnByClass(DescriptionColumn.class);
		}

		public UcNoColumn getUcNoColumn() {
			return getColumnSet().getColumnByClass(UcNoColumn.class);
		}

		public CodeNameColumn getCodeNameColumn() {
			return getColumnSet().getColumnByClass(CodeNameColumn.class);
		}

		public CodeIdColumn getCodeIdColumn() {
			return getColumnSet().getColumnByClass(CodeIdColumn.class);
		}

		@Order(1000)
		public class CodeIdColumn extends AbstractIdColumn {
		}

		@Order(2000)
		public class CodeNameColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Name0");
			}

			@Override
			protected int getConfiguredWidth() {
				return 250;
			}
		}

		@Order(3000)
		public class UcNoColumn extends AbstractBigDecimalColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("SortingNumber");
			}

			@Override
			protected int getConfiguredWidth() {
				return 150;
			}

			@Override
			protected boolean getConfiguredVisible() {
				return false;
			}
		}

		@Order(4000)
		public class DescriptionColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Description");
			}

			@Override
			protected int getConfiguredWidth() {
				return 300;
			}
		}

		@Order(10000)
		public class NewMenu extends AbstractNewMenu {

			@Override
			protected void execAction() {
				CodeForm form = new CodeForm();
				form.setCodeTypeId(getCodeTypeId());
				form.startNew();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}

			}
		}

		@Order(11000)
		public class EditMenu extends AbstractEditMenu {

			@Override
			protected void execAction() {

				CodeForm form = new CodeForm();
				form.setCodeTypeId(getCodeTypeId());
				form.setCodeId(getCodeIdColumn().getSelectedValue());
				form.startModify();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

	}
}
