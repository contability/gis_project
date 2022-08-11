package kr.co.gitt.kworks.projects.gn.service.ocpat;

import java.util.List;

import kr.co.gitt.kworks.model.KwsOcpatReg;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegisterSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegisterSearchSummaryDTO;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegisterSortDTO;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegisterSpatialSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegstrDTO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface OcpatRegstrService {

	/**
	 * 행정 읍면동을 갱신
	 * @return
	 */
	public Integer reloadUmdNam();
	
	/**
	 * 전제 검색
	 * @return
	 */
	public List<KwsOcpatReg> listAll();
	
	/**
	 * 레이어 정보
	 * @param ocpatIdn - 점용대장목록 관리번호
	 * @return
	 */
	public KwsOcpatReg selectOne(String ocpatIdn);

	/**
	 * 점용대장 통합검색
	 * @param ocpatRegs - 검색할 점용대장 목록
	 * @param ocpatRegisterSearchDTO - 검색조건
	 * @return
	 * @throws Exception
	 */
	public List<OcpatRegisterSearchSummaryDTO> searchSummaries(List<KwsOcpatReg> ocpatRegs, OcpatRegisterSearchDTO ocpatRegisterSearchDTO) throws Exception;
	
	/**
	 * 공간검색
	 * @param ocpatReg - 점용대장 정보
	 * @param ocpatRegBBoxSearchDTO - 검색영역
	 * @return
	 * @throws Exception
	 */
	public List<OcpatRegisterSearchSummaryDTO> searchByBBox(KwsOcpatReg ocpatReg, OcpatRegisterSpatialSearchDTO ocpatRegBBoxSearchDTO) throws Exception;
	
	/**
	 * 점용대장 정렬
	 * @param ocpatReg - 점용대장 정보
	 * @param ocpatRegisterSortDTO - 정렬 정보
	 * @return
	 * @throws Exception
	 */
	public OcpatRegisterSearchSummaryDTO sortRegister(KwsOcpatReg ocpatReg, OcpatRegisterSortDTO ocpatRegisterSortDTO) throws Exception;

	/**
	 * 점용대장 검색
	 * @param ocpatReg - 점용대장 정보
	 * @param ocpatRegisterSortDTO - 검색 정보
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> searchRegister(KwsOcpatReg ocpatReg, OcpatRegisterSortDTO ocpatRegisterSortDTO) throws Exception;
	
	/**
	 * 지번을 통한 점용대장 검색
	 * @param pnu - 지번코드
	 * @return
	 * @throws Exception 
	 */
	public List<OcpatRegisterSearchSummaryDTO> quickSearch(List<KwsOcpatReg> registerList, OcpatRegisterSpatialSearchDTO ocpatSpatialSearchDTO, String pnu) throws Exception;
	
	/**
	 * 허가번호 조회
	 * @param ftrCde - 지형지물번호
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public String getRegisterPmtNum(String ftrCde, Long ftrIdn);
	
	/**
	 * 대장의 읍면동을 갱신
	 * @param layerId - 시설물 레이어
	 * @param ftrCde - 대장 지형지물부호
	 * @param ftrIdn - 대장 관리번호
	 * @return
	 * @throws Exception
	 */
	public Integer updateRegsterUmd(String layerId, String ftrCde, Long ftrIdn) throws Exception;
	
	/**
	 * 대장을 등록
	 * @param ocpatRegstr - 대장 정보
	 * @return
	 * @throws Exception
	 */
	public Integer insertRegster(OcpatRegstrDTO ocpatRegstr) throws Exception;
}