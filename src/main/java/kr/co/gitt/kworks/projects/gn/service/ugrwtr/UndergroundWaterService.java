package kr.co.gitt.kworks.projects.gn.service.ugrwtr;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.model.BmlWellCheck;
import kr.co.gitt.kworks.projects.gn.model.BmlWellPs;

import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class UndergroundWaterService
/// kr.co.gitt.kworks.projects.gn.service.ugrwtr \n
///   ㄴ UndergroundWaterService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 2. 28. 오후 3:09:25 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 지하수관정 서비스 인터페이스 입니다.
///   -# 
/////////////////////////////////////////////
public interface UndergroundWaterService {

	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 지하수관정 카운터
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer listCount(BmlWellPs bmlWellPs);
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 지하수관정 목록검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<BmlWellPs> list(BmlWellPs bmlWellPs);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 지하수관정 단건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param permNtNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public BmlWellPs selectOne(BmlWellPs bmlWellps);
	
	/////////////////////////////////////////////
	/// @fn modify
	/// @brief 함수 간략한 설명 : 지하수관정 정보 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellps
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modify(BmlWellPs bmlWellPs, MultipartRequest meltiparTequest) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn listChckImprmnHist
	/// @brief 함수 간략한 설명 : 지하수관정 점검이력 리스트
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellCheck
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<BmlWellCheck> listChckImprmnHist(BmlWellCheck bmlWellCheck);
	
	/////////////////////////////////////////////
	/// @fn selectOneChckImprmnHist
	/// @brief 함수 간략한 설명 : 지하수관정 점검정비이력 수정창
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chkIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public BmlWellCheck selectOneChckImprmnHist(Long chkIdn);
	
	/////////////////////////////////////////////
	/// @fn modifyChckImprmnHist
	/// @brief 함수 간략한 설명 : 지하수관정 점검정비이력 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellCheck
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyChckImprmnHist(BmlWellCheck bmlWellCheck);
	
	/////////////////////////////////////////////
	/// @fn removeChckImprmnHist
	/// @brief 함수 간략한 설명 : 지하수관정 점검정비이력 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chkIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeChckImprmnHist(Long chkIdn);
	
	/////////////////////////////////////////////
	/// @fn addChckImprmnHist
	/// @brief 함수 간략한 설명 : 지하수관정 점검정비이력 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellCheck
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addChckImprmnHist(BmlWellCheck bmlWellCheck) throws FdlException;
	
}
