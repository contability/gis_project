package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.model.RdtOcdrDt;

import org.apache.ibatis.annotations.Param;

/**
 * 취하원 맵퍼
 * @author kwangsu.lee
 * 
 */
public interface RdtOcdrDtMapper {
	
	/**
	 * 취하원 전체 검색
	 * @return
	 */
	public List<RdtOcdrDt> selectAll(@Param("keyStr") String keyStr);
	
	/**
	 * 취하원 검색
	 * @param oldCde - 최초대장 지형지물부호
	 * @param oldIdn - 최초대장 관리번호
	 * @return
	 */
	public List<RdtOcdrDt> selectByOldId(@Param("oldCde") String oldCde, @Param("oldIdn") Long oldIdn, @Param("keyStr") String keyStr);

	/**
	 * 취하원 단건 검색
	 * @param ftrCde - 지형지물부호
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public RdtOcdrDt selectOneById(@Param("ftrCde") String ftrCde, @Param("ftrIdn") Long ftrIdn, @Param("keyStr") String keyStr);
	
	/**
	 * 취하원 단건 검색
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public RdtOcdrDt selectOne(@Param("ftrIdn") Long ftrIdn,@Param("keyStr") String keyStr);
	
	/**
	 * 취하원 등록
	 * @param rdtOcdrDt - 대장정보
	 * @return
	 */
	public Integer insert(@Param("ocdrDt") RdtOcdrDt rdtOcdrDt, @Param("keyStr") String keyStr);

	/**
	 * 취하원 읍면동 갱신
	 * @param umdNam - 읍면동
	 * @param ftrCde - 지형지물번호
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public Integer updateUmdNam(@Param("umdNam") String umdNam, @Param("ftrCde") String ftrCde, @Param("ftrIdn") Long ftrIdn);
	
	/**
	 * 취하원 등록
	 * @param rdtOcdrDt - 대장정보
	 * @return
	 */
	public Integer update(@Param("ocdrDt") RdtOcdrDt rdtOcdrDt, @Param("keyStr") String keyStr);
	
	public Integer delete(Long ftrIdn);
}
