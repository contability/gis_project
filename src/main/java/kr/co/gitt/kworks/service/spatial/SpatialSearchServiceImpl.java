package kr.co.gitt.kworks.service.spatial;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchResultDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.repository.SpatialSearchRepository;
import kr.co.gitt.kworks.service.data.DataService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class SpatialSearchServiceImpl
/// kr.co.gitt.kworks.service.spatial \n
///   ㄴ SpatialSearchServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 19. 오후 3:36:52 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 공간 검색 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("spatialSearchService")
public class SpatialSearchServiceImpl implements SpatialSearchService {
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());

	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/// 공간 검색 저장소
	@Resource
	SpatialSearchRepository spatialSearchRepository;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.SpatialSearchService#listAllSummaries(kr.co.gitt.kworks.cmmn.dto.SpatialSearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<SpatialSearchSummaryDTO> searchSummaries(SpatialSearchDTO spatialSearchDTO) throws Exception {
		List<SpatialSearchSummaryDTO> result = new ArrayList<SpatialSearchSummaryDTO>();
		List<String> dataIds = spatialSearchDTO.getDataIds();
		for(String dataId : dataIds) {
			SpatialSearchSummaryDTO spatialSearchSummaryDTO = listAllSummary(dataId, spatialSearchDTO);
			if(spatialSearchSummaryDTO != null) {
				result.add(spatialSearchSummaryDTO);
			}
		}
		return result;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.SpatialSearchService#searchAll(kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<SpatialSearchResultDTO> search(SpatialSearchDTO spatialSearchDTO) throws Exception {
		List<SpatialSearchResultDTO> result = new ArrayList<SpatialSearchResultDTO>();
		List<String> dataIds = spatialSearchDTO.getDataIds();
		for(String dataId : dataIds) {
			List<EgovMap> rows = listAll(dataId, spatialSearchDTO);
			if(rows != null && rows.size() > 0) {
				SpatialSearchResultDTO spatialSearchResultDTO = new SpatialSearchResultDTO();
				spatialSearchResultDTO.setDataId(dataId);
				spatialSearchResultDTO.setRows(rows);
				result.add(spatialSearchResultDTO);
			}
		}
		return result;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.SpatialSearchService#listAllSummary(java.lang.String, kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO)
	/////////////////////////////////////////////
	@Override
	public SpatialSearchSummaryDTO listAllSummary(String dataId, SpatialSearchDTO spatialSearchDTO) {
		SpatialSearchSummaryDTO result = null;
		
		KwsData kwsData = dataService.selectOneData(dataId);
		validatorDataAndDataField(kwsData, dataId);
		
		List<Long> ids = spatialSearchRepository.listAllSummary(kwsData, spatialSearchDTO);
		if(ids != null && ids.size() > 0) {
			result = new SpatialSearchSummaryDTO();
			result.setDataId(kwsData.getDataId());
			result.setDataAlias(kwsData.getDataAlias());
			result.setIds(ids);
		}
		
		return result;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.SpatialSearchService#listAll(kr.co.gitt.kworks.cmmn.dto.SpatialSearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<EgovMap> listAll(String dataId, SpatialSearchDTO spatialSearchDTO) throws Exception {
		KwsData kwsData = dataService.selectOneData(dataId);
		validatorDataAndDataField(kwsData, spatialSearchDTO.getDataId());
		return spatialSearchRepository.listAll(kwsData, spatialSearchDTO, false);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.SpatialSearchService#list(kr.co.gitt.kworks.cmmn.dto.SpatialSearchDTO, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<EgovMap> list(SpatialSearchDTO spatialSearchDTO, PaginationInfo paginationInfo) throws Exception {
		KwsData kwsData = dataService.selectOneData(spatialSearchDTO.getDataId());
		validatorDataAndDataField(kwsData, spatialSearchDTO.getDataId());
		
		Integer totalRecordCount = spatialSearchRepository.listCount(kwsData, spatialSearchDTO, false);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		return spatialSearchRepository.list(kwsData, spatialSearchDTO, false);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.SpatialSearchService#selectOne(kr.co.gitt.kworks.cmmn.dto.SpatialSearchDTO)
	/////////////////////////////////////////////
	@Override
	public EgovMap selectOne(SpatialSearchDTO spatialSearchDTO) throws Exception {
		KwsData kwsData = dataService.selectOneData(spatialSearchDTO.getDataId());
		validatorDataAndDataField(kwsData, spatialSearchDTO.getDataId());
		return spatialSearchRepository.selectOne(kwsData, spatialSearchDTO, false);
	}
	
	/////////////////////////////////////////////
	/// @fn validatorDataAndDataField
	/// @brief 함수 간략한 설명 : 데이터 와 데이터필드 값 확인
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsData
	/// @param dataId 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void validatorDataAndDataField(KwsData kwsData, String dataId) {
		/// 데이터 없음
		if(kwsData == null) {
			logger.warn("No Data : " + dataId);
			throw new RuntimeException("No Data : " + dataId);
		}
		
		List<KwsDataField> dataFields = kwsData.getKwsDataFields();
		/// 데이터 필드 없음
		if(dataFields == null || dataFields.size() < 1) {
			logger.warn("No Data Field : " + dataId);
			throw new RuntimeException("No Data Field : " + dataId);
		}
	}
}