package kr.co.gitt.kworks.service.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.spatial.FilterFidsDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO.FilterType;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsDataField.FieldTy;
import kr.co.gitt.kworks.model.KwsDataField.IndictTy;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.spatial.SpatialSearchService;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.h2.store.fs.FileUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.kmaps.framework.action.coordinateconvert.SRSTransform;
import com.kmaps.framework.common.Coordinate;
import com.kmaps.framework.common.Coordinates;
import com.kmaps.framework.common.Extent;
import com.kmaps.framework.reference.ISpatialReferenceSystem;
import com.kmaps.framework.reference.initialize.DefaultSRS;
import com.kmaps.framework.spatialdata.geometry.IGeometry;
import com.kmaps.framework.spatialdata.geometry.io.WKTReader;

import egovframework.rte.psl.dataaccess.util.CamelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/////////////////////////////////////////////
/// @class ExcelExportServiceImpl
/// kworks.map.file.service.impl \n
///   ㄴ ExcelExportServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 5. 25. 오전 10:40:36 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 엑셀 내보내기 서비스 구현입니다.
///   -# 
/////////////////////////////////////////////
@Service("excelExportService")
public class ExcelExportServiceImpl implements ExcelExportService {

	/// 메세지 소스
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/// 공간 검색 서비스
	@Resource
	SpatialSearchService spatialSearchService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExcelExportService#fidsExport(kr.co.gitt.kworks.cmmn.dto.SpatialSearchSummaryDTO, boolean, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public void fidsExport(SpatialSearchSummaryDTO spatialSearchSummaryDTO, boolean isFieldName, String path, String toSrid, Extent extent) throws Exception {
		String fromSrid = messageSource.getMessage("Map.Projection", null, Locale.getDefault());
		SRSTransform srsTrans = null;
		srsTrans = new SRSTransform();
		
		// 소스 좌표계
		ISpatialReferenceSystem sourceSrs = DefaultSRS.get(fromSrid, 0, 0);
		// 목표 좌표계
		ISpatialReferenceSystem targetSrs = DefaultSRS.get(toSrid, 0, 0);
		srsTrans.set(sourceSrs, targetSrs);
		
		String dataId = spatialSearchSummaryDTO.getDataId();
		
		SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
		Sheet sheet = workbook.createSheet();
		
		int rowIndex = 0;
		int cellIndex = 0;
		Map<String, Integer> columnIndex = new HashMap<String, Integer>();
		String geometryColumn = null;
		IndictTy indictColumn = null;

		Row headerRow = null;
		if(isFieldName) {
			headerRow = sheet.createRow(rowIndex++);
		}
		
		KwsData kwsData = dataService.selectOneData(dataId);
		List<KwsDataField> fields = kwsData.getKwsDataFields();
		for(KwsDataField kwsDataField : fields) {
			// PK_AT = 'Y'인 컬럼 패스
			if(StringUtils.equals(kwsDataField.getPkAt(), "Y")) {
				continue;
			}
			// FIELD_TY = 'Geometry'인 컬럼 패스
			else if(kwsDataField.getFieldTy().equals(FieldTy.GEOMETRY)) {
				geometryColumn = kwsDataField.getFieldId();
				continue;
			}
			// INDICT_TY = 'HIDE'인 컬럼패스
			else if((kwsDataField.getIndictTy().equals(IndictTy.HIDE))) {
				indictColumn = kwsDataField.getIndictTy();
				continue;
			}
			
			if(headerRow != null) {
				Cell cell = headerRow.createCell(cellIndex);
				cell.setCellValue(kwsDataField.getFieldAlias());
			}
			columnIndex.put(CamelUtil.convert2CamelCase((String) kwsDataField.getFieldId()), cellIndex++);
		}
		
		WKTReader reader = new WKTReader();
		
		List<Long> ids = spatialSearchSummaryDTO.getIds();
		SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
		spatialSearchDTO.setFilterType(FilterType.FIDS);
		FilterFidsDTO filterFidsDTO = null;
		List<EgovMap> data = null;
		
