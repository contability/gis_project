package kr.co.gitt.kworks.service.user;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsUserAuthorHist;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class UserAuthorHistService
/// kr.co.gitt.kworks.service.user \n
///   ㄴ UserAuthorHistService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 2. 10. 오전 11:37:27 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 권한 부여 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface UserAuthorHistService {
	
	/////////////////////////////////////////////
	/// @fn listUserAuthorHist
	/// @brief 함수 간략한 설명 : 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsUserAuthorHist> listUserAuthorHist(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listUserAuthorHist
	/// @brief 함수 간략한 설명 : 목록 조회
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
	public List<KwsUserAuthorHist> listUserAuthorHist(SearchDTO searchDTO, PaginationInfo paginationInfo);
	
	/////////////////////////////////////////////
	/// @fn addUserAuthorHist
	/// @brief 함수 간략한 설명 : 이력 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserAuthorHist
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addUserAuthorHist(KwsUserAuthorHist kwsUserAuthorHist);
	
	/////////////////////////////////////////////
	/// @fn listUserAuthorHistExcel
	/// @brief 함수 간략한 설명 : 엑셀용 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsUserAuthorHist> listUserAuthorHistExcel(SearchDTO searchDTO);
	
}
