package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.model.RdtConsMa;

/////////////////////////////////////////////
/// @class RdtConsMaMapper
/// kr.co.gitt.kworks.projects.gn.mappers \n
///   ㄴ RdtConsMaMapper.java
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
public interface RdtConsMaMapper {
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 도로 공사대장 리스트 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdtConsMa> list(RdtConsMa rdtConsMa);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 도로 공사대장 단 건 검색 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public RdtConsMa selectOne(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 도로 공사대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(RdtConsMa rdtConsMa);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 도로 공사대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(RdtConsMa rdtConsMa);

}
