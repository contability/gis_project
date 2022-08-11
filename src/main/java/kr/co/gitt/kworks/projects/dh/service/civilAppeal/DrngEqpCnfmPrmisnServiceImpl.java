package kr.co.gitt.kworks.projects.dh.service.civilAppeal;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.dh.mappers.SwtSpmtMaMapper;
import kr.co.gitt.kworks.projects.dh.model.SwtSpmtMa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/////////////////////////////////////////////
/// @class DrngEqpCnfmPrmisnServiceImpl
/// kr.co.gitt.kworks.projects.dh.service.civilAppeal \n
///   ㄴ DrngEqpCnfmPrmisnServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 23. 오후 4:54:15 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 배수설비인허가 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("drngEqpCnfmPrmisnService")
@Profile({"dh_dev", "dh_oper"})
public class DrngEqpCnfmPrmisnServiceImpl implements DrngEqpCnfmPrmisnService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 배수설비인허가 시퀀스 서비스
	@Resource
	EgovIdGnrService swtSpmtMaIdGnrService;
	
	// 배수설비인허가 맵퍼
	@Resource
	SwtSpmtMaMapper swtSpmtMaMapper;
	
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.civilAppeal.DrngEqpCnfmPrmisnService#listDrngEqpCnfmPrmisn(kr.co.gitt.kworks.projects.dh.model.SwtSpmtMa)
	/////////////////////////////////////////////
	public List<SwtSpmtMa> listDrngEqpCnfmPrmisn(SwtSpmtMa swtSpmtMa){
		return swtSpmtMaMapper.list(swtSpmtMa);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.civilAppeal.DrngEqpCnfmPrmisnService#selectOneDrngEqpCnfmPrmisn(java.lang.Long)
	/////////////////////////////////////////////
	public SwtSpmtMa selectOneDrngEqpCnfmPrmisn(Long ftrIdn){
		return swtSpmtMaMapper.selectOne(ftrIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.civilAppeal.DrngEqpCnfmPrmisnService#modifyDrngEqpCnfmPrmisn(kr.co.gitt.kworks.projects.dh.model.SwtSpmtMa)
	/////////////////////////////////////////////
	public Integer modifyDrngEqpCnfmPrmisn(SwtSpmtMa swtSpmtMa) {
		Integer rowCount = swtSpmtMaMapper.update(swtSpmtMa);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.civilAppeal.DrngEqpCnfmPrmisnService#addDrngEqpCnfmPrmisn(kr.co.gitt.kworks.projects.dh.model.SwtSpmtMa)
	/////////////////////////////////////////////
	public Integer addDrngEqpCnfmPrmisn(SwtSpmtMa swtSpmtMa) throws FdlException {
		Integer rowCount = swtSpmtMaMapper.insert(swtSpmtMa);
		return rowCount;
	}
}
