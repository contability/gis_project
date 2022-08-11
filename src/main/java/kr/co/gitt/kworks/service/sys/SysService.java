package kr.co.gitt.kworks.service.sys;

import java.util.List;

import kr.co.gitt.kworks.model.KwsSys;
import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class SysService
/// kr.co.gitt.kworks.service.sys \n
///   ㄴ SysService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 9. 오후 4:13:38 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 시스템 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface SysService {

	/////////////////////////////////////////////
	/// @fn listAllSys
	/// @brief 함수 간략한 설명 : 시스템 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsSys> listAllSys(KwsSys kwsSys);
	
	/////////////////////////////////////////////
	/// @fn selectOneSys
	/// @brief 함수 간략한 설명 : 시스템 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param sysId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsSys selectOneSys(Long sysId);
	
	/////////////////////////////////////////////
	/// @fn modifyBassThemamap
	/// @brief 함수 간략한 설명 : 시스템 주제도 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsSys
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyBassThemamap(KwsSys kwsSys) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn listSys
	/// @brief 함수 간략한 설명 : 시스템 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsSys> listSys();
	
}
