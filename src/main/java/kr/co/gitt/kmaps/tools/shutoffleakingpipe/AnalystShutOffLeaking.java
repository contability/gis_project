package kr.co.gitt.kmaps.tools.shutoffleakingpipe;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.deegree.framework.xml.XMLTools;
import org.deegree.ogcbase.CommonNamespaces;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.kmaps.framework.action.dataquery.DataRecords;
import com.kmaps.framework.common.Coordinate;
import com.kmaps.framework.common.Coordinates;
import com.kmaps.framework.spatialdata.filter.ComparisonOperator;
import com.kmaps.framework.spatialdata.filter.Filter;
import com.kmaps.framework.spatialdata.filter.IFilter;
import com.kmaps.framework.spatialdata.filter.LogicalOperator;
import com.kmaps.framework.spatialdata.filter.OgcFilterMaker;
import com.kmaps.framework.spatialdata.filter.PropertyName;
import com.kmaps.framework.spatialdata.filter.SpatialOperator;
import com.kmaps.framework.spatialdata.geometry.GeomFactory;
import com.kmaps.framework.spatialdata.geometry.GeomPolygon;
import com.kmaps.framework.spatialdata.geometry.IGeometry;

public class AnalystShutOffLeaking
{
	/**
	 * 입력 좌표계
	 */
	protected static String paramSrsName;
	
	/**
	 * 파이프 시설명 
	 */
	protected static String paramPipeName;
	
	/**
	 * 파이프 아이디 필드명
	 */
	protected static String paramPipeID;
	
	/**
	 * 파이프 공간 필드명
	 */
	protected static String paramPipeGeom;
	
	/**
	 * 연결 파이프 검색 버퍼거리 (미터)
	 */
	protected static double paramPipeSearchDist;
	
	/**
	 * 밸브 시설명 
	 */
	protected static String paramValveName;
	
	/**
	 * 밸브 아이디 필드명
	 */
	protected static String paramValveID;
	
	/**
	 * 밸브 공간 필드명
	 */
	protected static String paramValveGeom;
	
	/**
	 * 밸브 코드 필드명
	 */
	protected static String paramValveCodeField;
	
	/**
	 * 밸브 코드 값
	 */
	protected static String paramValveCodeValue;
	
	/**
	 * 연결 밸프 검색 버퍼거리 (미터)
	 */
	protected static double paramValveSearchDist;
	
	/**
	 * 파이프 파손지점
	 */
	protected static IGeometry paramLeakPoint;
	
	/**
	 * 누수파이프 검색 버퍼거리 (미터)
	 */
	protected static double paramLeakSearchDist;
	
	/**
	 * ArcGis 
	 */
	protected static boolean paramArcGis;
	
	/**
	 * 탐색한 파이프
	 */
	protected static Map<Long, IGeometry> searchPipe;
	
	/**
	 * 결과 파이프
	 */
	protected static Map<Long, IGeometry> resultPipe;

	protected static DataRecords resultPipeRecords;

	/**
	 * 결과 밸브
	 */
	protected static Map<Long, IGeometry> resultValve;

	protected static DataRecords resultValveRecords;
	

	public AnalystShutOffLeaking()
	{
		paramSrsName = "EPSG:5187";
		
		paramPipeName = "WTL_PIPE_LM";
		paramPipeID = "OBJECTID";
		paramPipeGeom = "GEOM";
//		paramPipeGeom = "SHAPE";	// ArcGis
		
		paramValveName = "WTL_VALV_PS";
		paramValveID = "OBJECTID";
		paramValveGeom = "GEOM";
//		paramValveGeom = "SHAPE";	// ArcGis
		paramValveCodeField = "FTR_CDE";
		paramValveCodeValue =  "SA200";
		
		paramLeakSearchDist = 0.5;	// 50 cm
		paramPipeSearchDist = 0.01;	// 1 cm
		paramValveSearchDist = 0.01;// 1 cm
		
		paramArcGis = false;
//		paramArcGis = true;	// ArcGis
		
		searchPipe = new HashMap<Long, IGeometry>();
		resultPipe = new HashMap<Long, IGeometry>();
		resultPipeRecords = new DataRecords();
		resultValve = new HashMap<Long, IGeometry>();
		resultValveRecords = new DataRecords();
	}
	
