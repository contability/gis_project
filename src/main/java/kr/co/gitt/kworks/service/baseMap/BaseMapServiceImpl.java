package kr.co.gitt.kworks.service.baseMap;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.dto.baseMap.BaseMapSearchDTO;
import kr.co.gitt.kworks.mappers.KwsBaseMapMapper;
import kr.co.gitt.kworks.model.KwsBaseMap;
import kr.co.gitt.kworks.model.KwsBaseMapHost;
import kr.co.gitt.kworks.model.KwsBaseMapLevel;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class BaseMapServiceImpl
/// kr.co.gitt.kworks.service.baseMap \n
///   ㄴ BaseMapServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 2. 3. 오전 10:10:55 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 기본 지도 서비스 구현 입니다. 
///   -# 
/////////////////////////////////////////////
@Service("baseMapService")
public class BaseMapServiceImpl implements BaseMapService {
	
	/// 기본 지도 맵퍼
	@Resource
	KwsBaseMapMapper kwsBaseMapMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.baseMap.BaseMapService#listAllBaseMap(kr.co.gitt.kworks.dto.baseMap.BaseMapSearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsBaseMap> listAllBaseMap(BaseMapSearchDTO searchDTO) {
		return kwsBaseMapMapper.listAll(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.baseMap.BaseMapService#listAllBaseMap(kr.co.gitt.kworks.dto.baseMap.BaseMapSearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsBaseMap> listLayerYBaseMap(BaseMapSearchDTO searchDTO) {
		return kwsBaseMapMapper.listLayerY(searchDTO);
	}

	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.baseMap.BaseMapService#getBaseMapDescriptor(kr.co.gitt.kworks.dto.baseMap.BaseMapSearchDTO)
	/////////////////////////////////////////////
	@Override
	public String getBaseMapDescriptor(BaseMapSearchDTO baseMapSearchDTO) {
		List<KwsBaseMap> kwsBaseMaps = kwsBaseMapMapper.listAll(baseMapSearchDTO);
		
		Document document = new Document();
		Element element = createBaseMapDescriptor(kwsBaseMaps);
		document.setRootElement(element);
		XMLOutputter xmlOutputter = new XMLOutputter(); 
		Format format = xmlOutputter.getFormat();
		format.setEncoding("UTF-8");
		xmlOutputter.setFormat(format);
		return xmlOutputter.outputString(document);
	}
	
	/////////////////////////////////////////////
	/// @fn createBaseMapDescriptor
	/// @brief 함수 간략한 설명 : BaseMapDescriptor 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsBaseMaps
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Element createBaseMapDescriptor(List<KwsBaseMap> kwsBaseMaps) {
		Element element = new Element("BaseMapDescriptor");
		element.setAttribute("version", "1.0.0");
		
		for(KwsBaseMap kwsBaseMap : kwsBaseMaps) {
			element.addContent(createBaseMap(kwsBaseMap));
		}
		
		return element;
	}
	
	/////////////////////////////////////////////
	/// @fn createBaseMap
	/// @brief 함수 간략한 설명 : BaseMap 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsBaseMap
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Element createBaseMap(KwsBaseMap kwsBaseMap) {
		Element element = new Element("BaseMap");

		element.addContent(createTitle(kwsBaseMap.getTitle()));
		element.addContent(createDescription(kwsBaseMap.getDescription()));
		element.addContent(createTileService(kwsBaseMap));
		
		return element;
	}
	
	/////////////////////////////////////////////
	/// @fn createTitle
	/// @brief 함수 간략한 설명 : Title 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param title
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Element createTitle(String title) {
		Element element = new Element("Title");
		element.setText(title);
		return element;
	}
	
	/////////////////////////////////////////////
	/// @fn createDescription
	/// @brief 함수 간략한 설명 : Description 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param description
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Element createDescription(String description) {
		Element element = new Element("Description");
		element.setText(description);
		return element;
	}
	
	/////////////////////////////////////////////
	/// @fn createTileService
	/// @brief 함수 간략한 설명 : TileService 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsBaseMap
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Element createTileService(KwsBaseMap kwsBaseMap) {
		Element element = new Element("TileService");
		
		element.addContent(createSrs(kwsBaseMap.getSrs()));
		element.addContent(createOrigin(kwsBaseMap.getOriginX(), kwsBaseMap.getOriginY()));
		element.addContent(createMode(kwsBaseMap.getMapMode()));
		element.addContent(createImageFormat(kwsBaseMap.getExtension(), kwsBaseMap.getHeight(), kwsBaseMap.getWidth()));
		element.addContent(createHosts(kwsBaseMap.getKwsBaseMapHosts()));
		element.addContent(createRequestFormat(kwsBaseMap.getRequestFormat(), kwsBaseMap.getExtension()));
		element.addContent(createLevelSet(kwsBaseMap.getKwsBaseMapLevels()));
		
		return element;
	}

