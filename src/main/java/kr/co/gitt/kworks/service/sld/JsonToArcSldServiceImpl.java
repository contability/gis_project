package kr.co.gitt.kworks.service.sld;

import java.io.IOException;
import java.util.Locale;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import kr.co.gitt.kworks.dto.sld.KwsFilterVO;
import kr.co.gitt.kworks.dto.sld.KwsLineSymbolVO;
import kr.co.gitt.kworks.dto.sld.KwsMarkVO;
import kr.co.gitt.kworks.dto.sld.KwsNamedLayersVO;
import kr.co.gitt.kworks.dto.sld.KwsPointSymbolVO;
import kr.co.gitt.kworks.dto.sld.KwsPolygonSymbolVO;
import kr.co.gitt.kworks.dto.sld.KwsRulesVO;
import kr.co.gitt.kworks.dto.sld.KwsSldRootVO;
import kr.co.gitt.kworks.dto.sld.KwsStrokeVO;
import kr.co.gitt.kworks.dto.sld.KwsTextSymbolVO;
import kr.co.gitt.kworks.dto.sld.json.KworksSld;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service("jsonToArcSldService")
public class JsonToArcSldServiceImpl implements JsonToSldService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	public String jsonToSld(String jsonStr){
		
		KwsSldRootVO vo = new KwsSldRootVO();
		String sld = "";
		
		try {
			vo = setRoot(jsonStr);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		try {
			sld = voToSld(vo);
		} catch (JAXBException e) {
			logger.error(e.getMessage());
		}
		return sld;
	}
	
