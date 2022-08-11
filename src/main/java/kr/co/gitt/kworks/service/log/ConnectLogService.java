package kr.co.gitt.kworks.service.log;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsConectLog;
import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class ConnectLogService
/// kr.co.gitt.kworks.service.log \n
///   ㄴ ConnectLogService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 28. 오후 5:48:50 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 접속 로그 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface ConnectLogService {
	
	/////////////////////////////////////////////
	/// @fn checkSession
	/// @brief 함수 간략한 설명 : 세션 확인
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param sessionId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer checkSession(String sessionId);
	
	/////////////////////////////////////////////
	/// @fn login
	/// @brief 함수 간략한 설명 : 로그인 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsConectLog
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer login(KwsConectLog kwsConectLog) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn logout
	/// @brief 함수 간략한 설명 : 로그아웃 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsConectLog
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer logout(KwsConectLog kwsConectLog);
	
	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 로그 전체 카운트
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param SearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer listCount(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listSearch
	/// @brief 함수 간략한 설명 : 로그 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param SearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsConectLog> listSearch(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listConectLogExcel
	/// @brief 함수 간략한 설명 : 접속로그 엑셀 다운로드 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsConectLog> listConectLogExcel(SearchDTO searchDTO);
	
}
