package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.ChgDtlsDt;

/////////////////////////////////////////////
/// @class ChgDtlsDtMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ ChgDtlsDtMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | yun4810 |
///    | Date | 2018. 10. 2. 오후 3:02:02 |
///    | Class Version | v1.0 |
///    | 작업자 | yun4810, Others... |
/// @section 상세설명
/// - 이 클래스는 변화탐지내역 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface ChgDtlsDtMapper {
	
	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 변화탐지내역 카운트
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgDtlsDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer listCount(ChgDtlsDt chgDtlsDt);
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 변화탐지내역 리스트
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgDtlsDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<ChgDtlsDt> list(ChgDtlsDt chgDtlsDt);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 변화탐지내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgDtlsDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(ChgDtlsDt chgDtlsDt);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 변화탐지내역 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeDetctNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public ChgDtlsDt selectOne(Long chngeDetctNo);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 변화 탐지 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgDtlsDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(ChgDtlsDt chgDtlsDt);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 변화 탐지 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeDetctNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(Long chngeDetctNo);

}
