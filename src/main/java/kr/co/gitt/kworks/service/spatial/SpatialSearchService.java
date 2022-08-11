package kr.co.gitt.kworks.service.spatial;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchResultDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class SpatialSearchService
/// kr.co.gitt.kworks.service.spatial \n
///   ㄴ SpatialSearchService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 19. 오후 3:36:32 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 공간 검색 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface SpatialSearchService {
	
	/////////////////////////////////////////////
	/// @fn searchSummaries
	/// @brief 함수 간략한 설명 : 전체 요약 목록 검색 (다중 테이블)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param spatialSearchDTO
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SpatialSearchSummaryDTO> searchSummaries(SpatialSearchDTO spatialSearchDTO) throws Exception;

	/////////////////////////////////////////////
	/// @fn search
	/// @brief 함수 간략한 설명 : 전체 목록 검색 (다중 테이블)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param spatialSearchDTO
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SpatialSearchResultDTO> search(SpatialSearchDTO spatialSearchDTO) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn listAllSummary
	/// @brief 함수 간략한 설명 : 요약 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param spatialSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public SpatialSearchSummaryDTO listAllSummary(String dataId, SpatialSearchDTO spatialSearchDTO);
	
	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param spatialSearchDTO
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<EgovMap> listAll(String dataId, SpatialSearchDTO spatialSearchDTO) throws Exception;

	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 검색 (페이징)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param spatialSearchDTO
	/// @param paginationInfo
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<EgovMap> list(SpatialSearchDTO spatialSearchDTO, PaginationInfo paginationInfo) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param spatialSearchDTO
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public EgovMap selectOne(SpatialSearchDTO spatialSearchDTO) throws Exception;
}
