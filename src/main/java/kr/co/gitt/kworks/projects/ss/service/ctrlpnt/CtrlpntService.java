package kr.co.gitt.kworks.projects.ss.service.ctrlpnt;

import java.util.List;

import org.springframework.web.multipart.MultipartRequest;

import kr.co.gitt.kworks.model.RdlPcbsPs;
import kr.co.gitt.kworks.model.RdtPcbsDt;

/////////////////////////////////////////////
/// @class CtrlpntService
/// kr.co.gitt.kworks.service.ctrlpnt \n
///   ㄴ CtrlpntService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 10. 31. 오전 11:22:53 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 도시기준점 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface CtrlpntService {
	
	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 전체검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdlPcbsPs> listAll();
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdlPcbsPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdlPcbsPs> list(RdlPcbsPs rdlPcbsPs);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public RdlPcbsPs selectOne(RdlPcbsPs rdlPcbsPs);
	
	/////////////////////////////////////////////
	/// @fn add
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdlPcbsPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer add(RdlPcbsPs rdlPcbsPs, MultipartRequest multipartRequest, String userId) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn modify
	/// @brief 함수 간략한 설명 : 편집
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdlPcbsPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modify(RdlPcbsPs rdlPcbsPs, MultipartRequest multipartRequest, String userId, List<String> deleteImageType) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn remove
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdlPcbsPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer remove(RdlPcbsPs rdlPcbsPs) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn listdtPcbsDt
	/// @brief 함수 간략한 설명 : 관리대장 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPcbsDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdtPcbsDt> listRdtPcbsDt(RdtPcbsDt rdtPcbsDt);
	
	/////////////////////////////////////////////
	/// @fn listAllRdtPcbsDt
	/// @brief 함수 간략한 설명 : 관리대장 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdtPcbsDt> listAllRdtPcbsDt();
	
	/////////////////////////////////////////////
	/// @fn selectOneRdtPcbsDt
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPcbsDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public RdtPcbsDt selectOneRdtPcbsDt(RdtPcbsDt rdtPcbsDt);
	
	/////////////////////////////////////////////
	/// @fn addRdtPcbsDt
	/// @brief 함수 간략한 설명 : 관리대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPcbsDt
	/// @param multipartRequest
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addRdtPcbsDt(RdtPcbsDt rdtPcbsDt, MultipartRequest multipartRequest, String userId) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn modifyRdtPcbsDt
	/// @brief 함수 간략한 설명 : 관리대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPcbsDt
	/// @param multipartRequest
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyRdtPcbsDt(RdtPcbsDt rdtPcbsDt, MultipartRequest multipartRequest, String userId, List<String> deleteImageType) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn removeRdtPcbsDt
	/// @brief 함수 간략한 설명 : 관리대장 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPcbsDt
	/// @param multipartRequest
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeRdtPcbsDt(RdtPcbsDt rdtPcbsDt) throws Exception;
}
