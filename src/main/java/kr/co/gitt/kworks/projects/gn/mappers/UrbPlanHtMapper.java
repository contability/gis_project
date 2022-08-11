package kr.co.gitt.kworks.projects.gn.mappers;

import kr.co.gitt.kworks.projects.gn.model.UrbPlanHt;

import org.apache.ibatis.annotations.Param;

/**
 * 도시계획도로 변경이력 맵퍼
 * @author kwangsu.lee
 *
 */
public interface UrbPlanHtMapper {
	
	/**
	 * 변견이력의 개수를 반환
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public Integer getCount(@Param("ftrIdn") Long ftrIdn);

	/**
	 * 마지막 이력순차번호를 반환
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public Integer getLastSequence(@Param("ftrIdn") Long ftrIdn);
	
	/**
	 * 도시계획도로 변경이력 추가
	 * @param data - 신규 데이터
	 * @return
	 */
	public Integer insert(@Param("data") UrbPlanHt data);
	
	/**
	 * 도시계획도로 변경이력 갱신
	 * @param ftrIdn - 관리번호
	 * @param uprSeq - 이력 순차번호
	 * @param data - 갱신할 데이터
	 * @return
	 */
	public Integer update(@Param("ftrIdn") Long ftrIdn, @Param("uprSeq") Long uprSeq, @Param("data") UrbPlanHt data);
	
	/**
	 * 도시계획도로 변경이력 일괄삭제
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public Integer deleteAll(@Param("ftrIdn") Long ftrIdn);
	
	/**
	 * 도시계획도로 변경이력 삭제
	 * @param ftrIdn - 관리번호
	 * @param uprSeq - 이력 순차번호
	 * @return
	 */
	public Integer delete(@Param("ftrIdn") Long ftrIdn, @Param("uprSeq") Long uprSeq);
}
