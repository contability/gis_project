package kr.co.gitt.kworks.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.model.KwsError;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import kr.co.gitt.kworks.cmmn.view.AbstractExcelView;

@Component("errorLogExcelDownload")
public class ErrorLogExcelDownload extends AbstractExcelView {

	@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb, HttpServletRequest req, HttpServletResponse res) throws Exception {

		HSSFCell cell = null;

		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setDefaultColumnWidth((short) 15);
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		
		setText(getCell(sheet, 0, 0), "에러번호");
		setText(getCell(sheet, 0, 1), "에러메세지");
		setText(getCell(sheet, 0, 2), "스택");
		setText(getCell(sheet, 0, 3), "발생일자");
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) model.get("categoryMap");
		@SuppressWarnings("unchecked")
		List<Object> categories = (List<Object>) map.get("category");

		@SuppressWarnings("unused")
		boolean isVO = false;
		
		if (categories.size() > 0) {
			Object obj = categories.get(0);
			isVO = obj instanceof KwsError;
		}

		for(int i = 0; i < categories.size(); i++) {
			KwsError category = (KwsError) categories.get(i);
			
			cell = getCell(sheet, 1 + i, 0);
			cell.setCellValue(category.getErrorNo());

			cell = getCell(sheet, 1 + i, 1);
			cell.setCellValue(category.getErrorMssage());
						
			cell = getCell(sheet, 1 + i, 2);
			cell.setCellValue(category.getErrorTrace());
			
			cell = getCell(sheet, 1 + i, 3);
			cell.setCellValue(category.getErrorDt());
			cell.setCellStyle(cellStyle);
		}
		
		res.setContentType("application/x-msdownload");
		res.setHeader("Content-Disposition", "attachment; filename=\'export.xls\'");
	}
	
}
