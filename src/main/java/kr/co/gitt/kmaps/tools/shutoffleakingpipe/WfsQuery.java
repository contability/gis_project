package kr.co.gitt.kmaps.tools.shutoffleakingpipe;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.deegree.framework.xml.XMLTools;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.kmaps.framework.action.dataquery.DataRecords;
import com.kmaps.framework.common.ValueType;
import com.kmaps.framework.metadata.Column;
import com.kmaps.framework.metadata.Columns;
import com.kmaps.framework.spatialdata.geometry.IGeometry;
import com.kmaps.framework.spatialdata.gml.GMLFactory;
import com.kmaps.framework.spatialdata.gml.IGML;
import com.kmaps.framework.spatialdata.gml.IGMLReader;

public class WfsQuery
{
	protected String objectId;
	protected String geometry;

	private HttpClient connect;
	private String servicePath;
	
	public WfsQuery(String host, int port, String path)
	{
		connect = new HttpClient(host, port, 1000);
		servicePath = path;
	}
	
	public boolean open()
	{
		return connect.open();
	}
	
	public void close()
	{
		connect.close();
	}
	
	public void setObjectID(String name)
	{
		objectId = name;
	}
	
	public void setGeometry(String name)
	{
		geometry = name;
	}
	
	public DataRecords sendPost(String paramDataset, String paramRequest, boolean paramArcgis) throws Exception
	{
		Channel channel = null;
		if (connect != null)
			channel = connect.getChannel();

		if(channel == null || !channel.isConnected())
			throw new Exception("channel create.");
		
        // Prepare the HTTP request.
		ChannelBuffer buffer = ChannelBuffers.copiedBuffer(paramRequest, Charset.defaultCharset());
        HttpRequest post = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, servicePath);
        post.setContent(buffer);
        
        HttpHeaders headers = post.headers();
        headers.set(HttpHeaders.Names.HOST, connect.getHost());
        headers.set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        headers.set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);
		headers.set(HttpHeaders.Names.CONTENT_LENGTH, buffer.readableBytes());

        HttpClientHandler handler = ((HttpClientHandler)channel.getPipeline().get("handler"));
        try
        {
            channel.write(post).sync();
            while(!handler.isEnd())
            {
            	Thread.sleep(100);
            }
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        
        if (handler.getStatus() != 200)
        {
            channel.close();
            channel = null;
        	return null;
        }
        
        String response = handler.getResponse();
        channel.close();
        channel = null;
        
        if (response == null)
        	return null;
		
//		Columns cols = new Columns();
//		cols.add(new Column(paramDataset, paramIdfield, ValueType.Long));
//		cols.add(new Column(paramDataset, paramGeomfield, ValueType.Geometry));

		DataRecords result = new DataRecords();
//		result.setColumns(cols);
		
		readFeatureCollection(paramDataset, result, response, paramArcgis);
		
		result.moveTo(-1);
		return result;
	}
	
	private void readFeatureCollection(String paramDataset, DataRecords paramRecords, String paramXml, boolean paramArcgis)
	{
		if (paramXml.startsWith("<?"))
			paramXml = paramXml.substring(paramXml.indexOf("?>")+2).trim();
		
		if (paramArcgis)
		{
			boolean chk = false;
			StringBuilder sb = new StringBuilder();
			int idx = paramXml.indexOf("xsi:schemaLocation='");
			int start = 0;
			int end = 0;
			for (int i = 0; i < paramXml.length(); i++)
			{
				if (i >= idx)
				{
					if (paramXml.substring(i, i+1).equals("'"))
					{
						if (!chk)
						{
							chk = true;
							start = i;
						}
						else
						{
							chk = false;
							end = i;
							break;
						}
					}
				}
			}
			
			sb.append(paramXml.substring(0, start+1));
			sb.append(paramXml.substring(end, paramXml.length()));
			paramXml = sb.toString();
		}
		
		Document xmlDoc = XMLTools.create();
		Element eleRoot = XMLTools.importStringFragment(paramXml, xmlDoc);		
		
		Node node = null;
		NodeList nodelist = eleRoot.getChildNodes();
		int length = nodelist.getLength();
		String name = null;
		
		for (int i = 0; i < length; i++)
		{
			node = nodelist.item(i);
			name = node.getLocalName();
			if (name == null)
				continue;
			
			if (name.equals("featureMember"))
				parsingFeatureMember(paramDataset, paramRecords, node, paramArcgis);
		}
	}
	
	private void parsingFeatureMember(String paramDataset, DataRecords paramRecords, Node paramNode, boolean paramArcgis)
	{
		Node node = null;
		NodeList nodelist = paramNode.getChildNodes();
		int length = nodelist.getLength();
		String name = null;
		
		for (int i = 0; i < length; i++)
		{
			node = nodelist.item(i);
			name = node.getLocalName();
			if (name == null)
				continue;
			
			if (name.equals(paramDataset))
				parsingRecord(paramDataset, paramRecords, node, paramArcgis);
		}
		
	}
	
	private void parsingRecord(String paramDataset, DataRecords paramRecords, Node paramNode, boolean paramArcgis)
	{
		Column col = null;
		Columns columns = new Columns();//paramRecords.getColumns();
		Node node = null;
		NodeList nodelist = paramNode.getChildNodes();
		int length = nodelist.getLength();
		String name = null;
		int index = 0;
		//Object[] values = new Object[columns.size()];
		List<Object> values = new ArrayList<Object>();
		
		for (int i = 0; i < length; i++)
		{
			node = nodelist.item(i);
			name = node.getLocalName();
			if (name == null)
				continue;
			
			if (!columns.contains(name))
			{
				if (name.equalsIgnoreCase(objectId))
					col = new Column(paramDataset, name, ValueType.Long);
				else if (name.equalsIgnoreCase(geometry))
					col = new Column(paramDataset, name, ValueType.Geometry);
				else
					col = new Column(paramDataset, name, ValueType.String);
				
				columns.add(col);
				if (col.getType() == ValueType.Geometry)
					values.add(parsingGML(node));
				else
					values.add(XMLTools.getStringValue(node));
			}
			else
			{
				col = columns.get(name);
				index = columns.indexOf(col);
				if (col.getType() == ValueType.Geometry)
				{
					//values[index] = parsingGML(node);
					values.set(index, parsingGML(node));
				}
				else
				{
					//values[index] = XMLTools.getStringValue(node);
					values.set(index, XMLTools.getStringValue(node));
				}
			}
		}
		
		if (!paramArcgis)
		{
			String value = ((Element)paramNode).getAttribute("gml:id");
			String[] id = value.split("\\.");
			
			col = columns.get(objectId);
			if (col == null)
			{
				col = new Column(paramDataset, objectId, ValueType.Long);
				columns.add(col);
				
				values.add(id[1]);
			}
			else
			{
				index = columns.indexOf(col);
				//values[index] = id[1];
				values.set(index, id[1]);
			}
		}
		
		if (paramRecords.getColumns() == null || paramRecords.getColumns().size() <= 0)
			paramRecords.setColumns(columns);
		
		Object[] valueArray = values.toArray(new Object[values.size()]);
		paramRecords.addValues(valueArray);
	}
	
	private IGeometry parsingGML(Node paramNode)
	{
		IGMLReader reader = GMLFactory.createReader(IGML.v311);
		
		IGeometry geometry = null;
		try
		{
			geometry = reader.read((Element) paramNode.getFirstChild());
		} 
		catch (Exception ex)
		{
			throw new IllegalArgumentException("can not parsing GML : " + paramNode);
		}
		
		return geometry;
		
	}
}