	/**
	 * @param paramArc (boolean) Arc=true, KMap=false
	 * @param buf (double) - 좌표의 버퍼 (5= 500 cm)
	 * @param paramGeom (String) Arc=SHAPE, KMap=GEOM
	 * @param paramSrs (String) 과천=EPSG:5186, 동해=EPSG:5187
	 * @return 
	 */
	public AnalystShutOffLeaking(boolean paramArc, double buf, String paramGeom, String paramSrs)
	{
		paramSrsName = paramSrs;
		
		paramPipeName = "WTL_PIPE_LM";
		paramPipeID = "OBJECTID";
		paramPipeGeom = paramGeom;
		
		paramValveName = "WTL_VALV_PS";
		paramValveID = "OBJECTID";
		paramValveGeom = paramGeom;
		paramValveCodeField = "FTR_CDE";
		paramValveCodeValue =  "SA200";
		
		paramLeakSearchDist = buf;
//		paramPipeSearchDist = buf/10;
//		paramValveSearchDist = buf/10;
		paramPipeSearchDist = buf/50;
		paramValveSearchDist = buf/50;

		paramArcGis = paramArc;
		
		searchPipe = new HashMap<Long, IGeometry>();
		resultPipe = new HashMap<Long, IGeometry>();
		resultPipeRecords = new DataRecords();
		resultValve = new HashMap<Long, IGeometry>();
		resultValveRecords = new DataRecords();
	}
	
