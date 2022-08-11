package kr.co.gitt.kworks.service.user;

import java.util.List;

import kr.co.gitt.kworks.dto.UserSearchDTO;
import kr.co.gitt.kworks.model.KwsUser;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


/////////////////////////////////////////////
/// @class UserService
/// kr.co.gitt.kworks.service.user \n
///   ㄴ UserService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 16. 오전 11:59:40 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 관리 서비스 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface UserService {
	
	/////////////////////////////////////////////
	/// @fn listUser
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsUser> listUser(UserSearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listUser
	/// @brief 함수 간략한 설명 : 목록 검색 
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
	public List<KwsUser> listUser(UserSearchDTO searchDTO, PaginationInfo paginationInfo);
	
	/////////////////////////////////////////////
	/// @fn selectOneUser
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param userId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsUser selectOneUser(String userId);
	
	/////////////////////////////////////////////
	/// @fn addUser
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUser
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addUser(KwsUser kwsUser);
	
	/////////////////////////////////////////////
	/// @fn addUser
	/// @brief 함수 간략한 설명 : 사용자 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUser
	/// @param authorGroups
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addUser(KwsUser kwsUser, List<Long> authorGroups);
	
	/////////////////////////////////////////////
	/// @fn modifyUser
	/// @brief 함수 간략한 설명 : 사용자 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUser
	/// @param authorGroups
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyUser(KwsUser kwsUser, List<Long> authorGroups) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeUser
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param userId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeUser(String userId);
	
	/////////////////////////////////////////////
	/// @fn modifyUserBySso
	/// @brief 함수 간략한 설명 : SSO 유저 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUser
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyUserBySso(KwsUser kwsUser) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn listUserExcel
	/// @brief 함수 간략한 설명 : 엑셀 다운로드용 사용자 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsUser> listUserExcel(UserSearchDTO searchDTO);
	
	public List<KwsUser> listUserInfo(UserSearchDTO searchDTO);
	
	public KwsUser selectOneUserInfo(String userId);
	
	public Integer modifyUserInfo(KwsUser kwsUser) throws FdlException;
	

}
