package kr.co.gitt.kworks.service.analysis.topographic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import kr.co.gitt.kworks.cmmn.dto.topographic.ElevationDTO;
import kr.co.gitt.kworks.cmmn.dto.topographic.TopographicAnalysisDTO;
import kr.co.gitt.kworks.cmmn.dto.topographic.TopographicAnalysisResultDTO;
import kr.co.gitt.kworks.cmmn.dto.topographic.TopographicSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.topographic.TopographicSearchResultDTO;
import kr.co.gitt.kworks.model.KwsTopographicMap;
import kr.co.gitt.kworks.service.topographyMap.TopographicMapService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("topographicAnalysisService")
public class TopographicAnalysisServiceImpl implements TopographicAnalysisService {

	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private TopographicMapService topographicMapService;
	
	
	@Override
	public TopographicSearchResultDTO search(String serviceUrl, TopographicSearchDTO searchDTO) throws Exception {
		TopographicSearchResultDTO result = null;
		
		String groupName = searchDTO.getGroupName();
		List<KwsTopographicMap> layers = topographicMapService.selectByGroup(groupName);

		int count = 0;
		StringBuffer paramLayers = new StringBuffer();
		Map<String, KwsTopographicMap> layerMap = new HashMap<String, KwsTopographicMap>();
		for (KwsTopographicMap layer : layers) {
			layerMap.put(layer.getLayerName(), layer);
			if (count != 0)
				paramLayers.append(",");
			paramLayers.append(layer.getLayerName());
			count++;
		}		
		
		HttpURLConnection huc = null;
        InputStream is = null;
		
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		try {
			EgovMap row = new EgovMap(); 
			
			StringBuffer params = new StringBuffer();
			params.append("SERVICE=WPS&VERSION=1.0.0&REQUEST=Execute&LANGUGE=en&IDENTIFIER=GridData&");
			params.append("DATAINPUTS=layers=" + paramLayers.toString());
			params.append(";width=" + searchDTO.getWidth());
			params.append(";height=" + searchDTO.getHeight());
			params.append(";pixel=" + searchDTO.getX());
			params.append("," + searchDTO.getY());
			params.append(";bbox=" + searchDTO.getMinX());
			params.append("," + searchDTO.getMinY());
			params.append("," + searchDTO.getMaxX());
			params.append("," + searchDTO.getMaxY());
			params.append(";srs=" + searchDTO.getSrs());
			params.append("&RAWDATAOUTPUT=griddataresponse");
			
			URL url = new URL(serviceUrl.concat("?") + params);
			URLConnection connection = url.openConnection();
			huc = (HttpURLConnection)connection;
			huc.setRequestMethod("GET");
			huc.setDoOutput(true);
			huc.setDoInput(true);
			huc.setUseCaches(false);
			huc.setDefaultUseCaches(false);
			
			is = huc.getInputStream();
			
			objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
            doc = objDocumentBuilder.parse(is);
			
			result = new TopographicSearchResultDTO(); 
			result.setDataId("TOPOGRAPHIC");
			result.setDataAlias(groupName);
			
			NodeList response = doc.getElementsByTagName("GridDataResponse");
            NodeList topoLayers = response.item(0).getChildNodes();
            
			for (int i = 0; i < topoLayers.getLength(); i++) {
				Node layer = topoLayers.item(i);
				if (layer.getNodeType() != Node.ELEMENT_NODE)
					continue;
				
				NamedNodeMap layerAttr = layer.getAttributes();
				String name = layerAttr.getNamedItem("name").getNodeValue();
				String srs = layerAttr.getNamedItem("srs").getNodeValue();
				
				Node data = layer.getChildNodes().item(0);
				NamedNodeMap dataAttr = data.getAttributes();
				String location = dataAttr.getNamedItem("location").getNodeValue();
				String value = data.getTextContent();
				
				row.put(layerMap.get(name).getLayerName(), value);
				row.put("srs", srs);
				row.put("location", location);
			}
			
			result.setData(row);
			
			long endTime = Calendar.getInstance().getTimeInMillis();
			long useTime = endTime - startTime;
			logger.debug("ProxyGet UseTime : " + useTime + "(ms)");
			logger.debug("ProxyGet URL : " + serviceUrl);
			logger.debug("ProxyGet Param : " + params);
		}
        catch(SocketTimeoutException ex) {
			
		} catch (IOException ex) {
        	
			logger.warn(ex.getMessage());
			
		} catch (Exception ex) {
			
			logger.warn(ex.getMessage());
		} finally {
			if(is != null)
				is.close();
			
			if(huc != null)
				huc.disconnect();
		}
		
		return result;
	}

