package kr.co.gitt.kworks.projects.ss.service.ctrlpnt;

import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.kmaps.framework.common.Coordinate;
import com.kmaps.framework.spatialdata.geometry.GeomFactory;
import com.kmaps.framework.spatialdata.geometry.IGeometry;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.mappers.RdlPcbsPsMapper;
import kr.co.gitt.kworks.mappers.RdtPcbsDtMapper;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.model.KwsImage.ImageTy;
import kr.co.gitt.kworks.model.RdlPcbsPs;
import kr.co.gitt.kworks.model.RdtPcbsDt;
import kr.co.gitt.kworks.service.cmmn.ErrorService;
import kr.co.gitt.kworks.service.cmmn.ImageService;

/////////////////////////////////////////////
/// @class CtrlpntServiceImpl
/// kr.co.gitt.kworks.service.ctrlpnt \n
///   ㄴ CtrlpntServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 10. 31. 오후 7:17:03 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 도시기준점 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("ctrlpntService")
@Profile({"ss_dev", "ss_oper"})
public class CtrlpntServiceImpl implements CtrlpntService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 폴더 명 - 파일 저장 시 사용
	public static String FOLDER_NAME = "rdlPcbsPs";
	
	/// 파일 명 선행자 - 파일 저장 시 사용
	public static String FILE_NAME_PREFIX = "KWS_RDLPCBSPS_";
	
	@Resource
	RdlPcbsPsMapper rdlPcbsPsMapper;
	
	@Resource
	RdtPcbsDtMapper rdtPcbsDtMapper;
	
	@Resource
	EgovIdGnrService rdlPcbsPsFtrIdnIdGnrService;
	
	@Resource
	EgovIdGnrService rdlPcbsPsObjectidIdGnrService;
	
	@Resource
	EgovIdGnrService rdtPcbsDtIdGnrService;
	
	@Resource
	EgovIdGnrService cmtImgeEtIdGnrService;
	
	@Resource
	MessageSource messageSource;
	
	// 이미지 서비스
	@Resource
	ImageService imageService;

	/// 에러 서비스
	@Resource
	ErrorService errorService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ctrlpnt.CtrlpntService#listAll()
	/////////////////////////////////////////////
	public List<RdlPcbsPs> listAll(){
		return rdlPcbsPsMapper.listAll();
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ctrlpnt.CtrlpntService#list(kr.co.gitt.kworks.model.RdlPcbsPs)
	/////////////////////////////////////////////
	public List<RdlPcbsPs> list(RdlPcbsPs rdlPcbsPs){
		return rdlPcbsPsMapper.list(rdlPcbsPs);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ctrlpnt.CtrlpntService#selectOne(kr.co.gitt.kworks.model.RdlPcbsPs)
	/////////////////////////////////////////////
	public RdlPcbsPs selectOne(RdlPcbsPs rdlPcbsPs){
		RdlPcbsPs selectObj = rdlPcbsPsMapper.selectOne(rdlPcbsPs);
		
		KwsImage kwsImage = new KwsImage();
		kwsImage.setFtrCde(selectObj.getFtrCde());
		kwsImage.setFtrIdn(selectObj.getFtrIdn());
		
		List<KwsImage> images = imageService.listAllImage(kwsImage);
		for(KwsImage image : images) {
			switch(image.getImageTy()) {
				case OUTLINE_MAP:
					selectObj.setOutlineMapImage(image);
					break;
				case OBSERVATION_MAP:
					selectObj.setObservationMapImage(image);
					break;
				case CLOSE_RANGE_VIEW:
					selectObj.setCloseRangeViewImage(image);
					break;
				case DISTANT_VIEW:
					selectObj.setDistantViewImage(image);
					break;
				default:
					logger.warn("정의되지 않은 이미지 타입입니다.");
					break;
			}
		}
		return selectObj;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ctrlpnt.CtrlpntService#add(kr.co.gitt.kworks.model.RdlPcbsPs)
	/////////////////////////////////////////////
	public Integer add(RdlPcbsPs rdlPcbsPs, MultipartRequest multipartRequest, String userId) throws Exception{
		
		Integer cnt = 0;
		
		try {
			rdlPcbsPs.setFtrCde("ZC502");
			rdlPcbsPs.setFtrIdn(rdlPcbsPsFtrIdnIdGnrService.getNextLongId());
			rdlPcbsPs.setObjectid(rdlPcbsPsObjectidIdGnrService.getNextIntegerId());
			
			IGeometry point = GeomFactory.createPoint(new Coordinate(rdlPcbsPs.getGrsXco(), rdlPcbsPs.getGrsYco()));
			String orgWkt = point.toWKT();
			rdlPcbsPs.setGeom(orgWkt);
			
			MultipartFile multipartFile = null;
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				ImageDTO imageDTO = new ImageDTO();
				imageDTO.setImageSj(rdlPcbsPs.getSerIdn());
				imageDTO.setFtrCde(rdlPcbsPs.getFtrCde());
				imageDTO.setFtrIdn(rdlPcbsPs.getFtrIdn());
				imageDTO.setLcX(rdlPcbsPs.getGrsXco());
				imageDTO.setLcY(rdlPcbsPs.getGrsYco());
				imageDTO.setWrterId(userId);
				imageDTO.setUpdusrId(userId);
				
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					if(StringUtils.equals("outlineMap", key)) {
						imageDTO.setImageCn(ImageTy.OUTLINE_MAP.getValue());
						imageDTO.setImageTy(ImageTy.OUTLINE_MAP);
					}
					else if(StringUtils.equals("observationMap", key)) {
						imageDTO.setImageCn(ImageTy.OBSERVATION_MAP.getValue());
						imageDTO.setImageTy(ImageTy.OBSERVATION_MAP);
					}
					else if(StringUtils.equals("closeRangeView", key)) {
						imageDTO.setImageCn(ImageTy.CLOSE_RANGE_VIEW.getValue());
						imageDTO.setImageTy(ImageTy.CLOSE_RANGE_VIEW);				
					}
					else if(StringUtils.equals("distantView", key)) {
						imageDTO.setImageCn(ImageTy.DISTANT_VIEW.getValue());
						imageDTO.setImageTy(ImageTy.DISTANT_VIEW);
					}
					imageService.addImage(imageDTO, multipartFile, 270, 210);
				}
			}
			
			cnt = rdlPcbsPsMapper.add(rdlPcbsPs);
		} catch (Exception e) {
			errorService.addError(e);
		}
		
		return cnt;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ctrlpnt.CtrlpntService#modify(kr.co.gitt.kworks.model.RdlPcbsPs)
	/////////////////////////////////////////////
	public Integer modify(RdlPcbsPs rdlPcbsPs, MultipartRequest multipartRequest, String userId, List<String> deleteImageType) throws Exception{

		Integer cnt = 0;
		
		try {
			for(Integer i = 0; i < deleteImageType.size(); i++){
				String imageType = deleteImageType.get(i);
				if(imageType.equals("") || imageType == null){
					continue;
				}
				
				ImageDTO imageDTO = new ImageDTO();
				imageDTO.setImageSj(rdlPcbsPs.getSerIdn());
				imageDTO.setFtrCde(rdlPcbsPs.getFtrCde());
				imageDTO.setFtrIdn(rdlPcbsPs.getFtrIdn());
				imageDTO.setLcX(rdlPcbsPs.getGrsXco());
				imageDTO.setLcY(rdlPcbsPs.getGrsYco());
				imageDTO.setWrterId(userId);
				imageDTO.setUpdusrId(userId);
				
				if(imageType.equals("outlineMap")){
					imageDTO.setImageCn(ImageTy.OUTLINE_MAP.getValue());
					imageDTO.setImageTy(ImageTy.OUTLINE_MAP);
					imageService.removeImage(imageDTO);
				}else if(imageType.equals("observationMap")){
					imageDTO.setImageCn(ImageTy.OBSERVATION_MAP.getValue());
					imageDTO.setImageTy(ImageTy.OBSERVATION_MAP);
					imageService.removeImage(imageDTO);
				}else if(imageType.equals("closeRangeView")){
					imageDTO.setImageCn(ImageTy.CLOSE_RANGE_VIEW.getValue());
					imageDTO.setImageTy(ImageTy.CLOSE_RANGE_VIEW);	
					imageService.removeImage(imageDTO);
				}else if(imageType.equals("distantView")){
					imageDTO.setImageCn(ImageTy.DISTANT_VIEW.getValue());
					imageDTO.setImageTy(ImageTy.DISTANT_VIEW);
					imageService.removeImage(imageDTO);
				}
			}
			
			
			MultipartFile multipartFile = null;
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				ImageDTO imageDTO = new ImageDTO();
				
				imageDTO.setImageSj(rdlPcbsPs.getSerIdn());
				imageDTO.setFtrCde(rdlPcbsPs.getFtrCde());
				imageDTO.setFtrIdn(rdlPcbsPs.getFtrIdn());
				imageDTO.setLcX(rdlPcbsPs.getGrsXco());
				imageDTO.setLcY(rdlPcbsPs.getGrsYco());
				imageDTO.setWrterId(userId);
				imageDTO.setUpdusrId(userId);
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					if(StringUtils.equals("outlineMap", key)) {
						imageDTO.setImageCn(ImageTy.OUTLINE_MAP.getValue());
						imageDTO.setImageTy(ImageTy.OUTLINE_MAP);
					}
					else if(StringUtils.equals("observationMap", key)) {
						imageDTO.setImageCn(ImageTy.OBSERVATION_MAP.getValue());
						imageDTO.setImageTy(ImageTy.OBSERVATION_MAP);
					}
					else if(StringUtils.equals("closeRangeView", key)) {
						imageDTO.setImageCn(ImageTy.CLOSE_RANGE_VIEW.getValue());
						imageDTO.setImageTy(ImageTy.CLOSE_RANGE_VIEW);				
					}
					else if(StringUtils.equals("distantView", key)) {
						imageDTO.setImageCn(ImageTy.DISTANT_VIEW.getValue());
						imageDTO.setImageTy(ImageTy.DISTANT_VIEW);
					}
					
					imageService.addImage(imageDTO, multipartFile, 270, 210);
				}
			}
			
			cnt = rdlPcbsPsMapper.modify(rdlPcbsPs);
		} catch (Exception e) {
			errorService.addError(e);
		}
		
		return cnt;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ctrlpnt.CtrlpntService#remove(kr.co.gitt.kworks.model.RdlPcbsPs)
	/////////////////////////////////////////////
	public Integer remove(RdlPcbsPs rdlPcbsPs) throws Exception {
		
		Integer cnt = 0;
		
		try {
			cnt = rdlPcbsPsMapper.remove(rdlPcbsPs);
		} catch (Exception e) {
			errorService.addError(e);
		}
		
		return cnt;
	}
	
	
	public List<RdtPcbsDt> listRdtPcbsDt(RdtPcbsDt rdtPcbsDt){
		return rdtPcbsDtMapper.list(rdtPcbsDt);
	}
	
	public List<RdtPcbsDt> listAllRdtPcbsDt(){
		return rdtPcbsDtMapper.listAll();
	}
	
	public RdtPcbsDt selectOneRdtPcbsDt(RdtPcbsDt rdtPcbsDt){
		RdtPcbsDt selectObj = rdtPcbsDtMapper.selectOne(rdtPcbsDt);
		KwsImage kwsImage = new KwsImage();
		kwsImage.setFtrCde(selectObj.getFtrCde());
		kwsImage.setFtrIdn(selectObj.getFtrIdn());
		
		List<KwsImage> images = imageService.listAllImage(kwsImage);
		for(KwsImage image : images) {
			switch(image.getImageTy()) {
				case CLOSE_RANGE_VIEW:
					selectObj.setCloseRangeViewImage(image);
					break;
				case DISTANT_VIEW:
					selectObj.setDistantViewImage(image);
					break;
				case TEMP_VIEW:
					selectObj.setTempViewImage(image);
					break;
				default:
					logger.warn("정의되지 않은 이미지 타입입니다.");
					break;
			}
		}
		
		return selectObj;
	}
	
	public Integer addRdtPcbsDt(RdtPcbsDt rdtPcbsDt, MultipartRequest multipartRequest, String userId) throws Exception{
		Integer cnt = 0;
		
		try {		
			rdtPcbsDt.setFtrIdn(rdtPcbsDtIdGnrService.getNextLongId());
			rdtPcbsDt.setFtrCde("ZC503");
			
			MultipartFile multipartFile = null;
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				ImageDTO imageDTO = new ImageDTO();
				imageDTO.setImageSj(rdtPcbsDt.getSerIdn());
				imageDTO.setFtrCde(rdtPcbsDt.getFtrCde());
				imageDTO.setFtrIdn(rdtPcbsDt.getFtrIdn());
	//			imageDTO.setLcX(rdtPcbsDt.getGrsXco());
	//			imageDTO.setLcY(rdtPcbsDt.getGrsYco());
				
				imageDTO.setWrterId(userId);
				imageDTO.setUpdusrId(userId);
				
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					if(StringUtils.equals("closeRangeView", key)) {
						imageDTO.setImageCn(ImageTy.CLOSE_RANGE_VIEW.getValue());
						imageDTO.setImageTy(ImageTy.CLOSE_RANGE_VIEW);	
						KwsImage imgObj = imageService.addImage(imageDTO, multipartFile, 270, 210);
						rdtPcbsDt.setClsRngViewImgId(String.valueOf(imgObj.getImageNo()));
					}
					else if(StringUtils.equals("distantView", key)) {
						imageDTO.setImageCn(ImageTy.DISTANT_VIEW.getValue());
						imageDTO.setImageTy(ImageTy.DISTANT_VIEW);
						KwsImage imgObj = imageService.addImage(imageDTO, multipartFile, 270, 210);
						rdtPcbsDt.setDstntViewImgId(String.valueOf(imgObj.getImageNo()));
					}
					else if(StringUtils.equals("tempView", key)) {
						imageDTO.setImageCn(ImageTy.TEMP_VIEW.getValue());
						imageDTO.setImageTy(ImageTy.TEMP_VIEW);
						KwsImage imgObj = imageService.addImage(imageDTO, multipartFile, 270, 210);
						rdtPcbsDt.setEtcImgId(String.valueOf(imgObj.getImageNo()));
					}
				}
			}
			
			cnt = rdtPcbsDtMapper.add(rdtPcbsDt);
			
		} catch (Exception e) {
			errorService.addError(e);
		}
		
		return cnt;
	}
	
	public Integer modifyRdtPcbsDt(RdtPcbsDt rdtPcbsDt, MultipartRequest multipartRequest, String userId, List<String> deleteImageType) throws Exception{
		Integer cnt = 0;
		
		try {
			for(Integer i = 0; i < deleteImageType.size(); i++){
				String imageType = deleteImageType.get(i);
				if(imageType.equals("") || imageType == null){
					continue;
				}
				
				ImageDTO imageDTO = new ImageDTO();
				imageDTO.setImageSj(rdtPcbsDt.getSerIdn());
				imageDTO.setFtrCde(rdtPcbsDt.getFtrCde());
				imageDTO.setFtrIdn(rdtPcbsDt.getFtrIdn());
	//			imageDTO.setLcX(rdtPcbsDt.getGrsXco());
	//			imageDTO.setLcY(rdtPcbsDt.getGrsYco());
				imageDTO.setWrterId(userId);
				imageDTO.setUpdusrId(userId);
				
				if(imageType.equals("closeRangeView")){
					imageDTO.setImageCn(ImageTy.CLOSE_RANGE_VIEW.getValue());
					imageDTO.setImageTy(ImageTy.CLOSE_RANGE_VIEW);	
					imageService.removeImage(imageDTO);
				}else if(imageType.equals("distantView")){
					imageDTO.setImageCn(ImageTy.DISTANT_VIEW.getValue());
					imageDTO.setImageTy(ImageTy.DISTANT_VIEW);
					imageService.removeImage(imageDTO);
				}else if(imageType.equals("tempView")){
					imageDTO.setImageCn(ImageTy.TEMP_VIEW.getValue());
					imageDTO.setImageTy(ImageTy.TEMP_VIEW);
					imageService.removeImage(imageDTO);
				}
			}
			
			
			MultipartFile multipartFile = null;
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				ImageDTO imageDTO = new ImageDTO();
				
				imageDTO.setImageSj(rdtPcbsDt.getSerIdn());
				imageDTO.setFtrCde(rdtPcbsDt.getFtrCde());
				imageDTO.setFtrIdn(rdtPcbsDt.getFtrIdn());
	//			imageDTO.setLcX(rdtPcbsDt.getGrsXco());
	//			imageDTO.setLcY(rdtPcbsDt.getGrsYco());
				imageDTO.setWrterId(userId);
				imageDTO.setUpdusrId(userId);
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					if(StringUtils.equals("closeRangeView", key)) {
						imageDTO.setImageCn(ImageTy.CLOSE_RANGE_VIEW.getValue());
						imageDTO.setImageTy(ImageTy.CLOSE_RANGE_VIEW);		
						KwsImage imgObj = imageService.addImage(imageDTO, multipartFile, 270, 210);
						rdtPcbsDt.setClsRngViewImgId(String.valueOf(imgObj.getImageNo()));
					}
					else if(StringUtils.equals("distantView", key)) {
						imageDTO.setImageCn(ImageTy.DISTANT_VIEW.getValue());
						imageDTO.setImageTy(ImageTy.DISTANT_VIEW);
						KwsImage imgObj = imageService.addImage(imageDTO, multipartFile, 270, 210);
						rdtPcbsDt.setDstntViewImgId(String.valueOf(imgObj.getImageNo()));
					}
					else if(StringUtils.equals("tempView", key)) {
						imageDTO.setImageCn(ImageTy.TEMP_VIEW.getValue());
						imageDTO.setImageTy(ImageTy.TEMP_VIEW);
						KwsImage imgObj = imageService.addImage(imageDTO, multipartFile, 270, 210);
						rdtPcbsDt.setEtcImgId(String.valueOf(imgObj.getImageNo()));
					}
				}
			}
			
			cnt = rdtPcbsDtMapper.modify(rdtPcbsDt);
		} catch (Exception e) {
			errorService.addError(e);
		}
		
		return cnt;
	}
	
	public Integer removeRdtPcbsDt(RdtPcbsDt rdtPcbsDt) throws Exception{
		Integer cnt = 0;
		
		try {
			cnt = rdtPcbsDtMapper.remove(rdtPcbsDt);
		} catch (Exception e) {
			errorService.addError(e);
		}
		
		return cnt;
	}
}
