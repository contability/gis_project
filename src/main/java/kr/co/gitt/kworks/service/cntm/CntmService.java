package kr.co.gitt.kworks.service.cntm;

import java.util.List;

import kr.co.gitt.kworks.model.KwsCntm;

/////////////////////////////////////////////
/// @class CntmService
/// kr.co.gitt.kworks.service.cntm \n
///   ㄴ CntmService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 11. 오후 3:56:48 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 좌표계 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface CntmService {

	/////////////////////////////////////////////
	/// @fn listAllCntm
	/// @brief 함수 간략한 설명 : 전체 좌표계 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsCntm> listAllCntm();
	
}
