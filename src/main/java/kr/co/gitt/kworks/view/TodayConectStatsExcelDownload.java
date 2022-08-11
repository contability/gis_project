package kr.co.gitt.kworks.view;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import kr.co.gitt.kworks.cmmn.view.AbstractExcelView;

@Component("todayConectStatsExcelDownload")
public class TodayConectStatsExcelDownload extends AbstractExcelView {

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
		HashMap<String, Integer> categories = (HashMap<String, Integer>) map.get("category");
		
		setText(getCell(sheet, 0, 0), "접속시간");
		setText(getCell(sheet, 0, 1), "통합시스템 접속자수");
		setText(getCell(sheet, 0, 2), "누적 접속자수");
		
		for(int i=0; i < 23; i++) {
			StringBuffer hours = new StringBuffer();
			hours.append(i).append(":00 ~ ").append(i+1).append(":00");
			setText(getCell(sheet, (i+1), 0), hours.toString());
			
			String hKey = "h_" + StringUtils.leftPad(String.valueOf(i), 2, "0");
			String tKey = "t_" + StringUtils.leftPad(String.valueOf(i), 2, "0");
			
			cell = getCell(sheet, (i+1), 1);
			cell.setCellValue(categories.get(hKey));

			cell = getCell(sheet, (i+1), 2);
			cell.setCellValue(categories.get(tKey));
		}
		
		res.setContentType("application/x-msdownload");
		res.setHeader("Content-Disposition", "attachment; filename=\'export.xls\'");
	}

}