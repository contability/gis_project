package kr.co.gitt.kworks.projects.yg.web.ecologyNatureMap;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchResultDTO;
import kr.co.gitt.kworks.cmmn.view.AbstractExcelView;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.yg.dto.EcologyNatureMapAttrDTO;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Component("ecologyNatureMapView")
@Profile({"yg_dev", "yg_oper"})
public class EcologyNatureMapView extends AbstractExcelView {
	
	/// 기본 컬럼 크기
	private final int DEFAULT_COLUMN_SIZE = 10;

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, Object> data = (Map<String, Object>) model.get("data");
		
		String infoStr = (String) data.get("data");
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode info = objectMapper.readTree(URLDecoder.decode(infoStr, "UTF-8"));
		
		EcologyNatureMapAttrDTO ecologyNatureMapAttrDTO = (EcologyNatureMapAttrDTO) data.get("jibun");
		List<SpatialSearchResultDTO> grades = (List<SpatialSearchResultDTO>) data.get("grades");
		
		Sheet sheet = workbook.createSheet("생태자연등급 대장조서");
		for(int i=0; i <=DEFAULT_COLUMN_SIZE; i++) {
			int width = 10*(500);
			if(i==0 || i==1) {
				width *= 2;
			}
			sheet.setColumnWidth(i, width);
		}
		
		CellStyle titleStyle = createTitleStyle(workbook);
		CellStyle subTitleStyle = createSubTitleStyle(workbook);
		
		int rowIndex = 0;
		createTitle(sheet, titleStyle, rowIndex++, "[지적필지기반 생태자연등급 대장조서]");
		
		createTitle(sheet, subTitleStyle, rowIndex++, "1. 기초정보");
		rowIndex = createBaseInfo(sheet, rowIndex, info, ecologyNatureMapAttrDTO);
		
		createTitle(sheet, subTitleStyle, rowIndex++, "2. 자연생태등급별 편입면적(총괄)");
		rowIndex = createIncorporationArea(sheet, rowIndex, info);
		
		createTitle(sheet, subTitleStyle, rowIndex++, "3. 등급별 자연생태정보 현황");
		rowIndex = createGradeStatusList(sheet, rowIndex, grades);
		
		createTitle(sheet, subTitleStyle, rowIndex++, "4. 토지 정보");
		rowIndex = createLandInfo(sheet, rowIndex, ecologyNatureMapAttrDTO);
		
		StringBuffer fileNameBuffer = new StringBuffer();
		fileNameBuffer.append("생태자연도_");
		fileNameBuffer.append(info.get("bjdNm").asText()==null?"":info.get("bjdNm").asText());
		fileNameBuffer.append("_").append(info.get("jibunNm").asText()==null?"":info.get("jibunNm").asText().toString());
		fileNameBuffer.append(".xls");
		String fileName = fileNameBuffer.toString();
		
