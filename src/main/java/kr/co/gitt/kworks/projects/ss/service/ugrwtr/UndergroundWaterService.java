package kr.co.gitt.kworks.projects.ss.service.ugrwtr;

import java.util.List;

import kr.co.gitt.kworks.projects.ss.model.BmlWellPs;

/////////////////////////////////////////////
/// @class UndergroundWaterService
/// kr.co.gitt.kworks.service.ugrwtr \n
///   ㄴ UndergroundWaterService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 9. 22. 오후 5:18:20 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 지하수관정 서비스입니다.
///   -# 
/////////////////////////////////////////////
public interface UndergroundWaterService {

	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 지하수관정 카운터
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer listCount(BmlWellPs bmlWellPs);
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 지하수관정 목록검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<BmlWellPs> list(BmlWellPs bmlWellPs);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 지하수관정 단건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param permNtNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public BmlWellPs selectOne(String objectid);
}
