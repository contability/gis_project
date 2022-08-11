package kr.co.gitt.kworks.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.EditHistoryResultDTO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import kr.co.gitt.kworks.cmmn.view.AbstractExcelView;

@Component("editHistoryExcelDownload")
public class EditHistoryExcelDownload extends AbstractExcelView {

	@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb, HttpServletRequest req, HttpServletResponse res) throws Exception {

		HSSFCell cell = null;

		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setDefaultColumnWidth((short) 15);
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		
		setText(getCell(sheet, 0, 0), "번호");
		setText(getCell(sheet, 0, 1), "서비스");
		setText(getCell(sheet, 0, 2), "레이어");
		setText(getCell(sheet, 0, 3), "편집종류");
		setText(getCell(sheet, 0, 4), "편집자");
		setText(getCell(sheet, 0, 5), "편집일시");
		setText(getCell(sheet, 0, 6), "위치");
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) model.get("categoryMap");
		@SuppressWarnings("unchecked")
		List<Object> categories = (List<Object>) map.get("category");

		@SuppressWarnings("unused")
		boolean isVO = false;
		
		if (categories.size() > 0) {
			Object obj = categories.get(0);
			isVO = obj instanceof EditHistoryResultDTO;
		}

		for(int i = 0; i < categories.size(); i++) {
			EditHistoryResultDTO category = (EditHistoryResultDTO) categories.get(i);
			
			cell = getCell(sheet, 1 + i, 0);
			cell.setCellValue(category.getHistNo());

			cell = getCell(sheet, 1 + i, 1);
			cell.setCellValue(category.getSysAlias());
			
			cell = getCell(sheet, 1 + i, 2);
			cell.setCellValue(category.getDataAlias());
			
			cell = getCell(sheet, 1 + i, 3);
			cell.setCellValue(category.getEditType().getValue());
			
			cell = getCell(sheet, 1 + i, 5);
			cell.setCellValue(category.getEditDt());
			cell.setCellStyle(cellStyle);
			
			cell = getCell(sheet, 1 + i, 6);
			cell.setCellValue(category.getBjdNam());
		}
		
		res.setContentType("application/x-msdownload");
		res.setHeader("Content-Disposition", "attachment; filename=\'export.xls\'");
	}

}