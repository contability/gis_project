package kr.co.gitt.kworks.service.authorGroup;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class AuthorGroupService
/// kr.co.gitt.kworks.service.authorGroup \n
///   ㄴ AuthorGroupService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 25. 오후 3:09:19 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 권한 그룹 관리 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface AuthorGroupService {

	/////////////////////////////////////////////
	/// @fn listAuthorGroup
	/// @brief 함수 간략한 설명 : 권한 그룹 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsAuthorGroup> listAuthorGroup(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listAuthorGroup
	/// @brief 함수 간략한 설명 : 권한 그룹 목록 검색 (페이징 포함)
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
	public List<KwsAuthorGroup> listAuthorGroup(SearchDTO searchDTO, PaginationInfo paginationInfo);
	
	/////////////////////////////////////////////
	/// @fn selectOneAuthorGroup
	/// @brief 함수 간략한 설명 : 권한 그룹 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param authorGroupNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsAuthorGroup selectOneAuthorGroup(Long authorGroupNo);
	
	/////////////////////////////////////////////
	/// @fn addAuthorGroup
	/// @brief 함수 간략한 설명 : 권한 그룹 추가
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsAuthorGroup
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addAuthorGroup(KwsAuthorGroup kwsAuthorGroup) throws FdlException ;
	
	/////////////////////////////////////////////
	/// @fn modifyAuthorGroup
	/// @brief 함수 간략한 설명 : 권한 그룹 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsAuthorGroup
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyAuthorGroup(KwsAuthorGroup kwsAuthorGroup) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeAuthorGroup
	/// @brief 함수 간략한 설명 : 권한 그룹 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param authorGroupNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeAuthorGroup(Long authorGroupNo);
	
	/////////////////////////////////////////////
	/// @fn listAllAuthorGroup
	/// @brief 함수 간략한 설명 : 권한 그룹 전체 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsAuthorGroup> listAllAuthorGroup();
	
}
