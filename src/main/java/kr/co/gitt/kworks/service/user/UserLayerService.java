package kr.co.gitt.kworks.service.user;

import java.util.List;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.gitt.kworks.model.KwsUserLyr;

/////////////////////////////////////////////
/// @class UserLayerService
/// kr.co.gitt.kworks.service.user \n
///   ㄴ UserLayerService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 10. 18. 오후 12:00:10 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 레이어 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface UserLayerService {

	/////////////////////////////////////////////
	/// @fn listAllUserLayer
	/// @brief 함수 간략한 설명 : 사용자 레이어 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserLyr
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsUserLyr> listAllUserLayer(KwsUserLyr kwsUserLyr);
	
	/////////////////////////////////////////////
	/// @fn addUserLayer
	/// @brief 함수 간략한 설명 : 사용자 레이어 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserLyr
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addUserLayer(KwsUserLyr kwsUserLyr) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeUserLayer
	/// @brief 함수 간략한 설명 : 사용자 레이어 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param lyrNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeUserLayer(Long lyrNo);
	
}
