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
import kr.co.gitt.kworks.dto.sld.KwsVendorVO;
import kr.co.gitt.kworks.dto.sld.json.KworksSld;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service("jsonToSldService")
public class JsonToSldServiceImpl implements JsonToSldService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	public String jsonToSld(String jsonStr){

		KwsSldRootVO vo = new KwsSldRootVO();
		String sld = "";
		
		try {
			vo = setRoot(jsonStr);
		} catch (JsonProcessingException e) {
//			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
//			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		
		try {
			sld = voToSld(vo);
		} catch (JAXBException e) {
//			e.printStackTrace();
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
	/////////////////// ?????? ??????  ////////////////////////
	///////////////////////////////////////////////////
	
	//sld ?????? ??????
	private String sldSt	= "<sld:";
	//sld ?????? ???
	private String sldEd	= "</sld:";
	
	//se ?????? ??????
	private String seSt		= "<se:";
	//se ?????? ???
	private String seEd		= "</se:";
	
	//ogc ?????? ??????
	private String ogcSt	= "<ogc:";
	//ogc ?????? ???
	private String ogcEd	= "</ogc:";
	
	//se:svgparameter ?????? ??????
	private String seSvgSt	= "<se:SvgParameter";
	//se:svgparameter ?????? ???
	private String seSvgEd	= "</se:SvgParameter>";
	
	//Stroke ??????
	private String svgStroke			= seSvgSt + " name=\"stroke\">";
	//stroke-opacity ??????
	private String svgStrokeOpacity		= seSvgSt + " name=\"stroke-opacity\">";
	//stroke-width ??????
	private String svgStrokeWidth		= seSvgSt + " name=\"stroke-width\">";
	//stroke-Linejoin ??????
	private String svgStrokeLinejoin	= seSvgSt + " name=\"stroke-linejoin\">";
	//stroke-Linejoin ??????
	private String svgStrokeLinecap		= seSvgSt + " name=\"stroke-linecap\">";
	//stroke-Linejoin ??????
	private String svgStrokeDasharray	= seSvgSt + " name=\"stroke-dasharray\">";
	//stroke-Linejoin ??????
	private String svgStrokeDashoffset	= seSvgSt + " name=\"stroke-dashoffset\">";
	
	//fill ??????
	private String svgFill				= seSvgSt + " name=\"fill\">";
	//fill-opacity ??????
	private String svgFillOpacity		= seSvgSt + " name=\"fill-opacity\">";
	
	//sld header ??????
	private String setHeader(){
		String header		= "";
		header 				+= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"; 
		header 				+= "<sld:StyledLayerDescriptor";
		header 				+= " xsi:schemaLocation=\"http://www.opengis.net/sld/StyledLayerDescriptor.xsd\"";
		header 				+= " version=\"1.1.0\"";
		header 				+= " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";
		header 				+= " xmlns:xlink=\"http://www.w3.org/1999/xlink\"";
		header 				+= " xmlns:se=\"http://www.opengis.net/se\"";
		header 				+= " xmlns:ogc=\"http://www.opengis.net/ogc\"";
		header 				+= " xmlns:sld=\"http://www.opengis.net/sld\">";
		return header;
	}

	//?????????????????? name????????? title????????? ????????? ??????
	private String nameTitleToString(String name, String title){
		String sld	 		= "";
		
		//name ??????
		if(!name.equals("")){
			sld				+= seSt + "Name>";
			sld				+= name;
			sld				+= seEd + "Name>";
		}else{
			sld				+= seSt + "Name/>";
		}
		
		//title ??????
		if(!title.equals("")){
			sld				+= seSt + "Description>";
			sld				+= seSt + "Title>";
			sld				+= title;
			sld				+= seEd + "Title>";
			sld				+= seEd + "Description>";
		}else{
			sld				+= seSt + "Description>";
			sld				+= seSt + "Title/>";
			sld				+= seEd + "Description>";
		}
		
		return sld;
	}
	
	//sld ?????????
	private String createSld(KwsSldRootVO vo){
		String sld	 		= "";
		//sld Header ????????????
		sld					+= setHeader();
		
		String name			 = vo.getName() == null ? "" : vo.getName(); 
		String title		 = vo.getTitle() == null ? "" : vo.getTitle();
		
		//name?????? ??? title?????? 
		sld 				+= nameTitleToString(name,title);
		//NamedLayer????????? ????????? ??????
		sld					+= createNamedLayers(vo.getNamedLayers());
		
		//sld Footer ?????? ??????
		sld					+= sldEd + "StyledLayerDescriptor>";
		return sld;
	}
	
	//NamedLayers ?????? ????????????(namedLayersVO ????????? ????????? )
	private String createNamedLayers(KwsNamedLayersVO[] vo){
		String sld			= "";
		
		int cnt 			= vo.length;
		for(int i=0; i < cnt; i++){
			KwsNamedLayersVO tmpVO = vo[i];
			//NamedLayer ?????? ??????
			sld 			+= namedLayersVOToString(tmpVO);
		}
		
		return sld;
	}
	
	//namedLayersVO ?????? ????????? ??????
	private String namedLayersVOToString(KwsNamedLayersVO vo){
		String sld	 		= "";
		
		//??????????????? ?????? ???????????? ??????
		String visible		= vo.getName() == null ? "" : vo.getVisible().toString();
		if(!visible.equals("true")){
			return "";
		}
		
		String name 			= vo.getName() == null ? "" : vo.getName().toString(); 
		String title 			= vo.getTitle() == null ? "" : vo.getTitle().toString();
		String source 			= vo.getSource() == null ? name : vo.getSource().toString();
		KwsRulesVO[] rVOArr 	= vo.getRules();
		KwsTextSymbolVO textVo	= vo.getText();
		
		//NamedLayer ?????? ??????
		sld					+= sldSt + "NamedLayer>";
		
		//name?????? ??? title?????? ??????
		sld 				+= nameTitleToString(name,title);
		//LayerFeatureConstraints ?????? ??????
//		sld 				+= LayerFeatureConstraintsToString(name);
		sld 				+= LayerFeatureConstraintsToString(source);
		//UserStyle ?????? ??????
//		sld					+= createUserStyle(name, title, rVOArr, textVo);
		sld					+= createUserStyle(name, title, source, rVOArr, textVo);
		
		//NamedLayer ?????? ???
		sld					+= sldEd + "NamedLayer>";
		return sld;
	}
	
	//LayerFeatureConstraints ?????? ??????
	private String LayerFeatureConstraintsToString(String name){
		String sld			= "";
		String mapPrefix = messageSource.getMessage("Map.Prefix", null, Locale.getDefault());
		
		sld					+= sldSt + "LayerFeatureConstraints>";
		sld					+= sldSt + "FeatureTypeConstraint>";
		sld					+= seSt + "FeatureTypeName>";
		sld					+= mapPrefix + ":" +  name;
		sld					+= seEd + "FeatureTypeName>";
		sld					+= sldEd + "FeatureTypeConstraint>";
		sld					+= sldEd + "LayerFeatureConstraints>";
		
		return sld;
	}
	
	//UserStyle ?????? ??????
	private String createUserStyle(String name, String title, String source, KwsRulesVO[] voArr, KwsTextSymbolVO textVo){
		String sld			= "";
		
		//UserStyle ?????? ??????
		sld					+= sldSt + "UserStyle>";
		
		//name?????? ??? title?????? 
		sld 				+= nameTitleToString(name,title);
		sld					+= sldSt + "IsDefault>" + "1" + sldEd + "IsDefault>";
		
		//UserStyle ?????? ??????
		sld 				+= createFeatureTypeStyle(name, title, source, voArr, textVo);
		
		//UserStyle ?????? ???
		sld					+= sldEd + "UserStyle>";
		return sld;
	}
	
	//FeatureTypeStyle ?????? ??????
	private String createFeatureTypeStyle(String name, String title, String source, KwsRulesVO[] voArr, KwsTextSymbolVO textVo){
		String sld			= "";
		String mapPrefix = messageSource.getMessage("Map.Prefix", null, Locale.getDefault());
		
		//FeatureTypeStyle ?????? ??????
		sld					+= seSt + "FeatureTypeStyle version=\"1.1.0\">";
	
		//name?????? ??? title?????? 
		sld 				+= nameTitleToString(name,title);
		
		sld					+= seSt + "FeatureTypeName>";
//		sld					+= mapPrefix + ":" +  name;
		sld					+= mapPrefix + ":" +  source;
		sld					+= seEd + "FeatureTypeName>";
		
		//Rules ?????? ??????
		sld					+= createRule(voArr, textVo);
				
		//FeatureTypeStyle ?????? ???
		sld					+= seEd + "FeatureTypeStyle>";
		return sld;
	}
	
	//Rules ?????? ??????
	private String createRule(KwsRulesVO[] voArr, KwsTextSymbolVO textVo){
		String sld			= "";
		
		int cnt				= voArr.length;
		for(int i=0; i < cnt; i++){
			KwsRulesVO vo	= voArr[i];
			//Rules ?????? ??????
			sld				+= kwsRulesVOToString(vo, textVo); 
		}
		
		return sld;
	}
		
	//Rules ?????? ??????
	private String kwsRulesVOToString(KwsRulesVO vo, KwsTextSymbolVO text){
		String sld			= "";
		
		//??????????????? ?????? ???????????? ??????
		boolean visible = false;
		if(StringUtils.isNotBlank(vo.getVisible()) && StringUtils.equalsIgnoreCase(vo.getVisible(), "true")) {
			visible = true;
		}
	
		boolean textVisible = false;
		if(text != null && StringUtils.isNotBlank(text.getVisible()) && StringUtils.equalsIgnoreCase(text.getVisible(), "true")) {
			textVisible = true;
		}
		if(!visible && !textVisible){
			return "";
		}
		
		String name 		= vo.getName() == null ? "" : vo.getName(); 
		String title 		= vo.getTitle() == null ? "" : vo.getTitle();
		String MaxScale		= vo.getMaxScale() == null ? "" : vo.getMaxScale();
		String MinScale		= vo.getMinScale() == null ? "" : vo.getMinScale();
		KwsFilterVO filterVO = vo.getFilter();
		
		KwsPolygonSymbolVO polygon	= vo.getPolygon();
		KwsLineSymbolVO line		= vo.getLine();
		KwsPointSymbolVO point		= vo.getPoint();
		
		sld					+= seSt + "Rule>";
		//name?????? ??? title?????? 
		sld 				+= nameTitleToString(name,title);
		if(filterVO != null){
			sld 				+= createFilter(filterVO);
		}
		
		sld					+= createMaxScale(MaxScale);
		sld					+= createMinScale(MinScale);
		
		
		if(visible) {
			if(polygon != null){
				sld					+= createPolygonSymbolizer(polygon);
			}
			
			if(line != null){
				sld					+= createLineSymbolizer(line);
			}
			
			if(point != null){
				sld					+= createPointSymbolizer(point);
			}
		}
		
		if(textVisible) {
			if(text != null){
				sld					+= createTextSymbolizer(text);
			}
		}
		
		sld 				+= seEd + "Rule>";
		
		return sld;
	}
	
	//MaxScaleDenominator ?????? ??????
	private String createMaxScale(String scale){
		String sld			= "";
		if(scale.equals("")){
			return sld;
		}
		
		sld					+= seSt + "MaxScaleDenominator>";
		sld					+= scale;
		sld					+= seEd + "MaxScaleDenominator>";
		return sld;
	}
	
	//MinScaleDenominator ?????? ??????
	private String createMinScale(String scale){
		String sld			= "";
		if(scale.equals("")){
			return sld;
		}
		
		sld					+= seSt + "MinScaleDenominator>";
		sld					+= scale;
		sld					+= seEd + "MinScaleDenominator>";
		return sld;
	}
	
	//Filter?????? ??????
	private String createFilter(KwsFilterVO vo){
		String sld			= "";
		
		String type			= vo.getType() == null ? "" : vo.getType();
		String literal		= vo.getLiteral() == null ? "" : vo.getLiteral();
		String property		= vo.getProperty() == null ? "" : vo.getProperty();
		
		sld					+= ogcSt + "Filter>";
		if(type.equals("==")){
			sld 			+= ogcSt + "PropertyIsEqualTo matchCase=\"true\">";
			sld 			+= createFilterProperty(property);
			sld 			+= createFilterLiteral(literal);
		}else if(type.equals(">=")){
			sld 			+= ogcSt + "PropertyIsGreaterThanOrEqualTo matchCase=\"false\">";
			sld 			+= createFilterProperty(property);
			sld 			+= createFilterLiteral(literal);
		}else if(type.equals("<")){
			sld 			+= ogcSt + "PropertyIsLessThan matchCase=\"false\">";
			sld 			+= createFilterProperty(property);
			sld 			+= createFilterLiteral(literal);
		}else if(type.equals("..")){
			sld 			+= ogcSt + "PropertyIsBetween matchCase=\"true\">";
			sld 			+= createFilterProperty(property);
			sld 			+= createFilterLiteral(literal);
		}else{
			sld 			+= ogcSt + "PropertyName>";
			sld 			+= property;
		}
		
//		//Property
//		sld 				+= ogcSt + "PropertyName>";
//		sld					+= property;
//		sld 				+= ogcEd + "PropertyName>";
//		
//		//Literal
//		sld 				+= ogcSt + "Literal>";
//		sld 				+= literal;
//		sld 				+= ogcEd + "Literal>";
		
		
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
		
//		sld 				+= ogcEd + "PropertyIsEqualTo>";
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
	
	//PolygonSymbolizer ?????? ??????
	private String createPolygonSymbolizer(KwsPolygonSymbolVO vo){
		String sld				= "";
		String daahArray		= vo.getStrokeDasharray() == null ? "0.0" : vo.getStrokeDasharray();
		String daahOffset		= vo.getStrokeDashoffset() == null ? "0" : vo.getStrokeDashoffset();
		String stroke			= vo.getStroke() == null ? "#000000" : vo.getStroke();
		String strokeOpacity	= vo.getStrokeOpacity()	== null ? "1"		: vo.getStrokeOpacity();
		String width			= vo.getStrokeWidth()	== null ? ""		: vo.getStrokeWidth();
		
		sld 				+= seSt + "PolygonSymbolizer uom=\"http://www.opengeospatial.org/se/units/pixel\">";
		
		//geometry
		sld					+= createGeometry();
		
		KwsStrokeVO strokeVO = new KwsStrokeVO();
		strokeVO.setStrokeDasharray(daahArray);
		strokeVO.setStrokeDashoffset(daahOffset);
		strokeVO.setStroke(stroke);
		strokeVO.setStrokeOpacity(strokeOpacity);
		strokeVO.setStrokeWidth(width);
		
		sld 				+= createStroke(strokeVO);
		
		sld 				+= createFill(vo);
		
		sld 				+= seEd + "PolygonSymbolizer>";
		return sld;
	}
	
	//Geometry ?????? ??????
	private String createGeometry(){
		String sld			= "";
		String mapGeometry = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		
		sld					+= seSt + "Geometry>";
		sld					+= ogcSt + "PropertyName>";
		sld					+= mapGeometry;
		sld					+= ogcEd + "PropertyName>";
		sld					+= seEd + "Geometry>";
		
		return sld;
	}
	
	//Geometry ?????? ??????
	private String createGeometryFun(String name){
		String sld			= "";
		String mapGeometry = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		
		sld					+= seSt + "Geometry>";
		sld					+= ogcSt + "Function name=\"" + name +  "\">";
		sld					+= ogcSt + "PropertyName>";
		sld					+= mapGeometry;
		sld					+= ogcEd + "PropertyName>";
		sld					+= ogcEd + "Function>";
		sld					+= seEd + "Geometry>";
		
		return sld;
	}
	
	//Storke ?????? ??????
	private String createStroke(KwsStrokeVO vo){
		String sld			= "";
		
		//stroke null ?????? ????????? #000000 ??????
		String stroke		= vo.getStroke()		== null ? "#000000" : vo.getStroke();
		//opacity null ?????? ????????? 1 ??????
		String opacity		= vo.getStrokeOpacity()	== null ? "1"		: vo.getStrokeOpacity();
		//width null ?????? ????????? 2 ??????
		String width		= vo.getStrokeWidth()	== null ? ""		: vo.getStrokeWidth();
		//dasharray
		String dasharray	= vo.getStrokeDasharray() == null ? ""		: vo.getStrokeDasharray();
		//dasharray
		String dashoffset	= vo.getStrokeDashoffset() == null ? "0.0"		: vo.getStrokeDashoffset();
		
		// width ?????? ????????? stroke??? ???????????? ??????.
		if((width.equals("") || width.equals("0"))){
			return sld;
		}
		
		sld					+= seSt + "Stroke>";
		//stroke ?????? ??????
		sld					+= svgStroke + stroke + seSvgEd;
		//stroke-opacity ?????? ??????
		sld					+= svgStrokeOpacity + opacity + seSvgEd;
		
		//stroke-width ?????? ??????
		sld					+= svgStrokeWidth + width + seSvgEd;
		
		//stroke-linejoin
		sld					+= svgStrokeLinejoin + "mitre" + seSvgEd;
		//stroke-linecap
		sld					+= svgStrokeLinecap + "butt" + seSvgEd;
		
		if(!dasharray.equals("")){
			//svgStrokeDasharray
			if(dasharray.equals("dot")){
				dasharray = "2.0 2.0";
			}else if(dasharray.equals("dash")){
				dasharray = "7.0 3.0";
			}else if(dasharray.equals("dashDot")){
				dasharray = "10.0 2.0 2.0 2.0";
			}else if(dasharray.equals("dashDotDot")){
				dasharray = "10.0 2.0 2.0 2.0 2.0 2.0";
			}
			
			if(!dasharray.equals("solid") && !dasharray.equals("0.0")){
				sld					+= svgStrokeDasharray + dasharray + seSvgEd;
				sld					+= svgStrokeDashoffset + dashoffset + seSvgEd;	
			}
			
		}
		
		sld					+= seEd + "Stroke>";
		return sld;
	}
	
	//Fill ?????? ??????(KwsPolygonSymbolVO)
	private String createFill(KwsPolygonSymbolVO vo){
		String sld			= "";
		String fill			= vo.getFill()			== null ? "" : vo.getFill().toString();
		String opacity		= vo.getFillOpacity()	== null ? "1.0" 	: vo.getFillOpacity().toString();
		
		if(fill.equals("")){
			return sld;
		}
		
		//fill ?????? ??????
		sld					+= seSt + "Fill>";
		//fill ?????? ??????
		sld					+= svgFill + fill + seSvgEd;
		//fill-opacity ?????? ??????
		sld					+= svgFillOpacity + opacity + seSvgEd;
		sld					+= seEd + "Fill>";
		
		return sld;
	}
	
	//Fill ?????? ??????(KwsTextSymbolVO)
	private String createTextFill(KwsTextSymbolVO vo){
		String sld			= "";
		String fill			= vo.getFill()			== null ? "#FFFFFF" : vo.getFill().toString();
		String opacity		= vo.getFillOpacity()	== null ? "0.5" 	: vo.getFillOpacity().toString();
		
		//fill ?????? ??????
		sld					+= seSt + "Fill>";
		//fill ?????? ??????
		sld					+= svgFill + fill + seSvgEd;
		//fill-opacity ?????? ??????
		sld					+= svgFillOpacity + opacity + seSvgEd;
		sld					+= seEd + "Fill>";
		
		return sld;
	}
	
	//Displacement
	
	//TextSymbolizer ?????? ??????
	private String createTextSymbolizer(KwsTextSymbolVO vo){
		String sld			= "";
		String visible		= vo.getVisible() == null ? "false" : vo.getVisible();
		String maxScale		= vo.getMaxScale() == null ? "" : vo.getMaxScale();
		String minScale		= vo.getMinScale() == null ? "" : vo.getMinScale();
		
		if(!visible.equals("true")){
			return sld;
		}
		
		sld 				+= seSt + "TextSymbolizer uom=\"http://www.opengeospatial.org/se/units/pixel\">";
		
		//geometry
		sld					+= createGeometry();
		
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
		
		if(vo.getVector() != null){
			sld					+= createVendor(vo.getVector());
		}
		
		sld 				+= seEd + "TextSymbolizer>";
		return sld;
	}
	
	//Label ?????? ??????
	private String createLabel(KwsTextSymbolVO vo){
		String sld			= "";
		
		sld					+= seSt + "Label>";
		sld					+= ogcSt + "PropertyName>";
		sld					+= vo.getLabel() == null ? "" : vo.getLabel();
		sld					+= ogcEd + "PropertyName>";
		sld					+= seEd + "Label>";
		
		return sld;
	}
	
	//LabelPlacement ?????? ??????
	private String createLabelPlacement(KwsTextSymbolVO vo){
		String sld			= "";

		String anchor 		= vo.getAnchor() == null ? "cm" : vo.getAnchor();
		String displaceX	= vo.getDisplacementX() == null ? "0.0" : vo.getDisplacementX();
		String displaceY	= vo.getDisplacementY() == null ? "0.0" : vo.getDisplacementY();
		
		sld					+= seSt + "LabelPlacement>"; 
		
		//Point
		sld					+= seSt + "PointPlacement>";
		sld					+= createAnchorPoint(anchor);

		//Point/Displacement
		sld					+= seSt + "Displacement>"; 
		//Point/Displacement/DisplacementX
		sld					+= seSt + "DisplacementX>"; 
		sld					+= displaceX; 
		sld					+= seEd + "DisplacementX>"; 
		//Point/Displacement/DisplacementY
		sld					+= seSt + "DisplacementY>"; 
		sld					+= displaceY; 
		sld					+= seEd + "DisplacementY>"; 
		sld					+= seEd + "Displacement>"; 
		
		sld					+= seEd + "PointPlacement>";
		
//		//Line
//		sld					+= seSt + "LinePlacement>";
//		sld					+= "";
//		sld					+= seEd + "LinePlacement>";
		
		sld					+= seEd + "LabelPlacement>";
		
		 
		
		return sld;
	}
	
	//AnchorPoint ?????? ??????
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
		sld					+= seSt + "AnchorPoint>";
		//Point/AnchorPoint/AnchorPointxX
		sld					+= seSt + "AnchorPointX>"; 
		sld					+= anchorX; 
		sld					+= seEd + "AnchorPointX>";
		//Point/AnchorPoint/AnchorPointxY
		sld					+= seSt + "AnchorPointY>"; 
		sld					+= anchorY; 
		sld					+= seEd + "AnchorPointY>";
		sld					+= seEd + "AnchorPoint>";
		
		return sld;
	}
	
	//Font ?????? ??????
	private String createFont(KwsTextSymbolVO vo){
		String sld			= "";
		
		sld					+= seSt + "Font>";
		//font-family ?????? ??????
		sld					+= seSvgSt + " name=\"font-family\">";
		sld					+= vo.getFontFamily() == null ? "??????" : vo.getFontFamily();
		sld					+= seSvgEd;
		//font-style ?????? ??????
		sld					+= seSvgSt + " name=\"font-style\">";
		sld					+= vo.getFontStyle() == null ? "normal" : vo.getFontStyle();
		sld					+= seSvgEd;
		//font-weight ?????? ??????
		sld					+= seSvgSt + " name=\"font-weight\">";
		sld					+= vo.getFontWeight() == null ? "normal" : vo.getFontWeight();
		sld					+= seSvgEd;
		//font-size ?????? ??????
		sld					+= seSvgSt + " name=\"font-size\">";
		sld					+= vo.getFontSize() == null ? "12" : vo.getFontSize();
		sld					+= seSvgEd;
		
		sld					+= seEd + "Font>";
		
		return sld;
	}
	
	//Halo ?????? ??????
	private String createHalo(KwsTextSymbolVO vo){
		String sld			= "";
		
		String fill			= vo.getHalo() == null ? "#ffffff" : vo.getHalo();
		String Opacity		= vo.getHaloOpacity() == null ? "1" : vo.getHaloOpacity();
		
		sld					+= seSt + "Halo>";
		sld					+= seSt + "Radius>";
		sld					+= vo.getHaloRadius() == null ? "2.0" : vo.getHaloRadius();
		sld					+= seEd + "Radius>";
		
		sld					+= seSt + "Fill>";
		sld					+= svgFill + fill + seSvgEd;
		sld					+= svgFillOpacity + Opacity + seSvgEd;
		sld					+= seEd + "Fill>";
		sld					+= seEd + "Halo>";
		
		return sld;
	}
	
	//Vendor ?????? ??????
	private String createVendor(KwsVendorVO vo){
		String sld			= "";
		String follow		= vo.getFollowLine() == null ? "" : vo.getFollowLine();
		
		
		if(follow.equals("")){
			//sld					+= sldSt + "VendorOption followLine=\"true\"/>";
		}else{
			sld					+= sldSt + "VendorOption followLine=\"true\">";
			sld					+= follow;
			sld					+= sldEd + "VendorOption>";
		}
		
		return sld;
	}	
	
	//LineSymbolizer ?????? ??????
	private String createLineSymbolizer(KwsLineSymbolVO vo){
		String sld			= "";
		
		sld 				+= seSt + "LineSymbolizer uom=\"http://www.opengeospatial.org/se/units/pixel\">";
		
		//geometry
		sld					+= createGeometry();
		KwsStrokeVO strokeVO = new KwsStrokeVO();
		strokeVO.setStrokeDasharray(vo.getStrokeDasharray());
		strokeVO.setStrokeDashoffset(vo.getStrokeDashoffset());
		strokeVO.setStroke(vo.getStroke());
		strokeVO.setStrokeOpacity(vo.getStrokeOpacity());
		strokeVO.setStrokeWidth(vo.getStrokeWidth());
		
		sld 				+= createStroke(strokeVO);
		
		//PerpendicularOffset
		sld					+= "<se:PerpendicularOffset>0.0</se:PerpendicularOffset>";
				
		sld 				+= seEd + "LineSymbolizer>";
		
		return sld;
	}
	
	//PointSymbolizer ????????????
	private String createPointSymbolizer(KwsPointSymbolVO vo){
		String sld			= "";
		
		sld 				+= seSt + "PointSymbolizer uom=\"http://www.opengeospatial.org/se/units/pixel\">";
		
		
		
		//Graphic ?????? ??????
		sld					+= createGraphic(vo);
		
		sld 				+= seEd + "PointSymbolizer>";
		
		return sld;
	}
	
	//Graphic ????????????
	private String createGraphic(KwsPointSymbolVO vo){
		String sld			= "";
		String anchor		= vo.getAnchor() == null ? "cm" : vo.getAnchor();
		String rotation		= vo.getRotation() == null ? "" : vo.getRotation();
		String opacity		= vo.getOpacity() == null ? "1.0" : vo.getOpacity();
		
		//Graphic????????? ?????? ??????
		if(vo.getMark() == null){
			//Graphic ?????? ?????? (ExternalGraphic)
			sld					+= createGeometry();
		}else{
			//Graphic ?????? ??????  (endpoint)
			sld					+= createGeometryFun("endpoint");
		}
				
		//Graphic ????????????
		sld					+= seSt + "Graphic>";

		//Graphic????????? ?????? ??????
		if(vo.getMark() == null){
			//Graphic ?????? ?????? (ExternalGraphic)
			sld					+= createExternalGraphic(vo);
		}else{
			//Graphic ?????? ??????  (Mark)
			sld					+= createMarkGraphic(vo.getMark());
			//Mark ??? ?????????  rotation??? empty ?????? ????????? Rotation??? 90?????? ??????
			if(rotation.equals("")) rotation = "90";
		}
		
		//rotation ?????? empty ?????? ????????? 0?????? ?????? (????????? mark?????? ?????? ?????? ????????? ????????? ?????? ?????? ??????????????? ?????? ?????? ?????????.
		if(rotation.equals(""))rotation = "0";
		
		//Opacity ????????????
		sld					+= seSt + "Opacity>";
		sld					+= opacity;
		sld					+= seEd + "Opacity>";
		
		//Size ????????????
		sld					+= seSt + "Size>";
		sld					+= vo.getSize() == null ? "15.0" : vo.getSize();
		sld					+= seEd + "Size>";
		
		//Rotation ????????????
		sld					+= seSt + "Rotation>";
		sld					+= rotation;
		sld					+= seEd + "Rotation>";
		
		//AnchorPoint ????????????
		sld					+= createAnchorPoint(anchor);
		
		//Displacement ????????????
		sld					+= seSt + "Displacement>";
		//DisplacementX ????????????
		sld					+= seSt + "DisplacementX>";
		sld					+= "0.0";
		sld					+= seEd + "DisplacementX>";
		//DisplacementY ????????????
		sld					+= seSt + "DisplacementY>";
		sld					+= "0.0";
		sld					+= seEd + "DisplacementY>";
		
		sld					+= seEd + "Displacement>";
		
		sld					+= seEd + "Graphic>";
		return sld;
	}
	
	//Mark ?????? ??????
	private String createMarkGraphic(KwsMarkVO vo){
		String sld				= "";
		String fill				= vo.getFill() == null ? "#000000" : vo.getFill();
		String opacity			= vo.getFillOpacity() == null ? "" : vo.getFillOpacity();
		String stroke			= vo.getStroke() == null ? "#000000" : vo.getStroke();
		String strokeOpacity	= vo.getStrokeOpacity()	== null ? "1"		: vo.getStrokeOpacity();
		String width			= vo.getStrokeWidth()	== null ? ""		: vo.getStrokeWidth();
		
		//Mark ?????? ??????
		sld					+= seSt + "Mark>";
		//WellKnownName ?????? ??????
		sld					+= seSt + "WellKnownName>";
		sld					+= vo.getWellKnownName();
		sld					+= seEd + "WellKnownName>";		
		//fill ?????? ??????
		sld					+= seSt + "Fill>";
		//fill ?????? ??????
		sld					+= svgFill + fill + seSvgEd;
		//fill-opacity ?????? ??????
		sld					+= svgFillOpacity + opacity + seSvgEd;
		sld					+= seEd + "Fill>";
		
		//stroke ?????? ??????
		sld					+= seSt + "Stroke>";
		//svgStroke ?????? ??????
		sld					+= svgStroke + stroke + seSvgEd;
		//stroke-opacity ?????? ??????
		sld					+= svgStrokeOpacity + strokeOpacity + seSvgEd;
		
		//stroke-width ?????? ??????
		sld					+= svgStrokeWidth + width + seSvgEd;
		
		//stroke-linejoin
		sld					+= svgStrokeLinejoin + "mitre" + seSvgEd;
		//stroke-linecap
		sld					+= svgStrokeLinecap + "butt" + seSvgEd;
		sld					+= seEd + "Stroke>";
		
		sld					+= seEd + "Mark>";		
		return sld;
	}
	
	//ExternalGraphic ?????? ??????
	private String createExternalGraphic(KwsPointSymbolVO vo){
		String resource		= vo.getResource() == null ? "" : vo.getResource();
		
		StringBuffer sld = new StringBuffer();
		if(StringUtils.equals("base64", vo.getType())) {
			//ExternalGraphic ????????????
			sld.append(seSt);
			sld.append("ExternalGraphic>");
			
			// InlineContent ????????????
			sld.append(seSt);
			sld.append("InlineContent encoding=\"base64\">");
			sld.append(resource);
			sld.append(seEd);
			sld.append("InlineContent>");
			
			//Format ????????????
			sld.append(seSt);
			sld.append("Format>");
			sld.append("image/png");
			sld.append(seEd);
			sld.append("Format>");
			
			sld.append(seEd);
			sld.append("ExternalGraphic>");
		}
		else {
			//Map.Url.Symbol
			String symbolPath	= messageSource.getMessage("Map.Url.Symbol", null, Locale.getDefault());
			
			//ExternalGraphic ????????????
			sld.append(seSt);
			sld.append("ExternalGraphic>");
			
			//Onlineresource ????????????
			sld.append(seSt);
			sld.append("OnlineResource xlink:type=\"simple\" xlink:href=\"");
			sld.append(symbolPath);
			sld.append("/");
			sld.append(resource);
			sld.append("\">");
			sld.append(seEd);
			sld.append("OnlineResource>");
			
			//Format ????????????
			sld.append(seSt);
			sld.append("Format>");
			sld.append("image/png");
			sld.append(seEd);
			sld.append("Format>");
			
			sld.append(seEd);
			sld.append("ExternalGraphic>");
		}
		return sld.toString();
	}

	@Override
	public String jsonToSld(KworksSld kworksSld) {
		return null;
	}
}
