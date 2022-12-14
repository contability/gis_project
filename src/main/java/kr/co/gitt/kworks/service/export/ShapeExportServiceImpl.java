package kr.co.gitt.kworks.service.export;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.spatial.FilterFidsDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO.FilterType;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import kr.co.gitt.kworks.mappers.KwsLyrMapper;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsData.GeomTy;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsDataField.FieldTy;
import kr.co.gitt.kworks.service.data.DataService;
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
import com.kmaps.framework.library.dbf.DbaseFileColumn;
import com.kmaps.framework.library.dbf.DbaseFileRecord;
import com.kmaps.framework.library.shp.ShapeFileWriter;
import com.kmaps.framework.library.shp.ShapeGeom;
import com.kmaps.framework.library.shp.ShapeGeomType;
import com.kmaps.framework.reference.ISpatialReferenceSystem;
import com.kmaps.framework.reference.initialize.DefaultSRS;
import com.kmaps.framework.spatialdata.geometry.GeomMultiPolygon;
import com.kmaps.framework.spatialdata.geometry.GeomPolygon;
import com.kmaps.framework.spatialdata.geometry.IGeometry;
import com.kmaps.framework.spatialdata.geometry.io.WKTReader;

import egovframework.rte.psl.dataaccess.util.CamelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/////////////////////////////////////////////
/// @class ShapeExportServiceImpl
/// kworks.map.file.service.impl \n
///   ??? ShapeExportServiceImpl.java
/// @section ?????????????????????
///    |    ???  ???       |      ???  ???       |
///    | :-------------: | -------------   |
///    | Company | (???)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 5. 27. ?????? 10:57:27 |
///    | Class Version | v1.0 |
///    | ????????? | admin, Others... |
/// @section ????????????
/// - ??? ???????????? Shape ???????????? ????????? ???????????????.
///   -# 
/////////////////////////////////////////////
@Service("shapeExportService")
public class ShapeExportServiceImpl implements ShapeExportService {
	
	/// ??????
	Logger logger = LoggerFactory.getLogger(getClass());

	/// ????????? ??????
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	/// ????????? ?????????
	@Resource
	DataService dataService;
	
	/// ?????? ?????? ?????????
	@Resource
	SpatialSearchService spatialSearchService;
	