	/////////////////////////////////////////////
	/// @fn createSrs
	/// @brief 함수 간략한 설명 : Srs 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param srs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Element createSrs(String srs) {
		Element element = new Element("SRS");
		element.setText(srs);
		return element;
	}
	
	/////////////////////////////////////////////
	/// @fn createOrigin
	/// @brief 함수 간략한 설명 : Origin 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param originX
	/// @param originY
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Element createOrigin(Double originX, Double originY) {
		Element element = new Element("Origin");
		element.setAttribute("y", originY.toString());
		element.setAttribute("x", originX.toString());
		return element;
	}
	
	/////////////////////////////////////////////
	/// @fn createMode
	/// @brief 함수 간략한 설명 : Mode 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param mode
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Element createMode(String mode) {
		Element element = new Element("Mode");
		element.setText(mode.toLowerCase());
		return element;
	}
	
	/////////////////////////////////////////////
	/// @fn createImageFormat
	/// @brief 함수 간략한 설명 : ImageFormat 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param extension
	/// @param height
	/// @param width
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Element createImageFormat(String extension, Integer height, Integer width) {
		Element element = new Element("ImageFormat");
		element.setAttribute("extension", extension!=null?extension:"");
		element.setAttribute("height", height.toString());
		element.setAttribute("width", width.toString());
		return element;
	}
	
	/////////////////////////////////////////////
	/// @fn createHosts
	/// @brief 함수 간략한 설명 : Hosts 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsBaseMapHosts
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Element createHosts(List<KwsBaseMapHost> kwsBaseMapHosts) {
		Element element = new Element("Hosts");
		for(KwsBaseMapHost kwsBaseMapHost : kwsBaseMapHosts) {
			element.addContent(createHost(kwsBaseMapHost));
		}
		return element;
	}
	
	/////////////////////////////////////////////
	/// @fn createHost
	/// @brief 함수 간략한 설명 : Host 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsBaseMapHost
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Element createHost(KwsBaseMapHost kwsBaseMapHost) {
		Element element = new Element("Host");
		element.setAttribute("port", kwsBaseMapHost.getPort().toString());
		element.setText(kwsBaseMapHost.getUrl());
		return element;
	}
	
	/////////////////////////////////////////////
	/// @fn createRequestFormat
	/// @brief 함수 간략한 설명 : RequestFormat 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param requestFormat
	/// @param extension
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Element createRequestFormat(String requestFormat, String extension) {
		String format = requestFormat.replace("{z}", "{0}").replace("{x}", "{1}").replace("{y}", "{2}");
		if(StringUtils.isNotBlank(extension)) {
			format = format.replace("{e}", "{3}");
		}
		
		Element element = new Element("RequestFormat");
		element.setText(format);
		return element;
	}
	
	/////////////////////////////////////////////
	/// @fn createLevelSet
	/// @brief 함수 간략한 설명 : LevelSet 생성 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsBaseMapLevels
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Element createLevelSet(List<KwsBaseMapLevel> kwsBaseMapLevels) {
		Element element = new Element("LevelSet");
		for(KwsBaseMapLevel kwsBaseMapLevel : kwsBaseMapLevels) {
			element.addContent(createLevel(kwsBaseMapLevel));
		}
		return element;
	}
	
	/////////////////////////////////////////////
	/// @fn createLevel
	/// @brief 함수 간략한 설명 : Level 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsBaseMapLevel
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Element createLevel(KwsBaseMapLevel kwsBaseMapLevel) {
		Element element = new Element("Level");
		element.setAttribute("resolution", kwsBaseMapLevel.getResolution().toString());
		element.setAttribute("number", kwsBaseMapLevel.getNum().toString());
		return element;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.baseMap.BaseMapService#modifyBcrnMap(kr.co.gitt.kworks.model.KwsBaseMap)
	/////////////////////////////////////////////
	@Override
	public Integer modifyBcrnMapBaseN(KwsBaseMap kwsBaseMap) {
		Integer rowCount = kwsBaseMapMapper.updateBcrnMapBaseN(kwsBaseMap);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.baseMap.BaseMapService#selectOneBaseMap(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public KwsBaseMap selectOneBaseMap(Long mapNo) {
		return kwsBaseMapMapper.selectOne(mapNo);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.baseMap.BaseMapService#modifyBaseMap(kr.co.gitt.kworks.dto.baseMap.BaseMapSearchDTO)
	/////////////////////////////////////////////
	@Override
	public Integer modifyBcrnMapInfo(KwsBaseMap kwsBaseMap) {
		
		if(kwsBaseMap.getBaseYn().equals("Y")){
			kwsBaseMapMapper.updateBcrnMapBaseN(kwsBaseMap);
		}
		
		Integer rowCount = kwsBaseMapMapper.updateBcrnMapInfo(kwsBaseMap);
		return rowCount;
	}
	
	
}
