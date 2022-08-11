package kr.co.gitt.kworks.projects.dh.mappers;

import kr.co.gitt.kworks.projects.dh.model.DhgRepaHt;

import org.apache.ibatis.annotations.Param;

/**
 * 동해시 도시계획도로 정비이력 맵퍼
 * 
 * @author kwangsu.lee
 *
 */
public interface DhgRepaHtMapper {

	/**
	 * 도시계획도로 정비이력 추가
	 * 
	 * @param data - 신규 데이터
	 * @return
	 */
	public Integer insert(@Param("data") DhgRepaHt data);
	
	/**
	 * 도시계획도로 정비이력 갱신
	 * 
	 * @param ftrIdn - 관리번호
	 * @param repSeq - 이력번호
	 * @param data - 갱신할 데이터
	 * @return
	 */
	public Integer update(@Param("ftrIdn") Long ftrIdn, @Param("repSeq") Long repSeq, @Param("data") DhgRepaHt data);
	
	/**
	 * 도시계획도로 정비이력 일괄삭제
	 * 
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public Integer deleteAll(@Param("ftrIdn") Long ftrIdn);	
	
	/**
	 * 도시계획도로 정비이력 삭제
	 * 
	 * @param ftrIdn - 관리번호
	 * @param repSeq - 이력번호
	 * @return
	 */
	public Integer delete(@Param("ftrIdn") Long ftrIdn, @Param("repSeq") Long repSeq);

}
