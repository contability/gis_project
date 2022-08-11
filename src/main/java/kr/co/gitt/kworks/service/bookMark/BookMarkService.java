package kr.co.gitt.kworks.service.bookMark;

import java.util.List;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.gitt.kworks.model.KwsDept;
import kr.co.gitt.kworks.model.KwsUserBkmk;

/////////////////////////////////////////////
/// @class BookMarkService
/// kr.co.gitt.kworks.service.bookMark \n
///   ㄴ BookMarkService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 28. 오후 5:33:32 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 북마크 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface BookMarkService {

	/////////////////////////////////////////////
	/// @fn listAllBookMark
	/// @brief 함수 간략한 설명 : 북마크 목록 검색 (페이징 X)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserBkmk
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsUserBkmk> listAllBookMark(KwsUserBkmk kwsUserBkmk);
	
	/////////////////////////////////////////////
	/// @fn selectOneUserBkmk
	/// @brief 함수 간략한 설명 : 북마크 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserBkmk
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsUserBkmk selectOneUserBkmk(KwsUserBkmk kwsUserBkmk);
	
	/////////////////////////////////////////////
	/// @fn addBookMark
	/// @brief 함수 간략한 설명 : 북마크 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserBkmk
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addBookMark(KwsUserBkmk kwsUserBkmk) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeBookMark
	/// @brief 함수 간략한 설명 : 북마크 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bkmkNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeBookMark(Long bkmkNo);
	
}
