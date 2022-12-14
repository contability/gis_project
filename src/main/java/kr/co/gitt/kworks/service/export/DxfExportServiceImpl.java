package kr.co.gitt.kworks.service.export;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.spatial.FilterFidsDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO.FilterType;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import kr.co.gitt.kworks.service.spatial.SpatialSearchService;

import org.apache.commons.lang.StringUtils;
import org.h2.store.fs.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.kmaps.framework.action.coordinateconvert.SRSTransform;
import com.kmaps.framework.common.Coordinate;
import com.kmaps.framework.common.Coordinates;
import com.kmaps.framework.common.Extent;
import com.kmaps.framework.common.SpatialType;
import com.kmaps.framework.library.dxf.ColorIndex;
import com.kmaps.framework.library.dxf.DxfEntities;
import com.kmaps.framework.library.dxf.DxfEntity;
import com.kmaps.framework.library.dxf.DxfEntityPoint;
import com.kmaps.framework.library.dxf.DxfEntityPolyline;
import com.kmaps.framework.library.dxf.DxfEntityText;
import com.kmaps.framework.library.dxf.DxfEntityVertex;
import com.kmaps.framework.library.dxf.DxfFile;
import com.kmaps.framework.library.dxf.DxfLayer;
import com.kmaps.framework.library.dxf.EntityObject;
import com.kmaps.framework.library.dxf.JustificationType;
import com.kmaps.framework.library.dxf.RandomColor;
import com.kmaps.framework.library.dxf.SectionType;
import com.kmaps.framework.library.shp.ShapeGeom;
import com.kmaps.framework.reference.ISpatialReferenceSystem;
import com.kmaps.framework.reference.initialize.DefaultSRS;
import com.kmaps.framework.spatialdata.geometry.GeomMultiPolygon;
import com.kmaps.framework.spatialdata.geometry.GeomPolygon;
import com.kmaps.framework.spatialdata.geometry.IGeometry;
import com.kmaps.framework.spatialdata.geometry.io.WKTReader;

import egovframework.rte.psl.dataaccess.util.CamelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/////////////////////////////////////////////
/// @class DxfExportServiceImpl
/// kr.co.gitt.kworks.service.export \n
///   ??? DxfExportServiceImpl.java
/// @section ?????????????????????
///    |    ???  ???       |      ???  ???       |
///    | :-------------: | -------------   |
///    | Company | (???)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. ?????? 12:35:51 |
///    | Class Version | v1.0 |
///    | ????????? | libraleo, Others... |
/// @section ????????????
/// - ??? ???????????? DXF ???????????? ????????? ?????? ?????????.
///   -# 
/////////////////////////////////////////////
@Service("dxfExportService")
public class DxfExportServiceImpl implements DxfExportService {
	
	/// DXF ??????????????? ???????????? ????????? ?????? ????????? ?????????
	private static final String INVALID_CHARS = ".*[<>/\\\\\"[:];?[*][|]=' \\(\\)\\{\\}\\[\\]\\.,].*";
	
	/// ???????????? ?????????
	private static final String REGEX_CHARS = "[<>/\\\\\"[:];?[*][|]=' \\(\\)\\{\\}\\[\\]\\.,]";
	
	/// ???????????????
	private static final String VALID_CHAR = "_";
	
	/// ??????
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// ????????? ??????
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	/// ?????? ?????? ?????????
	@Resource
	SpatialSearchService spatialSearchService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.service.export.DxfExportService#export(java.util.List, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public String export(List<SpatialSearchSummaryDTO> list, String path, String toSrid, Extent extent) throws Exception {
		String geometryName = CamelUtil.convert2CamelCase(messageSource.getMessage("Map.GeometryName", null, Locale.getDefault()));
		String fromSrid = messageSource.getMessage("Map.Projection", null, Locale.getDefault());
		
		SRSTransform srsTrans = null;
		srsTrans = new SRSTransform();
		
		// ?????? ?????????
		ISpatialReferenceSystem sourceSrs = DefaultSRS.get(fromSrid, 0, 0);
		// ?????? ?????????
		ISpatialReferenceSystem targetSrs = DefaultSRS.get(toSrid, 0, 0);
		srsTrans.set(sourceSrs, targetSrs);
		
		// ???????????? & ?????? ??????
		FileUtils.createDirectories(path);
		String dxfFile = path + File.separator + "EXPORT.DXF";
		FileUtils.createFile(dxfFile);
		
