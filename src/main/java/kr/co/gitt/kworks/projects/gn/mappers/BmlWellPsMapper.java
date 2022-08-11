package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.model.BmlWellPs;

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
	public BmlWellPs selectOne(BmlWellPs bmlWellPs);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(BmlWellPs bmlWellPs);
	
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
