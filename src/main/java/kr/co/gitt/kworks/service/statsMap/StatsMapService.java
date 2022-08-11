package kr.co.gitt.kworks.service.statsMap;

import java.util.List;

import kr.co.gitt.kworks.model.KwsStatsCl;
import kr.co.gitt.kworks.model.KwsStatsMastr;
import kr.co.gitt.kworks.model.KwsStatsValu;

/////////////////////////////////////////////
/// @class StatsMapService
/// kr.co.gitt.kworks.service.statsMap \n
///   ㄴ StatsMapService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 12. 28. 오후 4:33:28 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 통계지도 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface StatsMapService {
	
	/////////////////////////////////////////////
	/// @fn listKwsStatsCl
	/// @brief 함수 간략한 설명 : 통계지도 대분류 목록검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsCl
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsStatsCl> listKwsStatsCl(KwsStatsCl kwsStatsCl);
	
	/////////////////////////////////////////////
	/// @fn selectOneKwsStatsCl
	/// @brief 함수 간략한 설명 : 통계지도 대분류 단일 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsCl
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsStatsCl selectOneKwsStatsCl(KwsStatsCl kwsStatsCl);
	
	/////////////////////////////////////////////
	/// @fn listKwsStatsMastr
	/// @brief 함수 간략한 설명 : 통계지도 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsMastr
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsStatsMastr> listKwsStatsMastr(KwsStatsMastr kwsStatsMastr);
	
	/////////////////////////////////////////////
	/// @fn groupByIemYear
	/// @brief 함수 간략한 설명 : 통계지도 년도별 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsStatsMastr> groupByIemYear();
	
	/////////////////////////////////////////////
	/// @fn selectOneKwsStatsMastr
	/// @brief 함수 간략한 설명 : 통걔지도 목록 단일 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsMastr
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsStatsMastr selectOneKwsStatsMastr(KwsStatsMastr kwsStatsMastr);
	
	/////////////////////////////////////////////
	/// @fn listKwsStatsValu
	/// @brief 함수 간략한 설명 : 통계지도 데이터값 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsValu
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsStatsValu> listKwsStatsValu(KwsStatsValu kwsStatsValu);
	
	/////////////////////////////////////////////
	/// @fn minValuKwsStatsValu
	/// @brief 함수 간략한 설명 : 통계지도 데이터 최소값 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsValu
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsStatsValu minValuKwsStatsValu(KwsStatsValu kwsStatsValu);
	
	/////////////////////////////////////////////
	/// @fn maxValuKwsStatsValu
	/// @brief 함수 간략한 설명 : 통계지도 데이터 최대값 겁색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsValu
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsStatsValu maxValuKwsStatsValu(KwsStatsValu kwsStatsValu);
}
