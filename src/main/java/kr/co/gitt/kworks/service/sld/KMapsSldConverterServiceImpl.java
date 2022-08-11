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

@Service("kMapsSldConverterService")
public class KMapsSldConverterServiceImpl implements SldConverterService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 메세지 소스
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	/// 버전
	private final String VERSION = "1.1.0";
	
	/// SLD 네임스페이스 
	Namespace SLD = Namespace.getNamespace("sld", "http://www.opengis.net/sld");
	
	/// XSI 네임스페이스
	Namespace XSI = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
	
	/// OGC 네임스페이스
	Namespace OGC = Namespace.getNamespace("ogc", "http://www.opengis.net/ogc");
	
	/// XLINK 네임스페이스
	Namespace SE = Namespace.getNamespace("se", "http://www.opengis.net/se");
	
	/// XLINK 네임스페이스
	Namespace XLINK = Namespace.getNamespace("xlink", "http://www.w3.org/1999/xlink");

	@Override
	public String convert(KworksSld kworksSld) {
		Document document = new Document();
		
		Element element = createStyledLayerDescriptor(kworksSld);
		document.setRootElement(element);
		
		XMLOutputter xmlOutputter = new XMLOutputter(); 
		Format format = xmlOutputter.getFormat();
		format.setEncoding("UTF-8");
		xmlOutputter.setFormat(format);
		return xmlOutputter.outputString(document);
	}
	
	private Element createStyledLayerDescriptor(KworksSld kworksSld) {
		Element element = new Element("StyledLayerDescriptor", SLD);
		
		element.setAttribute("schemaLocation", "http://www.opengis.net/sld/StyledLayerDescriptor.xsd", XSI);
		element.setAttribute("version", VERSION);
		
		element.addNamespaceDeclaration(SLD);
		element.addNamespaceDeclaration(XSI);
		element.addNamespaceDeclaration(OGC);
		element.addNamespaceDeclaration(SE);
		element.addNamespaceDeclaration(XLINK);
		
		element.addContent(createName(kworksSld.getName()));
		element.addContent(createDescription(kworksSld.getTitle()));
		for(NamedLayer namedLayer : kworksSld.getNamedLayers()) {
			if(namedLayer.getVisible()) {
				element.addContent(createNameLayer(namedLayer));
			}
		}
			
		return element;
	}
	
	private Element createNameLayer(NamedLayer namedLayer) {
		Element element = new Element("NamedLayer", SLD);
		element.addContent(createName(namedLayer.getName()));
		element.addContent(createDescription(namedLayer.getTitle()));
		element.addContent(createLayerFeatureConstraints(namedLayer.getName()));
		element.addContent(createUserStyle(namedLayer));
		return element;
	}
	
	private Element createUserStyle(NamedLayer namedLayer) {
		Element element = new Element("UserStyle", SLD);
		element.addContent(createName(namedLayer.getName()));
		element.addContent(createDescription(namedLayer.getTitle()));
		element.addContent(createIsDefault());
		element.addContent(createFeatureTypeStyle(namedLayer));
		return element;
	}
	
	private Element createFeatureTypeStyle(NamedLayer namedLayer) {
		Element element = new Element("FeatureTypeStyle", SE);
		element.setAttribute("version", VERSION);
		
		element.addContent(createName(namedLayer.getName()));
		element.addContent(createDescription(namedLayer.getTitle()));
		element.addContent(createFeatureTypeName(namedLayer.getName()));
		
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
		Element element = new Element("Rule", SE);
		
		element.addContent(createName(rule.getName()));
		element.addContent(createDescription(rule.getTitle()));
		
		
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
		Element element = new Element("PointSymbolizer", SE);
		element.addContent(createGeometry());
		element.addContent(createGraphic(pointSymbolizer));
		return element;
	}

	private Element createGraphic(PointSymbolizer pointSymbolizer) {
		Element element = new Element("Graphic", SE);
		element.addContent(createExternalGraphic(pointSymbolizer.getResource()));
		element.addContent(createOpacity(1.0));
		element.addContent(createSize(pointSymbolizer.getSize()));
		element.addContent(createRotation());
		if(StringUtils.isNotBlank(pointSymbolizer.getAnchor())) {
			element.addContent(createAnchorPoint(pointSymbolizer.getAnchor()));
		}
		element.addContent(createDisplacement());
		return element;
	}
	
	private Element createLineSymbolizer(LineSymbolizer lineSymbolizer) {
		Element element = new Element("LineSymbolizer", SE);
		element.addContent(createGeometry());
		element.addContent(createStrokeSymbolizer(lineSymbolizer));
		return element;
	}
	
	private Element createStrokeSymbolizer(LineSymbolizer lineSymbolizer) {
		Element element = new Element("Stroke", SE);
		
		if(lineSymbolizer.getStroke() != null) {
			element.addContent(createSvgParameter("stroke", lineSymbolizer.getStroke()));
		}
		if(lineSymbolizer.getStrokeOpacity() != null) {
			element.addContent(createSvgParameter("stroke-opacity", lineSymbolizer.getStrokeOpacity().toString()));
		}
		if(lineSymbolizer.getStrokeWidth() != null) {
			element.addContent(createSvgParameter("stroke-width", lineSymbolizer.getStrokeWidth().toString()));
		}
		if(lineSymbolizer.getStrokeDasharray() != null) {
			element.addContent(createSvgParameter("stroke-dasharray", getDashArray(lineSymbolizer.getStrokeDasharray())));
		}
		
		element.addContent(createSvgParameter("stroke-linejoin", "mitre"));
		element.addContent(createSvgParameter("stroke-linecap", "butt"));
		element.addContent(createSvgParameter("stroke-dashoffset", "0"));
		
		return element;
	}
	
	private Element createPolygonSymbolizer(PolygonSymbolizer polygonSymbolizer) {
		Element element = new Element("PolygonSymbolizer", SE);
		
		if(polygonSymbolizer.getFill() != null) {
			element.addContent(createFill(polygonSymbolizer.getFill(), polygonSymbolizer.getFillOpacity()));
		}
		if(polygonSymbolizer.getStroke() != null) {
			element.addContent(createStrokeSymbolizer(polygonSymbolizer));
		}
		return element;
	}
	
	private Element createTextRule(TextSymbolizer textSymbolizer) {
		Element element = new Element("Rule", SE);
		element.addContent(createTextSymbolizer(textSymbolizer));
		return element;
	}
	
	private Element createTextSymbolizer(TextSymbolizer textSymbolizer) {
		Element element = new Element("TextSymbolizer", SE);
		element.addContent(createLabel(textSymbolizer.getLabel()));
		element.addContent(createFont(textSymbolizer.getFontFamily(), textSymbolizer.getFontSize(), textSymbolizer.getFontStyle(), textSymbolizer.getFontWeight()));
		element.addContent(createLabelPlacement(textSymbolizer.getAnchor()));
		element.addContent(createHalo(textSymbolizer.getHalo(), textSymbolizer.getHaloOpacity(), textSymbolizer.getHaloRadius()));
		element.addContent(createFill(textSymbolizer.getFill(), textSymbolizer.getFillOpacity()));
		return element;
	}

	
	
	private Element createName(String name) {
		Element element = new Element("Name", SE);
		element.setText(name);
		return element;
	}
	
	private Element createDescription(String title) {
		Element element = new Element("Description", SE);
		element.addContent(createTitle(title));
		return element;
	}
	
	private Element createTitle(String title) {
		Element element = new Element("Title", SE);
		element.setText(title);
		return element;
	}

	private Element createLayerFeatureConstraints(String name) {
		Element element = new Element("LayerFeatureConstraints", SLD);
		element.addContent(createFeatureTypeConstraint(name));
		return element;
	}

	private Element createFeatureTypeConstraint(String name) {
		Element element = new Element("FeatureTypeConstraint", SLD);
		element.addContent(createFeatureTypeName(name));
		return element;
	}

	private Element createFeatureTypeName(String name) {
		Element element = new Element("FeatureTypeName", SE);
		element.setText(getNameWithWorkspace(name));
		return element;
	}
	
	private Element createIsDefault() {
		Element element = new Element("FeatureTypeName", SLD);
		element.setText("1");
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
		Element element = new Element("MinScaleDenominator", SE);
		element.setText(minScale.toString());
		return element;
	}

	private Element createMaxScaleDenominator(Double maxScale) {
		Element element = new Element("MaxScaleDenominator", SE);
		element.setText(maxScale.toString());
		return element;
	}

	private Element createLiteral(String literal) {
		Element element = new Element("Literal", OGC);
		element.setText(literal);
		return element;
	}

	private Element createGeometry() {
		Element element = new Element("Geometry", SE);
		element.addContent(createPropertyName(getGeometryName()));
		return element;
	}
	
	private Element createExternalGraphic(String resource) {
		Element element = new Element("ExternalGraphic", SE);
		element.addContent(createOnlineResource(resource));
		element.addContent(createFormat());
		return element;
	}

	private Element createOnlineResource(String resource) {
		Element element = new Element("ExternalGraphic", SE);
		element.setAttribute("type", "simple", XLINK);
		element.setAttribute("href", getSymbolPath(resource), XLINK);
		return element;
	}
	
	private Element createFormat() {
		Element element = new Element("Format", SE);
		element.setText("image/png");
		return element;
	}
	
	private Element createOpacity(Double d) {
		Element element = new Element("Opacity", SE);
		element.setText(d.toString());
		return element;
	}

	private Element createSize(Double size) {
		Element element = new Element("Format", SE);
		element.setText(size.toString());
		return element;
	}
	
	private Element createRotation() {
		Element element = new Element("Rotation", SE);
		element.setText("0");
		return element;
	}
	
	private Element createAnchorPoint(String anchor) {
		Element element = new Element("AnchorPoint", SE);

		double x = 0.5;
		double y = 0.5;
		
		if(StringUtils.equals("lt", anchor)) {
			x = 0;
			y = 0;
		}
		else if(StringUtils.equals("ct", anchor)) {
			x = 0.5;
			y = 0;
		}
		else if(StringUtils.equals("rt", anchor)) {
			x = 1;
			y = 0;
		}
		else if(StringUtils.equals("lm", anchor)) {
			x = 0;
			y = 0.5;
		}
		else if(StringUtils.equals("cm", anchor)) {
			x = 0.5;
			y = 0.5;
		}
		else if(StringUtils.equals("rm", anchor)) {
			x = 1;
			y = 0.5;
		}
		else if(StringUtils.equals("lb", anchor)) {
			x = 0;
			y = 1;
		}
		else if(StringUtils.equals("cb", anchor)) {
			x = 0.5;
			y = 1;
		}
		else if(StringUtils.equals("rb", anchor)) {
			x = 1;
			y = 1;
		}
		element.addContent(createAnchorPointX(x));
		element.addContent(createAnchorPointY(y));
		return element;
	}
	
	private Element createAnchorPointX(Double x) {
		Element element = new Element("AnchorPointX", SE);
		element.setText(String.valueOf(x));
		return element;
	}

	private Element createAnchorPointY(Double y) {
		Element element = new Element("AnchorPointY", SE);
		element.setText(String.valueOf(y));
		return element;
	}

	private Element createDisplacement() {
		Element element = new Element("Displacement", SE);
		element.addContent(createDisplacementX(0.0));
		element.addContent(createDisplacementY(0.0));
		return element;
	}
	
	private Element createDisplacementX(Double x) {
		Element element = new Element("DisplacementX", SE);
		element.setText(String.valueOf(x));
		return element;
	}

	private Element createDisplacementY(Double y) {
		Element element = new Element("DisplacementY", SE);
		element.setText(String.valueOf(y));
		return element;
	}
	
	private Element createSvgParameter(String name, String text) {
		Element element = new Element("CssParameter", SE);
		element.setAttribute("name", name);
		element.setText(text);
		return element;
	}
	
	private Element createFill(String fill, Double fillOpacity) {
		Element element = new Element("Fill", SLD);
		if(fill != null) {
			element.addContent(createSvgParameter("fill", fill));
		}
		if(fillOpacity != null) {
			element.addContent(createSvgParameter("fill-opacity", fillOpacity.toString()));
		}
		return element;
	}
	
	private Element createLabel(String label) {
		Element element = new Element("Label", SE);
		element.addContent(createPropertyName(label));
		return element;
	}
	
	private Element createFont(String fontFamily, Double fontSize, String fontStyle, String fontWeight) {
		Element element = new Element("Font", SE);
		if(fontFamily != null) {
			element.addContent(createSvgParameter("font-family", fontFamily));
		}
		if(fontSize != null) {
			element.addContent(createSvgParameter("font-size", fontSize.toString()));
		}
		if(fontStyle != null) {
			element.addContent(createSvgParameter("font-style", fontStyle));
		}
		if(fontWeight != null) {
			element.addContent(createSvgParameter("font-weight", fontWeight));
		}
		return element;
	}
	
	private Element createLabelPlacement(String anchor) {
		Element element = new Element("LabelPlacement", SLD);
		element.addContent(createPointPlacement(anchor));
		return element;
	}
	
	private Element createPointPlacement(String anchor) {
		Element element = new Element("PointPlacement", SLD);
		element.addContent(createAnchorPoint(anchor));
		element.addContent(createDisplacement());
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
	
	
	
	
	private String getNameWithWorkspace(String name) {
		String workspace = messageSource.getMessage("Map.Prefix", null, Locale.getDefault());
		return workspace + ":" + name;
	}
	
	private String getGeometryName() {
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		return geometryName;
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
