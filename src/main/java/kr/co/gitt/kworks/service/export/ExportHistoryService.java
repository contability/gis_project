package kr.co.gitt.kworks.service.export;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.ExportHistoryResultDTO;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsExportOutpt;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class ExportHistoryService
/// kr.co.gitt.kworks.service.export \n
///   ㄴ ExportHistoryService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 23. 오전 11:57:28 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 이력 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface ExportHistoryService {
	
	public List<ExportHistoryResultDTO> listExportOutput(SearchDTO searchDTO);
	
	public List<ExportHistoryResultDTO> listExportOutput(SearchDTO searchDTO, PaginationInfo paginationInfo);
	
	/////////////////////////////////////////////
	/// @fn insertExportOutput
	/// @brief 함수 간략한 설명 : 내보내기 출력 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportOutpt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insertExportOutput(KwsExportOutpt kwsExportOutpt) throws FdlException;
	
	
}
