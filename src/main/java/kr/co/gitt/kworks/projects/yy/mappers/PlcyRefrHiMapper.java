package kr.co.gitt.kworks.projects.yy.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.yy.model.PlcyRefrHi;

/**
 * 부속자료 편집이력 맵퍼
 * @author kwangsu.lee
 *
 */
public interface PlcyRefrHiMapper {

	/**
	 * 편집이력 저장
	 * @param ocpatEditHi - 편집이력
	 * @return
	 */
	public Integer insert(PlcyRefrHi plcyRefrHi);
	
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
	public List<PlcyRefrHi> list(SearchDTO searchDTO);
	
	/**
	 * 다운로드 이력 개수
	 * @return
	 */
	public Integer downCount();
	
	/**
	 * 다운로드 검색
	 * @param searchDTO - 검색조건
	 * @return
	 */
	public List<PlcyRefrHi> downlist(SearchDTO searchDTO);
}
