package kr.co.gitt.kworks.projects.dh.service.civilAppeal;

import java.util.List;

import kr.co.gitt.kworks.projects.dh.model.SwtSpmtMa;
import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class DrngEqpCnfmPrmisnService
/// kr.co.gitt.kworks.projects.dh.service.civilAppeal \n
///   ㄴ DrngEqpCnfmPrmisnService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 23. 오후 4:53:48 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 배수설비인허가 서비스 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface DrngEqpCnfmPrmisnService {

	/////////////////////////////////////////////
	/// @fn listDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSpmtMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SwtSpmtMa> listDrngEqpCnfmPrmisn(SwtSpmtMa swtSpmtMa);
	
	/////////////////////////////////////////////
	/// @fn selectOneDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public SwtSpmtMa selectOneDrngEqpCnfmPrmisn(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn modifyDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSpmtMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyDrngEqpCnfmPrmisn(SwtSpmtMa swtSpmtMa);
	
	/////////////////////////////////////////////
	/// @fn addDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSpmtMa
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addDrngEqpCnfmPrmisn(SwtSpmtMa swtSpmtMa) throws FdlException;
	
}
