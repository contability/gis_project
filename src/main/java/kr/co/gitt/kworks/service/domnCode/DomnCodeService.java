package kr.co.gitt.kworks.service.domnCode;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.co.gitt.kworks.model.KwsDomnCode;

/////////////////////////////////////////////
/// @class DomnCodeService
/// kr.co.gitt.kworks.service.domn \n
///   ㄴ DomnCodeService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 8. 22. 오후 3:14:40 |
///    | Class Version | v1.0 |
///    | 작업자 | nam, Others... |
/// @section 상세설명
/// - 이 클래스는 도메인 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface DomnCodeService {
	
	/////////////////////////////////////////////
	/// @fn listDomnCode
	/// @brief 함수 간략한 설명 : 도메인코드 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param KwsDomnCode
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsDomnCode> listDomnCode(KwsDomnCode kwsDomnCode);
	
	/////////////////////////////////////////////
	/// @fn selectOneDomnCode
	/// @brief 함수 간략한 설명 : 도메인코드 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param domnNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsDomnCode selectOneDomnCode(String domnNo);
	
	/////////////////////////////////////////////
	/// @fn selectCodeIdByName
	/// @brief 함수 간략한 설명 : 도메인코드 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param domnId
	/// @param codeNm
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsDomnCode selectCodeIdByName(String domnId, String codeNm);
	
	/////////////////////////////////////////////
	/// @fn addDomnCode
	/// @brief 함수 간략한 설명 : 도메인코드 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param KwsDomnCode
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addDomnCode(KwsDomnCode kwsDomnCode);
	
	/////////////////////////////////////////////
	/// @fn modifyDomnCode
	/// @brief 함수 간략한 설명 : 도메인코드 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param KwsDomnCode
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyDomnCode(KwsDomnCode KwsDomnCode);
	
	/////////////////////////////////////////////
	/// @fn removeDomnCode
	/// @brief 함수 간략한 설명 : 도메인코드 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param domnNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeDomnCode(KwsDomnCode kwsDomnCode);
	
	public KwsDomnCode selectOneById(String domnId, String codeId);
}
