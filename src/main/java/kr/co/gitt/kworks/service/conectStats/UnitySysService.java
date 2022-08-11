package kr.co.gitt.kworks.service.conectStats;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsConectLog;

/////////////////////////////////////////////
/// @class UnitySysService
/// kr.co.gitt.kworks.service.conectStats \n
///   ㄴ UnitySysService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 10. 20. 오전 10:07:21 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 통합시스템 접속통계 서비스입니다.
///   -# 
/////////////////////////////////////////////
public interface UnitySysService {
	
	/////////////////////////////////////////////
	/// @fn list
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
	/// @fn listTodayGroupByHourCount
	/// @brief 함수 간략한 설명 : 금일 접속현황
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public HashMap<String, Integer> listTodayGroupByHourCount();
	
	/////////////////////////////////////////////
	/// @fn nowConectCount
	/// @brief 함수 간략한 설명 : 현재 접속자수
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
	/// @fn listGroupByDayCount
	/// @brief 함수 간략한 설명 : 일별 접속 통계
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
	public ArrayList<HashMap<String,String>> listGroupByDayCount(SearchDTO searchDTO) throws ParseException;
	
	/////////////////////////////////////////////
	/// @fn listGroupByMonthCount
	/// @brief 함수 간략한 설명 : 월별 접속 통계
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
	public ArrayList<HashMap<String,String>> listGroupByMonthCount(SearchDTO searchDTO) throws ParseException;
	
	/////////////////////////////////////////////
	/// @fn listGroupByWeekCount
	/// @brief 함수 간략한 설명 : 요일별 접속 통계
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
	public ArrayList<HashMap<String,String>> listGroupByWeekCount(SearchDTO searchDTO) throws ParseException;
}
