package kr.co.gitt.kworks.projects.gc.service.riverSidePoint;

import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.model.KwsImage.ImageTy;
import kr.co.gitt.kworks.projects.gc.mappers.RivrStatPsMapper;
import kr.co.gitt.kworks.projects.gc.model.RivrStatPs;
import kr.co.gitt.kworks.service.cmmn.ImageService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class RiverSidePointServiceImpl
/// kr.co.gitt.kworks.projects.gc.service.riverSidePoint \n
///   ㄴ RiverSidePointServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 5. 24. 오전 11:54:29 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 하천측점 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("riverSidePointService")
@Profile({"gc_dev", "gc_oper"})
public class RiverSidePointServiceImpl implements RiverSidePointService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 이미지 서비스
	@Resource
	ImageService imageService;
	
	/// 하천측점 맵퍼
	@Resource
	RivrStatPsMapper rivrStatPsMapper;
	
	// 이미지 시퀀스 서비스
	@Resource
	EgovIdGnrService kwsImageIdGnrService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gc.service.riverSidePoint.RiverSidePointService#listRiverSidePoint(kr.co.gitt.kworks.projects.gc.model.RivrStatPs)
	/////////////////////////////////////////////
	public List<RivrStatPs> listRiverSidePoint(RivrStatPs rivrStatPs){
		return rivrStatPsMapper.list(rivrStatPs);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gc.service.riverSidePoint.RiverSidePointService#selectOneRiverSidePoint(kr.co.gitt.kworks.projects.gc.model.RivrStatPs)
	/////////////////////////////////////////////
	public RivrStatPs selectOneRiverSidePoint(RivrStatPs rivrStatPs){
		RivrStatPs data = rivrStatPsMapper.selectOne(rivrStatPs);
		
		KwsImage kwsImage = new KwsImage();
		kwsImage.setFtrCde(data.getFtrCde());
		kwsImage.setFtrIdn(data.getStaIdn());
		
		List<KwsImage> images = imageService.listAllImage(kwsImage);
		for(KwsImage image : images) {
			switch(image.getImageTy()) {
				case WIDTH_SECTIONAL_VIEW:
					data.setRiverSidePointImage(image);
					break;
				default:
					logger.warn("정의되지 않은 이미지 타입입니다.");
					break;
			}
		}
		return data;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gc.service.riverSidePoint.RiverSidePointService#modifyRiverSidePoint(kr.co.gitt.kworks.projects.gc.model.RivrStatPs, org.springframework.web.multipart.MultipartRequest)
	/////////////////////////////////////////////
	public Integer modifyRiverSidePoint(RivrStatPs rivrStatPs, MultipartRequest multipartRequest) throws Exception {

		Integer cnt = 0;
		
		MultipartFile multipartFile = null;
		ImageDTO imageDTO = new ImageDTO();
		
		if(multipartRequest.getFileMap().entrySet() != null) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				
				String ftrCde = "BB992";
				imageDTO.setFtrCde(ftrCde);
				imageDTO.setFtrIdn(rivrStatPs.getStaIdn());

				UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
				imageDTO.setWrterId(userDTO.getUserId());
				imageDTO.setUpdusrId(userDTO.getUserId());
				
				Date toDay = new Date();
				imageDTO.setFrstRgsde(toDay);
				imageDTO.setLastUpdde(toDay);
				
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					if(StringUtils.equals("riverSidePointImageFile", key)) {
						imageDTO.setImageTy(ImageTy.WIDTH_SECTIONAL_VIEW);
						imageDTO.setImageSj("하천측점 횡단면도");
						imageDTO.setImageCn("하천측점 횡단면도 사진");
						imageService.removeImage(imageDTO);
					}
				}
				
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					if(StringUtils.equals("riverSidePointImageFile", key)) {
						imageDTO.setImageTy(ImageTy.WIDTH_SECTIONAL_VIEW);
						imageDTO.setImageSj("하천측점 횡단면도");
						imageDTO.setImageCn("하천측점 횡단면도 사진");
					}
				}
				imageService.addImage(imageDTO, multipartFile, 225, 200);
			}
			rivrStatPsMapper.update(rivrStatPs);
		}
		else {
			rivrStatPsMapper.update(rivrStatPs);
		}
		return cnt;
	}
}
