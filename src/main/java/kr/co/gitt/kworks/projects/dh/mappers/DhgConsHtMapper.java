package kr.co.gitt.kworks.projects.dh.mappers;

import kr.co.gitt.kworks.projects.dh.model.DhgConsHt;

import org.apache.ibatis.annotations.Param;

/**
 * 동해시 도시계획도로 공사개요 맵퍼
 * 
 * @author kwangsu.lee
 *
 */
public interface DhgConsHtMapper {

	/**
	 * 도시계획도로 공새개요 추가
	 * 
	 * @param data - 신규 데이터
	 * @return
	 */
	public Integer insert(@Param("data") DhgConsHt data);
	
	/**
	 * 도시계획도로 공사개요 갱신
	 * 
	 * @param ftrIdn - 관리번호
	 * @param conSeq - 이력번호
	 * @param data - 갱신할 데이터
	 * @return
	 */
	public Integer update(@Param("ftrIdn") Long ftrIdn, @Param("conSeq") Long conSeq, @Param("data") DhgConsHt data);
	
	/**
	 * 도시계획도로 공사개요 일괄삭제
	 * 
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public Integer deleteAll(@Param("ftrIdn") Long ftrIdn);	
	
	/**
	 * 도시계획도로 공사개요 삭제
	 * 
	 * @param ftrIdn - 관리번호
	 * @param conSeq - 이력번호
	 * @return
	 */
	public Integer delete(@Param("ftrIdn") Long ftrIdn, @Param("conSeq") Long conSeq);
}