	@Override
	public TopographicAnalysisResultDTO analysisProfile(String serviceUrl, TopographicAnalysisDTO analysisDTO) throws Exception {
		TopographicAnalysisResultDTO result = null;
		
		KwsTopographicMap layer = topographicMapService.selectOne(analysisDTO.getGroup(), analysisDTO.getLayer());
		
		HttpURLConnection huc = null;
        InputStream is = null;
		
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        
		long startTime = Calendar.getInstance().getTimeInMillis();

		try {
			List<ElevationDTO> rows = new ArrayList<ElevationDTO>();; 
			
			StringBuffer params = new StringBuffer();
			params.append("SERVICE=WPS&VERSION=1.0.0&REQUEST=Execute&LANGUGE=en&IDENTIFIER=Profile&");
			params.append("DATAINPUTS=layer=" + analysisDTO.getLayer());
			params.append(";width=" + analysisDTO.getWidth());
			params.append(";height=" + analysisDTO.getHeight());
			params.append(";bbox=" + analysisDTO.getMinX());
			params.append("," + analysisDTO.getMinY());
			params.append("," + analysisDTO.getMaxX());
			params.append("," + analysisDTO.getMaxY());
			params.append(";srs=" + analysisDTO.getSrs());
			params.append(";start=" + analysisDTO.getStartX());
			params.append("," + analysisDTO.getStartY());
			params.append(";end=" + analysisDTO.getEndX());
			params.append("," + analysisDTO.getEndY());
			params.append(";interval=" + analysisDTO.getInterval());
			params.append("&RAWDATAOUTPUT=profileresponse");
			
			URL url = new URL(serviceUrl.concat("?") + params);
			URLConnection connection = url.openConnection();
			huc = (HttpURLConnection)connection;
			huc.setRequestMethod("GET");
			huc.setDoOutput(true);
			huc.setDoInput(true);
			huc.setUseCaches(false);
			huc.setDefaultUseCaches(false);
			
			is = huc.getInputStream();
			
			objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
            doc = objDocumentBuilder.parse(is);
			
			result = new TopographicAnalysisResultDTO(); 
			result.setDataId(layer.getLayerName());
			result.setDataAlias(layer.getTitle());
			
			NodeList response = doc.getElementsByTagName("ProfileResponse");
            NodeList profileLayers = response.item(0).getChildNodes();
            
			for (int i = 0; i < profileLayers.getLength(); i++) {
				Node profileLayer = profileLayers.item(i);
				if (profileLayer.getNodeType() != Node.ELEMENT_NODE)
					continue;
				
				NamedNodeMap layerAttr = profileLayer.getAttributes();
				String name = layerAttr.getNamedItem("name").getNodeValue();
				if (!name.equalsIgnoreCase(layer.getLayerName()))
					continue;
				
				String srs = layerAttr.getNamedItem("srs").getNodeValue();
				String min = layerAttr.getNamedItem("min").getNodeValue();
				String max = layerAttr.getNamedItem("max").getNodeValue();
				
				result.setMin(Double.parseDouble(min));
				result.setMax(Double.parseDouble(max));
				result.setSrs(srs);
				
				NodeList datas = profileLayer.getChildNodes();
				for (int j = 0; j < datas.getLength(); j++) {
					Node data = datas.item(j);
					if (data.getNodeType() != Node.ELEMENT_NODE)
						continue;

					ElevationDTO row = new ElevationDTO();
					NodeList values = data.getChildNodes();
					for (int k = 0; k < values.getLength(); k++) {
						Node value = values.item(k);
						if (value.getNodeType() != Node.ELEMENT_NODE)
							continue;
					
						if (value.getNodeName().equalsIgnoreCase("Coordinate")){
							String coordinate = value.getTextContent();
							String[] pos = coordinate.split(" ");
							double x = Double.parseDouble(pos[0]);
							double y = Double.parseDouble(pos[1]);
							row.setX(x);
							row.setY(y);
						}
						else if (value.getNodeName().equalsIgnoreCase("Value")){
							String elevation = value.getTextContent();
							row.setElevation(Double.parseDouble(elevation));
						}
					}
					
					rows.add(row);
				}
			}
			
			result.setDatas(rows);
			
			long endTime = Calendar.getInstance().getTimeInMillis();
			long useTime = endTime - startTime;
			logger.debug("ProxyGet UseTime : " + useTime + "(ms)");
			logger.debug("ProxyGet URL : " + serviceUrl);
			logger.debug("ProxyGet Param : " + params);
		}
        catch(SocketTimeoutException ex) {
			
		} catch (IOException ex) {
        	
			logger.warn(ex.getMessage());
			
		} catch (Exception ex) {
			
			logger.warn(ex.getMessage());
		} finally {
			if(is != null)
				is.close();
			
			if(huc != null)
				huc.disconnect();
		}
		
		return result;
	}

}
