package kr.co.gitt.kworks.projects.yy.service.cntrwkRegstr;

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
import kr.co.gitt.kworks.projects.yy.dto.CntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.yy.mappers.RdtChngDtMapper;
import kr.co.gitt.kworks.projects.yy.mappers.RdtConsMaMapper;
import kr.co.gitt.kworks.projects.yy.mappers.RdtCostDtMapper;
import kr.co.gitt.kworks.projects.yy.mappers.RdtFlawDtMapper;
import kr.co.gitt.kworks.projects.yy.mappers.RdtSubcDtMapper;
import kr.co.gitt.kworks.projects.yy.model.RdtChngDt;
import kr.co.gitt.kworks.projects.yy.model.RdtConsMa;
import kr.co.gitt.kworks.projects.yy.model.RdtCostDt;
import kr.co.gitt.kworks.projects.yy.model.RdtFlawDt;
import kr.co.gitt.kworks.projects.yy.model.RdtSubcDt;
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
/// kr.co.gitt.kworks.projects.yy.service.cntrwkRegstr \n
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
/// - 이 클래스는 도로 공사대장 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("roadCntrwkRegstrService")
@Profile({"yy_dev", "yy_oper"})
public class RoadCntrwkRegstrServiceImpl implements RoadCntrwkRegstrService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());

	// 이미지 서비스
	@Resource
	ImageService imageService;
	
	// 도로 공사대장 맵퍼
	@Resource
	RdtConsMaMapper rdtConsMaMapper;
	
	// 도로 공사대장 하자 보수 내역 맵퍼
	@Resource
	RdtFlawDtMapper rdtFlawDtMapper;
	
	// 도로 공사대장 공사비 지급 내역 맵퍼
	@Resource
	RdtCostDtMapper rdtCostDtMapper;
	
	// 도로 공사대장 설계 변경 내역 맵퍼
	@Resource
	RdtChngDtMapper rdtChngDtMapper;
	
	// 도로 하도급 내역 맵퍼
	@Resource
	RdtSubcDtMapper rdtSubcDtMapper;
	
	// 이미지 맵퍼
	@Resource
	KwsImageMapper kwsImageMapper;
	
	// 도로 공사대장 시퀀스 서비스
	@Resource
	EgovIdGnrService rdtConsMaIdGnrService;
	
	// 도로 공사대장 하자보수내역 시퀀스 서비스
	@Resource
	EgovIdGnrService rdtFlawDtIdGnrService;
	
	// 도로 공사대장 공사비 지급 내역 시퀀스 서비스
	@Resource
	EgovIdGnrService rdtCostDtIdGnrService;
	
	// 도로 공사대장 설계 변경 내역 시퀀스 서비스
	@Resource
	EgovIdGnrService rdtChngDtIdGnrService;
	
	// 도로 하도급 내역 시퀀스 서비스
	@Resource
	EgovIdGnrService rdtSubcDtIdGnrService;
	
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
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#listRoad(kr.co.gitt.kworks.projects.gn.model.RdtConsMa)
	/////////////////////////////////////////////
	public List<RdtConsMa> listRoadCntrwkRegstr(RdtConsMa rdtConsMa){
		return rdtConsMaMapper.list(rdtConsMa);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#selectOne(java.lang.String)
	/////////////////////////////////////////////
	public RdtConsMa selectOneRoadCntrwkRegstr(Long ftrIdn){
		return rdtConsMaMapper.selectOne(ftrIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#modifyRoadCntrwkRegstr(kr.co.gitt.kworks.projects.gn.model.RdtConsMa)
	/////////////////////////////////////////////
	public Integer modifyRoadCntrwkRegstr(RdtConsMa rdtConsMa) {
		Integer rowCount = rdtConsMaMapper.update(rdtConsMa);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#addRoadCntrwkRegstr(kr.co.gitt.kworks.projects.gn.model.RdtConsMa)
	/////////////////////////////////////////////
	public Integer addRoadCntrwkRegstr(RdtConsMa rdtConsMa) throws FdlException {
		String ftrCde = "AD961"; 
		Long ftrIdn = rdtConsMaIdGnrService.getNextLongId();
		
		rdtConsMa.setFtrCde(ftrCde);
		rdtConsMa.setFtrIdn(ftrIdn);
		
		Integer rowCount = rdtConsMaMapper.insert(rdtConsMa);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#listRoadFlawMendngDtls(kr.co.gitt.kworks.projects.gn.model.RdtFlawDt)
	/////////////////////////////////////////////
	public List<RdtFlawDt> listRoadFlawMendngDtls(RdtFlawDt rdtFlawDt){
		return rdtFlawDtMapper.list(rdtFlawDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#selectOneRoadFlawMendngDtls(java.lang.Long, java.lang.Long)
	/////////////////////////////////////////////
	public RdtFlawDt selectOneRoadFlawMendngDtls(CntrwkRegstrDTO cntrwkRegstrDTO){
		return rdtFlawDtMapper.selectOne(cntrwkRegstrDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#modifyRoadFlawMendngDtls(kr.co.gitt.kworks.projects.gn.model.RdtFlawDt)
	/////////////////////////////////////////////
	public Integer modifyRoadFlawMendngDtls(RdtFlawDt rdtFlawDt) {
		Integer rowCount = rdtFlawDtMapper.update(rdtFlawDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#addRoadFlawMendngDtls(kr.co.gitt.kworks.projects.gn.model.RdtFlawDt)
	/////////////////////////////////////////////
	public Integer addRoadFlawMendngDtls(RdtFlawDt rdtFlawDt) throws FdlException {
		String ftrCde = "AD961"; 
		Long shtIdn = rdtFlawDtIdGnrService.getNextLongId();
		
		rdtFlawDt.setFtrCde(ftrCde);
		rdtFlawDt.setShtIdn(shtIdn);
		
		Integer rowCount = rdtFlawDtMapper.insert(rdtFlawDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#removeRoadFlawMendngDtls(java.lang.Long)
	/////////////////////////////////////////////
	public Integer removeRoadFlawMendngDtls(Long shtIdn) {
		return rdtFlawDtMapper.delete(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#listRoadCntrwkCtPymntDtls(kr.co.gitt.kworks.projects.gn.model.RdtCostDt)
	/////////////////////////////////////////////
	public List<RdtCostDt> listRoadCntrwkCtPymntDtls(RdtCostDt rdtCostDt){
		return rdtCostDtMapper.list(rdtCostDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#selectOneRoadCntrwkCtPymntDtls(java.lang.Long, java.lang.Long)
	/////////////////////////////////////////////
	public RdtCostDt selectOneRoadCntrwkCtPymntDtls(CntrwkRegstrDTO cntrwkRegstrDTO){
		return rdtCostDtMapper.selectOne(cntrwkRegstrDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#modifyRoadCntrwkCtPymntDtls(kr.co.gitt.kworks.projects.gn.model.RdtCostDt)
	/////////////////////////////////////////////
	public Integer modifyRoadCntrwkCtPymntDtls(RdtCostDt rdtCostDt) {
		Integer rowCount = rdtCostDtMapper.update(rdtCostDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#addRoadCntrwkCtPymntDtls(kr.co.gitt.kworks.projects.gn.model.RdtCostDt)
	/////////////////////////////////////////////
	public Integer addRoadCntrwkCtPymntDtls(RdtCostDt rdtCostDt) throws FdlException {
		String ftrCde = "AD961"; 
		Long shtIdn = rdtCostDtIdGnrService.getNextLongId();
		
		rdtCostDt.setFtrCde(ftrCde);
		rdtCostDt.setShtIdn(shtIdn);
		
		Integer rowCount = rdtCostDtMapper.insert(rdtCostDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#removeRoadCntrwkCtPymntDtls(java.lang.Long)
	/////////////////////////////////////////////
	public Integer removeRoadCntrwkCtPymntDtls(Long shtIdn) {
		return rdtCostDtMapper.delete(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#listRoadDsgnChangeDtls(kr.co.gitt.kworks.projects.gn.model.RdtChngDt)
	/////////////////////////////////////////////
	public List<RdtChngDt> listRoadDsgnChangeDtls(RdtChngDt rdtChngDt){
		return rdtChngDtMapper.list(rdtChngDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#selectOneRoadDsgnChangeDtls(java.lang.Long, java.lang.Long)
	/////////////////////////////////////////////
	public RdtChngDt selectOneRoadDsgnChangeDtls(CntrwkRegstrDTO cntrwkRegstrDTO){
		return rdtChngDtMapper.selectOne(cntrwkRegstrDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#modifyRoadDsgnChangeDtls(kr.co.gitt.kworks.projects.gn.model.RdtChngDt)
	/////////////////////////////////////////////
	public Integer modifyRoadDsgnChangeDtls(RdtChngDt rdtChngDt) {
		Integer rowCount = rdtChngDtMapper.update(rdtChngDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#addRoadDsgnChangeDtls(kr.co.gitt.kworks.projects.gn.model.RdtChngDt)
	/////////////////////////////////////////////
	public Integer addRoadDsgnChangeDtls(RdtChngDt rdtChngDt) throws FdlException {
		String ftrCde = "AD961"; 
		Long shtIdn = rdtChngDtIdGnrService.getNextLongId();
		
		rdtChngDt.setFtrCde(ftrCde);
		rdtChngDt.setShtIdn(shtIdn);
		
		Integer rowCount = rdtChngDtMapper.insert(rdtChngDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#removeRoadDsgnChangeDtls(java.lang.Long)
	/////////////////////////////////////////////
	public Integer removeRoadDsgnChangeDtls(Long shtIdn) {
		return rdtChngDtMapper.delete(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#listRoadSubcntrDtls(kr.co.gitt.kworks.projects.gn.model.RdtSubcDt)
	/////////////////////////////////////////////
	public List<RdtSubcDt> listRoadSubcntrDtls(RdtSubcDt rdtSubcDt){
		return rdtSubcDtMapper.list(rdtSubcDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#selectOneRoadSubcntrDtls(java.lang.Long, java.lang.Long)
	/////////////////////////////////////////////
	public RdtSubcDt selectOneRoadSubcntrDtls(CntrwkRegstrDTO cntrwkRegstrDTO){
		return rdtSubcDtMapper.selectOne(cntrwkRegstrDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#modifyRoadSubcntrDtls(kr.co.gitt.kworks.projects.gn.model.RdtSubcDt)
	/////////////////////////////////////////////
	public Integer modifyRoadSubcntrDtls(RdtSubcDt rdtSubcDt) {
		Integer rowCount = rdtSubcDtMapper.update(rdtSubcDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#addRoadSubcntrDtls(kr.co.gitt.kworks.projects.gn.model.RdtSubcDt)
	/////////////////////////////////////////////
	public Integer addRoadSubcntrDtls(RdtSubcDt rdtSubcDt) throws FdlException {
		String ftrCde = "AD961"; 
		Long shtIdn = rdtSubcDtIdGnrService.getNextLongId();
		
		rdtSubcDt.setFtrCde(ftrCde);
		rdtSubcDt.setShtIdn(shtIdn);
		
		Integer rowCount = rdtSubcDtMapper.insert(rdtSubcDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.CntrwkRegstrService#removeRoadSubcntrDtls(java.lang.Long)
	/////////////////////////////////////////////
	public Integer removeRoadSubcntrDtls(Long shtIdn) {
		return rdtSubcDtMapper.delete(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.RoadCntrwkRegstrService#addRoadCntrwkRegstrPhoto(kr.co.gitt.kworks.model.KwsImage, org.springframework.web.multipart.MultipartRequest)
	/////////////////////////////////////////////
	public Integer addRoadCntrwkRegstrPhoto(KwsImage kwsImage, MultipartRequest multipartRequest) throws Exception {
		
		Integer cnt = 0;
		
		MultipartFile multipartFile = null;
		
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			ImageDTO imageDTO = new ImageDTO();

			imageDTO.setImageSj(kwsImage.getImageSj());
			imageDTO.setImageCn(kwsImage.getImageCn());
			
			String ftrCde = "AD961";
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
				if(StringUtils.equals("roadCntrwkPhoto", key)) {
					imageDTO.setImageTy(ImageTy.ROAD_CNTRWK_PHOTO);
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
	/// @see kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.RoadCntrwkRegstrService#modifyRoadCntrwkRegstrPhoto(kr.co.gitt.kworks.model.KwsImage, org.springframework.web.multipart.MultipartRequest)
	/////////////////////////////////////////////
	@Override
	public Integer modifyRoadCntrwkRegstrPhoto(KwsImage kwsImage, MultipartRequest multipartRequest) throws Exception {
		
		Integer cnt = 0;
		
		ImageDTO imageDTO = new ImageDTO();
		imageDTO.setImageNo(kwsImage.getImageNo());
		
		MultipartFile multipartFile = null;
		
		if(multipartRequest.getFileMap().entrySet() != null) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				imageService.removeImage(imageDTO);
				
				imageDTO.setImageSj(kwsImage.getImageSj());
				imageDTO.setImageCn(kwsImage.getImageCn());
				
				String ftrCde = "AD961";
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
					if(StringUtils.equals("roadCntrwkPhoto", key)) {
						imageDTO.setImageTy(ImageTy.ROAD_CNTRWK_PHOTO);
					}
				}
				imageService.addImage(imageDTO, multipartFile, 270, 210);
			}
		}
		else {
			String ftrCde = "AD961";
			kwsImage.setFtrCde(ftrCde);
			
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			kwsImage.setUpdusrId(userDTO.getUserId());
			
			imageDTO.setImageTy(ImageTy.ROAD_CNTRWK_PHOTO);
			
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
		cmtPrsvHt.setCntCde("AD961");
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
