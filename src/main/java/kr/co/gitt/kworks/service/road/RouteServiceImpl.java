package kr.co.gitt.kworks.service.road;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.spatial.FilterBboxDTO;
import kr.co.gitt.kworks.mappers.RdtRoutDtMapper;
import kr.co.gitt.kworks.model.RdtRoutDt;
import kr.co.gitt.kworks.repository.RoadSearchRepository;

import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class RouteServiceImpl
/// kr.co.gitt.kworks.service.road \n
///   ㄴ RouteServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 28. 오후 5:54:53 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 노선 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("routeService")
public class RouteServiceImpl implements RouteService {

	/// 노선 대장 맵퍼
	@Resource
	RdtRoutDtMapper rdtRoutDtMapper;
	
	/// 도로 검색 저장소
	@Resource
	RoadSearchRepository roadSearchRepository;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.road.RouteService#selectOne(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public RdtRoutDt selectOne(Long ftrIdn) {
		return rdtRoutDtMapper.selectOne(ftrIdn);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.road.RouteService#searchExtent(kr.co.gitt.kworks.cmmn.dto.spatial.FilterBboxDTO)
	/////////////////////////////////////////////
	@Override
	public List<RdtRoutDt> searchExtent(FilterBboxDTO filterBboxDTO) {
		return roadSearchRepository.searchRdtRoutDtByExtent(filterBboxDTO);
	}

}
