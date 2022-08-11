package kr.co.gitt.kworks.projects.yg.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.yg.model.LdConsAtchmnfl;

/////////////////////////////////////////////
/// @class LdConsAtchmnflMapper
/// kr.co.gitt.kworks.projects.yg.mappers \n
///   ㄴ LdConsAtchmnflMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2019. 4. 18. 오후 6:23:52 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는  토지공사대장 첨부파일 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface LdConsAtchmnflMapper {

	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsAtchmnfl
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LdConsAtchmnfl> list();
	
	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 전체조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsAtchmnfl
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LdConsAtchmnfl> listAll(LdConsAtchmnfl ldConsAtchmnfl);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 상세조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param fileNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public LdConsAtchmnfl selectOne(Long fileNo);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsAtchmnfl
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(LdConsAtchmnfl ldConsAtchmnfl);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsAtchmnfl
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(LdConsAtchmnfl ldConsAtchmnfl);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param fileNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(Long fileNo);
	
}
