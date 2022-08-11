package kr.co.gitt.kworks.service.sld;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

/////////////////////////////////////////////
/// @class SldCacheService
/// kr.co.gitt.kworks.service.sld \n
///   ㄴ SldCacheService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 28. 오전 10:29:18 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 SLD 캐시 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface SldCacheService {

	/////////////////////////////////////////////
	/// @fn putSLD
	/// @brief 함수 간략한 설명 : SLD 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param json
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String putSLD(String json) throws JsonParseException, JsonMappingException, IOException;
	
	/////////////////////////////////////////////
	/// @fn getSLD
	/// @brief 함수 간략한 설명 : SLD 가져오기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param sldKey
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String getSLD(String sldKey);
	
}