		int patchSize = 1000;
		int fromIndex = 0;
		int toIndex = patchSize;
		while(fromIndex < ids.size()) {
			if(toIndex > ids.size()) {
				toIndex = ids.size();
			}
			filterFidsDTO = new FilterFidsDTO();
			filterFidsDTO.setFids(ids.subList(fromIndex, toIndex));
			spatialSearchDTO.setFilterFidsDTO(filterFidsDTO);
			
			data = spatialSearchService.listAll(dataId, spatialSearchDTO);
			
			for(int i=0, len=data.size(); i < len; i++) {
				EgovMap map = data.get(i);
				Row row = sheet.createRow(rowIndex++);
				for(Object key : map.keySet()) {
					if(StringUtils.equalsIgnoreCase(key.toString(), geometryColumn)) {
//						continue;
						IGeometry geom = null;
						IGeometry geometry = reader.execute(map.get(key).toString());
						geometry.setSRSName(fromSrid);
						
						extent.union(geometry.extent());
						
						if(StringUtils.isNotBlank(toSrid) && !StringUtils.equals(fromSrid, toSrid)) {
							geom = srsTrans.convert(geometry);
						}
						else {
							geom = geometry;
						}
					}
					else {
						if(columnIndex.get(key) != null && map.get(key) != null) {
							row.createCell(columnIndex.get(key)).setCellValue(map.get(key).toString());;
						}
					}
				}
			}
			fromIndex += patchSize;
			toIndex += patchSize;
		}
		
		//속성테이블 엑셀 내보내기 시 extent를 구하지 못하는 것에 대처
		//지자체 Map.Extent를 대신 사용
		if (Double.isInfinite(extent.getMaxX()) || Double.isInfinite(extent.getMaxY()) 
				|| Double.isInfinite(extent.getMinX()) || Double.isInfinite(extent.getMinX())) {
			String mapExtent = messageSource.getMessage("Map.Extent", null, Locale.getDefault());
			String[] extentArray = mapExtent.split(",");
			extent.setMinX(Double.parseDouble(extentArray[0]));
			extent.setMinY(Double.parseDouble(extentArray[1]));
			extent.setMaxX(Double.parseDouble(extentArray[2]));
			extent.setMaxY(Double.parseDouble(extentArray[3]));
		}
		
		
		FileUtils.createDirectories(path);
		String dataAlias = kwsData.getDataAlias().replace('/',  '_');
		String excelFileName = dataAlias + "(" + dataId + ")";
		String filePath = path + File.separator + excelFileName + ".xlsx";
		FileUtils.createFile(filePath);
		OutputStream os = new FileOutputStream(filePath);
		workbook.write(os);
		workbook.dispose();
		workbook.close();
		os.close();
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExcelExportService#fidsExport(kr.co.gitt.kworks.cmmn.dto.SpatialSearchSummaryDTO, boolean, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public void fidsExportwithCoordinates(SpatialSearchSummaryDTO spatialSearchSummaryDTO, boolean isFieldName, String path, String toSrid, Extent extent) throws Exception {
		String fromSrid = messageSource.getMessage("Map.Projection", null, Locale.getDefault());
		SRSTransform srsTrans = null;
		srsTrans = new SRSTransform();
		
		// 소스 좌표계
		ISpatialReferenceSystem sourceSrs = DefaultSRS.get(fromSrid, 0, 0);
		// 목표 좌표계
		ISpatialReferenceSystem targetSrs = DefaultSRS.get(toSrid, 0, 0);
		srsTrans.set(sourceSrs, targetSrs);
		
		String dataId = spatialSearchSummaryDTO.getDataId();
		
		SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
		Sheet sheet = workbook.createSheet();
		
		int rowIndex = 0;
		int cellIndex = 0;
		Map<String, Integer> columnIndex = new HashMap<String, Integer>();
		String geometryColumn = null;
		IndictTy indictColumn = null;

		Row headerRow = null;
		if(isFieldName) {
			headerRow = sheet.createRow(rowIndex++);
		}
		
		KwsData kwsData = dataService.selectOneData(dataId);
		List<KwsDataField> fields = kwsData.getKwsDataFields();
		for(KwsDataField kwsDataField : fields) {
			// PK_AT = 'Y'인 컬럼 패스
			if(StringUtils.equals(kwsDataField.getPkAt(), "Y")) {
				continue;
			}
			// FIELD_TY = 'Geometry'인 컬럼 패스
			else if(kwsDataField.getFieldTy().equals(FieldTy.GEOMETRY)) {
				geometryColumn = kwsDataField.getFieldId();
				continue;
			}
			// INDICT_TY = 'HIDE'인 컬럼패스
			else if((kwsDataField.getIndictTy().equals(IndictTy.HIDE))) {
				indictColumn = kwsDataField.getIndictTy();
				continue;
			}
			
			if(headerRow != null) {
				Cell cell = headerRow.createCell(cellIndex);
				cell.setCellValue(kwsDataField.getFieldAlias());
			}
			columnIndex.put(CamelUtil.convert2CamelCase((String) kwsDataField.getFieldId()), cellIndex++);
		}
		
