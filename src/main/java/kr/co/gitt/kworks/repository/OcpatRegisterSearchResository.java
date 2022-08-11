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
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegisterSpatialSearchDTO;
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
 * 점용허가 검색 저장소
 * @author wongi.jo
 *
 */
@Repository("ocpatRegisterSearchResository")
public class OcpatRegisterSearchResository {
	
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

	
	private String createSelectFromSql(KwsData kwsData) {
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM ").append(kwsData.getDataId()).append(" T ");
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

	public List<Long> listAllSummary(KwsData kwsData, String idnField, String whereSql) {
		String sql = createSummarySql(kwsData, idnField, whereSql, false);
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
				sql = createSummarySql(kwsData, idnField, whereSql, true);
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
	
	private String createSummarySql(KwsData kwsData, String idnField, String whereSql, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append(createSummaryColumnSql(idnField));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSql(kwsData, whereSql));
		sql.append(createSelectOrderBy(kwsData, idnField, "ASC"));
		return sql.toString();
	}	
	
	public List<Long> listAllSummary(KwsData kwsData, String idnField, OcpatRegisterSpatialSearchDTO ocpatSpatialSearchDTO, String whereSql) {
		String sql = createSummarySql(kwsData, idnField, ocpatSpatialSearchDTO, whereSql, false);
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
				sql = createSummarySql(kwsData, idnField, ocpatSpatialSearchDTO, whereSql, true);
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

	private String createSummarySql(KwsData kwsData, String idnField, OcpatRegisterSpatialSearchDTO ocpatSpatialSearchDTO, String whereSql, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append(createSpatialSelectDistinctColumnSql(kwsData, idnField));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSpatialSql(kwsData, ocpatSpatialSearchDTO, whereSql, isReverse));
		sql.append(createSelectOrderBy(kwsData, idnField, "ASC"));
		return sql.toString();
	}
	
	private String createSpatialSelectDistinctColumnSql(KwsData kwsData, String idnField) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/ ");
		sql.append("DISTINCT T.").append(idnField);
		
		return sql.toString();
	}
	
	private String createSelectWhereSpatialSql(KwsData kwsData, OcpatRegisterSpatialSearchDTO ocpatSpatialSearchDTO, String whereSql, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();

		String wkt = null;
		if (ocpatSpatialSearchDTO.getWktArea() == null) {
			GeometryFactory geometryFactory = new GeometryFactory();
			Coordinate[] coordinates = new Coordinate[5];
			coordinates[0] = new Coordinate(ocpatSpatialSearchDTO.getXmin(), ocpatSpatialSearchDTO.getYmin());
			coordinates[1] = new Coordinate(ocpatSpatialSearchDTO.getXmax(), ocpatSpatialSearchDTO.getYmin());
			coordinates[2] = new Coordinate(ocpatSpatialSearchDTO.getXmax(), ocpatSpatialSearchDTO.getYmax());
			coordinates[3] = new Coordinate(ocpatSpatialSearchDTO.getXmin(), ocpatSpatialSearchDTO.getYmax());
			coordinates[4] = new Coordinate(ocpatSpatialSearchDTO.getXmin(), ocpatSpatialSearchDTO.getYmin());
			Polygon polygon = geometryFactory.createPolygon(coordinates);
			
			WKTWriter wktWriter = new WKTWriter();
			wkt = wktWriter.write(polygon);
		}
		else {
			wkt = ocpatSpatialSearchDTO.getWktArea();
		}
		
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
	
	private String createSelectOrderBy(KwsData kwsData, String field, String direction) {
		StringBuffer sql = new StringBuffer();

		if(StringUtils.isNotBlank(field)) {
			sql.append(" ORDER BY ").append("T.").append(field);
			if(StringUtils.isNotBlank(direction)) {
				sql.append(" ").append(direction);
			}
		} else {
			sql.append(" ORDER BY T.FTF_IDN ");
		}
		
		return sql.toString();
	}

	public List<EgovMap> listAllSummary(KwsData kwsData, String idnField, OcpatRegisterSpatialSearchDTO ocpatSpatialSearchDTO) {
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String sql = createSummarySql(kwsData, idnField, ocpatSpatialSearchDTO, false);
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
	
	private String createSummarySql(KwsData kwsData, String idnField, OcpatRegisterSpatialSearchDTO ocpatSpatialSearchDTO, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append(createSpatialSelectDistinctColumnSql(kwsData, idnField));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSpatialSql(kwsData, ocpatSpatialSearchDTO, null, isReverse));
		sql.append(createSelectOrderBy(kwsData, idnField, "ASC"));
		return sql.toString();
	}
	
	public List<EgovMap> listAllSummary(KwsData kwsData, String cdeField, String idnField, OcpatRegisterSpatialSearchDTO ocpatSpatialSearchDTO) {
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String sql = createSummarySql(kwsData, cdeField, idnField, ocpatSpatialSearchDTO, false);
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
	
	private String createSummarySql(KwsData kwsData, String cdeField, String idnField, OcpatRegisterSpatialSearchDTO ocpatSpatialSearchDTO, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append(createSpatialSelectDistinctColumnSql(kwsData, cdeField, idnField));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSpatialSql(kwsData, ocpatSpatialSearchDTO, null, isReverse));
		sql.append(createSelectOrderBy(kwsData, idnField, "ASC"));
		return sql.toString();
	}
	
	private String createSpatialSelectDistinctColumnSql(KwsData kwsData, String cdeField, String idnField) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/ ");
		sql.append("DISTINCT T.").append(idnField).append(", T.").append(cdeField);
		
		return sql.toString();
	}

