package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.model.RdtOcpeHt;

/**
 * 이클래스는 변경이력 맵퍼클래스입니다.
 * 변경이력 맵퍼
 * @author wongi.Jo
 *
 */
public interface RdtOcpeHtMapper {

	/**
	 * 변경이력 대장 조회
	 * @param rdtOcpeHt - 대장정보
	 * @return
	 */
	public List<RdtOcpeHt> list(RdtOcpeHt rdtOcpeHt);
	
	/**
	 * 변경이력 단건조회
	 * @param rdtOcpeHt - 대장정보
	 * @return
	 */
	public RdtOcpeHt selectOne(Long histNo);
	
	/**
	 * 변경이력 등록
	 * @param rdtOcpeHt - 대장수정
	 * @return
	 */
	public Integer update(RdtOcpeHt rdtOcpeHt);
	/**
	 * 변경이력 등록
	 * @param rdtOcpeHt - 대장등록
	 * @return
	 */
	public Integer insert(RdtOcpeHt rdtOcpeHt);
	/**
	 * 변경이력 등록
	 * @param rdtOcpeHt - 대장삭제
	 * @return
	 */
	public Integer delete(Long histNo);
}
