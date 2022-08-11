package kr.co.gitt.kworks.service.export;

import kr.co.gitt.kworks.model.KwsExport;

/////////////////////////////////////////////
/// @class ExportAuthorService
/// kr.co.gitt.kworks.service.export \n
///   ㄴ ExportAuthorService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 4. 19. 오후 2:18:04 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 권한 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface ExportAuthorService {

	/////////////////////////////////////////////
	/// @fn checkAutoConsent
	/// @brief 함수 간략한 설명 : 자동 승인 확인
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public boolean checkAutoConsent(KwsExport kwsExport);
	
}
