package kr.co.gitt.kworks.service.analysis.topographic;

import kr.co.gitt.kworks.cmmn.dto.topographic.TopographicAnalysisDTO;
import kr.co.gitt.kworks.cmmn.dto.topographic.TopographicAnalysisResultDTO;
import kr.co.gitt.kworks.cmmn.dto.topographic.TopographicSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.topographic.TopographicSearchResultDTO;

/**
 * 지형데이터 분석 서비스 
 * @author kwangsu.lee
 *
 */
public interface TopographicAnalysisService {

	/**
	 * 지형데이터 검색
	 * @param searchDTO
	 * @return
	 * @throws Exception 
	 */
	public TopographicSearchResultDTO search(String serviceUrl, TopographicSearchDTO searchDTO) throws Exception;
	
	/**
	 * 지형단면 분석
	 * @param analysisDTO
	 * @return
	 * @throws Exception 
	 */
	public TopographicAnalysisResultDTO analysisProfile(String serviceUrl, TopographicAnalysisDTO analysisDTO) throws Exception; 
	
}
