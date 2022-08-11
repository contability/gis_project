package kr.co.gitt.kworks.projects.gn.service.civilAppeal;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.gn.mappers.SwtSserMaMapper;
import kr.co.gitt.kworks.projects.gn.model.SwtSserMa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/////////////////////////////////////////////
/// @class SwtCivilAppealServiceImpl
/// kr.co.gitt.kworks.projects.gn.service.civilAppeal \n
///   ㄴ SwtCivilAppealServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 22. 오전 10:38:08 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는하수시스템 민원 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("swgeCivilAppealService")
@Profile({"gn_dev", "gn_oper", "gds_dev", "gds_oper"})
public class SwgeCivilAppealServiceImpl implements SwgeCivilAppealService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 상수민원 시퀀스 서비스
	@Resource
	EgovIdGnrService swtSserMaIdGnrService;
	
	// 상수민원 맵퍼
	@Resource
	SwtSserMaMapper swtSserMaMapper;
	
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.civilAppeal.SwtCivilAppealService#listSwtCivilAppeal(kr.co.gitt.kworks.projects.gn.model.SwtSserMa)
	/////////////////////////////////////////////
	public List<SwtSserMa> listSwgeCivilAppeal(SwtSserMa swtSserMa){
		return swtSserMaMapper.list(swtSserMa);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.civilAppeal.SwtCivilAppealService#selectOneSwtCivilAppeal(java.lang.Long)
	/////////////////////////////////////////////
	public SwtSserMa selectOneSwgeCivilAppeal(Long ftrIdn){
		return swtSserMaMapper.selectOne(ftrIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.civilAppeal.SwtCivilAppealService#modifySwtCivilAppeal(kr.co.gitt.kworks.projects.gn.model.SwtSserMa)
	/////////////////////////////////////////////
	public Integer modifySwgeCivilAppeal(SwtSserMa swtSserMa) {
		Integer rowCount = swtSserMaMapper.update(swtSserMa);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.civilAppeal.SwtCivilAppealService#addSwtCivilAppeal(kr.co.gitt.kworks.projects.gn.model.SwtSserMa)
	/////////////////////////////////////////////
	public Integer addSwgeCivilAppeal(SwtSserMa swtSserMa) throws FdlException {
		Integer rowCount = swtSserMaMapper.insert(swtSserMa);
		return rowCount;
	}
}