		DxfFile dxf = null;
		try {
			// DXF ?????? ??????
			dxf = new DxfFile();
			dxf.writeOpen(dxfFile);
			
			WKTReader reader = new WKTReader();
			Extent totalExtent = new Extent();
			Map<String, List<IGeometry>> geomMap = new HashMap<String, List<IGeometry>>();
			
			for(SpatialSearchSummaryDTO spatialSearchSummaryDTO : list) {
				
				// ID ?????? ??????
				String dataId = spatialSearchSummaryDTO.getDataId();
				String alias = toValidName(spatialSearchSummaryDTO.getDataAlias());	// TODO - LKS: ????????? ???????????? & ????????? ???????????? ??????
				SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
				spatialSearchDTO.setIsOriginColumnValue(true);
				spatialSearchDTO.setFilterType(FilterType.FIDS);

				List<Long> ids = spatialSearchSummaryDTO.getIds();
				if(ids.size() > 0) {
					
					FilterFidsDTO filterFidsDTO = null;
					
					List<IGeometry> geometries = new ArrayList<IGeometry>();
					int patchSize = 1000;
					int fromIndex = 0;
					int toIndex = patchSize;
					
					DxfLayer layer = null;
					RandomColor color = new RandomColor();	//TODO - LKS: ?????? ?????? ?????????
					
					while(fromIndex < ids.size()) {
						if(toIndex > ids.size()) {
							toIndex = ids.size();
						}
						filterFidsDTO = new FilterFidsDTO();
						filterFidsDTO.setFids(ids.subList(fromIndex, toIndex));
						spatialSearchDTO.setFilterFidsDTO(filterFidsDTO);
						
						List<EgovMap> data = spatialSearchService.listAll(dataId, spatialSearchDTO);
						int geomType = 0;
						if(fromIndex == 0) {
							// ?????? ????????? ???????????? ????????? ??????
							EgovMap egovMap = data.get(0);
							for(Object key : egovMap.keySet()) {
								if(StringUtils.equalsIgnoreCase(key.toString(), geometryName)) {
									IGeometry geom = reader.execute(egovMap.get(key).toString());
									geomType = geom.getSpatialType();
									break;
								}
							}
							
							// DXF ????????? ??????
//							dxf.tables.addLayerData(toDxfLayer(geomType, dataId));
							layer = toDxfLayer(geomType, alias); 	//TODO - LKS: ?????? ????????????????????? ??????
							layer.setColor(color.getColorCode());	//TODO - LKS: ????????? ?????? ??????
							dxf.tables.addLayerData(layer);
						}
						
						for(int i=0, len=data.size(); i < len; i++) {
							EgovMap map = data.get(i);
							IGeometry geom = null;
							for(Object key : map.keySet()) {
								if(StringUtils.equalsIgnoreCase(key.toString(), geometryName)) {
									// ArcGis ?????? WKT ?????? ??? POLYGON ???????????? ???????????? ????????? ?????? ?????? (?????? ???????????? ????????? ??????)
									String wkt = map.get(key).toString();
									if(geomType == SpatialType.Polygon) {
										if(!wkt.startsWith("POLYGON") && !wkt.startsWith("MULTIPOLYGON")) {
											wkt = "POLYGON " + wkt;
										}
									}
									IGeometry geometry = reader.execute(wkt);
									geometry.setSRSName(fromSrid);
									if(!geometry.isValidCoordinateFlow()) {
										geometry.repairCoordinateFlow();
									}
									
									extent.union(geometry.extent());
									
									if(StringUtils.isNotBlank(toSrid) && !StringUtils.equals(fromSrid, toSrid)) {
										geom = srsTrans.convert(geometry);
									}
									else {
										geom = geometry;
									}
									geom.setObjectID(i+1);
									totalExtent.union(geom.extent());
									geometries.add(geom);
								}
							}
						}
						
						fromIndex += patchSize;
						toIndex += patchSize;
					}
					
					//geomMap.put(dataId, geometries);
					geomMap.put(alias, geometries);	//TODO - LKS: ?????? ????????????????????? ??????
				}
			}
			
			dxf.header.setExtent(totalExtent);
			dxf.write();
			dxf.writeSectionBegin(SectionType.Entities);

			for(Entry<String, List<IGeometry>> entry : geomMap.entrySet()) {
				for(IGeometry geometry : entry.getValue())	{
					dxf.tables.setCurrentLayer(entry.getKey(), "CONTINUOUS");	//TODO LKS - ?????? ???????????? ????????? ?????? 
					writeDxfEntity(dxf, entry.getKey(), geometry);
				}
			}
			
			dxf.writeSectionEnd();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if(dxf != null) {
				dxf.close();
			}
		}
		
