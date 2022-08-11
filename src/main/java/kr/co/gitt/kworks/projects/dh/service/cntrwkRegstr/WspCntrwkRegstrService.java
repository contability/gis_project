package kr.co.gitt.kworks.projects.dh.service.cntrwkRegstr;

import java.util.List;

import kr.co.gitt.kworks.projects.dh.model.WttSplyMa;
import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class WspCntrwkRegstrService
/// kr.co.gitt.kworks.projects.dh.service.cntrwkRegstr \n
///   ㄴ WspCntrwkRegstrService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 14. 오전 10:35:05 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 급수 공사대장 서비스 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface WspCntrwkRegstrService {
	
	/////////////////////////////////////////////
	/// @fn listWsp
	/// @brief 함수 간략한 설명 : 급수 공사대장 리스트 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSplyMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<WttSplyMa> listWspCntrwkRegstr(WttSplyMa wttSplyMa);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 급수 공사대장 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public WttSplyMa selectOneWspCntrwkRegstr(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn modify
	/// @brief 함수 간략한 설명 : 급수 공사대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSplyMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyWspCntrwkRegstr(WttSplyMa wttSplyMa);
	
	/////////////////////////////////////////////
	/// @fn addWspCntrwkRegstr
	/// @brief 함수 간략한 설명 : 급수 공사대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSplyMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addWspCntrwkRegstr(WttSplyMa wttSplyMa) throws FdlException;

	/////////////////////////////////////////////
	/// @fn searchSpatial
	/// @brief 함수 간략한 설명 : 공사 위치 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<String> searchSpatial(Long ftrIdn);
	
}
