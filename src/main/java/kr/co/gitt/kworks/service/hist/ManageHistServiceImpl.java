package kr.co.gitt.kworks.service.hist;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.CmtPrsvHtMapper;
import kr.co.gitt.kworks.mappers.RdtPrsvHtMapper;
import kr.co.gitt.kworks.mappers.SwtPrsvHtMapper;
import kr.co.gitt.kworks.mappers.WttPrsvHtMapper;
import kr.co.gitt.kworks.model.CmtPrsvHt;
import kr.co.gitt.kworks.model.RdtPrsvHt;
import kr.co.gitt.kworks.model.SwtPrsvHt;
import kr.co.gitt.kworks.model.WttPrsvHt;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/////////////////////////////////////////////
/// @class ManageHistServiceImpl
/// kr.co.gitt.kworks.service.hist \n
///   ㄴ ManageHistServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2016. 11. 17. 오전 11:09:12 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 관리이력 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("manageHistService")
public class ManageHistServiceImpl implements ManageHistService {

	/// 관리이력 - 도로 맵퍼 
	@Resource
	RdtPrsvHtMapper rdtPrsvHtMapper;
	
	/// 관리이력 - 상수 맵퍼 
	@Resource
	WttPrsvHtMapper wttPrsvHtMapper;
	
	/// 관리이력 - 하수 맵퍼 
	@Resource
	SwtPrsvHtMapper swtPrsvHtMapper;
	
	/// 관리이력 - 공통 맵퍼 
	@Resource
	CmtPrsvHtMapper cmtPrsvHtMapper;
	
	/// 관리이력 - 도로 ID 생성 서비스
	@Resource
	EgovIdGnrService rdtPrsvHtIdGnrService;
	
	/// 관리이력 - 상수 ID 생성 서비스
	@Resource
	EgovIdGnrService wttPrsvHtIdGnrService;
	
	/// 관리이력 - 하수 ID 생성 서비스
	@Resource
	EgovIdGnrService swtPrsvHtIdGnrService;
	
