package kr.co.gitt.kworks.service.chngeDetct;

import java.io.IOException;
import java.util.List;

import kr.co.gitt.kworks.model.ChgAreaDt;
import kr.co.gitt.kworks.model.ChgDtlsDt;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class ChangeDetectionDetailsService
/// kr.co.gitt.kworks.service.chngeDetct \n
///   ㄴ ChangeDetectionDetailsService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | yun4810 |
///    | Date | 2018. 10. 2. 오후 3:00:02 |
///    | Class Version | v1.0 |
///    | 작업자 | yun4810, Others... |
/// @section 상세설명
/// - 이 클래스는 변화탐지 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface ChangeDetectionService {
	
	/////////////////////////////////////////////
	/// @fn listChangeDetectionDetails
	/// @brief 함수 간략한 설명 : 변화탐지내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgDtlsDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<ChgDtlsDt> listChangeDetectionDetails(ChgDtlsDt chgDtlsDt);
	
	/////////////////////////////////////////////
	/// @fn listChangeDetectionDetails
	/// @brief 함수 간략한 설명 : 변화탐지내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgDtlsDt
	/// @param paginationInfo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<ChgDtlsDt> listChangeDetectionDetails(ChgDtlsDt chgDtlsDt, PaginationInfo paginationInfo);
	
	/////////////////////////////////////////////
	/// @fn addChangeDetectionDetails
	/// @brief 함수 간략한 설명 : 변화탐지내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgDtlsDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addChangeDetectionDetails(ChgDtlsDt chgDtlsDt) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn modifyChangeDetectionDetails
	/// @brief 함수 간략한 설명 : 변화 탐지 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgDtlsDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyChangeDetectionDetails(ChgDtlsDt chgDtlsDt) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeChangeDetectionDetails
	/// @brief 함수 간략한 설명 : 변화 탐지 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeDetctNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeChangeDetectionDetails(Long chngeDetctNo);

	/////////////////////////////////////////////
	/// @fn selectOneChangeDetectionDetails
	/// @brief 함수 간략한 설명 : 변화탐지내역 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeDetctNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public ChgDtlsDt selectOneChangeDetectionDetails(Long chngeDetctNo);

	/////////////////////////////////////////////
	/// @fn listChangeDetectionArea
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgAreaDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<ChgAreaDt> listChangeDetectionArea(ChgAreaDt chgAreaDt);
	
	/////////////////////////////////////////////
	/// @fn listChangeDetectionArea
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgAreaDt
	/// @param paginationInfo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<ChgAreaDt> listChangeDetectionArea(ChgAreaDt chgAreaDt, PaginationInfo paginationInfo);
	
	/////////////////////////////////////////////
	/// @fn selectOneChangeDetectionArea
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 상세조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeAreaNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public ChgAreaDt selectOneChangeDetectionArea(Long chngeAreaNo);
	
	/////////////////////////////////////////////
	/// @fn removeChangeDetectionArea
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeAreaNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeChangeDetectionArea(Long chngeAreaNo);
	
	/////////////////////////////////////////////
	/// @fn modifyChangeDetectionArea
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgAreaDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyChangeDetectionArea(ChgAreaDt chgAreaDt, String[] data) throws Exception, FdlException, IOException;
	
	/////////////////////////////////////////////
	/// @fn addChangeDetectionArea
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgAreaDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addChangeDetectionArea(ChgAreaDt chgAreaDt, String[] data) throws Exception, FdlException, IOException;
	
}
