package kr.co.gitt.kworks.mappers;

import kr.co.gitt.kworks.model.RdtRnmgDt;

/////////////////////////////////////////////
/// @class RdtRnmgDtMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ RdtRnmgDtMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 2:44:21 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도로 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface RdtRnmgDtMapper {

	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdnCde
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public RdtRnmgDt selectOne(String rdnCde);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtRnmgDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(RdtRnmgDt rdtRnmgDt);
	
}
