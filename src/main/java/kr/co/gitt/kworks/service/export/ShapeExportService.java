package kr.co.gitt.kworks.service.export;

import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import kr.co.gitt.kworks.model.KwsData;

import com.kmaps.framework.common.Extent;

/////////////////////////////////////////////
/// @class ShapeExportService
/// kworks.map.file.service \n
///   ㄴ ShapeExportService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 5. 27. 오전 10:55:57 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 Shape 내보내기 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface ShapeExportService {

	/////////////////////////////////////////////
	/// @fn export
	/// @brief 함수 간략한 설명 : 내보내기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param spatialSearchSummaryDTO
	/// @param path
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void export(SpatialSearchSummaryDTO spatialSearchSummaryDTO, String path, String toSrid, Extent extent) throws Exception;
	
	/**
	 * SHP 내보내기
	 * @param dataId - 데이터 아이디
	 * @param path - 경로
	 * @throws Exception
	 */
	public void export(KwsData data, String path) throws Exception;	
}
