package kr.co.gitt.kworks.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import kr.co.gitt.kworks.cmmn.view.AbstractExcelView;

@Component("weekConectStatsExcelDownload")
public class WeekConectStatsExcelDownload extends AbstractExcelView {

	@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb, HttpServletRequest req, HttpServletResponse res) throws Exception {

		HSSFCell cell = null;

		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setDefaultColumnWidth((short) 15);
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) model.get("categoryMap");
		@SuppressWarnings("unchecked")
		ArrayList<HashMap<String, String>> categories = (ArrayList<HashMap<String, String>>) map.get("category");
		
		setText(getCell(sheet, 0, 0), "접속요일");
		setText(getCell(sheet, 0, 1), "요일별 접속자 수");
		setText(getCell(sheet, 0, 2), "요일별 누적 접속자 수");
		
		for(int i=0; i < categories.size(); i++ ) {
			cell = getCell(sheet, (i+1), 0);
			cell.setCellValue(categories.get(i).get("week").toString());
			
			cell = getCell(sheet, (i+1), 1);
			cell.setCellValue(categories.get(i).get("count").toString());
			
			cell = getCell(sheet, (i+1), 2);
			cell.setCellValue(categories.get(i).get("totalCount").toString());
		}
		
		res.setContentType("application/x-msdownload");
		res.setHeader("Content-Disposition", "attachment; filename=\'export.xls\'");
	}

}