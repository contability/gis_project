package kr.co.gitt.kworks.projects.gn.service.cmmnProp;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.CamelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterCqlDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO.FilterType;
import kr.co.gitt.kworks.cmmn.view.AbstractExcelView;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsDataField.IndictTy;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.spatial.SpatialSearchService;

@Component("cmmnPropExportExcel")
public class CmmnPropExportExcel extends AbstractExcelView{
	
	@Resource
	DataService dataService;
	
	@Resource
	SpatialSearchService searchService;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		@SuppressWarnings("unchecked")
		List<String> dataIds = (List<String>)model.get("dataIdList");
		
		String fileName = ""; 
		
		for(String dataId : dataIds) {
			if(dataId.equals("BML_LOAN_AS")) {
				fileName = "대부기간 만료 임박 내역.xls";
			}else {
				fileName = "지번불부합 공유재산.xls";
			}
			
			KwsData kwsData = dataService.selectOneData(dataId);
			List<KwsDataField> kwsDataFields = kwsData.getKwsDataFields();
			
			HSSFSheet sheet = workbook.createSheet(kwsData.getDataAlias());
			sheet.setDefaultColumnWidth((short) 15);
			
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
			
			HSSFCell cell = null;
			
			int cellIndex = 0;
			Map<String, Integer> columnIndex = new HashMap<String, Integer>();
			
			for(KwsDataField kwsDataField : kwsDataFields) {
				if(!kwsDataField.getIndictTy().equals(IndictTy.WKT) && !kwsDataField.getIndictTy().equals(IndictTy.HIDE)) {
					cell = getCell(sheet, 0, cellIndex);
					cell.setCellValue(kwsDataField.getFieldAlias());
					columnIndex.put(CamelUtil.convert2CamelCase(kwsDataField.getFieldId()), cellIndex++);
				}
			}
			
			UserDTO userDTO = (UserDTO)EgovUserDetailsHelper.getAuthenticatedUser();
			String deptCode = userDTO.getDeptCode();
			
			SpatialSearchDTO searchDTO = new SpatialSearchDTO();
			searchDTO.setDataId(dataId);
			searchDTO.setFilterType(FilterType.CQL);
			
			FilterCqlDTO cqlDTO = new FilterCqlDTO();
			String cql = "";
			
			if(dataId.equals("BML_LOAN_AS")) {
				cql += " TO_DATE(LON_END) - 60 <= SYSDATE ";
				if(!deptCode.equals("4200245")) {
					cql += " AND MAN_CDE = " + deptCode + "0000 ";
				}
				cqlDTO.setCql(cql);
				
			}else {
				cql += " GEOM IS NULL ";
				cqlDTO.setCql(cql);
			}
			
			searchDTO.setFilterCqlDTO(cqlDTO);
			
			List<EgovMap> data = searchService.listAll(dataId, searchDTO);
			
			for(int i = 0; i < data.size(); i++) {
				EgovMap map = data.get(i);
				Row row = sheet.createRow(i + 1);
				
				for(Object key : map.keySet()) {
					if(columnIndex.get(key) != null && map.get(key) != null) {
						String val = map.get(key).toString();
						row.createCell(columnIndex.get(key)).setCellValue(val.trim());
					}
				}
			}
		}
		
		String userAgent = request.getHeader("User-Agent");
		String docName = "";
		if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")) {
			docName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20") + ";";
		}else {
			docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-88859-1") + "\"";
		}
		
		response.setContentType("application/x-msdownload");
		response.setHeader("content-Disposition", "attachment; filename=" + docName);
	}

}
