package kr.co.gitt.kworks.projects.ss.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.ss.model.BmlWellPs;

/////////////////////////////////////////////
/// @class BmlWellPsMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ BmlWellPsMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 2:02:39 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 지하수 관정 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface BmlWellPsMapper {

	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 목록 카운터
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer listCount(BmlWellPs bmlWellPs);
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<BmlWellPs> list(BmlWellPs bmlWellPs);
	
	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 전체검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<BmlWellPs> listAll(BmlWellPs bmlWellPs);
	
	/////////////////////////////////////////////
	/// @fn listAllGeomIsNull
	/// @brief 함수 간략한 설명 : GEOM 이 NULL인 값 전체 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<BmlWellPs> listAllGeomIsNull(BmlWellPs bmlWellPs);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단일 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param permNtNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public BmlWellPs selectOne(String objectid);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 입력
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(BmlWellPs bmlWellPs);
	
	/////////////////////////////////////////////
	/// @fn remove
	/// @brief 함수 간략한 설명 : 단건 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer remove(BmlWellPs bmlWellPs);
	
	/////////////////////////////////////////////
	/// @fn removeAll
	/// @brief 함수 간략한 설명 : 전체 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeAll();
	
	/////////////////////////////////////////////
	/// @fn geomModify
	/// @brief 함수 간략한 설명 : 공간객체 update
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer geomModify(BmlWellPs bmlWellPs);
}
