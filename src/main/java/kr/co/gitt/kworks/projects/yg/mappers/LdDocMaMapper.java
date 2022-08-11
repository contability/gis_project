package kr.co.gitt.kworks.projects.yg.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.yg.model.LdDocMa;

/////////////////////////////////////////////
/// @class LdDocMaMapper
/// kr.co.gitt.kworks.projects.yg.mappers \n
///   ㄴ LdDocMaMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2018. 4. 11. 오후 5:38:35 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 토지사용증명서 파일 맵퍼입니다.
///   -# 
/////////////////////////////////////////////
public interface LdDocMaMapper {

	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldDocMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LdDocMa> list(LdDocMa ldDocMa);
	
	/////////////////////////////////////////////
	/// @fn fileList
	/// @brief 함수 간략한 설명 : 토지사용증명서 파일조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param docFile
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LdDocMa> fileList(LdDocMa ldDocMa);

	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldDocMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(LdDocMa ldDocMa);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldDocMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(LdDocMa ldDocMa);
	
}
