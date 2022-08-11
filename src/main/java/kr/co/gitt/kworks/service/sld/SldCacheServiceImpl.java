package kr.co.gitt.kworks.service.sld;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import javax.annotation.Resource;

import kr.co.gitt.kworks.dto.sld.json.KworksSld;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class SldCacheServiceImpl
/// kr.co.gitt.kworks.service.sld \n
///   ㄴ SldCacheServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 28. 오전 10:30:35 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 SLD 캐시 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("sldCacheService")
public class SldCacheServiceImpl implements SldCacheService {
Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 캐쉬 매니저
	@Resource(name="cacheManager")
	CacheManager cacheManager;
	
	/// 메세지 소스
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	/// JSon, Sld 변환 서비스 (KMaps)
	@Resource(name="jsonToSldService")
	JsonToSldService jsonToSldService;
	
	/// JSon, Sld 변환 서비스 (Arcgis)
	@Resource(name="jsonToArcSldService")
	JsonToSldService jsonToArcSldService;
	
	/// JSon, Sld 변환 서비스 (Arcgis)
	@Resource(name="geoServerSldConverterService")
	SldConverterService geoServerSldConverterService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.sld.SldCacheService#putSLD(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public String putSLD(String json) throws JsonParseException, JsonMappingException, IOException {
		String serverType = messageSource.getMessage("Map.ServerType", null, Locale.getDefault());
		
		UUID uuid = UUID.randomUUID();
		String authKey = Base64.encodeBase64URLSafeString(uuid.toString().getBytes());
		
		String sld = null;
		ObjectMapper mapper = new ObjectMapper();
		if(serverType.equals("kmaps")) {
			sld = jsonToSldService.jsonToSld(json);
		}
		else if(serverType.equals("geoserver")) {
			KworksSld kworksSld = mapper.readValue(json, KworksSld.class);
			sld = geoServerSldConverterService.convert(kworksSld);
		}
		else if(serverType.equals("arcgis")) {
			sld = jsonToArcSldService.jsonToSld(json);
		}
		cacheManager.getCache("SLD").put(authKey, sld);
		
		/// KMaps 엔진이 아닐 경우 고급출력을 위한 SLD 를 따로 생성한다.
		if(!StringUtils.equals("kmaps", serverType)) {
			cacheManager.getCache("SLD").put(authKey+"Print", jsonToSldService.jsonToSld(json));
		}
		return authKey;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.sld.SldCacheService#getSLD(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public String getSLD(String sldKey) {
		return cacheManager.getCache("SLD").get(sldKey).get().toString();
	}

}
