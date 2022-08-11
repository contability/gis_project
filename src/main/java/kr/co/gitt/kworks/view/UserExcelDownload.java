package kr.co.gitt.kworks.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.model.KwsUser;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import kr.co.gitt.kworks.cmmn.view.AbstractExcelView;

@Component("userExcelDownload")
public class UserExcelDownload extends AbstractExcelView {

	@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb, HttpServletRequest req, HttpServletResponse res) throws Exception {

		HSSFCell cell = null;

		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setDefaultColumnWidth((short) 12);
		
		setText(getCell(sheet, 0, 0), "사용자 아이디");
		setText(getCell(sheet, 0, 1), "사용자 명");
		setText(getCell(sheet, 0, 2), "부서");
		setText(getCell(sheet, 0, 3), "사용자 등급");
		setText(getCell(sheet, 0, 4), "권한 그룹");
		setText(getCell(sheet, 0, 5), "사용자 종류");
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) model.get("categoryMap");
		@SuppressWarnings("unchecked")
		List<Object> categories = (List<Object>) map.get("category");

		@SuppressWarnings("unused")
		boolean isVO = false;
		
		if (categories.size() > 0) {
			Object obj = categories.get(0);
			isVO = obj instanceof KwsUser;
		}

		for(int i = 0; i < categories.size(); i++) {
			KwsUser category = (KwsUser) categories.get(i);
			
			cell = getCell(sheet, 1 + i, 0);
			cell.setCellValue(category.getUserId());

			cell = getCell(sheet, 1 + i, 1);
			cell.setCellValue(category.getUserNm());
			
			cell = getCell(sheet, 1 + i, 2);
			cell.setCellValue(category.getDeptNm());
			
			cell = getCell(sheet, 1 + i, 3);
			cell.setCellValue(category.getUserGrad().getValue());
			
			cell = getCell(sheet, 1 + i, 4);
			cell.setCellValue(category.getAuthorGroupNm());
			
			cell = getCell(sheet, 1 + i, 5);
			cell.setCellValue(category.getUserType().getValue());
		}
		
		res.setContentType("application/x-msdownload");
		res.setHeader("Content-Disposition", "attachment; filename=\'export.xls\'");
	}

}