package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.RdlPcbsPs;

/////////////////////////////////////////////
/// @class RdlPcbsPsMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ RdlPcbsPsMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 10. 31. 오후 7:18:33 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 
///   -# 
/////////////////////////////////////////////
public interface RdlPcbsPsMapper {

	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdlPcbsPs> listAll();
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 전체 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdlPcbsPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdlPcbsPs> list(RdlPcbsPs rdlPcbsPs);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdlPcbsPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public RdlPcbsPs selectOne(RdlPcbsPs rdlPcbsPs);
	
	/////////////////////////////////////////////
	/// @fn add
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdlPcbsPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer add(RdlPcbsPs rdlPcbsPs);
	
	/////////////////////////////////////////////
	/// @fn modify
	/// @brief 함수 간략한 설명 : 편집
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdlPcbsPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modify(RdlPcbsPs rdlPcbsPs);
	
	/////////////////////////////////////////////
	/// @fn remove
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdlPcbsPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer remove(RdlPcbsPs rdlPcbsPs);
}
