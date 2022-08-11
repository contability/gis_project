package kr.co.gitt.kworks.service.sharingMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.dto.SharingMapDTO;
import kr.co.gitt.kworks.dto.feature.FeatureKmlResultDTO;
import kr.co.gitt.kworks.dto.feature.KmlPlacemarkDTO;
import kr.co.gitt.kworks.mappers.CmtCnrsMapMapper;
import kr.co.gitt.kworks.model.CmtCnrsMap;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.service.cmmn.ImageService;
import kr.co.gitt.kworks.service.feature.FeatureService;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kmaps.framework.action.coordinateconvert.SRSTransform;
import com.kmaps.framework.common.Coordinate;
import com.kmaps.framework.common.Coordinates;
import com.kmaps.framework.reference.ISpatialReferenceSystem;
import com.kmaps.framework.reference.initialize.DefaultSRS;
import com.kmaps.framework.spatialdata.geometry.IGeometry;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/////////////////////////////////////////////
/// @class SharingMapServiceImpl
/// kr.co.gitt.kworks.service.sharingMap \n
///   ㄴ SharingMapServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | 이승재 |
///    | Date | 2019. 5. 15. |
///    | Class Version | v1.0 |
///    | 작업자 | 이승재 |
/// @section 상세설명
/// - 이 클래스는 이미지 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("sharingMapService")
@Profile({"yg_dev", "yg_oper"})
public class SharingMapServiceImpl implements SharingMapService {

	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 메세지 소스
	@Resource(name="messageSource")
	private MessageSource messageSource;
		
	//공유지도 맵퍼
	@Resource
	CmtCnrsMapMapper cmtCnrsMapMapper;
	
	//이미지 서비스
	@Resource
	ImageService imageService;
	
	
	//KML 파싱 서비스
	@Resource
	FeatureService featureService;
	
