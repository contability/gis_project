package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsDataFieldCtgry;

/////////////////////////////////////////////
/// @class KwsDataFieldCtgryMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsDataFieldCtgryMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오전 11:49:51 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 필드 카테고리 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsDataFieldCtgryMapper {

	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 데이터 필드 카테고리 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsDataFieldCtgry> listAll();
	
}
