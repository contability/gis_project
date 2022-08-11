package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.model.OcpatDownHi;

/**
 * 점용대장 부속자료 다운로드 이력 맵퍼
 * @author kwangsu.lee
 *
 */
public interface OcpatDownHiMapper {

	/**
	 * 다운로드 이력 저장
	 * @param ocpatEditHi
	 * @return
	 */
	public Integer insert(OcpatDownHi ocpatEditHi);
	
	/**
	 * 다운로드 이력 개수
	 * @return
	 */
	public Integer listCount();
	
	/**
	 * 다운로드 검색
	 * @param searchDTO - 검색조건
	 * @return
	 */
	public List<OcpatDownHi> list(SearchDTO searchDTO);
}
