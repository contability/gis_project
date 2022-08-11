package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.EditHistoryResultDTO;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsEditHist;

/////////////////////////////////////////////
/// @class KwsEditHistMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsEditHistMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 21. 오후 3:01:10 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 
///   -# 
/////////////////////////////////////////////
public interface KwsEditHistMapper {

	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 목록 수 검색
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
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<EditHistoryResultDTO> list(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsEditHist
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsEditHist kwsEditHist);
	
	/////////////////////////////////////////////
	/// @fn listEditHistoryExcel
	/// @brief 함수 간략한 설명 : 엑셀 다운로드용 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<EditHistoryResultDTO> listEditHistoryExcel(SearchDTO searchDTO);
	
}
