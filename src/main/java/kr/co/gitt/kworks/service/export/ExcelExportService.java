package kr.co.gitt.kworks.service.export;

import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;

import com.kmaps.framework.common.Extent;

/////////////////////////////////////////////
/// @class ExcelExportService
/// kworks.map.file.service \n
///   ㄴ ExcelExportService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 5. 25. 오전 10:35:43 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 엑셀 내보내기 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface ExcelExportService {
	
	/////////////////////////////////////////////
	/// @fn export
	/// @brief 함수 간략한 설명 : 필터FIDS 내보내기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param spatialSearchSummaryDTO
	/// @param isFieldName
	/// @param path
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void fidsExport(SpatialSearchSummaryDTO spatialSearchSummaryDTO, boolean isFieldName, String path, String toSrid, Extent extent) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn export
	/// @brief 함수 간략한 설명 : 필터FIDS, 좌표포험 내보내기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param spatialSearchSummaryDTO
	/// @param isFieldName
	/// @param path
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void fidsExportwithCoordinates(SpatialSearchSummaryDTO spatialSearchSummaryDTO, boolean isFieldName, String path, String toSrid, Extent extent) throws Exception;

	/////////////////////////////////////////////
	/// @fn export
	/// @brief 함수 간략한 설명 : 내보내기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param spatialSearchSummaryDTO
	/// @param isFieldName
	/// @param path
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void export(SpatialSearchSummaryDTO spatialSearchSummaryDTO, boolean isFieldName, String path, String toSrid, Extent extent) throws Exception;
}
