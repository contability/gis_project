package kr.co.gitt.kworks.service.topographyMap;

import java.util.List;

import kr.co.gitt.kworks.model.KwsTopographicMap;

/**
 * 지형지도 서비스
 * @author kwangsu.lee
 *
 */
public interface TopographicMapService {

	/**
	 * 전체 그룹명을 반환
	 * @return
	 */
	public List<String> listGroup();	

	/**
	 * 전체 지형지도 레이어를 반환
	 * @return
	 */
	public List<KwsTopographicMap> listAll();
	
	/**
	 * 그룹명과 일치하는 레이어 검색
	 * @param groupName - 그룹명
	 * @return
	 */
	public List<KwsTopographicMap> selectByGroup(String groupName);
	
	/**
	 * 일치하는 레이어 검색
	 * @param groupName - 그룹 명침
	 * @param layerName - 레이어 명칭
	 * @return
	 */
	public KwsTopographicMap selectOne(String groupName, String layerName); 
	
}