	/**
	 * @param x (double) - 직각좌표 X
	 * @param y (double) - 직각좌표 Y
	 * @param ip (String) - IP address
	 * @param port (int) - port
	 * @param url  (String) - WFS service url
	 * @return 
	 */
	public String execute(double x, double y, String ip, int port, String url)
	{
/*
		// 서비스 주소
//		String ip = "192.168.0.21";
		String ip = "192.168.0.124";
		int port = 8991;
//		int port = 60800;
//		String url = "http://211.170.66.21:60800/arcgis/services/gcgis/MapServer/WFSServer";
		String url = "http://192.168.0.124:8991/OGCService";
*/
		WfsQuery query = new WfsQuery(ip, port, url);

		query.setObjectID(paramPipeID);
		query.setGeometry(paramPipeGeom);

		//execute(208831.08, 549668.13, ip, port, url, 1, false, "GEOM", "EPSG:5187");
			
		try
		{
			///////////////////////////////////////////////////////////////////////
			// Step 1 - 입력된 누수지점으로 누수 파이프 검색
			///////////////////////////////////////////////////////////////////////
			
			// 누수지점
			IGeometry point = GeomFactory.createPoint(new Coordinate(x, y));
			// 누수 파이프를 검색하기 위해 누수지점을 버퍼
			IGeometry searchArea = point.buffer(paramLeakSearchDist);
			searchArea.setSRSName(paramSrsName);
			
			// GetFeature request 만들기
			String request = createRequest(paramPipeName, paramPipeID, paramPipeGeom, searchArea, null, null);
			
			// Send request
//			if (!query.connect())
			if (!query.open())
			{
				return "kmapNoOpen";
			}
			
			DataRecords response = new DataRecords();

			try
			{
				response = query.sendPost(paramPipeName, request, paramArcGis);
			}
			catch (Exception e)
			{
				return "kmapNoOpen";
			}
			
			if (response == null)
			{
				return "noResponse";
			}
			
			query.close();
			
			if(response.getColumns() == null)
			{
				return "noPipe";
			}

			// 검색된 파이프 중 가장 인접한 파이프를 누수파이프로 선정한다.
			resultPipeRecords.setColumns(response.getColumns());
			
			double dist = -1;
			double minDist = -1;
			IGeometry geom = null;
			IGeometry leakpipe = null;
			Object[] attribute = null;
			long leakid = -1;
			
			while (response.next())
			{
				geom = (IGeometry) response.getValue(paramPipeGeom);
				dist = geom.distance(point);
				
				if (minDist <= -1)
				{
					minDist = dist;
					leakpipe = geom;
					leakid = Long.valueOf((String) response.getValue(paramPipeID));
					attribute = response.getValues();
				}
				else if (minDist < dist)
				{
					minDist = dist;
					leakpipe = geom;
					leakid = Long.valueOf((String) response.getValue(paramPipeID));
					attribute = response.getValues();
				}
			}
			
			if (leakpipe == null)
				return null;

			// 파이프의 누수지점
			paramLeakPoint = nearPoint(leakpipe, point);
			
			if (paramLeakPoint != null)
			{
				paramLeakPoint.setSRSName(paramSrsName);
			}
			
			///////////////////////////////////////////////////////////////////////
			// Step 2 - 파이프가 선정되면 검색을 시작한다.
			///////////////////////////////////////////////////////////////////////
			
			findShutOffValve(query, leakid, leakpipe, attribute, null);
			
			ClosedResult result = new ClosedResult(paramLeakPoint, paramPipeID, paramPipeGeom, resultPipe, resultPipeRecords, paramValveID, paramValveGeom, resultValve, resultValveRecords);
			result.setServicePath(url);
			result.setSrsName(paramSrsName);
			result.setPipeName(paramPipeName);
			result.setValveName(paramValveName);
			
			return result.toXmlString();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		ClosedResult result = new ClosedResult(paramLeakPoint, paramPipeID, paramPipeGeom, resultPipe, resultPipeRecords, paramValveID, paramValveGeom, resultValve, resultValveRecords);

		result.setServicePath(url);
		result.setSrsName(paramSrsName);
		
		return result.toXmlString();
	}

	/**
	 * GetFeature Request를 생성합니다.
	 * @param dataset - 검색대상 시설물명
	 * @param idfield - 아이디 필드
	 * @param geomfield - 공간 필드
	 * @param search - 검색영역
	 * @param codefield - 코드 필드
	 * @param codevalue - 코드값
	 * @return 검색 결과를 반환
	 * @throws Exception
	 */
	private static String createRequest(String dataset, String idfield, String geomfield, IGeometry search, String codefield, String codevalue) throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		factory.setNamespaceAware(true);
		factory.setExpandEntityReferences(false);
		factory.setIgnoringElementContentWhitespace(false);
		factory.setValidating(false);

		Document xmlDoc = factory.newDocumentBuilder().newDocument();

		Element eleRoot = xmlDoc.createElementNS(CommonNamespaces.WFSNS.toASCIIString(), CommonNamespaces.WFS_PREFIX + ":GetFeature");
		XMLTools.appendNSBinding(eleRoot, CommonNamespaces.WFS_PREFIX, CommonNamespaces.WFSNS);
		XMLTools.appendNSBinding(eleRoot, CommonNamespaces.OGC_PREFIX, CommonNamespaces.OGCNS);
		XMLTools.appendNSBinding(eleRoot, CommonNamespaces.XSI_PREFIX, CommonNamespaces.XSINS);
		
		eleRoot.setAttributeNS(CommonNamespaces.XSINS.toASCIIString(), CommonNamespaces.XSI_PREFIX + ":schemaLocation", "http://www.opengis.net/wfs ../wfs/1.1.0/WFS.xsd");
		xmlDoc.importNode(eleRoot, false);

		eleRoot.setAttribute("service", "WFS");
		eleRoot.setAttribute("version", "1.1.0");
		eleRoot.setAttribute("outputFormat", "text/xml; subtype=gml/3.1.1");
		
		Element eleQuery = XMLTools.appendElement(eleRoot, CommonNamespaces.WFSNS, CommonNamespaces.WFS_PREFIX + ":Query");
		eleQuery.setAttribute("typeName", dataset);
		
//		XMLTools.appendElement(eleQuery, CommonNamespaces.WFSNS, CommonNamespaces.WFS_PREFIX + ":PropertyName", idfield);
//		XMLTools.appendElement(eleQuery, CommonNamespaces.WFSNS, CommonNamespaces.WFS_PREFIX + ":PropertyName", geomfield);
		
		SpatialOperator so = new SpatialOperator();
		so.setSpatial(geomfield);
		so.defineIntersects(search);
		
		IFilter filter = new Filter();

		if (codefield != null)
		{
			LogicalOperator lo = new LogicalOperator(LogicalOperator.LOGICAL_AND);
			ComparisonOperator co = new ComparisonOperator();
			PropertyName property = new PropertyName();
			property.setPropertyName(codefield);
			co.setExpression(property);
			co.defineEqual(codevalue);
			
			lo.addOperation(so);
			lo.addOperation(co);
			
			filter.setOperator(lo);
		}
		else
		{
			filter.setOperator(so);
		}

		OgcFilterMaker.createFilter(eleQuery, filter);

		DOMSource domSource = new DOMSource(eleRoot);

		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "true");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.transform(domSource, result);
		
