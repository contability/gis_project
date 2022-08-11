package kr.co.gitt.kmaps.tools.shutoffleakingpipe;

import java.io.StringWriter;
import java.net.URI;
import java.util.Date;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.deegree.framework.xml.XMLTools;
import org.deegree.ogcbase.CommonNamespaces;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.kmaps.framework.action.dataquery.DataRecords;
import com.kmaps.framework.common.Extent;
import com.kmaps.framework.common.ValueType;
import com.kmaps.framework.common.util.TypeConversion;
import com.kmaps.framework.metadata.Column;
import com.kmaps.framework.metadata.Columns;
import com.kmaps.framework.spatialdata.geometry.GeomFactory;
import com.kmaps.framework.spatialdata.geometry.IGeometry;
import com.kmaps.framework.spatialdata.gml.GMLFactory;
import com.kmaps.framework.spatialdata.gml.IGML;
import com.kmaps.framework.spatialdata.gml.IGMLWriter;

public class ClosedResult
{
	protected static final String WFS = CommonNamespaces.WFS_PREFIX + ":";
	
	protected static String servicePath;
	
	protected static String srsName;
	
	/**
	 * 파손지점
	 */
	protected static IGeometry leakPoint;
	
	/**
	 * 파이프 시설명 
	 */
	protected static String pipeName;

	/**
	 * 파이프 아이디 필드명
	 */
	protected static String pipeID;
	
	/**
	 * 파이프 공간 필드명
	 */
	protected static String pipeGeom;
	
	/**
	 * 폐쇄 파이프
	 */
	protected static Map<Long, IGeometry> closedPipes;
	
	protected static DataRecords closedPipesAttribute;

	/**
	 * 밸브 시설명 
	 */
	protected static String valveName;

	/**
	 * 밸브 아이디 필드명
	 */
	protected static String valveID;
	
	/**
	 * 밸브 공간 필드명
	 */
	protected static String valveGeom;
	
	/**
	 * 밸브
	 */
	protected static Map<Long, IGeometry> offValves;

	protected static DataRecords offValvesAttribute;
	
	
	public ClosedResult(IGeometry point, String pipeid, String pipegeom, Map<Long, IGeometry> pipes, DataRecords pipesAttribute, String valveid, String valvegeom, Map<Long, IGeometry> valves, DataRecords valvesAttribute)
	{
		leakPoint = point;
		pipeID = pipeid;
		pipeGeom = pipegeom;
		closedPipes = pipes;
		closedPipesAttribute = pipesAttribute;
		valveID = valveid;
		valveGeom = valvegeom;
		offValves = valves;
		offValvesAttribute = valvesAttribute;
	}
	
	public IGeometry getLeakPoint()
	{
		return leakPoint;
	}
	
	public Map<Long, IGeometry> getClosedPipes()
	{
		return closedPipes;
	}
	
	public Map<Long, IGeometry> getOffValves()
	{
		return offValves;
	}
	
	public void setServicePath(String path)
	{
		servicePath = path;
	}
	
	public void setSrsName(String srsname)
	{
		srsName = srsname;
	}
	
	public void setPipeName(String name)
	{
		pipeName = name;
	}

	public void setValveName(String name)
	{
		valveName = name;
	}
	
