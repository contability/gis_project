package kr.co.gitt.kworks.service.road;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.spatial.FilterBboxDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import kr.co.gitt.kworks.mappers.RdtRnmgDtMapper;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.RdtRnmgDt;
import kr.co.gitt.kworks.repository.RoadSearchRepository;
import kr.co.gitt.kworks.service.data.DataService;

import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class RoadServiceImpl
/// kr.co.gitt.kworks.service.road \n
///   ㄴ RoadServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오전 11:12:18 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도로 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("roadService")
public class RoadServiceImpl implements RoadService {

	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/// 도로명 맵퍼
	@Resource
	RdtRnmgDtMapper rdtRnmgDtMapper;
	
	/// 도로 검색 저장소
	@Resource
	RoadSearchRepository roadSearchRepository;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.road.RoadService#selectOne(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public RdtRnmgDt selectOne(String rdnCde) {
		return rdtRnmgDtMapper.selectOne(rdnCde);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.road.RoadService#searchExtent(kr.co.gitt.kworks.cmmn.dto.spatial.FilterBboxDTO)
	/////////////////////////////////////////////
	@Override
	public List<RdtRnmgDt> searchExtent(FilterBboxDTO filterBboxDTO) {
		return roadSearchRepository.searchRdtRnmgDtByExtent(filterBboxDTO);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.road.RoadService#searchFacilities(java.util.List, java.lang.String, java.util.List)
	/////////////////////////////////////////////
	@Override
	public List<SpatialSearchSummaryDTO> searchFacilities(List<String> dataIds, String relationDataId, List<Long> fids) {
		List<SpatialSearchSummaryDTO> result = new ArrayList<SpatialSearchSummaryDTO>();
		for(String dataId : dataIds) {
			SpatialSearchSummaryDTO spatialSearchSummaryDTO = searchFacility(dataId, relationDataId, fids);
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
	/// @see kr.co.gitt.kworks.service.road.RoadService#searchRelationRegister(java.lang.String, java.util.List, kr.co.gitt.kworks.cmmn.dto.spatial.FilterBboxDTO)
	/////////////////////////////////////////////
	@Override
	public SpatialSearchSummaryDTO searchRelationRegister(String dataId, List<String> relationDataIds, FilterBboxDTO filterBboxDTO) {
		SpatialSearchSummaryDTO result = null;
		KwsData kwsData = dataService.selectOneData(dataId);
		
		List<Long> ids = new ArrayList<Long>();
		for(String relationDataId : relationDataIds) {
			ids.addAll(roadSearchRepository.searchRelationRegister(dataId, relationDataId, filterBboxDTO));
		}
		
		if(ids != null && ids.size() > 0) {
			// 중복 제거
			HashSet<Long> hashSet = new HashSet<Long>(ids);
			List<Long> newIds = new ArrayList<Long>(hashSet);
			
			// 정렬
			Collections.sort(newIds);
			
			result = new SpatialSearchSummaryDTO();
			result.setDataId(kwsData.getDataId());
			result.setDataAlias(kwsData.getDataAlias());
			result.setIds(newIds);
		}
		
		return result;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.road.RoadService#modifyRoad(kr.co.gitt.kworks.model.RdtRnmgDt)
	/////////////////////////////////////////////
	@Override
	public Integer modifyRoad(RdtRnmgDt rdtRnmgDt) {
		return rdtRnmgDtMapper.update(rdtRnmgDt);
	}
	
	/////////////////////////////////////////////
	/// @fn searchFacility
	/// @brief 함수 간략한 설명 : 시설물 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param relationDataId
	/// @param fids
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private SpatialSearchSummaryDTO searchFacility(String dataId, String relationDataId, List<Long>fids) {
		SpatialSearchSummaryDTO result = null;
		KwsData kwsData = dataService.selectOneData(dataId);
		List<Long> ids = roadSearchRepository.searchFacility(dataId, relationDataId, fids);
		if(ids != null && ids.size() > 0) {
			result = new SpatialSearchSummaryDTO();
			result.setDataId(kwsData.getDataId());
			result.setDataAlias(kwsData.getDataAlias());
			result.setIds(ids);
		}
		return result;
	}

}
