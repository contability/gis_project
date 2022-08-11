package kr.co.gitt.kworks.mappers;

import kr.co.gitt.kworks.model.KwsFile;

/////////////////////////////////////////////
/// @class KwsFileMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsFileMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 3. 오전 9:54:26 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 파일 맵퍼입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsFileMapper {
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param fileNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsFile selectOne(Long fileNo);
	
	/////////////////////////////////////////////
	/// @fn selectFileNm
	/// @brief 함수 간략한 설명 : 토지사용증명서 파일원본명 단건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param streFileNm
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsFile selectFileNm(String streFileNm);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsFile
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsFile kwsFile);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param fileNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(Long fileNo);
	
	/////////////////////////////////////////////
	/// @fn deleteFileNm
	/// @brief 함수 간략한 설명 : 토지사용증명서 파일원본명 조건 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param streFileNm
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer deleteFileNm(String streFileNm);
	
}
