package kr.co.gitt.kworks.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.model.KwsUserAuthorHist;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import kr.co.gitt.kworks.cmmn.view.AbstractExcelView;

@Component("userAuthorHistoryExcelDownload")
public class UserAuthorHistoryExcelDownload extends AbstractExcelView {

	@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb, HttpServletRequest req, HttpServletResponse res) throws Exception {

		HSSFCell cell = null;

		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setDefaultColumnWidth((short) 15);
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		
		setText(getCell(sheet, 0, 0), "연번");
		setText(getCell(sheet, 0, 1), "소석");
		setText(getCell(sheet, 0, 2), "성명");
		setText(getCell(sheet, 0, 3), "접근권한");
		setText(getCell(sheet, 0, 4), "처리내용");
		setText(getCell(sheet, 0, 5), "처리일자");
		setText(getCell(sheet, 0, 6), "처리자");
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) model.get("categoryMap");
		@SuppressWarnings("unchecked")
		List<Object> categories = (List<Object>) map.get("category");

		@SuppressWarnings("unused")
		boolean isVO = false;
		
		if (categories.size() > 0) {
			Object obj = categories.get(0);
			isVO = obj instanceof KwsUserAuthorHist;
		}

		for(int i = 0; i < categories.size(); i++) {
			KwsUserAuthorHist category = (KwsUserAuthorHist) categories.get(i);
			
			cell = getCell(sheet, 1 + i, 0);
			cell.setCellValue(i + 1);
			
			cell = getCell(sheet, 1 + i, 1);
			cell.setCellValue(category.getDeptNm());

			cell = getCell(sheet, 1 + i, 2);
			cell.setCellValue(category.getUserNm());
			
			cell = getCell(sheet, 1 + i, 3);
			cell.setCellValue(category.getAuthorGroupNm());
			
			cell = getCell(sheet, 1 + i, 4);
			cell.setCellValue(category.getPrcrCn());
						
			cell = getCell(sheet, 1 + i, 5);
			cell.setCellValue(category.getGradAlwncDt());
			cell.setCellStyle(cellStyle);
			
			cell = getCell(sheet, 1 + i, 6);
			cell.setCellValue(category.getGradUserNm());
		}
		
		res.setContentType("application/x-msdownload");
		res.setHeader("Content-Disposition", "attachment; filename=\'authorLog.xls\'");
	}

}