	private KwsSldRootVO setRoot(String jsonStr) throws JsonProcessingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		KwsSldRootVO vo = mapper.readValue(jsonStr, KwsSldRootVO.class);
		return vo;
	}
	

	private String voToSld(KwsSldRootVO vo) throws JAXBException{
		String sld = createSld(vo);
		return sld;
	}
	
	
	///////////////////////////////////////////////////
	/////////////////// 상수 정의  ////////////////////////
	///////////////////////////////////////////////////
	
	//sld 태그 시작
	private String sldSt	= "<sld:";
	//sld 태그 끝
	private String sldEd	= "</sld:";
	
	//ogc 태그 시작
	private String ogcSt	= "<ogc:";
	//ogc 태그 끝
	private String ogcEd	= "</ogc:";
	
	//se:svgparameter 태그 시작
	private String sldCssSt	= "<sld:CssParameter";
	//se:svgparameter 태그 끝
	private String sldCssEd	= "</sld:CssParameter>";
		
	//Stroke 태그
	private String cssStroke			= sldCssSt + " name=\"stroke\">";
	//stroke-opacity 태그
	private String cssStrokeOpacity		= sldCssSt + " name=\"stroke-opacity\">";
	//stroke-width 태그
	private String cssStrokeWidth		= sldCssSt + " name=\"stroke-width\">";
	//stroke-Linejoin 태그
	private String cssStrokeLinejoin	= sldCssSt + " name=\"stroke-linejoin\">";
	//stroke-Linejoin 태그
	private String cssStrokeLinecap		= sldCssSt + " name=\"stroke-linecap\">";
	//stroke-Linejoin 태그
	private String cssStrokeDasharray	= sldCssSt + " name=\"stroke-dasharray\">";
	//stroke-Linejoin 태그
	private String cssStrokeDashoffset	= sldCssSt + " name=\"stroke-dashoffset\">";
	
	//fill 태그
	private String cssFill				= sldCssSt + " name=\"fill\">";
	//fill-opacity 태그
	private String cssFillOpacity		= sldCssSt + " name=\"fill-opacity\">";
	
	//sld header 부분
	private String setHeader(){
		String header		= "";
		header 				+= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"; 
		header 				+= "<sld:StyledLayerDescriptor";
		header 				+= " version=\"1.0.0\"";
		header 				+= " xmlns=\"http://www.opengis.net/ogc\"";
		header 				+= " xmlns:sld=\"http://www.opengis.net/sld\"";
		header 				+= " xmlns:ogc=\"http://www.opengis.net/ogc\"";
		header 				+= " xmlns:gml=\"http://www.opengis.net/gml\"";
		header 				+= " xmlns:xlink=\"http://www.w3.org/1999/xlink\"";
		header 				+= " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";
		header 				+= " xsi:schemaLocation=\"http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd\">";
		return header;
	}
	
	//자주사용되는 name태그와 title태그를 함수로 구현
	private String nameTitleToString(String name, String title){
		String sld	 		= "";
		
		//name 태그
		sld					+= nameToString(name);
		
		//title 태그
		sld					+= titleToString(title);
		
		return sld;
	}
	
	//자주사용되는 name태그를 함수로 구현
	private String nameToString(String name){
		String sld	 		= "";
		
		//name 태그
		if(!name.equals("")){
			sld				+= sldSt + "Name>";
			sld				+= name;
			sld				+= sldEd + "Name>";
		}else{
			sld				+= sldSt + "Name/>";
		}
				
		return sld;
	}
	
	//자주사용되는 title태그를 함수로 구현
	private String titleToString(String title){
		String sld	 		= "";
		
		//title 태그
		if(!title.equals("")){
			sld				+= sldSt + "Title>";
			sld				+= title;
			sld				+= sldEd + "Title>";
		}else{
			sld				+= sldSt + "Title/>";
		}
		
		return sld;
	}
	
	//sld 만들기
	private String createSld(KwsSldRootVO vo){
		logger.debug("KwsSldRootVO vo : " + vo);
		String sld	 		= "";
		//sld Header 부분생성
		sld					+= setHeader();
		
		String name			 = vo.getName() == null ? "" : vo.getName(); 
		String title		 = vo.getTitle() == null ? "" : vo.getTitle();
		
		//name태그 와 title태그 
		sld 				+= nameTitleToString(name,title);
		//NamedLayer배열을 태그로 변환
		sld					+= createNamedLayers(vo.getNamedLayers());
		
		//sld Footer 부분 생성
		sld					+= sldEd + "StyledLayerDescriptor>";
		return sld;
	}
	
	//NamedLayers 태그 생성하기(namedLayersVO 배열을 처리함 )
	private String createNamedLayers(KwsNamedLayersVO[] vo){
		String sld			= "";
		
		int cnt 			= vo.length;
		for(int i=0; i < cnt; i++){
			KwsNamedLayersVO tmpVO = vo[i];
			//NamedLayer 태그 삽입
			sld 			+= namedLayersVOToString(tmpVO);
		}
		
		return sld;
	}
	
	//namedLayersVO 값을 태그로 변환
	private String namedLayersVOToString(KwsNamedLayersVO vo){
		String sld	 		= "";
		
		//표현여부를 먼저 체크해서 처리
		String visible		= vo.getName() == null ? "" : vo.getVisible().toString();
		if(!visible.equals("true")){
			return "";
		}
		
		String name 			= vo.getName() == null ? "" : vo.getName().toString(); 
		String title 			= vo.getTitle() == null ? "" : vo.getTitle().toString();
		KwsRulesVO[] rVOArr 	= vo.getRules();
		KwsTextSymbolVO textVo	= vo.getText();
		
		//NamedLayer 태그 시작
		sld					+= sldSt + "NamedLayer>";
		
		//name태그 삽입
		sld 				+= nameToString(name);
		
		//UserStyle 태그 삽입
		sld					+= createUserStyle(name, title, rVOArr, textVo);
		
		//NamedLayer 태그 끝
		sld					+= sldEd + "NamedLayer>";
		return sld;
	}
	
	//UserStyle 태그 생성
	private String createUserStyle(String name, String title, KwsRulesVO[] voArr, KwsTextSymbolVO textVo){
		String sld			= "";
		
		//UserStyle 태그 시작
		sld					+= sldSt + "UserStyle>";
		
		//name태그 와 title태그 
		sld 				+= nameTitleToString(name,title);
		
		//UserStyle 태그 삽입
		sld 				+= createFeatureTypeStyle(name, title, voArr, textVo);
		
		//UserStyle 태그 끝
		sld					+= sldEd + "UserStyle>";
		return sld;
	}

	//FeatureTypeStyle 태그 생성
	private String createFeatureTypeStyle(String name, String title, KwsRulesVO[] voArr, KwsTextSymbolVO textVo){
		String sld			= "";
		
		//FeatureTypeStyle 태그 시작
		sld					+= sldSt + "FeatureTypeStyle>";
	
		//name태그 와 title태그 
		sld 				+= nameTitleToString(name,title);
		
		//Rules 태그 삽입
		sld					+= createRule(voArr, textVo);
				
		//FeatureTypeStyle 태그 끝
		sld					+= sldEd + "FeatureTypeStyle>";
		return sld;
	}
	
	//Rules 태그 생성
	private String createRule(KwsRulesVO[] voArr, KwsTextSymbolVO textVo){
		String sld			= "";
		
		int cnt				= voArr.length;
		for(int i=0; i < cnt; i++){
			KwsRulesVO vo	= voArr[i];
			//Rules 태그 삽입
			sld				+= kwsRulesVOToString(vo, textVo); 
		}
		
		return sld;
	}
	
	//Rules 태그 삽입
	private String kwsRulesVOToString(KwsRulesVO vo, KwsTextSymbolVO text){
		String sld			= "";
		
		//표현여부를 먼저 체크해서 처리
		String visible		= vo.getVisible() == null ? "" : vo.getVisible().toString();
		if(!visible.equals("true")){
			return "";
		}
		
		String name 		= vo.getName() == null ? "" : vo.getName().toString(); 
		String title 		= vo.getTitle() == null ? "" : vo.getTitle().toString();
		String MaxScale		= vo.getMaxScale() == null ? "" : vo.getMaxScale();
		String MinScale		= vo.getMinScale() == null ? "" : vo.getMinScale();
		KwsFilterVO filterVO = vo.getFilter();
		
		KwsPolygonSymbolVO polygon	= vo.getPolygon();
		KwsLineSymbolVO line		= vo.getLine();
		KwsPointSymbolVO point		= vo.getPoint();
		
		sld					+= sldSt + "Rule>";
		//name태그 와 title태그 
		sld 				+= nameTitleToString(name,title);
		if(filterVO != null){
			sld 				+= createFilter(filterVO);
		}
		
		sld					+= createMaxScale(MaxScale);
		sld					+= createMinScale(MinScale);
		
		if(polygon != null){
			sld					+= createPolygonSymbolizer(polygon);
		}
		
		if(line != null){
			sld					+= createLineSymbolizer(line);
		}
		
		if(point != null){
			sld					+= createPointSymbolizer(point);
		}
		
		if(text != null){
			sld					+= createTextSymbolizer(text);
		}
		
		sld 				+= sldEd + "Rule>";
		
		return sld;
	}	
	
	//MaxScaleDenominator 태그 삽입
	private String createMaxScale(String scale){
		String sld			= "";
		if(scale.equals("")){
			return sld;
		}
		
		sld					+= sldSt + "MaxScaleDenominator>";
		sld					+= scale;
		sld					+= sldEd + "MaxScaleDenominator>";
		return sld;
	}
	
	//MinScaleDenominator 태그 삽입
	private String createMinScale(String scale){
		String sld			= "";
		if(scale.equals("")){
			return sld;
		}
		
		sld					+= sldSt + "MinScaleDenominator>";
		sld					+= scale;
		sld					+= sldEd + "MinScaleDenominator>";
		return sld;
	}
	
	//Filter태그 생성
	private String createFilter(KwsFilterVO vo){
		String sld			= "";
		
		String type			= vo.getType() == null ? "" : vo.getType();
		String literal		= vo.getLiteral() == null ? "" : vo.getLiteral();
		String property		= vo.getProperty() == null ? "" : vo.getProperty();
		
		sld					+= ogcSt + "Filter>";
		if(type.equals("==")){
			sld 			+= ogcSt + "PropertyIsEqualTo>";
			sld 			+= createFilterProperty(property);
			sld 			+= createFilterLiteral(literal);
		}else if(type.equals(">=")){
			sld 			+= ogcSt + "PropertyIsGreaterThanOrEqualTo>";
			sld 			+= createFilterProperty(property);
			sld 			+= createFilterLiteral(literal);
		}else if(type.equals("<")){
			sld 			+= ogcSt + "PropertyIsLessThan>";
			sld 			+= createFilterProperty(property);
			sld 			+= createFilterLiteral(literal);
		}else if(type.equals("..")){
				sld 			+= ogcSt + "PropertyIsBetween>";
				sld 			+= createFilterProperty(property);
				sld 			+= createFilterLiteral(literal);
		}else{
			sld 			+= ogcSt + "PropertyName>";
			sld 			+= property;
		}
		
		
		if(type.equals("==")){
			sld 			+= ogcEd + "PropertyIsEqualTo>";
		}else if(type.equals(">=")){
			sld 			+= ogcEd + "PropertyIsGreaterThanOrEqualTo>";
		}else if(type.equals("<")){
			sld 			+= ogcEd + "PropertyIsLessThan>";
		}else if(type.equals("..")){
				sld 			+= ogcEd + "PropertyIsBetween>";
		}else{
			sld 			+= ogcEd + "PropertyName>";
		}
		
		sld 				+= ogcEd + "Filter>";
		return sld;
	}
	
	//Filter Property
	private String createFilterProperty(String property){
		String sld			= "";
		
		//Property
		sld 				+= ogcSt + "PropertyName>";
		sld					+= property;
		sld 				+= ogcEd + "PropertyName>";
		
		return sld;
	}
	
	//Filter Literal
	private String createFilterLiteral(String literal){
		String sld			= "";
		
		//Literal
		sld 				+= ogcSt + "Literal>";
		sld 				+= literal;
		sld 				+= ogcEd + "Literal>";
		
		return sld;
	}	
	
	//LineSymbolizer 태그 생성
	private String createLineSymbolizer(KwsLineSymbolVO vo){
		String sld			= "";
		
		sld 				+= sldSt + "LineSymbolizer>";
		
		KwsStrokeVO strokeVO = new KwsStrokeVO();
		strokeVO.setStrokeDasharray(vo.getStrokeDasharray());
		strokeVO.setStrokeDashoffset(vo.getStrokeDashoffset());
		strokeVO.setStroke(vo.getStroke());
		strokeVO.setStrokeOpacity(vo.getStrokeOpacity());
		strokeVO.setStrokeWidth(vo.getStrokeWidth());
		
		sld 				+= createStroke(strokeVO);
				
		sld 				+= sldEd + "LineSymbolizer>";
		
		return sld;
	}	

	//Storke 태그 생성
	private String createStroke(KwsStrokeVO vo){
		String sld			= "";
		
		//stroke null 일때 기본값 #000000 설정
		String stroke		= vo.getStroke()		== null ? "#000000" : vo.getStroke();
		//opacity null 일때 기본값 1 설정
		String opacity		= vo.getStrokeOpacity()	== null ? "1"		: vo.getStrokeOpacity();
		//width null 일때 기본값 2 설정
		String width		= vo.getStrokeWidth()	== null ? ""		: vo.getStrokeWidth();
		//dasharray
		String dasharray	= vo.getStrokeDasharray() == null ? ""		: vo.getStrokeDasharray();
		//dasharray
		String dashoffset	= vo.getStrokeDashoffset() == null ? "0.0"	: vo.getStrokeDashoffset();
		
		// width 값이 없으면 stroke를 설정하지 않음.
		if((width.equals("") || width.equals("0"))){
			return sld;
		}
		
		sld					+= sldSt + "Stroke>";
		//stroke 태그 삽입
		sld					+= cssStroke + stroke + sldCssEd;
		//stroke-opacity 태그 삽입
		sld					+= cssStrokeOpacity + opacity + sldCssEd;
		
		//stroke-width 태그 삽입
		sld					+= cssStrokeWidth + width + sldCssEd;
		
		//stroke-linejoin
		sld					+= cssStrokeLinejoin + "mitre" + sldCssEd;
		//stroke-linecap
		sld					+= cssStrokeLinecap + "butt" + sldCssEd;
		
		if(!dasharray.equals("")){
			//svgStrokeDasharray
			if(dasharray.equals("dot")){
				dasharray = "0 100 100";
			}else if(dasharray.equals("dash")){
				dasharray = "0 400 200";
			}else if(dasharray.equals("dashDot")){
				dasharray = "0 1000 400 200 180 20 200 180 20";
			}else if(dasharray.equals("dashDotDot")){
				dasharray = "0 1000 600 200 180 20 200 180 20 200 180 20";
			}
			
			if(!dasharray.equals("solid") && !dasharray.equals("0.0")){
				sld					+= cssStrokeDasharray + dasharray + sldCssEd;
				sld					+= cssStrokeDashoffset + dashoffset + sldCssEd;	
			}
			
		}
		
		sld					+= sldEd + "Stroke>";
		return sld;
	}	
	
	//PolygonSymbolizer 태그 생성
	private String createPolygonSymbolizer(KwsPolygonSymbolVO vo){
		String sld				= "";
		String daahArray		= vo.getStrokeDasharray() == null ? "0.0" : vo.getStrokeDasharray();
		String daahOffset		= vo.getStrokeDashoffset() == null ? "0" : vo.getStrokeDashoffset();
		String stroke			= vo.getStroke() == null ? "#000000" : vo.getStroke();
		String strokeOpacity	= vo.getStrokeOpacity()	== null ? "1"		: vo.getStrokeOpacity();
		String width			= vo.getStrokeWidth()	== null ? ""		: vo.getStrokeWidth();
		
		sld 				+= sldSt + "PolygonSymbolizer>";
		
		sld 				+= createFill(vo);
		
		KwsStrokeVO strokeVO = new KwsStrokeVO();
		strokeVO.setStrokeDasharray(daahArray);
		strokeVO.setStrokeDashoffset(daahOffset);
		strokeVO.setStroke(stroke);
		strokeVO.setStrokeOpacity(strokeOpacity);
		strokeVO.setStrokeWidth(width);
		
		sld 				+= createStroke(strokeVO);
		
		sld 				+= sldEd + "PolygonSymbolizer>";
		return sld;
	}
	
	//Fill 태그 생성(KwsPolygonSymbolVO)
	private String createFill(KwsPolygonSymbolVO vo){
		String sld			= "";
		String fill			= vo.getFill()			== null ? "" : vo.getFill().toString();
		String opacity		= vo.getFillOpacity()	== null ? "1.0" 	: vo.getFillOpacity().toString();
		
		if(fill.equals("")){
			return sld;
		}
		
		//fill 태그 삽입
		sld					+= sldSt + "Fill>";
		//fill 태그 삽입
		sld					+= cssFill + fill + sldCssEd;
		//fill-opacity 태그 삽입
		sld					+= cssFillOpacity + opacity + sldCssEd;
		sld					+= sldEd + "Fill>";
		
		return sld;
	}	
	
	//PointSymbolizer 태그생성
	private String createPointSymbolizer(KwsPointSymbolVO vo){
		String sld			= "";
		
		sld 				+= sldSt + "PointSymbolizer>";
		
		//Graphic 태그 삽입
		sld					+= createGraphic(vo);
		
		sld 				+= sldEd + "PointSymbolizer>";
		
		return sld;
	}	
	
	//TextSymbolizer 태그 생성
	private String createTextSymbolizer(KwsTextSymbolVO vo){
		String sld			= "";
		String visible		= vo.getVisible() == null ? "false" : vo.getVisible();
		String maxScale		= vo.getMaxScale() == null ? "" : vo.getMaxScale();
		String minScale		= vo.getMinScale() == null ? "" : vo.getMinScale();
		
		if(!visible.equals("true")){
			return sld;
		}
		
		sld 				+= sldSt + "TextSymbolizer>";
		sld 				+= createLabel(vo);
		sld 				+= createFont(vo);
		sld 				+= createLabelPlacement(vo);
		sld 				+= createHalo(vo);
		sld 				+= createTextFill(vo);
		
		if(!maxScale.equals("")){
			createMaxScale(maxScale);
		}
		
		if(!minScale.equals("")){
			createMinScale(minScale);
		}
		
		sld 				+= sldEd + "TextSymbolizer>";
		return sld;
	}
	
	//Label 태그 생성
	private String createLabel(KwsTextSymbolVO vo){
		String sld			= "";
		
		sld					+= sldSt + "Label>";
		sld					+= ogcSt + "PropertyName>";
		sld					+= vo.getLabel() == null ? "" : vo.getLabel();
		sld					+= ogcEd + "PropertyName>";
		sld					+= sldEd + "Label>";
		
		return sld;
	}	

	//LabelPlacement 태그 생성
	private String createLabelPlacement(KwsTextSymbolVO vo){
		String sld			= "";

		String anchor 		= vo.getAnchor() == null ? "cm" : vo.getAnchor();
		String displaceX	= vo.getDisplacementX() == null ? "0.0" : vo.getDisplacementX();
		String displaceY	= vo.getDisplacementY() == null ? "0.0" : vo.getDisplacementY();
		
		sld					+= sldSt + "LabelPlacement>"; 
		
		//Point
		sld					+= sldSt + "PointPlacement>";
		sld					+= createAnchorPoint(anchor);

		//Point/Displacement
		sld					+= sldSt + "Displacement>"; 
		//Point/Displacement/DisplacementX
		sld					+= sldSt + "DisplacementX>"; 
		sld					+= displaceX; 
		sld					+= sldEd + "DisplacementX>"; 
		//Point/Displacement/DisplacementY
		sld					+= sldSt + "DisplacementY>"; 
		sld					+= displaceY; 
		sld					+= sldEd + "DisplacementY>"; 
		sld					+= sldEd + "Displacement>"; 
		
		sld					+= sldEd + "PointPlacement>";
		
		sld					+= sldEd + "LabelPlacement>";
		
		return sld;
	}

	//AnchorPoint 태그 생성
	private String createAnchorPoint(String anchor){
		String sld			= "";
		
//		String anchor 		= vo.getAnchor() == null ? "cm" : vo.getAnchor();
		String anchorX		= anchor.substring(0,1);
		String anchorY		= anchor.substring(1,2);
		
		if(anchorX.equals("r")){
			anchorX = "0.0";
		}else if(anchorX.equals("c")){
			anchorX = "0.5";
		}else if(anchorX.equals("l")){
			anchorX = "1.0";
		}
		
		if(anchorY.equals("t")){
			anchorY = "0.0";
		}else if(anchorY.equals("m")){
			anchorY = "0.5";
		}else if(anchorY.equals("b")){
			anchorY = "1.0";
		}
		
		//Point/AnchorPoint
		sld					+= sldSt + "AnchorPoint>";
		//Point/AnchorPoint/AnchorPointxX
		sld					+= sldSt + "AnchorPointX>"; 
		sld					+= anchorX; 
		sld					+= sldEd + "AnchorPointX>";
		//Point/AnchorPoint/AnchorPointxY
		sld					+= sldSt + "AnchorPointY>"; 
		sld					+= anchorY; 
		sld					+= sldEd + "AnchorPointY>";
		sld					+= sldEd + "AnchorPoint>";
		
		return sld;
	}
	
	//Font 태그 생성
	private String createFont(KwsTextSymbolVO vo){
		String sld			= "";
		
		sld					+= sldSt + "Font>";
		//font-family 태그 삽입
		sld					+= sldCssSt + " name=\"font-family\">";
		sld					+= vo.getFontFamily() == null ? "굴림" : vo.getFontFamily();
		sld					+= sldCssEd;
		//font-style 태그 삽입
		sld					+= sldCssSt + " name=\"font-style\">";
		sld					+= vo.getFontStyle() == null ? "normal" : vo.getFontStyle();
		sld					+= sldCssEd;
		//font-weight 태그 삽입
		sld					+= sldCssSt + " name=\"font-weight\">";
		sld					+= vo.getFontWeight() == null ? "normal" : vo.getFontWeight();
		sld					+= sldCssEd;
		//font-size 태그 삽입
		sld					+= sldCssSt + " name=\"font-size\">";
		sld					+= vo.getFontSize() == null ? "12" : vo.getFontSize();
		sld					+= sldCssEd;
		
		sld					+= sldEd + "Font>";
		
		return sld;
	}
	
	//Halo 태그 생성
	private String createHalo(KwsTextSymbolVO vo){
		String sld			= "";
		
		String fill			= vo.getHalo() == null ? "#ffffff" : vo.getHalo();
		String Opacity		= vo.getHaloOpacity() == null ? "1" : vo.getHaloOpacity();
		
		sld					+= sldSt + "Halo>";
		sld					+= sldSt + "Radius>";
		sld					+= vo.getHaloRadius() == null ? "2.0" : vo.getHaloRadius();
		sld					+= sldEd + "Radius>";
		
		sld					+= sldSt + "Fill>";
		sld					+= cssFill + fill + sldCssEd;
		sld					+= cssFillOpacity + Opacity + sldCssEd;
		sld					+= sldEd + "Fill>";
		sld					+= sldEd + "Halo>";
		
		return sld;
	}	

	//Fill 태그 생성(KwsTextSymbolVO)
	private String createTextFill(KwsTextSymbolVO vo){
		String sld			= "";
		String fill			= vo.getFill()			== null ? "#FFFFFF" : vo.getFill().toString();
		String opacity		= vo.getFillOpacity()	== null ? "0.5" 	: vo.getFillOpacity().toString();
		
		//fill 태그 삽입
		sld					+= sldSt + "Fill>";
		//fill 태그 삽입
		sld					+= cssFill + fill + sldCssEd;
		//fill-opacity 태그 삽입
		sld					+= cssFillOpacity + opacity + sldCssEd;
		sld					+= sldEd + "Fill>";
		
		return sld;
	}	
	
	//Graphic 태그삽입
	private String createGraphic(KwsPointSymbolVO vo){
		String sld			= "";
		//String anchor		= vo.getAnchor() == null ? "cm" : vo.getAnchor();
		String rotation		= vo.getRotation() == null ? "" : vo.getRotation();
		String opacity		= vo.getOpacity() == null ? "1.0" : vo.getOpacity();
		
		//Graphic 태그삽입
		sld					+= sldSt + "Graphic>";

		//Graphic타입에 따른 분류
		if(vo.getMark() == null){
			//Graphic 태그 삽입 (ExternalGraphic)
			sld					+= createExternalGraphic(vo);
		}else{
			//Graphic 태그 삽입  (Mark)
			sld					+= createMarkGraphic(vo.getMark());
			//Mark 가 있을때  rotation이 empty 이면 무조건 Rotation은 90으로 고정
			if(rotation.equals("")) rotation = "90";
		}
		
		//rotation 값이 empty 일때 기본값 0으로 설정 (위에서 mark에서 먼저 값을 넣을지 체크를 해야 해서 현위치에서 값을 새로 설정함.
		if(rotation.equals(""))rotation = "0";
		
		//Opacity 태그삽입
		sld					+= sldSt + "Opacity>";
		sld					+= opacity;
		sld					+= sldEd + "Opacity>";
		
		//Size 태그삽입
		sld					+= sldSt + "Size>";
		sld					+= vo.getSize() == null ? "15.0" : vo.getSize();
		sld					+= sldEd + "Size>";
		
		//Rotation 태그삽입
		sld					+= sldSt + "Rotation>";
		sld					+= rotation;
		sld					+= sldEd + "Rotation>";
		
		//AnchorPoint 태그삽입
//			sld					+= createAnchorPoint(anchor);
		
//		if(vo.getMark() == null){
//			//Displacement 태그삽입
//			sld					+= sldSt + "Displacement>";
//			//DisplacementX 태그삽입
//			sld					+= sldSt + "DisplacementX>";
//			sld					+= "0.0";
//			sld					+= sldEd + "DisplacementX>";
//			//DisplacementY 태그삽입
//			sld					+= sldSt + "DisplacementY>";
//			sld					+= "0.0";
//			sld					+= sldEd + "DisplacementY>";
//			
//			sld					+= sldEd + "Displacement>";
//		}
		
		sld					+= sldEd + "Graphic>";
		return sld;
	}

	
	//ExternalGraphic 태그 삽입
	private String createExternalGraphic(KwsPointSymbolVO vo){
		String sld			= "";
		//Map.Url.Symbol
		String symbolPath	= messageSource.getMessage("Map.Url.Symbol", null, Locale.getDefault());
		String resource		= vo.getResource() == null ? "" : vo.getResource();

		//ExternalGraphic 태그삽입
		sld					+= sldSt + "ExternalGraphic>";

		//Onlineresource 태그삽입
		sld					+= sldSt + "OnlineResource xlink:type=\"simple\" xlink:href=\"" + symbolPath + "/" + resource + "\">";
		sld					+= sldEd + "OnlineResource>";

		//Format 태그삽입
		sld					+= sldSt + "Format>";
//		sld					+= "image/" + extStr;
		sld					+= "image/png";
		sld					+= sldEd + "Format>";
		
		sld					+= sldEd + "ExternalGraphic>";
		
		return sld;
	}
	
	//Mark 태그 삽입
	private String createMarkGraphic(KwsMarkVO vo){
		String sld				= "";
		String fill				= vo.getFill() == null ? "#000000" : vo.getFill();
		String opacity			= vo.getFillOpacity() == null ? "" : vo.getFillOpacity();
		String stroke			= vo.getStroke() == null ? "#000000" : vo.getStroke();
		String strokeOpacity	= vo.getStrokeOpacity()	== null ? "1"		: vo.getStrokeOpacity();
//		String width			= vo.getStrokeWidth()	== null ? ""		: vo.getStrokeWidth();
		String width			= "1.0";
		
		//Mark 태그 삽입
		sld					+= sldSt + "Mark>";
		//WellKnownName 태그 삽입
		sld					+= sldSt + "WellKnownName>";
		sld					+= vo.getWellKnownName();
		sld					+= sldEd + "WellKnownName>";		
		//fill 태그 삽입
		sld					+= sldSt + "Fill>";
		//fill 태그 삽입
		sld					+= cssFill + fill + sldCssEd;
		//fill-opacity 태그 삽입
		sld					+= cssFillOpacity + opacity + sldCssEd;
		sld					+= sldEd + "Fill>";
		
		//stroke 태그 삽입
		sld					+= sldSt + "Stroke>";
		//svgStroke 태그 삽입
		sld					+= cssStroke + stroke + sldCssEd;
		//stroke-opacity 태그 삽입
		sld					+= cssStrokeOpacity + strokeOpacity + sldCssEd;
		
		//stroke-width 태그 삽입
		sld					+= cssStrokeWidth + width + sldCssEd;
		
		//stroke-linejoin
		sld					+= cssStrokeLinejoin + "mitre" + sldCssEd;
		//stroke-linecap
		sld					+= cssStrokeLinecap + "butt" + sldCssEd;
		sld					+= sldEd + "Stroke>";
		
		sld					+= sldEd + "Mark>";		
		return sld;
	}

	@Override
	public String jsonToSld(KworksSld kworksSld) {
		// TODO Auto-generated method stub
		return null;
	}	
}
