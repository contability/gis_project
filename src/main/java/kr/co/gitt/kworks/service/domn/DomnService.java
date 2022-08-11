package kr.co.gitt.kworks.service.domn;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsDomn;

/////////////////////////////////////////////
/// @class DomnService
/// kr.co.gitt.kworks.service.domn \n
///   ㄴ DomnService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 8. 16. 오후 4:14:40 |
///    | Class Version | v1.0 |
///    | 작업자 | nam, Others... |
/// @section 상세설명
/// - 이 클래스는 도메인 코드 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface DomnService {
	
	/////////////////////////////////////////////
	/// @fn listDomn
	/// @brief 함수 간략한 설명 : 도메인 코드 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param KwsDomn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsDomn> listDomn(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn selectOneDomn
	/// @brief 함수 간략한 설명 : 도메인 코드 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param domnId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsDomn selectOneDomn(String domnId);
	
	/////////////////////////////////////////////
	/// @fn addDomn
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param KwsDomn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addDomn(KwsDomn kwsDomn);
	
	/////////////////////////////////////////////
	/// @fn modifyDomn
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param KwsDomn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyDomn(KwsDomn KwsDomn);
	
	/////////////////////////////////////////////
	/// @fn removeDomn
	/// @brief 함수 간략한 설명 : 도메인 코드 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param domnNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeDomn(String domnId);
}
