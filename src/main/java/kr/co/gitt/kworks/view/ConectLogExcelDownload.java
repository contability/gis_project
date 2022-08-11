package kr.co.gitt.kworks.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.model.KwsConectLog;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import kr.co.gitt.kworks.cmmn.view.AbstractExcelView;

@Component("conectLogExcelDownload")
public class ConectLogExcelDownload extends AbstractExcelView {

	@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb, HttpServletRequest req, HttpServletResponse res) throws Exception {

		HSSFCell cell = null;

		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setDefaultColumnWidth((short) 15);
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		
		setText(getCell(sheet, 0, 0), "사용자");
		setText(getCell(sheet, 0, 1), "접속일자");
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) model.get("categoryMap");
		@SuppressWarnings("unchecked")
		List<Object> categories = (List<Object>) map.get("category");

		@SuppressWarnings("unused")
		boolean isVO = false;
		
		if (categories.size() > 0) {
			Object obj = categories.get(0);
			isVO = obj instanceof KwsConectLog;
		}

		for(int i = 0; i < categories.size(); i++) {
			KwsConectLog category = (KwsConectLog) categories.get(i);
			
			cell = getCell(sheet, 1 + i, 0);
			cell.setCellValue(category.getUserNm());

			cell = getCell(sheet, 1 + i, 1);
			cell.setCellValue(category.getLoginDt());
			cell.setCellStyle(cellStyle);
		}
		
		res.setContentType("application/x-msdownload");
		res.setHeader("Content-Disposition", "attachment; filename=\'export.xls\'");
	}

}