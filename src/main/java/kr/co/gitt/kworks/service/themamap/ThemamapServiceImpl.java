package kr.co.gitt.kworks.service.themamap;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import kr.co.gitt.kworks.cmmn.dto.ThemamapSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.mappers.KwsThemamapBaseMapMapper;
import kr.co.gitt.kworks.mappers.KwsThemamapLyrMapper;
import kr.co.gitt.kworks.mappers.KwsThemamapMapper;
import kr.co.gitt.kworks.model.KwsDept;
import kr.co.gitt.kworks.model.KwsLyr;
import kr.co.gitt.kworks.model.KwsSys;
import kr.co.gitt.kworks.model.KwsThemamap;
import kr.co.gitt.kworks.model.KwsThemamap.ThemamapTy;
import kr.co.gitt.kworks.model.KwsThemamapBaseMap;
import kr.co.gitt.kworks.model.KwsThemamapLyr;
import kr.co.gitt.kworks.service.cmmn.ImageService;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.dept.DeptService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;
import kr.co.gitt.kworks.service.layer.LayerService;
import kr.co.gitt.kworks.service.sld.SldCacheService;
import kr.co.gitt.kworks.service.sys.SysService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class ThemamapServiceImpl
/// kr.co.gitt.kworks.service.themamap \n
///   ㄴ ThemamapServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 10. 오후 5:21:33 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 주제도 서비스 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("themamapService")
public class ThemamapServiceImpl implements ThemamapService {
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 주제도 ID 생성 서비스
	@Resource
	EgovIdGnrService kwsThemamapIdGnrService;
	
	/// 레이어 서비스
	@Resource
	LayerService layerService;

	/// SLD 캐시 서비스
	@Resource
	SldCacheService sldCacheService;
	
	/// 주제도 맵퍼
	@Resource
	KwsThemamapMapper kwsThemamapMapper;
	
	/// 주제도 레이어 맵퍼
	@Resource
	KwsThemamapLyrMapper kwsThemamapLyrMapper;
	
	/// 주제도 기본지도 맵퍼
	@Resource
	KwsThemamapBaseMapMapper kwsThemamapBaseMapMapper;
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/// 부서 서비스
	@Resource
	DeptService deptService;
	
	/// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	/// 시스템 서비스
	@Resource
	SysService sysService;
	
	/// 메세지 소스
	@Resource
    private MessageSource messageSource;
	
