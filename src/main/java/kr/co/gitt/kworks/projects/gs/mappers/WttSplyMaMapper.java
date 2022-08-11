package kr.co.gitt.kworks.projects.gs.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gs.model.WttSplyMa;

/////////////////////////////////////////////
/// @class WttSplyMaMapper
/// kr.co.gitt.kworks.projects.gs.mappers \n
///   ㄴ WttSplyMaMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 14. 오전 10:35:38 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 급수 공사대장 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface WttSplyMaMapper {
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 급수 공사대장 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSplyMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<WttSplyMa> list(WttSplyMa wttSplyMa);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 급수 공사대장 단 건 검색 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public WttSplyMa selectOne(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 급수 공사대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSplyMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(WttSplyMa wttSplyMa);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 급수 공사대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSplyMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(WttSplyMa wttSplyMa);
	
}
