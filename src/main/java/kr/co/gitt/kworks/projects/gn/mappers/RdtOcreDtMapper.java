package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.model.RdtOcreDt;

import org.apache.ibatis.annotations.Param;

/**
 * 원상회복공사 대장
 * @author kwangsu.lee
 *
 */
public interface RdtOcreDtMapper {
	
	/**
	 * 원상회복 전체 검색
	 * @return
	 */
	public List<RdtOcreDt> selectAll(@Param("keyStr") String keyStr);

	/**
	 * 원상회복공사 단건 검색
	 * @param ftrCde - 지형지물부호
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public RdtOcreDt selectOneById(@Param("ftrCde") String ftrCde, @Param("ftrIdn") Long ftrIdn, @Param("keyStr") String keyStr);
	
	/**
	 * 원상회복공사 단건 검색
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public RdtOcreDt selectOne(@Param("ftrIdn") Long ftrIdn,@Param("keyStr") String keyStr);
	
	/**
	 * 원상회복공사 등록
	 * @param rdtOcreDt - 대장정보
	 * @return
	 */
	public Integer insert(@Param("ocreDt") RdtOcreDt rdtOcreDt, @Param("keyStr") String keyStr);

	/**
	 * 원상회복공사 읍면동 갱신
	 * @param umdNam - 읍면동
	 * @param ftrCde - 대장 지형지물번호
	 * @param ftrIdn - 대장 관리번호
	 * @return
	 */
	public Integer updateUmdNam(@Param("umdNam") String umdNam, @Param("ftrCde") String ftrCde, @Param("ftrIdn") Long ftrIdn);
	
	/**
	 * 원상회복공사 등록
	 * @param rdtOcreDt - 대장정보
	 * @return
	 */
	public Integer update(@Param("ocreDt") RdtOcreDt rdtOcreDt, @Param("keyStr") String keyStr);
	
	public Integer delete(Long ftrIdn);
}
