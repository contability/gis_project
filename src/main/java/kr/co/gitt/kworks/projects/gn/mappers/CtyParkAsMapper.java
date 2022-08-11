package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.dto.CityParkSearchDTO;
import kr.co.gitt.kworks.projects.gn.model.CtyParkAs;

/////////////////////////////////////////////
/// @class CtyParkAsMapper
/// kr.co.gitt.kworks.projects.gn.mappers \n
///   ㄴ CtyParkAsMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 4. 21. 오후 5:23:37 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도시 공원 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface CtyParkAsMapper {
	
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
	public List<CtyParkAs> listAll();

	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 목록 수 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cityParkSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer listCount(CityParkSearchDTO cityParkSearchDTO);
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cityParkSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyParkAs> list(CityParkSearchDTO cityParkSearchDTO);
	
}
