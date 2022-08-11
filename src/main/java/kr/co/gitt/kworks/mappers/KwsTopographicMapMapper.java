package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsTopographicMap;

import org.apache.ibatis.annotations.Param;

/**
 * 지형지도 맵퍼
 * @author kwangsu.lee
 *
 */
public interface KwsTopographicMapMapper {
	
	/**
	 * 그룹목록 검색
	 * @return
	 */
	public List<String> listGroup();

	/**
	 * 전체목록 검색
	 * @return
	 */
	public List<KwsTopographicMap> listAll();
	
	/**
	 * 그룹명으로 검색
	 * @param group - 그룹명
	 * @return
	 */
	public List<KwsTopographicMap> selectByGroup(@Param("group") String group);
	
	/**
	 * 검색
	 * @param group - 그룹명
	 * @param layer - 레이어명
	 * @return
	 */
	public KwsTopographicMap selectOne(@Param("group") String group, @Param("layer") String layer);
	
}
