package kr.co.gitt.kworks.mappers;

import kr.co.gitt.kworks.model.KwsThemamapLyr;

/////////////////////////////////////////////
/// @class KwsThemamapLyrMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsThemamapLyrMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 23. 오전 9:53:25 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 주제도 레이어 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsThemamapLyrMapper {

	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsThemamapLyr
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsThemamapLyr kwsThemamapLyr);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsThemamapId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(Long kwsThemamapId);

}
