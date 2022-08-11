package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.dto.DrngEqpCnfmPrmisnAtchmnflDTO;
import kr.co.gitt.kworks.projects.gn.model.SwtSpmtAtchmnflDt;

/////////////////////////////////////////////
/// @class SwtSpmtAtchmnflDtMapper
/// kr.co.gitt.kworks.projects.gn.mappers \n
///   ㄴ SwtSpmtAtchmnflDtMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | 이승재 |
///    | Date | 2017.12.15 |
///    | Class Version | v1.0 |
///    | 작업자 | 이승재 |
/// @section 상세설명
/// - 이 클래스는 배수설비인허가대장 첨부파일 맵퍼 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public interface SwtSpmtAtchmnflDtMapper {
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 배수설비인허가대장 첨부파일 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSpmtAtchmnflDt
	/// @return List<SwtSpmtAtchmnflDt>
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<DrngEqpCnfmPrmisnAtchmnflDTO> select(DrngEqpCnfmPrmisnAtchmnflDTO drngEqpCnfmPrmisnAtchmnflDTO);
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 배수설비인허가대장 첨부파일 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSpmtAtchmnflDt
	/// @return Integer
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(SwtSpmtAtchmnflDt swtSpmtAtchmnflDt);
	
	public Integer insert(SwtSpmtAtchmnflDt swtSpmtAtchmnflDt);
}
