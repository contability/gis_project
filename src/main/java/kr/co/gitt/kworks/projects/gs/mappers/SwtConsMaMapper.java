package kr.co.gitt.kworks.projects.gs.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gs.model.SwtConsMa;

/////////////////////////////////////////////
/// @class SwtConsMaMapper
/// kr.co.gitt.kworks.projects.gs.mappers \n
///   ㄴ SwtConsMaMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 3. 오전 10:50:16 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 공사대장 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface SwtConsMaMapper {
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 하수 공사대장 리스트 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SwtConsMa> list(SwtConsMa swtConsMa);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 하수 공사대장 단 건 검색 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public SwtConsMa selectOne(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 하수 공사대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(SwtConsMa swtConsMa);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 하수 공사대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(SwtConsMa swtConsMa);

}
