package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.WttPrsvHt;

/////////////////////////////////////////////
/// @class WtlPrsvHtMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ WtlPrsvHtMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2016. 11. 21. 오전 10:48:21 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 이력관리 - 상수 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface WttPrsvHtMapper {

	/////////////////////////////////////////////
	/// @fn selectOneFtrCde
	/// @brief 함수 간략한 설명 : 지형지물부호 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public WttPrsvHt selectOneFtrCde(WttPrsvHt wttPrsvHt);
	
	/////////////////////////////////////////////
	/// @fn listMnh
	/// @brief 함수 간략한 설명 : 관리이력구분 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param domnId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<WttPrsvHt> listMnh(String domnId);
	
	/////////////////////////////////////////////
	/// @fn listManageHistWater
	/// @brief 함수 간략한 설명 : 관리이력 리스트 조회 - 상수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<WttPrsvHt> listManageHistWater(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn selectOneManageHistWater
	/// @brief 함수 간략한 설명 : 관리이력 조회 - 상수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public WttPrsvHt selectOneManageHistWater(Long shtIdn);
	
	/////////////////////////////////////////////
	/// @fn updateManageHistWater
	/// @brief 함수 간략한 설명 : 관리이력 수정 -상수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer updateManageHistWater(WttPrsvHt wttPrsvHt);
	
	/////////////////////////////////////////////
	/// @fn insertManageHistWater
	/// @brief 함수 간략한 설명 : 관리이력 등록 - 상수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insertManageHistWater(WttPrsvHt wttPrsvHt);
	
}
