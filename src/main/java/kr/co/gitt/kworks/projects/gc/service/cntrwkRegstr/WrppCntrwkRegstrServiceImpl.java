package kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.EhojoCntrwkRegstrDTO;
import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.contact.ehojo.mappers.CntrwkRegstrMapper;
import kr.co.gitt.kworks.dto.cntrwkRegstr.FacilityResultDTO;
import kr.co.gitt.kworks.mappers.CmtPrsvHtMapper;
import kr.co.gitt.kworks.mappers.KwsImageMapper;
import kr.co.gitt.kworks.model.CmtPrsvHt;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.model.KwsImage.ImageTy;
import kr.co.gitt.kworks.projects.gc.dto.CntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.gc.mappers.WttChngDtMapper;
import kr.co.gitt.kworks.projects.gc.mappers.WttConsMaMapper;
import kr.co.gitt.kworks.projects.gc.mappers.WttCostDtMapper;
import kr.co.gitt.kworks.projects.gc.mappers.WttFlawDtMapper;
import kr.co.gitt.kworks.projects.gc.mappers.WttSubcDtMapper;
import kr.co.gitt.kworks.projects.gc.model.WttChngDt;
import kr.co.gitt.kworks.projects.gc.model.WttConsMa;
import kr.co.gitt.kworks.projects.gc.model.WttCostDt;
import kr.co.gitt.kworks.projects.gc.model.WttFlawDt;
import kr.co.gitt.kworks.projects.gc.model.WttSubcDt;
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
/// @class WrppCntrwkRegstrServiceImpl
/// kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr \n
///   ??? WrppCntrwkRegstrServiceImpl.java
/// @section ?????????????????????
///    |    ???  ???       |      ???  ???       |
///    | :-------------: | -------------   |
///    | Company | (???)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 3. ?????? 10:48:48 |
///    | Class Version | v1.0 |
///    | ????????? | GittGDS_YJG, Others... |
/// @section ????????????
/// - ??? ???????????? ?????? ???????????? ?????? ????????? ?????????.
///   -# 
/////////////////////////////////////////////
@Service("wrppCntrwkRegstrService")
@Profile({"gc_dev", "gc_oper"})
public class WrppCntrwkRegstrServiceImpl implements WrppCntrwkRegstrService {
	
	/// ??????
	Logger logger = LoggerFactory.getLogger(getClass());

	// ????????? ?????????
	@Resource
	ImageService imageService;
	
	// ?????? ???????????? ??????
	@Resource
	WttConsMaMapper wttConsMaMapper;
	
	// ?????? ???????????? ?????? ?????? ?????? ??????
	@Resource
	WttFlawDtMapper wttFlawDtMapper;
	
	// ?????? ???????????? ????????? ?????? ?????? ??????
	@Resource
	WttCostDtMapper wttCostDtMapper;
	
	// ?????? ???????????? ?????? ?????? ?????? ??????
	@Resource
	WttChngDtMapper wttChngDtMapper;
	
	// ?????? ????????? ?????? ??????
	@Resource
	WttSubcDtMapper wttSubcDtMapper;
	
	// ehojo ??????
	@Resource
	CntrwkRegstrMapper cntrwkRegstrMapper;
	
	// ????????? ??????
	@Resource
	KwsImageMapper kwsImageMapper;
	
	// ?????? ???????????? ????????? ?????????
	@Resource
	EgovIdGnrService wttConsMaIdGnrService;
	
	// ?????? ???????????? ?????????????????? ????????? ?????????
	@Resource
	EgovIdGnrService wttFlawDtIdGnrService;
	
	// ?????? ???????????? ????????? ?????? ?????? ????????? ?????????
	@Resource
	EgovIdGnrService wttCostDtIdGnrService;
	
	// ?????? ???????????? ?????? ?????? ?????? ????????? ?????????
	@Resource
	EgovIdGnrService wttChngDtIdGnrService;
	