	public List<Long> listAllSummary(KwsData kwsData, String idnField, List<String> featureIds, String sortField, String sortDirection) {
		String sql = createSummarySql(kwsData, idnField, featureIds, sortField, sortDirection, false);
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
				sql = createSummarySql(kwsData, idnField, featureIds, sortField, sortDirection, true);
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
	
	private String createSummarySql(KwsData kwsData, String idnField, List<String> featureIds, String sortField, String sortDirection, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append(createSummaryColumnSql(idnField));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSql(kwsData, idnField, featureIds));
		sql.append(createSelectOrderBy(kwsData, sortField, sortDirection));
		return sql.toString();
	}	
	
	private String createSummaryColumnSql(String idnField) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT T.").append(idnField).append(" ");
		
		return sql.toString();
	}	
	
	private String createSelectWhereSql(KwsData kwsData, String idnField, List<String> featureIds) {
		StringBuffer sql = new StringBuffer();
		if(featureIds != null && featureIds.size() > 0) {
			sql.append("WHERE T.").append(idnField).append(" IN (");
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
	
	public List<EgovMap> listAll(KwsData kwsData, String idnField, List<String> featureIds, boolean isOriginValue, String sortField, String sortDirection) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		String sql = createSelectSql(kwsData, idnField, featureIds, isOriginValue, sortField, sortDirection);
		
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
	
	private String createSelectSql(KwsData kwsData, String idnField, List<String> featureIds, boolean isOriginValue, String sortField, String sortDirection) {
		StringBuffer sql = new StringBuffer();

		if (isOriginValue)
			sql.append(createSelectColumnSql(kwsData));
		else
			sql.append(createSelectViewColumnSql(kwsData));

		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSql(kwsData, idnField, featureIds));
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
			case CUSTOM :
				sql.append(createSelectColumnCustomSql(kwsDataField));
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
	
	private String createSelectColumnWktSql(KwsDataField kwsDataField) {
		return queryGeneratorService.createSelectColumnWktSql(kwsDataField.getFieldId());
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
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		String customSql = kwsDataField.getCustomSql();
		
		StringBuffer sql = new StringBuffer();
		if (customSql!= null && customSql.length() > 0) {
			customSql = customSql.replace("KWorksKeyString", keyString);
			sql.append(customSql).append(" AS ").append(kwsDataField.getFieldId());
		}
		else
			sql.append("T.").append(kwsDataField.getFieldId());
		
		return sql.toString();
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
	
	private String createSelectWhereSql(KwsData kwsData, String whereSql) {
		if(StringUtils.isNotBlank(whereSql)) {
			return " WHERE " + whereSql;
		}
		return "";
	}
}