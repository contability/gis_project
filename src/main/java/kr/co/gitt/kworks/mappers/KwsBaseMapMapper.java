package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.dto.baseMap.BaseMapSearchDTO;
import kr.co.gitt.kworks.model.KwsBaseMap;

/////////////////////////////////////////////
/// @class KwsBaseMapMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsBaseMapMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 2. 1. 오전 11:41:56 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 기본 지도 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsBaseMapMapper {

	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 검색 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param list
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsBaseMap> listAll(BaseMapSearchDTO baseMapSearchDTO);
	
	/////////////////////////////////////////////
	/// @fn listLayerY
	/// @brief 함수 간략한 설명 : 레이어 사용 Y만 목록 검색 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param list
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsBaseMap> listLayerY(BaseMapSearchDTO baseMapSearchDTO);

	
	/////////////////////////////////////////////
	/// @fn updateBcrnMap
	/// @brief 함수 간략한 설명 : 배경지도 사용여부 수정 N
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsBaseMap
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer updateBcrnMapBaseN(KwsBaseMap kwsBaseMap);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param mapNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsBaseMap selectOne(Long mapNo);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param baseMapSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer updateBcrnMapInfo(KwsBaseMap kwsBaseMap);
	
}