		String strXml = writer.toString();		
		
		return strXml;
	}
	
	/**
	 * 잠가야하는 밸브와 차단되는 파이프를 검색
	 * @param paramQuery - 검색 서비스
	 * @param paramID - 검색 파이프 아이디
	 * @param paramPipe - 검색 파이프
	 * @param paramAttribute - 검색 파이프 속성데이터
	 * @param paramLinkedPipe - 연결 파이프
	 */
	private static void findShutOffValve(WfsQuery paramQuery, long paramID, IGeometry paramPipe, Object[] paramAttribute, IGeometry paramLinkedPipe)
	{
		// 이미 조회된 파이프인지 확인
		if (searchPipe.containsKey(paramID))
		{
			return;
		}
		else
		{
			searchPipe.put(paramID, paramPipe);	// 추가
		}
		
		try
		{
			///////////////////////////////////////////////////////////////////////
			//
			// Step 3 - 파이프와 연결된 밸브가 있는지 검색한다.
			//
			
			// 밸브를 검색하기 위해서 파이프를 버퍼처리
			IGeometry searchArea = paramPipe.buffer(paramValveSearchDist);
			searchArea.setSRSName(paramSrsName);
			
			// GetFeature request 만들기
			String request = createRequest(paramValveName, paramValveID, paramValveGeom, searchArea, paramValveCodeField, paramValveCodeValue);
			
			// Send request
//			if (!paramQuery.connect())
			if (!paramQuery.open())
				return;
			
			paramQuery.setObjectID(paramValveID);
			paramQuery.setGeometry(paramValveGeom);
			DataRecords response = paramQuery.sendPost(paramValveName, request, paramArcGis);

			paramQuery.close();
			
			IGeometry pipe = null;

			if (response != null)
			{	
				if (resultValveRecords.getColumns() == null || resultValveRecords.getColumns().size() <= 0)
					if (response.getColumns() != null && response.getColumns().size() > 0)
						resultValveRecords.setColumns(response.getColumns());
				
				boolean checkValve = false;
				long valveid = -1;
				IGeometry valve = null;
				Map<Long, IGeometry> offvalves = new HashMap<Long, IGeometry>();
				Map<Long, Object[]> attribute = new HashMap<Long, Object[]>();
				
				while (response.next())
				{
					valveid = Long.valueOf((String) response.getValue(paramValveID));
					valve = (IGeometry) response.getValue(paramValveGeom);
					if (resultValve.containsKey(valveid))
					{	// 이미 검색된 밸브이면 패스
						checkValve = true;
						continue;
					}
					
					offvalves.put(valveid, valve);
					attribute.put(valveid, response.getValues());
				}
				
				if (offvalves.size() > 0)
				{// 잠가야하는 밸브가 있으면 파이프를 잘라서 저장
					pipe = closedPipe(paramLinkedPipe, paramPipe, offvalves);
					if (pipe != null)
					{
						resultPipe.put(paramID, pipe);
						resultPipeRecords.addValues(paramAttribute);
					}
				}
				else if (!checkValve)
				{// 밸브가 없는 파이프는 연결 파이프이므로 저장
					pipe = paramPipe;
					resultPipe.put(paramID, pipe);
					resultPipeRecords.addValues(paramAttribute);
				}
				
				if (pipe == null)
					return;// 폐쇄되는 파이프가 없으면 검색 중단

				Set<Long> keys = offvalves.keySet();
				for (Long key : keys)
				{// 후보 밸브 중 폐쇄된 파이프와 연결된 밸브만 저장 
					valve = offvalves.get(key);
					if (pipe.intersects(valve) || pipe.touches(valve) || pipe.distance(valve) < paramValveSearchDist)
					{
						resultValve.put(key, valve);
						resultValveRecords.addValues(attribute.get(key));
					}
				}
			}
			
			///////////////////////////////////////////////////////////////////////
			//
			// Step 4 - 파이프와 연결된 파이프를 검색한다.
			//
			
			// 파이프의 시점과 종점을 버퍼링
			boolean chkStart = false;
			boolean chkEnd = false;
			IGeometry start = GeomFactory.createPoint(pipe.getCoordinateN(0)).buffer(paramPipeSearchDist);
			start.setSRSName(paramSrsName);
			IGeometry end = GeomFactory.createPoint(pipe.getCoordinateN(pipe.getNumPoints()-1)).buffer(paramPipeSearchDist);
			end.setSRSName(paramSrsName);
			
			Set<Long> keys = resultValve.keySet();
			for (Long key : keys)
			{
				if (start.contains(resultValve.get(key)))
					chkStart = true;
				
				if (end.contains(resultValve.get(key)))
					chkEnd = true;	
			}

			if (!chkStart && !chkEnd)
			{// 양방으로 검색
				GeomPolygon[] buffer = new GeomPolygon[2];
				buffer[0] = (GeomPolygon) start;
				buffer[1] = (GeomPolygon) end;
				
				searchArea = GeomFactory.createMultiPolygon(buffer);
				searchArea.setSRSName(paramSrsName);
			}
			else if (!chkStart && chkEnd)
			{// 시점 방향으로 검색
				searchArea = start;
			}
			else if (chkStart && !chkEnd)
			{// 종점 방향으로 검색
				searchArea = end;
			}
			else
			{// 시점과 종점이 모두 밸브위치와 같으면 폐쇄되었으므로 연결 파이프를 검색하지 않는다.
				return;
			}
			
			// GetFeature request 만들기
			request = createRequest(paramPipeName, paramPipeID, paramPipeGeom, searchArea, null, null);
			
			// Send request
//			if (!paramQuery.connect())
			if (!paramQuery.open())
				return;
			
			paramQuery.setObjectID(paramPipeID);
			paramQuery.setGeometry(paramPipeGeom);
			response = paramQuery.sendPost(paramPipeName, request, paramArcGis);
			
			paramQuery.close();
			
			if (response != null)
			{
				long pipeid = -1;
				IGeometry pipegeom = null;
				
				while (response.next())
				{
					pipeid = Long.valueOf((String) response.getValue(paramPipeID));
					pipegeom = (IGeometry) response.getValue(paramPipeGeom);
					
					// Step.3 ~ 4 를 반복해서 수행한다.
					findShutOffValve(paramQuery, pipeid, pipegeom, response.getValues(), pipe);
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * 밸브에의해 차단되는 파이프 구간을 연산.
	 * @param paramLinkedPipe - 연결 파이프
	 * @param paramPipe - 파이프
	 * @param paramValves - 밸브
	 * @return
	 */
	private static IGeometry closedPipe(IGeometry paramLinkedPipe, IGeometry paramPipe, Map<Long, IGeometry> paramValves)
	{
		IGeometry closedPipe = null;
		
		IGeometry valve = null;
		IGeometry crossPoint = null;
		IGeometry clipPipe = paramPipe;
		List<IGeometry> splitPipe = null;
		
		double distance = clipPipe.length();
		Set<Long> keys = paramValves.keySet();
		//int size = paramValves.size();
		//for (int i = 0; i < size; i++)
		for (Long key : keys)
		{
			valve = paramValves.get(key);
			crossPoint = nearPoint(clipPipe, valve);
			if (crossPoint == null)
				continue;
			
			// 파이프와 밸브의 교차점을 구하여 분할한다.
			splitPipe = splitPipe(clipPipe.getCoordinates(), crossPoint.getCoordinate());
			if (splitPipe.size() > 0)
			{
				for (IGeometry pipe : splitPipe)
				{
					if (paramLinkedPipe == null)
					{// 연결 파이프가 없으면 최초의 누수 파이프
						if (paramLeakPoint.intersects(pipe) || paramLeakPoint.touches(pipe) || paramLeakPoint.distance(pipe) < paramPipeSearchDist)
						{// 누수 지점을 포함한 파이프를 선택한다.
//							if (valve.distance(paramLeakPoint) < distance)
							if (pipe.length() <= distance)
							{
								distance = pipe.length();//valve.distance(paramLeakPoint);
								clipPipe = pipe;
							}
						}
					}
					else
					{// 연결 파이프와 연결된 파이프를 선택 
						if (paramLinkedPipe.intersects(pipe) || paramLinkedPipe.touches(pipe) || paramLinkedPipe.distance(pipe) < paramPipeSearchDist)
							clipPipe = pipe;
					}
				}
			}
		}
		
		if (clipPipe != null && !clipPipe.isEmpty())
			closedPipe = clipPipe;
		
		return closedPipe;
	}

	/**
	 * 파이프를 분할.
	 * @param paramPoints - 파이프 정점
	 * @param paramPosition - 분할 위치
	 * @return
	 */
	private static List<IGeometry> splitPipe(Coordinates paramPoints, Coordinate paramPosition)
	{
        int size = paramPoints.size();
		List<IGeometry> result = new ArrayList<IGeometry>();
		
		if (paramPoints.get(0).distance(paramPosition) == 0 || paramPoints.get(size - 1).distance(paramPosition) == 0)
        {
			result.add(GeomFactory.createLineString(paramPoints));
            return result;
        }
		
        boolean check = false;
        Map<String, Object> lineDist = null;
        Coordinates splitPipe = new Coordinates();
        Coordinate pointStart = null;
        Coordinate pointEnd = null;
        double dist = 0.0;

        for (int i = 0; i < size - 1; i++)
        {
            pointStart = paramPoints.get(i);
            pointEnd = paramPoints.get(i + 1);
            splitPipe.add(pointStart);

            if (!check)
            {
                lineDist = lineDistance(paramPosition, pointStart, pointEnd);
                dist = (Double) lineDist.get("dist");
                
                if (dist <= 0.1)
                {
                    check = true;
                    splitPipe.add(new Coordinate(paramPosition));
                    result.add(GeomFactory.createLineString(splitPipe));

                    splitPipe = new Coordinates();

                    if (paramPosition.getX() != pointEnd.getX() || paramPosition.getY() != pointEnd.getY())
                        splitPipe.add(new Coordinate(paramPosition));
                }
            }
        }

        splitPipe.add(new Coordinate(paramPoints.get(size - 1)));
        result.add(GeomFactory.createLineString(splitPipe));

        return result;
	}
	
	/**
	 * 직선과 점의 거리
	 * @param paramPoint - 점
	 * @param paramStart - 직선 시작점
	 * @param paramEnd - 직선 끝점
	 * @return
	 */
    public static Map<String, Object> lineDistance(Coordinate paramPoint, Coordinate paramStart, Coordinate paramEnd)
    {
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	double deltaX1 = 0.0;
    	double deltaY1 = 0.0;
    	double deltaX2 = 0.0;
    	double deltaY2 = 0.0;
    	double deltaX3 = 0.0;
    	double deltaY3 = 0.0;
    	
        double value1 = 0.0;
        double value2 = 0.0;
        double value3 = 0.0;
        double value4 = 0.0;

        double startX = paramStart.getX();
        double startY = paramStart.getY();
        double endX = paramEnd.getX();
        double endY = paramEnd.getY();
        double pointX = paramPoint.getX();
        double pointY = paramPoint.getY();

        if (pointX == startX && pointY == startY)
        {// 시작점과 같으면
    		result.put("state", 1);
    		result.put("dist", 0.0);
            
	        return result;
        }
        else if (pointX == endX && pointY == endY)
        {// 끝점과 같으면
    		result.put("state", 1);
    		result.put("dist", 0.0);
    		
	        return result;
        }
        
        deltaX1 = endX - startX;
        deltaY1 = endY - startY;
        deltaX2 = pointX - startX;
        deltaY2 = pointY - startY;

        value2 = deltaX1 * deltaX2 + deltaY1 * deltaY2;

        if (value2 < 0.0)
        {
    		result.put("state", -1);
    		result.put("dist", Math.hypot(deltaX2, deltaY2));
        }
        else
        {
            deltaX3 = pointX - endX;
            deltaY3 = pointY - endY;

            value2 = deltaX1 * deltaX3 + deltaY1 * deltaY3;

            if (value2 > 0.0)
            {// 선분에 존재
        		result.put("state", 1);
        		result.put("dist", Math.hypot(deltaX3, deltaY3));
            }
            else
            {// 선분 밖에 존재
                value1 = deltaX1 * startY - deltaY1 * startX;
                value3 = Math.abs(deltaY1 * pointX - deltaX1 * pointY + value1);
                value4 = deltaX1 * deltaX1 + deltaY1 * deltaY1;
                
        		result.put("state", 0);
        		result.put("dist", value3 / Math.sqrt(value4));
            }
        }

        return result;
    }	
	
	/**
	 * 인접점을 계산
	 * @param paramPipe - 파이프
	 * @param paramValve - 밸브
	 * @return
	 */
	private static IGeometry nearPoint(IGeometry paramPipe, IGeometry paramValve)
	{
		Coordinate valve = paramValve.getCoordinate();
		Coordinates points = paramPipe.getCoordinates();
		
        if (points.size() == 1)
	        return null;
        
        double dist = 0.0;
        double length = paramPipe.length();
        Coordinate resultPoint = null;

        try
        {
        	Map<String, Object> result = null;
	        int count = points.size();
	        for (int i = 0; i < (count - 1); i++)
	        {
	        	result = nearPoint(valve, points.get(i), points.get(i+1));
	        	
		        if (((Integer)result.get("state")) >= 0 && ((Integer)result.get("state")) <= 2)
		        {
		        	dist = (Double)result.get("dist");
			        if (dist <= length)
			        {
				        length = dist;
				        resultPoint = (Coordinate) result.get("coord");
			        }
		        }
	        }
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
        }
        
        if (resultPoint == null)
        	return null;
        
        
        return GeomFactory.createPoint(resultPoint);
	}
	
	/**
	 * 인접점을 계산
	 * @param paramValve - 밸브
	 * @param paramStart - 시작점
	 * @param paramEnd - 끝점
	 * @return
	 */
    private static Map<String, Object> nearPoint(Coordinate paramValve, Coordinate paramStart, Coordinate paramEnd)
    {
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	double valveX = paramValve.getX();
    	double valveY = paramValve.getY();
    	double startX = paramStart.getX();
    	double startY = paramStart.getY();
    	double endX = paramEnd.getX();
    	double endY = paramEnd.getY();
    	
    	double deltaX = endX - startX;
    	double deltaY = endY - startY;

        try
        {
	    	if (deltaX==0 && deltaY==0)
	    	{// 시작점과 끝점이 동일
	    		deltaX = valveX - startX;
	    		deltaY = valveY - startY;
	
	    		result.put("state", -1);
	    		result.put("coord", new Coordinate(startX, startY));
	    		result.put("dist", Math.sqrt((double)(deltaX * deltaX + deltaY * deltaY)));
	    		
	    		return result;
	    	}
	
	    	double state = (double)((valveX - startX) * deltaX + (valveY - startY) * deltaY) / (double)(deltaX * deltaX + deltaY * deltaY);
	
	    	if (state < 0)
	    	{// 시작점 밖에 점이 존재
	    		deltaX = valveX - startX;
	    		deltaY = valveY - startY;
	    		
	    		result.put("state", 0);
	    		result.put("coord", new Coordinate(startX, startY));
	    		result.put("dist", Math.sqrt((double)(deltaX * deltaX + deltaY * deltaY)));
	    	}
	    	else if (state > 1)
	    	{// 끝점 밖에 점이 존재
	    		deltaX = valveX - endX;
	    		deltaY = valveY - endY;
	    		
	    		result.put("state", 2);
	    		result.put("coord", new Coordinate(endX, endY));
	    		result.put("dist", Math.sqrt((double)(deltaX * deltaX + deltaY * deltaY)));
	    	}
	    	else
	    	{// 시작점과 끝점 사이에 점이 존재
	    		double nearX = startX + state * deltaX;
	    		double nearY = startY + state * deltaY;
	    		deltaX = valveX - nearX;
	    		deltaY = valveY - nearY;
	    		
	    		result.put("state", 1);
	    		result.put("coord", new Coordinate(nearX, nearY));
	    		result.put("dist", Math.sqrt((double)(deltaX * deltaX + deltaY * deltaY)));
	    	}
        }
        catch (Exception ex)
        {
        }
        
        return result;
    }
}
