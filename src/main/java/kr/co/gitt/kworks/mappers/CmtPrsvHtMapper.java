package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.dto.cntrwkRegstr.FacilityResultDTO;
import kr.co.gitt.kworks.model.CmtPrsvHt;

/////////////////////////////////////////////
/// @class CmtPrsvHtMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ CmtPrsvHtMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2016. 11. 21. 오전 11:02:08 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 관리이력 - 공통 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface CmtPrsvHtMapper {
	
	/////////////////////////////////////////////
	/// @fn selectOneFtrCde
	/// @brief 함수 간략한 설명 : 지형지물부호 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cmtPrsvHt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public CmtPrsvHt selectOneFtrCde(CmtPrsvHt cmtPrsvHt);
	
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
	public List<CmtPrsvHt> listMnh(String domnId);
	
	/////////////////////////////////////////////
	/// @fn listManageHistCommon
	/// @brief 함수 간략한 설명 : 관리이력 리스트 조회 - 공통
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CmtPrsvHt> listManageHistCommon(CmtPrsvHt cmtPrsvHt);
	
	/////////////////////////////////////////////
	/// @fn listRoadCntrwkRegstrManageHistCommon
	/// @brief 함수 간략한 설명 : 관리이력 리스트 조회 - 도로공사대장 연계
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CmtPrsvHt> listRoadCntrwkRegstrManageHistCommon(CmtPrsvHt cmtPrsvHt);
	
	/////////////////////////////////////////////
	/// @fn listWrppCntrwkRegstrManageHistCommon
	/// @brief 함수 간략한 설명 : 관리이력 리스트 조회 - 상수공사대장 연계
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CmtPrsvHt> listWrppCntrwkRegstrManageHistCommon(CmtPrsvHt cmtPrsvHt);
	
	/////////////////////////////////////////////
	/// @fn listSwgeCntrwkRegstrManageHistCommon
	/// @brief 함수 간략한 설명 : 관리이력 리스트 조회 - 하수공사대장 연계
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CmtPrsvHt> listSwgeCntrwkRegstrManageHistCommon(CmtPrsvHt cmtPrsvHt);
	
	/////////////////////////////////////////////
	/// @fn selectOneManageHistCommon
	/// @brief 함수 간략한 설명 : 관리이력 조회 - 공통
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public CmtPrsvHt selectOneManageHistCommon(Long shtIdn);
	
	/////////////////////////////////////////////
	/// @fn updateManageHistCommon
	/// @brief 함수 간략한 설명 : 관리이력 수정 - 공통
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cmtPrsvHt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer updateManageHistCommon(CmtPrsvHt cmtPrsvHt);
	
	/////////////////////////////////////////////
	/// @fn insertManageHistSewage
	/// @brief 함수 간략한 설명 : 관리이력 등록 - 공통
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cmtPrsvHt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insertManageHistCommon(CmtPrsvHt cmtPrsvHt);
	
	/////////////////////////////////////////////
	/// @fn listFacilities
	/// @brief 함수 간략한 설명 : 시설물 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<FacilityResultDTO> listFacilities(CmtPrsvHt cmtPrsvHt);
	
}
