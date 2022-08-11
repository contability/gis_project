package kr.co.gitt.kworks.contact.ehojo.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.CntrctRegstrDTO;
import kr.co.gitt.kworks.cmmn.dto.CntrctRegstrSearchDTO;
import kr.co.gitt.kworks.contact.ehojo.model.VTcmCtrtbooksLink;

/////////////////////////////////////////////
/// @class CntrctRegstrMapper
/// kr.co.gitt.kworks.cntc.ehojo.mappers \n
///   ㄴ CntrctRegstrMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 28. 오전 11:33:33 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 연계 계약 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface CntrctRegstrMapper {
	
	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 건 수 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntrctRegstrSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer listCount(CntrctRegstrSearchDTO cntrctRegstrSearchDTO);

	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntrctRegstrSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<VTcmCtrtbooksLink> list(CntrctRegstrSearchDTO cntrctRegstrSearchDTO);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctrtAcctBookMngNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public CntrctRegstrDTO selectOne(String ctrtAcctBookMngNo);
	
	
	/////////////////////////////////////////////
	/// @fn listYears
	/// @brief 함수 간략한 설명 : 회계년도 목록 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<String> listYears();
	
	/////////////////////////////////////////////
	/// @fn listCodes
	/// @brief 함수 간략한 설명 : 도메인 코드 목록 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param clId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<String> listCodes(String clId);
	
}
