package kr.co.gitt.kworks.mappers;

import java.util.List;
import java.util.Map;

import kr.co.gitt.kworks.model.KwsNoticeFile;

/////////////////////////////////////////////
/// @class KwsNoticeFileMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsNoticeFileMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 3. 오전 9:49:47 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 공지사항 파일 맵퍼입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsNoticeFileMapper {
	
	/////////////////////////////////////////////
	/// @fn select
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param map
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsNoticeFile> listAll(Map<String, Object> map);

	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsNoticeFile
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsNoticeFile kwsNoticeFile);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsNoticeFile
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(KwsNoticeFile kwsNoticeFile);
	
}
