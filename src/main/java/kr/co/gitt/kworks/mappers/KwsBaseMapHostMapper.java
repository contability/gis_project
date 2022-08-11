package kr.co.gitt.kworks.mappers;

import java.util.List;
import java.util.Map;

import kr.co.gitt.kworks.model.KwsBaseMapHost;

/////////////////////////////////////////////
/// @class KwsBaseMapHostMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsBaseMapHostMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 2. 3. 오전 10:06:17 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 기본 지도 호스트 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsBaseMapHostMapper {

	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param map
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsBaseMapHost> listAll(Map<String, Object> map);
	
}
