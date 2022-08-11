package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.RdtPrsvHt;

/////////////////////////////////////////////
/// @class RdtPrsvHtMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ RdtPrsvHtMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2016. 11. 17. 오전 11:12:48 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 이력관리 - 도로 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface RdtPrsvHtMapper {

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
	public RdtPrsvHt selectOneFtrCde(RdtPrsvHt rdtPrsvHt);
	
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
	public List<RdtPrsvHt> listMnh(String domnId);
	
	/////////////////////////////////////////////
	/// @fn listManageHistRoad
	/// @brief 함수 간략한 설명 : 관리이력 리스트 조회 - 도로
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdtPrsvHt> listManageHistRoad(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn selectOneManageHistRoad
	/// @brief 함수 간략한 설명 : 관리이력 조회 - 도로
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public RdtPrsvHt selectOneManageHistRoad(Long shtIdn);
	
	/////////////////////////////////////////////
	/// @fn updateManageHistRoad
	/// @brief 함수 간략한 설명 : 관리이력 수정 - 도로 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer updateManageHistRoad(RdtPrsvHt rdtPrsvHt);
	
	/////////////////////////////////////////////
	/// @fn insertManageHistRoad
	/// @brief 함수 간략한 설명 : 관리이력 등록 - 도로
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insertManageHistRoad(RdtPrsvHt rdtPrsvHt);

}
