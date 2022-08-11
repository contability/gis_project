package kr.co.gitt.kworks.projects.gn.mappers;

import kr.co.gitt.kworks.projects.gn.model.UrbPlanMa;

import org.apache.ibatis.annotations.Param;

/**
 * 도시계획도로 맵퍼
 * @author kwangsu.lee
 *
 */
public interface UrbPlanMaMapper {

	/**
	 * 도시계획도로 추가
	 * @param data - 신규 데이터
	 * @return
	 */
	public Integer insert(@Param("data") UrbPlanMa data);
	
	/**
	 * 도시계획도로 갱신
	 * @param ftrIdn  - 관리번호
	 * @param data - 갱신할 데이터
	 * @return
	 */
	public Integer update(@Param("ftrIdn") Long ftrIdn, @Param("data") UrbPlanMa data);
	
	/**
	 * 도시계획도로 삭제
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public Integer delete(@Param("ftrIdn") Long ftrIdn);
}
