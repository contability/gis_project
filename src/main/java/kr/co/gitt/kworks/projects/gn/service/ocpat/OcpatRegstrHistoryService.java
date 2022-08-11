package kr.co.gitt.kworks.projects.gn.service.ocpat;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.model.OcpatDownHi;
import kr.co.gitt.kworks.projects.gn.model.OcpatEditHi;

/**
 * 점용대장 편집 이력 서비스
 * @author kwangsu.lee
 *
 */
public interface OcpatRegstrHistoryService {
	
	/**
	 * 편집이력 저장
	 * @param ocpatEditHi - 편집이력
	 * @return
	 * @throws Exception 
	 */
	public Integer insert(OcpatEditHi ocpatEditHi) throws Exception;
	
	/**
	 * 편집이력 전체의 개수를 반환
	 * @return
	 * @throws Exception 
	 */
	public Integer editCount();
	
	/**
	 * 편집이력 검색
	 * @param searchDTO - 검색조건
	 * @return
	 */
	public List<OcpatEditHi> editSearch(SearchDTO searchDTO);
	
	/**
	 * 다운로드 이력 저장
	 * @param ocpatEditHi - 편집이력
	 * @return
	 */
	public Integer insert(OcpatDownHi ocpatDownHi) throws Exception;
	
	/**
	 * 다운로드 이력 전체의 개수를 반환
	 * @return
	 */
	public Integer downloadCount();
	
	/**
	 * 다운로드 이력 검색
	 * @param searchDTO - 검색조건
	 * @return
	 */
	public List<OcpatDownHi> downloadSearch(SearchDTO searchDTO);
}
