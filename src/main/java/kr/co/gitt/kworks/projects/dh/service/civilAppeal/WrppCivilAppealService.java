package kr.co.gitt.kworks.projects.dh.service.civilAppeal;

import java.util.List;

import kr.co.gitt.kworks.projects.dh.model.WttWserMa;
import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class WrppCivilAppealService
/// kr.co.gitt.kworks.projects.dh.service.civilAppeal \n
///   ㄴ WrppCivilAppealService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 22. 오전 10:37:45 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 상수시스템 민원 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface WrppCivilAppealService {

	/////////////////////////////////////////////
	/// @fn listWrppCivilAppeal
	/// @brief 함수 간략한 설명 : 상수민원 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttWserMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<WttWserMa> listWrppCivilAppeal(WttWserMa wttWserMa);
	
	/////////////////////////////////////////////
	/// @fn selectOneWrppCivilAppeal
	/// @brief 함수 간략한 설명 : 상수민원 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public WttWserMa selectOneWrppCivilAppeal(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn modifyWrppCivilAppeal
	/// @brief 함수 간략한 설명 : 상수민원 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttWserMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyWrppCivilAppeal(WttWserMa wttWserMa);
	
	/////////////////////////////////////////////
	/// @fn addWrppCivilAppeal
	/// @brief 함수 간략한 설명 : 상수민원 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttWserMa
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addWrppCivilAppeal(WttWserMa wttWserMa) throws FdlException;
	
}
