package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.RdtRtcnDt;

/////////////////////////////////////////////
/// @class RdtRtcnDtMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ RdtRtcnDtMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 2:45:38 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 노선 연결 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface RdtRtcnDtMapper {

	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdtRtcnDt> listAll(Long ftrIdn);
	
}
