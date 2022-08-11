package kr.co.gitt.kworks.projects.sc.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.sc.model.WttWserMa;

/////////////////////////////////////////////
/// @class WttWserMaMapper
/// kr.co.gitt.kworks.projects.sc.mappers \n
///   ㄴ WttWserMaMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 22. 오후 2:16:17 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 상수민원관리 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface WttWserMaMapper {

	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 상수민원 리스트 조회 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtgRserMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<WttWserMa> list(WttWserMa wttWserMa);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 상수민원 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public WttWserMa selectOne(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 상수민원 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtgRserMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(WttWserMa wttWserMa);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 상수민원 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtgRserMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(WttWserMa wttWserMa);
	
}
