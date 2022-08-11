package kr.co.gitt.kworks.projects.dh.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.dh.model.EttCpsvDt;

/////////////////////////////////////////////
/// @class EttCpsvDtMapper
/// kr.co.gitt.kworks.projects.dh.mappers \n
///   ㄴ EttCpsvDtMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 4. 26. 오후 12:15:34 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 측량표지현황조사 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface EttCpsvDtMapper {
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 측량표지현황조사 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettCpsvDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<EttCpsvDt> list(EttCpsvDt ettCpsvDt);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 측량표지현황조사 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettCpsvDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public EttCpsvDt selectOne(EttCpsvDt ettCpsvDt);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 측량표지현황조사 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettCpsvDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(EttCpsvDt ettCpsvDt);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 측량표지현황 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettCpsvDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(EttCpsvDt ettCpsvDt);
	
}