		String docName = null;
		String userAgent = request.getHeader("User-Agent");
		if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")) {
			docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20")+";";
		}
		else {
			docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
		}
		response.setHeader("Content-Disposition", "attachment; filename="+docName);
		
	}
	
	private Font createFont(Workbook workbook) {
		Font font = workbook.createFont();
		font.setFontName("함초롬바탕");
		return font;
	}
	
	private CellStyle createTitleStyle(Workbook workbook) {
		Font font = createFont(workbook);
		font.setBold(true);
		font.setFontHeightInPoints((short)30);
		
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		return cellStyle;
	}
	
	private CellStyle createSubTitleStyle(Workbook workbook) {
		Font font = createFont(workbook);
		font.setBold(true);
		font.setFontHeightInPoints((short)20);
		
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		return cellStyle;
	}
	
	private CellStyle createGradeTitleStyle(Workbook workbook) {
		Font font = createFont(workbook);
		font.setBold(true);
		font.setFontHeightInPoints((short)14);
		
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		return cellStyle;
	}
	
	private CellStyle createBoldStyle(Workbook workbook) {
		Font font = createFont(workbook);
		font.setBold(true);
		font.setFontHeightInPoints((short)14);
		
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		return cellStyle;
	}
	
	private CellStyle createNormalStyle(Workbook workbook) {
		Font font = createFont(workbook);
		font.setFontHeightInPoints((short)14);
		
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		return cellStyle;
	}
	
	private void createTitle(Sheet sheet, CellStyle cellStyle, int rowIndex, String value) {
		Row row = sheet.createRow(rowIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, DEFAULT_COLUMN_SIZE));
		
		Cell cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}
	
	private void createThirdTitle(Sheet sheet, CellStyle cellStyle, int rowIndex, String value) {
		Row row = sheet.createRow(rowIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, DEFAULT_COLUMN_SIZE));
		
		Cell cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}
	
	private void setBorderStyle(CellStyle style, int rowIndex, int cellIndex, int rowSize, int columnSize) {
		if(rowIndex == 0) {
			style.setBorderTop(BorderStyle.MEDIUM);
		}
		if(rowIndex == rowSize-1) {
			style.setBorderBottom(BorderStyle.MEDIUM);
		}
		else {
			style.setBorderBottom(BorderStyle.DOTTED);
		}
		
		if(cellIndex==0) {
			style.setBorderLeft(BorderStyle.MEDIUM);
		}
		if(cellIndex==columnSize) {
			style.setBorderRight(BorderStyle.MEDIUM);
		}
		else {
			style.setBorderRight(BorderStyle.DOTTED);
		}
	}
	
	private int createBaseInfo(Sheet sheet, int rowIndex, JsonNode info, EcologyNatureMapAttrDTO ecologyNatureMapAttrDTO) {
		Row row = sheet.createRow(rowIndex++);
		for(int i=0; i <= DEFAULT_COLUMN_SIZE; i++) {
			Cell cell = row.createCell(i);
			CellStyle style = createBoldStyle(sheet.getWorkbook());
			setBorderStyle(style, 0, i, 2, DEFAULT_COLUMN_SIZE);
			cell.setCellStyle(style);
			
			if(i==0) {
				cell.setCellValue("필지정보");
			}
			else if(i==1) {
				cell.setCellValue("소재지");
			}
			else if(i==4) {
				cell.setCellValue("지번");
			}
			else if(i==6) {
				cell.setCellValue("지목");
			}
			else if(i==8) {
				cell.setCellValue("면적");
			}
			else if(i==10) {
				cell.setCellValue("소유자");
			}
		}
		
		row = sheet.createRow(rowIndex++);
		for(int i=0; i <= DEFAULT_COLUMN_SIZE; i++) {
			Cell cell = row.createCell(i);
			CellStyle style = createNormalStyle(sheet.getWorkbook());
			setBorderStyle(style, 1, i, 2, DEFAULT_COLUMN_SIZE);
			if(i==8) {
				style.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat("#,#0.00"));
			}
			cell.setCellStyle(style);
			
			if(i==1) {
				String value = info.get("bjdNm").asText()==null?"":info.get("bjdNm").asText().toString();
				cell.setCellValue(value);
			}
			else if(i==4) {
				cell.setCellValue(info.get("jibunNm").asText()==null?"":info.get("jibunNm").asText().toString());
			}
			else if(i==6) {
				cell.setCellValue(ecologyNatureMapAttrDTO.getJimokNm()==null?"":ecologyNatureMapAttrDTO.getJimokNm());
			}
			else if(i==8) {
				Double value = ecologyNatureMapAttrDTO.getParea();
				cell.setCellValue(value);
			}
			else if(i==10) {
				cell.setCellValue(ecologyNatureMapAttrDTO.getOwnerNm()==null?"":ecologyNatureMapAttrDTO.getOwnerNm());
			}
		}
		
		sheet.addMergedRegion(new CellRangeAddress(rowIndex-2, rowIndex-1, 0, 0));

		sheet.addMergedRegion(new CellRangeAddress(rowIndex-2, rowIndex-2, 1, 3));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex-2, rowIndex-2, 4, 5));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex-2, rowIndex-2, 6, 7));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex-2, rowIndex-2, 8, 9));
		
		sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex-1, 1, 3));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex-1, 4, 5));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex-1, 6, 7));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex-1, 8, 9));
		
		return rowIndex;
	}

	private int createIncorporationArea(Sheet sheet, int rowIndex, JsonNode info) {
		int rowSize = 3;
		
		Map<Integer, String> gradeTitles = new HashMap<Integer, String>();
		gradeTitles.put(1, "1등급");
		gradeTitles.put(3, "2등급");
		gradeTitles.put(5, "3등급");
		gradeTitles.put(7, "별도관리지역");
		gradeTitles.put(9, "소계");
		
		Map<Integer, String> gradeLayers = new HashMap<Integer, String>();
		gradeLayers.put(1, "NEL_ECO1_AS");
		gradeLayers.put(3, "NEL_ECO2_AS");
		gradeLayers.put(5, "NEL_ECO3_AS");
		gradeLayers.put(7, "NEL_ECO9_AS");
		gradeLayers.put(9, "TOTAL");
		
		Row row = sheet.createRow(rowIndex++);
		for(int i=0; i <= DEFAULT_COLUMN_SIZE; i++) {
			Cell cell = row.createCell(i);
			CellStyle style = createBoldStyle(sheet.getWorkbook());
			setBorderStyle(style, 0, i, rowSize, DEFAULT_COLUMN_SIZE);
			cell.setCellStyle(style);
			
			if(i==0) {
				cell.setCellValue("구분");
			}
			else if(gradeTitles.containsKey(i)) {
				cell.setCellValue(gradeTitles.get(i));
			}
		}
		
		
		Map<String, String> grades = new HashMap<String, String>();
		JsonNode gradesNode = info.path("grades");
		Iterator<String> gradeFieldNames = gradesNode.getFieldNames();
		while(gradeFieldNames.hasNext()) {
			String fieldName = gradeFieldNames.next();
			grades.put(fieldName, gradesNode.path(fieldName).asText());
		}
		
		
		Map<String, String> ratios = new HashMap<String, String>();
		JsonNode ratiosNode = info.path("ratios");
		Iterator<String> ratioFieldNames = ratiosNode.getFieldNames();
		while(ratioFieldNames.hasNext()) {
			String fieldName = ratioFieldNames.next();
			ratios.put(fieldName, ratiosNode.path(fieldName).asText());
		}
		
		row = sheet.createRow(rowIndex++);
		for(int i=0; i <= DEFAULT_COLUMN_SIZE; i++) {
			Cell cell = row.createCell(i);
			CellStyle style = null;
			if(i==0) {
				style = createBoldStyle(sheet.getWorkbook());
			}
			else {
				style = createNormalStyle(sheet.getWorkbook());
				style.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat("#,#0.00"));
			}
			setBorderStyle(style, 1, i, rowSize, DEFAULT_COLUMN_SIZE);
			cell.setCellStyle(style);
			
			if(i==0) {
				cell.setCellValue("면적");
			}
			else if(gradeTitles.containsKey(i)) {
				String gradeLayer = gradeLayers.get(i);
				Double value = StringUtils.isNotBlank(grades.get(gradeLayer))?Double.valueOf(grades.get(gradeLayer)):0;
				cell.setCellValue(value);
			}
		}
		
		row = sheet.createRow(rowIndex++);
		for(int i=0; i <= DEFAULT_COLUMN_SIZE; i++) {
			Cell cell = row.createCell(i);
			CellStyle style = null;
			if(i==0) {
				style = createBoldStyle(sheet.getWorkbook());
			}
			else {
				style = createNormalStyle(sheet.getWorkbook());
				style.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat("0.00%"));
			}
			setBorderStyle(style, 2, i, rowSize, DEFAULT_COLUMN_SIZE);
			cell.setCellStyle(style);
			
			if(i==0) {
				cell.setCellValue("분포비율(%)");
			}
			else if(gradeTitles.containsKey(i)) {
				String gradeLayer = gradeLayers.get(i);
				Double value = StringUtils.isNotBlank(ratios.get(gradeLayer))?Double.valueOf(ratios.get(gradeLayer)):0;
				cell.setCellValue(value/100);
			}
		}
		
		for(int i=rowSize; i > 0; i--) {
			sheet.addMergedRegion(new CellRangeAddress(rowIndex-i, rowIndex-i, 1, 2));
			sheet.addMergedRegion(new CellRangeAddress(rowIndex-i, rowIndex-i, 3, 4));
			sheet.addMergedRegion(new CellRangeAddress(rowIndex-i, rowIndex-i, 5, 6));
			sheet.addMergedRegion(new CellRangeAddress(rowIndex-i, rowIndex-i, 7, 8));
			sheet.addMergedRegion(new CellRangeAddress(rowIndex-i, rowIndex-i, 9, 10));
		}
		return rowIndex;
	}
	
	private int createGradeStatusList(Sheet sheet, int rowIndex, List<SpatialSearchResultDTO> grades) {
		String[] gradeTitles = {"가. 1등급 현황", "나. 2등급 현황", "다. 3등급 현황", "라. 별도관리지역"};
		for(int i=0, len=gradeTitles.length; i < len; i++) {
			String gradeTitle = gradeTitles[i];
			String dataId = null;
			if(i==0) {
				dataId = "NEL_ECO1_AS";
			}
			else if(i==1) {
				dataId = "NEL_ECO2_AS";
			}
			else if(i==2) {
				dataId = "NEL_ECO3_AS";
			}
			else {
				dataId = "NEL_ECO9_AS";
			}
			
			List<EgovMap> gradeRows = null;
			for(SpatialSearchResultDTO spatialSearchResultDTO : grades) {
				if(StringUtils.equals(spatialSearchResultDTO.getDataId(), dataId)) {
					gradeRows = spatialSearchResultDTO.getRows();
				}
			}
			
			if(i == 3) {
				rowIndex = createGradeEtcStatus(sheet, rowIndex, gradeTitle, gradeRows);
			}
			else {
				rowIndex = createGradeStatus(sheet, rowIndex, gradeTitle, gradeRows);
			}
		}

		return rowIndex;
	}
	
	private int createGradeStatus(Sheet sheet, int rowIndex, String gradeTitle, List<EgovMap> gradeRows) {
		CellStyle gradeTitleStyle = createGradeTitleStyle(sheet.getWorkbook());
		createThirdTitle(sheet, gradeTitleStyle, rowIndex++, gradeTitle);
		
		Integer rowSize = 1;
		if(gradeRows == null || gradeRows.size() == 0) {
			rowSize += 1;
		}
		else {
			rowSize += gradeRows.size();
		}

		Row row = sheet.createRow(rowIndex++);
		for(int i=0; i <= DEFAULT_COLUMN_SIZE; i++) {
			Cell cell = row.createCell(i);
			CellStyle style = createBoldStyle(sheet.getWorkbook());
			setBorderStyle(style, 0, i, rowSize, DEFAULT_COLUMN_SIZE);
			cell.setCellStyle(style);
			
			if(i==0) {
				cell.setCellValue("식물군락명");
			}
			else if(i==1) {
				cell.setCellValue("군락기호");
			}
			else if(i==2) {
				cell.setCellValue("분류코드");
			}
			else if(i==3) {
				cell.setCellValue("대분류코드");
			}
			else if(i==4) {
				cell.setCellValue("대분류");
			}
			else if(i==5) {
				cell.setCellValue("보전등급");
			}
			else if(i==6) {
				cell.setCellValue("식생평가");
			}
			else if(i==7) {
				cell.setCellValue("동식물평가");
			}
			else if(i==8) {
				cell.setCellValue("습지평가");
			}
			else if(i==9) {
				cell.setCellValue("지형평가");
			}
			else if(i==10) {
				sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex-1, 9, 10));
				//cell.setCellValue("편입면적");
			}
		}
		
		int innerRowIndex = 1;
		if(gradeRows == null || gradeRows.size() == 0) {
			row = sheet.createRow(rowIndex++);
			for(int i=0; i <= DEFAULT_COLUMN_SIZE; i++) {
				Cell cell = row.createCell(i);
				CellStyle style = createNormalStyle(sheet.getWorkbook());
				setBorderStyle(style, innerRowIndex, i, rowSize, DEFAULT_COLUMN_SIZE);
				cell.setCellStyle(style);
			}
			sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex-1, 9, 10));
			innerRowIndex++;
		}
		else {
			for(EgovMap gradeRow : gradeRows) {
				row = sheet.createRow(rowIndex++);
				for(int i=0; i <= DEFAULT_COLUMN_SIZE; i++) {
					Cell cell = row.createCell(i);
					CellStyle style = createNormalStyle(sheet.getWorkbook());
					setBorderStyle(style, innerRowIndex, i, rowSize, DEFAULT_COLUMN_SIZE);
					cell.setCellStyle(style);
					
					if(i==0) {
						cell.setCellValue(gradeRow.get("plcmNm")==null?"":gradeRow.get("plcmNm").toString());
					}
					else if(i==1) {
						cell.setCellValue(gradeRow.get("cmSymbl")==null?"":gradeRow.get("cmSymbl").toString());
					}
					else if(i==2) {
						cell.setCellValue(gradeRow.get("clCd")==null?"":gradeRow.get("clCd").toString());
					}
					else if(i==3) {
						cell.setCellValue(gradeRow.get("lclasCd")==null?"":gradeRow.get("lclasCd").toString());
					}
					else if(i==4) {
						cell.setCellValue(gradeRow.get("lclasNm")==null?"":gradeRow.get("lclasNm").toString());
					}
					else if(i==5) {
						cell.setCellValue(gradeRow.get("evsrGrd")==null?"":gradeRow.get("evsrGrd").toString());
					}
					else if(i==6) {
						cell.setCellValue(gradeRow.get("vtnEvl")==null?"":gradeRow.get("vtnEvl").toString());
					}
					else if(i==7) {
						cell.setCellValue(gradeRow.get("amplEvl")==null?"":gradeRow.get("amplEvl").toString());
					}
					else if(i==8) {
						cell.setCellValue(gradeRow.get("smldEvl")==null?"":gradeRow.get("smldEvl").toString());
					}
					else if(i==9) {
						cell.setCellValue(gradeRow.get("tpgEvl")==null?"":gradeRow.get("tpgEvl").toString());
					}
					else if(i==10) {
						sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex-1, 9, 10));
						//cell.setCellValue(data.get("편입면적")==null?"":data.get("편입면적").toString());
					}
				}
				innerRowIndex++;
			}
		}
		return rowIndex;
	}
	
	private int createGradeEtcStatus(Sheet sheet, int rowIndex, String gradeTitle, List<EgovMap> gradeRows) {
		CellStyle gradeTitleStyle = createGradeTitleStyle(sheet.getWorkbook());
		createThirdTitle(sheet, gradeTitleStyle, rowIndex++, gradeTitle);
		
		Integer rowSize = 1;
		if(gradeRows == null || gradeRows.size() == 0) {
			rowSize += 1;
		}
		else {
			rowSize += gradeRows.size();
		}

		Row row = sheet.createRow(rowIndex++);
		for(int i=0; i <= DEFAULT_COLUMN_SIZE; i++) {
			Cell cell = row.createCell(i);
			CellStyle style = createBoldStyle(sheet.getWorkbook());
			setBorderStyle(style, 0, i, rowSize, DEFAULT_COLUMN_SIZE);
			cell.setCellStyle(style);
			
			if(i==0) {
				cell.setCellValue("천연기념물");
			}
			else if(i==1) {
				cell.setCellValue("산림유전");
			}
			else if(i==2) {
				cell.setCellValue("야생동식물");
			}
			else if(i==3) {
				cell.setCellValue("수산자원");
			}
			else if(i==4) {
				cell.setCellValue("습지보호");
			}
			else if(i==5) {
				cell.setCellValue("백두대간");
			}
			else if(i==6) {
				cell.setCellValue("생태경관");
			}
			else if(i==7) {
				cell.setCellValue("국립공원");
			}
			else if(i==8) {
				cell.setCellValue("도립공원");
			}
			else if(i==9) {
				cell.setCellValue("군립공원");
			}
			else if(i==10) {
				sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex-1, 9, 10));
				//cell.setCellValue("편입면적");
			}
		}
		
		int innerRowIndex = 1;
		if(gradeRows == null || gradeRows.size() == 0) {
			row = sheet.createRow(rowIndex++);
			for(int i=0; i <= DEFAULT_COLUMN_SIZE; i++) {
				Cell cell = row.createCell(i);
				CellStyle style = createNormalStyle(sheet.getWorkbook());
				setBorderStyle(style, innerRowIndex, i, rowSize, DEFAULT_COLUMN_SIZE);
				cell.setCellStyle(style);
			}
			sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex-1, 9, 10));
			innerRowIndex++;
		}
		else {
			for(EgovMap gradeRow : gradeRows) {
				row = sheet.createRow(rowIndex++);
				for(int i=0; i <= DEFAULT_COLUMN_SIZE; i++) {
					Cell cell = row.createCell(i);
					CellStyle style = createNormalStyle(sheet.getWorkbook());
					setBorderStyle(style, innerRowIndex, i, rowSize, DEFAULT_COLUMN_SIZE);
					cell.setCellStyle(style);
					
					if(i==0) {
						cell.setCellValue(gradeRow.get("ntrmnNm")==null?"":gradeRow.get("ntrmnNm").toString());
					}
					else if(i==1) {
						cell.setCellValue(gradeRow.get("mtstNm")==null?"":gradeRow.get("mtstNm").toString());
					}
					else if(i==2) {
						cell.setCellValue(gradeRow.get("wlpntNm")==null?"":gradeRow.get("wlpntNm").toString());
					}
					else if(i==3) {
						cell.setCellValue(gradeRow.get("mncscs")==null?"":gradeRow.get("mncscs").toString());
					}
					else if(i==4) {
						cell.setCellValue(gradeRow.get("smldPrt")==null?"":gradeRow.get("smldPrt").toString());
					}
					else if(i==5) {
						cell.setCellValue(gradeRow.get("bgtsNm")==null?"":gradeRow.get("bgtsNm").toString());
					}
					else if(i==6) {
						cell.setCellValue(gradeRow.get("mstrvc")==null?"":gradeRow.get("mstrvc").toString());
					}
					else if(i==7) {
						cell.setCellValue(gradeRow.get("nlprkNm")==null?"":gradeRow.get("nlprkNm").toString());
					}
					else if(i==8) {
						cell.setCellValue(gradeRow.get("prvpkNm")==null?"":gradeRow.get("prvpkNm").toString());
					}
					else if(i==9) {
						cell.setCellValue(gradeRow.get("pypkNm")==null?"":gradeRow.get("pypkNm").toString());
					}
					else if(i==10) {
						sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex-1, 9, 10));
						//cell.setCellValue(data.get("편입면적")==null?"":data.get("편입면적").toString());
					}
				}
				innerRowIndex++;
			}
		}
		return rowIndex;
	}
	
	private int createLandInfo(Sheet sheet, int rowIndex, EcologyNatureMapAttrDTO ecologyNatureMapAttrDTO) {
		rowIndex = createChararterInfo(sheet, rowIndex, "가. 지형특성정보", ecologyNatureMapAttrDTO);
		rowIndex = createSpecificUseDistrictInfo(sheet, rowIndex, "나. 토지용도지구", ecologyNatureMapAttrDTO.getUsePlans());
		rowIndex = createCoverInfo(sheet, rowIndex, "다. 토지피복정보", ecologyNatureMapAttrDTO.getUseCls());
		return rowIndex;
	}
	
	private int createChararterInfo(Sheet sheet, int rowIndex, String title, EcologyNatureMapAttrDTO ecologyNatureMapAttrDTO) {
		int columnSize = 3;
		
		CellStyle gradeTitleStyle = createGradeTitleStyle(sheet.getWorkbook());
		createThirdTitle(sheet, gradeTitleStyle, rowIndex++, title);
		
		String sfhht = ecologyNatureMapAttrDTO.getSfhht();
		String sltDrc = ecologyNatureMapAttrDTO.getSltDrc();
		String sltNgl = ecologyNatureMapAttrDTO.getSltNgl();
		String grdnt = ecologyNatureMapAttrDTO.getGrdnt();
		
		Integer rowSize = 2;
		Row row = sheet.createRow(rowIndex++);
		for(int i=0; i <= columnSize; i++) {
			Cell cell = row.createCell(i);
			CellStyle style = createBoldStyle(sheet.getWorkbook());
			setBorderStyle(style, 0, i, rowSize, columnSize);
			cell.setCellStyle(style);
			
			if(i==0) {
				cell.setCellValue("고도");
			}
			else if(i==1) {
				cell.setCellValue("방향");
			}
			else if(i==2) {
				cell.setCellValue("경사향");
			}
			else if(i==3) {
				cell.setCellValue("경사도");
			}
		}
		
		int innerRowIndex = 1;
		if(sfhht != null || StringUtils.isNotBlank(sltDrc) || sltNgl != null || grdnt != null) {
			row = sheet.createRow(rowIndex++);
			for(int i=0; i <= columnSize; i++) {
				Cell cell = row.createCell(i);
				CellStyle style = createNormalStyle(sheet.getWorkbook());
				setBorderStyle(style, innerRowIndex, i, rowSize, columnSize);
				cell.setCellStyle(style);
				
				if(i==0) {
					cell.setCellValue(sfhht==null?"":sfhht.toString());
				}
				else if(i==1) {
					cell.setCellValue(StringUtils.isNotBlank(sltDrc)?sltDrc:"");
				}
				else if(i==2) {
					cell.setCellValue(sltNgl==null?"":sltNgl.toString());
				}
				else if(i==3) {
					cell.setCellValue(grdnt==null?"":grdnt.toString());
				}
			}
			innerRowIndex++;
		}
		else {
			row = sheet.createRow(rowIndex++);
			for(int i=0; i <= columnSize; i++) {
				Cell cell = row.createCell(i);
				CellStyle style = createNormalStyle(sheet.getWorkbook());
				setBorderStyle(style, innerRowIndex, i, rowSize, columnSize);
				cell.setCellStyle(style);
			}
			sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex-1, 9, 10));
			innerRowIndex++;
		}
		return rowIndex;
	}
	
	private int createSpecificUseDistrictInfo(Sheet sheet, int rowIndex, String title, List<KwsDomnCode> userPlans) {
		int columnSize = 1;
		
		CellStyle gradeTitleStyle = createGradeTitleStyle(sheet.getWorkbook());
		createThirdTitle(sheet, gradeTitleStyle, rowIndex++, title);
		
		Integer rowSize = 1;
		if(userPlans == null || userPlans.size() == 0) {
			rowSize += 1;
		}
		else {
			rowSize += userPlans.size();
		}

		Row row = sheet.createRow(rowIndex++);
		for(int i=0; i <= columnSize; i++) {
			Cell cell = row.createCell(i);
			CellStyle style = createBoldStyle(sheet.getWorkbook());
			setBorderStyle(style, 0, i, rowSize, columnSize);
			cell.setCellStyle(style);
			
			if(i==0) {
				cell.setCellValue("코드");
			}
			else if(i==1) {
				cell.setCellValue("용도지구");
			}
		}
		
		int innerRowIndex = 1;
		if(userPlans == null || userPlans.size() == 0) {
			row = sheet.createRow(rowIndex++);
			for(int i=0; i <= columnSize; i++) {
				Cell cell = row.createCell(i);
				CellStyle style = createNormalStyle(sheet.getWorkbook());
				setBorderStyle(style, innerRowIndex, i, rowSize, columnSize);
				cell.setCellStyle(style);
			}
			sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex-1, 9, 10));
			innerRowIndex++;
		}
		else {
			for(KwsDomnCode usePlan : userPlans) {
				row = sheet.createRow(rowIndex++);
				for(int i=0; i <= columnSize; i++) {
					Cell cell = row.createCell(i);
					CellStyle style = createNormalStyle(sheet.getWorkbook());
					setBorderStyle(style, innerRowIndex, i, rowSize, columnSize);
					cell.setCellStyle(style);
					
					if(i==0) {
						cell.setCellValue(usePlan.getCodeId()==null?"":usePlan.getCodeId());
					}
					else if(i==1) {
						cell.setCellValue(usePlan.getCodeNm()==null?"":usePlan.getCodeNm());
					}
				}
				innerRowIndex++;
			}
		}
		return rowIndex;
	}

	private int createCoverInfo(Sheet sheet, int rowIndex, String title, Set<String> useCls) {
		int columnSize = 0;
		
		CellStyle gradeTitleStyle = createGradeTitleStyle(sheet.getWorkbook());
		createThirdTitle(sheet, gradeTitleStyle, rowIndex++, title);
		
		Integer rowSize = 1;
		if(useCls == null || useCls.size() == 0) {
			rowSize += 1;
		}
		else {
			rowSize += useCls.size();
		}

		Row row = sheet.createRow(rowIndex++);
		for(int i=0; i <= columnSize; i++) {
			Cell cell = row.createCell(i);
			CellStyle style = createBoldStyle(sheet.getWorkbook());
			setBorderStyle(style, 0, i, rowSize, columnSize);
			cell.setCellStyle(style);
			
			if(i==0) {
				cell.setCellValue("대분류구분");
			}
		}
		
		int innerRowIndex = 1;
		if(useCls == null || useCls.size() == 0) {
			row = sheet.createRow(rowIndex++);
			for(int i=0; i <= columnSize; i++) {
				Cell cell = row.createCell(i);
				CellStyle style = createNormalStyle(sheet.getWorkbook());
				setBorderStyle(style, innerRowIndex, i, rowSize, columnSize);
				cell.setCellStyle(style);
			}
			sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex-1, 9, 10));
			innerRowIndex++;
		}
		else {
			for(String useCl : useCls) {
				row = sheet.createRow(rowIndex++);
				for(int i=0; i <= columnSize; i++) {
					Cell cell = row.createCell(i);
					CellStyle style = createNormalStyle(sheet.getWorkbook());
					setBorderStyle(style, innerRowIndex, i, rowSize, columnSize);
					cell.setCellStyle(style);
					
					if(i==0) {
						cell.setCellValue(StringUtils.isNotBlank(useCl)?useCl:"");
					}
				}
				innerRowIndex++;
			}
		}
		return rowIndex;
	}
	
}