	/**
	 * ????????? ??????
	 */
	@Resource
	KwsLyrMapper kwsLyrMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) ?????? ????????? ??????
	/// @remark
	/// - ??????????????? ????????? ?????? ??????
	/// @see kr.co.gitt.kworks.service.export.ShapeExportService#export(kr.co.gitt.kworks.cmmn.dto.SpatialSearchSummaryDTO, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public void export(SpatialSearchSummaryDTO spatialSearchSummaryDTO, String path, String toSrid, Extent extent) throws Exception {
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		String fromSrid = messageSource.getMessage("Map.Projection", null, Locale.getDefault());

		SRSTransform srsTrans = null;
		srsTrans = new SRSTransform();
		
		// ?????? ?????????
		ISpatialReferenceSystem sourceSrs = DefaultSRS.get(fromSrid, 0, 0);
		// ?????? ?????????
		ISpatialReferenceSystem targetSrs = DefaultSRS.get(toSrid, 0, 0);
		srsTrans.set(sourceSrs, targetSrs);
		
		String dataId = spatialSearchSummaryDTO.getDataId();
		
		FileUtils.createDirectories(path);
		String shapeFile = path + File.separator + dataId + ".SHP";
		FileUtils.createFile(shapeFile);
		
		WKTReader reader = new WKTReader();
		
		int geomType = 0;
		ShapeFileWriter writer = null;
		
		List<Long> ids = spatialSearchSummaryDTO.getIds();
		if(ids.size() > 0) {
			
			KwsData kwsData = dataService.selectOneData(dataId);
			List<KwsDataField> fields = kwsData.getKwsDataFields();
			
			SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
			spatialSearchDTO.setIsOriginColumnValue(true);
			spatialSearchDTO.setFilterType(FilterType.FIDS);
			FilterFidsDTO filterFidsDTO = null;
			filterFidsDTO = new FilterFidsDTO();
			filterFidsDTO.setFids(ids.subList(0, 1));
			spatialSearchDTO.setFilterFidsDTO(filterFidsDTO);
			
			List<EgovMap> data = spatialSearchService.listAll(dataId, spatialSearchDTO);
			EgovMap egovMap = data.get(0);
			for(Object key : egovMap.keySet()) {
				if(StringUtils.equalsIgnoreCase(key.toString(), geometryName)) {
					IGeometry geom = reader.execute(egovMap.get(key).toString());
					geomType = geom.getSpatialType();
					writer = new ShapeFileWriter(toShapeGeomType(geom), "KSC5601");
					break;
				}
			}
			
			if (!writer.open(shapeFile))
			{
				logger.warn("target file open fail.");
				return;
			}
			
			List<DbaseFileColumn> dbaseColumnList = new ArrayList<DbaseFileColumn>();
			for(int i=0, len=fields.size(); i < len; i++) {
				if(fields.get(i).getFieldTy().equals(FieldTy.GEOMETRY) || fields.get(i).getFieldId().equals("OBJECTID")) {
					continue;
				}
				dbaseColumnList.add(toDBFColumn(fields.get(i)));
			}
			DbaseFileColumn[] dbaseColumns = new DbaseFileColumn[dbaseColumnList.size()];
			writer.setFields(dbaseColumnList.toArray(dbaseColumns));
			writer.beginStore();
			
			int patchSize = 1000;
			int fromIndex = 0;
			int toIndex = patchSize;
			while(fromIndex < ids.size()) {
				if(toIndex > ids.size()) {
					toIndex = ids.size();
				}
				filterFidsDTO = new FilterFidsDTO();
				filterFidsDTO.setFids(ids.subList(fromIndex, toIndex));
				spatialSearchDTO.setFilterFidsDTO(filterFidsDTO);
				
				data = spatialSearchService.listAll(dataId, spatialSearchDTO);
				
				for(int i=0, len=data.size(); i < len; i++) {
					EgovMap map = data.get(i);
					IGeometry geom = null;
					DbaseFileRecord dbfRecord = new DbaseFileRecord(dbaseColumns);
					dbfRecord.newRecord();
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
						}
						else {
							for(int j=0; j < dbaseColumns.length; j++) {
								if(StringUtils.equals(CamelUtil.convert2CamelCase(dbaseColumns[j].name), key.toString())) {
									if(map.get(key) == null) {
										dbfRecord.setValue(j, "");
									}
									else {
										dbfRecord.setValue(j, map.get(key));
									}
									break;
								}
							}
						}
					}
					ShapeGeom shape = toShapeGeom(geom);
					writer.writeShape(shape, dbfRecord);
				}
				fromIndex += patchSize;
				toIndex += patchSize;
			}
			
			writer.endStore();
			writer.close();
		}
	}
	
	/////////////////////////////////////////////
	/// @fn toDBFColumn
	/// @brief ?????? ????????? ?????? : DBF ?????? ?????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param kwsDataFieldVO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private DbaseFileColumn toDBFColumn(KwsDataField kwsDataField) {
		DbaseFileColumn dbfColumn = new DbaseFileColumn();
		dbfColumn.name = kwsDataField.getFieldId();
		/*
		 * FTY	????????????	STRING	FTY001	Y
		 * FTY	????????????	NUMBER	FTY002	Y
		 * FTY	????????????	YMDSTR	FTY004	Y
		 * FTY	????????????	DATE	FTY003	Y
		 * FTY	????????????	CLOB	FTY005	Y
		 */
		FieldTy fieldTy = kwsDataField.getFieldTy();
		if(fieldTy.equals(FieldTy.NUMBER)) {
			dbfColumn.type = (byte) 'N';
			Integer dcmlLt = kwsDataField.getDcmlLt();
			dbfColumn.numberOfDecimalPlaces = dcmlLt==null?0:dcmlLt.shortValue();
		}
