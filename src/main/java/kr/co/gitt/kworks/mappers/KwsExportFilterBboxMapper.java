package kr.co.gitt.kworks.mappers;

import kr.co.gitt.kworks.model.KwsExportFilterBbox;

/////////////////////////////////////////////
/// @class KwsExportFilterBboxMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsExportFilterBboxMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | "윤중근" |
///    | Date | 2016. 9. 7. 오후 2:44:28 |
///    | Class Version | v1.0 |
///    | 작업자 | "윤중근", Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 필터 BBOX 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsExportFilterBboxMapper {
	
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
	public KwsExportFilterBbox selectOne(Long exportNo);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportFilterBbox
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsExportFilterBbox kwsExportFilterBbox);

}
