package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.model.CtyPksdAs;

/////////////////////////////////////////////
/// @class CtyPksdAsMapper
/// kr.co.gitt.kworks.projects.gn.mappers \n
///   ㄴ CtyPksdAsMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 4. 26. 오후 4:17:45 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 공원 구역 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface CtyPksdAsMapper {

	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 공원구역 - 조경화단 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyPksdAs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	List<CtyPksdAs> listScap(CtyPksdAs ctyPksdAs);
	
	/////////////////////////////////////////////
	/// @fn listAthl
	/// @brief 함수 간략한 설명 : 공원구역 - 운동시설 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyPksdAs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	List<CtyPksdAs> listAthl(CtyPksdAs ctyPksdAs);
	
	/////////////////////////////////////////////
	/// @fn listPlay
	/// @brief 함수 간략한 설명 : 공원구역 - 유희시설 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyPksdAs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	List<CtyPksdAs> listPlay(CtyPksdAs ctyPksdAs);
	
}
