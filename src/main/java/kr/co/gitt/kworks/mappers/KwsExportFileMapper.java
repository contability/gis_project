package kr.co.gitt.kworks.mappers;

import java.util.List;
import java.util.Map;

import kr.co.gitt.kworks.model.KwsExportFile;

/////////////////////////////////////////////
/// @class KwsExportFileMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsExportFileMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 6. 오후 5:06:21 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 파일 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsExportFileMapper {

	///////////////////////////////////////////
	/// @fn select
	/// @brief 함수 간략한 설명 : 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param map
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsExportFile> listAll(Map<String, Object> map);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportFile
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsExportFile kwsExportFile);
	
}
