package kr.co.gitt.kworks.mappers;

import kr.co.gitt.kworks.model.KwsExportExcelOptn;

/////////////////////////////////////////////
/// @class KwsExportExcelOptnMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsExportExcelOptnMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | "윤중근" |
///    | Date | 2016. 9. 7. 오후 2:24:25 |
///    | Class Version | v1.0 |
///    | 작업자 | "윤중근", Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 엑셀 옵션 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsExportExcelOptnMapper {
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsExportExcelOptn selectOne(Long exportNo);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportExcelOptn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsExportExcelOptn kwsExportExcelOptn);

}
