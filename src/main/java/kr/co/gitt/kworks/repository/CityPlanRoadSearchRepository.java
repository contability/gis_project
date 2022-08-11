package kr.co.gitt.kworks.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.cityplanroad.PlanRoadSearchDTO;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsDataField.IndictTy;
import kr.co.gitt.kworks.service.spatial.QueryGeneratorService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("cityPlanRoadSearchRepository")
public class CityPlanRoadSearchRepository {

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
	
	/**
	 * 조건검색 결과의 개수를 반환.
	 * @param kwsData
	 * @param where
	 * @return
	 */
	public Integer listCount(KwsData kwsData, String where) {
		String sql = createSelectCountSql(kwsData, where);
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	private String createSelectCountSql(KwsData kwsData, String where) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) ");
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSql(where));
		return sql.toString();
	}	
	
	private String createSelectFromSql(KwsData kwsData) {
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM ").append(kwsData.getDataId()).append(" T ");
		return sql.toString();
	}
	
	private String createSelectWhereSql(String where) {
		if(StringUtils.isNotBlank(where.toString())) {
			return " WHERE " + where.toString();
		}
		return "";
	}
	
	/**
	 * 조건검색 결과를 반환.
	 * @param kwsData
	 * @param searchDTO
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> list(KwsData kwsData, PlanRoadSearchDTO searchDTO, String where) throws Exception {
		
		String sql = createSelectPagingSql(kwsData, searchDTO, where);
		List<EgovMap> result = new ArrayList<EgovMap>();
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

	private String createSelectPagingSql(KwsData kwsData, PlanRoadSearchDTO searchDTO, String where) {
		String selectSql = createSelectSql(kwsData, searchDTO, where);
		return queryGeneratorService.createSelctPagingSql(selectSql, searchDTO);
	}
	
	private String createSelectSql(KwsData kwsData, PlanRoadSearchDTO searchDTO, String where) {
		StringBuffer sql = new StringBuffer();
		
		Boolean isOriginValue = searchDTO.getIsOriginValue();
		if(isOriginValue != null && isOriginValue) {
			sql.append(createSelectColumnSql(kwsData));
		}
		else {
			sql.append(createSelectViewColumnSql(kwsData));
		}
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSql(where));
		sql.append(createSelectOrderBy(searchDTO));
		
		return sql.toString();
	}
	
	private String createSelectColumnSql(KwsData kwsData) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/");
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
	
	private String createSelectColumnWktSql(KwsDataField kwsDataField) {
		return queryGeneratorService.createSelectColumnWktSql(kwsDataField.getFieldId());
	}
	
	private String createSelectColumnBasisSql(KwsDataField kwsDataField) {
		StringBuffer sql = new StringBuffer();
		sql.append("T.").append(kwsDataField.getFieldId());
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

	private String createSelectOrderBy(PlanRoadSearchDTO searchDTO) {
		StringBuffer sql = new StringBuffer();
		
		String sortOrdr = searchDTO.getSortOrdr();
		if(StringUtils.isNotBlank(sortOrdr)) {
			sql.append(" ORDER BY ").append("T.").append(sortOrdr);
			String sortDirection = searchDTO.getSortDirection();
			if(StringUtils.isNotBlank(sortDirection)) {
				sql.append(" ").append(sortDirection);
			}
		}
		
		return sql.toString();
	}
	
	/**
	 * 공간데이터 조회.
	 * @param kwsData
	 * @param geomNamne
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectGeometry(KwsData kwsData, String geomName, String where) throws Exception {
		String sql = createSelectSql(kwsData, geomName, where);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		
		EgovMap result = new EgovMap();
		for(Map<String, Object> map : list) {
			for(Entry<String, Object> entry : map.entrySet()) {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		
		return result;
	}

	private String createSelectSql(KwsData kwsData, String geomName, String where) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/");
		sql.append(queryGeneratorService.createSelectColumnWktSql(geomName));
		sql.append(createSelectFromSql(kwsData));
		sql.append(createSelectWhereSql(where));
		
		return sql.toString();
	}
	
	/**
	 * 단건조회 결과를 반환.
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
	 * 변경이력 조회 결과를 반환.
	 * @param kwsData - 레이어
	 * @param where - 조건절
	 * @param isOrigin - 도메인 적용 여부
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectHistory(KwsData kwsData, String where, boolean isOrigin) throws Exception {
		String sql = createSelectSql(kwsData, where, isOrigin);
		
		List<EgovMap> result = new ArrayList<EgovMap>();
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
