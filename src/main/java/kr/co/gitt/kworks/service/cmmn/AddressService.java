package kr.co.gitt.kworks.service.cmmn;

/////////////////////////////////////////////
/// @class AddressService
/// kr.co.gitt.kworks.service.cmmn \n
///   ㄴ AddressService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2018. 4. 20. 오전 11:16:00 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 주소조회 서비스 클래스입니다.
///   -# 
/////////////////////////////////////////////
public interface AddressService {
	
	/////////////////////////////////////////////
	/// @fn getAdministrationZone
	/// @brief 함수 간략한 설명 : 행정구역 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param bjdCde
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String getAdministrationZone(String dataId, String bjdCde) throws Exception;
	
	
	/////////////////////////////////////////////
	/// @fn getAddress
	/// @brief 함수 간략한 설명 : 주소 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param pnu
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String getAddress(String dataId, String pnu) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn getFullAddress
	/// @brief 함수 간략한 설명 : 전체주소(행정구역 + 주소) 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param zoneDataId
	/// @param dataId
	/// @param pnu
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String getFullAddress(String zoneDataId, String dataId, String pnu) throws Exception;

}
