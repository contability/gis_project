package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.co.gitt.kworks.projects.gn.model.RdtOccnDt;

/**
 * 변경대장 맵퍼
 * @author kwangsu.lee
 *
 */
public interface RdtOccnDtMapper {

	/**
	 * 변경대장 전체 검색
	 * @return
	 */
	public List<RdtOccnDt> selectAll(@Param("keyStr") String keyStr);
	
	/**
	 * 변경대장 단건 검색
	 * @param ftrCde - 지형지물부호
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public RdtOccnDt selectOneById(@Param("ftrCde") String ftrCde, @Param("ftrIdn") Long oldftrIdnIdn, @Param("keyStr") String keyStr);
	
	/**
	 * 변경대장 단건 검색
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public RdtOccnDt selectOne(@Param("ftrIdn") Long ftrIdn,@Param("keyStr") String keyStr);
	
	/**
	 * 변경대장 등록
	 * @param rdtOccnDt - 대장정보
	 * @return
	 */
	public Integer insert(@Param("occnDt") RdtOccnDt rdtOccnDt, @Param("keyStr") String keyStr);
	
	/**
	 * 변경대장 읍면동 갱신
	 * @param umdNam - 읍면동
	 * @param ftrCde - 대장 지형지물번호
	 * @param ftrIdn - 대장 관리번호
	 * @return
	 */
	public Integer updateUmdNam(@Param("umdNam") String umdNam, @Param("ftrCde") String ftrCde, @Param("ftrIdn") Long ftrIdn);

	
	/**
	 * 변경대장 등록
	 * @param rdtOccnDt - 대장정보
	 * @return
	 */
	public Integer update(@Param("occnDt") RdtOccnDt rdtOccnDt, @Param("keyStr") String keyStr);
	
	/**
	 * 변경대장 단건 검색
	 * @param ftrCde - 지형지물부호
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public Integer delete(Long ftrIdn);

}
