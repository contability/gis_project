package kr.co.gitt.kworks.projects.yy.service.policy;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.yy.model.PlcyEditHi;
import kr.co.gitt.kworks.projects.yy.model.PlcyRefrHi;
import kr.co.gitt.kworks.projects.yy.model.PlcyRepoHi;

/**
 * 정책 편집 이력 서비스
 * @author wongi.Jo
 *
 */
public interface PolicyRegstrHistoryService {
	
	/**
	 * 정책 편집이력 저장
	 * @param plcyEditHi - 편집이력
	 * @return
	 * @throws Exception 
	 */
	public Integer insert(PlcyEditHi plcyEditHi) throws Exception;
	
	/**
	 * 정책 편집 편집이력 전체의 개수를 반환
	 * @return
	 * @throws Exception 
	 */
	public Integer editCount();
	
	/**
	 * 편집이력 검색
	 * @param searchDTO - 검색조건
	 * @return
	 */
	public List<PlcyEditHi> editSearch(SearchDTO searchDTO);
	
	//부속자료시작
	/**
	 * 부속자료이력 편집이력 저장
	 * @param plcyRefrHi - 편집이력
	 * @return
	 * @throws Exception 
	 */
	public Integer insertRefrHi(PlcyRefrHi plcyRefrHi) throws Exception;
	
	/**
	 * 부속자료이력 편집이력 전체의 개수를 반환
	 * @return
	 * @throws Exception 
	 */
	public Integer refrCount();
	
	/**
	 * 부속자료이력 검색
	 * @param searchDTO - 검색조건
	 * @return
	 */
	public List<PlcyRefrHi> refrSearch(SearchDTO searchDTO);
	
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
	public List<PlcyRefrHi> downloadSearch(SearchDTO searchDTO);
	
	/**
	 * 보고서 편집이력 저장
	 * @param plcyEditHi - 편집이력
	 * @return
	 * @throws Exception 
	 */
	public Integer insertRepoHi(PlcyRepoHi plcyRepoHi) throws Exception;
	
	/**
	 * 보고서 편집이력 전체의 개수를 반환
	 * @return
	 * @throws Exception 
	 */
	public Integer repoCount();
	
	/**
	 * 보고서 편집이력 검색
	 * @param searchDTO - 검색조건
	 * @return
	 */
	public List<PlcyRepoHi> repoSearch(SearchDTO searchDTO);
	

}
