package kr.co.gitt.kworks.service.log;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsMenuConectLog;
import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class MenuConnectLogService
/// kr.co.gitt.kworks.service.log \n
///   ㄴ MenuConnectLogService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 4. 오후 2:42:33 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 메뉴 로그 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface MenuConnectLogService {

	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 접속 로그 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsMenuConectLog
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsMenuConectLog kwsMenuConectLog) throws FdlException;
	
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
	public List<KwsMenuConectLog> listSearch(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listMenuLogExcel
	/// @brief 함수 간략한 설명 : 엑셀 다운로드용 메뉴 로그 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsMenuConectLog> listMenuLogExcel(SearchDTO searchDTO);
	
}
