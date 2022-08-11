package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.model.OcpatEditHi;

/**
 * 점용대장 편집이력 맵퍼
 * @author kwangsu.lee
 *
 */
public interface OcpatEditHiMapper {

	/**
	 * 편집이력 저장
	 * @param ocpatEditHi - 편집이력
	 * @return
	 */
	public Integer insert(OcpatEditHi ocpatEditHi);
	
	/**
	 * 편집이력 개수
	 * @return
	 */
	public Integer listCount();
	
	/**
	 * 편집이력 검색
	 * @param searchDTO - 검색조건
	 * @return
	 */
	public List<OcpatEditHi> list(SearchDTO searchDTO);
}
