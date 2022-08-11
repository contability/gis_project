package kr.co.gitt.kworks.service.data;

import java.util.List;

import kr.co.gitt.kworks.dto.search.DataSearchDTO;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataCtgry;
import kr.co.gitt.kworks.model.KwsDataFieldCtgry;
import kr.co.gitt.kworks.model.KwsDataFtrCde;

/////////////////////////////////////////////
/// @class DataService
/// kr.co.gitt.kworks.service.data \n
///   ㄴ DataService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | admin |
///    | Date | 2016. 8. 18. 오전 11:13:56 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 서비스 입니다.
/// - 수정자 : 이승재, 2021.02.19, getPkColumnName, getGeometryColumnName 추가
/////////////////////////////////////////////
public interface DataService {
	
	/////////////////////////////////////////////
	/// @fn selectOneData
	/// @brief 함수 간략한 설명 : 데이터 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsData selectOneData(String dataId);
	
	/////////////////////////////////////////////
	/// @fn listAllDataCtgry
	/// @brief 함수 간략한 설명 : 데이터 카테고리 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsDataCtgry> listAllDataCtgry();
	
	/////////////////////////////////////////////
	/// @fn listAllData
	/// @brief 함수 간략한 설명 : 데이터 리스트 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsData> listAllData(DataSearchDTO dataSearchDTO);
	
	/////////////////////////////////////////////
	/// @fn listAllDataFieldCtgry
	/// @brief 함수 간략한 설명 : 데이터 필드 카테고리 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsDataFieldCtgry> listAllDataFieldCtgry();
	
	/////////////////////////////////////////////
	/// @fn listAllDataFtrCde
	/// @brief 함수 간략한 설명 : 데이터, 지형지물부호 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDataFtrCde
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsDataFtrCde> listAllDataFtrCde(KwsDataFtrCde kwsDataFtrCde);
	
	// Lks : 도로대장
	/////////////////////////////////////////////
	/// @fn selectOneRoadReg
	/// @brief 함수 간략한 설명 : 도로대장 데이터 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsData selectOneRoadReg(String dataId);
	
	/////////////////////////////////////////////
	/// @fn selectRoadRegByAttr
	/// @brief 함수 간략한 설명 : 도로대장을 속성으로 가지는 데이터 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsData> selectRoadRegByAttr(String dataId);
	
	/////////////////////////////////////////////
	/// @fn selectRoadRegByCode
	/// @brief 함수 간략한 설명 : 도로대장의 특정 코드에 해당하는 데이터 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsData> selectRoadRegByCode(String dataId, String typeCode);
	
	/////////////////////////////////////////////
	/// @fn getPkColumnName
	/// @brief 함수 간략한 설명 : PK 컬럼 명 검색
	/// @remark
	/// - 함수의 상세 설명 : kr.co.gitt.kworks.repository.SpatialSearchRepository에서 이동
	/// @param kwsData
	/// @return List<String>
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<String> getPkColumnName(KwsData kwsData);
	
	/////////////////////////////////////////////
	/// @fn getGeometryColumnName
	/// @brief 함수 간략한 설명 : 공간정보 컬럼 명 검색
	/// @remark
	/// - 함수의 상세 설명 : kr.co.gitt.kworks.repository.SpatialSearchRepository에서 이동
	/// @param kwsData
	/// @return List<String>
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<String> getGeometryColumnName(KwsData kwsData);
}
