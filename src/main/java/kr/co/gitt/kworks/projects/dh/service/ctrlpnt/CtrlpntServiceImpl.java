package kr.co.gitt.kworks.projects.dh.service.ctrlpnt;

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
import kr.co.gitt.kworks.projects.dh.mappers.EtlRgcpPsMapper;
import kr.co.gitt.kworks.projects.dh.model.EtlRgcpPs;
import kr.co.gitt.kworks.service.cmmn.ImageService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.kmaps.framework.common.Coordinate;
import com.kmaps.framework.spatialdata.geometry.GeomFactory;
import com.kmaps.framework.spatialdata.geometry.IGeometry;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class CtrlpntServiceImpl
/// kr.co.gitt.kworks.projects.dh.service.ctrlpnt \n
///   ㄴ CtrlpntServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 14. 오후 3:51:07 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 기준점 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("ctrlpntService")
@Profile({"dh_dev", "dh_oper"})
public class CtrlpntServiceImpl implements CtrlpntService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 이미지 서비스
	@Resource
	ImageService imageService;
	
	// 기준점 맵퍼
	@Resource
	EtlRgcpPsMapper etlRgcpPsMapper;
	
	// 기준점 시퀀스 서비스 - FTR_IDN
	@Resource
	EgovIdGnrService etlRgcpPsFtrIdnIdGnrService;
	
	// 기준점 시퀀스 서비스 - OBJECTID
	@Resource
	EgovIdGnrService etlRgcpPsObjectidIdGnrService;
	
	// 이미지 시퀀스 서비스
	@Resource
	EgovIdGnrService kwsImageIdGnrService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.ctrlpnt.CtrlpntService#listCtrlpnt(kr.co.gitt.kworks.projects.dh.model.EtlRgcpPs)
	/////////////////////////////////////////////
	public List<EtlRgcpPs> listCtrlpnt(EtlRgcpPs etlRgcpPs){
		return etlRgcpPsMapper.list(etlRgcpPs);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.ctrlpnt.CtrlpntService#selectOneCtrlpnt(java.lang.Long)
	/////////////////////////////////////////////
	public EtlRgcpPs selectOneCtrlpnt(EtlRgcpPs etlRgcpPs){
		EtlRgcpPs data = etlRgcpPsMapper.selectOne(etlRgcpPs);
		
		KwsImage kwsImage = new KwsImage();
		kwsImage.setFtrCde(data.getFtrCde());
		kwsImage.setFtrIdn(data.getFtrIdn());
		
		List<KwsImage> images = imageService.listAllImage(kwsImage);
		for(KwsImage image : images) {
			switch(image.getImageTy()) {
				case OUTLINE_MAP:
					data.setCtrlPntOutlineMap(image);
					break;
				case VISIBILITY_MAP:
					data.setCtrlPntVisibilityMap(image);
					break;
				case CLOSE_RANGE_VIEW:
					data.setCtrlPntCloseRangeView(image);
					break;
				case DISTANT_VIEW:
					data.setCtrlPntDistantView(image);
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
	/// @see kr.co.gitt.kworks.projects.dh.service.ctrlpnt.CtrlpntService#modifyCtrlpnt(kr.co.gitt.kworks.projects.dh.model.EtlRgcpPs)
	/////////////////////////////////////////////
	public Integer modifyCtrlpnt(EtlRgcpPs etlRgcpPs, MultipartRequest multipartRequest) throws Exception {

		Integer cnt = 0;
		
		String ftrCde = "ZA090";
		etlRgcpPs.setFtrCde(ftrCde);
		
		double nggX = Double.parseDouble(etlRgcpPs.getNggX());
		double nggY = Double.parseDouble(etlRgcpPs.getNggY());
		
		IGeometry point = GeomFactory.createPoint(new Coordinate(nggX, nggY));
		String orgWkt = point.toWKT();
		etlRgcpPs.setWkt(orgWkt);
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		etlRgcpPs.setUsrDes(userDTO.getUserId());
		
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd", Locale.KOREA);
		Date today = new Date();
		String udtYmd = formatter.format(today);
		etlRgcpPs.setUdtYmd(udtYmd);
		
		MultipartFile multipartFile = null;
		ImageDTO imageDTO = new ImageDTO();
		
		if(multipartRequest.getFileMap().entrySet() != null) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				
				imageDTO.setFtrCde(ftrCde);
				imageDTO.setFtrIdn(etlRgcpPs.getFtrIdn());

				double lcX = Double.parseDouble(etlRgcpPs.getNggX());
				double lcY = Double.parseDouble(etlRgcpPs.getNggY());
				
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
					if(StringUtils.equals("outlineMap", key)) {
						imageDTO.setImageTy(ImageTy.OUTLINE_MAP);
						imageDTO.setImageSj("원경");
						imageDTO.setImageCn("원경 사진");
						imageService.removeImage(imageDTO);
					}
					else if(StringUtils.equals("visibilityMap", key)) {
						imageDTO.setImageTy(ImageTy.VISIBILITY_MAP);
						imageDTO.setImageSj("시통도");
						imageDTO.setImageCn("시통도 사진");
						imageService.removeImage(imageDTO);
					}
					else if(StringUtils.equals("closeRangeView", key)) {
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
					if(StringUtils.equals("outlineMap", key)) {
						imageDTO.setImageTy(ImageTy.OUTLINE_MAP);
						imageDTO.setImageSj("원경");
						imageDTO.setImageCn("원경 사진");
					}
					else if(StringUtils.equals("visibilityMap", key)) {
						imageDTO.setImageTy(ImageTy.VISIBILITY_MAP);
						imageDTO.setImageSj("시통도");
						imageDTO.setImageCn("시통도 사진");
					}
					else if(StringUtils.equals("closeRangeView", key)) {
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
			etlRgcpPsMapper.update(etlRgcpPs);
		}
		else {
			etlRgcpPsMapper.update(etlRgcpPs);
		}
		return cnt;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.ctrlpnt.CtrlpntService#addCtrlpnt(kr.co.gitt.kworks.projects.dh.model.EtlRgcpPs)
	/////////////////////////////////////////////
	public Integer addCtrlpnt(EtlRgcpPs etlRgcpPs, MultipartRequest multipartRequest) throws Exception {
		String ftrCde = "ZA090";
		etlRgcpPs.setFtrCde(ftrCde);
		
		Long objectid = etlRgcpPsObjectidIdGnrService.getNextLongId();
		etlRgcpPs.setObjectid(objectid);
		
		Long ftrIdn = etlRgcpPsFtrIdnIdGnrService.getNextLongId();
		etlRgcpPs.setFtrIdn(ftrIdn);

		double nggX = Double.parseDouble(etlRgcpPs.getNggX());
		double nggY = Double.parseDouble(etlRgcpPs.getNggY());
		
		IGeometry point = GeomFactory.createPoint(new Coordinate(nggX, nggY));
		String orgWkt = point.toWKT();
		etlRgcpPs.setWkt(orgWkt);
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		etlRgcpPs.setUsrDes(userDTO.getUserId());
		
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd", Locale.KOREA);
		Date today = new Date();
		String udtYmd = formatter.format(today);
		etlRgcpPs.setUdtYmd(udtYmd);
		
		Integer rowCount = etlRgcpPsMapper.insert(etlRgcpPs);
		
		MultipartFile multipartFile = null;
		ImageDTO imageDTO = new ImageDTO();
		
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {

			imageDTO.setFtrCde(ftrCde);
			imageDTO.setFtrIdn(etlRgcpPs.getFtrIdn());

			double lcX = Double.parseDouble(etlRgcpPs.getNggX());
			double lcY = Double.parseDouble(etlRgcpPs.getNggY());
			
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
				if(StringUtils.equals("outlineMap", key)) {
					imageDTO.setImageTy(ImageTy.OUTLINE_MAP);
					imageDTO.setImageSj("원경");
					imageDTO.setImageCn("원경 사진");
				}
				else if(StringUtils.equals("visibilityMap", key)) {
					imageDTO.setImageTy(ImageTy.VISIBILITY_MAP);
					imageDTO.setImageSj("시통도");
					imageDTO.setImageCn("시통도 사진");
				}
				else if(StringUtils.equals("closeRangeView", key)) {
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
	
}
