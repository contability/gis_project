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
///   ㄴ DxfExportServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오후 12:35:51 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 DXF 내보내기 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("dxfExportService")
public class DxfExportServiceImpl implements DxfExportService {
	
	/// DXF 레이어명에 사용하면 안되는 문자 검색용 정규식
	private static final String INVALID_CHARS = ".*[<>/\\\\\"[:];?[*][|]=' \\(\\)\\{\\}\\[\\]\\.,].*";
	
	/// 대체문자 정규식
	private static final String REGEX_CHARS = "[<>/\\\\\"[:];?[*][|]=' \\(\\)\\{\\}\\[\\]\\.,]";
	
	/// 대체할문자
	private static final String VALID_CHAR = "_";
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 메세지 소스
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	/// 공간 검색 서비스
	@Resource
	SpatialSearchService spatialSearchService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.DxfExportService#export(java.util.List, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public String export(List<SpatialSearchSummaryDTO> list, String path, String toSrid, Extent extent) throws Exception {
		String geometryName = CamelUtil.convert2CamelCase(messageSource.getMessage("Map.GeometryName", null, Locale.getDefault()));
		String fromSrid = messageSource.getMessage("Map.Projection", null, Locale.getDefault());
		
		SRSTransform srsTrans = null;
		srsTrans = new SRSTransform();
		
		// 소스 좌표계
		ISpatialReferenceSystem sourceSrs = DefaultSRS.get(fromSrid, 0, 0);
		// 목표 좌표계
		ISpatialReferenceSystem targetSrs = DefaultSRS.get(toSrid, 0, 0);
		srsTrans.set(sourceSrs, targetSrs);
		
		// 디렉토리 & 파일 생성
		FileUtils.createDirectories(path);
		String dxfFile = path + File.separator + "EXPORT.DXF";
		FileUtils.createFile(dxfFile);
		
		DxfFile dxf = null;
		try {
			// DXF 파일 오픈
			dxf = new DxfFile();
			dxf.writeOpen(dxfFile);
			
			WKTReader reader = new WKTReader();
			Extent totalExtent = new Extent();
			Map<String, List<IGeometry>> geomMap = new HashMap<String, List<IGeometry>>();
			
			for(SpatialSearchSummaryDTO spatialSearchSummaryDTO : list) {
				
				// ID 목록 검색
				String dataId = spatialSearchSummaryDTO.getDataId();
				String alias = toValidName(spatialSearchSummaryDTO.getDataAlias());	// TODO - LKS: 레이어 한글명칭 & 유효한 문자열로 변환
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
					RandomColor color = new RandomColor();	//TODO - LKS: 랜덤 색상 생성기
					
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
							// 공간 컬럼을 확인하기 위해서 사용
							EgovMap egovMap = data.get(0);
							for(Object key : egovMap.keySet()) {
								if(StringUtils.equalsIgnoreCase(key.toString(), geometryName)) {
									IGeometry geom = reader.execute(egovMap.get(key).toString());
									geomType = geom.getSpatialType();
									break;
								}
							}
							
							// DXF 레이어 생성
//							dxf.tables.addLayerData(toDxfLayer(geomType, dataId));
							layer = toDxfLayer(geomType, alias); 	//TODO - LKS: 한글 레이어명칭으로 변경
							layer.setColor(color.getColorCode());	//TODO - LKS: 레이어 색상 지정
							dxf.tables.addLayerData(layer);
						}
						
						for(int i=0, len=data.size(); i < len; i++) {
							EgovMap map = data.get(i);
							IGeometry geom = null;
							for(Object key : map.keySet()) {
								if(StringUtils.equalsIgnoreCase(key.toString(), geometryName)) {
									// ArcGis 에서 WKT 반환 시 POLYGON 문자열이 누락되는 버그가 있어 추가 (항상 일어나는 경우는 아님)
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
					geomMap.put(alias, geometries);	//TODO - LKS: 한글 레이어명칭으로 변경
				}
			}
			
			dxf.header.setExtent(totalExtent);
			dxf.write();
			dxf.writeSectionBegin(SectionType.Entities);

			for(Entry<String, List<IGeometry>> entry : geomMap.entrySet()) {
				for(IGeometry geometry : entry.getValue())	{
					dxf.tables.setCurrentLayer(entry.getKey(), "CONTINUOUS");	//TODO LKS - 현재 출력중인 레이어 설정 
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
	/// @brief 함수 간략한 설명 : Dxf의 레이어 명칭을 유효한 명칭으로 수정 및 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param name
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
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
	/// @brief 함수 간략한 설명 : DxfLayer 생성 및 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param type
	/// @param name
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
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
	/// @brief 함수 간략한 설명 : Dxf 엔티티 쓰기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dxf
	/// @param layer
	/// @param geometry 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
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
	/// @brief 함수 간략한 설명 : 점형 엔트리 쓰기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param entities
	/// @param layer
	/// @param geometry 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
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
	/// @brief 함수 간략한 설명 : 선형 엔트리 쓰기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param entities
	/// @param layer
	/// @param geometry 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
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
	/// @brief 함수 간략한 설명 : 면형 엔트리 쓰기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param entities
	/// @param layer
	/// @param geometry 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
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
	/// @brief 함수 간략한 설명 : 파트를 계산
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param geometry
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
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
	/// @brief 함수 간략한 설명 : 폴리곤의 파트를 계산
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param polygon
	/// @param parts 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
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
	/// @brief 함수 간략한 설명 : 멀티 폴리곤의 파트를 계산
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param multi
	/// @param parts
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
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
	/// @brief 함수 간략한 설명 : 텍스트 쓰기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param entities
	/// @param layer
	/// @param shape
	/// @param text 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
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
