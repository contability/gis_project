package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsSysConectLog;

/////////////////////////////////////////////
/// @class KwsSysConectLogMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsSysConectLogMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | nam |
///    | Date | 2016. 9. 5. 오후 6:21:17 |
///    | Class Version | v1.0 |
///    | 작업자 | nam, Others... |
/// @section 상세설명
/// - 이 클래스는 로그 통계 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsSysConectLogMapper {
	
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
	public List<KwsSysConectLog> listSearch(SearchDTO searchDTO);
	
	public List<KwsSysConectLog> listSys(SearchDTO searchDTO);
	
	public List<KwsSysConectLog> listCountGroupBySys(SearchDTO searchDTO);
	
	public Integer listCountUser(SearchDTO searchDTO);
	
	public List<KwsSysConectLog> listUser(SearchDTO searchDTO);
	
	public List<KwsSysConectLog> listCountGroupByUser(SearchDTO searchDTO);
	
	public Integer listCountDept(SearchDTO searchDTO);
	
	public List<KwsSysConectLog> listDept(SearchDTO searchDTO);
	
	public List<KwsSysConectLog> listCountGroupByDept(SearchDTO searchDTO);
	
	public List<KwsSysConectLog> listMonth(SearchDTO searchDTO);
	
	public Integer listCountMonth(SearchDTO searchDTO);
	
	public List<KwsSysConectLog> listCountGroupByMonth(SearchDTO searchDTO);
	
	public List<KwsSysConectLog> listDay(SearchDTO searchDTO);
	
	public Integer listCountDay(SearchDTO searchDTO);
	
	public List<KwsSysConectLog> listCountGroupByDay(SearchDTO searchDTO);
	
	public List<KwsSysConectLog> listCountGroupByWeekday(SearchDTO searchDTO);
	
	public Integer listCountWeekday(SearchDTO searchDTO);
	
	public List<KwsSysConectLog> listWeekday(SearchDTO searchDTO);
	
	public List<KwsSysConectLog> listCountGroupByTime(SearchDTO searchDTO);

	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsSysConectLog
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsSysConectLog kwsSysConectLog);
	
	/////////////////
	public List <KwsSysConectLog> listTodayGroupByHourCount();
	
	public List<KwsSysConectLog> list(KwsSysConectLog KwsSysConectLog);
	
	public List <KwsSysConectLog> listGroupByDay(SearchDTO searchDTO);
	
	public List <KwsSysConectLog> listGroupByMonth(SearchDTO searchDTO);
	
	public List <KwsSysConectLog> listGroupByWeek(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listSysConectLogExcel
	/// @brief 함수 간략한 설명 : 엑셀다운로드용 시스템 접속 로그 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsSysConectLog> listSysConectLogExcel(SearchDTO searchDTO);
}