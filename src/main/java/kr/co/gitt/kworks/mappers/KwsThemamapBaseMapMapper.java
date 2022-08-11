package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.ThemamapSearchDTO;
import kr.co.gitt.kworks.model.KwsThemamapBaseMap;

/////////////////////////////////////////////
/// @class KwsThemamapBaseMapMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsThemamapBaseMapMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | libra |
///    | Date | 2018. 10. 12. 오후 4:43:37 |
///    | Class Version | v1.0 |
///    | 작업자 | libra, Others... |
/// @section 상세설명
/// - 이 클래스는 
///   -# 
/////////////////////////////////////////////
public interface KwsThemamapBaseMapMapper {

	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsThemamapId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsThemamapBaseMap> list(Long kwsThemamapId);

	/////////////////////////////////////////////
	/// @fn listByAuthor
	/// @brief 함수 간략한 설명 : 권한 있는 목록만 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param themamapSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsThemamapBaseMap> listByAuthor(ThemamapSearchDTO themamapSearchDTO);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsThemamapBaseMap
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsThemamapBaseMap kwsThemamapBaseMap);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsThemamapId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(Long kwsThemamapId);
	
}
