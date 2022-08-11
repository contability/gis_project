package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsUserLyr;

/////////////////////////////////////////////
/// @class KwsUserLyrMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsUserLyrMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 10. 18. 오전 11:57:48 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 레이어 매퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsUserLyrMapper {

	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserLyr
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsUserLyr> listAll(KwsUserLyr kwsUserLyr);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserLyr
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsUserLyr kwsUserLyr);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param lyrNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(Long lyrNo);
	
	// 유저 아이디로 삭제
	public Integer deleteByUserId(String userId);
	
}