	// 공유지도 시퀀스 서비스
	@Resource
	EgovIdGnrService cmtCnrsMapIdGnrService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method)  KML파일 임포트
	/// @remark
	/// - KML 파일을 파싱하여 저장
	/// @see kr.co.gitt.kworks.service.sharingMap#importKml(...)
	/////////////////////////////////////////////
	@Override
	public boolean importKml(MultipartFile multipartFile, SharingMapDTO sharingMapDTO) throws Exception {
		boolean isSuccess = true;
		
		File kmlFile = comvertMultipartFiletoFile(multipartFile);		
		FeatureKmlResultDTO featureKmlResultDTO = featureService.getFeaturesFromKmlFile(kmlFile);	//KML파일 파싱
		kmlFile.delete();
		
		List<CmtCnrsMap> cmtCntsMapList = getCmtCnrsMapList(featureKmlResultDTO, sharingMapDTO);	//KML파싱 결과를 List<CmtCnrsMap>로 변환
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", cmtCntsMapList);
        cmtCnrsMapMapper.insertAll(map);
        
		return isSuccess;
	}
	
	//KML파싱 결과를 List<CmtCnrsMap>로 변환
	private List<CmtCnrsMap> getCmtCnrsMapList(
			FeatureKmlResultDTO featureKmlResultDTO, SharingMapDTO sharingMapDTO) throws Exception {
		List<CmtCnrsMap> cmtCnrsMaps = new ArrayList<CmtCnrsMap>();
		String ftrCde = sharingMapDTO.getFtrCde();
		String wrterId = sharingMapDTO.getWrterId();
		String updusrId = sharingMapDTO.getUpdusrId();
		
		List<KmlPlacemarkDTO> kmlPlacemarks = featureKmlResultDTO.getPlacemark();
		if (kmlPlacemarks.size() > 0) {
			for (KmlPlacemarkDTO kmlPlacemark : kmlPlacemarks) {
				Coordinate coord = getTargetSrsCoordinate(kmlPlacemark.getGeometry());	//좌표변환 및 geometry를 Coordinate로 변환
				
				CmtCnrsMap cmtCnrsMap = new CmtCnrsMap();
				cmtCnrsMap.setFtrCde(ftrCde);
				cmtCnrsMap.setFtrIdn(cmtCnrsMapIdGnrService.getNextLongId());
				cmtCnrsMap.setWrterId(wrterId);
				cmtCnrsMap.setPoiSj(kmlPlacemark.getName());
				cmtCnrsMap.setPoiCn(kmlPlacemark.getDescription());
				cmtCnrsMap.setLcX(coord.getX());
				cmtCnrsMap.setLcY(coord.getY());
				cmtCnrsMap.setUpdusrId(updusrId);
				cmtCnrsMaps.add(cmtCnrsMap);
			}
		}
		return cmtCnrsMaps;
	}

	//Point Geometry를 Coordinate로 변환
	private Coordinate getTargetSrsCoordinate(IGeometry geometry) throws Exception {
		
		//좌표변환 (WGS84 -> )
		String fromSrid = "EPSG:4326";
		String toSrid = messageSource.getMessage("Map.Projection", null, Locale.getDefault());
		SRSTransform srsTrans = null;
		srsTrans = new SRSTransform();
		
		// 소스 좌표계
		ISpatialReferenceSystem sourceSrs = DefaultSRS.get(fromSrid, 0, 0);
		// 목표 좌표계
		ISpatialReferenceSystem targetSrs = DefaultSRS.get(toSrid, 0, 0);
		srsTrans.set(sourceSrs, targetSrs);
		
		IGeometry geom = null;
		geometry.setSRSName(fromSrid);
		if(StringUtils.isNotBlank(toSrid) && !StringUtils.equals(fromSrid, toSrid)) {
			geom = srsTrans.convert(geometry);
		}
		else {
			geom = geometry;
		}
		Coordinates coords = geom.getCoordinates();	//Geometry를 Coordinate로 변환
		return coords.get(0);
	}

	private File comvertMultipartFiletoFile(MultipartFile multipartFile) throws IOException {
		File toFile = new File(multipartFile.getOriginalFilename());		
		multipartFile.transferTo(toFile);
		return toFile;
	}
	
	@Override
	public SharingMapDTO addSharingMap(SharingMapDTO sharingMapDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception {
		addSharingMap(sharingMapDTO);
		
		if (multipartFile != null) {
			ImageDTO imageDTO = getImageDTO(sharingMapDTO);
			
			KwsImage kwsImage = imageService.addImage(imageDTO, multipartFile, thumbnailWidth, thumbnailHeight);
			sharingMapDTO.setFileExtsn(kwsImage.getFileExtsn());
			sharingMapDTO.setImageFileNo(kwsImage.getImageFileNo());
			sharingMapDTO.setImageNo(kwsImage.getImageNo());
			sharingMapDTO.setOutptFileNo(kwsImage.getOutptFileNo());
			sharingMapDTO.setThumbFileNo(kwsImage.getThumbFileNo());
		}
		
		return sharingMapDTO;
	}
	
	@Override
	public SharingMapDTO addSharingMap(SharingMapDTO sharingMapDTO) throws Exception {
		sharingMapDTO.setFtrIdn(cmtCnrsMapIdGnrService.getNextLongId());
		List<CmtCnrsMap> cmtCnrsMaps = new ArrayList<CmtCnrsMap>();
		ModelMapper modelMapper = new ModelMapper();
		cmtCnrsMaps.add(modelMapper.map(sharingMapDTO, CmtCnrsMap.class));
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", cmtCnrsMaps);
		cmtCnrsMapMapper.insertAll(map);
		return sharingMapDTO;
	}
	
	@Override
	public List<SharingMapDTO> listAll() {
		return cmtCnrsMapMapper.listAll();
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method)  조회
	/// @remark
	/// - 공유지도 한건 조회
	/// @see kr.co.gitt.kworks.service.sharingMap#selectOne(...)
	/////////////////////////////////////////////
	@Override
	public SharingMapDTO selectOne(SharingMapDTO sharingMapDTO) {
		ModelMapper modelMapper = new ModelMapper();
		SharingMapDTO resultSharingMapDTO = modelMapper.map(cmtCnrsMapMapper.selectOne(sharingMapDTO.getFtrIdn()), SharingMapDTO.class);
		//이미자 파일 정보 조회
		getStroedImageInfo(resultSharingMapDTO);
		return resultSharingMapDTO;
	}
	
	private void getStroedImageInfo(SharingMapDTO sharongMapDTO) {
		KwsImage kwsImage = new KwsImage();
		kwsImage.setFtrCde(sharongMapDTO.getFtrCde());
		kwsImage.setFtrIdn(sharongMapDTO.getFtrIdn());
		List<KwsImage> kwsImages = imageService.listAllImageFile(kwsImage);
		
		if (kwsImages.size() == 1) {
			sharongMapDTO.setImageNo(kwsImages.get(0).getImageNo());
			sharongMapDTO.setImageFileNo(kwsImages.get(0).getImageFileNo());
			sharongMapDTO.setThumbFileNo(kwsImages.get(0).getThumbFileNo());
			sharongMapDTO.setOutptFileNo(kwsImages.get(0).getOutptFileNo());
			sharongMapDTO.setFileExtsn(kwsImages.get(0).getFileExtsn());
		}
	}

	@Override
	public SharingMapDTO modifySharingMap(SharingMapDTO sharingMapDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception {
		if (multipartFile != null) {
			ImageDTO imageDTO = getImageDTO(sharingMapDTO);
			
			KwsImage kwsImage;
			if (imageDTO.getImageNo() == null) {
				kwsImage = imageService.addImage(imageDTO, multipartFile, thumbnailWidth, thumbnailHeight);
			} else {
				kwsImage = imageService.modifyImage(imageDTO, multipartFile, thumbnailWidth, thumbnailHeight);
			}
			sharingMapDTO.setFileExtsn(kwsImage.getFileExtsn());
			sharingMapDTO.setImageFileNo(kwsImage.getImageFileNo());
			sharingMapDTO.setImageNo(kwsImage.getImageNo());
			sharingMapDTO.setOutptFileNo(kwsImage.getOutptFileNo());
			sharingMapDTO.setThumbFileNo(kwsImage.getThumbFileNo());
		}
		
		ModelMapper modelMapper = new ModelMapper();
		cmtCnrsMapMapper.update(modelMapper.map(sharingMapDTO, CmtCnrsMap.class));
		return sharingMapDTO;
	}

	private ImageDTO getImageDTO(SharingMapDTO sharingMapDTO) {
		ImageDTO imageDTO = new ImageDTO();
		imageDTO.setFtrCde(sharingMapDTO.getFtrCde());
		imageDTO.setFtrIdn(sharingMapDTO.getFtrIdn());
		imageDTO.setImageCn(sharingMapDTO.getPoiCn());
		imageDTO.setImageFileNo(sharingMapDTO.getImageFileNo());
		imageDTO.setImageNo(sharingMapDTO.getImageNo());
		imageDTO.setImageSj(sharingMapDTO.getPoiSj());
		imageDTO.setImageTy(KwsImage.ImageTy.TEMP_VIEW);
		imageDTO.setLcX(sharingMapDTO.getLcX());
		imageDTO.setLcY(sharingMapDTO.getLcY());
		imageDTO.setOutptFileNo(sharingMapDTO.getOutptFileNo());
		imageDTO.setThumbFileNo(sharingMapDTO.getThumbFileNo());
		imageDTO.setUpdusrId(sharingMapDTO.getUpdusrId());
		if (sharingMapDTO.getImageNo() == null) {
			imageDTO.setWrterId(sharingMapDTO.getUpdusrId());
		}
		return imageDTO;
	}
	
	@Override
	public Integer removeSharingMap(SharingMapDTO sharingMapDTO) {
		ImageDTO imageDTO = getImageDTO(sharingMapDTO);
		imageService.removeImage(imageDTO);
		
		Integer sharingMapRemoveRows = cmtCnrsMapMapper.delete(sharingMapDTO.getFtrIdn());
		
		return sharingMapRemoveRows;
	}
}
