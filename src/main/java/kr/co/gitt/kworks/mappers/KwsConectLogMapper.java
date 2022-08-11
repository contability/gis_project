package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsConectLog;

/////////////////////////////////////////////
/// @class KwsConectLogMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsConectLogMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 10. 20. 오전 10:03:05 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 접속로그 맵퍼입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsConectLogMapper {

	/////////////////////////////////////////////
	/// @fn listToday
	/// @brief 함수 간략한 설명 : 목록검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsConectLog
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsConectLog> list(KwsConectLog kwsConectLog);
	
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
	public List<KwsConectLog> listSearch(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listToday
	/// @brief 함수 간략한 설명 : 금일 목록을 시간으로 group by 해서 카운터한 리스트
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsConectLog> listTodayGroupByHourCount();
	
	/////////////////////////////////////////////
	/// @fn checkSession
	/// @brief 함수 간략한 설명 : 세션 등록 여부 체크 
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
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsConectLog
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsConectLog kwsConectLog);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsConectLog
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(KwsConectLog kwsConectLog);
	
	/////////////////////////////////////////////
	/// @fn nowConectCount
	/// @brief 함수 간략한 설명 : 현재 시스템 접속자수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	
	public Integer nowConectCount();
	
	/////////////////////////////////////////////
	/// @fn listGroupByDay
	/// @brief 함수 간략한 설명 : 날짜별 그룹 카운터
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsConectLog> listGroupByDay(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listGroupByMonth
	/// @brief 함수 간략한 설명 : 달별 그룹 카운터
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsConectLog> listGroupByMonth(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listGroupByWeek
	/// @brief 함수 간략한 설명 : 요일별 그룹 카운터
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsConectLog> listGroupByWeek(SearchDTO searchDTO);
	
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
