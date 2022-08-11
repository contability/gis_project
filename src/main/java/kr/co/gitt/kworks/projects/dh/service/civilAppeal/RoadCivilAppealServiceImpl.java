package kr.co.gitt.kworks.projects.dh.service.civilAppeal;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.dh.model.RdtRserMa;
import kr.co.gitt.kworks.projects.dh.mappers.RdtRserMaMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/////////////////////////////////////////////
/// @class RoadCivilAppealServiceImpl
/// kr.co.gitt.kworks.projects.dh.service.civilAppeal \n
///   ㄴ RoadCivilAppealServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 22. 오전 10:38:08 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 도로시스템 민원 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("roadCivilAppealService")
@Profile({"dh_dev", "dh_oper"})
public class RoadCivilAppealServiceImpl implements RoadCivilAppealService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도로민원 시퀀스 서비스
	@Resource
	EgovIdGnrService rdtRserMaIdGnrService;
	
	// 도로민원 맵퍼
	@Resource
	RdtRserMaMapper rdtRserMaMapper;
	
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.civilAppeal.RoadCivilAppealService#listRoadCivilAppeal(kr.co.gitt.kworks.projects.dh.model.RdtRserMa)
	/////////////////////////////////////////////
	public List<RdtRserMa> listRoadCivilAppeal(RdtRserMa rdtRserMa){
		return rdtRserMaMapper.list(rdtRserMa);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.civilAppeal.RoadCivilAppealService#selectOneRoadCivilAppeal(java.lang.Long)
	/////////////////////////////////////////////
	public RdtRserMa selectOneRoadCivilAppeal(Long ftrIdn){
		return rdtRserMaMapper.selectOne(ftrIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.civilAppeal.RoadCivilAppealService#modifyRoadCivilAppeal(kr.co.gitt.kworks.projects.dh.model.RdtRserMa)
	/////////////////////////////////////////////
	public Integer modifyRoadCivilAppeal(RdtRserMa rdtRserMa) {
		Integer rowCount = rdtRserMaMapper.update(rdtRserMa);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.civilAppeal.RoadCivilAppealService#addRoadCivilAppeal(kr.co.gitt.kworks.projects.dh.model.RdtRserMa)
	/////////////////////////////////////////////
	public Integer addRoadCivilAppeal(RdtRserMa rdtRserMa) throws FdlException {
		Integer rowCount = rdtRserMaMapper.insert(rdtRserMa);
		return rowCount;
	}
}
