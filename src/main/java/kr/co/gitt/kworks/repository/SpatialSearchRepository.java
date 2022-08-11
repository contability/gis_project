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
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterBboxDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterComparisonDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterComparisonDTO.ComparisonType;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterCqlDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterFidDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterFidsDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterRelationDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterSpatialDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO.FilterType;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsDataField.IndictTy;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.spatial.ArcgisBugfixService;
import kr.co.gitt.kworks.service.spatial.QueryGeneratorService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTWriter;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/////////////////////////////////////////////
/// @class SpatialSearchRepository
/// kr.co.gitt.kworks.repository \n
///   ㄴ SpatialSearchRepository.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | admin |
///    | Date | 2016. 8. 19. 오후 3:05:56 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 공간 검색 저장소 입니다. (단 일 테이블에 대한 검색 기능을 제공합니다.)
/// - 수정자 : 이승재, 2021.02.19, getPkColumnName, getGeometryColumnName kr.co.gitt.kworks.service.data.DataService로 이동
///         createSelectRelationInnerJoinSql, createSelectWhereRelationSql 수정
/////////////////////////////////////////////
@Repository("spatialSearchRepository")
public class SpatialSearchRepository {

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
	
	/// 데이터 서비스
	@Autowired
	DataService dataService;
	
	/////////////////////////////////////////////
	/// @fn listAllSummary
	/// @brief 함수 간략한 설명 : 요약 전체 목록 검색 (ID 목록만 반환)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<Long> listAllSummary(KwsData kwsData, SpatialSearchDTO spatialSearchDTO) {
		String sql = createSummarySql(kwsData, spatialSearchDTO, false);
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
				sql = createSummarySql(kwsData, spatialSearchDTO, true);
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

	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<EgovMap> listAll(KwsData kwsData, SpatialSearchDTO spatialSearchDTO, Boolean isReverse) throws Exception {
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		String sql = createSelectSql(kwsData, spatialSearchDTO, isReverse);
		List<EgovMap> result = new ArrayList<EgovMap>();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> map : list) {
			EgovMap egovMap = new EgovMap();
			for(Entry<String, Object> entry : map.entrySet()) {
				// 이승재, 2020.05.15
				// entry.getValue()가 null인경우 entry.getValue().toString()오류 발생
				// String.valueOf(entry.getValue())로 대체하여, null 처리
				// 이승재, 2020.05.29
				// entry.getValue()가 null이 아닐경우 Object로 egvMap에 put 되어야 함
				String entryValue = String.valueOf(entry.getValue());
				if(StringUtils.equals(geometryName, entry.getKey())) {
					egovMap.put(entry.getKey(), arcgisBugfixService.repairWKT(kwsData.getDataId(), entryValue));
				} else {
					if ("null".equals(entryValue)) {
						egovMap.put(entry.getKey(), entryValue);
					} else {
						egovMap.put(entry.getKey(), entry.getValue());
					}
				}
			}
			result.add(egovMap);
		}
		return result;
	}
	
	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 목록 수 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer listCount(KwsData kwsData, SpatialSearchDTO spatialSearchDTO, Boolean isReverse) {
		String sql = createSelectCountSql(kwsData, spatialSearchDTO, isReverse);
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 검색 (페이징)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<EgovMap> list(KwsData kwsData, SpatialSearchDTO spatialSearchDTO, Boolean isReverse) throws Exception {
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		String sql = createSelectPagingSql(kwsData, spatialSearchDTO, isReverse);
		List<EgovMap> result = new ArrayList<EgovMap>();
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

	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public EgovMap selectOne(KwsData kwsData, SpatialSearchDTO spatialSearchDTO, Boolean isReverse) throws Exception {
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		EgovMap egovMap = new EgovMap();
		String sql = createSelectSql(kwsData, spatialSearchDTO, isReverse);
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);
		for(Entry<String, Object> entry : map.entrySet()) {
			if(StringUtils.equals(geometryName, entry.getKey())) {
				egovMap.put(entry.getKey(), arcgisBugfixService.repairWKT(kwsData.getDataId(), entry.getValue().toString()));
			}
			else {
				egovMap.put(entry.getKey(), entry.getValue());
			}
		}
		return egovMap;
	}
	
	/////////////////////////////////////////////
	/// @fn createSummarySql
	/// @brief 함수 간략한 설명 : 요약 검색 Select 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSummarySql(KwsData kwsData, SpatialSearchDTO spatialSearchDTO, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append(createSummarySelectColumnSql(kwsData));
		sql.append(createSelectFromSql(kwsData, spatialSearchDTO));
		sql.append(createSelectWhereSql(kwsData, spatialSearchDTO, isReverse));
		sql.append(createSelectOrderBy(kwsData, spatialSearchDTO));
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn createSummarySelectColumnSql
	/// @brief 함수 간략한 설명 : 요약 Select 항목 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSummarySelectColumnSql(KwsData kwsData) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/ ");

		List<String> pkColumnNames = dataService.getPkColumnName(kwsData);
		
		if(pkColumnNames.size() == 1) {
			String pkColumnName = pkColumnNames.get(0);
			sql.append("T.").append(pkColumnName).append(" ");
		}
		else if(pkColumnNames.size() == 0) {
			logger.warn("No PK : " + kwsData.getDataId());
			throw new RuntimeException("No PK : " + kwsData.getDataId());
		}
		else {
			logger.warn("PK is not the only one : " + kwsData.getDataId());
			throw new RuntimeException("PK is not the only one : " + kwsData.getDataId());
		}
		
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn createSelectSql
	/// @brief 함수 간략한 설명 : Select 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectSql(KwsData kwsData, SpatialSearchDTO spatialSearchDTO, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		
		Boolean isOriginColumnValue = spatialSearchDTO.getIsOriginColumnValue();
		if(isOriginColumnValue != null && isOriginColumnValue) {
			sql.append(createSelectColumnSql(kwsData));
		}
		else {
			sql.append(createSelectViewColumnSql(kwsData));
		}
		sql.append(createSelectFromSql(kwsData, spatialSearchDTO));
		sql.append(createSelectWhereSql(kwsData, spatialSearchDTO, isReverse));
		sql.append(createSelectOrderBy(kwsData, spatialSearchDTO));
		return sql.toString();
	}
	
	/////////////////////////////////////////////
	/// @fn createSelectCountSql
	/// @brief 함수 간략한 설명 : Count Select 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectCountSql(KwsData kwsData, SpatialSearchDTO spatialSearchDTO, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) ");
		sql.append(createSelectFromSql(kwsData, spatialSearchDTO));
		sql.append(createSelectWhereSql(kwsData, spatialSearchDTO, isReverse));
		return sql.toString();
	}
	
	/////////////////////////////////////////////
	/// @fn createSelectPagingSql
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 공간 쿼리 페이징이 너무 성능이 느려서 사용 안함
	/// @param kwsData
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectPagingSql(KwsData kwsData, SpatialSearchDTO spatialSearchDTO, Boolean isReverse) {
		String selectSql = createSelectSql(kwsData, spatialSearchDTO, isReverse);
		return queryGeneratorService.createSelctPagingSql(selectSql, spatialSearchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn createSelectColumnSql
	/// @brief 함수 간략한 설명 : Select 항목 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectColumnSql(KwsData kwsData) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT /*+ INDEX(T ").append(kwsData.getDataId()).append("_GEOM)*/ ");
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
	
	/////////////////////////////////////////////
	/// @fn createSelectViewColumnSql
	/// @brief 함수 간략한 설명 : 조회용 Select 항목 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
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
	
	/////////////////////////////////////////////
	/// @fn createSelectColumnBasisSql
	/// @brief 함수 간략한 설명 : 기본 항목 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDataField
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectColumnBasisSql(KwsDataField kwsDataField) {
		StringBuffer sql = new StringBuffer();
		IndictTy indictTy = kwsDataField.getIndictTy();
		if (indictTy != IndictTy.VIEW)
			sql.append("T.").append(kwsDataField.getFieldId());
		
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn createSelectColumnDateFromStringSql
	/// @brief 함수 간략한 설명 : 문자열형 날짜 항목 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDataField
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectColumnDateFromStringSql(KwsDataField kwsDataField) {
		return queryGeneratorService.createSelectColumnDateFromStringSql(kwsDataField);
	}

	/////////////////////////////////////////////
	/// @fn createSelectColumnDomainSql
	/// @brief 함수 간략한 설명 : 도메인 항목 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDataField
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
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

	/////////////////////////////////////////////
	/// @fn createSelectColumnCurrencySql
	/// @brief 함수 간략한 설명 : 통화 항목 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDataField
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectColumnCurrencySql(KwsDataField kwsDataField) {
		return queryGeneratorService.createSelectColumnCurrencySql(kwsDataField);
	}

	/////////////////////////////////////////////
	/// @fn createSelectColumnCustomSql
	/// @brief 함수 간략한 설명 : 관습 항목 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDataField
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectColumnCustomSql(KwsDataField kwsDataField) {
		StringBuffer sql = new StringBuffer();
		sql.append(kwsDataField.getCustomSql()).append(" AS ").append(kwsDataField.getFieldId());
		return sql.toString();
	}
	
	/////////////////////////////////////////////
	/// @fn createSelectColumnWktSql
	/// @brief 함수 간략한 설명 : 공간 정보 항목 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDataField
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectColumnWktSql(KwsDataField kwsDataField) {
		return queryGeneratorService.createSelectColumnWktSql(kwsDataField.getFieldId());
	}
	
	/////////////////////////////////////////////
	/// @fn createSelectFromSql
	/// @brief 함수 간략한 설명 : FROM 절 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectFromSql(KwsData kwsData, SpatialSearchDTO spatialSearchDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM ").append(kwsData.getDataId()).append(" T ");
		if(spatialSearchDTO.getFilterType() != null && spatialSearchDTO.getFilterType().equals(FilterType.RELATION)) {
			// 아래 2020.06.30일 수정에 따라 파라미터 변경
			sql.append(createSelectInnerJoinSql(kwsData, spatialSearchDTO));
		}
		return sql.toString();
	}
	
	// 아래 2020.06.30일 수정에 따라 파라미터 변경
	private String createSelectInnerJoinSql(KwsData kwsData, SpatialSearchDTO spatialSearchDTO) {
		FilterRelationDTO filterRelationDTO = spatialSearchDTO.getFilterRelationDTO();
		
		StringBuffer sql = new StringBuffer();
		
		String pkColumnName = null;
		List<String> pkColumnNames = dataService.getPkColumnName(kwsData);
		if(pkColumnNames.size() == 1) {
			pkColumnName = pkColumnNames.get(0);
		}
		else if(pkColumnNames.size() == 0) {
			logger.warn("No PK : " + kwsData.getDataId());
			throw new RuntimeException("No PK : " + kwsData.getDataId());
		}
		else {
			logger.warn("PK is not the only one : " + kwsData.getDataId());
			throw new RuntimeException("PK is not the only one : " + kwsData.getDataId());
		}
		
		List<String> geometryColumnNames = dataService.getGeometryColumnName(kwsData);
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
		
		// 이승재 2020.06.30
		// 시설물검색->연관검색->버퍼 1 (예 교량->1개 선택->연관검색->가로등 선택, 버퍼 1)시
		// 검색결과 이상
		// 기존에는
		// SELECT T.OBJECT FROM RDL_STLT_PS T INNER JOIN RDL_BRDG_AS R ON ST_INTERSECTION(T.GEOM, ST_BUFFER(R.GEOM, 1)) = 1 WHERE R.OBJECTID IN (8)
		// 쿼리가 실행됐는데 결과가 이상
		// 연관검색 일경우 아래처럼 쿼리가 생성 조회 되도록 수정
		// SELECT T.OBJECT FROM RDL_STLT_PS T INNER JOIN (SELECT * FROM RDL_BRDG_AS WHERE OBJECTID IN (8)) R ON ST_INTERSECTION(T.GEOM, ST_BUFFER(R.GEOM, 1)) = 1 WHERE R.OBJECTID IN (8)
		// 전체의 케이스를 테스트하지는 못했으며
		// 위 예의 케이스만 테스트 함
		FilterType filterType = spatialSearchDTO.getFilterType();
		if(filterType != null) {
			switch (filterType) {
				case RELATION : 
					sql.append(createSelectRelationInnerJoinSql(filterRelationDTO));
					break;
				default:
					sql.append(" INNER JOIN ").append(filterRelationDTO.getRelationDataId()).append(" R ");
					sql.append(" ON ");
					break;
			}
		} else {
			sql.append(" INNER JOIN ").append(filterRelationDTO.getRelationDataId()).append(" R ");
			sql.append(" ON ");
		}
				
		SpatialType spatialType = filterRelationDTO.getSpatialType();
		if(spatialType != null) {
			switch (spatialType) {
				case INTERSECTS :
					sql.append(queryGeneratorService.createSelectFromRelationIntersectsSql(geometryColumnName, pkColumnName, filterRelationDTO));
					break;
				case WITHIN :
					sql.append(queryGeneratorService.createSelectFromRelationWithinSql(geometryColumnName, pkColumnName, filterRelationDTO));
					break;
				default : 
					break;
			}
		}
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief 함수 간략한 설명 : 연관검색일 경우 join문 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @작성자 : 이승재, 2020.06.30
	/// @수정자
	///   - 이승재, 2021.02.19, 연관테이블의 PK컬럼명을 이용하여 쿼리 작성하도록 수정
	/// @param kwsData
	/// @param filterRelationDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Object createSelectRelationInnerJoinSql(FilterRelationDTO filterRelationDTO) {
		List<Long> fids = filterRelationDTO.getFids();
		String relatedDataId = filterRelationDTO.getRelationDataId();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" INNER JOIN ").append("(SELECT * FROM ").append(relatedDataId).append(" WHERE ");
				
		String pkColumnName = filterRelationDTO.getRelationDataPkColumnName();
		sql.append(pkColumnName);
		if(fids != null && fids.size() > 0) {
			sql.append(" IN ( ");
			for(int i=0, len=fids.size(); i < len; i++) {
				if(i >= 1000) {
					break;
				}
				if(i!=0) {
					sql.append(", ");
				}
				sql.append(fids.get(i));
			}
			sql.append(") ");
		}
		else if(filterRelationDTO.getFid() != null) {
			sql.append(" = ").append(filterRelationDTO.getFid());
		}
		else {
			logger.warn("No Value : " + relatedDataId);
		}
		
		sql.append(" ) R ON ");
		
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn createSelectWhereSql
	/// @brief 함수 간략한 설명 : 조건 절 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectWhereSql(KwsData kwsData, SpatialSearchDTO spatialSearchDTO, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		
		FilterType filterType = spatialSearchDTO.getFilterType();
		if(filterType != null) {
			switch (filterType) {
				case FID : 
					sql.append(createSelectWherePkSql(kwsData, spatialSearchDTO.getFilterFidDTO()));
					break;
				case FIDS : 
					sql.append(createSelectWherePksSql(kwsData, spatialSearchDTO.getFilterFidsDTO()));
					break;
				case COMPARISON : 
					sql.append(createSelectWhereComparisonSql(kwsData, spatialSearchDTO.getFilterComparisonDTO()));
					break;
				case BBOX : 
					sql.append(createSelectWhereBboxSql(kwsData, spatialSearchDTO.getFilterBboxDTO(), isReverse));
					break;
				case SPATIAL : 
					sql.append(createSelectWhereSpatialSql(kwsData, spatialSearchDTO.getFilterSpatialDTO(), isReverse));
					break;
				case RELATION : 
					sql.append(createSelectWhereRelationSql(kwsData, spatialSearchDTO.getFilterRelationDTO()));
					break;
				case CQL :
					sql.append(createSelectWhereCqlSql(kwsData, spatialSearchDTO.getFilterCqlDTO()));
			}
		}
		if(StringUtils.isNotBlank(sql.toString())) {
			return " WHERE " + sql.toString();
		}
		return "";
	}

	/////////////////////////////////////////////
	/// @fn createSelectWhereFidSql
	/// @brief 함수 간략한 설명 : PK 항목 조건 절 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param fid
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectWherePkSql(KwsData kwsData, FilterFidDTO filterFidDTO) {
		StringBuffer sql = new StringBuffer();
		Long fid = filterFidDTO.getFid();
		
		if(fid != null) {
			List<String> pkColumnNames = dataService.getPkColumnName(kwsData);
			if(pkColumnNames.size() == 1) {
				String pkColumnName = pkColumnNames.get(0);
				sql.append(pkColumnName).append(" = ").append(fid);
			}
			else if(pkColumnNames.size() == 0) {
				logger.warn("No PK : " + kwsData.getDataId());
				throw new RuntimeException("No PK : " + kwsData.getDataId());
			}
			else {
				logger.warn("PK is not the only one : " + kwsData.getDataId());
				throw new RuntimeException("PK is not the only one : " + kwsData.getDataId());
			}
		}
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn createSelectWherePksSql
	/// @brief 함수 간략한 설명 : PK 목록 항목 조건 절 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param fids
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Object createSelectWherePksSql(KwsData kwsData, FilterFidsDTO filterFidsDTO) {
		List<Long> fids = filterFidsDTO.getFids();
		StringBuffer sql = new StringBuffer();
		if(fids != null && fids.size() > 0) {
			List<String> pkColumnNames = dataService.getPkColumnName(kwsData);
			if(pkColumnNames.size() == 1) {
				String pkColumnName = pkColumnNames.get(0);
				sql.append(pkColumnName).append(" IN ( ");
				for(int i=0, len=fids.size(); i < len; i++) {
					if(i >= 1000) {
						break;
					}
					if(i!=0) {
						sql.append(", ");
					}
					sql.append(fids.get(i));
				}
				sql.append(") ");
			}
			else if(pkColumnNames.size() == 0) {
				logger.warn("No PK : " + kwsData.getDataId());
				throw new RuntimeException("No PK : " + kwsData.getDataId());
			}
			else {
				logger.warn("PK is not the only one : " + kwsData.getDataId());
				throw new RuntimeException("PK is not the only one : " + kwsData.getDataId());
			}
		}
		return sql.toString();
	}
	
	/////////////////////////////////////////////
	/// @fn createSelectWhereComparisonSql
	/// @brief 함수 간략한 설명 : 속성 정보 조건 절 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Object createSelectWhereComparisonSql(KwsData kwsData, FilterComparisonDTO filterComparisonDTO) {
		StringBuffer sql = new StringBuffer();
		
		ComparisonType comparisonType = filterComparisonDTO.getComparisonType();
		String comparisonProperty = filterComparisonDTO.getProperty();
		String comparisonValue = filterComparisonDTO.getValue();
		
		if(comparisonType != null && StringUtils.isNotBlank(comparisonProperty) && StringUtils.isNotBlank(comparisonValue)) {
			sql.append(comparisonProperty);
			switch (comparisonType) {
				case EQUAL_TO :
					sql.append(" = ");
					break;
				case LIKE :
					sql.append(" LIKE ");
					break;
				default : 
					break;
			}
			sql.append("'").append(comparisonValue).append("'");
		}
		
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn createSelectWhereBboxSql
	/// @brief 함수 간략한 설명 : BBOX 공간 정보 조건 절 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param filterBboxDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectWhereBboxSql(KwsData kwsData, FilterBboxDTO filterBboxDTO, Boolean isReverse) {
		GeometryFactory geometryFactory = new GeometryFactory();
		
		Coordinate[] coordinates = new Coordinate[5];
		coordinates[0] = new Coordinate(filterBboxDTO.getXmin(), filterBboxDTO.getYmin());
		coordinates[1] = new Coordinate(filterBboxDTO.getXmax(), filterBboxDTO.getYmin());
		coordinates[2] = new Coordinate(filterBboxDTO.getXmax(), filterBboxDTO.getYmax());
		coordinates[3] = new Coordinate(filterBboxDTO.getXmin(), filterBboxDTO.getYmax());
		coordinates[4] = new Coordinate(filterBboxDTO.getXmin(), filterBboxDTO.getYmin());
		Polygon polygon = geometryFactory.createPolygon(coordinates);
		
		WKTWriter wktWriter = new WKTWriter();
		String wkt = wktWriter.write(polygon);
		
		FilterSpatialDTO filterSpatialDTO = new FilterSpatialDTO();
		filterSpatialDTO.setSpatialType(SpatialType.INTERSECTS);
		filterSpatialDTO.setWkt(wkt);
		
		return createSelectWhereSpatialSql(kwsData, filterSpatialDTO, isReverse);
	}
	
	/////////////////////////////////////////////
	/// @fn createSelectWhereSpatialSql
	/// @brief 함수 간략한 설명 : 공간 정보 조건 절 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectWhereSpatialSql(KwsData kwsData, FilterSpatialDTO filterSpatialDTO, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		
		SpatialType spatialType = filterSpatialDTO.getSpatialType();
		if(spatialType != null) {
			List<String> geometryColumnNames = dataService.getGeometryColumnName(kwsData);
			
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
	
	/////////////////////////////////////////////
	/// @fn createSelectWhereRelationSql
	/// @brief 함수 간략한 설명 : 연관 검색 조건 절 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @수정자
	///   - 이승재, 2021.02.19, 연관테이블의 PK컬럼명을 이용하여 쿼리 작성하도록 수정
	/// @param kwsData
	/// @param filterRelationDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectWhereRelationSql(KwsData kwsData, FilterRelationDTO filterRelationDTO) {
		List<Long> fids = filterRelationDTO.getFids();
		StringBuffer sql = new StringBuffer();
		String pkColumnName = filterRelationDTO.getRelationDataPkColumnName();
		sql.append("R.").append(pkColumnName);
		if(fids != null && fids.size() > 0) {
			sql.append(" IN ( ");
			for(int i=0, len=fids.size(); i < len; i++) {
				if(i >= 1000) {
					break;
				}
				if(i!=0) {
					sql.append(", ");
				}
				sql.append(fids.get(i));
			}
			sql.append(") ");
		}
		else if(filterRelationDTO.getFid() != null) {
			sql.append(" = ").append(filterRelationDTO.getFid());
		}
		else {
			logger.warn("No Value : " + filterRelationDTO.getRelationDataId());
		}
		return sql.toString();
	}
	
	/////////////////////////////////////////////
	/// @fn createSelectWhereCqlSql
	/// @brief 함수 간략한 설명 : CQL 필터
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param filterCqlDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectWhereCqlSql(KwsData kwsData, FilterCqlDTO filterCqlDTO) {
		return filterCqlDTO.getCql();
	}
	
	/////////////////////////////////////////////
	/// @fn createSelectOrderBy
	/// @brief 함수 간략한 설명 : 정렬 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectOrderBy(KwsData kwsData, SpatialSearchDTO spatialSearchDTO) {
		StringBuffer sql = new StringBuffer();
		
		String sortOrdr = spatialSearchDTO.getSortOrdr();
		if(StringUtils.isNotBlank(sortOrdr)) {
			sql.append(" ORDER BY ").append("T.").append(sortOrdr);
			String sortDirection = spatialSearchDTO.getSortDirection();
			if(StringUtils.isNotBlank(sortDirection)) {
				sql.append(" ").append(sortDirection);
			}
		}
		else {
			List<String> pkColumnNames = dataService.getPkColumnName(kwsData);
			if(pkColumnNames.size() == 1) {
				String pkColumnName = pkColumnNames.get(0);
				sql.append(" ORDER BY ").append("T.").append(pkColumnName);
			}
			else if(pkColumnNames.size() == 0) {
				logger.warn("No PK : " + kwsData.getDataId());
				throw new RuntimeException("No PK : " + kwsData.getDataId());
			}
			else {
				logger.warn("PK is not the only one : " + kwsData.getDataId());
				throw new RuntimeException("PK is not the only one : " + kwsData.getDataId());
			}
		}
		return sql.toString();
	}
}