		if(headerRow != null) {
			if (targetSrs.getName().toUpperCase().equals("EPSG:4737") 
					|| targetSrs.getName().toUpperCase().equals("EPSG:4166") 
					|| targetSrs.getName().toUpperCase().equals("EPSG:4326")
					|| targetSrs.getName().toUpperCase().equals("EPSG:4162")
					|| targetSrs.getName().toUpperCase().equals("EPSG:4004")) {
				headerRow.createCell(cellIndex).setCellValue("경도");
				columnIndex.put("x", cellIndex++);
				headerRow.createCell(cellIndex).setCellValue("위도");
				columnIndex.put("y", cellIndex++);
			} else {
				headerRow.createCell(cellIndex).setCellValue("X좌표");
				columnIndex.put("x", cellIndex++);
				headerRow.createCell(cellIndex).setCellValue("Y죄표");
				columnIndex.put("y", cellIndex++);
			}
		} else {
			columnIndex.put("x", cellIndex++);
			columnIndex.put("y", cellIndex++);
		}
		
		WKTReader reader = new WKTReader();
		
		List<Long> ids = spatialSearchSummaryDTO.getIds();
		SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
		spatialSearchDTO.setFilterType(FilterType.FIDS);
		FilterFidsDTO filterFidsDTO = null;
		List<EgovMap> data = null;
		
		int patchSize = 1000;
		int fromIndex = 0;
		int toIndex = patchSize;
		while(fromIndex < ids.size()) {
			if(toIndex > ids.size()) {
				toIndex = ids.size();
			}
			filterFidsDTO = new FilterFidsDTO();
			filterFidsDTO.setFids(ids.subList(fromIndex, toIndex));
			spatialSearchDTO.setFilterFidsDTO(filterFidsDTO);
			
			data = spatialSearchService.listAll(dataId, spatialSearchDTO);
			
			for(int i=0, len=data.size(); i < len; i++) {
				EgovMap map = data.get(i);
				Row row = sheet.createRow(rowIndex++);
				for(Object key : map.keySet()) {
					if(StringUtils.equalsIgnoreCase(key.toString(), geometryColumn)) {
						IGeometry geom = null;
						IGeometry geometry = reader.execute(map.get(key).toString());
						geometry.setSRSName(fromSrid);
						
						extent.union(geometry.extent());
						
						if(StringUtils.isNotBlank(toSrid) && !StringUtils.equals(fromSrid, toSrid)) {
							geom = srsTrans.convert(geometry);
						}
						else {
							geom = geometry;
						}
						Coordinates coords = geom.getCoordinates();
						if(coords.size() == 1) {
							Coordinate coord = coords.get(0);
							row.createCell(columnIndex.get("x")).setCellValue(coord.getX());
							row.createCell(columnIndex.get("y")).setCellValue(coord.getY());
						}
					}
					else {
						if(columnIndex.get(key) != null && map.get(key) != null) {
							row.createCell(columnIndex.get(key)).setCellValue(map.get(key).toString());;
						}
					}
				}
			}
			fromIndex += patchSize;
			toIndex += patchSize;
		}
		
		FileUtils.createDirectories(path);
		String dataAlias = kwsData.getDataAlias().replace('/',  '_');
		String excelFileName = dataAlias + "(" + dataId + ")";
		String filePath = path + File.separator + excelFileName + ".xlsx";
		FileUtils.createFile(filePath);
		OutputStream os = new FileOutputStream(filePath);
		workbook.write(os);
		workbook.dispose();
		workbook.close();
		os.close();
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExcelExportService#export(kr.co.gitt.kworks.cmmn.dto.SpatialSearchSummaryDTO, boolean, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public void export(SpatialSearchSummaryDTO spatialSearchSummaryDTO, boolean isFieldName, String path, String toSrid, Extent extent) throws Exception {
		String fromSrid = messageSource.getMessage("Map.Projection", null, Locale.getDefault());
		SRSTransform srsTrans = null;
		srsTrans = new SRSTransform();
		
