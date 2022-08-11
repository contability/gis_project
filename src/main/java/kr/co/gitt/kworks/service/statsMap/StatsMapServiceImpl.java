package kr.co.gitt.kworks.service.statsMap;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsStatsClMapper;
import kr.co.gitt.kworks.mappers.KwsStatsMastrMapper;
import kr.co.gitt.kworks.mappers.KwsStatsValuMapper;
import kr.co.gitt.kworks.model.KwsStatsCl;
import kr.co.gitt.kworks.model.KwsStatsMastr;
import kr.co.gitt.kworks.model.KwsStatsValu;

import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class StatsMapServiceImpl
/// kr.co.gitt.kworks.service.statsMap \n
///   ㄴ StatsMapServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 12. 28. 오후 4:27:42 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 통계지도 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("statsMapService")
public class StatsMapServiceImpl implements StatsMapService{

	//통계지도 대분류
	@Resource
	KwsStatsClMapper kwsStatsClMapper;
	
	//통계지도 목록
	@Resource
	KwsStatsMastrMapper kwsStatsMastrMapper;
	
	//통계지도 값
	@Resource
	KwsStatsValuMapper kwsStatsValuMapper;
	
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.statsMap.StatsMapService#listKwsStatsCl(kr.co.gitt.kworks.model.KwsStatsCl)
	/////////////////////////////////////////////
	public List<KwsStatsCl> listKwsStatsCl(KwsStatsCl kwsStatsCl){
		return kwsStatsClMapper.list(kwsStatsCl); 
	}
	
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.statsMap.StatsMapService#selectOneKwsStatsCl(kr.co.gitt.kworks.model.KwsStatsCl)
	/////////////////////////////////////////////
	public KwsStatsCl selectOneKwsStatsCl(KwsStatsCl kwsStatsCl){
		return kwsStatsClMapper.selectOne(kwsStatsCl);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.statsMap.StatsMapService#listKwsStatsMastr(kr.co.gitt.kworks.model.KwsStatsMastr)
	/////////////////////////////////////////////
	public List<KwsStatsMastr> listKwsStatsMastr(KwsStatsMastr kwsStatsMastr){
		return kwsStatsMastrMapper.list(kwsStatsMastr);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.statsMap.StatsMapService#groupByIemYear()
	/////////////////////////////////////////////
	public List<KwsStatsMastr> groupByIemYear(){
		return kwsStatsMastrMapper.groupByIemYear();
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.statsMap.StatsMapService#selectOneKwsStatsMastr(kr.co.gitt.kworks.model.KwsStatsMastr)
	/////////////////////////////////////////////
	public KwsStatsMastr selectOneKwsStatsMastr(KwsStatsMastr kwsStatsMastr){
		return kwsStatsMastrMapper.selectOne(kwsStatsMastr);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.statsMap.StatsMapService#listKwsStatsValu(kr.co.gitt.kworks.model.KwsStatsValu)
	/////////////////////////////////////////////
	public List<KwsStatsValu> listKwsStatsValu(KwsStatsValu kwsStatsValu){
		return kwsStatsValuMapper.list(kwsStatsValu);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.statsMap.StatsMapService#minValuKwsStatsValu(kr.co.gitt.kworks.model.KwsStatsValu)
	/////////////////////////////////////////////
	public KwsStatsValu minValuKwsStatsValu(KwsStatsValu kwsStatsValu){
		return kwsStatsValuMapper.minValu(kwsStatsValu);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.statsMap.StatsMapService#maxValuKwsStatsValu(kr.co.gitt.kworks.model.KwsStatsValu)
	/////////////////////////////////////////////
	public KwsStatsValu maxValuKwsStatsValu(KwsStatsValu kwsStatsValu){
		return kwsStatsValuMapper.maxValu(kwsStatsValu);
	}
}
