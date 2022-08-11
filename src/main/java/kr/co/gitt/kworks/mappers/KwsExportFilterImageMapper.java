package kr.co.gitt.kworks.mappers;

import kr.co.gitt.kworks.model.KwsExportFilterImage;

/////////////////////////////////////////////
/// @class KwsExportFilterImageMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsExportFilterImageMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 4. 19. 오전 11:49:57 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 필터 이미지 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsExportFilterImageMapper {
	
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
	public KwsExportFilterImage selectOne(Long exportNo);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportFilterImage
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsExportFilterImage kwsExportFilterImage);

}
