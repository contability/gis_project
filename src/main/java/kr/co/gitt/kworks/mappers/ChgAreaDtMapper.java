package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.ChgAreaDt;

/////////////////////////////////////////////
/// @class ChgAreaDtMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ ChgAreaDtMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | yun4810 |
///    | Date | 2018. 10. 5. 오후 3:55:02 |
///    | Class Version | v1.0 |
///    | 작업자 | yun4810, Others... |
/// @section 상세설명
/// - 이 클래스는 변화 탐지 지역 맵퍼 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public interface ChgAreaDtMapper {
	
	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 건 수 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgAreaDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer listCount(ChgAreaDt chgAreaDt);

	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgAreaDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<ChgAreaDt> list(ChgAreaDt chgAreaDt);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeDetctNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public ChgAreaDt selectOne(Long chngeAreaNo);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeAreaNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(Long chngeAreaNo);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgAreaDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(ChgAreaDt chgAreaDt);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 등록 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgAreaDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(ChgAreaDt chgAreaDt);
	
}