	/// 관리이력 - 공통 ID 생성 서비스
	@Resource
	EgovIdGnrService cmtPrsvHtIdGnrService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#selectOneFtrCde(kr.co.gitt.kworks.model.RdtPrsvHt)
	/////////////////////////////////////////////
	public RdtPrsvHt selectOneFtrCde(RdtPrsvHt rdtPrsvHt){
		return rdtPrsvHtMapper.selectOneFtrCde(rdtPrsvHt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#listMnh(java.lang.String)
	/////////////////////////////////////////////
	public List<RdtPrsvHt> listMnh(String domnId){
		return rdtPrsvHtMapper.listMnh(domnId);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#listManageHistRoad(java.lang.Long)
	/////////////////////////////////////////////
	public List<RdtPrsvHt> listManageHistRoad(Long ftrIdn){
		return rdtPrsvHtMapper.listManageHistRoad(ftrIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#selectOneManageHistRoad(java.lang.Long)
	/////////////////////////////////////////////
	public RdtPrsvHt selectOneManageHistRoad(Long shtIdn){
		return rdtPrsvHtMapper.selectOneManageHistRoad(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#modifyManageHistRoad(kr.co.gitt.kworks.model.RdtPrsvHt)
	/////////////////////////////////////////////
	public Integer modifyManageHistRoad(RdtPrsvHt rdtPrsvHt){
		return rdtPrsvHtMapper.updateManageHistRoad(rdtPrsvHt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#addManageHistRoad(kr.co.gitt.kworks.model.RdtPrsvHt)
	/////////////////////////////////////////////
	public Integer addManageHistRoad(RdtPrsvHt rdtPrsvHt) throws FdlException{
		Long shtIdn = rdtPrsvHtIdGnrService.getNextLongId();
		rdtPrsvHt.setShtIdn(shtIdn);
		return rdtPrsvHtMapper.insertManageHistRoad(rdtPrsvHt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#listManageHistWater(java.lang.Long)
	/////////////////////////////////////////////
	public List<WttPrsvHt> listManageHistWater(Long ftrIdn){
		return wttPrsvHtMapper.listManageHistWater(ftrIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#selectOneManageHistWater(java.lang.Long)
	/////////////////////////////////////////////
	public WttPrsvHt selectOneManageHistWater(Long shtIdn){
		return wttPrsvHtMapper.selectOneManageHistWater(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#modifyManageHistWater(kr.co.gitt.kworks.model.RdtPrsvHt)
	/////////////////////////////////////////////
	public Integer modifyManageHistWater(WttPrsvHt wttPrsvHt){
		return wttPrsvHtMapper.updateManageHistWater(wttPrsvHt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#addManageHistWater(kr.co.gitt.kworks.model.RdtPrsvHt)
	/////////////////////////////////////////////
	public Integer addManageHistWater(WttPrsvHt wttPrsvHt) throws FdlException{
		Long shtIdn = wttPrsvHtIdGnrService.getNextLongId();
		wttPrsvHt.setShtIdn(shtIdn);
		return wttPrsvHtMapper.insertManageHistWater(wttPrsvHt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#listManageHistSewage(java.lang.Long)
	/////////////////////////////////////////////
	public List<SwtPrsvHt> listManageHistSewage(Long ftrIdn){
		return swtPrsvHtMapper.listManageHistSewage(ftrIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#selectOneManageHistSewage(java.lang.Long)
	/////////////////////////////////////////////
	public SwtPrsvHt selectOneManageHistSewage(Long shtIdn){
		return swtPrsvHtMapper.selectOneManageHistSewage(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#modifyManageHistSewage(kr.co.gitt.kworks.model.RdtPrsvHt)
	/////////////////////////////////////////////
	public Integer modifyManageHistSewage(SwtPrsvHt swtPrsvHt){
		return swtPrsvHtMapper.updateManageHistSewage(swtPrsvHt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#addManageHistSewage(kr.co.gitt.kworks.model.RdtPrsvHt)
	/////////////////////////////////////////////
	public Integer addManageHistSewage(SwtPrsvHt swtPrsvHt) throws FdlException{
		Long shtIdn = swtPrsvHtIdGnrService.getNextLongId();
		swtPrsvHt.setShtIdn(shtIdn);
		return swtPrsvHtMapper.insertManageHistSewage(swtPrsvHt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#listManageHistCommon(java.lang.Long)
	/////////////////////////////////////////////
	public List<CmtPrsvHt> listManageHistCommon(CmtPrsvHt cmtPrsvHt){
		return cmtPrsvHtMapper.listManageHistCommon(cmtPrsvHt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#listRoadCntrwkRegstrManageHistCommon(java.lang.Long)
	/////////////////////////////////////////////
	public List<CmtPrsvHt> listRoadCntrwkRegstrManageHistCommon(CmtPrsvHt cmtPrsvHt){
		return cmtPrsvHtMapper.listRoadCntrwkRegstrManageHistCommon(cmtPrsvHt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#listWrppCntrwkRegstrManageHistCommon(java.lang.Long)
	/////////////////////////////////////////////
	public List<CmtPrsvHt> listWrppCntrwkRegstrManageHistCommon(CmtPrsvHt cmtPrsvHt){
		return cmtPrsvHtMapper.listWrppCntrwkRegstrManageHistCommon(cmtPrsvHt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#listSwgeCntrwkRegstrManageHistCommon(java.lang.Long)
	/////////////////////////////////////////////
	public List<CmtPrsvHt> listSwgeCntrwkRegstrManageHistCommon(CmtPrsvHt cmtPrsvHt){
		return cmtPrsvHtMapper.listSwgeCntrwkRegstrManageHistCommon(cmtPrsvHt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#selectOneManageHistCommon(java.lang.Long)
	/////////////////////////////////////////////
	public CmtPrsvHt selectOneManageHistCommon(Long shtIdn){
		return cmtPrsvHtMapper.selectOneManageHistCommon(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#modifyManageHistCommon(kr.co.gitt.kworks.model.RdtPrsvHt)
	/////////////////////////////////////////////
	public Integer modifyManageHistCommon(CmtPrsvHt cmtPrsvHt){
		return cmtPrsvHtMapper.updateManageHistCommon(cmtPrsvHt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.hist.ManageHistService#addManageHistCommon(kr.co.gitt.kworks.model.RdtPrsvHt)
	/////////////////////////////////////////////
	public Integer addManageHistCommon(CmtPrsvHt cmtPrsvHt) throws FdlException{
		Long shtIdn = cmtPrsvHtIdGnrService.getNextLongId();
		cmtPrsvHt.setShtIdn(shtIdn);
		return cmtPrsvHtMapper.insertManageHistCommon(cmtPrsvHt);
	}
	
}
