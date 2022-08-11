package kr.co.gitt.kworks.rest.spatial;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterBboxDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterComparisonDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterCqlDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterFidDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterFidsDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterRelationDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterSpatialDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO.FilterType;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsMenu;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.spatial.SpatialSearchService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class SpatialSearchController
/// kr.co.gitt.kworks.web.gis \n
///   ㄴ SpatialSearchController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | admin |
///    | Date | 2016. 8. 21. 오후 9:06:32 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 공간 검색 컨트롤러 입니다.
/// - 수정자 : 2021.02.19, setFilter 수정 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/spatial/")
public class SpatialSearchController {
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 공간 검색 서비스
	@Resource
	SpatialSearchService spatialSearchService;
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/////////////////////////////////////////////
	/// @fn searchSummaries
	/// @brief 함수 간략한 설명 : 전체 요약 목록 검색 (다중 테이블)
	/// @remark
	/// - 함수의 상세 설명 : PK값만 select
	/// @param spatialSearchDTO
	/// @param filterFidDTO
	/// @param filterFidsDTO
	/// @param filterComparisonDTO
	/// @param filterBboxDTO
	/// @param filterSpatialDTO
	/// @param filterRelationDTO
	/// @param filterCqlDTO
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="searchSummaries.do", method=RequestMethod.POST)
	public String searchSummaries(SpatialSearchDTO spatialSearchDTO, 
			FilterFidDTO filterFidDTO,
			FilterFidsDTO filterFidsDTO,
			FilterComparisonDTO filterComparisonDTO,
			FilterBboxDTO filterBboxDTO,
			FilterSpatialDTO filterSpatialDTO,
			FilterRelationDTO filterRelationDTO,
			FilterCqlDTO filterCqlDTO,
			Model model) throws Exception {
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		for(int i=spatialSearchDTO.getDataIds().size()-1; i >= 0; i--) {
			String dataId = spatialSearchDTO.getDataIds().get(i);
			boolean isAuthor = false;
			for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
				for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
					if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
						isAuthor = true;
						break;
					}
				}
			}
			if(!isAuthor) {
				spatialSearchDTO.getDataIds().remove(dataId);
			}
		}
		
		if(spatialSearchDTO.getDataIds().size() <= 0) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}

		setFilter(spatialSearchDTO, filterFidDTO, filterFidsDTO, filterComparisonDTO, filterBboxDTO, filterSpatialDTO, filterRelationDTO, filterCqlDTO);
		
		model.addAttribute("rows", spatialSearchService.searchSummaries(spatialSearchDTO));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn search
	/// @brief 함수 간략한 설명 : 전체 목록 검색 (다중 테이블)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param spatialSearchDTO
	/// @param filterFidDTO
	/// @param filterFidsDTO
	/// @param filterComparisonDTO
	/// @param filterBboxDTO
	/// @param filterSpatialDTO
	/// @param filterRelationDTO
	/// @param filterCqlDTO
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="search.do", method=RequestMethod.POST)
	public String search(SpatialSearchDTO spatialSearchDTO, 
			FilterFidDTO filterFidDTO,
			FilterFidsDTO filterFidsDTO,
			FilterComparisonDTO filterComparisonDTO,
			FilterBboxDTO filterBboxDTO,
			FilterSpatialDTO filterSpatialDTO,
			FilterRelationDTO filterRelationDTO,
			FilterCqlDTO filterCqlDTO,
			Model model) throws Exception {
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		for(int i=spatialSearchDTO.getDataIds().size()-1; i >= 0; i--) {
			String dataId = spatialSearchDTO.getDataIds().get(i);
			boolean isAuthor = false;
			for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
				for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
					if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
						isAuthor = true;
						break;
					}
				}
			}
			if(!isAuthor) {
				spatialSearchDTO.getDataIds().remove(dataId);
			}
		}
		
		if(spatialSearchDTO.getDataIds().size() <= 0) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}

		setFilter(spatialSearchDTO, filterFidDTO, filterFidsDTO, filterComparisonDTO, filterBboxDTO, filterSpatialDTO, filterRelationDTO, filterCqlDTO);
		
		model.addAttribute("rows", spatialSearchService.search(spatialSearchDTO));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listAllDataSummary
	/// @brief 함수 간략한 설명 : 요약 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param spatialSearchDTO
	/// @param filterFidDTO
	/// @param filterFidsDTO
	/// @param filterComparisonDTO
	/// @param filterBboxDTO
	/// @param filterSpatialDTO
	/// @param filterRelationDTO
	/// @param filterCqlDTO
	/// @param kwsMenu
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{dataId}/listAllSummary.do", method=RequestMethod.POST)
	public String listAllDataSummary(
			@PathVariable("dataId") String dataId,
			SpatialSearchDTO spatialSearchDTO, 
			FilterFidDTO filterFidDTO,
			FilterFidsDTO filterFidsDTO,
			FilterComparisonDTO filterComparisonDTO,
			FilterBboxDTO filterBboxDTO,
			FilterSpatialDTO filterSpatialDTO,
			FilterRelationDTO filterRelationDTO,
			FilterCqlDTO filterCqlDTO,
			KwsMenu kwsMenu,
			Model model) throws Exception {
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthor = false;
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}

		List<String> dataIds = new ArrayList<String>();
		dataIds.add(dataId);
		spatialSearchDTO.setDataIds(dataIds);
		
		setFilter(spatialSearchDTO, filterFidDTO, filterFidsDTO, filterComparisonDTO, filterBboxDTO, filterSpatialDTO, filterRelationDTO, filterCqlDTO);
		
		model.addAttribute("data", spatialSearchService.listAllSummary(dataId, spatialSearchDTO));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listAllData
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param spatialSearchDTO
	/// @param filterFidDTO
	/// @param filterFidsDTO
	/// @param filterComparisonDTO
	/// @param filterBboxDTO
	/// @param filterSpatialDTO
	/// @param filterRelationDTO
	/// @param filterCqlDTO
	/// @param kwsMenu
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{dataId}/listAll.do", method=RequestMethod.POST)
	public String listAllData(@PathVariable("dataId") String dataId, 
			SpatialSearchDTO spatialSearchDTO, 
			FilterFidDTO filterFidDTO,
			FilterFidsDTO filterFidsDTO,
			FilterComparisonDTO filterComparisonDTO,
			FilterBboxDTO filterBboxDTO,
			FilterSpatialDTO filterSpatialDTO,
			FilterRelationDTO filterRelationDTO,
			FilterCqlDTO filterCqlDTO,
			KwsMenu kwsMenu,
			Model model) throws Exception {
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthor = false;
		
		KwsData kwsData = dataService.selectOneData(dataId);
		if(StringUtils.equals(kwsData.getAuthorManageAt(), "N")) {
			isAuthor = true;
		}
		else {
			List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
			for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
				for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
					if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
						isAuthor = true;
						break;
					}
				}
			}
		}
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		setFilter(spatialSearchDTO, filterFidDTO, filterFidsDTO, filterComparisonDTO, filterBboxDTO, filterSpatialDTO, filterRelationDTO, filterCqlDTO);
		
		model.addAttribute("rows", spatialSearchService.listAll(dataId, spatialSearchDTO));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 검색 (페이징)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param spatialSearchDTO
	/// @param paginationInfo
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{dataId}/list.do", method=RequestMethod.POST)
	public String listData(@PathVariable("dataId") String dataId, 
			SpatialSearchDTO spatialSearchDTO, 
			FilterFidDTO filterFidDTO,
			FilterFidsDTO filterFidsDTO,
			FilterComparisonDTO filterComparisonDTO,
			FilterBboxDTO filterBboxDTO,
			FilterSpatialDTO filterSpatialDTO,
			FilterRelationDTO filterRelationDTO,
			FilterCqlDTO filterCqlDTO,
			PaginationInfo paginationInfo, 
			Model model) throws Exception {
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthor = false;
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		spatialSearchDTO.setDataId(dataId);
		paginationInfo.setCurrentPageNo(spatialSearchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(spatialSearchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(spatialSearchDTO.getPageSize());
		
		spatialSearchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		spatialSearchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		spatialSearchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		setFilter(spatialSearchDTO, filterFidDTO, filterFidsDTO, filterComparisonDTO, filterBboxDTO, filterSpatialDTO, filterRelationDTO, filterCqlDTO);
		
		model.addAttribute("rows", spatialSearchService.list(spatialSearchDTO, paginationInfo));
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param layerName
	/// @param fid
	/// @param spatialSearchDTO
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{dataId}/{fid}/select.do", method=RequestMethod.GET)
	public String selectOneData(@PathVariable("dataId") String dataId, @PathVariable("fid") Long fid, SpatialSearchDTO spatialSearchDTO, Model model) throws Exception {
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthor = false;
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		spatialSearchDTO.setDataId(dataId);
		spatialSearchDTO.setFilterType(FilterType.FID);
		
		FilterFidDTO filterFidDTO = new FilterFidDTO();
		filterFidDTO.setFid(fid);
		spatialSearchDTO.setFilterFidDTO(filterFidDTO);
		model.addAttribute("data", spatialSearchService.selectOne(spatialSearchDTO));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn setFilter
	/// @brief 함수 간략한 설명 : 필터 설정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param spatialSearchDTO
	/// @param filterFidDTO
	/// @param filterFidsDTO
	/// @param filterComparisonDTO
	/// @param filterBboxDTO
	/// @param filterSpatialDTO
	/// @param filterRelationDTO
	/// @param filterCqlDTO 
	///
	/// 수정자 : 이승재, 2021.02.19, 연관검색 시 연관테이블 PK컬럼명 처리 추가
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void setFilter(SpatialSearchDTO spatialSearchDTO, 
			FilterFidDTO filterFidDTO,
			FilterFidsDTO filterFidsDTO,
			FilterComparisonDTO filterComparisonDTO,
			FilterBboxDTO filterBboxDTO,
			FilterSpatialDTO filterSpatialDTO,
			FilterRelationDTO filterRelationDTO,
			FilterCqlDTO filterCqlDTO) {
		FilterType filterType = spatialSearchDTO.getFilterType();
		if(filterType != null) {
			switch(filterType) {
				case FID :
					spatialSearchDTO.setFilterFidDTO(filterFidDTO);
					break;
				case FIDS:
					spatialSearchDTO.setFilterFidsDTO(filterFidsDTO);
					break;
				case COMPARISON:
					spatialSearchDTO.setFilterComparisonDTO(filterComparisonDTO);
					break;
				case BBOX:
					spatialSearchDTO.setFilterBboxDTO(filterBboxDTO);
					break;
				case RELATION:
					KwsData relationKwsData = dataService.selectOneData(filterRelationDTO.getRelationDataId());
					List<String> relationDataPkColumnNames = dataService.getPkColumnName(relationKwsData);
					if(relationDataPkColumnNames.size() == 1) {
						filterRelationDTO.setRelationDataPkColumnName(relationDataPkColumnNames.get(0));
					}
					else if(relationDataPkColumnNames.size() == 0) {
						logger.warn("No PK : " + relationKwsData.getDataId());
						throw new RuntimeException("No PK : " + relationKwsData.getDataId());
					}
					else {
						logger.warn("PK is not the only one : " + relationKwsData.getDataId());
						throw new RuntimeException("PK is not the only one : " + relationKwsData.getDataId());
					}
					
					spatialSearchDTO.setFilterRelationDTO(filterRelationDTO);
					break;
				case SPATIAL:
					spatialSearchDTO.setFilterSpatialDTO(filterSpatialDTO);
					break;
				case CQL : 
					spatialSearchDTO.setFilterCqlDTO(filterCqlDTO);
				default:
					break;
			}
		}
	}
	
}
