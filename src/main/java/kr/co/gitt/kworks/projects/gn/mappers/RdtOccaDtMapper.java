package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.model.RdtOccaDt;

import org.apache.ibatis.annotations.Param;

/**
 * 취소신청서 맵퍼
 * @author kwangsu.lee
 *
 */
public interface RdtOccaDtMapper {

	/**
	 * 취소신청서 전체 검색
	 * @return
	 */
	public List<RdtOccaDt> selectAll(@Param("keyStr") String keyStr);
	
	/**
	 * 취소신청서 검색
	 * @param oldCde - 최초대장 지형지물부호
	 * @param oldIdn - 최초대장 관리번호
	 * @return
	 */
	public List<RdtOccaDt> selectByOldId(@Param("oldCde") String oldCde, @Param("oldIdn") Long oldIdn, @Param("keyStr") String keyStr);

	/**
	 * 취소신청서 단건 검색
	 * @param ftrCde - 지형지물부호
	 * @param ftrdIdn - 관리번호
	 * @return
	 */
	public RdtOccaDt selectOneById(@Param("ftrCde") String ftrCde, @Param("ftrIdn") Long ftrIdn, @Param("keyStr") String keyStr);
	
	/**
	 * 취소신청서 단건 검색
	 * @param oldCde - 최초대장 지형지물부호
	 * @param oldIdn - 최초대장 관리번호
	 * @return
	 */
	public RdtOccaDt selectOne(@Param("ftrIdn") Long ftrIdn,@Param("keyStr") String keyStr);
	
	/**
	 * 취소신청서 대장 등록
	 * @param rdtOccaDt - 대장정보
	 * @return
	 */
	public Integer insert(@Param("occaDt") RdtOccaDt rdtOccaDt, @Param("keyStr") String keyStr);

	/**
	 * 취소신청서 읍면동 갱신
	 * @param umdNam - 읍면동
	 * @param ftrCde - 지형지물번호
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public Integer updateUmdNam(@Param("umdNam") String umdNam, @Param("ftrCde") String ftrCde, @Param("ftrIdn") Long ftrIdn);
	
	/**
	 * 취소신청서 대장 수정
	 * @param rdtOccaDt - 대장정보
	 * @return
	 */
	public Integer update(@Param("occaDt") RdtOccaDt rdtOccaDt, @Param("keyStr") String keyStr);
	
	/**
	 * 취소신청서 삭제 
	 * @param ftrCde - 최초대장 지형지물부호
	 * @param ftrIdn - 최초대장 관리번호
	 * @return
	 */
	public Integer delete(Long ftrIdn);
	
}
