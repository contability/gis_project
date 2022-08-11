package kr.co.gitt.kworks.saeoll.service;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.kmaps.framework.action.coordinateconvert.SRSTransform;
import com.kmaps.framework.common.Coordinate;
import com.kmaps.framework.reference.ISpatialReferenceSystem;
import com.kmaps.framework.reference.initialize.DefaultSRS;
import com.kmaps.framework.spatialdata.geometry.GeomFactory;
import com.kmaps.framework.spatialdata.geometry.IGeometry;

/////////////////////////////////////////////
/// @class SpatialObjectServiceImpl
/// kr.co.gitt.kworks.saeoll.service \n
///   ㄴ SpatialObjectServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 12. 28. 오후 4:12:02 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 공간객체 생성 서비스 구현클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("spatialObjectService")
public class SpatialObjectServiceImpl implements SpatialObjectService{

	@Resource
	MessageSource messageSource;
	
	/////////////////////////////////////////////
	public String grs80PointObject(Double x, Double y) throws Exception{
		String toSrid = messageSource.getMessage("Map.Projection", null, Locale.getDefault());
		SRSTransform srsTrans = null;
		srsTrans = new SRSTransform();
		
		// 소스 좌표계
		ISpatialReferenceSystem sourceSrs = DefaultSRS.get("EPSG:4326", 0, 0);
		// 목표 좌표계
		ISpatialReferenceSystem targetSrs = DefaultSRS.get(toSrid, 0, 0);
		srsTrans.set(sourceSrs, targetSrs);

		// 좌표 생성
		IGeometry point = GeomFactory.createPoint(new Coordinate(x, y));
		
		// 좌표 변환
		IGeometry geom = srsTrans.convert(point);

		// 변환된 공간 객체의 WKT 구하기
		String wkt = geom.toWKT();
		
		return wkt;
	}
	
	public Double convertLonLatByDms (String d, String m, String s){
		Double convertValue = (double) 0;
		convertValue = Double.valueOf( d ) + (Double.valueOf(m) / 60) + (Double.valueOf(s) / 3600);
		return convertValue;
	}
}
