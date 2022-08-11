package kr.co.gitt.kworks.projects.sc.service.civilAppeal;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.sc.mappers.WttWserMaMapper;
import kr.co.gitt.kworks.projects.sc.model.WttWserMa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/////////////////////////////////////////////
/// @class WrppCivilAppealServiceImpl
/// kr.co.gitt.kworks.projects.sc.service.civilAppeal \n
///   ㄴ WrppCivilAppealServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 22. 오전 10:38:08 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 상수시스템 민원 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("wrppCivilAppealService")
@Profile({"sc_dev", "sc_oper"})
public class WrppCivilAppealServiceImpl implements WrppCivilAppealService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 상수민원 시퀀스 서비스
	@Resource
	EgovIdGnrService wttWserMaIdGnrService;
	
	// 상수민원 맵퍼
	@Resource
	WttWserMaMapper wttWserMaMapper;
	
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.sc.service.civilAppeal.WrppCivilAppealService#listWrppCivilAppeal(kr.co.gitt.kworks.projects.sc.model.WttWserMa)
	/////////////////////////////////////////////
	public List<WttWserMa> listWrppCivilAppeal(WttWserMa wttWserMa){
		return wttWserMaMapper.list(wttWserMa);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.sc.service.civilAppeal.WrppCivilAppealService#selectOneWrppCivilAppeal(java.lang.Long)
	/////////////////////////////////////////////
	public WttWserMa selectOneWrppCivilAppeal(Long ftrIdn){
		return wttWserMaMapper.selectOne(ftrIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.sc.service.civilAppeal.WrppCivilAppealService#modifyWrppCivilAppeal(kr.co.gitt.kworks.projects.sc.model.WttWserMa)
	/////////////////////////////////////////////
	public Integer modifyWrppCivilAppeal(WttWserMa wttWserMa) {
		Integer rowCount = wttWserMaMapper.update(wttWserMa);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.sc.service.civilAppeal.WrppCivilAppealService#addWrppCivilAppeal(kr.co.gitt.kworks.projects.sc.model.WttWserMa)
	/////////////////////////////////////////////
	public Integer addWrppCivilAppeal(WttWserMa wttWserMa) throws FdlException {
		Integer rowCount = wttWserMaMapper.insert(wttWserMa);
		return rowCount;
	}
}
