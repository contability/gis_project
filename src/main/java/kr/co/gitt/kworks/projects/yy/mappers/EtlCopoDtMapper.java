package kr.co.gitt.kworks.projects.yy.mappers;

import java.util.List;
import java.util.Map;

import kr.co.gitt.kworks.projects.yy.model.EtlCopoDt;

import org.apache.ibatis.annotations.Param;

/**
 * 수준점 맵퍼
 * @author kwangsu.lee
 *
 */
public interface EtlCopoDtMapper {

	/**
	 * 수준점 목록
	 * @param map
	 * @return
	 */
	public List<EtlCopoDt> selectById(Map<String, Object> map);

	/**
	 * 수준점 목록
	 * @param ftrCde
	 * @param ftrIdn
	 * @return
	 */
	public List<EtlCopoDt> selectByIdn(@Param("ftrCde") String ftrCde, @Param("ftrIdn") Long ftrIdn);

	/**
	 * 단건 조회
	 * @param ecpNo
	 * @return
	 */
	public EtlCopoDt selectOneByNo(Long ecpNo);
	
	/**
	 * 등록
	 * @param data
	 * @return
	 */
	public Integer insert(EtlCopoDt data);
	
	/**
	 * 갱신
	 * @param data
	 * @return
	 */
	public Integer update(EtlCopoDt data);
	
	/**
	 * 삭제
	 * @param ecpNo
	 * @return
	 */
	public Integer delete(Long ecpNo);
	
	/**
	 * 삭제
	 * @param ftrCde
	 * @param ftrIdn
	 * @return
	 */
	public Integer deleteById(@Param("ftrCde") String ftrCde, @Param("ftrIdn") Long ftrIdn);
}
