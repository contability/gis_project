package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.dto.search.DataSearchDTO;
import kr.co.gitt.kworks.model.KwsData;

import org.apache.ibatis.annotations.Param;

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
public interface KwsDataMapper {
	
	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsData> listAll(DataSearchDTO dataSearchDTO);

	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsData selectOne(String dataId);
	
	// Lks : 도로대장
	/////////////////////////////////////////////
	/// @fn selectOneRoadReg
	/// @brief 함수 간략한 설명 : 도로대장 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsData selectOneRoadReg(String dataId);
	
	/////////////////////////////////////////////
	/// @fn selectRoadRegByAttr
	/// @brief 함수 간략한 설명 : 도로대장을 속성으로 가지는 데이터 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsData> selectRoadRegByAttr(String dataId);
	
	/////////////////////////////////////////////
	/// @fn selectRoadRegByCode
	/// @brief 함수 간략한 설명 : 도로대장의 특정 타입에 해당하는 데이터 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param tyCode
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsData> selectRoadRegByCode(@Param("dataId") String dataId, @Param("typeCode") String typeCode);	
}
