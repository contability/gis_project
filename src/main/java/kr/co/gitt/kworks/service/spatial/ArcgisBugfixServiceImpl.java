package kr.co.gitt.kworks.service.spatial;

import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsLyr;
import kr.co.gitt.kworks.model.KwsLyr.LyrTy;
import kr.co.gitt.kworks.service.layer.LayerService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service("arcgisBugfixService")
public class ArcgisBugfixServiceImpl implements ArcgisBugfixService {

	/// 메세지 소스
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	@Resource
	private LayerService layerService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kworks.map.file.service.ArcgisBugfixService#repairWKT(java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public String repairWKT(String dataId, String wkt) throws Exception {
		String spatialType = messageSource.getMessage("Map.ServerType", null, Locale.getDefault());
		if(StringUtils.equalsIgnoreCase(spatialType, "arcgis") && wkt.startsWith("(")) {
			
			KwsLyr kwsLyr = layerService.selectOneLayerByDataId(dataId);
			LyrTy lyrTy = kwsLyr.getLyrTy();
			
			if(lyrTy == LyrTy.POINT || lyrTy == LyrTy.MULTIPOINT) {
				return "POINT " + wkt;
			}
			else if(lyrTy == LyrTy.LINESTRING || lyrTy == LyrTy.MULTILINESTRING) {
				return "LINESTRING " + wkt;
			}
			else if(lyrTy == LyrTy.POLYGON || lyrTy == LyrTy.MULTIPOLYGON) {
				if(wkt.startsWith("(((")) {
					return  "MULTIPOLYGON " + wkt;
				}
				else {
					return "POLYGON " + wkt;
				}
			}
			else {
				return wkt;
			}
		}
		else {
			return wkt;
		}
		
	}
	
}
