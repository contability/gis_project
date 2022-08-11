package kr.co.gitt.kworks.service.edit;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import kr.co.gitt.kworks.cmmn.dto.EditHistoryResultDTO;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.cmmn.dto.SpatialType;
import kr.co.gitt.kworks.cmmn.dto.WfsTransactionDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterSpatialDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO.FilterType;
import kr.co.gitt.kworks.mappers.KwsEditHistMapper;
import kr.co.gitt.kworks.model.KwsEditHist;
import kr.co.gitt.kworks.model.KwsEditHist.EditDqlType;
import kr.co.gitt.kworks.service.spatial.SpatialSearchService;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;



import com.kmaps.framework.common.Coordinate;
import com.kmaps.framework.common.Coordinates;
import com.kmaps.framework.spatialdata.geometry.GeomFactory;
import com.kmaps.framework.spatialdata.geometry.IGeometry;
import com.kmaps.framework.spatialdata.gml.GML311Reader;
import com.kmaps.framework.spatialdata.gml.IGML;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class EditHistoryServiceImpl
/// kr.co.gitt.kworks.service.edit \n
///   ㄴ EditHistoryServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 21. 오후 3:33:39 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 편집 이력 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("editHistoryService")
public class EditHistoryServiceImpl implements EditHistoryService {
	
	/// 동 데이터 아이디
	private final String DONG_DATA_ID = "BML_BADM_AS";
	
	/// 동 코드 프로퍼티
	private final String DONG_CODE_PROPERTY = "bjdCde";
	
	/// 메세지 소스
	@Resource(name="messageSource")
    private MessageSource messageSource;

	/// 편집 이력 맵퍼
	@Resource
	KwsEditHistMapper kwsEditHistMapper;
	
	/// 편집 이력 ID 생성 서비스
	@Resource
	EgovIdGnrService kwsEditHistIdGnrService;
	
	/// 공간 검색 서비스
	@Resource
	SpatialSearchService spatialSearchService;
	