//		else if(fieldTy.equals(FieldTy.DATE_FROM_STRING)) {
//			dbfColumn.type = (byte) 'C';
//			dbfColumn.numberOfDecimalPlaces = 0;
//		}
		else if(fieldTy.equals(FieldTy.STRING) || fieldTy.equals(FieldTy.DATE_FROM_STRING)){
			dbfColumn.type = (byte) 'C';
			dbfColumn.numberOfDecimalPlaces = 0;
		}
		
		Integer fieldLt = kwsDataField.getFieldLt();
		if(fieldLt != null) {
			dbfColumn.length = fieldLt<255?fieldLt.shortValue():255;
		}
		else {
			dbfColumn.length =  0;
		}
		
		return dbfColumn;
	}
	
	/////////////////////////////////////////////
	/// @fn toShapeGeomType
	/// @brief ?????? ????????? ?????? : Shape ?????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param geometry
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private int toShapeGeomType(IGeometry geometry) {
		int shapeGeomType = ShapeGeomType.None;
		
		switch (geometry.getSpatialType())
		{
		case SpatialType.Point:
			shapeGeomType = ShapeGeomType.Point;
			break;
			
		case SpatialType.MultiPoint:
			shapeGeomType = ShapeGeomType.MultiPoint;
			break;
			
		case SpatialType.Line:
		case SpatialType.LineString:
		case SpatialType.MultiLineString:
			shapeGeomType = ShapeGeomType.Arc;
			break;
			
		case SpatialType.Polygon:
		case SpatialType.MultiPolygon:
			shapeGeomType = ShapeGeomType.Polygon;
			break;
			
		default:
			shapeGeomType = ShapeGeomType.None;
			break;
		}
		return shapeGeomType;
	}
	
	/////////////////////////////////////////////
	/// @fn toShapeGeom
	/// @brief ?????? ????????? ?????? : ????????????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param geometry
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private ShapeGeom toShapeGeom(IGeometry geometry)
	{
		ShapeGeom shape = new ShapeGeom();
		
		shape.shapeID = (int)geometry.getObjectID();
		shape.coordCount = geometry.getNumPoints();
		
		switch (geometry.getSpatialType())
		{
		case SpatialType.Point:
			shape.shapeType = ShapeGeomType.Point;
			break;
			
		case SpatialType.MultiPoint:
			shape.shapeType = ShapeGeomType.MultiPoint;
			break;
			
		case SpatialType.Line:
		case SpatialType.LineString:
		case SpatialType.MultiLineString:
			shape.shapeType = ShapeGeomType.Arc;
			break;
			
		case SpatialType.Polygon:
		case SpatialType.MultiPolygon:
			shape.shapeType = ShapeGeomType.Polygon;
			break;
			
		default:
			shape.shapeType = ShapeGeomType.None;
			break;
		}
		
		shape.shapeDimension = shape.shapeType / 10;

		Coordinate coord = null;
		Coordinates coordnates = geometry.getCoordinates();
		if(shape.coordCount > 0 && shape.shapeType != ShapeGeomType.None)
		{
			shape.coords = new Coordinate[shape.coordCount];
			
			for(int i = 0; i < shape.coordCount; i++)
			{
				coord = coordnates.get(i);
				shape.coords[i] = new Coordinate(coord);
			}
			
			switch(geometry.getSpatialType())
			{
			case SpatialType.MultiPoint:
				break;
				
			case SpatialType.MultiLineString:
				break;
				
			case SpatialType.Polygon:
			case SpatialType.MultiPolygon:
			{
				List<Integer> parts = getPartCount(geometry);
				shape.partCount = parts.size();
				shape.parts = new int[parts.size() + 1];
				shape.parts[0] = 0;
				
				int size = shape.partCount;
				for(int i = 0; i < size; i++)
					shape.parts[i+1] = shape.parts[i] + parts.get(i);
			}
				break;
				
			default:
			{
				shape.partCount = geometry.getNumGeometries();
				shape.parts = new int[shape.partCount + 1];
				shape.parts[0] = 0;
				
				int size = shape.partCount;
				for(int i = 0; i < size; i++)
					shape.parts[i+1] = shape.parts[i] + geometry.getGeometryN(i).getNumPoints();
			}
				break;
			}
			
			Extent extent = geometry.extent();
			shape.extent.set(extent);
		}
		
		return shape;
	}

	/////////////////////////////////////////////
	/// @fn getPartCount
	/// @brief ?????? ????????? ?????? : ????????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param geometry - ?????????
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
	/// @param polygon - ?????????
	/// @param parts - ?????? ??????
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
	/// @param multi - ?????? ?????????
	/// @param parts - ?????? ??????
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
	
	@Override
	public void export(KwsData kwsData, String path) throws Exception {
		String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		String srs = messageSource.getMessage("Map.Projection", null, Locale.getDefault());
		
		// ?????? ??? ?????? ??????
		FileUtils.createDirectories(path);
		String shapeFile = path + File.separator + kwsData.getDataId() + ".SHP";
		FileUtils.createFile(shapeFile);
		
		int shpType = ShapeGeomType.None;
		GeomTy geomTy = kwsData.getGeomTy();
		switch(geomTy) {
		case POINT:
			shpType = ShapeGeomType.Point;
			break;
			
		case LINESTRING:
			shpType = ShapeGeomType.Arc;
			break;
			
		case POLYGON:
			shpType = ShapeGeomType.Polygon;
			break;
		}
		
		String dataId = kwsData.getDataId();
		List<KwsDataField> fields = kwsData.getKwsDataFields();
		
		SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
		spatialSearchDTO.setIsOriginColumnValue(true);
		
		// ?????? ????????? ?????? ??????
		SpatialSearchSummaryDTO summary = spatialSearchService.listAllSummary(dataId, spatialSearchDTO);
		List<Long> ids = summary.getIds();
		
		// SHP Writer ??????
		ShapeFileWriter writer = new ShapeFileWriter(shpType, "KSC5601");
		if (!writer.open(shapeFile))
		{
			logger.warn("target file open fail.");
			writer.close();
			return;
		}
		
		// DBF?????? ????????????
		List<DbaseFileColumn> dbaseColumnList = new ArrayList<DbaseFileColumn>();
		for(int i=0, len=fields.size(); i < len; i++) {
			if(fields.get(i).getFieldTy().equals(FieldTy.GEOMETRY) || fields.get(i).getFieldId().equals("OBJECTID")) {
				continue;
			}
			dbaseColumnList.add(toDBFColumn(fields.get(i)));
		}
		DbaseFileColumn[] dbaseColumns = new DbaseFileColumn[dbaseColumnList.size()];
		writer.setFields(dbaseColumnList.toArray(dbaseColumns));
		writer.beginStore();
			
		// ????????? ??????
		int patchSize = 1000;
		int fromIndex = 0;
		int toIndex = patchSize;
		WKTReader reader = new WKTReader();
		
		try
		{
			while(fromIndex < ids.size()) {
				if(toIndex > ids.size()) {
					toIndex = ids.size();
				}
				FilterFidsDTO filterFidsDTO = new FilterFidsDTO();
				filterFidsDTO.setFids(ids.subList(fromIndex, toIndex));
				
				spatialSearchDTO.setFilterType(FilterType.FIDS);
				spatialSearchDTO.setFilterFidsDTO(filterFidsDTO);
				
				List<EgovMap> datas = spatialSearchService.listAll(dataId, spatialSearchDTO);
				for(int i=0, len = datas.size(); i < len; i++) {
					
					int objId = i+1;
					IGeometry geom = null;
					EgovMap map = datas.get(i);
					
					DbaseFileRecord dbfRecord = new DbaseFileRecord(dbaseColumns);
					dbfRecord.newRecord();
					
					Boolean isNullShape = false;
					for(Object key : map.keySet()) {
						if(StringUtils.equalsIgnoreCase(key.toString(), geometryName)) {
							// ArcGis ?????? WKT ?????? ??? POLYGON ???????????? ???????????? ????????? ?????? ?????? (?????? ???????????? ????????? ??????)
							String wkt = map.get(key).toString();
							try	{
								if (wkt != null && wkt.length() > 0 && !wkt.equalsIgnoreCase("EMPTY")) {
									if(geomTy == GeomTy.POLYGON) {
										if(!wkt.startsWith("POLYGON") && !wkt.startsWith("MULTIPOLYGON")) {
											wkt = "POLYGON " + wkt;
										}
									}
									
									geom = reader.execute(wkt);
									geom.setSRSName(srs);
									if(!geom.isValidCoordinateFlow()) {
										geom.repairCoordinateFlow();
									}
									
									geom.setObjectID(objId);
								}
								else {
									geom = null;
									isNullShape = true;
									logger.warn(dataId + "(objectId:" + map.get("objectid") + ") geometry is null");
									break;
								}
							}
							catch(Exception ex) {
								logger.warn(ex.getLocalizedMessage());
								geom = null;
							}
						}
						else {
							for(int j=0; j < dbaseColumns.length; j++) {
								if(StringUtils.equals(CamelUtil.convert2CamelCase(dbaseColumns[j].name), key.toString())) {
									if(map.get(key) == null) {
										dbfRecord.setValue(j, "");
									}
									else {
										dbfRecord.setValue(j, map.get(key));
									}
									break;
								}
							}
						}
					}
					
					if (!isNullShape) {
						ShapeGeom shape = toShapeGeom(geom, shpType, objId);
						writer.writeShape(shape, dbfRecord);
					}
				}
				
				fromIndex += patchSize;
				toIndex += patchSize;
			}
			
		}
		catch(Exception ex)	{
			logger.warn(dataId + " : export fail  - " + ex.getMessage());
		}
		finally {
			writer.endStore();
			writer.close();
		}
	}
	
	private ShapeGeom toShapeGeom(IGeometry geometry, int shapeType, int objectId)
	{
		ShapeGeom shape = new ShapeGeom();
		
		shape.shapeID = objectId;
		if (geometry != null)
			shape.coordCount = geometry.getNumPoints();
		else
			shape.coordCount = 0;
		
		shape.shapeType = shapeType;
		shape.shapeDimension = shape.shapeType / 10;

		if(shape.coordCount > 0 && shape.shapeType != ShapeGeomType.None)
		{
			Coordinate coord = null;
			Coordinates coordnates = geometry.getCoordinates();
			
			shape.coords = new Coordinate[shape.coordCount];
			
			for(int i = 0; i < shape.coordCount; i++)
			{
				coord = coordnates.get(i);
				shape.coords[i] = new Coordinate(coord);
			}
			
			switch(geometry.getSpatialType())
			{
			case SpatialType.MultiPoint:
				break;
				
			case SpatialType.MultiLineString:
				break;
				
			case SpatialType.Polygon:
			case SpatialType.MultiPolygon:
			{
				List<Integer> parts = getPartCount(geometry);
				shape.partCount = parts.size();
				shape.parts = new int[parts.size() + 1];
				shape.parts[0] = 0;
				
				int size = shape.partCount;
				for(int i = 0; i < size; i++)
					shape.parts[i+1] = shape.parts[i] + parts.get(i);
			}
				break;
				
			default:
			{
				shape.partCount = geometry.getNumGeometries();
				shape.parts = new int[shape.partCount + 1];
				shape.parts[0] = 0;
				
				int size = shape.partCount;
				for(int i = 0; i < size; i++)
					shape.parts[i+1] = shape.parts[i] + geometry.getGeometryN(i).getNumPoints();
			}
				break;
			}
			
			Extent extent = geometry.extent();
			shape.extent.set(extent);
		}
		
		return shape;
	}
	
}