package kr.co.gitt.kworks.service.cmmn;



/////////////////////////////////////////////
/// @class ErrorService
/// kr.co.gitt.kworks.service.cmmn \n
///   ㄴ ErrorService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 9. 오후 4:58:04 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 에러 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface ErrorService {

	/////////////////////////////////////////////
	/// @fn addError
	/// @brief 함수 간략한 설명 : 에러 등록 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exception
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addError(Exception exception);
	
}
