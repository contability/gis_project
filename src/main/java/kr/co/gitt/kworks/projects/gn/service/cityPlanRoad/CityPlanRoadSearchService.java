package kr.co.gitt.kworks.projects.gn.service.cityPlanRoad;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.cityplanroad.PlanRoadSearchDTO;
import kr.co.gitt.kworks.projects.gn.model.UrbPlanHt;
import kr.co.gitt.kworks.projects.gn.model.UrbPlanMa;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public interface CityPlanRoadSearchService {

	/**
	 * 도시계획도로 목록 검색
	 * @param searchDTO - 검색조건
	 * @param paginationInfo - 페이지 정보
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> listRegister(PlanRoadSearchDTO searchDTO, PaginationInfo paginationInfo) throws Exception;

	/**
	 * 도시계획도로 조회
	 * @param uprIdn - 교통시설 관리번호
	 * @param isOrigin - 도메인 적용 여부
	 * @return
	 * @throws Exception
	 */
	public EgovMap getRegister(String uprIdn) throws Exception;

	/**
	 * 도시계획도로 조회
	 * @param ftrIdn - 관리번호
	 * @param isOrigin - 도메인 적용 여부
	 * @return
	 * @throws Exception
	 */
	public EgovMap getRegister(Long ftrIdn, boolean isOrigin) throws Exception;

	/**
	 * 도시계획도로 삭제
	 * @param ftrIdn - 관리번호
	 * @return
	 * @throws Exception
	 */
	public Integer deleteRegister(Long ftrIdn) throws Exception;

	/**
	 * 도시계획도로 갱신
	 * @param ftrIdn - 관리번호
	 * @param data - 갱신할 데이터
	 * @return
	 * @throws Exception
	 */
	public Integer updateRegister(Long ftrIdn, UrbPlanMa data) throws Exception;
	
	/**
	 * 도시계횏도로 추가
	 * @param data - 신규 데이터
	 * @return
	 * @throws Exception
	 */
	public Integer insertRegister(UrbPlanMa data) throws Exception;
	
	/**
	 * 도기셰획도로 변경이력 목록조회
	 * @param ftrIdn - 관리번호
	 * @param isOrigin - 도메인 적용 여부
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> getHistory(Long ftrIdn, boolean isOrigin) throws Exception;	

	/**
	 * 도시계획도로 변경이력 상세조회
	 * @param ftrIdn - 관리번호
	 * @param uprSeq - 이력번호
	 * @param isOrigin - 도메인 적용 여부
	 * @return
	 * @throws Exception
	 */
	public EgovMap getDetail(Long ftrIdn, Long uprSeq, boolean isOrigin) throws Exception;

	/**
	 * 도시계획도로 변경이력 삭제
	 * @param ftrIdn - 관리번호
	 * @return
	 * @throws Exception
	 */
	public Integer deleteHistory(Long ftrIdn) throws Exception;

	/**
	 * 도시계획도로 변경이력 삭제
	 * @param ftrIdn - 관리번호
	 * @param uprSeq - 이력번호
	 * @return
	 * @throws Exception
	 */
	public Integer deleteHistory(Long ftrIdn, Long uprSeq) throws Exception;
	
	/**
	 * 도시계획도로 변경이력 갱신 
	 * @param ftrIdn - 관리번호
	 * @param uprSeq - 이력번호
	 * @param data - 갱신할 데이터
	 * @return
	 * @throws Exception
	 */
	public Integer updateHistory(Long ftrIdn, Long uprSeq, UrbPlanHt data) throws Exception;
	
	/**
	 * 도시계횏도로 변경이력 추가
	 * @param ftrIdn - 관리번호
	 * @param data - 신규 데이터
	 * @return
	 * @throws Exception
	 */
	public Integer insertHistory(Long ftrIdn, UrbPlanHt data) throws Exception;
}
