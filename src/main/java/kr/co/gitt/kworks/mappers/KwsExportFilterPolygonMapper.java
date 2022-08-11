package kr.co.gitt.kworks.mappers;

import kr.co.gitt.kworks.model.KwsExportFilterPolygon;

/////////////////////////////////////////////
/// @class KwsExportFilterPolygonMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsExportFilterPolygonMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | libra |
///    | Date | 2018. 6. 5. 오후 3:50:27 |
///    | Class Version | v1.0 |
///    | 작업자 | libra, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 필터 다각형 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsExportFilterPolygonMapper {

	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 조회 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsExportFilterPolygon selectOne(Long exportNo);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportFilterPolygon
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsExportFilterPolygon kwsExportFilterPolygon);
	
}
