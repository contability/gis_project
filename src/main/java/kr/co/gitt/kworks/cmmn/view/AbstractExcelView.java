package kr.co.gitt.kworks.cmmn.view;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public abstract class AbstractExcelView extends org.springframework.web.servlet.view.document.AbstractExcelView {

	protected HSSFCell getCell(HSSFSheet sheet, int row, int col) {
		HSSFRow sheetRow = sheet.getRow(row);
		if (sheetRow == null) {
			sheetRow = sheet.createRow(row);
		}
		HSSFCell cell = sheetRow.getCell((int) col);
		if (cell == null) {
			cell = sheetRow.createCell((int) col);
		}
		return cell;
	}
	
}
