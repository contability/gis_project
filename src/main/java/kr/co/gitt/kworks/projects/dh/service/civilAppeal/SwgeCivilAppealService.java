package kr.co.gitt.kworks.projects.dh.service.civilAppeal;

import java.util.List;

import kr.co.gitt.kworks.projects.dh.model.SwtSserMa;
import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class SwtCivilAppealService
/// kr.co.gitt.kworks.projects.dh.service.civilAppeal \n
///   ㄴ SwtCivilAppealService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 22. 오전 10:37:45 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 하수시스템 민원 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface SwgeCivilAppealService {

	/////////////////////////////////////////////
	/// @fn listSwtCivilAppeal
	/// @brief 함수 간략한 설명 : 하수민원 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSserMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SwtSserMa> listSwgeCivilAppeal(SwtSserMa swtSserMa);
	
	/////////////////////////////////////////////
	/// @fn selectOneSwtCivilAppeal
	/// @brief 함수 간략한 설명 : 하수민원 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public SwtSserMa selectOneSwgeCivilAppeal(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn modifySwtCivilAppeal
	/// @brief 함수 간략한 설명 : 하수민원 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSserMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifySwgeCivilAppeal(SwtSserMa swtSserMa);
	
	/////////////////////////////////////////////
	/// @fn addSwtCivilAppeal
	/// @brief 함수 간략한 설명 : 하수민원 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSserMa
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addSwgeCivilAppeal(SwtSserMa swtSserMa) throws FdlException;
	
}
