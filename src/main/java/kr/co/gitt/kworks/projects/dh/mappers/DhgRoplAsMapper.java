package kr.co.gitt.kworks.projects.dh.mappers;

import kr.co.gitt.kworks.projects.dh.model.DhgRoplAs;

import org.apache.ibatis.annotations.Param;

/**
 * 동해시 도시계획도로 맵퍼
 * 
 * @author kwangsu.lee
 *
 */
public interface DhgRoplAsMapper {

	/**
	 * 도시계획도로 갱신
	 * 
	 * @param ftrIdn  - 관리번호
	 * @param data - 갱신할 데이터
	 * @return
	 */
	public Integer update(@Param("ftrIdn") Long ftrIdn, @Param("data") DhgRoplAs data);

}
