package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsStatsValu;

/////////////////////////////////////////////
/// @class KwsStatsValuMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsStatsValuMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 28. 오전 11:58:52 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 통계 값 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsStatsValuMapper {

	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsValu
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsStatsValu> list(KwsStatsValu kwsStatsValu);
	
	/////////////////////////////////////////////
	/// @fn minValu
	/// @brief 함수 간략한 설명 : 최소 값
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsValu
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsStatsValu minValu(KwsStatsValu kwsStatsValu);
	
	/////////////////////////////////////////////
	/// @fn maxValu
	/// @brief 함수 간략한 설명 : 최대 값
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsValu
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsStatsValu maxValu(KwsStatsValu kwsStatsValu);

}