	private Element createFeatureCollection()
	{
		Document xmlDoc = XMLTools.create();
		xmlDoc.setXmlVersion("1.0");
		
		Element eleRoot = xmlDoc.createElementNS(CommonNamespaces.WFSNS.toString(), WFS + "FeatureCollection");
		XMLTools.appendNSBinding(eleRoot, CommonNamespaces.XSI_PREFIX, CommonNamespaces.XSINS);
		XMLTools.appendNSBinding(eleRoot, CommonNamespaces.GML_PREFIX, CommonNamespaces.GMLNS);
		XMLTools.appendNSBinding(eleRoot, CommonNamespaces.WFS_PREFIX, CommonNamespaces.WFSNS);
		XMLTools.appendNSBinding(eleRoot, CommonNamespaces.OGC_PREFIX, CommonNamespaces.OGCNS);

		eleRoot.setAttributeNS(CommonNamespaces.XSINS.toASCIIString(), "xsi:schemaLocation", "http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd");
		
		xmlDoc.importNode(eleRoot, false);
		
		int total = 0;
		String prefix = null;
		URI namespace = null;
		IGMLWriter writer = GMLFactory.createWriter(IGML.v311);
		
		Element eleTotalBoundedBy = null;
		Element eleFeatureMember = null;
		Element eleTableName = null;
		Element eleGeometry = null;
			
		String idField = null;
		String geomField = null;
		String featureId = null;
			
		boolean chkGeom = false;
		IGeometry geom = null;		
		Extent extent = null;
		Extent totalExtent = new Extent();
		
		int count = 0;
		String table[] = null;
		String datasetname = null;
		Column column = null;
		Columns fields = null;
		DataRecords recordset = null;
		Map<Long, IGeometry> geometrydatas = null;
		
		try
		{
			// total extent
			eleTotalBoundedBy = XMLTools.appendElement(eleRoot, CommonNamespaces.GMLNS, "gml:boundedBy");
			
			for(int i = 0; i < 2 ; i++)
			{
				if (srsName.contains("urn:ogc:def:crs:"))
					writer.setSRS(srsName);
				else
					writer.setSRS("urn:ogc:def:crs:" + srsName);
				
				if (i == 0)
				{
					recordset = closedPipesAttribute;
					geometrydatas = closedPipes;
					idField = pipeID;
					geomField = pipeGeom;
					table = pipeName.split(":");
				}
				else
				{
					recordset = offValvesAttribute;
					geometrydatas = offValves;
					idField = valveID;
					geomField = valveGeom;
					table = valveName.split(":");
				}

				if (table.length == 2)
				{
					prefix = table[0];
    				namespace = new URI(servicePath + "/" + prefix);
					datasetname = table[1];
					XMLTools.appendNSBinding(eleRoot, prefix, namespace);
				}
				else
				{
					namespace = null;
					datasetname = table[0];
				}
				
				while (recordset.next())
				{
					eleFeatureMember = XMLTools.appendElement(eleRoot, CommonNamespaces.GMLNS, "gml:featureMember");
					if (prefix == null)
						eleTableName = XMLTools.appendElement(eleFeatureMember, namespace, datasetname);
					else
						eleTableName = XMLTools.appendElement(eleFeatureMember, namespace, prefix + ":" + datasetname);
					
					featureId = recordset.getString(idField);
					if ((featureId != null) && !featureId.equals(""))
						eleTableName.setAttributeNS(CommonNamespaces.GMLNS.toASCIIString(), "gml:id", datasetname + "." + featureId);
						
					fields = recordset.getColumns();
					count = fields.size();
					
					for (int j = 0; j < count; j++)
					{
						chkGeom = false;
						column = fields.get(j);
						if (column.getName().equalsIgnoreCase(geomField))
						{
							geom = geometrydatas.get(Long.valueOf(featureId));
    						if (geom != null)
    						{
        						extent = geom.extent();
        						totalExtent.union(extent);
        						
       							if (namespace != null)
       								eleGeometry = XMLTools.appendElement(eleTableName, namespace, prefix + ":" + geomField);
       							else
       								eleGeometry = XMLTools.appendElement(eleTableName, namespace, geomField);
       							
        						writer.write(eleGeometry, geom);
        						chkGeom = true;
    						}
						}
						else if (!column.isSystemColumn() && column.getType() != ValueType.Geometry)
						{
							String value = recordset.getString(column.getName());
							if (value != null)
								if (namespace != null)
									XMLTools.appendElement(eleTableName, namespace, prefix + ":" + column.getName(), new String(value.getBytes("UTF-8"), "UTF-8"));
								else
									XMLTools.appendElement(eleTableName, namespace, column.getName(), new String(value.getBytes("UTF-8"), "UTF-8"));
						}
					}
					
					total++;
				}// End while : RecordSet
				
				recordset.clear();
    			recordset = null;
			}// End for : loop
			
			// feature member total extent
			if (eleTotalBoundedBy != null)
			{
				if (count <= 0 || !chkGeom)
					eleRoot.removeChild(eleTotalBoundedBy);
				else
					writer.write(eleTotalBoundedBy, GeomFactory.createEnvelope(totalExtent));
			}
			
			eleRoot.setAttribute("numberOfFeatures", String.valueOf(total));
			eleRoot.setAttribute("timeStamp", TypeConversion.toString("yyyy-MM-dd'T'HH:mm:ss", new Date()));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			closedPipesAttribute.clear();
			offValvesAttribute.clear();
		}
		
		writer.clear();
		
		return eleRoot;
	}
	
	public String toXmlString()
	{
		String xml = null;
		try
		{
			DOMSource domSource = new DOMSource(createFeatureCollection());
			
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "true");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.transform(domSource, result);
			
			xml = writer.toString();
		}
		catch (TransformerException ex)
		{
			return xml;
		}				
		
		return xml;
	}		
}
