package kr.co.gitt.kworks.mappers;

import kr.co.gitt.kworks.model.KwsExportFilterOutpt;

/////////////////////////////////////////////
/// @class KwsExportFilterOutptMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsExportFilterOutptMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 9. 오전 9:52:05 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 필터 출력 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsExportFilterOutptMapper {

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
	public KwsExportFilterOutpt selectOne(Long exportNo);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportFilterOutpt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsExportFilterOutpt kwsExportFilterOutpt);
	
}
