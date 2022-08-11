package kr.co.gitt.kworks.projects.sc.service.mesrSgnal;

import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.model.KwsImage.ImageTy;
import kr.co.gitt.kworks.projects.sc.mappers.EttCpsvDtMapper;
import kr.co.gitt.kworks.projects.sc.model.EttCpsvDt;
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
/// @class MesrSgnalSttusExaminHistServiceImpl
/// kr.co.gitt.kworks.projects.sc.service.mesrSgnal \n
///   ㄴ MesrSgnalSttusExaminHistServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 4. 26. 오후 12:15:18 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 측량표지현황조사 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("mesrSgnalSttusExaminHistService")
@Profile({"sc_dev", "sc_oper"})
public class MesrSgnalSttusExaminHistServiceImpl implements MesrSgnalSttusExaminHistService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 이미지 서비스
	@Resource
	ImageService imageService;
	
	// 기준점 맵퍼
	@Resource
	EttCpsvDtMapper ettCpsvDtMapper;
	
	// 측량표지현황조사 시퀀스 서비스 - FTR_IDN
	@Resource
	EgovIdGnrService ettCpsvDtIdGnrService;
	
	// 이미지 시퀀스 서비스
	@Resource
	EgovIdGnrService kwsImageIdGnrService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.sc.service.mesrSgnal.MesrSgnalSttusExaminHistService#listMesrSgnalSttusExaminHist(kr.co.gitt.kworks.projects.sc.model.EttCpsvDt)
	/////////////////////////////////////////////
	public List<EttCpsvDt> listMesrSgnalSttusExaminHist(EttCpsvDt ettCpsvDt){
		return ettCpsvDtMapper.list(ettCpsvDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.sc.service.mesrSgnal.MesrSgnalSttusExaminHistService#selectOneMesrSgnalSttusExaminHist(kr.co.gitt.kworks.projects.sc.model.EttCpsvDt)
	/////////////////////////////////////////////
	public EttCpsvDt selectOneMesrSgnalSttusExaminHist(EttCpsvDt ettCpsvDt){
		EttCpsvDt data = ettCpsvDtMapper.selectOne(ettCpsvDt);
		
		KwsImage kwsImage = new KwsImage();
		kwsImage.setFtrCde(data.getFtrCde());
		kwsImage.setFtrIdn(data.getFtrIdn());
		
		List<KwsImage> images = imageService.listAllImage(kwsImage);
		for(KwsImage image : images) {
			switch(image.getImageTy()) {
				case CLOSE_RANGE_VIEW:
					data.setMesrSgnalSttusExaminHistCloseRangeView(image);
					break;
				case DISTANT_VIEW:
					data.setMesrSgnalSttusExaminHistDistantView(image);
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
	/// @see kr.co.gitt.kworks.projects.sc.service.mesrSgnal.MesrSgnalSttusExaminHistService#addMesrSgnalSttusExaminHist(kr.co.gitt.kworks.projects.sc.model.EttCpsvDt, org.springframework.web.multipart.MultipartRequest)
	/////////////////////////////////////////////
	public Integer addMesrSgnalSttusExaminHist(EttCpsvDt ettCpsvDt, MultipartRequest multipartRequest) throws Exception {
		String ftrCde = "ZA101";
		ettCpsvDt.setFtrCde(ftrCde);
		
		Long ftrIdn = ettCpsvDtIdGnrService.getNextLongId();
		ettCpsvDt.setFtrIdn(ftrIdn);
		
		Integer rowCount = ettCpsvDtMapper.insert(ettCpsvDt);
		
		MultipartFile multipartFile = null;
		ImageDTO imageDTO = new ImageDTO();
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {

			imageDTO.setFtrCde(ftrCde);
			imageDTO.setFtrIdn(ettCpsvDt.getFtrIdn());

			double lcX = 0;
			double lcY = 0;
			
			imageDTO.setLcX(lcX);
			imageDTO.setLcY(lcY);
			
			imageDTO.setWrterId(userDTO.getUserId());
			imageDTO.setUpdusrId(userDTO.getUserId());
			
			Date toDay = new Date();
			imageDTO.setFrstRgsde(toDay);
			imageDTO.setLastUpdde(toDay);
			
			multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				String key = entry.getKey();
				if(StringUtils.equals("closeRangeView", key)) {
					imageDTO.setImageTy(ImageTy.CLOSE_RANGE_VIEW);
					imageDTO.setImageSj("근경");
					imageDTO.setImageCn("근경 사진");
				}
				else if(StringUtils.equals("distantView", key)) {
					imageDTO.setImageTy(ImageTy.DISTANT_VIEW);
					imageDTO.setImageSj("원경");
					imageDTO.setImageCn("원경 사진");
				}
			}
			imageService.addImage(imageDTO, multipartFile, 270, 210);
		}
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.sc.service.mesrSgnal.MesrSgnalSttusExaminHistService#modifyMesrSgnalSttusExaminHist(kr.co.gitt.kworks.projects.sc.model.EttCpsvDt, org.springframework.web.multipart.MultipartRequest)
	/////////////////////////////////////////////
	public Integer modifyMesrSgnalSttusExaminHist(EttCpsvDt ettCpsvDt, MultipartRequest multipartRequest) throws Exception {

		Integer cnt = 0;
		
		String ftrCde = "ZA101";
		ettCpsvDt.setFtrCde(ftrCde);
		
		MultipartFile multipartFile = null;
		ImageDTO imageDTO = new ImageDTO();
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if(multipartRequest.getFileMap().entrySet() != null) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				
				imageDTO.setFtrCde(ftrCde);
				imageDTO.setFtrIdn(ettCpsvDt.getFtrIdn());

				double lcX = 0;
				double lcY = 0;
				
				imageDTO.setLcX(lcX);
				imageDTO.setLcY(lcY);
				
				imageDTO.setWrterId(userDTO.getUserId());
				imageDTO.setUpdusrId(userDTO.getUserId());
				
				Date toDay = new Date();
				imageDTO.setFrstRgsde(toDay);
				imageDTO.setLastUpdde(toDay);
				
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					if(StringUtils.equals("closeRangeView", key)) {
						imageDTO.setImageTy(ImageTy.CLOSE_RANGE_VIEW);
						imageDTO.setImageSj("근경");
						imageDTO.setImageCn("근경 사진");
						imageService.removeImage(imageDTO);
					}
					else if(StringUtils.equals("distantView", key)) {
						imageDTO.setImageTy(ImageTy.DISTANT_VIEW);
						imageDTO.setImageSj("원경");
						imageDTO.setImageCn("원경 사진");
						imageService.removeImage(imageDTO);
					}
				}
				
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					if(StringUtils.equals("closeRangeView", key)) {
						imageDTO.setImageTy(ImageTy.CLOSE_RANGE_VIEW);
						imageDTO.setImageSj("근경");
						imageDTO.setImageCn("근경 사진");
					}
					else if(StringUtils.equals("distantView", key)) {
						imageDTO.setImageTy(ImageTy.DISTANT_VIEW);
						imageDTO.setImageSj("원경");
						imageDTO.setImageCn("원경 사진");
					}
				}
				imageService.addImage(imageDTO, multipartFile, 270, 210);
			}
			ettCpsvDtMapper.update(ettCpsvDt);
		}
		else {
			ettCpsvDtMapper.update(ettCpsvDt);
		}
		return cnt;
	}
	
}
