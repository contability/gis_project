package kr.co.gitt.kworks.service.sld;

import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.dto.sld.json.Filter;
import kr.co.gitt.kworks.dto.sld.json.KworksSld;
import kr.co.gitt.kworks.dto.sld.json.LineSymbolizer;
import kr.co.gitt.kworks.dto.sld.json.NamedLayer;
import kr.co.gitt.kworks.dto.sld.json.PointSymbolizer;
import kr.co.gitt.kworks.dto.sld.json.PolygonSymbolizer;
import kr.co.gitt.kworks.dto.sld.json.Rule;
import kr.co.gitt.kworks.dto.sld.json.TextSymbolizer;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class GeoServerSldConverterServiceImpl
/// kr.co.gitt.kworks.service.sld \n
///   ㄴ GeoServerSldConverterServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 24. 오후 3:56:58 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 GeoServer 용 SLD 변환기 입니다.
///   -# 
/////////////////////////////////////////////
@Service("geoServerSldConverterService")
public class GeoServerSldConverterServiceImpl implements SldConverterService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 메세지 소스
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	/// 버전
	private final String VERSION = "1.0.0";
	
	/// SLD 네임스페이스
	Namespace SLD = Namespace.getNamespace("http://www.opengis.net/sld");
	
	/// XSI 네임스페이스
	Namespace XSI = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
	
	/// OGC 네임스페이스
	Namespace OGC = Namespace.getNamespace("ogc", "http://www.opengis.net/ogc");
	
	/// XLINK 네임스페이스
	Namespace XLINK = Namespace.getNamespace("xlink", "http://www.w3.org/1999/xlink");
	
	@Override
	public String convert(KworksSld kworksSld) {
		Document document = new Document();
	
		Element element = createStyledLayerDescriptor(kworksSld);
		document.setRootElement(element);
		
		XMLOutputter xmlOutputter = new XMLOutputter(); 
		Format format = xmlOutputter.getFormat();
		format.setEncoding("EUC-KR");
		xmlOutputter.setFormat(format);
		return xmlOutputter.outputString(document);
	}
	
	private Element createStyledLayerDescriptor(KworksSld kworksSld) {
		Element element = new Element("StyledLayerDescriptor", SLD);
		
		element.setAttribute("schemaLocation", "http://www.opengis.net/sld/StyledLayerDescriptor.xsd", XSI);
		element.setAttribute("version", VERSION);
		
		element.addNamespaceDeclaration(XSI);
		element.addNamespaceDeclaration(XLINK);
		element.addNamespaceDeclaration(OGC);
		element.addNamespaceDeclaration(SLD);
		
		for(NamedLayer namedLayer : kworksSld.getNamedLayers()) {
			if(namedLayer.getVisible()) {
				element.addContent(createNameLayer(namedLayer));
			}
		}
			
		return element;
	}
	
	private Element createNameLayer(NamedLayer namedLayer) {
		Element element = new Element("NamedLayer", SLD);
		element.addContent(createName(getNameWithWorkspace(namedLayer.getName())));
		element.addContent(createUserStyle(namedLayer));
		return element;
	}

	private Element createUserStyle(NamedLayer namedLayer) {
		Element element = new Element("UserStyle", SLD);
		element.addContent(createName(namedLayer.getName()));
		element.addContent(createTitle(namedLayer.getTitle()));
		element.addContent(createFeatureTypeStyle(namedLayer));
		return element;
	}
	
	private Element createFeatureTypeStyle(NamedLayer namedLayer) {
		Element element = new Element("FeatureTypeStyle", SLD);
		element.addContent(createName(namedLayer.getName()));
		element.addContent(createTitle(namedLayer.getTitle()));
		
		for(Rule rule : namedLayer.getRules()) {
			if(rule.getVisible()) {
				element.addContent(createRule(rule));
			}
		}
		if(namedLayer.getText() != null) {
			element.addContent(createTextRule(namedLayer.getText()));
		}
		
		return element;
	}

	private Element createRule(Rule rule) {
		Element element = new Element("Rule", SLD);
		
		element.addContent(createName(rule.getName()));
		element.addContent(createTitle(rule.getTitle()));
		
		if(rule.getFilter() != null) {
			element.addContent(createFilter(rule.getFilter()));
		}
		if(rule.getMinScale() != null) {
			element.addContent(createMinScaleDenominator(rule.getMinScale()));
		}
		if(rule.getMaxScale() != null) {
			element.addContent(createMaxScaleDenominator(rule.getMaxScale()));
		}
		if(rule.getPoint() != null) {
			element.addContent(createPointSymbolizer(rule.getPoint()));
		}
		if(rule.getLine() != null) {
			element.addContent(createLineSymbolizer(rule.getLine()));
		}
		if(rule.getPolygon() != null) {
			element.addContent(createPolygonSymbolizer(rule.getPolygon()));
		}
		
		return element;
	}

	private Element createFilter(Filter filter) {
		Element element = new Element("Filter", OGC);

		String filterType = filter.getType();
		if(StringUtils.equals("==", filterType)) {
			element.addContent(createPropertyIsEqualTo(filter.getProperty(), filter.getLiteral()));
		}
		else if(StringUtils.equals(">=", filterType)) {
			element.addContent(createPropertyIsGreaterThanOrEqualTo(filter.getProperty(), filter.getLiteral()));
		}
		else if(StringUtils.equals("<", filterType)) {
			element.addContent(createPropertyIsLessThan(filter.getProperty(), filter.getLiteral()));
		}
		else if(StringUtils.equals("..", filterType)) {
			element.addContent(createPropertyIsBetween(filter.getProperty(), filter.getLiteral()));
		}
		
		return element;
	}

	private Element createPointSymbolizer(PointSymbolizer pointSymbolizer) {
		Element element = new Element("PointSymbolizer", SLD);
		element.addContent(createGraphic(pointSymbolizer));
		return element;
	}

	private Element createGraphic(PointSymbolizer pointSymbolizer) {
		Element element = new Element("Graphic", SLD);
		element.addContent(createExternalGraphic(pointSymbolizer.getResource()));
		element.addContent(createOpacity(1.0));
		element.addContent(createSize(pointSymbolizer.getSize()));
		return element;
	}
	
	private Element createLineSymbolizer(LineSymbolizer lineSymbolizer) {
		Element element = new Element("LineSymbolizer", SLD);
		element.addContent(createStrokeSymbolizer(lineSymbolizer));
		return element;
	}
	
	private Element createStrokeSymbolizer(LineSymbolizer lineSymbolizer) {
		Element element = new Element("Stroke", SLD);
		
		if(lineSymbolizer.getStroke() != null) {
			element.addContent(createCssParameter("stroke", lineSymbolizer.getStroke()));
		}
		if(lineSymbolizer.getStrokeOpacity() != null) {
			element.addContent(createCssParameter("stroke-opacity", lineSymbolizer.getStrokeOpacity().toString()));
		}
		if(lineSymbolizer.getStrokeWidth() != null) {
			element.addContent(createCssParameter("stroke-width", lineSymbolizer.getStrokeWidth().toString()));
		}
		if(lineSymbolizer.getStrokeDasharray() != null) {
			element.addContent(createCssParameter("stroke-dasharray", getDashArray(lineSymbolizer.getStrokeDasharray())));
		}
		
		element.addContent(createCssParameter("stroke-linejoin", "mitre"));
		element.addContent(createCssParameter("stroke-linecap", "butt"));
		element.addContent(createCssParameter("stroke-dashoffset", "0"));
		
		return element;
	}
	
	private Element createPolygonSymbolizer(PolygonSymbolizer polygonSymbolizer) {
		Element element = new Element("PolygonSymbolizer", SLD);
		
		if(polygonSymbolizer.getFill() != null) {
			element.addContent(createFill(polygonSymbolizer.getFill(), polygonSymbolizer.getFillOpacity()));
		}
		if(polygonSymbolizer.getStroke() != null) {
			element.addContent(createStrokeSymbolizer(polygonSymbolizer));
		}
		return element;
	}
	
	private Element createTextRule(TextSymbolizer textSymbolizer) {
		Element element = new Element("Rule", SLD);
		if(textSymbolizer.getMinScale() != null) {
			element.addContent(createMinScaleDenominator(textSymbolizer.getMinScale()));
		}
		if(textSymbolizer.getMaxScale() != null) {
			element.addContent(createMaxScaleDenominator(textSymbolizer.getMaxScale()));
		}
		element.addContent(createTextSymbolizer(textSymbolizer));
		return element;
	}
	
	private Element createTextSymbolizer(TextSymbolizer textSymbolizer) {
		Element element = new Element("TextSymbolizer", SLD);
		element.addContent(createLabel(textSymbolizer.getLabel()));
		element.addContent(createFont(textSymbolizer.getFontFamily(), textSymbolizer.getFontSize(), textSymbolizer.getFontStyle(), textSymbolizer.getFontWeight()));
		element.addContent(createHalo(textSymbolizer.getHalo(), textSymbolizer.getHaloOpacity(), textSymbolizer.getHaloRadius()));
		element.addContent(createFill(textSymbolizer.getFill(), textSymbolizer.getFillOpacity()));
		return element;
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	private Element createName(String name) {
		Element element = new Element("Name", SLD);
		element.setText(getLowerCase(name));
		return element;
	}
	
	private Element createTitle(String title) {
		Element element = new Element("Title", SLD);
		element.setText(title);
		return element;
	}
	
	private Element createPropertyName(String property) {
		Element element = new Element("PropertyName", OGC);
		element.setText(property);
		return element;
	}
	
	private Element createPropertyIsEqualTo(String property, String literal) {
		Element element = new Element("PropertyIsEqualTo", OGC);
		element.addContent(createPropertyName(property));
		element.addContent(createLiteral(literal));
		return element;
	}

	private Element createPropertyIsGreaterThanOrEqualTo(String property, String literal) {
		Element element = new Element("PropertyIsGreaterThanOrEqualTo", OGC);
		element.addContent(createPropertyName(property));
		element.addContent(createLiteral(literal));
		return element;
	}

	private Element createPropertyIsLessThan(String property, String literal) {
		Element element = new Element("PropertyIsLessThan", OGC);
		element.addContent(createPropertyName(property));
		element.addContent(createLiteral(literal));
		return element;
	}

	private Element createPropertyIsBetween(String property, String literal) {
		Element element = new Element("PropertyIsBetween", OGC);
		element.addContent(createPropertyName(property));
		element.addContent(createLiteral(literal));
		return element;
	}

	private Element createMinScaleDenominator(Double minScale) {
		Element element = new Element("MinScaleDenominator", SLD);
		element.setText(minScale.toString());
		return element;
	}

	private Element createMaxScaleDenominator(Double maxScale) {
		Element element = new Element("MaxScaleDenominator", SLD);
		element.setText(maxScale.toString());
		return element;
	}

	private Element createLiteral(String literal) {
		Element element = new Element("Literal", OGC);
		element.setText(literal);
		return element;
	}
	
	private Element createExternalGraphic(String resource) {
		Element element = new Element("ExternalGraphic", SLD);
		element.addContent(createOnlineResource(resource));
		element.addContent(createFormat());
		return element;
	}

	private Element createOnlineResource(String resource) {
		Element element = new Element("ExternalGraphic", SLD);
		element.setAttribute("type", "simple", XLINK);
		element.setAttribute("href", getSymbolPath(resource), XLINK);
		return element;
	}
	
	private Element createFormat() {
		Element element = new Element("Format", SLD);
		element.setText("image/png");
		return element;
	}
	
	private Element createOpacity(Double d) {
		Element element = new Element("Opacity", SLD);
		element.setText(d.toString());
		return element;
	}
	
	private Element createSize(Double size) {
		Element element = new Element("Format", SLD);
		element.setText(size.toString());
		return element;
	}
	
	private Element createCssParameter(String name, String text) {
		Element element = new Element("CssParameter", SLD);
		element.setAttribute("name", name);
		element.setText(text);
		return element;
	}
	
	private Element createFill(String fill, Double fillOpacity) {
		Element element = new Element("Fill", SLD);
		if(fill != null) {
			element.addContent(createCssParameter("fill", fill));
		}
		if(fillOpacity != null) {
			element.addContent(createCssParameter("fill-opacity", fillOpacity.toString()));
		}
		return element;
	}
	
	private Element createLabel(String label) {
		Element element = new Element("Label", SLD);
		element.addContent(createPropertyName(getLowerCase(label)));
		return element;
	}
	
	private Element createFont(String fontFamily, Double fontSize, String fontStyle, String fontWeight) {
		Element element = new Element("Font", SLD);
		if(fontFamily != null) {
			element.addContent(createCssParameter("font-family", fontFamily));
		}
		if(fontSize != null) {
			element.addContent(createCssParameter("font-size", fontSize.toString()));
		}
		if(fontStyle != null) {
			element.addContent(createCssParameter("font-style", fontStyle));
		}
		if(fontWeight != null) {
			element.addContent(createCssParameter("font-weight", fontWeight));
		}
		return element;
	}

	private Element createHalo(String halo, Double haloOpacity, Double haloRadius) {
		Element element = new Element("Halo", SLD);
		if(haloRadius != null) {
			element.addContent(createRadius(haloRadius));
		}
		element.addContent(createFill(halo, haloOpacity));
		return element;
	}

	private Element createRadius(Double haloRadius) {
		Element element = new Element("Radius", SLD);
		element.setText(haloRadius.toString());
		return element;
	}

	private String getLowerCase(String name) {
		return name.toLowerCase();
	}
	
	private String getNameWithWorkspace(String name) {
		String workspace = messageSource.getMessage("Map.Prefix", null, Locale.getDefault());
		return workspace + ":" + getLowerCase(name);
	}

	private String getSymbolPath(String resource) {
		String symbolPath = messageSource.getMessage("Map.Url.Symbol", null, Locale.getDefault());
		return symbolPath + "/" + resource;
	}
	
	private String getDashArray(String dashArray) {
		if(StringUtils.equals("dot", dashArray)) {
			return "2.0 2.0";
		}
		else if(StringUtils.equals("dash", dashArray)) {
			return "7.0 3.0";
		}
		else if(StringUtils.equals("dashDot", dashArray)) {
			return "10.0 2.0 2.0 2.0";
		}
		else if(StringUtils.equals("dashDotDot", dashArray)) {
			return "10.0 2.0 2.0 2.0 2.0 2.0";
		}
		else {
			return null;
		}
	}

}
