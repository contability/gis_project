package kr.co.gitt.kworks.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SpatialType;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterSpatialDTO;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsDataField.FieldTy;
import kr.co.gitt.kworks.model.KwsDataField.IndictTy;
import kr.co.gitt.kworks.projects.gn.dto.RoadRegisterBBoxSearchDTO;
import kr.co.gitt.kworks.service.spatial.ArcgisBugfixService;
import kr.co.gitt.kworks.service.spatial.QueryGeneratorService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTWriter;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 도로대장 검색 저장소
 * @author kwangsu.lee
 *
 */
@Repository("roadRegisterSearchRepository")
public class RoadRegisterSearchResository {
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());

	/// JdbcTemplate 
	@Resource
	JdbcTemplate jdbcTemplate;
	
	/// 쿼리 생성 서비스
	@Resource
	QueryGeneratorService queryGeneratorService;
	
	/// 메세지 소스
	@Resource(name="messageSource")
    MessageSource messageSource;
	
	/// 아크 버그 패치
	@Resource
	ArcgisBugfixService arcgisBugfixService;

	
	public List<Long> listAllSummary(KwsData kwsData, String whereSql) {
		String sql = createSummarySql(kwsData, whereSql, false);
		List<Long> ids = null;
		try {
			ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong(1);
				}
			});
		}
		catch(Exception e) {
			try {
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
			catch(Exception ex) {
				sql = createSummarySql(kwsData, whereSql, true);
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
		}
		return ids;
	}

	private String createSummarySql(KwsData kwsData, String whereSql, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append(createSummarySelectColumnSql(kwsData));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSql(kwsData, whereSql));
		sql.append(createSelectOrderBy(kwsData, "FTR_IDN", "ASC"));
		return sql.toString();
	}
	
	private String createSummarySelectColumnSql(KwsData kwsData) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT T.FTR_IDN ");
		
		return sql.toString();
	}
	
	private String createSelectFromSql(KwsData kwsData) {
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM ").append(kwsData.getDataId()).append(" T ");
		return sql.toString();
	}
	
	private String createSelectWhereSql(KwsData kwsData, String whereSql) {
		if(StringUtils.isNotBlank(whereSql)) {
			return " WHERE " + whereSql;
		}
		return "";
	}
	
	private String createSelectOrderBy(KwsData kwsData, String field, String direction) {
		StringBuffer sql = new StringBuffer();

		if(StringUtils.isNotBlank(field)) {
			sql.append(" ORDER BY ").append("T.").append(field);
			if(StringUtils.isNotBlank(direction)) {
				sql.append(" ").append(direction);
			}
		} else {
			sql.append(" ORDER BY T.FTR_IDN ");
		}
		
		return sql.toString();
	}
	
	public List<Long> listAllSummary(KwsData kwsData, List<String> featureIds, String sortField, String sortDirection) {
		String sql = createSummarySql(kwsData, featureIds, sortField, sortDirection, false);
		List<Long> ids = null;
		try {
			ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong(1);
				}
			});
		}
		catch(Exception e) {
			try {
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
			catch(Exception ex) {
				sql = createSummarySql(kwsData, featureIds, sortField, sortDirection, true);
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
		}
		return ids;
	}
	
	private String createSummarySql(KwsData kwsData, List<String> featureIds, String sortField, String sortDirection, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append(createSummarySelectColumnSql(kwsData));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSql(kwsData, featureIds));
		sql.append(createSelectOrderBy(kwsData, sortField, sortDirection));
		return sql.toString();
	}
	
	private String createSelectWhereSql(KwsData kwsData, List<String> featureIds) {
		StringBuffer sql = new StringBuffer();
		if(featureIds != null && featureIds.size() > 0) {
			sql.append("WHERE T.FTR_IDN").append(" IN (");
			for(int i=0; i < featureIds.size(); i++) {
				if(i >= 1000) {
					break;
				}
				if(i!=0) {
					sql.append(", ");
				}
				sql.append(featureIds.get(i));
			}
			sql.append(") ");
		}
		return sql.toString();
	}
	
	public List<EgovMap> listAll(KwsData kwsData, List<String> featureIds, boolean isOriginValue, String sortField, String sortDirection) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		String sql = createSelectSql(kwsData, featureIds, isOriginValue, sortField, sortDirection);
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> map : list) {
			EgovMap egovMap = new EgovMap();
			for(Entry<String, Object> entry : map.entrySet()) {
				if(StringUtils.equals(geometryName, entry.getKey())) {
					egovMap.put(entry.getKey(), arcgisBugfixService.repairWKT(kwsData.getDataId(), entry.getValue().toString()));
				}
				else {
					egovMap.put(entry.getKey(), entry.getValue());
				}
			}
			result.add(egovMap);
		}

		return result;
	}
	
	private String createSelectSql(KwsData kwsData, List<String> featureIds, boolean isOriginValue, String sortField, String sortDirection) {
		StringBuffer sql = new StringBuffer();

		if (isOriginValue)
			sql.append(createSelectColumnSql(kwsData));
		else
			sql.append(createSelectViewColumnSql(kwsData));

		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSql(kwsData, featureIds));
		sql.append(createSelectOrderBy(kwsData, sortField, sortDirection));
		
		return sql.toString();
	}
	
	private String createSelectColumnSql(KwsData kwsData) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/ ");
		List<KwsDataField> kwsDataFields = kwsData.getKwsDataFields();
		for(int i=0, len=kwsDataFields.size(); i < len; i++) {
			KwsDataField kwsDataField = kwsDataFields.get(i);
			if(i != 0) {
				sql.append(", ");
			}
			
			IndictTy incidtTy = kwsDataField.getIndictTy();
			switch (incidtTy) {
			case WKT : 
				sql.append(createSelectColumnWktSql(kwsDataField));
				break;
			default : 
				sql.append(createSelectColumnBasisSql(kwsDataField));
				break;
			}
		}
		return sql.toString();
	}
	
	private String createSelectViewColumnSql(KwsData kwsData) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/ ");
		List<KwsDataField> kwsDataFields = kwsData.getKwsDataFields();
		for(int i=0, len=kwsDataFields.size(); i < len; i++) {
			KwsDataField kwsDataField = kwsDataFields.get(i);
			if(i != 0) {
				sql.append(", ");
			}
			
			IndictTy incidtTy = kwsDataField.getIndictTy();
			switch (incidtTy) {
			case HIDE :
			case BASIS :
				sql.append(createSelectColumnBasisSql(kwsDataField));
				break;
			case DATE_FROM_STRING :
				sql.append(createSelectColumnDateFromStringSql(kwsDataField));
				break;
			case DOMAIN :
				sql.append(createSelectColumnDomainSql(kwsDataField));
				break;
			case CURRENCY : 
				sql.append(createSelectColumnCurrencySql(kwsDataField));
				break;
			case CUSTOM :
				sql.append(createSelectColumnCustomSql(kwsDataField));
				break;
			case WKT : 
				sql.append(createSelectColumnWktSql(kwsDataField));
				break;
			default : 
				break;
			}
		}
		return sql.toString();
	}
	
	private String createSelectColumnBasisSql(KwsDataField kwsDataField) {
		StringBuffer sql = new StringBuffer();
		sql.append("T.").append(kwsDataField.getFieldId());
		return sql.toString();
	}
	
	private String createSelectColumnDateFromStringSql(KwsDataField kwsDataField) {
		return queryGeneratorService.createSelectColumnDateFromStringSql(kwsDataField);
	}
	
	private String createSelectColumnDomainSql(KwsDataField kwsDataField) {
		StringBuffer sql = new StringBuffer();
		sql.append("(SELECT CODE_NM FROM KWS_DOMN_CODE WHERE USE_AT = 'Y' AND DOMN_ID = '");
		sql.append(kwsDataField.getDomnId());
		sql.append("' AND CODE_ID = ");
		sql.append("T.").append(kwsDataField.getFieldId());
		sql.append(") AS ");
		sql.append(kwsDataField.getFieldId());
		return sql.toString();
	}
	
	private String createSelectColumnCurrencySql(KwsDataField kwsDataField) {
		return queryGeneratorService.createSelectColumnCurrencySql(kwsDataField);
	}
	
	private String createSelectColumnCustomSql(KwsDataField kwsDataField) {
		StringBuffer sql = new StringBuffer();
		sql.append(kwsDataField.getCustomSql()).append(" AS ").append(kwsDataField.getFieldId());
		return sql.toString();
	}
	
	private String createSelectColumnWktSql(KwsDataField kwsDataField) {
		return queryGeneratorService.createSelectColumnWktSql(kwsDataField.getFieldId());
	}
	
	public List<EgovMap> listAll(KwsData kwsData, String whereSql, boolean isOriginValue, String sortField, String sortDirection) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		String sql = createSelectSql(kwsData, whereSql, isOriginValue, sortField, sortDirection);
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> map : list) {
			EgovMap egovMap = new EgovMap();
			for(Entry<String, Object> entry : map.entrySet()) {
				if(StringUtils.equals(geometryName, entry.getKey())) {
					egovMap.put(entry.getKey(), arcgisBugfixService.repairWKT(kwsData.getDataId(), entry.getValue().toString()));
				}
				else {
					egovMap.put(entry.getKey(), entry.getValue());
				}
			}
			result.add(egovMap);
		}

		return result;
	}

	private String createSelectSql(KwsData kwsData, String whereSql, boolean isOriginValue, String sortField, String sortDirection) {
		StringBuffer sql = new StringBuffer();

		if (isOriginValue)
			sql.append(createSelectColumnSql(kwsData));
		else
			sql.append(createSelectViewColumnSql(kwsData));

		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSql(kwsData, whereSql));
		sql.append(createSelectOrderBy(kwsData, sortField, sortDirection));
		
		return sql.toString();
	}

	public List<Long> listAllSummary(KwsData kwsData, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO, String whereSql) {
		String sql = createSummarySql(kwsData, roadRegisterBBoxSearchDTO, whereSql, false);
		List<Long> ids = null;
		try {
			ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong(1);
				}
			});
		}
		catch(Exception e) {
			try {
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
			catch(Exception ex) {
				sql = createSummarySql(kwsData, roadRegisterBBoxSearchDTO, whereSql, true);
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
		}
		return ids;
	}

	private String createSummarySql(KwsData kwsData, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO, String whereSql, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append(createSpatialSelectColumnSql(kwsData));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereBboxSql(kwsData, roadRegisterBBoxSearchDTO, whereSql, isReverse));
		sql.append(createSelectOrderBy(kwsData, null, null));
		return sql.toString();
	}
	
	private String createSpatialSelectColumnSql(KwsData kwsData) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/ ");

		sql.append("T.").append("FTR_IDN").append(" ");
		
		return sql.toString();
	}
	
	private String createSelectWhereBboxSql(KwsData kwsData, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO, String whereSql, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();

		GeometryFactory geometryFactory = new GeometryFactory();
		
		Coordinate[] coordinates = new Coordinate[5];
		coordinates[0] = new Coordinate(roadRegisterBBoxSearchDTO.getXmin(), roadRegisterBBoxSearchDTO.getYmin());
		coordinates[1] = new Coordinate(roadRegisterBBoxSearchDTO.getXmax(), roadRegisterBBoxSearchDTO.getYmin());
		coordinates[2] = new Coordinate(roadRegisterBBoxSearchDTO.getXmax(), roadRegisterBBoxSearchDTO.getYmax());
		coordinates[3] = new Coordinate(roadRegisterBBoxSearchDTO.getXmin(), roadRegisterBBoxSearchDTO.getYmax());
		coordinates[4] = new Coordinate(roadRegisterBBoxSearchDTO.getXmin(), roadRegisterBBoxSearchDTO.getYmin());
		Polygon polygon = geometryFactory.createPolygon(coordinates);
		
		WKTWriter wktWriter = new WKTWriter();
		String wkt = wktWriter.write(polygon);
		
		FilterSpatialDTO filterSpatialDTO = new FilterSpatialDTO();
		filterSpatialDTO.setSpatialType(SpatialType.INTERSECTS);
		filterSpatialDTO.setWkt(wkt);
		
		sql.append(createSelectWhereSpatialSql(kwsData, filterSpatialDTO, isReverse));

		if (whereSql != null && whereSql.length() > 0) {
			sql.append(" AND ");
			sql.append(whereSql);
		}
			
		if(StringUtils.isNotBlank(sql.toString())) {
			return " WHERE " + sql.toString();
		}
		return "";
	}

	private String createSelectWhereSpatialSql(KwsData kwsData, FilterSpatialDTO filterSpatialDTO, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		
		SpatialType spatialType = filterSpatialDTO.getSpatialType();
		if(spatialType != null) {
			List<String> geometryColumnNames = getGeometryColumnName(kwsData);
			
			String geometryColumnName = null;
			if(geometryColumnNames.size() == 1) {
				geometryColumnName = geometryColumnNames.get(0);
			}
			else if(geometryColumnNames.size() == 0) {
				logger.warn("No Geometry Column : " + kwsData.getDataId());
				throw new RuntimeException("No Geometry Column : " + kwsData.getDataId());
			}
			else {
				logger.warn("Geometry Column is not the only one : " + kwsData.getDataId());
				throw new RuntimeException("Geometry Column is not the only one : " + kwsData.getDataId());
			}
			
			switch (spatialType) {
				case INTERSECTS :
					sql.append(queryGeneratorService.createSelectWhereSpatialIntersectsSql(geometryColumnName, filterSpatialDTO.getWkt(), isReverse));
					break;
				case WITHIN :
					sql.append(queryGeneratorService.createSelectWhereSpatialWithinSql(geometryColumnName, filterSpatialDTO.getWkt(), isReverse));
					break;
				default : 
					break;
			}
		}
		return sql.toString();
	}
	
	private List<String> getGeometryColumnName(KwsData kwsData) {
		List<String> geometryColumnNames = new ArrayList<String>();
		List<KwsDataField> kwsDataFields = kwsData.getKwsDataFields();
		for(int i=0, len=kwsDataFields.size(); i < len; i++) {
			KwsDataField kwsDataField = kwsDataFields.get(i);
			
			FieldTy fieldTy = kwsDataField.getFieldTy();
			if(fieldTy == FieldTy.GEOMETRY) {
				geometryColumnNames.add(kwsDataField.getFieldId());
			}
		}
		return geometryColumnNames;
	}	

	public List<Long> listDistinct(KwsData kwsData, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO, String field) {
		String sql = createDistinctSql(kwsData, roadRegisterBBoxSearchDTO, field, false);
		List<Long> ids = null;
		try {
			ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong(1);
				}
			});
		}
		catch(Exception e) {
			try {
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
			catch(Exception ex) {
				sql = createDistinctSql(kwsData, roadRegisterBBoxSearchDTO, field, true);
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
		}
		return ids;
	}

	private String createDistinctSql(KwsData kwsData, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO, String field, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append(createSpatialSelectDistinctSql(kwsData, field));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereBboxSql(kwsData, roadRegisterBBoxSearchDTO, field + " > 0", isReverse));
		sql.append(createSelectOrderBy(kwsData, field, "ASC"));
		return sql.toString();
	}
	
	private String createSpatialSelectDistinctSql(KwsData kwsData, String field) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/ ");
		sql.append("DISTINCT T.").append(field).append(" ");
		
		return sql.toString();
	}

	public List<Long> videoAllSummary(KwsData kwsData, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO, String whereSql) {
		String sql = createVideoSummarySql(kwsData, roadRegisterBBoxSearchDTO, whereSql, false);
		List<Long> ids = null;
		try {
			ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong(1);
				}
			});
		}
		catch(Exception e) {
			try {
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
			catch(Exception ex) {
				sql = createVideoSummarySql(kwsData, roadRegisterBBoxSearchDTO, whereSql, true);
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
		}
		return ids;
	}

	private String createVideoSummarySql(KwsData kwsData, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO, String whereSql, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append(createVideoSelectColumnSql(kwsData));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereBboxSql(kwsData, roadRegisterBBoxSearchDTO, whereSql, isReverse));
		sql.append(createSelectOrderBy(kwsData, kwsData.getRoadRegField(), "asc"));
		return sql.toString();
	}
	
	private String createVideoSelectColumnSql(KwsData kwsData) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/ ");

		sql.append("T.").append("PATH_IDN").append(" ");
		
		return sql.toString();
	}
	
	public List<Long> videoAllSummary(KwsData kwsData, List<String> featureIds, String sortField, String sortDirection) {
		String sql = createVideoSql(kwsData, featureIds, sortField, sortDirection, false);
		List<Long> ids = null;
		try {
			ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong(1);
				}
			});
		}
		catch(Exception e) {
			try {
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
			catch(Exception ex) {
				sql = createVideoSql(kwsData, featureIds, sortField, sortDirection, true);
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
		}
		return ids;
	}
	
	private String createVideoSql(KwsData kwsData, List<String> featureIds, String sortField, String sortDirection, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append(createVideoSelectColumnSql(kwsData));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereVideoSql(kwsData, featureIds));
		sql.append(createSelectVideoOrderBy(kwsData, sortField, sortDirection));
		return sql.toString();
	}
	
	private String createSelectWhereVideoSql(KwsData kwsData, List<String> featureIds) {
		StringBuffer sql = new StringBuffer();
		if(featureIds != null && featureIds.size() > 0) {
			sql.append("WHERE T.PATH_IDN").append(" IN (");
			for(int i=0; i < featureIds.size(); i++) {
				if(i >= 1000) {
					break;
				}
				if(i!=0) {
					sql.append(", ");
				}
				sql.append(featureIds.get(i));
			}
			sql.append(") ");
		}
		return sql.toString();
	}
	
	private String createSelectVideoOrderBy(KwsData kwsData, String field, String direction) {
		StringBuffer sql = new StringBuffer();

		if(StringUtils.isNotBlank(field)) {
			sql.append(" ORDER BY ").append("T.").append(field);
			if(StringUtils.isNotBlank(direction)) {
				sql.append(" ").append(direction);
			}
		} else {
			sql.append(" ORDER BY T.PATH_IDN ");
		}
		
		return sql.toString();
	}
	
	public List<EgovMap> videoAll(KwsData kwsData, List<String> featureIds, boolean isOriginValue, String sortField, String sortDirection) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		String sql = createSelectVideoSql(kwsData, featureIds, isOriginValue, sortField, sortDirection);
		
		String layerId = kwsData.getRoadRegAttr();
		String idField = kwsData.getRoadRegField();
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> map : list) {
			EgovMap egovMap = new EgovMap();
			for(Entry<String, Object> entry : map.entrySet()) {
				if(StringUtils.equals(geometryName, entry.getKey())) {
					egovMap.put(entry.getKey(), arcgisBugfixService.repairWKT(kwsData.getDataId(), entry.getValue().toString()));
				}
				else if (StringUtils.equals(idField.toLowerCase(), entry.getKey())) {
					List<String> times = new ArrayList<String>();
					String tmSql = createSelectTimeLineSql(layerId, idField, entry.getValue().toString());
					List<Map<String, Object>> rows = jdbcTemplate.queryForList(tmSql);
					for(Map<String, Object> row : rows) {
						for(Entry<String, Object> data : row.entrySet()) {
							if(StringUtils.equals("inte_sec", data.getKey())) {
								times.add(data.getValue().toString());
							}							
						}
					}
					
					egovMap.put(entry.getKey(), entry.getValue());
					egovMap.put("intervalTimes", times);
				}
				else {
					egovMap.put(entry.getKey(), entry.getValue());
				}
			}
			result.add(egovMap);
		}

		return result;
	}
	
	private String createSelectVideoSql(KwsData kwsData, List<String> featureIds, boolean isOriginValue, String sortField, String sortDirection) {
		StringBuffer sql = new StringBuffer();

		if (isOriginValue)
			sql.append(createVideoSelectColumnSql(kwsData));
		else
			sql.append(createSelectViewColumnSql(kwsData));

		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereVideoSql(kwsData, featureIds));
		sql.append(createSelectVideoOrderBy(kwsData, sortField, sortDirection));
		
		return sql.toString();
	}
	
	private String createSelectTimeLineSql(String dataId, String fieldId, String featureId) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("SELECT INTE_SEC FROM ");
		sql.append(dataId);
		sql.append(" WHERE ");
		sql.append(fieldId);
		sql.append(" = ");
		sql.append(featureId);
		sql.append(" ORDER BY NODE_IDX ASC");
		
		return sql.toString();
	}
	
	public Map<String, Object> selectOneVideo(KwsData kwsData, String featureId) throws Exception {
		
		String sql = createSelectVideoSql(kwsData, featureId);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		
		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}
	
	private String createSelectVideoSql(KwsData kwsData, String featureId) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT T.VIDEO_NM ");
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereOneVideoSql(kwsData, featureId));
		
		return sql.toString();
	}
	
	private String createSelectWhereOneVideoSql(KwsData kwsData, String featureId) {
		StringBuffer sql = new StringBuffer();
		if(featureId != null && featureId.length() > 0) {
			sql.append("WHERE T.PATH_IDN").append(" IN (");
			sql.append(featureId);
			sql.append(") ");
		}
		return sql.toString();
	}	
	
	public List<EgovMap> listAllGeometry(KwsData kwsData, String whereSql) {
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String sql = createGeometrySql(kwsData, whereSql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		
		for(Map<String, Object> map : list) {
			EgovMap egovMap = new EgovMap();
			for(Entry<String, Object> entry : map.entrySet()) {
				egovMap.put(entry.getKey(), entry.getValue());
			}
			result.add(egovMap);
		}
		
		return result;
	}
	
	private String createGeometrySql(KwsData kwsData, String whereSql) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/ P.FTR_IDN, P.IDN, ");
		sql.append(queryGeneratorService.createSelectColumnWktSql("P", "GEOM"));
		sql.append(" FROM ").append(kwsData.getDataId()).append(" T");
//		sql.append(" INNER JOIN ").append(kwsData.getDataId()).append("_PART P ON (T.ROT_NAM=P.ROT_NAM AND T.ROT_IDN=P.ROT_IDN AND T.SEC_IDN=P.SEC_IDN)");
		sql.append(" INNER JOIN RDST_PART_AS P ON (T.ROT_NAM=P.ROT_NAM AND T.ROT_IDN=P.ROT_IDN AND T.SEC_IDN=P.SEC_IDN)");	// Lks : 도로구역 파트 - 하드코딩 문제 해결해야함.
		sql.append(createSelectWhereSql(kwsData, whereSql));
		sql.append(" ORDER BY P.FTR_IDN ASC, P.IDN ASC ");		
		
		return sql.toString();
	}
	
	public List<EgovMap> listAllGeometry(KwsData kwsData, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO) {
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String sql = createGeometrySql(kwsData, roadRegisterBBoxSearchDTO, false);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		
		for(Map<String, Object> map : list) {
			EgovMap egovMap = new EgovMap();
			for(Entry<String, Object> entry : map.entrySet()) {
				egovMap.put(entry.getKey(), entry.getValue());
			}
			result.add(egovMap);
		}
		
		return result;
	}
	
	private String createGeometrySql(KwsData kwsData, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO, boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/ P.FTR_IDN, P.IDN, ");
		sql.append(queryGeneratorService.createSelectColumnWktSql("P", "GEOM"));
		sql.append(" FROM ").append(kwsData.getDataId()).append(" T");
//		sql.append(" INNER JOIN ").append(kwsData.getDataId()).append("_PART P ON (T.ROT_NAM=P.ROT_NAM AND T.ROT_IDN=P.ROT_IDN AND T.SEC_IDN=P.SEC_IDN)");
		sql.append(" INNER JOIN RDST_PART_AS P ON (T.ROT_NAM=P.ROT_NAM AND T.ROT_IDN=P.ROT_IDN AND T.SEC_IDN=P.SEC_IDN)");	// Lks : 도로구역 파트 - 하드코딩 문제 해결해야함.
		
		sql.append(createSelectWhereBboxSql(kwsData, roadRegisterBBoxSearchDTO, isReverse));
		sql.append(" ORDER BY P.FTR_IDN ASC, P.IDN ASC ");		
		
		return sql.toString();
	}
	
	private String createSelectWhereBboxSql(KwsData kwsData, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();

		GeometryFactory geometryFactory = new GeometryFactory();
		
		Coordinate[] coordinates = new Coordinate[5];
		coordinates[0] = new Coordinate(roadRegisterBBoxSearchDTO.getXmin(), roadRegisterBBoxSearchDTO.getYmin());
		coordinates[1] = new Coordinate(roadRegisterBBoxSearchDTO.getXmax(), roadRegisterBBoxSearchDTO.getYmin());
		coordinates[2] = new Coordinate(roadRegisterBBoxSearchDTO.getXmax(), roadRegisterBBoxSearchDTO.getYmax());
		coordinates[3] = new Coordinate(roadRegisterBBoxSearchDTO.getXmin(), roadRegisterBBoxSearchDTO.getYmax());
		coordinates[4] = new Coordinate(roadRegisterBBoxSearchDTO.getXmin(), roadRegisterBBoxSearchDTO.getYmin());
		Polygon polygon = geometryFactory.createPolygon(coordinates);
		
		WKTWriter wktWriter = new WKTWriter();
		String wkt = wktWriter.write(polygon);
		
		FilterSpatialDTO filterSpatialDTO = new FilterSpatialDTO();
		filterSpatialDTO.setSpatialType(SpatialType.INTERSECTS);
		filterSpatialDTO.setWkt(wkt);
		
		sql.append(createSelectWhereSpatialSql(kwsData, filterSpatialDTO, isReverse));
			
		if(StringUtils.isNotBlank(sql.toString())) {
			return " WHERE " + sql.toString();
		}
		return "";
	}
	
	public List<Long> listAllPnu(KwsData kwsData, String wkt) {
		String sql = createPnuSql(kwsData, wkt, true);
		List<Long> ids = null;
		try {
			ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong(1);
				}
			});
		}
		catch(Exception e) {
			try {
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
			catch(Exception ex) {
				sql = createPnuSql(kwsData, wkt, false);
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
		}
		return ids;
	}
	
	private String createPnuSql(KwsData kwsData, String wkt, boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/ T.PNU");
		
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereWktSql(kwsData, wkt, isReverse));
		sql.append(createSelectOrderBy(kwsData, "PNU", "ASC"));
		return sql.toString();
	}

	private String createSelectWhereWktSql(KwsData kwsData, String wkt, boolean isReverse) {
		StringBuffer sql = new StringBuffer();

		FilterSpatialDTO filterSpatialDTO = new FilterSpatialDTO();
		filterSpatialDTO.setSpatialType(SpatialType.INTERSECTS);
		filterSpatialDTO.setWkt(wkt);
		
		sql.append(createSelectWhereSpatialSql(kwsData, filterSpatialDTO, isReverse));
			
		if(StringUtils.isNotBlank(sql.toString())) {
			return " WHERE " + sql.toString();
		}
		return "";
	}

	public List<Long> listAllPnu(KwsData kwsData, List<String> featureIds, String sortField, String sortDirection) {
		String sql = createPnuSql(kwsData, featureIds, sortField, sortDirection, false);
		List<Long> ids = null;
		try {
			ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong(1);
				}
			});
		}
		catch(Exception e) {
			try {
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
			catch(Exception ex) {
				sql = createPnuSql(kwsData, featureIds, sortField, sortDirection, true);
				ids = jdbcTemplate.query(sql, new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
			}
		}
		return ids;
	}
	
	private String createPnuSql(KwsData kwsData, List<String> featureIds, String sortField, String sortDirection, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT T.PNU");
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWherePnuSql(kwsData, featureIds));
		sql.append(createSelectOrderBy(kwsData, sortField, sortDirection));
		return sql.toString();
	}
	
	private String createSelectWherePnuSql(KwsData kwsData, List<String> featureIds) {
		StringBuffer sql = new StringBuffer();
		if(featureIds != null && featureIds.size() > 0) {
			sql.append("WHERE T.PNU").append(" IN (");
			for(int i=0; i < featureIds.size(); i++) {
				if(i >= 1000) {
					break;
				}
				if(i!=0) {
					sql.append(", ");
				}
				sql.append(featureIds.get(i));
			}
			sql.append(") ");
		}
		return sql.toString();
	}
	
	public List<EgovMap> selectByPnu(KwsData kwsData, List<String> featureIds, boolean isOriginValue, String sortField, String sortDirection) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		String sql = createSelectPnuSql(kwsData, featureIds, isOriginValue, sortField, sortDirection);
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> map : list) {
			EgovMap egovMap = new EgovMap();
			for(Entry<String, Object> entry : map.entrySet()) {
				if(StringUtils.equals(geometryName, entry.getKey())) {
					egovMap.put(entry.getKey(), arcgisBugfixService.repairWKT(kwsData.getDataId(), entry.getValue().toString()));
				}
				else {
					egovMap.put(entry.getKey(), entry.getValue());
				}
			}
			result.add(egovMap);
		}

		return result;
	}

	private String createSelectPnuSql(KwsData kwsData, List<String> featureIds, boolean isOriginValue, String sortField, String sortDirection) {
		StringBuffer sql = new StringBuffer();

		if (isOriginValue)
			sql.append(createSelectColumnSql(kwsData));
		else
			sql.append(createSelectViewColumnSql(kwsData));
		
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectPnuWhereSql(kwsData, featureIds));
		sql.append(createSelectPnuOrderBy(kwsData, sortField, sortDirection));
		
		return sql.toString();
	}
	
	private String createSelectPnuWhereSql(KwsData kwsData, List<String> featureIds) {
		StringBuffer sql = new StringBuffer();
		if(featureIds != null && featureIds.size() > 0) {
			sql.append("WHERE T.PNU").append(" IN (");
			for(int i=0; i < featureIds.size(); i++) {
				if(i >= 1000) {
					break;
				}
				if(i!=0) {
					sql.append(", ");
				}
				sql.append(featureIds.get(i));
			}
			sql.append(") ");
		}
		return sql.toString();
	}
	
	private String createSelectPnuOrderBy(KwsData kwsData, String field, String direction) {
		StringBuffer sql = new StringBuffer();

		if(StringUtils.isNotBlank(field)) {
			sql.append(" ORDER BY ").append("T.").append(field);
			if(StringUtils.isNotBlank(direction)) {
				sql.append(" ").append(direction);
			}
		} else {
			sql.append(" ORDER BY T.PNU ");
		}
		
		return sql.toString();
	}	
	
	public EgovMap selectOneData(KwsData kwsData, String ftrIdn, boolean isOriginValue) {
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String sql = createSelectOneSql(kwsData, ftrIdn, isOriginValue);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		
		for(Map<String, Object> map : list) {
			EgovMap egovMap = new EgovMap();
			for(Entry<String, Object> entry : map.entrySet()) {
				egovMap.put(entry.getKey(), entry.getValue());
			}
			result.add(egovMap);
		}
		
		if (result.size() <= 0)
			return null;
		
		return result.get(0);
	}	
	
	private String createSelectOneSql(KwsData kwsData, String ftrIdn, boolean isOriginValue) {
		StringBuffer sql = new StringBuffer();

		if (isOriginValue)
			sql.append(createSelectColumnSql(kwsData));
		else
			sql.append(createSelectViewColumnSql(kwsData));

		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSql(kwsData, "T.FTR_IDN = '" + ftrIdn + "'"));
		
		return sql.toString();
	}
	
	public List<EgovMap> listAll(KwsData kwsData, List<String> selectFields, String sortField, String sortDirection) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		String sql = createSelectSql(kwsData, selectFields, sortField, sortDirection);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> map : list) {
			EgovMap egovMap = new EgovMap();
			for(Entry<String, Object> entry : map.entrySet()) {
				if(StringUtils.equals(geometryName, entry.getKey())) {
					egovMap.put(entry.getKey(), arcgisBugfixService.repairWKT(kwsData.getDataId(), entry.getValue().toString()));
				}
				else {
					egovMap.put(entry.getKey(), entry.getValue());
				}
			}
			result.add(egovMap);
		}

		return result;
	}
	
	private String createSelectSql(KwsData kwsData, List<String> selectFields, String sortField, String sortDirection) {
		StringBuffer sql = new StringBuffer();

		sql.append(createSelectColumnSql(kwsData, selectFields));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectOrderBy(kwsData, sortField, sortDirection));
		
		return sql.toString();
	}	
	
	private String createSelectColumnSql(KwsData kwsData, List<String> selectFields) {
		StringBuffer sql = new StringBuffer();
		
		int count = 0;
		sql.append("SELECT ");
		List<KwsDataField> kwsDataFields = kwsData.getKwsDataFields();
		for(int i=0, len=kwsDataFields.size(); i < len; i++) {
			KwsDataField kwsDataField = kwsDataFields.get(i);
			if (!selectFields.contains(kwsDataField.getFieldId()))
				continue;
			
			if(count != 0) {
				sql.append(", ");
			}
			
			IndictTy incidtTy = kwsDataField.getIndictTy();
			switch (incidtTy) {
			case WKT : 
				sql.append(createSelectColumnWktSql(kwsDataField));
				break;
			default : 
				sql.append(createSelectColumnBasisSql(kwsDataField));
				break;
			}
			
			count++;
		}
		
		return sql.toString();
	}	
}