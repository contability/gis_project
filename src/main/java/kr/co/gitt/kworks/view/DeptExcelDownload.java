package kr.co.gitt.kworks.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.view.AbstractExcelView;
import kr.co.gitt.kworks.model.KwsDept;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

@Component("deptExcelDownload")
public class DeptExcelDownload extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb, HttpServletRequest req, HttpServletResponse res) throws Exception {

		HSSFCell cell = null;

		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setDefaultColumnWidth((short) 12);
		
		setText(getCell(sheet, 0, 0), "부서코드");
		setText(getCell(sheet, 0, 1), "부서명");
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) model.get("categoryMap");
		@SuppressWarnings("unchecked")
		List<Object> categories = (List<Object>) map.get("category");

		@SuppressWarnings("unused")
		boolean isVO = false;
		
		if (categories.size() > 0) {
			Object obj = categories.get(0);
			isVO = obj instanceof KwsDept;
		}

		for(int i = 0; i < categories.size(); i++) {
			KwsDept category = (KwsDept) categories.get(i);
			
			cell = getCell(sheet, 1 + i, 0);
			cell.setCellValue(category.getDeptCode());

			cell = getCell(sheet, 1 + i, 1);
			cell.setCellValue(category.getDeptNm());
		}
		
		res.setContentType("application/x-msdownload");
		res.setHeader("Content-Disposition", "attachment; filename=\'export.xls\'");
	}

}