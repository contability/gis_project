package kr.co.gitt.kworks.service.sld;

import kr.co.gitt.kworks.dto.sld.json.KworksSld;

/////////////////////////////////////////////
/// @class SldConverter
/// kr.co.gitt.kworks.service.sld \n
///   ㄴ SldConverter.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 24. 오후 2:50:15 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 SLD 변환기 입니다.
///   -# 
/////////////////////////////////////////////
public interface SldConverterService {

	/////////////////////////////////////////////
	/// @fn convert
	/// @brief 함수 간략한 설명 : 변환 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kworksSld
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String convert(KworksSld kworksSld);
	
}