		// 소스 좌표계
		ISpatialReferenceSystem sourceSrs = DefaultSRS.get(fromSrid, 0, 0);
		// 목표 좌표계
		ISpatialReferenceSystem targetSrs = DefaultSRS.get(toSrid, 0, 0);
		srsTrans.set(sourceSrs, targetSrs);
		
		String dataId = spatialSearchSummaryDTO.getDataId();
		
		SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
		Sheet sheet = workbook.createSheet();
		
		int rowIndex = 0;
		int cellIndex = 0;
		Map<String, Integer> columnIndex = new HashMap<String, Integer>();
		String geometryColumn = null;

		Row headerRow = null;
		if(isFieldName) {
			headerRow = sheet.createRow(rowIndex++);
		}
		
		KwsData kwsData = dataService.selectOneData(dataId);
		List<KwsDataField> fields = kwsData.getKwsDataFields();
		for(KwsDataField kwsDataField : fields) {
			// PK_AT = 'Y'인 컬럼 패스
			if(StringUtils.equals(kwsDataField.getPkAt(), "Y")) {
				continue;
			}
			// FIELD_TY = 'Geometry'인 컬럼 패스
			else if(kwsDataField.getFieldTy().equals(FieldTy.GEOMETRY)) {
				geometryColumn = kwsDataField.getFieldId();
				continue;
			}
			
			if(headerRow != null) {
				Cell cell = headerRow.createCell(cellIndex);
				cell.setCellValue(kwsDataField.getFieldAlias());
			}
			columnIndex.put(CamelUtil.convert2CamelCase((String) kwsDataField.getFieldId()), cellIndex++);
		}
		
		 if(headerRow != null) {
		 headerRow.createCell(cellIndex).setCellValue("X좌표");
		 }
		 columnIndex.put("x", cellIndex++);
		
		 if(headerRow != null) {
		 headerRow.createCell(cellIndex).setCellValue("Y좌표");
		 }
		 columnIndex.put("y", cellIndex++);
		
		WKTReader reader = new WKTReader();
		
		List<Long> ids = spatialSearchSummaryDTO.getIds();
		SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
		spatialSearchDTO.setFilterType(FilterType.FIDS);
		FilterFidsDTO filterFidsDTO = null;
		List<EgovMap> data = null;
		
		int patchSize = 1000;
		int fromIndex = 0;
		int toIndex = patchSize;
		while(fromIndex < ids.size()) {
			if(toIndex > ids.size()) {
				toIndex = ids.size();
			}
			filterFidsDTO = new FilterFidsDTO();
			filterFidsDTO.setFids(ids.subList(fromIndex, toIndex));
			spatialSearchDTO.setFilterFidsDTO(filterFidsDTO);
			
			data = spatialSearchService.listAll(dataId, spatialSearchDTO);
			
			for(int i=0, len=data.size(); i < len; i++) {
				EgovMap map = data.get(i);
				Row row = sheet.createRow(rowIndex++);
				for(Object key : map.keySet()) {
					if(StringUtils.equalsIgnoreCase(key.toString(), geometryColumn)) {
						IGeometry geom = null;
						IGeometry geometry = reader.execute(map.get(key).toString());
						geometry.setSRSName(fromSrid);
						
						extent.union(geometry.extent());
						
						if(StringUtils.isNotBlank(toSrid) && !StringUtils.equals(fromSrid, toSrid)) {
							geom = srsTrans.convert(geometry);
						}
						else {
							geom = geometry;
						}
						Coordinates coords = geom.getCoordinates();
						if(coords.size() == 1) {
							Coordinate coord = coords.get(0);
							row.createCell(columnIndex.get("x")).setCellValue(coord.getX());
							row.createCell(columnIndex.get("y")).setCellValue(coord.getY());
						}
					}
					else {
						if(columnIndex.get(key) != null && map.get(key) != null) {
							row.createCell(columnIndex.get(key)).setCellValue(map.get(key).toString());;
						}
					}
				}
			}
			fromIndex += patchSize;
			toIndex += patchSize;
		}
		
		FileUtils.createDirectories(path);
		String dataAlias = kwsData.getDataAlias().replace('/',  '_');
		String excelFileName = dataAlias + "(" + dataId + ")";
		String filePath = path + File.separator + excelFileName + ".xlsx";
		FileUtils.createFile(filePath);
		OutputStream os = new FileOutputStream(filePath);
		workbook.write(os);
		workbook.dispose();
		workbook.close();
		os.close();
	}
	
}
