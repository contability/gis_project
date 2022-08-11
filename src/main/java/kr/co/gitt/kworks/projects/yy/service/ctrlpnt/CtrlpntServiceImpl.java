package kr.co.gitt.kworks.projects.yy.service.ctrlpnt;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterCqlDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterFidDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO.FilterType;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.model.KwsImage.ImageTy;
import kr.co.gitt.kworks.projects.yy.dto.ControlPointDTO;
import kr.co.gitt.kworks.projects.yy.dto.ControlPointSearchDTO;
import kr.co.gitt.kworks.projects.yy.mappers.EtlCopoDtMapper;
import kr.co.gitt.kworks.projects.yy.mappers.EtlCtbpPsMapper;
import kr.co.gitt.kworks.projects.yy.mappers.EtlNtbpPsMapper;
import kr.co.gitt.kworks.projects.yy.mappers.EtlNtlpPsMapper;
import kr.co.gitt.kworks.projects.yy.mappers.EtlNttpPsMapper;
import kr.co.gitt.kworks.projects.yy.mappers.EtlPcspPsMapper;
import kr.co.gitt.kworks.projects.yy.mappers.EtlPctpPsMapper;
import kr.co.gitt.kworks.projects.yy.mappers.EtlPctsPsMapper;
import kr.co.gitt.kworks.projects.yy.model.EtlCopoDt;
import kr.co.gitt.kworks.projects.yy.model.EtlCtbpPs;
import kr.co.gitt.kworks.projects.yy.model.EtlNtbpPs;
import kr.co.gitt.kworks.projects.yy.model.EtlNtlpPs;
import kr.co.gitt.kworks.projects.yy.model.EtlNttpPs;
import kr.co.gitt.kworks.projects.yy.model.EtlPcspPs;
import kr.co.gitt.kworks.projects.yy.model.EtlPctpPs;
import kr.co.gitt.kworks.projects.yy.model.EtlPctsPs;
import kr.co.gitt.kworks.service.cmmn.ImageService;
import kr.co.gitt.kworks.service.spatial.SpatialSearchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/////////////////////////////////////////////
/// @class CtrlpntServiceImpl
/// kr.co.gitt.kworks.projects.gn.service.ctrlpnt \n
///   ㄴ CtrlpntServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 14. 오후 3:51:07 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 기준점 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("ctrlpntService")
@Profile({"yy_dev", "yy_oper", "gds_dev", "gds_oper"})
public class CtrlpntServiceImpl implements CtrlpntService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 공간검색 서비스
	 */
	@Resource
	SpatialSearchService spatialSearchService;
	
	/**
	 * 통합기준점
	 */
	@Resource
	EtlNtbpPsMapper etlNtbpPsMapper;
	
	@Resource
	EgovIdGnrService etlNtbpPsIdGnrService;
	
	/**
	 * 삼각점
	 */
	@Resource
	EtlNttpPsMapper etlNttpPsMapper;

	@Resource
	EgovIdGnrService etlNttpPsIdGnrService;
	
	/**
	 * 수준점
	 */
	@Resource
	EtlNtlpPsMapper etlNtlpPsMapper;
	
	@Resource
	EgovIdGnrService etlNtlpPsIdGnrService;

	/**
	 * 지적도근점
	 */
	@Resource
	EtlPcspPsMapper etlPcspPsMapper;
	
	@Resource
	EgovIdGnrService etlPcspPsIdGnrService;
	
	/**
	 * 지적삼각점
	 */
	@Resource
	EtlPctpPsMapper etlPctpPsMapper;
	
	@Resource
	EgovIdGnrService etlPctpPsIdGnrService;
	
	/**
	 * 지적삼각보조점
	 */
	@Resource
	EtlPctsPsMapper etlPctsPsMapper;
	
	@Resource
	EgovIdGnrService etlPctsPsIdGnrService;
	
	/**
	 * 도시기준점
	 */
	@Resource
	EtlCtbpPsMapper etlCtbpPsMapper;
	
	@Resource
	EgovIdGnrService etlCtbpPsIdGnrService;

	/**
	 * 도시기준점
	 */
	@Resource
	EtlCopoDtMapper etlCopoDtMapper;
	
	@Resource
	EgovIdGnrService etlCopoDtIdGnrService;
	
	
	/**
	 *  이미지 서비스
	 */
	@Resource
	ImageService imageService;
	
	
	@Override
	public List<SpatialSearchSummaryDTO> searchSummaries(List<String> dataIds, ControlPointSearchDTO searchDTO) throws Exception {
		
		String bseNam = searchDTO.getBseNam();
		String strYmd = searchDTO.getStrYmd();
		String endYmd = searchDTO.getEndYmd();
		String setLoc = searchDTO.getSetLoc();
		
		StringBuilder cql = new StringBuilder();
		if (bseNam != null && bseNam.length() > 0) {
			cql.append("BSE_NAM LIKE '%").append(bseNam).append("%'");
		}
		
		if (strYmd != null && strYmd.length() > 0) {
			if (cql.length() > 0)
				cql.append(" AND ");
			cql.append("SET_YMD >= '").append(strYmd).append("'");
		}
		
		if (endYmd != null && endYmd.length() > 0) {
			if (cql.length() > 0)
				cql.append(" AND ");
			cql.append("SET_YMD <= '").append(endYmd).append("'");
		}
		
		if (setLoc != null && setLoc.length() > 0) {
			if (cql.length() > 0)
				cql.append(" AND ");
			cql.append("SET_LOC LIKE '%").append(setLoc).append("%'");
		}
		
		SpatialSearchDTO search = new SpatialSearchDTO();		
		search.setDataIds(dataIds);
		search.setFilterType(FilterType.CQL);
		search.setIsOriginColumnValue(true);

		FilterCqlDTO filter = new FilterCqlDTO(); 
		filter.setCql(cql.toString());
		search.setFilterCqlDTO(filter);
		
		List<SpatialSearchSummaryDTO> result = spatialSearchService.searchSummaries(search);
		return result;
	}
	
	@Override
	public EgovMap getRegister(String dataId, Long ftrIdn, boolean isOriginColumnValue) throws Exception {
		
		SpatialSearchDTO searchDTO = new SpatialSearchDTO();		
		searchDTO.setDataId(dataId);
		searchDTO.setFilterType(FilterType.FID);
		searchDTO.setIsOriginColumnValue(isOriginColumnValue);
		
		FilterFidDTO filterFidDTO = new FilterFidDTO();
		filterFidDTO.setFid(ftrIdn);
		searchDTO.setFilterFidDTO(filterFidDTO);
		
		EgovMap egovMap = spatialSearchService.selectOne(searchDTO);	
		return egovMap;
	}
	
	@Override
	public Integer insertRegister(ControlPointDTO registerDTO) throws Exception {
		Integer result = -1;
		
		String ftrCde = registerDTO.getFtrCde();

		if (ftrCde.equalsIgnoreCase("ZA070")) {
			result = insertEtlNtbpPs(registerDTO);
		}
		else if (ftrCde.equalsIgnoreCase("ZA071")) {
			result = insertEtlNttpPs(registerDTO);
		}
		else if (ftrCde.equalsIgnoreCase("ZA072")) {
			result = insertEtlNtlpPs(registerDTO);
		}
		else if (ftrCde.equalsIgnoreCase("ZA080")) {
			result = insertEtlPcspPs(registerDTO);
		}
		else if (ftrCde.equalsIgnoreCase("ZA081")) {
			result = insertEtlPctpPs(registerDTO);
		}
		else if (ftrCde.equalsIgnoreCase("ZA082")) {
			result = insertEtlPctsPs(registerDTO);
		}
		else if (ftrCde.equalsIgnoreCase("ZA090")) {
			result = insertEtlCtbpPs(registerDTO);
		}
		
		return result;
	}
	
	/**
	 * 통합기준점 등록
	 * @param registerDTO - 기준점 정보
	 * @return
	 * @throws Exception
	 */
	private Integer insertEtlNtbpPs(ControlPointDTO registerDTO) throws Exception {
		
		EtlNtbpPs newData = new EtlNtbpPs();
		newData.setFtrCde(registerDTO.getFtrCde());
		newData.setBseNam(registerDTO.getBseNam());
		newData.setPntNam(registerDTO.getPntNam());
		newData.setNbgX(registerDTO.getNbgX());
		newData.setNbgY(registerDTO.getNbgY());
		newData.setNggX(registerDTO.getNggX());
		newData.setNggY(registerDTO.getNggY());
		newData.setNgwB(registerDTO.getNgwB());
		newData.setNgwL(registerDTO.getNgwL());
		newData.setBseHgt(registerDTO.getBseHgt());
		newData.setMerCon(registerDTO.getMerCon());
		newData.setSetLoc(registerDTO.getSetLoc());
		newData.setSetYmd(registerDTO.getSetYmd());
		newData.setSetCde(registerDTO.getSetCde());
		newData.setSetMet(registerDTO.getSetMet());
		newData.setRmkDes(registerDTO.getRmkDes());
		
		newData.setWkt(registerDTO.getWkt());
		newData.setBjdCde(registerDTO.getBjdCde());
		newData.setHjdCde(registerDTO.getHjdCde());
		
		Long ftrIdn = etlNtbpPsIdGnrService.getNextLongId();
		newData.setFtrIdn(ftrIdn);
		
		Integer result = etlNtbpPsMapper.insert(newData);
		return result;
	}

	/**
	 * 삼각점 등록
	 * @param registerDTO - 기준점 정보
	 * @return
	 * @throws Exception
	 */
	private Integer insertEtlNttpPs(ControlPointDTO registerDTO) throws Exception {
		
		EtlNttpPs newData = new EtlNttpPs();
		newData.setFtrCde(registerDTO.getFtrCde());
		newData.setBseNam(registerDTO.getBseNam());
		newData.setPntNam(registerDTO.getPntNam());
		newData.setNbgX(registerDTO.getNbgX());
		newData.setNbgY(registerDTO.getNbgY());
		newData.setNggX(registerDTO.getNggX());
		newData.setNggY(registerDTO.getNggY());
		newData.setNgwB(registerDTO.getNgwB());
		newData.setNgwL(registerDTO.getNgwL());
		newData.setBseHgt(registerDTO.getBseHgt());
		newData.setMerCon(registerDTO.getMerCon());
		newData.setSetLoc(registerDTO.getSetLoc());
		newData.setSetYmd(registerDTO.getSetYmd());
		newData.setSetCde(registerDTO.getSetCde());
		newData.setSetMet(registerDTO.getSetMet());
		newData.setRmkDes(registerDTO.getRmkDes());
		
		newData.setWkt(registerDTO.getWkt());
		newData.setBjdCde(registerDTO.getBjdCde());
		newData.setHjdCde(registerDTO.getHjdCde());
		
		Long ftrIdn = etlNttpPsIdGnrService.getNextLongId();
		newData.setFtrIdn(ftrIdn);
		
		Integer result = etlNttpPsMapper.insert(newData);
		return result;
	}
	
	/**
	 * 수준점 등록
	 * @param registerDTO - 기준점 정보
	 * @return
	 * @throws Exception
	 */
	private Integer insertEtlNtlpPs(ControlPointDTO registerDTO) throws Exception {
		
		EtlNtlpPs newData = new EtlNtlpPs();
		newData.setFtrCde(registerDTO.getFtrCde());
		newData.setBseNam(registerDTO.getBseNam());
		newData.setPntNam(registerDTO.getPntNam());
		newData.setNbgX(registerDTO.getNbgX());
		newData.setNbgY(registerDTO.getNbgY());
		newData.setNggX(registerDTO.getNggX());
		newData.setNggY(registerDTO.getNggY());
		newData.setNgwB(registerDTO.getNgwB());
		newData.setNgwL(registerDTO.getNgwL());
		newData.setBseHgt(registerDTO.getBseHgt());
		newData.setMerCon(registerDTO.getMerCon());
		newData.setSetLoc(registerDTO.getSetLoc());
		newData.setSetYmd(registerDTO.getSetYmd());
		newData.setSetCde(registerDTO.getSetCde());
		newData.setSetMet(registerDTO.getSetMet());
		newData.setRmkDes(registerDTO.getRmkDes());
		
		newData.setWkt(registerDTO.getWkt());
		newData.setBjdCde(registerDTO.getBjdCde());
		newData.setHjdCde(registerDTO.getHjdCde());
		
		Long ftrIdn = etlNtlpPsIdGnrService.getNextLongId();
		newData.setFtrIdn(ftrIdn);
		
		Integer result = etlNtlpPsMapper.insert(newData);
		return result;
	}
	
	/**
	 * 지적도근점 등록
	 * @param registerDTO - 기준점 정보
	 * @return
	 * @throws Exception
	 */
	private Integer insertEtlPcspPs(ControlPointDTO registerDTO) throws Exception {
		
		EtlPcspPs newData = new EtlPcspPs();
		newData.setFtrCde(registerDTO.getFtrCde());
		newData.setBseNam(registerDTO.getBseNam());
		newData.setPntNam(registerDTO.getPntNam());
		newData.setNbgX(registerDTO.getNbgX());
		newData.setNbgY(registerDTO.getNbgY());
		newData.setNggX(registerDTO.getNggX());
		newData.setNggY(registerDTO.getNggY());
		newData.setNgwB(registerDTO.getNgwB());
		newData.setNgwL(registerDTO.getNgwL());
		newData.setBseHgt(registerDTO.getBseHgt());
		newData.setMerCon(registerDTO.getMerCon());
		newData.setSetLoc(registerDTO.getSetLoc());
		newData.setSetYmd(registerDTO.getSetYmd());
		newData.setSetCde(registerDTO.getSetCde());
		newData.setSetMet(registerDTO.getSetMet());
		newData.setRmkDes(registerDTO.getRmkDes());
		
		newData.setWkt(registerDTO.getWkt());
		newData.setBjdCde(registerDTO.getBjdCde());
		newData.setHjdCde(registerDTO.getHjdCde());
		
		Long ftrIdn = etlPcspPsIdGnrService.getNextLongId();
		newData.setFtrIdn(ftrIdn);
		
		Integer result = etlPcspPsMapper.insert(newData);
		return result;
	}
	
	/**
	 * 지적삼각점 등록
	 * @param registerDTO - 기준점 정보
	 * @return
	 * @throws Exception
	 */
	private Integer insertEtlPctpPs(ControlPointDTO registerDTO) throws Exception {
		
		EtlPctpPs newData = new EtlPctpPs();
		newData.setFtrCde(registerDTO.getFtrCde());
		newData.setBseNam(registerDTO.getBseNam());
		newData.setPntNam(registerDTO.getPntNam());
		newData.setNbgX(registerDTO.getNbgX());
		newData.setNbgY(registerDTO.getNbgY());
		newData.setNggX(registerDTO.getNggX());
		newData.setNggY(registerDTO.getNggY());
		newData.setNgwB(registerDTO.getNgwB());
		newData.setNgwL(registerDTO.getNgwL());
		newData.setBseHgt(registerDTO.getBseHgt());
		newData.setMerCon(registerDTO.getMerCon());
		newData.setSetLoc(registerDTO.getSetLoc());
		newData.setSetYmd(registerDTO.getSetYmd());
		newData.setSetCde(registerDTO.getSetCde());
		newData.setSetMet(registerDTO.getSetMet());
		newData.setRmkDes(registerDTO.getRmkDes());
		
		newData.setWkt(registerDTO.getWkt());
		newData.setBjdCde(registerDTO.getBjdCde());
		newData.setHjdCde(registerDTO.getHjdCde());
		
		Long ftrIdn = etlPctpPsIdGnrService.getNextLongId();
		newData.setFtrIdn(ftrIdn);
		
		Integer result = etlPctpPsMapper.insert(newData);
		return result;
	}
	
	/**
	 * 지적삼각보조점 등록
	 * @param registerDTO - 기준점 정보
	 * @return
	 * @throws Exception
	 */
	private Integer insertEtlPctsPs(ControlPointDTO registerDTO) throws Exception {
		
		EtlPctsPs newData = new EtlPctsPs();
		newData.setFtrCde(registerDTO.getFtrCde());
		newData.setBseNam(registerDTO.getBseNam());
		newData.setPntNam(registerDTO.getPntNam());
		newData.setNbgX(registerDTO.getNbgX());
		newData.setNbgY(registerDTO.getNbgY());
		newData.setNggX(registerDTO.getNggX());
		newData.setNggY(registerDTO.getNggY());
		newData.setNgwB(registerDTO.getNgwB());
		newData.setNgwL(registerDTO.getNgwL());
		newData.setBseHgt(registerDTO.getBseHgt());
		newData.setMerCon(registerDTO.getMerCon());
		newData.setSetLoc(registerDTO.getSetLoc());
		newData.setSetYmd(registerDTO.getSetYmd());
		newData.setSetCde(registerDTO.getSetCde());
		newData.setSetMet(registerDTO.getSetMet());
		newData.setRmkDes(registerDTO.getRmkDes());
		
		newData.setWkt(registerDTO.getWkt());
		newData.setBjdCde(registerDTO.getBjdCde());
		newData.setHjdCde(registerDTO.getHjdCde());
		
		Long ftrIdn = etlPctsPsIdGnrService.getNextLongId();
		newData.setFtrIdn(ftrIdn);
		
		Integer result = etlPctsPsMapper.insert(newData);
		return result;
	}
	
	/**
	 * 도시기준점 등록
	 * @param registerDTO - 기준점 정보
	 * @return
	 * @throws Exception
	 */
	private Integer insertEtlCtbpPs(ControlPointDTO registerDTO) throws Exception {
		
		EtlCtbpPs newData = new EtlCtbpPs();
		newData.setFtrCde(registerDTO.getFtrCde());
		newData.setBseNam(registerDTO.getBseNam());
		newData.setPntNam(registerDTO.getPntNam());
		newData.setNbgX(registerDTO.getNbgX());
		newData.setNbgY(registerDTO.getNbgY());
		newData.setNggX(registerDTO.getNggX());
		newData.setNggY(registerDTO.getNggY());
		newData.setNgwB(registerDTO.getNgwB());
		newData.setNgwL(registerDTO.getNgwL());
		newData.setBseHgt(registerDTO.getBseHgt());
		newData.setMerCon(registerDTO.getMerCon());
		newData.setSetLoc(registerDTO.getSetLoc());
		newData.setSetYmd(registerDTO.getSetYmd());
		newData.setSetCde(registerDTO.getSetCde());
		newData.setSetMet(registerDTO.getSetMet());
		newData.setRmkDes(registerDTO.getRmkDes());
		
		newData.setWkt(registerDTO.getWkt());
		newData.setBjdCde(registerDTO.getBjdCde());
		newData.setHjdCde(registerDTO.getHjdCde());
		
		Long ftrIdn = etlCtbpPsIdGnrService.getNextLongId();
		newData.setFtrIdn(ftrIdn);
		
		Integer result = etlCtbpPsMapper.insert(newData);
		return result;
	}

	@Override
	public Integer updateRegister(Long ftrIdn, ControlPointDTO registerDTO) throws Exception {
		Integer result = -1;
		
		String ftrCde = registerDTO.getFtrCde();

		if (ftrCde.equalsIgnoreCase("ZA070")) {
			result = updateEtlNtbpPs(ftrIdn, registerDTO);
		}
		else if (ftrCde.equalsIgnoreCase("ZA071")) {
			result = updateEtlNttpPs(ftrIdn, registerDTO);
		}
		else if (ftrCde.equalsIgnoreCase("ZA072")) {
			result = updateEtlNtlpPs(ftrIdn, registerDTO);
		}
		else if (ftrCde.equalsIgnoreCase("ZA080")) {
			result = updateEtlPcspPs(ftrIdn, registerDTO);
		}
		else if (ftrCde.equalsIgnoreCase("ZA081")) {
			result = updateEtlPctpPs(ftrIdn, registerDTO);
		}
		else if (ftrCde.equalsIgnoreCase("ZA082")) {
			result = updateEtlPctsPs(ftrIdn, registerDTO);
		}
		else if (ftrCde.equalsIgnoreCase("ZA090")) {
			result = updateEtlCtbpPs(ftrIdn, registerDTO);
		}
		
		return result;
	}
	
	/**
	 * 통합기준점 변경
	 * @param ftrIdn - 관리번호
	 * @param registerDTO - 기준점 정보
	 * @return
	 * @throws Exception
	 */
	private Integer updateEtlNtbpPs(Long ftrIdn, ControlPointDTO registerDTO) throws Exception {
		
		EtlNtbpPs newData = new EtlNtbpPs();
		newData.setFtrIdn(ftrIdn);
		newData.setFtrCde(registerDTO.getFtrCde());
		newData.setBseNam(registerDTO.getBseNam());
		newData.setPntNam(registerDTO.getPntNam());
		newData.setNbgX(registerDTO.getNbgX());
		newData.setNbgY(registerDTO.getNbgY());
		newData.setNggX(registerDTO.getNggX());
		newData.setNggY(registerDTO.getNggY());
		newData.setNgwB(registerDTO.getNgwB());
		newData.setNgwL(registerDTO.getNgwL());
		newData.setBseHgt(registerDTO.getBseHgt());
		newData.setMerCon(registerDTO.getMerCon());
		newData.setSetLoc(registerDTO.getSetLoc());
		newData.setSetYmd(registerDTO.getSetYmd());
		newData.setSetCde(registerDTO.getSetCde());
		newData.setSetMet(registerDTO.getSetMet());
		newData.setRmkDes(registerDTO.getRmkDes());
		
		newData.setWkt(registerDTO.getWkt());
		newData.setBjdCde(registerDTO.getBjdCde());
		newData.setHjdCde(registerDTO.getHjdCde());
		
		Integer result = etlNtbpPsMapper.update(newData);
		return result;
	}
	
	/**
	 * 삼각점 변경
	 * @param ftrIdn - 관리번호 
	 * @param registerDTO - 기준점 정보
	 * @return
	 * @throws Exception
	 */
	private Integer updateEtlNttpPs(Long ftrIdn, ControlPointDTO registerDTO) throws Exception {
		
		EtlNttpPs newData = new EtlNttpPs();
		newData.setFtrIdn(ftrIdn);
		newData.setFtrCde(registerDTO.getFtrCde());
		newData.setBseNam(registerDTO.getBseNam());
		newData.setPntNam(registerDTO.getPntNam());
		newData.setNbgX(registerDTO.getNbgX());
		newData.setNbgY(registerDTO.getNbgY());
		newData.setNggX(registerDTO.getNggX());
		newData.setNggY(registerDTO.getNggY());
		newData.setNgwB(registerDTO.getNgwB());
		newData.setNgwL(registerDTO.getNgwL());
		newData.setBseHgt(registerDTO.getBseHgt());
		newData.setMerCon(registerDTO.getMerCon());
		newData.setSetLoc(registerDTO.getSetLoc());
		newData.setSetYmd(registerDTO.getSetYmd());
		newData.setSetCde(registerDTO.getSetCde());
		newData.setSetMet(registerDTO.getSetMet());
		newData.setRmkDes(registerDTO.getRmkDes());
		
		newData.setWkt(registerDTO.getWkt());
		newData.setBjdCde(registerDTO.getBjdCde());
		newData.setHjdCde(registerDTO.getHjdCde());
		
		Integer result = etlNttpPsMapper.update(newData);
		return result;
	}
	
	/**
	 * 수준점 변경
	 * @param ftrIdn - 관리번호 
	 * @param registerDTO - 기준점 정보
	 * @return
	 * @throws Exception
	 */
	private Integer updateEtlNtlpPs(Long ftrIdn, ControlPointDTO registerDTO) throws Exception {
		
		EtlNtlpPs newData = new EtlNtlpPs();
		newData.setFtrIdn(ftrIdn);
		newData.setFtrCde(registerDTO.getFtrCde());
		newData.setBseNam(registerDTO.getBseNam());
		newData.setPntNam(registerDTO.getPntNam());
		newData.setNbgX(registerDTO.getNbgX());
		newData.setNbgY(registerDTO.getNbgY());
		newData.setNggX(registerDTO.getNggX());
		newData.setNggY(registerDTO.getNggY());
		newData.setNgwB(registerDTO.getNgwB());
		newData.setNgwL(registerDTO.getNgwL());
		newData.setBseHgt(registerDTO.getBseHgt());
		newData.setMerCon(registerDTO.getMerCon());
		newData.setSetLoc(registerDTO.getSetLoc());
		newData.setSetYmd(registerDTO.getSetYmd());
		newData.setSetCde(registerDTO.getSetCde());
		newData.setSetMet(registerDTO.getSetMet());
		newData.setRmkDes(registerDTO.getRmkDes());
		
		newData.setWkt(registerDTO.getWkt());
		newData.setBjdCde(registerDTO.getBjdCde());
		newData.setHjdCde(registerDTO.getHjdCde());
		
		Integer result = etlNtlpPsMapper.update(newData);
		return result;
	}
	
	/**
	 * 지적도근점 변경
	 * @param ftrIdn - 관리번호 
	 * @param registerDTO - 기준점 정보
	 * @return
	 * @throws Exception
	 */
	private Integer updateEtlPcspPs(Long ftrIdn, ControlPointDTO registerDTO) throws Exception {
		
		EtlPcspPs newData = new EtlPcspPs();
		newData.setFtrIdn(ftrIdn);
		newData.setFtrCde(registerDTO.getFtrCde());
		newData.setBseNam(registerDTO.getBseNam());
		newData.setPntNam(registerDTO.getPntNam());
		newData.setNbgX(registerDTO.getNbgX());
		newData.setNbgY(registerDTO.getNbgY());
		newData.setNggX(registerDTO.getNggX());
		newData.setNggY(registerDTO.getNggY());
		newData.setNgwB(registerDTO.getNgwB());
		newData.setNgwL(registerDTO.getNgwL());
		newData.setBseHgt(registerDTO.getBseHgt());
		newData.setMerCon(registerDTO.getMerCon());
		newData.setSetLoc(registerDTO.getSetLoc());
		newData.setSetYmd(registerDTO.getSetYmd());
		newData.setSetCde(registerDTO.getSetCde());
		newData.setSetMet(registerDTO.getSetMet());
		newData.setRmkDes(registerDTO.getRmkDes());
		
		newData.setWkt(registerDTO.getWkt());
		newData.setBjdCde(registerDTO.getBjdCde());
		newData.setHjdCde(registerDTO.getHjdCde());
		
		Integer result = etlPcspPsMapper.update(newData);
		return result;
	}
	
	/**
	 * 지적삼각점 변경
	 * @param ftrIdn - 관리번호 
	 * @param registerDTO - 기준점 정보
	 * @return
	 * @throws Exception
	 */
	private Integer updateEtlPctpPs(Long ftrIdn, ControlPointDTO registerDTO) throws Exception {
		
		EtlPctpPs newData = new EtlPctpPs();
		newData.setFtrIdn(ftrIdn);
		newData.setFtrCde(registerDTO.getFtrCde());
		newData.setBseNam(registerDTO.getBseNam());
		newData.setPntNam(registerDTO.getPntNam());
		newData.setNbgX(registerDTO.getNbgX());
		newData.setNbgY(registerDTO.getNbgY());
		newData.setNggX(registerDTO.getNggX());
		newData.setNggY(registerDTO.getNggY());
		newData.setNgwB(registerDTO.getNgwB());
		newData.setNgwL(registerDTO.getNgwL());
		newData.setBseHgt(registerDTO.getBseHgt());
		newData.setMerCon(registerDTO.getMerCon());
		newData.setSetLoc(registerDTO.getSetLoc());
		newData.setSetYmd(registerDTO.getSetYmd());
		newData.setSetCde(registerDTO.getSetCde());
		newData.setSetMet(registerDTO.getSetMet());
		newData.setRmkDes(registerDTO.getRmkDes());
		
		newData.setWkt(registerDTO.getWkt());
		newData.setBjdCde(registerDTO.getBjdCde());
		newData.setHjdCde(registerDTO.getHjdCde());
		
		Integer result = etlPctpPsMapper.update(newData);
		return result;
	}
	
	/**
	 * 지적삼각보조점 변경
	 * @param registerDTO - 기준점 정보
	 * @param ftrIdn - 관리번호 
	 * @return
	 * @throws Exception
	 */
	private Integer updateEtlPctsPs(Long ftrIdn, ControlPointDTO registerDTO) throws Exception {
		
		EtlPctsPs newData = new EtlPctsPs();
		newData.setFtrIdn(ftrIdn);
		newData.setFtrCde(registerDTO.getFtrCde());
		newData.setBseNam(registerDTO.getBseNam());
		newData.setPntNam(registerDTO.getPntNam());
		newData.setNbgX(registerDTO.getNbgX());
		newData.setNbgY(registerDTO.getNbgY());
		newData.setNggX(registerDTO.getNggX());
		newData.setNggY(registerDTO.getNggY());
		newData.setNgwB(registerDTO.getNgwB());
		newData.setNgwL(registerDTO.getNgwL());
		newData.setBseHgt(registerDTO.getBseHgt());
		newData.setMerCon(registerDTO.getMerCon());
		newData.setSetLoc(registerDTO.getSetLoc());
		newData.setSetYmd(registerDTO.getSetYmd());
		newData.setSetCde(registerDTO.getSetCde());
		newData.setSetMet(registerDTO.getSetMet());
		newData.setRmkDes(registerDTO.getRmkDes());
		
		newData.setWkt(registerDTO.getWkt());
		newData.setBjdCde(registerDTO.getBjdCde());
		newData.setHjdCde(registerDTO.getHjdCde());
		
		Integer result = etlPctsPsMapper.update(newData);
		return result;
	}
	
	/**
	 * 도시기준점 변경
	 * @param ftrIdn - 관리번호 
	 * @param registerDTO - 기준점 정보
	 * @return
	 * @throws Exception
	 */
	private Integer updateEtlCtbpPs(Long ftrIdn, ControlPointDTO registerDTO) throws Exception {
		
		EtlCtbpPs newData = new EtlCtbpPs();
		newData.setFtrIdn(ftrIdn);
		newData.setFtrCde(registerDTO.getFtrCde());
		newData.setBseNam(registerDTO.getBseNam());
		newData.setPntNam(registerDTO.getPntNam());
		newData.setNbgX(registerDTO.getNbgX());
		newData.setNbgY(registerDTO.getNbgY());
		newData.setNggX(registerDTO.getNggX());
		newData.setNggY(registerDTO.getNggY());
		newData.setNgwB(registerDTO.getNgwB());
		newData.setNgwL(registerDTO.getNgwL());
		newData.setBseHgt(registerDTO.getBseHgt());
		newData.setMerCon(registerDTO.getMerCon());
		newData.setSetLoc(registerDTO.getSetLoc());
		newData.setSetYmd(registerDTO.getSetYmd());
		newData.setSetCde(registerDTO.getSetCde());
		newData.setSetMet(registerDTO.getSetMet());
		newData.setRmkDes(registerDTO.getRmkDes());
		
		newData.setWkt(registerDTO.getWkt());
		newData.setBjdCde(registerDTO.getBjdCde());
		newData.setHjdCde(registerDTO.getHjdCde());
		
		Integer result = etlCtbpPsMapper.update(newData);
		return result;
	}
	
	@Override
	public Integer deleteRegister(String ftrCde, Long ftrIdn) throws Exception {
		Integer result = -1;
		
		if (ftrCde.equalsIgnoreCase("ZA070")) {
			result = etlNtbpPsMapper.delete(ftrIdn);
		}
		else if (ftrCde.equalsIgnoreCase("ZA071")) {
			result = etlNttpPsMapper.delete(ftrIdn);
		}
		else if (ftrCde.equalsIgnoreCase("ZA072")) {
			result = etlNtlpPsMapper.delete(ftrIdn);
		}
		else if (ftrCde.equalsIgnoreCase("ZA080")) {
			result = etlPcspPsMapper.delete(ftrIdn);
		}
		else if (ftrCde.equalsIgnoreCase("ZA081")) {
			result = etlPctpPsMapper.delete(ftrIdn);
		}
		else if (ftrCde.equalsIgnoreCase("ZA082")) {
			result = etlPctsPsMapper.delete(ftrIdn);
		}
		else if (ftrCde.equalsIgnoreCase("ZA090")) {
			result = etlCtbpPsMapper.delete(ftrIdn);
		}
		
		etlCopoDtMapper.deleteById(ftrCde, ftrIdn);
		return result;
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////
	// 시준점
	//
	@Override
	public List<EtlCopoDt> listPassPoint(String ftrCde, Long ftrIdn) throws Exception {
	
		return etlCopoDtMapper.selectByIdn(ftrCde, ftrIdn);
	}
	
	@Override
	public EtlCopoDt selectassPointByNo(Long ecpNo) throws Exception {
		
		return etlCopoDtMapper.selectOneByNo(ecpNo);
	}
	
	@Override
	public Integer insertPassPoint(EtlCopoDt data) throws Exception {
		
		Long ecpNo = etlCopoDtIdGnrService.getNextLongId();
		data.setEcpNo(ecpNo);
		
		Integer result = etlCopoDtMapper.insert(data);
		return result;
	}
	
	@Override
	public Integer updatePassPoint(Long ecpNo, EtlCopoDt data) throws Exception {
		
		data.setEcpNo(ecpNo);
		return etlCopoDtMapper.update(data);
	}

	@Override
	public Integer deletePassPoint(Long ecpNo) throws Exception {
		
		return etlCopoDtMapper.delete(ecpNo);
	}

	@Override
	public Integer deletePassPoint(String ftrCde, Long ftrIdn) throws Exception {
		
		return etlCopoDtMapper.deleteById(ftrCde, ftrIdn);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	// 사진
	//
	@Override
	public List<KwsImage> listPhoto(String ftrCde, Long ftrIdn) throws Exception {
	
		KwsImage cql = new KwsImage();
		cql.setFtrCde(ftrCde);
		cql.setFtrIdn(ftrIdn);
		cql.setImageTy(ImageTy.SURVEY_BASE_POINT);
		
		return imageService.listAllImage(cql);
	}
	
	@Override
	public KwsImage selectPhotoByNo(Long imageNo) throws Exception {
		
		return imageService.selectOneImage(imageNo);
	}
	
	@Override
	public KwsImage insertPhoto(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception {

		return imageService.addImage(imageDTO, multipartFile, thumbnailWidth, thumbnailHeight);
	}
	
	@Override
	public KwsImage updatePhoto(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception {

		return imageService.modifyImage(imageDTO, multipartFile, thumbnailWidth, thumbnailHeight);
	}
}
