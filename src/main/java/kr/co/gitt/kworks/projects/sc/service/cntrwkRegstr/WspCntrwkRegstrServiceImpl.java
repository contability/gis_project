package kr.co.gitt.kworks.projects.sc.service.cntrwkRegstr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.dto.cntrwkRegstr.FacilityResultDTO;
import kr.co.gitt.kworks.mappers.CmtPrsvHtMapper;
import kr.co.gitt.kworks.model.CmtPrsvHt;
import kr.co.gitt.kworks.projects.sc.mappers.WttSplyMaMapper;
import kr.co.gitt.kworks.projects.sc.model.WttSplyMa;
import kr.co.gitt.kworks.repository.CorporationRepository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/////////////////////////////////////////////
/// @class WspCntrwkRegstrServiceImpl
/// kr.co.gitt.kworks.projects.sc.service.cntrwkRegstr \n
///   ㄴ WspCntrwkRegstrServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 14. 오전 10:35:19 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 급수 공사대장 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("wspCntrwkRegstrService")
@Profile({"sc_dev", "sc_oper"})
public class WspCntrwkRegstrServiceImpl implements WspCntrwkRegstrService {
	
	// 급수 공사대장 맵퍼
	@Resource
	WttSplyMaMapper wttSplyMaMapper;
	
	// 급수 공사대장 시퀀스 서비스
	@Resource
	EgovIdGnrService wttSplyMaIdGnrService;
	
	/// 관리 이력 서비스
	@Resource
	CmtPrsvHtMapper cmtPrsvHtMapper;
	
	// 공사 서비스
	@Resource
	CorporationRepository corporationRepository;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.sc.service.cntrwkRegstr.WspCntrwkRegstrService#listWspCntrwkRegstr(kr.co.gitt.kworks.projects.sc.model.WttSplyMa)
	/////////////////////////////////////////////
	public List<WttSplyMa> listWspCntrwkRegstr(WttSplyMa wttSplyMa){
		return wttSplyMaMapper.list(wttSplyMa);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.sc.service.cntrwkRegstr.WspCntrwkRegstrService#selectOneWspCntrwkRegstr(java.lang.Long)
	/////////////////////////////////////////////
	public WttSplyMa selectOneWspCntrwkRegstr(Long ftrIdn){
		return wttSplyMaMapper.selectOne(ftrIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.sc.service.cntrwkRegstr.WspCntrwkRegstrService#modifyWspCntrwkRegstr(kr.co.gitt.kworks.projects.sc.model.WttSplyMa)
	/////////////////////////////////////////////
	public Integer modifyWspCntrwkRegstr(WttSplyMa wttSplyMa) {
		Integer rowCount = wttSplyMaMapper.update(wttSplyMa);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.sc.service.cntrwkRegstr.WspCntrwkRegstrService#addWspCntrwkRegstr(kr.co.gitt.kworks.projects.sc.model.WttSplyMa)
	/////////////////////////////////////////////
	public Integer addWspCntrwkRegstr(WttSplyMa wttSplyMa) throws FdlException {
		String ftrCde = "SA952"; 
		Long ftrIdn = wttSplyMaIdGnrService.getNextLongId();
		
		wttSplyMa.setFtrCde(ftrCde);
		wttSplyMa.setFtrIdn(ftrIdn);
		
		Integer rowCount = wttSplyMaMapper.insert(wttSplyMa);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.sc.service.cntrwkRegstr.WspCntrwkRegstrService#searchSpatial(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public List<String> searchSpatial(Long ftrIdn) {
		CmtPrsvHt cmtPrsvHt = new CmtPrsvHt();
		cmtPrsvHt.setCntCde("SA952");
		cmtPrsvHt.setFtrIdn(ftrIdn);
		
		List<FacilityResultDTO> list = cmtPrsvHtMapper.listFacilities(cmtPrsvHt);
		
		Map<String, List<Long>> facilities = new HashMap<String, List<Long>>(); 
		for(FacilityResultDTO facilityResultDTO : list) {
			String dataId = facilityResultDTO.getDataId();
			Long id = facilityResultDTO.getFtrIdn();
			if(!facilities.containsKey(dataId)) {
				facilities.put(dataId, new ArrayList<Long>());
			}
			facilities.get(dataId).add(id);
		}
		
		List<String> wkts = new ArrayList<String>();
		for(Entry<String, List<Long>> entrySet : facilities.entrySet()) {
			wkts.addAll(corporationRepository.searchWKT(entrySet.getKey(), entrySet.getValue()));	
		}
		return wkts;
	}
}