	// ?????? ????????? ?????? ????????? ?????????
	@Resource
	EgovIdGnrService wttSubcDtIdGnrService;
	
	/// ?????? ?????? ?????????
	@Resource
	CmtPrsvHtMapper cmtPrsvHtMapper;
	
	// ?????? ?????????
	@Resource
	CorporationRepository corporationRepository;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#listWrpp(kr.co.gitt.kworks.projects.gc.model.WttConsMa)
	/////////////////////////////////////////////
	public List<WttConsMa> listWrppCntrwkRegstr(WttConsMa wttConsMa){
		return wttConsMaMapper.list(wttConsMa);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#selectOne(java.lang.String)
	/////////////////////////////////////////////
	public WttConsMa selectOneWrppCntrwkRegstr(Long ftrIdn){
		return wttConsMaMapper.selectOne(ftrIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#modifyWrppCntrwkRegstr(kr.co.gitt.kworks.projects.gc.model.WttConsMa)
	/////////////////////////////////////////////
	public Integer modifyWrppCntrwkRegstr(WttConsMa wttConsMa) {
		Integer rowCount = wttConsMaMapper.update(wttConsMa);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#addWrppCntrwkRegstr(kr.co.gitt.kworks.projects.gc.model.WttConsMa)
	/////////////////////////////////////////////
	public Integer addWrppCntrwkRegstr(WttConsMa wttConsMa) throws FdlException {
		String ftrCde = "SA951"; 
		Long ftrIdn = wttConsMaIdGnrService.getNextLongId();
		
		wttConsMa.setFtrCde(ftrCde);
		wttConsMa.setFtrIdn(ftrIdn);
		
		Integer rowCount = wttConsMaMapper.insert(wttConsMa);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#listWrppFlawMendngDtls(kr.co.gitt.kworks.projects.gc.model.WttFlawDt)
	/////////////////////////////////////////////
	public List<WttFlawDt> listWrppFlawMendngDtls(WttFlawDt wttFlawDt){
		return wttFlawDtMapper.list(wttFlawDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#selectOneWrppFlawMendngDtls(java.lang.Long, java.lang.Long)
	/////////////////////////////////////////////
	public WttFlawDt selectOneWrppFlawMendngDtls(CntrwkRegstrDTO cntrwkRegstrDTO){
		return wttFlawDtMapper.selectOne(cntrwkRegstrDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#modifyWrppFlawMendngDtls(kr.co.gitt.kworks.projects.gc.model.WttFlawDt)
	/////////////////////////////////////////////
	public Integer modifyWrppFlawMendngDtls(WttFlawDt wttFlawDt) {
		Integer rowCount = wttFlawDtMapper.update(wttFlawDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#addWrppFlawMendngDtls(kr.co.gitt.kworks.projects.gc.model.WttFlawDt)
	/////////////////////////////////////////////
	public Integer addWrppFlawMendngDtls(WttFlawDt wttFlawDt) throws FdlException {
		String ftrCde = "SA951"; 
		Long shtIdn = wttFlawDtIdGnrService.getNextLongId();
		
		wttFlawDt.setFtrCde(ftrCde);
		wttFlawDt.setShtIdn(shtIdn);
		
		Integer rowCount = wttFlawDtMapper.insert(wttFlawDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#removeWrppFlawMendngDtls(java.lang.Long)
	/////////////////////////////////////////////
	public Integer removeWrppFlawMendngDtls(Long shtIdn) {
		return wttFlawDtMapper.delete(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#listWrppCntrwkCtPymntDtls(kr.co.gitt.kworks.projects.gc.model.WttCostDt)
	/////////////////////////////////////////////
	public List<WttCostDt> listWrppCntrwkCtPymntDtls(WttCostDt wttCostDt){
		return wttCostDtMapper.list(wttCostDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#selectOneWrppCntrwkCtPymntDtls(java.lang.Long, java.lang.Long)
	/////////////////////////////////////////////
	public WttCostDt selectOneWrppCntrwkCtPymntDtls(CntrwkRegstrDTO cntrwkRegstrDTO){
		return wttCostDtMapper.selectOne(cntrwkRegstrDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#modifyWrppCntrwkCtPymntDtls(kr.co.gitt.kworks.projects.gc.model.WttCostDt)
	/////////////////////////////////////////////
	public Integer modifyWrppCntrwkCtPymntDtls(WttCostDt wttCostDt) {
		Integer rowCount = wttCostDtMapper.update(wttCostDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#addWrppCntrwkCtPymntDtls(kr.co.gitt.kworks.projects.gc.model.WttCostDt)
	/////////////////////////////////////////////
	public Integer addWrppCntrwkCtPymntDtls(WttCostDt wttCostDt) throws FdlException {
		String ftrCde = "SA951"; 
		Long shtIdn = wttCostDtIdGnrService.getNextLongId();
		
		wttCostDt.setFtrCde(ftrCde);
		wttCostDt.setShtIdn(shtIdn);
		
		Integer rowCount = wttCostDtMapper.insert(wttCostDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#removeWrppCntrwkCtPymntDtls(java.lang.Long)
	/////////////////////////////////////////////
	public Integer removeWrppCntrwkCtPymntDtls(Long shtIdn) {
		return wttCostDtMapper.delete(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#listWrppDsgnChangeDtls(kr.co.gitt.kworks.projects.gc.model.WttChngDt)
	/////////////////////////////////////////////
	public List<WttChngDt> listWrppDsgnChangeDtls(WttChngDt wttChngDt){
		return wttChngDtMapper.list(wttChngDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#selectOneWrppDsgnChangeDtls(java.lang.Long, java.lang.Long)
	/////////////////////////////////////////////
	public WttChngDt selectOneWrppDsgnChangeDtls(CntrwkRegstrDTO cntrwkRegstrDTO){
		return wttChngDtMapper.selectOne(cntrwkRegstrDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#modifyWrppDsgnChangeDtls(kr.co.gitt.kworks.projects.gc.model.WttChngDt)
	/////////////////////////////////////////////
	public Integer modifyWrppDsgnChangeDtls(WttChngDt wttChngDt) {
		Integer rowCount = wttChngDtMapper.update(wttChngDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#addWrppDsgnChangeDtls(kr.co.gitt.kworks.projects.gc.model.WttChngDt)
	/////////////////////////////////////////////
	public Integer addWrppDsgnChangeDtls(WttChngDt wttChngDt) throws FdlException {
		String ftrCde = "SA951"; 
		Long shtIdn = wttChngDtIdGnrService.getNextLongId();
		
		wttChngDt.setFtrCde(ftrCde);
		wttChngDt.setShtIdn(shtIdn);
		
		Integer rowCount = wttChngDtMapper.insert(wttChngDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#removeWrppDsgnChangeDtls(java.lang.Long)
	/////////////////////////////////////////////
	public Integer removeWrppDsgnChangeDtls(Long shtIdn) {
		return wttChngDtMapper.delete(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#listWrppSubcntrDtls(kr.co.gitt.kworks.projects.gc.model.WttSubcDt)
	/////////////////////////////////////////////
	public List<WttSubcDt> listWrppSubcntrDtls(WttSubcDt wttSubcDt){
		return wttSubcDtMapper.list(wttSubcDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#selectOneWrppSubcntrDtls(java.lang.Long, java.lang.Long)
	/////////////////////////////////////////////
	public WttSubcDt selectOneWrppSubcntrDtls(CntrwkRegstrDTO cntrwkRegstrDTO){
		return wttSubcDtMapper.selectOne(cntrwkRegstrDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#modifyWrppSubcntrDtls(kr.co.gitt.kworks.projects.gc.model.WttSubcDt)
	/////////////////////////////////////////////
	public Integer modifyWrppSubcntrDtls(WttSubcDt wttSubcDt) {
		Integer rowCount = wttSubcDtMapper.update(wttSubcDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#addWrppSubcntrDtls(kr.co.gitt.kworks.projects.gc.model.WttSubcDt)
	/////////////////////////////////////////////
	public Integer addWrppSubcntrDtls(WttSubcDt wttSubcDt) throws FdlException {
		String ftrCde = "SA951"; 
		Long shtIdn = wttSubcDtIdGnrService.getNextLongId();
		
		wttSubcDt.setFtrCde(ftrCde);
		wttSubcDt.setShtIdn(shtIdn);
		
		Integer rowCount = wttSubcDtMapper.insert(wttSubcDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.CntrwkRegstrService#removeWrppSubcntrDtls(java.lang.Long)
	/////////////////////////////////////////////
	public Integer removeWrppSubcntrDtls(Long shtIdn) {
		return wttSubcDtMapper.delete(shtIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.WrppCntrwkRegstrService#addWrppCntrwkRegstrPhoto(kr.co.gitt.kworks.model.KwsImage, org.springframework.web.multipart.MultipartRequest)
	/////////////////////////////////////////////
	public Integer addWrppCntrwkRegstrPhoto(KwsImage kwsImage, MultipartRequest multipartRequest) throws Exception {
		
		Integer cnt = 0;
		
		MultipartFile multipartFile = null;
		
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			ImageDTO imageDTO = new ImageDTO();

			imageDTO.setImageSj(kwsImage.getImageSj());
			imageDTO.setImageCn(kwsImage.getImageCn());
			
			String ftrCde = "SA951";
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
				if(StringUtils.equals("wrppCntrwkPhoto", key)) {
					imageDTO.setImageTy(ImageTy.WRPP_CNTRWK_PHOTO);
				}
			}
			imageService.addImage(imageDTO, multipartFile, 270, 210);
		}
		return cnt;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.WrppCntrwkRegstrService#modifyWrppCntrwkRegstrPhoto(kr.co.gitt.kworks.model.KwsImage, org.springframework.web.multipart.MultipartRequest)
	/////////////////////////////////////////////
	@Override
	public Integer modifyWrppCntrwkRegstrPhoto(KwsImage kwsImage, MultipartRequest multipartRequest) throws Exception {
		
		Integer cnt = 0;
		
		ImageDTO imageDTO = new ImageDTO();
		imageDTO.setImageNo(kwsImage.getImageNo());
		
		MultipartFile multipartFile = null;
		
		if(multipartRequest.getFileMap().entrySet() != null) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				imageService.removeImage(imageDTO);
				
				imageDTO.setImageSj(kwsImage.getImageSj());
				imageDTO.setImageCn(kwsImage.getImageCn());
				
				String ftrCde = "SA951";
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
					if(StringUtils.equals("wrppCntrwkPhoto", key)) {
						imageDTO.setImageTy(ImageTy.WRPP_CNTRWK_PHOTO);
					}
				}
				imageService.addImage(imageDTO, multipartFile, 270, 210);
			}
		}
		else {
			String ftrCde = "SA951";
			kwsImage.setFtrCde(ftrCde);
			
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			kwsImage.setUpdusrId(userDTO.getUserId());
			
			imageDTO.setImageTy(ImageTy.WRPP_CNTRWK_PHOTO);
			
			kwsImageMapper.update(kwsImage);
		}
		return cnt;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.WrppCntrwkRegstrService#ehojoInfo(java.lang.String)
	/////////////////////////////////////////////
	public EhojoCntrwkRegstrDTO ehojoInfo(String cttNum){
		return cntrwkRegstrMapper.selectOne(cttNum);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.projects.gc.service.cntrwkRegstr.WrppCntrwkRegstrService#searchSpatial(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public List<String> searchSpatial(Long ftrIdn) {
		CmtPrsvHt cmtPrsvHt = new CmtPrsvHt();
		cmtPrsvHt.setCntCde("SA961");
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
