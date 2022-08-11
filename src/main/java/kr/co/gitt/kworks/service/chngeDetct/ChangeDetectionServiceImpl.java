package kr.co.gitt.kworks.service.chngeDetct;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.mappers.ChgAreaDtMapper;
import kr.co.gitt.kworks.mappers.ChgDtlsDtMapper;
import kr.co.gitt.kworks.model.ChgAreaDt;
import kr.co.gitt.kworks.model.ChgDtlsDt;
import kr.co.gitt.kworks.model.KwsImage.ImageTy;
import kr.co.gitt.kworks.service.cmmn.ImageService;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class ChangeDetectionDetailsServiceImpl
/// kr.co.gitt.kworks.service.chngeDetct \n
///   ㄴ ChangeDetectionDetailsServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | yun4810 |
///    | Date | 2018. 10. 2. 오후 3:01:13 |
///    | Class Version | v1.0 |
///    | 작업자 | yun4810, Others... |
/// @section 상세설명
/// - 이 클래스는 변화탐지 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("changeDetectionService")
public class ChangeDetectionServiceImpl implements ChangeDetectionService {
	
	/// 변화탐지내역 맵퍼
	@Resource
	ChgDtlsDtMapper chgDtlsDtMapper;
	
	/// 변화탐지내역 맵퍼
	@Resource
	ChgAreaDtMapper chgAreaDtMapper;
	
	/// 변화탐지내역 시퀀스 서비스
	@Resource
	EgovIdGnrService chgDtlsDtIdGnrService;
	
	/// 변화탐지지역 시퀀스 서비스
	@Resource
	EgovIdGnrService chgAreaDtIdGnrService;
	
	// 이미지 서비스
	@Resource
	ImageService imageService;
	
