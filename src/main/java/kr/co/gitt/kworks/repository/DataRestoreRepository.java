package kr.co.gitt.kworks.repository;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/////////////////////////////////////////////
/// @class DataRestoreRepository
/// kr.co.gitt.kworks.repository \n
///   ㄴ DataRestoreRepository.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 19. 오후 5:11:22 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 복원 저장소 입니다.
///   -# 
/////////////////////////////////////////////
@Repository("dataRestoreRepository")
public class DataRestoreRepository {

	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());

	/// Jdbc 템플릿
	@Resource
	JdbcTemplate jdbcTemplate;
	
	/////////////////////////////////////////////
	/// @fn count
	/// @brief 함수 간략한 설명 : 데이터 수 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Long count(String dataId) {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM "+dataId, Long.class);
	}
	
	/////////////////////////////////////////////
	/// @fn load
	/// @brief 함수 간략한 설명 : 데이터 로드
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param sourceDataId
	/// @param targetDataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public long load(String sourceDataId, String targetDataId) {
		long count = 0;
		
		// 공간 인덱스 삭제
		try {
			StringBuffer dropIndexSql = new StringBuffer();
			dropIndexSql.append("DROP INDEX ").append(targetDataId).append("_GEOM");
			jdbcTemplate.execute(dropIndexSql.toString());
		}
		catch(Exception se) {
			// 이전 연계 실패로 공간 인덱스가 삭제되었을 경우 없을 수도 있음.
			logger.warn(se.getMessage());
		}
		
		// 데이터 삭제
		StringBuffer deleteAllSql = new StringBuffer();
		deleteAllSql.append("DELETE FROM ").append(targetDataId);
		jdbcTemplate.update(deleteAllSql.toString());
		
		// 데이터 입력
		if(StringUtils.equalsIgnoreCase(targetDataId, "TL_SPBD_BULD")) {
			count = tlSpbdBuldInsert(sourceDataId, targetDataId);
		}
		else {
			count = commonInsert(sourceDataId, targetDataId);
		}
		
		// 공간 인덱스 생성
		StringBuffer createIndexSql = new StringBuffer();
		createIndexSql.append("CREATE INDEX ").append(targetDataId).append("_GEOM ON ");
		createIndexSql.append(targetDataId).append(" (").append("GEOM").append(")").append(" INDEXTYPE IS MDSYS.SPATIAL_INDEX ");
		jdbcTemplate.execute(createIndexSql.toString());
		
		return count;
	}
	
	/////////////////////////////////////////////
	/// @fn commonInsert
	/// @brief 함수 간략한 설명 : 공통 등록 SQL
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param sourceDataId
	/// @param targetDataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public long commonInsert(String sourceDataId, String targetDataId) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ").append(targetDataId);
		sql.append(" SELECT * FROM ").append(sourceDataId);
		return jdbcTemplate.update(sql.toString());
	}
	
	/////////////////////////////////////////////
	/// @fn tlSpbdBuldInsert
	/// @brief 함수 간략한 설명 : 도로명주소 건물 등록 SQL
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param sourceDataId
	/// @param targetDataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public long tlSpbdBuldInsert(String sourceDataId, String targetDataId) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ").append(targetDataId);
		sql.append(" SELECT T.*, ");
		sql.append("CASE ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '00' THEN 21 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '01' THEN 0 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '02' THEN 1 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '03' THEN 2 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '04' THEN 3 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '05' THEN 4 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '06' THEN 5 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '07' THEN 6 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '08' THEN 7 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '09' THEN 8 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '10' THEN 9 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '11' THEN 10 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '12' THEN 11 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '13' THEN 12 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '14' THEN 13 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '15' THEN 14 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '16' THEN 15 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '17' THEN 16 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '18' THEN 17 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '19' THEN 18 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '20' THEN 19 ");
		sql.append("WHEN SUBSTR(BDTYP_CD, 1, 2) = '21' THEN 20 ");
		sql.append("ELSE NULL END AS SUBTYPE, ");
		sql.append("POS_BUL_NM AS LABEL ");
		sql.append("FROM ").append(sourceDataId).append(" T");
		return jdbcTemplate.update(sql.toString());
	}
	
}
