package kr.co.gitt.kworks.service.road;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.spatial.FilterBboxDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import kr.co.gitt.kworks.model.RdtRnmgDt;

/////////////////////////////////////////////
/// @class RoadService
/// kr.co.gitt.kworks.service.road \n
///   ㄴ RoadService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오전 11:11:17 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도로 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface RoadService {

	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdnCde
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public RdtRnmgDt selectOne(String rdnCde);
	
	/////////////////////////////////////////////
	/// @fn searchExtent
	/// @brief 함수 간략한 설명 : 영역 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param filterBboxDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdtRnmgDt> searchExtent(FilterBboxDTO filterBboxDTO);
	
	/////////////////////////////////////////////
	/// @fn searchFacilities
	/// @brief 함수 간략한 설명 : 시설물들 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataIds
	/// @param relationDataId
	/// @param fids
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SpatialSearchSummaryDTO> searchFacilities(List<String> dataIds, String relationDataId, List<Long> fids);
	
	/////////////////////////////////////////////
	/// @fn searchRelationRegister
	/// @brief 함수 간략한 설명 : 연관 대장 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param relationDataIds
	/// @param filterBboxDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public SpatialSearchSummaryDTO searchRelationRegister(String dataId, List<String> relationDataIds, FilterBboxDTO filterBboxDTO);
	
	/////////////////////////////////////////////
	/// @fn modifyRoad
	/// @brief 함수 간략한 설명 : 도로명 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtRnmgDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyRoad(RdtRnmgDt rdtRnmgDt);
	
}