	/////////////////////////////////////////////
	/// @fn listEditHistory
	/// @brief 함수 간략한 설명 : 편집 이력 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private List<EditHistoryResultDTO> listEditHistory(SearchDTO searchDTO) {
		return kwsEditHistMapper.list(searchDTO);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.edit.EditHistoryService#listEditHistory(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<EditHistoryResultDTO> listEditHistory(SearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = kwsEditHistMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			return listEditHistory(searchDTO);
		}
		else {
			return new ArrayList<EditHistoryResultDTO>();
		}
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.edit.EditHistoryService#addEditHistory(kr.co.gitt.kworks.model.KwsEditHist)
	/////////////////////////////////////////////
	@Override
	public Integer addEditHistory(KwsEditHist kwsEditHist) throws FdlException {
		kwsEditHist.setHistNo(kwsEditHistIdGnrService.getNextLongId());
		return kwsEditHistMapper.insert(kwsEditHist);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.edit.EditHistoryService#addEditHistories(kr.co.gitt.kworks.cmmn.dto.WfsTransactionDTO, java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	public Integer addEditHistories(WfsTransactionDTO wfsTransactionDTO, String userNm, String resultString, String responseString) throws Exception {
		Integer rowCount = 0;
		for(KwsEditHist kwsEditHist : parseRequest(resultString)) {
			kwsEditHist.setSysId(wfsTransactionDTO.getSysId());
			kwsEditHist.setEditType(wfsTransactionDTO.getEditType());
			kwsEditHist.setEditorId(wfsTransactionDTO.getUserId());
			kwsEditHist.setEditorNm(userNm);
			
			rowCount += addEditHistory(kwsEditHist);
		}
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn parseRequest
	/// @brief 함수 간략한 설명 : 요청 정보 파싱
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param content
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	private List<KwsEditHist> parseRequest(String content) throws Exception {
		XMLOutputter xmlOutputter = new XMLOutputter();
		
		List<KwsEditHist> kwsEditHists = new ArrayList<KwsEditHist>();
		
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader(content));
		Element root = document.getRootElement();
		
		List<Element> elements =  root.getChildren();
		for(Element element : elements) {
			KwsEditHist kwsEditHist = null;
			String name = element.getName();
			if(name.equalsIgnoreCase("Insert")) {
				kwsEditHist = parseInsert(element);
			}
			else if(name.equalsIgnoreCase("Update")) {
				kwsEditHist = parseUpdate(element);
			}
			else if(name.equalsIgnoreCase("Delete")) {
				kwsEditHist = parseDelete(element);
			}
			kwsEditHist.setEditCn(xmlOutputter.outputString(element));
			kwsEditHists.add(kwsEditHist);
		}
		return kwsEditHists;
	}
	
	/////////////////////////////////////////////
	/// @fn parseInsert
	/// @brief 함수 간략한 설명 : 등록 파싱
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param element
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	private KwsEditHist parseInsert(Element element) throws Exception {
		XMLOutputter xmlOutputter = new XMLOutputter();
		
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		KwsEditHist kwsEditHist = new KwsEditHist();
		if(element.getChildren().size() > 0) {
			Element layerElement = (Element) element.getChildren().get(0);
			kwsEditHist.setDataId(layerElement.getName());
			kwsEditHist.setEditDqlType(EditDqlType.INSERT);
			
			List<Element> columnElements = layerElement.getChildren();
			for(Element columnElement : columnElements) {
				if(StringUtils.equalsIgnoreCase(geometryName, columnElement.getName())) {
					if(columnElement.getChildren().size() > 0) {
						Element geometryElement = (Element) columnElement.getChildren().get(0);
						String geometryContent = xmlOutputter.outputString(geometryElement);
						
						String bjdCde = getBjdCde(geometryContent);
						kwsEditHist.setBjdCde(bjdCde);
					}
				}
			}
		}
		return kwsEditHist;
	}
	
	/////////////////////////////////////////////
	/// @fn parseUpdate
	/// @brief 함수 간략한 설명 : 수정 파싱
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param element
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	private KwsEditHist parseUpdate(Element element) throws Exception {
		XMLOutputter xmlOutputter = new XMLOutputter();
		
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		
		KwsEditHist kwsEditHist = new KwsEditHist();
		String typeName = element.getAttribute("typeName").getValue();
		String[] splitTypeName = typeName.split(":");
		if(splitTypeName.length == 2) {
			kwsEditHist.setDataId(splitTypeName[1]);
		}
		kwsEditHist.setEditDqlType(EditDqlType.UPDATE);
		
		List<Element> propertyElements = element.getChildren();
		for(Element propertyElement : propertyElements) {
			if(StringUtils.equalsIgnoreCase(propertyElement.getName(), "Property")) {
				
				List<Element> propertyContents = propertyElement.getChildren();
				boolean isGeometry = false;
				for(Element propertyContent : propertyContents) {
					if(StringUtils.equalsIgnoreCase(propertyContent.getName(), "Name")) {
						if(StringUtils.equalsIgnoreCase(geometryName, propertyContent.getValue())) {
							isGeometry = true; 
						}
					}
					else if(isGeometry && StringUtils.equalsIgnoreCase(propertyContent.getName(), "Value")) {
						if(propertyContent.getChildren().size() > 0) {
							Element geometryElement = (Element) propertyContent.getChildren().get(0);
							String geometryContent = xmlOutputter.outputString(geometryElement);
							
							String bjdCde = getBjdCde(geometryContent);
							kwsEditHist.setBjdCde(bjdCde);
						}
						break;
					}
				}
			}
		}
		return kwsEditHist;
	}
	
	/////////////////////////////////////////////
	/// @fn parseDelete
	/// @brief 함수 간략한 설명 : 삭제 파싱
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param element
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private KwsEditHist parseDelete(Element element) {
		KwsEditHist kwsEditHist = new KwsEditHist();
		String typeName = element.getAttribute("typeName").getValue();
		String[] splitTypeName = typeName.split(":");
		if(splitTypeName.length == 2) {
			kwsEditHist.setDataId(splitTypeName[1]);
		}
		kwsEditHist.setEditDqlType(EditDqlType.DELETE);
			
		return kwsEditHist;
	}
	
	/////////////////////////////////////////////
	/// @fn getBjdCde
	/// @brief 함수 간략한 설명 : 법정동 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param content
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String getBjdCde(String content) throws Exception {
		String bjdCde = null;
		Coordinate coordinate = getFirstCoordinate(content);
		if(coordinate != null) {
			SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
			spatialSearchDTO.setIsOriginColumnValue(true);
			spatialSearchDTO.setFilterType(FilterType.SPATIAL);
			
			IGeometry point = GeomFactory.createPoint(coordinate);
			
			FilterSpatialDTO filterSpatialDTO = new FilterSpatialDTO();
			filterSpatialDTO.setSpatialType(SpatialType.INTERSECTS);
			filterSpatialDTO.setWkt(point.toWKT());
			spatialSearchDTO.setFilterSpatialDTO(filterSpatialDTO);
			
			List<EgovMap> list = spatialSearchService.listAll(DONG_DATA_ID, spatialSearchDTO);
			if(list.size() > 0) {
				if(list.get(0).containsKey(DONG_CODE_PROPERTY)) {
					bjdCde = (String) list.get(0).get(DONG_CODE_PROPERTY);
				}
			}
		
		}
		return bjdCde;
	}
	
	/////////////////////////////////////////////
	/// @fn getFirstCoordinate
	/// @brief 함수 간략한 설명 : 첫 좌표 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param content
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Coordinate getFirstCoordinate(String content) throws Exception {
		Coordinate coordinate = null;
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
		org.w3c.dom.Document geometryDocument = documentBuilder.parse(new InputSource(new StringReader(content)));
		org.w3c.dom.Element geomElement = geometryDocument.getDocumentElement();
		
		GML311Reader gmlReader = new GML311Reader(IGML.v311);
		IGeometry geometry = gmlReader.read(geomElement);
		Coordinates coordinates = geometry.getCoordinates();
		if(coordinates.size() > 0) {
			coordinate = coordinates.get(0);
		}
		return coordinate;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.edit.EditHistoryService#listEditHistoryExcel(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<EditHistoryResultDTO> listEditHistoryExcel(SearchDTO searchDTO) {
		return kwsEditHistMapper.listEditHistoryExcel(searchDTO);
	}

}
