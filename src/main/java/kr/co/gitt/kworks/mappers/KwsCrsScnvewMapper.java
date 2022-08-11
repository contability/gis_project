package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsCrsScnvew;

/////////////////////////////////////////////
/// @class KwsCrsScnvewMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsCrsScnvewMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 1. 오전 11:25:39 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 횡단면도 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsCrsScnvewMapper {

	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsCrsScnvew> listAll();
	
}
