package kr.co.gitt.kworks.projects.ss.service.ugrwtr;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import kr.co.gitt.kworks.projects.ss.mappers.BmlWellPsMapper;
import kr.co.gitt.kworks.projects.ss.model.BmlWellPs;

/////////////////////////////////////////////
/// @class UndergroundWaterServiceImpl
/// kr.co.gitt.kworks.service.ugrwtr \n
///   ㄴ UndergroundWaterServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 9. 22. 오후 5:19:14 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 지하수관정 서비스 구현 클래스입니다.
///   -# 
/////////////////////////////////////////////
@Service("undergroundWaterService")
@Profile({"ss_dev", "ss_oper"})
public class UndergroundWaterServiceImpl implements UndergroundWaterService{
	
	//지하수관정 맵퍼
	@Resource
	BmlWellPsMapper bmlWellPsMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ugrwtr.UndergroundWaterService#listCount(kr.co.gitt.kworks.model.BmlWellPs)
	/////////////////////////////////////////////
	public Integer listCount(BmlWellPs bmlWellPs){
		return bmlWellPsMapper.listCount(bmlWellPs);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ugrwtr.UndergroundWaterService#list(kr.co.gitt.kworks.model.BmlWellPs)
	/////////////////////////////////////////////
	public List<BmlWellPs> list(BmlWellPs bmlWellPs){
		return bmlWellPsMapper.list(bmlWellPs);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ugrwtr.UndergroundWaterService#selectOne(java.lang.String)
	/////////////////////////////////////////////
	public BmlWellPs selectOne(String objectid){
		return bmlWellPsMapper.selectOne(objectid);
	}
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 입력
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(BmlWellPs bmlWellPs){
		return bmlWellPsMapper.insert(bmlWellPs);
	}
}
