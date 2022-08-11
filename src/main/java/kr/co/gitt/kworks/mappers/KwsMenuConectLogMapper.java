package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsMenuConectLog;

/////////////////////////////////////////////
/// @class KwsMenuConectLogMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsMenuConectLogMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 2:18:20 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 메뉴 접속 로그 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsMenuConectLogMapper {

	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsMenuConectLog
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsMenuConectLog kwsMenuConectLog);

	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 로그 전체 카운트
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
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
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsMenuConectLog> listSearch(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listMenuLogExcel
	/// @brief 함수 간략한 설명 : 엑셀다운로드용 리스트 조회
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
