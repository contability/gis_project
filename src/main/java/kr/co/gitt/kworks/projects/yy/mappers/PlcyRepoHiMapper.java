package kr.co.gitt.kworks.projects.yy.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.yy.model.PlcyRepoHi;

/**
 * 점용대장 편집이력 맵퍼
 * @author kwangsu.lee
 *
 */
public interface PlcyRepoHiMapper {

	/**
	 * 편집이력 저장
	 * @param ocpatEditHi - 편집이력
	 * @return
	 */
	public Integer insert(PlcyRepoHi plcyRepoHi);
	
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
	public List<PlcyRepoHi> list(SearchDTO searchDTO);
}
