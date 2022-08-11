package kr.co.gitt.kworks.projects.yg.service.cntrwkRegstr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.dto.cntrwkRegstr.FacilityResultDTO;
import kr.co.gitt.kworks.mappers.CmtPrsvHtMapper;
import kr.co.gitt.kworks.mappers.KwsImageMapper;
import kr.co.gitt.kworks.model.CmtPrsvHt;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.model.KwsImage.ImageTy;
import kr.co.gitt.kworks.projects.yg.dto.CntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.yg.mappers.SwtChngDtMapper;
import kr.co.gitt.kworks.projects.yg.mappers.SwtConsMaMapper;
import kr.co.gitt.kworks.projects.yg.mappers.SwtCostDtMapper;
import kr.co.gitt.kworks.projects.yg.mappers.SwtFlawDtMapper;
import kr.co.gitt.kworks.projects.yg.mappers.SwtSubcDtMapper;
import kr.co.gitt.kworks.projects.yg.model.SwtChngDt;
import kr.co.gitt.kworks.projects.yg.model.SwtConsMa;
import kr.co.gitt.kworks.projects.yg.model.SwtCostDt;
import kr.co.gitt.kworks.projects.yg.model.SwtFlawDt;
import kr.co.gitt.kworks.projects.yg.model.SwtSubcDt;
import kr.co.gitt.kworks.repository.CorporationRepository;
import kr.co.gitt.kworks.service.cmmn.ImageService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class CntrwkRegstrServiceImpl
/// kr.co.gitt.kworks.projects.yg.service.cntrwkRegstr \n
///   ㄴ CntrwkRegstrServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 3. 오전 10:48:48 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 하수 공사대장 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("swgeCntrwkRegstrService")
@Profile({"yg_dev", "yg_oper"})
public class SwgeCntrwkRegstrServiceImpl implements SwgeCntrwkRegstrService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 이미지 서비스
	@Resource
	ImageService imageService;
	
	// 하수 공사대장 맵퍼
	@Resource
	SwtConsMaMapper swtConsMaMapper;
	
	// 하수 공사대장 하자 보수 내역 맵퍼
	@Resource
	SwtFlawDtMapper swtFlawDtMapper;
	
	// 하수 공사대장 공사비 지급 내역 맵퍼
	@Resource
	SwtCostDtMapper swtCostDtMapper;
	
	// 하수 공사대장 설계 변경 내역 맵퍼
	@Resource
	SwtChngDtMapper swtChngDtMapper;
	
	// 하수 하도급 내역 맵퍼
	@Resource
	SwtSubcDtMapper swtSubcDtMapper;
	
	// 이미지 맵퍼
	@Resource
	KwsImageMapper kwsImageMapper;
	
	// 하수 공사대장 시퀀스 서비스
	@Resource
	EgovIdGnrService swtConsMaIdGnrService;
	
	// 하수 공사대장 하자보수내역 시퀀스 서비스
	@Resource
	EgovIdGnrService swtFlawDtIdGnrService;
	
	// 하수 공사대장 공사비 지급 내역 시퀀스 서비스
	@Resource
	EgovIdGnrService swtCostDtIdGnrService;
	
	// 하수 공사대장 설계 변경 내역 시퀀스 서비스
	@Resource
	EgovIdGnrService swtChngDtIdGnrService;
	
	// 하수 하도급 내역 시퀀스 서비스
	@Resource
	EgovIdGnrService swtSubcDtIdGnrService;
	
	// 이미지 시퀀스 서비스
	@Resource
	EgovIdGnrService kwsImageIdGnrService;
	
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
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#listSwge(kr.co.gitt.kworks.projects.gn.model.SwtConsMa)
	/////////////////////////////////////////////
	public List<SwtConsMa> listSwgeCntrwkRegstr(SwtConsMa swtConsMa){
		return swtConsMaMapper.list(swtConsMa);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#selectOne(java.lang.String)
	/////////////////////////////////////////////
	public SwtConsMa selectOneSwgeCntrwkRegstr(Long ftrIdn){
		return swtConsMaMapper.selectOne(ftrIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#modifySwgeCntrwkRegstr(kr.co.gitt.kworks.projects.gn.model.SwtConsMa)
	/////////////////////////////////////////////
	public Integer modifySwgeCntrwkRegstr(SwtConsMa swtConsMa) {
		Integer rowCount = swtConsMaMapper.update(swtConsMa);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#addSwgeCntrwkRegstr(kr.co.gitt.kworks.projects.gn.model.SwtConsMa)
	/////////////////////////////////////////////
	public Integer addSwgeCntrwkRegstr(SwtConsMa swtConsMa) throws FdlException {
		String ftrCde = "SB951"; 
		Long ftrIdn = swtConsMaIdGnrService.getNextLongId();
		
		swtConsMa.setFtrCde(ftrCde);
		swtConsMa.setFtrIdn(ftrIdn);
		
		Integer rowCount = swtConsMaMapper.insert(swtConsMa);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#listSwgeFlawMendngDtls(kr.co.gitt.kworks.projects.gn.model.SwtFlawDt)
	/////////////////////////////////////////////
	public List<SwtFlawDt> listSwgeFlawMendngDtls(SwtFlawDt swtFlawDt){
		return swtFlawDtMapper.list(swtFlawDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#selectOneSwgeFlawMendngDtls(java.lang.Long, java.lang.Long)
	/////////////////////////////////////////////
	public SwtFlawDt selectOneSwgeFlawMendngDtls(CntrwkRegstrDTO cntrwkRegstrDTO){
		return swtFlawDtMapper.selectOne(cntrwkRegstrDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#modifySwgeFlawMendngDtls(kr.co.gitt.kworks.projects.gn.model.SwtFlawDt)
	/////////////////////////////////////////////
	public Integer modifySwgeFlawMendngDtls(SwtFlawDt swtFlawDt) {
		Integer rowCount = swtFlawDtMapper.update(swtFlawDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#addSwgeFlawMendngDtls(kr.co.gitt.kworks.projects.gn.model.SwtFlawDt)
	/////////////////////////////////////////////
	public Integer addSwgeFlawMendngDtls(SwtFlawDt swtFlawDt) throws FdlException {
		String ftrCde = "SB951"; 
		Long shtIdn = swtFlawDtIdGnrService.getNextLongId();
		
		swtFlawDt.setFtrCde(ftrCde);
		swtFlawDt.setShtIdn(shtIdn);
		
		Integer rowCount = swtFlawDtMapper.insert(swtFlawDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#removeSwgeFlawMendngDtls(java.lang.Long)
	/////////////////////////////////////////////
	public Integer removeSwgeFlawMendngDtls(Long shtIdn) {
		return swtFlawDtMapper.delete(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#listSwgeCntrwkCtPymntDtls(kr.co.gitt.kworks.projects.gn.model.SwtCostDt)
	/////////////////////////////////////////////
	public List<SwtCostDt> listSwgeCntrwkCtPymntDtls(SwtCostDt swtCostDt){
		return swtCostDtMapper.list(swtCostDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#selectOneSwgeCntrwkCtPymntDtls(java.lang.Long, java.lang.Long)
	/////////////////////////////////////////////
	public SwtCostDt selectOneSwgeCntrwkCtPymntDtls(CntrwkRegstrDTO cntrwkRegstrDTO){
		return swtCostDtMapper.selectOne(cntrwkRegstrDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#modifySwgeCntrwkCtPymntDtls(kr.co.gitt.kworks.projects.gn.model.SwtCostDt)
	/////////////////////////////////////////////
	public Integer modifySwgeCntrwkCtPymntDtls(SwtCostDt swtCostDt) {
		Integer rowCount = swtCostDtMapper.update(swtCostDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#addSwgeCntrwkCtPymntDtls(kr.co.gitt.kworks.projects.gn.model.SwtCostDt)
	/////////////////////////////////////////////
	public Integer addSwgeCntrwkCtPymntDtls(SwtCostDt swtCostDt) throws FdlException {
		String ftrCde = "SB951"; 
		Long shtIdn = swtCostDtIdGnrService.getNextLongId();
		
		swtCostDt.setFtrCde(ftrCde);
		swtCostDt.setShtIdn(shtIdn);
		
		Integer rowCount = swtCostDtMapper.insert(swtCostDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#removeSwgeCntrwkCtPymntDtls(java.lang.Long)
	/////////////////////////////////////////////
	public Integer removeSwgeCntrwkCtPymntDtls(Long shtIdn) {
		return swtCostDtMapper.delete(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#listSwgeDsgnChangeDtls(kr.co.gitt.kworks.projects.gn.model.SwtChngDt)
	/////////////////////////////////////////////
	public List<SwtChngDt> listSwgeDsgnChangeDtls(SwtChngDt swtChngDt){
		return swtChngDtMapper.list(swtChngDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#selectOneSwgeDsgnChangeDtls(java.lang.Long, java.lang.Long)
	/////////////////////////////////////////////
	public SwtChngDt selectOneSwgeDsgnChangeDtls(CntrwkRegstrDTO cntrwkRegstrDTO){
		return swtChngDtMapper.selectOne(cntrwkRegstrDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#modifySwgeDsgnChangeDtls(kr.co.gitt.kworks.projects.gn.model.SwtChngDt)
	/////////////////////////////////////////////
	public Integer modifySwgeDsgnChangeDtls(SwtChngDt swtChngDt) {
		Integer rowCount = swtChngDtMapper.update(swtChngDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#addSwgeDsgnChangeDtls(kr.co.gitt.kworks.projects.gn.model.SwtChngDt)
	/////////////////////////////////////////////
	public Integer addSwgeDsgnChangeDtls(SwtChngDt swtChngDt) throws FdlException {
		String ftrCde = "SB951"; 
		Long shtIdn = swtChngDtIdGnrService.getNextLongId();
		
		swtChngDt.setFtrCde(ftrCde);
		swtChngDt.setShtIdn(shtIdn);
		
		Integer rowCount = swtChngDtMapper.insert(swtChngDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#removeSwgeDsgnChangeDtls(java.lang.Long)
	/////////////////////////////////////////////
	public Integer removeSwgeDsgnChangeDtls(Long shtIdn) {
		return swtChngDtMapper.delete(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#listSwgeSubcntrDtls(kr.co.gitt.kworks.projects.gn.model.SwtSubcDt)
	/////////////////////////////////////////////
	public List<SwtSubcDt> listSwgeSubcntrDtls(SwtSubcDt swtSubcDt){
		return swtSubcDtMapper.list(swtSubcDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#selectOneSwgeSubcntrDtls(java.lang.Long, java.lang.Long)
	/////////////////////////////////////////////
	public SwtSubcDt selectOneSwgeSubcntrDtls(CntrwkRegstrDTO cntrwkRegstrDTO){
		return swtSubcDtMapper.selectOne(cntrwkRegstrDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#modifySwgeSubcntrDtls(kr.co.gitt.kworks.projects.gn.model.SwtSubcDt)
	/////////////////////////////////////////////
	public Integer modifySwgeSubcntrDtls(SwtSubcDt swtSubcDt) {
		Integer rowCount = swtSubcDtMapper.update(swtSubcDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#addSwgeSubcntrDtls(kr.co.gitt.kworks.projects.gn.model.SwtSubcDt)
	/////////////////////////////////////////////
	public Integer addSwgeSubcntrDtls(SwtSubcDt swtSubcDt) throws FdlException {
		String ftrCde = "SB951"; 
		Long shtIdn = swtSubcDtIdGnrService.getNextLongId();
		
		swtSubcDt.setFtrCde(ftrCde);
		swtSubcDt.setShtIdn(shtIdn);
		
		Integer rowCount = swtSubcDtMapper.insert(swtSubcDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#removeSwgeSubcntrDtls(java.lang.Long)
	/////////////////////////////////////////////
	public Integer removeSwgeSubcntrDtls(Long shtIdn) {
		return swtSubcDtMapper.delete(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.SwgeCntrwkRegstrService#addSwgeCntrwkRegstrPhoto(kr.co.gitt.kworks.model.KwsImage, org.springframework.web.multipart.MultipartRequest)
	/////////////////////////////////////////////
	public Integer addSwgeCntrwkRegstrPhoto(KwsImage kwsImage, MultipartRequest multipartRequest) throws Exception {
		
		Integer cnt = 0;
		
		MultipartFile multipartFile = null;
		
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			ImageDTO imageDTO = new ImageDTO();

			imageDTO.setImageSj(kwsImage.getImageSj());
			imageDTO.setImageCn(kwsImage.getImageCn());
			
			String ftrCde = "SB951";
			imageDTO.setFtrCde(ftrCde);

			imageDTO.setFtrIdn(kwsImage.getFtrIdn());

			imageDTO.setLcX(kwsImage.getLcX());
			imageDTO.setLcY(kwsImage.getLcY());
			
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			imageDTO.setWrterId(userDTO.getUserId());
			imageDTO.setUpdusrId(userDTO.getUserId());
			
			Date toDay = new Date();
			imageDTO.setFrstRgsde(toDay);
			imageDTO.setLastUpdde(toDay);
			
			multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				String key = entry.getKey();
				if(StringUtils.equals("swgeCntrwkPhoto", key)) {
					imageDTO.setImageTy(ImageTy.SWGE_CNTRWK_PHOTO);
				}
			}
			imageService.addImage(imageDTO, multipartFile, 270, 210);
		}
		return cnt;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.SwgeCntrwkRegstrService#modifySwgeCntrwkRegstrPhoto(kr.co.gitt.kworks.model.KwsImage, org.springframework.web.multipart.MultipartRequest)
	/////////////////////////////////////////////
	@Override
	public Integer modifySwgeCntrwkRegstrPhoto(KwsImage kwsImage, MultipartRequest multipartRequest) throws Exception {
		
		Integer cnt = 0;
		
		ImageDTO imageDTO = new ImageDTO();
		imageDTO.setImageNo(kwsImage.getImageNo());
		
		MultipartFile multipartFile = null;
		
		if(multipartRequest.getFileMap().entrySet() != null) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				imageService.removeImage(imageDTO);
				
				imageDTO.setImageSj(kwsImage.getImageSj());
				imageDTO.setImageCn(kwsImage.getImageCn());
				
				String ftrCde = "SB951";
				imageDTO.setFtrCde(ftrCde);
	
				imageDTO.setFtrIdn(kwsImage.getFtrIdn());
	
				imageDTO.setLcX(kwsImage.getLcX());
				imageDTO.setLcY(kwsImage.getLcY());
				
				UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
				imageDTO.setWrterId(userDTO.getUserId());
				imageDTO.setUpdusrId(userDTO.getUserId());
				
				Date toDay = new Date();
				imageDTO.setFrstRgsde(toDay);
				imageDTO.setLastUpdde(toDay);
				
				multipartFile = entry.getValue();
				
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					if(StringUtils.equals("swgeCntrwkPhoto", key)) {
						imageDTO.setImageTy(ImageTy.SWGE_CNTRWK_PHOTO);
					}
				}
				imageService.addImage(imageDTO, multipartFile, 270, 210);
			}
		}
		else {
			String ftrCde = "SB951";
			kwsImage.setFtrCde(ftrCde);
			
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			kwsImage.setUpdusrId(userDTO.getUserId());
			
			imageDTO.setImageTy(ImageTy.SWGE_CNTRWK_PHOTO);
			
			kwsImageMapper.update(kwsImage);
		}
		return cnt;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.RoadCntrwkRegstrService#searchSpatial(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public List<String> searchSpatial(Long ftrIdn) {
		CmtPrsvHt cmtPrsvHt = new CmtPrsvHt();
		cmtPrsvHt.setCntCde("SB951");
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
