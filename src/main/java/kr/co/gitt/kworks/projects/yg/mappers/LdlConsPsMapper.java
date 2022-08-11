package kr.co.gitt.kworks.projects.yg.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.yg.dto.LandCenterCntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.yg.model.LdlConsPs;

/////////////////////////////////////////////
/// @class LdConsMaMapper
/// kr.co.gitt.kworks.projects.yg.mappers \n
///   ㄴ LdConsMaMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2018. 3. 27. 오후 6:11:55 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 토지중심공사대장 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface LdlConsPsMapper {

	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LdlConsPs> listAllConsName();
	
	/////////////////////////////////////////////
	/// @fn cntSelectOne
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public LdlConsPs cntSelectOne(Long cntIdn);
	
	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 토지공사위치의 지도 상 표현을 위한 전체 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param 
	/// @return List<LandCenterCntrwkRegstrDTO>
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LandCenterCntrwkRegstrDTO> listAll();
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public LdlConsPs selectOne(LandCenterCntrwkRegstrDTO landCenterCntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(LandCenterCntrwkRegstrDTO landCenterCntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(LandCenterCntrwkRegstrDTO landCenterCntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(Long cntIdn);
	
	/////////////////////////////////////////////
	/// @fn searchWKT
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<Integer> searchWKT(Long cntIdn);
}
