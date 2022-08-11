package kr.co.gitt.kworks.mappers;

import kr.co.gitt.kworks.model.LpPaCbnd;

/////////////////////////////////////////////
/// @class LpPaCbndMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ LpPaCbndMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2018. 5. 11. 오전 10:51:44 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 연속지적도 맵퍼입니다.
///   -# 
/////////////////////////////////////////////
public interface LpPaCbndMapper {

	/////////////////////////////////////////////
	/// @fn lpPaCbndList
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 연속지적도 조회
	/// @param lpPaCbnd
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public LpPaCbnd lpPaCbndList(LpPaCbnd lpPaCbnd);
};
