package kr.co.gitt.kworks.projects.dh.service.ugrwtr;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.model.KwsImage.ImageTy;
import kr.co.gitt.kworks.projects.dh.mappers.BmlWellCheckMapper;
import kr.co.gitt.kworks.projects.dh.mappers.BmlWellPsMapper;
import kr.co.gitt.kworks.projects.dh.model.BmlWellCheck;
import kr.co.gitt.kworks.projects.dh.model.BmlWellPs;
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
/// @class UndergroundWaterServiceImpl
/// kr.co.gitt.kworks.projects.dh.service.ugrwtr \n
///   ㄴ UndergroundWaterServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 2. 28. 오후 3:09:09 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 지하수관정 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("undergroundWaterService")
@Profile({"dh_dev", "dh_oper"})
public class UndergroundWaterServiceImpl implements UndergroundWaterService{
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 이미지 서비스
	@Resource
	ImageService imageService;
	
	// 이미지 시퀀스 서비스
	@Resource
	EgovIdGnrService kwsImageIdGnrService;
	
	// 지하수관정 맵퍼
	@Resource
	BmlWellPsMapper bmlWellPsMapper;
	
	// 지하수관정 점검이력 맵퍼
	@Resource
	BmlWellCheckMapper bmlWellCheckMapper;
	