		return dxfFile;
	}

	/////////////////////////////////////////////
	/// @fn toValidName
	/// @brief ?????? ????????? ?????? : Dxf??? ????????? ????????? ????????? ???????????? ?????? ??? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param name
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String toValidName(String name)
	{
		String validName = null;
		
		if (name.matches(INVALID_CHARS))
			validName = name.replaceAll(REGEX_CHARS, VALID_CHAR);
		else
			validName = name;
		
		return validName;
	}
	
	/////////////////////////////////////////////
	/// @fn toDxfLayer
	/// @brief ?????? ????????? ?????? : DxfLayer ?????? ??? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param type
	/// @param name
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private DxfLayer toDxfLayer(int type, String name)
	{
        DxfLayer layer = new DxfLayer();
        layer.setName(name);
        layer.setLineType("CONTINUOUS");
        layer.setColor(ColorIndex.Black);
        
//        switch(type)
//        {
//        case SpatialType.Point:
//        	break;
//        	
//        case SpatialType.LineString:
//        	break;
//        	
//        case SpatialType.Polygon:
//        	break;
//        	
//        default:
//        	break;
//        }
        
		return layer;
	}
	
	/////////////////////////////////////////////
	/// @fn writeDxfEntity
	/// @brief ?????? ????????? ?????? : Dxf ????????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param dxf
	/// @param layer
	/// @param geometry 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void writeDxfEntity(DxfFile dxf, String layer, IGeometry geometry)
	{
		DxfEntities entities = dxf.entities;
		
		switch(geometry.getSpatialType())
		{
		case SpatialType.Point:
		case SpatialType.MultiPoint:
			writePointEntity(entities, layer, geometry);
			break;
			
		case SpatialType.Line:
		case SpatialType.LineString:
		case SpatialType.LinearRing:
		case SpatialType.MultiLineString:
			writePolylineEntity(entities, layer, geometry);
			break;
			
		case SpatialType.Polygon:
		case SpatialType.MultiPolygon:
			writePolygonEntity(entities, layer, geometry);
			break;
		}
	}
	

	/////////////////////////////////////////////
	/// @fn writePointEntity
	/// @brief ?????? ????????? ?????? : ?????? ????????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param entities
	/// @param layer
	/// @param geometry 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private static void writePointEntity(DxfEntities entities, String layer, IGeometry geometry)
	{
		int partCount = geometry.getNumGeometries();
		Coordinate pt = null;
		Coordinates points = geometry.getCoordinates();
		
		for (int i = 0; i < partCount; i++)
		{
			DxfEntity ent = new DxfEntity();
	        DxfEntityPoint obj = new DxfEntityPoint();
	
	        ent.getHeader().setLayerName(layer);
	        ent.getHeader().setEntityType(EntityObject.Point);
	
	        pt = points.get(i);
	        obj.getPoint().setN(0, pt.getX());
	        obj.getPoint().setN(1, pt.getY());
	        obj.getPoint().setN(2, 0);
	
	        ent.setData(obj);
	        entities.writeEntityData(ent);
		}
	}

	/////////////////////////////////////////////
	/// @fn writePolylineEntity
	/// @brief ?????? ????????? ?????? : ?????? ????????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param entities
	/// @param layer
	/// @param geometry 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private static void writePolylineEntity(DxfEntities entities, String layer, IGeometry geometry)
	{
		int partCount = geometry.getNumGeometries();
		int[] parts = new int[partCount + 1];
		parts[0] = 0;
		
		for(int i = 0; i < partCount; i++)
			parts[i+1] = parts[i] + geometry.getGeometryN(i).getNumPoints();
		
		Coordinate pt = null;
		Coordinates points = geometry.getCoordinates();
		
		for (int i = 0; i < partCount; i++)
		{		
			DxfEntity ent = new DxfEntity();
	        DxfEntityPolyline obj = new DxfEntityPolyline();
	
	        ent.getHeader().setLayerName(layer);
	        ent.getHeader().setEntityType(EntityObject.Polyline);
	
	        obj.getVertex().clear();
	        for (int j = parts[i]; j < parts[i+1]; j++)
	        {
	            DxfEntityVertex vtx = new DxfEntityVertex();
	            
	            pt = points.get(j);
	            vtx.getPoint().setN(0, pt.getX());
	            vtx.getPoint().setN(1, pt.getY());
	            vtx.getPoint().setN(2, 0.0);
	
	            obj.getVertex().add(vtx);
	        }
	
	        ent.setData(obj);
	        entities.writeEntityData(ent);
		}
	}

	/////////////////////////////////////////////
	/// @fn writePolygonEntity
	/// @brief ?????? ????????? ?????? : ?????? ????????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param entities
	/// @param layer
	/// @param geometry 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private static void writePolygonEntity(DxfEntities entities, String layer, IGeometry geometry)
	{
		int partCount = 0;
		int[] parts = null;
		
		switch(geometry.getSpatialType())
		{
		case SpatialType.Polygon:
		case SpatialType.MultiPolygon:
			List<Integer> count = getPartCount(geometry);
			parts = new int[count.size() + 1];
			partCount = count.size();
			
			for(int i = 0; i < partCount; i++)
				parts[i+1] = parts[i] + count.get(i);
			break;
			
		default:
			partCount = geometry.getNumGeometries();			
			parts = new int[partCount + 1];
			parts[0] = 0;
			
			for(int i = 0; i < partCount; i++)
				parts[i+1] = parts[i] + geometry.getGeometryN(i).getNumPoints();
			break;
		}
		
		Coordinate pt = null;
		Coordinates points = geometry.getCoordinates();
		
		for (int i = 0; i < partCount; i++)
		{		
			DxfEntity ent = new DxfEntity();
	        DxfEntityPolyline obj = new DxfEntityPolyline();
	
	        ent.getHeader().setLayerName(layer);
	        ent.getHeader().setEntityType(EntityObject.Polyline);
	
	        obj.getVertex().clear();
	        for (int j = parts[i]; j < parts[i+1]; j++)
	        {
	            DxfEntityVertex vtx = new DxfEntityVertex();
	            
	            pt = points.get(j);
	            vtx.getPoint().setN(0, pt.getX());
	            vtx.getPoint().setN(1, pt.getY());
	            vtx.getPoint().setN(2, 0.0);
	
	            obj.getVertex().add(vtx);
	        }
	
	        ent.setData(obj);
	        entities.writeEntityData(ent);
		}
	}
	
	/////////////////////////////////////////////
	/// @fn getPartCount
	/// @brief ?????? ????????? ?????? : ????????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param geometry
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private static List<Integer> getPartCount(IGeometry geometry)
	{
		List<Integer> parts = new ArrayList<Integer>();
		
		switch(geometry.getSpatialType())
		{
		case SpatialType.Polygon:
			getPolygonPartCount((GeomPolygon)geometry, parts);
			break;
			
		case SpatialType.MultiPolygon:
			getMultiPolygonPartCount((GeomMultiPolygon)geometry, parts);
			break;
		}
		
		return parts;
	}

	/////////////////////////////////////////////
	/// @fn getPolygonPartCount
	/// @brief ?????? ????????? ?????? : ???????????? ????????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param polygon
	/// @param parts 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private static void getPolygonPartCount(GeomPolygon polygon, List<Integer> parts)
	{
		parts.add(polygon.getExteriorRing().getNumPoints());
		
		int count = polygon.getNumInteriorRing();
		for (int i = 0; i < count; i++)
			parts.add(polygon.getInteriorRingN(i).getNumPoints());
	}
	
	/////////////////////////////////////////////
	/// @fn getMultiPolygonPartCount
	/// @brief ?????? ????????? ?????? : ?????? ???????????? ????????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param multi
	/// @param parts
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private static List<Integer> getMultiPolygonPartCount(GeomMultiPolygon multi, List<Integer> parts)
	{
		int count = multi.getNumGeometries();
		for (int i = 0; i < count; i++)
			getPolygonPartCount((GeomPolygon) multi.getGeometryN(i), parts);
		
		return parts;
	}
	
	/////////////////////////////////////////////
	/// @fn writeTextEntity
	/// @brief ?????? ????????? ?????? : ????????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param entities
	/// @param layer
	/// @param shape
	/// @param text 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@SuppressWarnings("unused")
	private void writeTextEntity(DxfEntities entities, String layer, ShapeGeom shape, String text)
	{
		 DxfEntity ent = new DxfEntity();
         DxfEntityText obj = new DxfEntityText();

         ent.getHeader().setLayerName(layer);
         ent.getHeader().setEntityType(EntityObject.Text);

         Coordinate base = null;
         obj.setText(text);
         obj.getTextData().setTextStyleName("GHS");
         obj.getTextData().setWidthFactor(1.0);

         base = shape.extent.center();
         obj.getPoint().setN(0, base.getX());
         obj.getPoint().setN(1, base.getY());
         obj.getPoint().setN(2, 0.0);

         obj.getTextData().setHeight(2.5f);
         obj.getTextData().setRotateAngle(0.0);
         obj.getTextData().setJustification(JustificationType.Center);

         ent.setData(obj);

         entities.writeEntityData(ent);
	}

}
