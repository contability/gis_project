package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.dto.UserSearchDTO;
import kr.co.gitt.kworks.model.KwsUser;


/////////////////////////////////////////////
/// @class KwsUserMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsUserMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 16. 오후 12:17:20 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 관리 맵퍼입니다. 
///   -# 
/////////////////////////////////////////////
public interface KwsUserMapper {
	
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
	public Integer listCount(UserSearchDTO searchDTO);
	
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
	public List<KwsUser> list(UserSearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param userId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsUser selectOne(String userId);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUser
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsUser kwsUser);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param userVO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(KwsUser userVO);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 사용자 환경 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param userId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer deleteEnvrn(String userId);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param userId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(String userId);
	
	/////////////////////////////////////////////
	/// @fn listUserExcel
	/// @brief 함수 간략한 설명 : 엑셀 다운로드용 사용자 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsUser> listUserExcel(UserSearchDTO searchDTO);
	
	public List<KwsUser> listInfo(UserSearchDTO searchDTO);
	
	public KwsUser selectOneUserInfo(String userId);
	
	public Integer updateUserInfo(KwsUser userVO);
	
}
