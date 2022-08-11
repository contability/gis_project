package kr.co.gitt.kworks.projects.yg.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.yg.model.LedgerEditHi;

/////////////////////////////////////////////
/// @class LedgerEditHiMapper
/// kr.co.gitt.kworks.projects.yg.mappers \n
///   ㄴ LedgerEditHiMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2019. 4. 18. 오후 6:23:55 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 대장편집 이력정보 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////

public interface LedgerEditHiMapper {
	
	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 목록 갯수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer listCount(SearchDTO searchDTO);

	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LedgerEditHi> list(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 전체 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ledgerEditHi
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LedgerEditHi> listAll(LedgerEditHi ledgerEditHi);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param hisNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public LedgerEditHi selectOne(Long hisNo);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ledgerEditHi
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(LedgerEditHi ledgerEditHi);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ledgerEditHi
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(LedgerEditHi ledgerEditHi);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param hisNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(Long hisNo);
}
