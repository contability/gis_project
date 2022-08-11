package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.ExportHistoryResultDTO;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsExportOutpt;

/////////////////////////////////////////////
/// @class KwsExportOutptMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsExportOutptMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 23. 오후 6:25:42 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 출력 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsExportOutptMapper {
	
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
	public List<ExportHistoryResultDTO> list(SearchDTO searchDTO);

	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportOutpt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsExportOutpt kwsExportOutpt);
	
}
