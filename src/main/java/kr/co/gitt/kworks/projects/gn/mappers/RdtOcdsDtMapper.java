package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.model.RdtOcdsDt;

import org.apache.ibatis.annotations.Param;

/**
 * 불허가 대장
 * @author kwangsu.lee
 *
 */
public interface RdtOcdsDtMapper {

	/**
	 * 불허가 전체 검색
	 * @return
	 */
	public List<RdtOcdsDt> selectAll(@Param("keyStr") String keyStr);
	
	/**
	 * 불허가 검색
	 * @param oldCde - 최초대장 지형지물부호
	 * @param oldIdn - 최초대장 관리번호
	 * @return
	 */
	public List<RdtOcdsDt> selectByOldId(@Param("oldCde") String oldCde, @Param("oldIdn") Long oldIdn, @Param("keyStr") String keyStr);

	/**
	 * 불허가 단건 검색
	 * @param ftrCde - 지형지물부호
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public RdtOcdsDt selectOneById(@Param("ftrCde") String ftrCde, @Param("ftrIdn") Long ftrIdn, @Param("keyStr") String keyStr);
	
	/**
	 * 불허가 단건 검색
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public RdtOcdsDt selectOne(@Param("ftrIdn") Long ftrIdn,@Param("keyStr") String keyStr);
	
	/**
	 * 불허가 대장 등록
	 * @param rdtOcdsDt - 대장정보
	 * @return
	 */
	public Integer insert(@Param("ocdsDt") RdtOcdsDt rdtOcdsDt, @Param("keyStr") String keyStr);

	/**
	 * 불허가 읍면동 갱신
	 * @param umdNam - 읍면동
	 * @param ftrCde - 지형지물번호
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public Integer updateUmdNam(@Param("umdNam") String umdNam, @Param("ftrCde") String ftrCde, @Param("ftrIdn") Long ftrIdn);
	
	/**
	 * 불허가 대장 등록
	 * @param rdtOcdsDt - 대장정보
	 * @return
	 */
	public Integer update(@Param("ocdsDt") RdtOcdsDt rdtOcdsDt, @Param("keyStr") String keyStr);
	
	public Integer delete(Long ftrIdn);
}
