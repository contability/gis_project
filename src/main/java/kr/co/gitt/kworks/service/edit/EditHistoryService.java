package kr.co.gitt.kworks.service.edit;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.EditHistoryResultDTO;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.cmmn.dto.WfsTransactionDTO;
import kr.co.gitt.kworks.model.KwsEditHist;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class EditHistoryService
/// kr.co.gitt.kworks.service.edit \n
///   ㄴ EditHistoryService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 21. 오후 3:33:30 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 편집 이력 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface EditHistoryService {
	
	/////////////////////////////////////////////
	/// @fn listEditHistory
	/// @brief 함수 간략한 설명 : 편집 이력 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param paginationInfo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<EditHistoryResultDTO> listEditHistory(SearchDTO searchDTO, PaginationInfo paginationInfo);
	
	/////////////////////////////////////////////
	/// @fn addEditHistory
	/// @brief 함수 간략한 설명 : 편집 이력 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsEditHist
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addEditHistory(KwsEditHist kwsEditHist) throws FdlException;

	/////////////////////////////////////////////
	/// @fn addEditHistories
	/// @brief 함수 간략한 설명 : 편집 이력 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wfsTransactionDTO
	/// @param userNm
	/// @param resultString
	/// @param responseString
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addEditHistories(WfsTransactionDTO wfsTransactionDTO, String userNm, String resultString, String responseString) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn listEditHistoryExcel
	/// @brief 함수 간략한 설명 : 엑셀 다운로드 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<EditHistoryResultDTO> listEditHistoryExcel(SearchDTO searchDTO);
	
}
