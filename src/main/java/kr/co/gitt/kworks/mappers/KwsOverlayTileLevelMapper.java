package kr.co.gitt.kworks.mappers;

import java.util.List;
import java.util.Map;

import kr.co.gitt.kworks.model.KwsOverlayTileLevel;

/**
 * 중첩 타일맵 레벨 맵퍼
 * @author kwangsu.lee
 *
 */
public interface KwsOverlayTileLevelMapper {

	/**
	 * 레벨목록 검색
	 * @param map - 
	 * @return
	 */
	public List<KwsOverlayTileLevel> selectByTileNo(Map<String, Object> map);	
}
