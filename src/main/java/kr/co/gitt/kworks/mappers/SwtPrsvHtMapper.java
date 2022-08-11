package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.SwtPrsvHt;

/////////////////////////////////////////////
/// @class SwlPrsvHtMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ SwlPrsvHtMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2016. 11. 21. 오전 11:02:08 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 관리이력 - 하수 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface SwtPrsvHtMapper {

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
	public SwtPrsvHt selectOneFtrCde(SwtPrsvHt swtPrsvHt);
	
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
	public List<SwtPrsvHt> listMnh(String domnId);
	
	/////////////////////////////////////////////
	/// @fn listManageHistSewage
	/// @brief 함수 간략한 설명 : 관리이력 리스트 조회 - 하수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SwtPrsvHt> listManageHistSewage(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn selectOneManageHistSewage
	/// @brief 함수 간략한 설명 : 관리이력 조회 - 하수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public SwtPrsvHt selectOneManageHistSewage(Long shtIdn);
	
	/////////////////////////////////////////////
	/// @fn updateManageHistSewage
	/// @brief 함수 간략한 설명 : 관리이력 수정 - 하수 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer updateManageHistSewage(SwtPrsvHt swtPrsvHt);
	
	/////////////////////////////////////////////
	/// @fn insertManageHistSewage
	/// @brief 함수 간략한 설명 : 관리이력 등록 - 하수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insertManageHistSewage(SwtPrsvHt swtPrsvHt);

}
