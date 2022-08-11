package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.dto.CityParkChangeDetailsResultDTO;
import kr.co.gitt.kworks.projects.gn.dto.CityParkChangeDetailsSearchDTO;

/////////////////////////////////////////////
/// @class CityParkChangeDetailsMapper
/// kr.co.gitt.kworks.projects.gn.mappers \n
///   ㄴ CityParkChangeDetailsMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 5. 26. 오후 6:21:55 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도시공원 변천내역 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface CityParkChangeDetailsMapper {

	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 총 건수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer listCount(CityParkChangeDetailsSearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CityParkChangeDetailsResultDTO> list(CityParkChangeDetailsSearchDTO searchDTO);
	
}
