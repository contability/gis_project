package kr.co.gitt.kworks.mappers;

import kr.co.gitt.kworks.model.KwsUserEnvrn;

/////////////////////////////////////////////
/// @class KwsUserEnvrnMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsUserEnvrnMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 2:22:08 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 환경 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsUserEnvrnMapper {

	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param userId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsUserEnvrn selectOne(String userId);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserEnvrn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsUserEnvrn kwsUserEnvrn);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserEnvrn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(KwsUserEnvrn kwsUserEnvrn);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserEnvrn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	//public Integer delete(KwsUserEnvrn kwsUserEnvrn);
	public Integer delete(String userId);
	
}
