package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsDataCtgry;

/////////////////////////////////////////////
/// @class KwsDataMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsDataMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 18. 오전 11:47:45 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsDataCtgryMapper {

	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : dataCtgry list 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsDataCtgry> listAll();
	
}
