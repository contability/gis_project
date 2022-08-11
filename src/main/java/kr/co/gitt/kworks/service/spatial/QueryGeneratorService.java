package kr.co.gitt.kworks.service.spatial;

import kr.co.gitt.kworks.cmmn.dto.cityplanroad.PlanRoadSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterRelationDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.model.KwsDataField;

/////////////////////////////////////////////
/// @class QueryGeneratorService
/// kr.co.gitt.kworks.service.spatial \n
///   ㄴ QueryGeneratorService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 19. 오후 3:08:31 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 쿼리 생성 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface QueryGeneratorService {

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
	public String createSelectColumnDateFromStringSql(KwsDataField kwsDataField);
	
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
	public String createSelectColumnCurrencySql(KwsDataField kwsDataField);
	
	/////////////////////////////////////////////
	/// @fn createSelectColumnWktSql
	/// @brief 함수 간략한 설명 : 공간정보 항목 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDataField
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String createSelectColumnWktSql(String fieldId);

	/////////////////////////////////////////////
	/// @fn createSelectColumnWktSql
	/// @brief 함수 간략한 설명 : 공간정보 항목 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param prefix
	/// @param kwsDataField
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String createSelectColumnWktSql(String prefix, String fieldId);
	
	/////////////////////////////////////////////
	/// @fn createSelectWhereSpatialIntersectsSql
	/// @brief 함수 간략한 설명 : INTERSECTS 연산 조건 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param geometryColumnName
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String createSelectWhereSpatialIntersectsSql(String geometryColumnName, String wkt, Boolean isReverse);
	
	public String createSpatialIntersectsSql(String sourceGeometryColumnName, String targetGeometryName, Boolean isReverse);
	
	/////////////////////////////////////////////
	/// @fn createSelectWhereSpatialContainsSql
	/// @brief 함수 간략한 설명 : CONTAINS 연산 조건 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param geometryColumnName
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String createSelectWhereSpatialWithinSql(String geometryColumnName, String wkt, Boolean isReverse);
	
	/////////////////////////////////////////////
	/// @fn createSelectFromRelationIntersectsSql
	/// @brief 함수 간략한 설명 : 연관 검색 (Intersects) 조건 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param geometryColumnName
	/// @param pkColumnName
	/// @param filterRelationDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String createSelectFromRelationIntersectsSql(String geometryColumnName, String pkColumnName, FilterRelationDTO filterRelationDTO);
	
	/////////////////////////////////////////////
	/// @fn createSelectFromRelationWithinSql
	/// @brief 함수 간략한 설명 : 연관 검색 (Within) 조건 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param geometryColumnName
	/// @param pkColumnName
	/// @param filterRelationDTO
	/// @param buffer
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String createSelectFromRelationWithinSql(String geometryColumnName, String pkColumnName, FilterRelationDTO filterRelationDTO);
	
	/////////////////////////////////////////////
	/// @fn createSelectFromBufferSql
	/// @brief 함수 간략한 설명 : 버퍼 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param geometryColumnName
	/// @param buffer
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String createSelectFromBufferSql(String geometryColumnName, Integer buffer);
	
	/////////////////////////////////////////////
	/// @fn createSelectWhereRelationIntersectsSql
	/// @brief 함수 간략한 설명 : 연관 검색 조건 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param geometryColumnName
	/// @param pkColumnName
	/// @param filterRelationDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	//public String createSelectWhereRelationIntersectsSql(String geometryColumnName, String pkColumnName, FilterRelationDTO filterRelationDTO);
	
	/////////////////////////////////////////////
	/// @fn createGeometrySqlByWkt
	/// @brief 함수 간략한 설명 : WKT 를 Geometry 객체로 변환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wkt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String createGeometrySqlByWkt(String wkt);

	/////////////////////////////////////////////
	/// @fn createSelctPagingSql
	/// @brief 함수 간략한 설명 : 페이징 검색 문자열 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param selectSql
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String createSelctPagingSql(String selectSql, SpatialSearchDTO spatialSearchDTO);
	
	/**
	 * 도시계획도로 페이징  
	 * @param selectSql
	 * @param spatialSearchDTO
	 * @return
	 */
	public String createSelctPagingSql(String selectSql, PlanRoadSearchDTO searchDTO);	
}
