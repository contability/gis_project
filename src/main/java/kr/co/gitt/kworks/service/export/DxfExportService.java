package kr.co.gitt.kworks.service.export;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;

import com.kmaps.framework.common.Extent;

/////////////////////////////////////////////
/// @class DxfExportService
/// kr.co.gitt.kworks.service.export \n
///   ㄴ DxfExportService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오후 12:35:03 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 DXF 내보내기 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface DxfExportService {

	/////////////////////////////////////////////
	/// @fn export
	/// @brief 함수 간략한 설명 : 내보내기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param list
	/// @param path
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String export(List<SpatialSearchSummaryDTO> list, String path, String toSrid, Extent extent) throws Exception;
}