	// 지하수관정 점검점비이력 시퀀스 서비스
	@Resource
	EgovIdGnrService bmlWellCheckIdGnrService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ugrwtr.UndergroundWaterService#listCount(kr.co.gitt.kworks.model.BmlWellPs)
	/////////////////////////////////////////////
	public Integer listCount(BmlWellPs bmlWellPs){
		return bmlWellPsMapper.listCount(bmlWellPs);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ugrwtr.UndergroundWaterService#list(kr.co.gitt.kworks.model.BmlWellPs)
	/////////////////////////////////////////////
	public List<BmlWellPs> list(BmlWellPs bmlWellPs){
		return bmlWellPsMapper.list(bmlWellPs);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ugrwtr.UndergroundWaterService#selectOne(java.lang.String)
	/////////////////////////////////////////////
	public BmlWellPs selectOne(BmlWellPs bmlWellPs){
		BmlWellPs data = bmlWellPsMapper.selectOne(bmlWellPs);
		
		KwsImage kwsImage = new KwsImage();
		kwsImage.setFtrCde(data.getFtrCde());
		kwsImage.setFtrIdn(data.getFtrIdn());
		
		List<KwsImage> images = imageService.listAllImage(kwsImage);
		for(KwsImage image : images) {
			switch(image.getImageTy()) {
				case DISTANT_VIEW:
					data.setBmlDistantView(image);
					break;
				case CLOSE_RANGE_VIEW:
					data.setBmlCloseRangeView(image);
					break;
				case GPS_OBSERVATION_MAP:
					data.setBmlGpsObservationMap(image);
					break;
				case ABANDONED_WELL:
					data.setBmlAbandonedWell(image);
					break;
				case TEMP_VIEW:
					data.setBmlTempView(image);
					break;
				default:
					logger.warn("정의되지 않은 이미지 타입입니다.");
					break;
			}
		}
		return data;
	}
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 입력
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(BmlWellPs bmlWellPs){
		return bmlWellPsMapper.insert(bmlWellPs);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.ugrwtr.UndergroundWaterService#modify(kr.co.gitt.kworks.projects.dh.model.BmlWellPs)
	/////////////////////////////////////////////
	public Integer modify(BmlWellPs bmlWellPs, MultipartRequest multipartRequest) throws Exception {
		
		Integer cnt = 0;
		
		String ftrCde = "BW001";
		bmlWellPs.setFtrCde(ftrCde);
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		/*SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd", Locale.KOREA);
		Date today = new Date();
		String udtYmd = formatter.format(today);
		bmlWellPs.setUdtYmd(udtYmd);*/
		
		MultipartFile multipartFile = null;
		ImageDTO imageDTO = new ImageDTO();
		
		imageDTO.setFtrCde(ftrCde);
		imageDTO.setFtrIdn(bmlWellPs.getFtrIdn());
		
		imageDTO.setWrterId(userDTO.getUserId());
		imageDTO.setUpdusrId(userDTO.getUserId());
		
		Date toDay = new Date();
		imageDTO.setFrstRgsde(toDay);
		imageDTO.setLastUpdde(toDay);

		if(multipartRequest.getFileMap().entrySet() != null) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					if(StringUtils.equals("distantView", key)) {
						imageDTO.setImageTy(ImageTy.DISTANT_VIEW);
						imageDTO.setImageSj("원경");
						imageDTO.setImageCn("원경 사진");
						imageService.removeImage(imageDTO);
					}
					else if(StringUtils.equals("closeRangeView", key)) {
						imageDTO.setImageTy(ImageTy.CLOSE_RANGE_VIEW);
						imageDTO.setImageSj("근경");
						imageDTO.setImageCn("근경 사진");
						imageService.removeImage(imageDTO);
					}
					else if(StringUtils.equals("gpsObservationMap", key)) {
						imageDTO.setImageTy(ImageTy.GPS_OBSERVATION_MAP);
						imageDTO.setImageSj("GPS 관측도");
						imageDTO.setImageCn("GPS 관측도 사진");
						imageService.removeImage(imageDTO);
					}
					else if(StringUtils.equals("abandonedWell", key)) {
						imageDTO.setImageTy(ImageTy.ABANDONED_WELL);
						imageDTO.setImageSj("폐공");
						imageDTO.setImageCn("폐공 사진");
						imageService.removeImage(imageDTO);
					}
					else if(StringUtils.equals("tempView", key)) {
						imageDTO.setImageTy(ImageTy.TEMP_VIEW);
						imageDTO.setImageSj("기타");
						imageDTO.setImageCn("기타 사진");
						imageService.removeImage(imageDTO);
					}
				}
				
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					if(StringUtils.equals("distantView", key)) {
						imageDTO.setImageTy(ImageTy.DISTANT_VIEW);
						imageDTO.setImageSj("원경");
						imageDTO.setImageCn("원경 사진");
					}
					else if(StringUtils.equals("closeRangeView", key)) {
						imageDTO.setImageTy(ImageTy.CLOSE_RANGE_VIEW);
						imageDTO.setImageSj("근경");
						imageDTO.setImageCn("근경 사진");
					}
					else if(StringUtils.equals("gpsObservationMap", key)) {
						imageDTO.setImageTy(ImageTy.GPS_OBSERVATION_MAP);
						imageDTO.setImageSj("GPS 관측도");
						imageDTO.setImageCn("GPS 관측도 사진");
					}
					else if(StringUtils.equals("abandonedWell", key)) {
						imageDTO.setImageTy(ImageTy.ABANDONED_WELL);
						imageDTO.setImageSj("폐공");
						imageDTO.setImageCn("폐공 사진");
					}
					else if(StringUtils.equals("tempView", key)) {
						imageDTO.setImageTy(ImageTy.TEMP_VIEW);
						imageDTO.setImageSj("기타");
						imageDTO.setImageCn("기타 사진");
					}
				}
				imageService.addImage(imageDTO, multipartFile, 270, 210);
			}
			bmlWellPsMapper.update(bmlWellPs);
		}
		else {
			bmlWellPsMapper.update(bmlWellPs);
		}
		return cnt;
		
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.ugrwtr.UndergroundWaterService#listChckImprmnHist(kr.co.gitt.kworks.projects.dh.model.BmlWellCheck)
	/////////////////////////////////////////////
	public List<BmlWellCheck> listChckImprmnHist(BmlWellCheck bmlWellCheck){
		return bmlWellCheckMapper.list(bmlWellCheck);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.ugrwtr.UndergroundWaterService#selectOneChckImprmnHist(java.lang.String)
	/////////////////////////////////////////////
	public BmlWellCheck selectOneChckImprmnHist(String chkIdn){
		return bmlWellCheckMapper.selectOne(chkIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.ugrwtr.UndergroundWaterService#modifyChckImprmnHist(kr.co.gitt.kworks.projects.dh.model.BmlWellCheck)
	/////////////////////////////////////////////
	public Integer modifyChckImprmnHist(BmlWellCheck bmlWellCheck) {
		Integer rowCount = bmlWellCheckMapper.update(bmlWellCheck);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.ugrwtr.UndergroundWaterService#removeChckImprmnHist(java.lang.String)
	/////////////////////////////////////////////
	public Integer removeChckImprmnHist(String chkIdn) {
		return bmlWellCheckMapper.delete(chkIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.ugrwtr.UndergroundWaterService#addChckImprmnHist(kr.co.gitt.kworks.projects.dh.model.BmlWellCheck)
	/////////////////////////////////////////////
	public Integer addChckImprmnHist(BmlWellCheck bmlWellCheck) throws FdlException {
		Long chkIdn = bmlWellCheckIdGnrService.getNextLongId();
		bmlWellCheck.setChkIdn(chkIdn.toString());
		
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd", Locale.KOREA);
		Date today = new Date();
		String regYmd = formatter.format(today);
		bmlWellCheck.setRegYmd(regYmd);
		
		Integer rowCount = bmlWellCheckMapper.insert(bmlWellCheck);
		return rowCount;
	}
	
}