	/// 이미지 서비스
	@Resource
	private ImageService imageService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.themamap.ThemamapService#listAllThemamap(kr.co.gitt.kworks.cmmn.dto.ThemamapSearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsThemamap> listAllThemamap(ThemamapSearchDTO themamapSearchDTO) {
		return kwsThemamapMapper.listAll(themamapSearchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.themamap.ThemamapService#selectOneThemamap(kr.co.gitt.kworks.cmmn.dto.ThemamapSearchDTO)
	/////////////////////////////////////////////
	@Override
	public KwsThemamap selectOneThemamap(ThemamapSearchDTO themamapSearchDTO) {
		KwsThemamap kwsThemamap = kwsThemamapMapper.selectOne(themamapSearchDTO);
		kwsThemamap.setKwsThemamapBaseMaps(kwsThemamapBaseMapMapper.listByAuthor(themamapSearchDTO));
		return kwsThemamap;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.themamap.ThemamapService#insertThemamap(kr.co.gitt.kworks.model.KwsThemamap, java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public Integer insertThemamap(KwsThemamap kwsThemamap, String jsonStr, String sldUrl, String baseMapStr) throws FdlException, IOException {
		Long themamapId = kwsThemamapIdGnrService.getNextLongId();
		kwsThemamap.setThemamapId(themamapId);
		kwsThemamap.setThemamapTy(ThemamapTy.USER);
		
		// 주제도 저장
		int rowCount = kwsThemamapMapper.insert(kwsThemamap);
		
		// 주제도 레이어 저장
		addThememapLayers(themamapId, jsonStr);
		
		// 주제도 기본지도 저장
		addThemamapBaseMaps(themamapId, baseMapStr);
		
		// 썸네일 저장
		storeThumnail(themamapId, sldUrl);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn addThememapLayers
	/// @brief 함수 간략한 설명 : 주제도 레이어 목록 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param themamapId
	/// @param jsonStr
	/// @throws JsonProcessingException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void addThememapLayers(Long themamapId, String jsonStr) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(jsonStr);
		ArrayNode namedLayers = (ArrayNode) jsonNode.path("namedLayers");
		
		String[] layerIds = new String[namedLayers.size()];
		for(int i=0, len=namedLayers.size(); i < len; i++) {
			ObjectNode namedLayer = (ObjectNode) namedLayers.get(i);
			layerIds[i] = namedLayer.get("name").asText();		
		}
		
		for(int i=0, len=namedLayers.size(); i < len; i++) {
			KwsThemamapLyr kwsThemamapLyr = new KwsThemamapLyr();
			
			ObjectNode namedLayer = (ObjectNode) namedLayers.get(i);
			String lyrId = namedLayer.get("name").asText();
			
			kwsThemamapLyr.setThemamapId(themamapId);
			kwsThemamapLyr.setLyrId(lyrId);
			kwsThemamapLyr.setSortOrdr(i+1);
			kwsThemamapLyr.setLyrStyle(namedLayer.toString());
			
			kwsThemamapLyrMapper.insert(kwsThemamapLyr);
		}
	}
	
	/////////////////////////////////////////////
	/// @fn addThemamapBaseMaps
	/// @brief 함수 간략한 설명 : 주제도 기본지도 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param themamapId
	/// @param baseMapStr
	/// @throws JsonProcessingException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void addThemamapBaseMaps(Long themamapId, String baseMapStr) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(baseMapStr);
		
		ArrayNode baseMaps = (ArrayNode) jsonNode.path("baseMaps");
		for(int i=0, len=baseMaps.size(); i < len; i++) {
			JsonNode baseMap = baseMaps.get(i);
			
			Long mapNo = baseMap.get("mapNo").asLong();
			String visibleAt = baseMap.get("visible").asBoolean()?"Y":"N";
			Double opacity = baseMap.get("opacity").asDouble(0);
			
			KwsThemamapBaseMap kwsThemamapBaseMap = new KwsThemamapBaseMap();
			kwsThemamapBaseMap.setMapNo(mapNo);
			kwsThemamapBaseMap.setThemamapId(themamapId);
			kwsThemamapBaseMap.setSortOrdr(i+1);
			kwsThemamapBaseMap.setVisibleAt(visibleAt);
			kwsThemamapBaseMap.setOpacity(opacity);
			
			kwsThemamapBaseMapMapper.insert(kwsThemamapBaseMap);
		}
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.themamap.ThemamapService#removeThemamap(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer removeThemamap(Long kwsThemamapId) {
		kwsThemamapLyrMapper.delete(kwsThemamapId);
		kwsThemamapBaseMapMapper.delete(kwsThemamapId);
		return kwsThemamapMapper.delete(kwsThemamapId);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.themamap.ThemamapService#getThumnail(java.lang.Long, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public BufferedImage getThumnail(Long themamapId, String sldUrl) throws IOException {
		String path = messageSource.getMessage("Com.Theme.Path", null, Locale.getDefault());
		FileUtils.forceMkdir(new File(path));
		
		//String filePath = path + themamapId + ".jpeg";
		String filePath = path + themamapId + ".png";
		File file = new File(filePath);
		if(file.isFile()) {
			return ImageIO.read(file);
		}
		else {
			return storeThumnail(themamapId, sldUrl);
		}
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.themamap.ThemamapService#getSldString(kr.co.gitt.kworks.model.KwsThemamap)
	/////////////////////////////////////////////
	@Override
	public String getSldString(KwsThemamap kwsThemamap) throws JsonProcessingException, IOException {
		
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<KwsLyr> kwsLyrs = layerService.listAllLayer(null);
		
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put("name", kwsThemamap.getThemamapId());
		objectNode.put("title", kwsThemamap.getThemamapNm());
		ArrayNode arrayNode = objectMapper.createArrayNode();
		
		if(StringUtils.equals(prjCode, "dh")){
			List<KwsSys> sysList = sysService.listSys();
			boolean isSysThemamap = false;
			
			for(KwsSys kwsSys : sysList){
				if(kwsThemamap.getThemamapId().equals(kwsSys.getThemamapId())){
					isSysThemamap = true;
					break;
				}
			}
			
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			String deptCode = userDTO.getDeptCode();
			
			for(KwsThemamapLyr kwsThemamapLyr : kwsThemamap.getKwsThemamapLyrs()) {
				
				String layerId = kwsThemamapLyr.getLyrId();
				String layerStyle = kwsThemamapLyr.getLyrStyle();
				
				if(isSysThemamap && !StringUtils.equals(deptCode, "4210209") && (StringUtils.equals(layerId, "BML_PROP_AS") || StringUtils.equals(layerId, "BML_BUID_AS") || StringUtils.equals(layerId, "BML_LOAN_AS") || StringUtils.equals(layerId, "BML_OCCP_AS") )){
					
						ObjectNode newLyrBassStyle = new ObjectMapper().createObjectNode();
						
						newLyrBassStyle.put("visible", true);
						newLyrBassStyle.put("name", kwsThemamapLyr.getLyrId());
						
						String dataId = dataService.selectOneData(kwsThemamapLyr.getLyrId()).getDataAlias();
						newLyrBassStyle.put("title", dataId);
						
						ArrayNode rules = new ObjectMapper().createArrayNode();
						ObjectNode rule = new ObjectMapper().createObjectNode();
						rule.put("visible", true);
						ObjectNode filter = new ObjectMapper().createObjectNode();
							filter.put("type", "==");
							filter.put("property", "MAN_CDE");
							filter.put("literal", deptCode + "0000");
							rule.put("filter", filter);
						ObjectNode polygon = new ObjectMapper().createObjectNode();
						
						if(StringUtils.equals(layerId, "BML_PROP_AS")){
							polygon.put("fill", "#a92fa0");
							polygon.put("fillOpacity", "0.5");
							polygon.put("stroke", "#0000FF");
							polygon.put("strokeOpacity", "1.0");
							polygon.put("strokeWidth", "1.0");
						}else if(StringUtils.equals(layerId, "BML_BUID_AS")){
							polygon.put("fill", "#5a6391");
							polygon.put("fillOpacity", "0.5");
							polygon.put("stroke", "#2ee6d1");
							polygon.put("strokeOpacity", "1.0");
							polygon.put("strokeWidth", "1.0");
						}else if(StringUtils.equals(layerId, "BML_LOAN_AS")){
							polygon.put("fill", "#49EE7F");
							polygon.put("fillOpacity", "0.5");
							polygon.put("stroke", "#4d1c69");
							polygon.put("strokeOpacity", "1.0");
							polygon.put("strokeWidth", "1.0");
						}else if(StringUtils.equals(layerId, "V_BML_ACEX_AS")){
							polygon.put("fill", "#128f5f");
							polygon.put("fillOpacity", "0.5");
							polygon.put("stroke", "#7f4d9c");
							polygon.put("strokeOpacity", "1.0");
							polygon.put("strokeWidth", "1.0");
						}else{
							polygon.put("fill", "#aed93d");
							polygon.put("fillOpacity", "0.5");
							polygon.put("stroke", "#414756");
							polygon.put("strokeOpacity", "1.0");
							polygon.put("strokeWidth", "1.0");
						}
						rule.put("polygon", polygon);
						rule.put("name", "DEFAULT");
						
						KwsDept kwsDept = deptService.selectOneDept(deptCode);
						rule.put("title", kwsDept.getDeptNm());
						
						rules.add(rule);
						
						newLyrBassStyle.put("rules", rules);
						
						arrayNode.add(newLyrBassStyle);
				}else{
					if(StringUtils.isNotBlank(layerStyle)) {
						arrayNode.add(objectMapper.readTree(layerStyle));
					}
					else {
						for(KwsLyr kwsLyr : kwsLyrs) {
							if(StringUtils.equals(layerId, kwsLyr.getLyrId())) {
								arrayNode.add(objectMapper.readTree(kwsLyr.getLyrBassStyle()));
								break;
							}
						}
					}
				}
			}
		}else{
			for(KwsThemamapLyr kwsThemamapLyr : kwsThemamap.getKwsThemamapLyrs()) {
				String layerId = kwsThemamapLyr.getLyrId();
				String layerStyle = kwsThemamapLyr.getLyrStyle();
				if(StringUtils.isNotBlank(layerStyle)) {
					arrayNode.add(objectMapper.readTree(layerStyle));
				}
				else {
					for(KwsLyr kwsLyr : kwsLyrs) {
						if(StringUtils.equals(layerId, kwsLyr.getLyrId())) {
							arrayNode.add(objectMapper.readTree(kwsLyr.getLyrBassStyle()));
							break;
						}
					}
				}
			}
		}
		
		objectNode.put("namedLayers", arrayNode);
		
		return objectNode.toString();
	}
	
	/////////////////////////////////////////////
	/// @fn storeThumnail
	/// @brief 함수 간략한 설명 : 썸네일 저장
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param themamapId
	/// @param sldUrl
	/// @return
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private BufferedImage storeThumnail(Long themamapId, String sldUrl) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		ThemamapSearchDTO themamapSearchDTO = new ThemamapSearchDTO();
		themamapSearchDTO.setThemamapId(themamapId);
		
		KwsThemamap kwsThemamap = kwsThemamapMapper.selectOne(themamapSearchDTO);
		StringBuffer layers = new StringBuffer();
		for(int i=0, len=kwsThemamap.getKwsThemamapLyrs().size(); i < len; i++) {
			KwsThemamapLyr kwsThemamapLyr = kwsThemamap.getKwsThemamapLyrs().get(i);
			String layerStyle = kwsThemamapLyr.getLyrStyle();
			if(StringUtils.isBlank(layerStyle)) {
				layers.append(kwsThemamapLyr.getLyrId()).append(",");
			}
			else {
				JsonNode jsonNode = objectMapper.readTree(layerStyle);
				if(jsonNode.get("visible").asBoolean()) {
					layers.append(kwsThemamapLyr.getLyrId()).append(",");
				}
			}
		}
		layers.deleteCharAt(layers.length()-1);
		
		String sld = sldCacheService.putSLD(getSldString(kwsThemamap));
		String urlStr = messageSource.getMessage("Map.Url.WMS", null, Locale.getDefault());
		String projection = messageSource.getMessage("Map.Projection", null, Locale.getDefault());
		String thumnailBbox = messageSource.getMessage("Map.Themamap.Thumnail.BBOX", null, Locale.getDefault());
		String defaultCase = messageSource.getMessage("Com.DefaultCase", null, Locale.getDefault());
		String wmsVersion = messageSource.getMessage("Map.Wms.Version", null, Locale.getDefault());
		
		
		
		String layerParam = null;
		if(StringUtils.equals(defaultCase, "lower")) {
			layerParam = StringUtils.lowerCase(layers.toString());
		}
		else {
			layerParam = StringUtils.upperCase(layers.toString());
		}
		
		StringBuffer params = new StringBuffer();
		params.append("SERVICE=WMS");
		params.append("&VERSION=");
		params.append(wmsVersion);
		params.append("&REQUEST=GetMap");
		params.append("&FORMAT=image/jpeg");
		params.append("&TRANSPARENT=true");
		params.append("&");
		if(StringUtils.equals("1.1.0", wmsVersion)) {
			params.append("SRS");
		}
		else {
			params.append("CRS");
		}
		params.append("=");
		params.append(projection);
		params.append("&LAYERS=");
		params.append(layerParam);
		params.append("&STYLES=");
		params.append(layerParam);
		params.append("&WIDTH=650");
		params.append("&HEIGHT=650");
		params.append("&BBOX=");
		params.append(thumnailBbox);
		params.append("&SLD=");
		params.append(sldUrl);
		params.append(sld);
		
		HttpURLConnection huc = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedImage clip = null;
		
		try {
			URL url = new URL(urlStr.concat("?")+params.toString());
			URLConnection connection = url.openConnection();
			huc = (HttpURLConnection)connection;
			huc.setRequestMethod("GET");
			huc.setDoOutput(true);
			huc.setDoInput(true);
			huc.setUseCaches(false);
			huc.setDefaultUseCaches(false);
			
			
			String path = messageSource.getMessage("Com.Theme.Path", null, Locale.getDefault());
			FileUtils.forceMkdir(new File(path));
			
			String filePath = path + kwsThemamap.getThemamapId() + ".jpeg";
			
			is = huc.getInputStream();
			
			os = new FileOutputStream(filePath);
			
			BufferedImage bufferedImage = ImageIO.read(is);
			
			clip = imageService.resize(bufferedImage, 65, 65);
			
			
			ImageIO.write(clip, "jpg", os);
		} catch (IOException e) {
			logger.warn(e.getMessage());
		} finally {
			if(is != null) {
				is.close();
			}
			if(os != null) {
				os.close();
			}
			if(huc != null) {
				huc.disconnect();
			}
		}
		return clip;
	}

}
