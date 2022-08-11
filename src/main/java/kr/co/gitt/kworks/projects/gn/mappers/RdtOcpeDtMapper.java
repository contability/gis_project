package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.model.RdtOcpeDt;

import org.apache.ibatis.annotations.Param;

/**
 * 점용허가 맵퍼
 * @author kwangsu.lee
 *
 */
public interface RdtOcpeDtMapper {

	/**
	 * 점용허가 전체 검색
	 * @return
	 */
	public List<RdtOcpeDt> selectAll(@Param("keyStr") String keyStr);
	
	/**
	 * 점용허가 단건 검색
	 * @param ftrCde - 지형지물부호
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public RdtOcpeDt selectOneById(@Param("ftrCde") String ftrCde, @Param("ftrIdn") Long ftrIdn, @Param("keyStr") String keyStr);
	
	/**
	 * 점용허가 단건 검색
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public RdtOcpeDt selectOne(@Param("ftrIdn") Long ftrIdn, @Param("keyStr") String keyStr);
	
	/**
	 * 점용허가 등록
	 * @param rdtOcpeDt - 대장정보
	 * @return
	 */
	public Integer insert(@Param("ocpeDt") RdtOcpeDt rdtOcpeDt, @Param("keyStr") String keyStr);
	
	/**
	 * 점용허가 읍면동 갱신
	 * @param umdNam - 읍면동
	 * @param ftrCde - 대장 지형지물번호
	 * @param ftrIdn - 대장 관리번호
	 */
	public Integer updateUmdNam(@Param("umdNam") String umdNam, @Param("ftrCde") String ftrCde, @Param("ftrIdn") Long ftrIdn);
	
	/**
	 * 점용허가 등록
	 * @param rdtOcpeDt - 대장정보
	 * @return
	 */
	public Integer update(@Param("ocpeDt") RdtOcpeDt rdtOcpeDt, @Param("keyStr") String keyStr);
	
	public Integer delete(Long ftrIdn);
}
