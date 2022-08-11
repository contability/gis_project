package kr.co.gitt.kworks.mappers;

import java.util.List;
import java.util.Map;

import kr.co.gitt.kworks.model.KwsDataAuthor;

/////////////////////////////////////////////
/// @class KwsDataAuthorMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsDataAuthorMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 25. 오후 3:16:25 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 권한 맵퍼 입니다. 
///   -# 
/////////////////////////////////////////////
public interface KwsDataAuthorMapper {
	
	/////////////////////////////////////////////
	/// @fn select
	/// @brief 함수 간략한 설명 : 데이터 권한 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param map
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsDataAuthor> listAll(Map<String, Object> map);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 데이터 권한 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataAuthorVO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsDataAuthor dataAuthorVO);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 데이터 권한 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param authorGroupNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(Long authorGroupNo);
	
}
