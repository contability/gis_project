package kr.co.gitt.kworks.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import kr.co.gitt.kworks.model.KwsSys;

@Component("todayIndvdlzSysConectStatsExcelDownload")
public class TodayIndvdlzSysConectStatsExcelDownload extends AbstractExcelView {

	@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb, HttpServletRequest req, HttpServletResponse res) throws Exception {

		HSSFCell cell = null;

		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setDefaultColumnWidth((short) 15);
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		
		@SuppressWarnings("unchecked")
		Map<String, Object> categoryMap = (Map<String, Object>) model.get("categoryMap");
		@SuppressWarnings("unchecked")
		Map<String, Object> category = (Map<String, Object>) categoryMap.get("category");
		@SuppressWarnings("unchecked")
		ArrayList<HashMap<String, String>> categories = (ArrayList<HashMap<String,String>>)category.get("mapList");
		@SuppressWarnings("unchecked")
		List<KwsSys> sysList = (List<KwsSys>) category.get("sysList");
		@SuppressWarnings("unchecked")
		Map<String, Integer> sysTotalCntMap = (Map<String, Integer>) category.get("sysTotalCnt");
		
		setText(getCell(sheet, 0, 0), "접속시간");
		setText(getCell(sheet, 0, 1), "합계");
		
		for(int i = 0; i < sysList.size(); i++){
			setText(getCell(sheet, 0, i + 2), sysList.get(i).getSysAlias());
		}
//		setText(getCell(sheet, 0, sysList.size() + 1), "합계");
		
		for(int i=0; i < 24; i++) {
			StringBuffer time = new StringBuffer();
			time.append(i).append(":00 ~ ").append(i+1).append(":00");
			setText(getCell(sheet, (i+1), 0), time.toString());
			
			cell = getCell(sheet, (i+1), 1);
			cell.setCellValue(categories.get(i).get("totalCount").toString());
			
			for(int j = 0; j < sysList.size(); j++){
				cell = getCell(sheet, (i + 1), (j + 2));
				cell.setCellValue(categories.get(i).get("sys" + sysList.get(j).getSysId() + "Count").toString());
			}
			
//			cell = getCell(sheet, (i+1), (sysList.size() + 1));
//			cell.setCellValue(categories.get(i).get("totalCount").toString());
		}
		
		cell = getCell(sheet, 25, 0);
		cell.setCellValue("합계");
		
		cell = getCell(sheet, 25, 1);
		cell.setCellValue(sysTotalCntMap.get("sysTotalCnt").toString());
		
		for(int i = 0; i < sysList.size(); i++){
			cell = getCell(sheet, 25, (i + 2));
			cell.setCellValue(sysTotalCntMap.get("sys" + sysList.get(i).getSysId() + "Total").toString());
		}
		
//		cell = getCell(sheet, 25, (sysList.size() + 1));
//		cell.setCellValue(sysTotalCntMap.get("sysTotalCnt").toString());
		
		
		
		res.setContentType("application/x-msdownload");
		res.setHeader("Content-Disposition", "attachment; filename=\'export.xls\'");
	}

}