package kr.co.gitt.kworks.service.road;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.spatial.FilterBboxDTO;
import kr.co.gitt.kworks.model.RdtRoutDt;

/////////////////////////////////////////////
/// @class RouteService
/// kr.co.gitt.kworks.service.road \n
///   ㄴ RouteService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 28. 오후 5:53:33 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도로 노선 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface RouteService {

	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public RdtRoutDt selectOne(Long ftrIdn);
	
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
	public List<RdtRoutDt> searchExtent(FilterBboxDTO filterBboxDTO);
	
}
