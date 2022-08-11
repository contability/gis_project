package kr.co.gitt.kworks.projects.dh.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.dh.model.BmlWellCheck;

/////////////////////////////////////////////
/// @class BmlWellCheckMapper
/// kr.co.gitt.kworks.projects.dh.mappers \n
///   ㄴ BmlWellCheckMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 2. 27. 오후 12:08:50 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 지하수관정 점검이력 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface BmlWellCheckMapper {

	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 지하수관정 점검이력 리스트
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellCheck
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<BmlWellCheck> list(BmlWellCheck bmlWellCheck);
	
	/////////////////////////////////////////////
	/// @fn selectOneChckImprmnHist
	/// @brief 함수 간략한 설명 : 지하수관정 점검정비이력 수정창
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chkIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public BmlWellCheck selectOne(String chkIdn);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 지하수관정 점검정비이력 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellCheck
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(BmlWellCheck bmlWellCheck);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 지하수관정 점검정비이력 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chkIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(String chkIdn);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 지하수관정 점검정비이력 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellCheck
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(BmlWellCheck bmlWellCheck);
	
}
