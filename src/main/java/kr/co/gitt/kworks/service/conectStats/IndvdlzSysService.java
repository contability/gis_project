package kr.co.gitt.kworks.service.conectStats;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsSysConectLog;

/////////////////////////////////////////////
/// @class IndvdlzSysService
/// kr.co.gitt.kworks.service.conectStats \n
///   ㄴ IndvdlzSysService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 12. 28. 오후 5:30:37 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 개별시스템 접속 통계 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface IndvdlzSysService {
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param KwsSysConectLog
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsSysConectLog> list(KwsSysConectLog KwsSysConectLog);
	
	/////////////////////////////////////////////
	/// @fn listTodayGroupByHourCount
	/// @brief 함수 간략한 설명 : 금일 접속통계 현황
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Map<String, Object> listTodayGroupByHourCount();

	/////////////////////////////////////////////
	/// @fn listGroupByDayCount
	/// @brief 함수 간략한 설명 : 일별 접속통계 현황
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return
	/// @throws ParseException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Map<String, Object> listGroupByDayCount(SearchDTO searchDTO) throws ParseException;
	
	/////////////////////////////////////////////
	/// @fn listGroupByMonthCount
	/// @brief 함수 간략한 설명 : 월별 접속통계 현황
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return
	/// @throws ParseException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	public Map<String, Object> listGroupByMonthCount(SearchDTO searchDTO) throws ParseException;
	
	/////////////////////////////////////////////
	/// @fn listGroupByWeekCount
	/// @brief 함수 간략한 설명 : 요일별 접속 통계 현황
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return
	/// @throws ParseException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Map<String, Object> listGroupByWeekCount(SearchDTO searchDTO) throws ParseException;
}
