package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsOverlayTile;

/**
 * 중첩용 타일맵 맵퍼
 * @author kwangsu.lee
 *
 */
public interface KwsOverlayTileMapper {

	/**
	 * 타일맵 전체 목록
	 * @return
	 */
	public List<KwsOverlayTile> listAll();	
}