	/// 메세지 소스
	@Resource
	private MessageSource messageSource;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.chngeDetct.ChangeDetectionDetailsService#listChangeDetectionDetails(kr.co.gitt.kworks.model.ChgDtlsDt)
	/////////////////////////////////////////////
	@Override
	public List<ChgDtlsDt> listChangeDetectionDetails(ChgDtlsDt chgDtlsDt) {
		return chgDtlsDtMapper.list(chgDtlsDt);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.chngeDetct.ChangeDetectionDetailsService#listChangeDetectionDetails(kr.co.gitt.kworks.model.ChgDtlsDt, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<ChgDtlsDt> listChangeDetectionDetails(ChgDtlsDt chgDtlsDt, PaginationInfo paginationInfo) {
		Integer totalRecordCount = chgDtlsDtMapper.listCount(chgDtlsDt);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			return listChangeDetectionDetails(chgDtlsDt);
		}
		else {
			return new ArrayList<ChgDtlsDt>();
		}
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.chngeDetct.ChangeDetectionDetailsService#addChangeDetectionDetails(kr.co.gitt.kworks.model.ChgDtlsDt)
	/////////////////////////////////////////////
	public Integer addChangeDetectionDetails(ChgDtlsDt chgDtlsDt) throws FdlException {
		Long chngeDetctNo = chgDtlsDtIdGnrService.getNextLongId();
		chgDtlsDt.setChngeDetctNo(chngeDetctNo);
		
		Calendar calendar = new GregorianCalendar(Locale.KOREA);
		int nYear = calendar.get(Calendar.YEAR);
		
		String wrterYear = Integer.toString(nYear);
		chgDtlsDt.setWrterYear(wrterYear);
		
		Integer rowCount = chgDtlsDtMapper.insert(chgDtlsDt);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.chngeDetct.ChangeDetectionService#modifyChangeDetectionDetails(kr.co.gitt.kworks.model.ChgDtlsDt)
	/////////////////////////////////////////////
	@Override
	public Integer modifyChangeDetectionDetails(ChgDtlsDt chgDtlsDt) throws FdlException {
		Integer rowCount = chgDtlsDtMapper.update(chgDtlsDt);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.chngeDetct.ChangeDetectionService#removeChangeDetectionDetails(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer removeChangeDetectionDetails(Long chngeDetctNo) {
		return chgDtlsDtMapper.delete(chngeDetctNo);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.chngeDetct.ChangeDetectionDetailsService#selectOneChangeDetectionDetails(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public ChgDtlsDt selectOneChangeDetectionDetails(Long chngeDetctNo) {
		return chgDtlsDtMapper.selectOne(chngeDetctNo);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.chngeDetct.ChangeDetectionService#listChangeDetectionArea(kr.co.gitt.kworks.model.ChgAreaDt)
	/////////////////////////////////////////////
	@Override
	public List<ChgAreaDt> listChangeDetectionArea(ChgAreaDt chgAreaDt) {
		return chgAreaDtMapper.list(chgAreaDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.chngeDetct.ChangeDetectionService#listChangeDetectionArea(kr.co.gitt.kworks.model.ChgAreaDt, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<ChgAreaDt> listChangeDetectionArea(ChgAreaDt chgAreaDt, PaginationInfo paginationInfo) {
		Integer totalRecordCount = chgAreaDtMapper.listCount(chgAreaDt);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			return listChangeDetectionArea(chgAreaDt);
		}
		else {
			return new ArrayList<ChgAreaDt>();
		}
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.chngeDetct.ChangeDetectionService#selectOneChangeDetectionArea(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public ChgAreaDt selectOneChangeDetectionArea(Long chngeAreaNo) {
		return chgAreaDtMapper.selectOne(chngeAreaNo);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.chngeDetct.ChangeDetectionService#removeChangeDetectionArea(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer removeChangeDetectionArea(Long chngeAreaNo) {
		return chgAreaDtMapper.delete(chngeAreaNo);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.chngeDetct.ChangeDetectionService#modifyChangeDetectionArea(kr.co.gitt.kworks.model.ChgAreaDt)
	/////////////////////////////////////////////
	@Override
	public Integer modifyChangeDetectionArea(ChgAreaDt chgAreaDt, String[] data) throws Exception, FdlException, IOException {
		Long chngeAreaNo = chgAreaDt.getChngeAreaNo();
		Long chngeDetctNo = chgAreaDt.getChngeDetctNo();
		chgAreaDt.setChngeAreaNo(chngeAreaNo);
		
		
		ImageDTO imageDTO = new ImageDTO();
		imageDTO.setFtrCde("ZZ998");
		imageDTO.setFtrIdn(chngeAreaNo);
		
		imageService.removeImage(imageDTO);
		
		String base64Str = null;
		for(int i=0, len=data.length; i < len; i++) {
			String[] split = data[i].split(",");
			if(split.length == 2) {
				base64Str = split[1];
			}
			
			BufferedImage bufferedImage = imageService.getBufferdImageByBase64String(base64Str);
			
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			Date toDay = new Date();
			
			String originalFilename = "cda_" + chngeDetctNo + "_" + chngeAreaNo;
			imageDTO.setImageSj("변화탐지지역_"+(i+1));
			imageDTO.setImageCn("변화탐지지역_"+(i+1)+"번 사진");
			imageDTO.setFtrCde("ZZ998");
			imageDTO.setFtrIdn(chngeAreaNo);
			imageDTO.setImageTy(ImageTy.CHANGE_DETECTION_AREA_PHOTO);
			imageDTO.setWrterId(userDTO.getUserId());
			imageDTO.setFrstRgsde(toDay);
			imageDTO.setUpdusrId(userDTO.getUserId());
			imageDTO.setLastUpdde(toDay);
			
			imageService.addImage(imageDTO, originalFilename, bufferedImage);
		}
		Integer rowCount = chgAreaDtMapper.update(chgAreaDt);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.chngeDetct.ChangeDetectionService#addChangeDetectionArea(kr.co.gitt.kworks.model.ChgAreaDt)
	/////////////////////////////////////////////
	@Override
	public Integer addChangeDetectionArea(ChgAreaDt chgAreaDt, String[] data) throws Exception, FdlException, IOException {
		chgAreaDt.setChngeAreaNo(chgAreaDtIdGnrService.getNextLongId());
		Integer rowCount = chgAreaDtMapper.insert(chgAreaDt);
		
		String base64Str = null;
		
		Long chngeDetctNo = chgAreaDt.getChngeDetctNo();
		Long chngeAreaNo = chgAreaDt.getChngeAreaNo();
		
		for(int i=0, len=data.length; i < len; i++) {
			String[] split = data[i].split(",");
			if(split.length == 2) {
				base64Str = split[1];
			}
			
			BufferedImage bufferedImage = imageService.getBufferdImageByBase64String(base64Str);
			
			ImageDTO imageDTO = new ImageDTO();
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			Date toDay = new Date();
			
			String originalFilename = "cda_" + chngeDetctNo + "_" + chngeAreaNo;
			imageDTO.setImageSj("변화탐지지역_"+(i+1));
			imageDTO.setImageCn("변화탐지지역_"+(i+1)+"번 사진");
			imageDTO.setFtrCde("ZZ998");
			imageDTO.setFtrIdn(chngeAreaNo);
			imageDTO.setImageTy(ImageTy.CHANGE_DETECTION_AREA_PHOTO);
			imageDTO.setWrterId(userDTO.getUserId());
			imageDTO.setFrstRgsde(toDay);
			imageDTO.setUpdusrId(userDTO.getUserId());
			imageDTO.setLastUpdde(toDay);
			
			imageService.addImage(imageDTO, originalFilename, bufferedImage);
		}
		return rowCount;
	}
	
}
