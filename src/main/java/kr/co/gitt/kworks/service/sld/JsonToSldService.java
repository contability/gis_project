package kr.co.gitt.kworks.service.sld;

import kr.co.gitt.kworks.dto.sld.json.KworksSld;

/////////////////////////////////////////////
/// @class JsonToSldService
/// kr.co.gitt.kworks.service.sld \n
///   ㄴ JsonToSldService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 28. 오전 10:33:30 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 JSon, Sld 변환 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface JsonToSldService {

	/////////////////////////////////////////////
	/// @fn jsonToSld
	/// @brief 함수 간략한 설명 : json을 sld로 변환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param jsonStr
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String jsonToSld(String jsonStr);
	
	
	public String jsonToSld(KworksSld kworksSld);
	
}
