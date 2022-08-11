package kr.co.gitt.kworks.service.hist;

import java.util.List;


import kr.co.gitt.kworks.model.CmtPrsvHt;
import kr.co.gitt.kworks.model.RdtPrsvHt;
import kr.co.gitt.kworks.model.SwtPrsvHt;
import kr.co.gitt.kworks.model.WttPrsvHt;
import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class ManageHistService
/// kr.co.gitt.kworks.service.hist \n
///   ㄴ ManageHistService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2016. 11. 17. 오전 10:51:53 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 관리이력 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface ManageHistService {

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
	/// @brief 함수 간략한 설명 : 관리이력구분 조회
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
	/// @fn modifyManageHistRoad
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
	public Integer modifyManageHistRoad(RdtPrsvHt rdtPrsvHt);
	
	/////////////////////////////////////////////
	/// @fn addManageHistRoad
	/// @brief 함수 간략한 설명 : 관리이력 등록 - 도로
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addManageHistRoad(RdtPrsvHt rdtPrsvHt) throws FdlException;
	
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
	/// @fn modifyManageHistWater
	/// @brief 함수 간략한 설명 : 관리이력 수정 - 상수 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyManageHistWater(WttPrsvHt wttPrsvHt);
	
	/////////////////////////////////////////////
	/// @fn addManageHistWater
	/// @brief 함수 간략한 설명 : 관리이력 등록 - 상수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addManageHistWater(WttPrsvHt wttPrsvHt) throws FdlException;
	
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
	/// @fn modifyManageHistSewage
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
	public Integer modifyManageHistSewage(SwtPrsvHt swtPrsvHt);
	
	/////////////////////////////////////////////
	/// @fn addManageHistSewage
	/// @brief 함수 간략한 설명 : 관리이력 등록 - 하수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addManageHistSewage(SwtPrsvHt swtPrsvHt) throws FdlException;
	
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
	/// @fn modifyManageHistCommon
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
	public Integer modifyManageHistCommon(CmtPrsvHt cmtPrsvHt);
	
	/////////////////////////////////////////////
	/// @fn addManageHistCommon
	/// @brief 함수 간략한 설명 : 관리이력 등록 - 공통
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cmtPrsvHt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addManageHistCommon(CmtPrsvHt cmtPrsvHt) throws FdlException;
	}
