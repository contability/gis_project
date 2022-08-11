package kr.co.gitt.kworks.service.user;

import kr.co.gitt.kworks.model.KwsUserEnvrn;

/////////////////////////////////////////////
/// @class UserEnvironmentService
/// kr.co.gitt.kworks.service.user \n
///   ㄴ UserEnvironmentService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 17. 오후 12:49:50 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 환경 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface UserEnvironmentService {
	
	/////////////////////////////////////////////
	/// @fn selectOneUserEnvironment
	/// @brief 함수 간략한 설명 : 사용자 환경 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param userId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsUserEnvrn selectOneUserEnvironment(String userId);
	
	/////////////////////////////////////////////
	/// @fn persistUserEnvironment
	/// @brief 함수 간략한 설명 : 사용자 환경 영속화
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserEnvrn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer persistUserEnvironment(KwsUserEnvrn selectUserEnvrn, KwsUserEnvrn kwsUserEnvrn);
	
	/////////////////////////////////////////////
	/// @fn removeUserEnvironment
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param domnNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeUserEnvironment(KwsUserEnvrn kwsUserEnvrn);
}
