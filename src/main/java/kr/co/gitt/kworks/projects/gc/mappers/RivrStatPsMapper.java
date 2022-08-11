package kr.co.gitt.kworks.projects.gc.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gc.model.RivrStatPs;

/////////////////////////////////////////////
/// @class RivrStatPsMapper
/// kr.co.gitt.kworks.projects.gc.mappers \n
///   ㄴ RivrStatPsMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 5. 24. 오후 12:25:35 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 하천측점 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface RivrStatPsMapper {
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 하전측점 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rivrStatPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RivrStatPs> list(RivrStatPs rivrStatPs);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 하천측점 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rivrStatPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public RivrStatPs selectOne(RivrStatPs rivrStatPs);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 하천측점 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rivrStatPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(RivrStatPs rivrStatPs);
	
}
