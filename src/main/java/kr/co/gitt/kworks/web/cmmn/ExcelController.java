package kr.co.gitt.kworks.web.cmmn;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.spatial.FilterFidsDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO.FilterType;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsDataField.FieldTy;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.spatial.SpatialSearchService;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.psl.dataaccess.util.CamelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/////////////////////////////////////////////
/// @class ExcelController
/// kr.co.gitt.kworks.web.cmmn \n
///   ㄴ ExcelController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 30. 오후 6:16:25 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 엑셀 컨트롤러입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/excel/")
public class ExcelController {
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
		
	/// 공간검색 서비스
	@Resource
	SpatialSearchService spatialSearchService;

	/////////////////////////////////////////////
	/// @fn exportExcelFromJson
	/// @brief 함수 간략한 설명 : 엑셀 내보내기 (JSON)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param data
	/// @param userAgent
	/// @param response
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("exportFromJson.do")
	public void exportExcelFromJson(String data, @RequestHeader(value="User-Agent") String userAgent, HttpServletResponse response) throws IOException {
		/// Excel 파일
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		int rowNumber = 0;
		
		/// 요청 String 을 JsonNode 로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(URLDecoder.decode(data, "UTF-8"));
		ArrayNode tableNodes = (ArrayNode) rootNode.get("tables");
		Iterator<JsonNode> iterator = tableNodes.iterator();
		while(iterator.hasNext()) {
			JsonNode tableNode = iterator.next();
			
			/// 테이블명 표시
			String tableName = tableNode.get("title").getTextValue();
			Row tableRow = sheet.createRow(rowNumber);
			Cell tableCell = tableRow.createCell(0);
			tableCell.setCellValue(tableName);
			
			/// 속성정보
			ArrayNode columnsArrayNode = (ArrayNode) tableNode.get("columns");
			int columnSize = columnsArrayNode.size();
			
			/// 레이어명 셀 병합
			sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 0, columnSize-1));
						
			/// 속성정보 표시
			Map<String, Integer> columnMap = new HashMap<String, Integer>();
			Row columnRow = sheet.createRow(++rowNumber);
			for(int i=0; i < columnSize; i++) {
				String title = columnsArrayNode.get(i).get("title").getTextValue();
				Cell columnCell = columnRow.createCell(i);
				columnCell.setCellValue(title);
				columnMap.put(columnsArrayNode.get(i).get("field").getTextValue(), i);
			}
			
			/// 데이터 표시
			ArrayNode rowsArrayNode = (ArrayNode) tableNode.get("rows");
			for(int i=0, len=rowsArrayNode.size(); i < len; i++) {
				Row row = sheet.createRow(++rowNumber);
				for(String key : columnMap.keySet()) {
					Cell cell = row.createCell(columnMap.get(key));
					JsonNode valueNode = rowsArrayNode.get(i).get(key);
					if(valueNode != null) {
						String value = rowsArrayNode.get(i).get(key).getTextValue();
						if(StringUtils.isNotBlank(value))  {
							cell.setCellValue(value);
						}
					}
				}
			}
			rowNumber += 2;
		}
		
		response.setContentType("application/vnd.ms-excel");
		String fileName = rootNode.get("name").getTextValue() + FilenameUtils.EXTENSION_SEPARATOR_STR + "xlsx";
		String docName = null;
		if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")) {
			docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20")+";";
		}
		else {
			docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
		}
		response.setHeader("Content-Disposition", "attachment; filename="+docName);
	
		OutputStream os =  response.getOutputStream();
		workbook.write(os);
		os.close();
		workbook.close();
	}
	
	/////////////////////////////////////////////
	/// @fn exportExcelFromData
	/// @brief 함수 간략한 설명 : 엑셀 내보내기 (데이터, FIDS)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param data
	/// @param userAgent
	/// @param response
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("exportFromData.do")
	public void exportExcelFromData(String data, @RequestHeader(value="User-Agent") String userAgent, HttpServletResponse response) throws Exception {
		/// Excel 파일
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		int rowNumber = 0;
		
		/// 요청 String 을 JsonNode 로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(URLDecoder.decode(data, "UTF-8"));
		ArrayNode dataNodes = (ArrayNode) rootNode.get("data");
		Iterator<JsonNode> iterator = dataNodes.iterator();
		while(iterator.hasNext()) {
			JsonNode dataNode = iterator.next();
			
			String dataId = dataNode.get("dataId").getTextValue();
			KwsData kwsData = dataService.selectOneData(dataId);
			/// 테이블명 표시
			String tableName = kwsData.getDataAlias();
			Row tableRow = sheet.createRow(rowNumber);
			Cell tableCell = tableRow.createCell(0);
			tableCell.setCellValue(tableName);
			
			/// 속성정보
			int columnSize = kwsData.getKwsDataFields().size();
			/// 레이어명 셀 병합
			sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 0, columnSize-1));
			
			/// 속성정보 표시
			Map<String, Integer> columnMap = new HashMap<String, Integer>();
			Row columnRow = sheet.createRow(++rowNumber);
			
			int columnIndex = 0;
			for(int i=0; i < columnSize; i++) {
				KwsDataField kwsDataField = kwsData.getKwsDataFields().get(i);
				// PK, Geometry 컬럼 패스
				if(StringUtils.equals(kwsDataField.getPkAt(), "Y")) {
					continue;
				}
				else if(kwsDataField.getFieldTy().equals(FieldTy.GEOMETRY)) {
					continue;
				}
				
				String title = kwsDataField.getFieldAlias();
				Cell columnCell = columnRow.createCell(columnIndex);
				columnCell.setCellValue(title);
				columnMap.put(kwsDataField.getFieldId(), columnIndex++);
			}
			
			SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
			FilterFidsDTO filterFidsDTO = new FilterFidsDTO();
			List<Long> fids = new ArrayList<Long>();
			ArrayNode fidsNode = (ArrayNode) dataNode.get("ids");
			Iterator<JsonNode> idsIterator = fidsNode.iterator();
			while(idsIterator.hasNext()) {
				JsonNode idNode = idsIterator.next();
				fids.add(Long.parseLong(idNode.getTextValue()));
			}
			filterFidsDTO.setFids(fids);
			spatialSearchDTO.setFilterType(FilterType.FIDS);
			spatialSearchDTO.setFilterFidsDTO(filterFidsDTO);
			List<EgovMap> list = spatialSearchService.listAll(dataId, spatialSearchDTO);
			for(EgovMap map : list) {
				Row row = sheet.createRow(++rowNumber);
				for(String key : columnMap.keySet()) {
					Cell cell = row.createCell(columnMap.get(key));
					Object value = map.get(CamelUtil.convert2CamelCase(key));
					if(value != null)  {
						cell.setCellValue(value.toString());
					}
				}
			}
			rowNumber += 2;
		}
		
		response.setContentType("application/vnd.ms-excel");
		String fileName = rootNode.get("name").getTextValue() + FilenameUtils.EXTENSION_SEPARATOR_STR + "xlsx";
		String docName = null;
		if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")) {
			docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20")+";";
		}
		else {
			docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
		}
		response.setHeader("Content-Disposition", "attachment; filename="+docName);
	
		OutputStream os =  response.getOutputStream();
		workbook.write(os);
		os.close();
		workbook.close();
	}
	
}
