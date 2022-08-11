package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.LayerSearchDTO;
import kr.co.gitt.kworks.model.KwsLyr;

/////////////////////////////////////////////
/// @class KwsLyrMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsLyrMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 11. 오후 5:56:08 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 레이어 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsLyrMapper {

	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param layerSearchdTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsLyr> listAll(LayerSearchDTO layerSearchDTO);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsLyr
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsLyr selectOne(String lyrId);
	
	/////////////////////////////////////////////
	/// @fn selectOneByKwsLyr
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsLyr
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsLyr selectOneByKwsLyr(KwsLyr kwsLyr);
	
}
