package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsStatsMastr;

/////////////////////////////////////////////
/// @class KwsStatsMastrMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsStatsMastrMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 28. 오전 11:56:57 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 통계지도 마스터 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsStatsMastrMapper {

	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsMastr
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsStatsMastr selectOne(KwsStatsMastr kwsStatsMastr);
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsMastr
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsStatsMastr> list(KwsStatsMastr kwsStatsMastr);
	
	/////////////////////////////////////////////
	/// @fn groupByIemYear
	/// @brief 함수 간략한 설명 : 목록 검색 (년도별)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsStatsMastr> groupByIemYear();
	
}
