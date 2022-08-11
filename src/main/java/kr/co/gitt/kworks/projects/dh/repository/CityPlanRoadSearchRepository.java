package kr.co.gitt.kworks.projects.dh.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsData.GeomTy;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsDataField.IndictTy;
import kr.co.gitt.kworks.service.spatial.ArcgisBugfixService;
import kr.co.gitt.kworks.service.spatial.QueryGeneratorService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("dhPlanRoadSearchRepository")
@Profile({"dh_dev", "dh_oper"})
public class CityPlanRoadSearchRepository 
{
	/**
	 * 로거
	 */
	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 쿼리 생성 서비스
	 */
	@Resource
	QueryGeneratorService queryGeneratorService;
	
	/**
	 * JdbcTemplate 
	 */
	@Resource
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 메세지 소스
	 */
	@Resource(name="messageSource")
    MessageSource messageSource;
	
	/// 아크 버그 패치
	@Resource
	ArcgisBugfixService arcgisBugfixService;
	
	
	private String createSummarySql(KwsData kwsData, String idnField, String whereSql, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append(createSummaryColumnSql(idnField));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSql(whereSql));
		sql.append(createSelectOrderBy(kwsData, idnField, "ASC"));
		return sql.toString();
	}
	
	private String createSummaryColumnSql(String idnField) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT T.").append(idnField).append(" ");
		
		return sql.toString();
	}	
	
	private String createSelectFromSql(KwsData kwsData) 
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM ").append(kwsData.getDataId()).append(" T ");
		return sql.toString();
	}
	
	private String createSelectWhereSql(String where) 
	{
		if(StringUtils.isNotBlank(where.toString())) {
			return " WHERE " + where.toString();
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
	
	private String createSummarySql(KwsData kwsData, String idnField, List<String> featureIds, String sortField, String sortDirection, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append(createSummaryColumnSql(idnField));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSql(kwsData, idnField, featureIds));
		sql.append(createSelectOrderBy(kwsData, sortField, sortDirection));
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
	
	private String createSelectSql(KwsData kwsData, String idnField, List<String> featureIds, String sortField, String sortDirection, boolean isOriginValue) {
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
		
		if (kwsData.getGeomTy() != null) {
			sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/");
		}
		else {
			sql.append("SELECT ");
		}
		List<KwsDataField> kwsDataFields = kwsData.getKwsDataFields();
		for(int i=0, len=kwsDataFields.size(); i < len; i++) {
			KwsDataField kwsDataField = kwsDataFields.get(i);
			IndictTy indictTy = kwsDataField.getIndictTy();
			
			if(i != 0 && indictTy != IndictTy.VIEW) {
				sql.append(", ");
			}
			
			switch (indictTy) {
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
	
	private String createSelectColumnWktSql(KwsDataField kwsDataField) 
	{
		return queryGeneratorService.createSelectColumnWktSql(kwsDataField.getFieldId());
	}
	
	private String createSelectColumnBasisSql(KwsDataField kwsDataField) 
	{
		StringBuffer sql = new StringBuffer();
		IndictTy indictTy = kwsDataField.getIndictTy();
		if (indictTy != IndictTy.VIEW)
			sql.append("T.").append(kwsDataField.getFieldId());
		
		return sql.toString();
	}

	private String createSelectViewColumnSql(KwsData kwsData) {
		StringBuffer sql = new StringBuffer();
		
		if (kwsData.getGeomTy() != null) {
			sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/ ");
		}
		else {
			sql.append("SELECT ");
		}
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
			case VIEW :
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
		String customSql = kwsDataField.getCustomSql();
		
		StringBuffer sql = new StringBuffer();
		if (customSql!= null && customSql.length() > 0) {
			sql.append(customSql).append(" AS ").append(kwsDataField.getFieldId());
		}
		else
			sql.append("T.").append(kwsDataField.getFieldId());
		
		return sql.toString();
	}
	
	public List<EgovMap> listAll(KwsData kwsData, String idnField, List<String> featureIds, String sortField, String sortDirection, boolean isOriginValue) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		String sql = createSelectSql(kwsData, idnField, featureIds, sortField, sortDirection, isOriginValue);
		
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
	
	private String createSelectSql(KwsData kwsData, String where, boolean isOrigin) {
		StringBuffer sql = new StringBuffer();
		
		if(isOrigin) {
			sql.append(createSelectColumnSql(kwsData));
		}
		else {
			sql.append(createSelectViewColumnSql(kwsData));
		}
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSql(where));
		
		return sql.toString();
	}	
	
	/**
	 * 단건조회 결과를 반환.
	 * 
	 * @param kwsData - 레이어
	 * @param where - 조건절
	 * @param isOrigin - 도메인 적용 여부
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectOne(KwsData kwsData, String where, boolean isOrigin) throws Exception {
		EgovMap result = new EgovMap();
		
		String sql = createSelectSql(kwsData, where, isOrigin);
		
		try {
			Map<String, Object> map = jdbcTemplate.queryForMap(sql);
			if (map != null && map.size() > 0) {
				for(Entry<String, Object> entry : map.entrySet()) {
					result.put(entry.getKey(), entry.getValue());
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
	
	
	/**
	 * 이력조회 결과를 반환.
	 * 
	 * @param kwsData - 레이어
	 * @param where - 조건절
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectList(KwsData kwsData, String where, boolean isOrigin) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String sql = createSelectSql(kwsData, where, isOrigin);
		
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
	
}
