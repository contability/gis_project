package kr.co.gitt.kworks.projects.gn.service.cityPark;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.dto.CityParkChangeDetailsResultDTO;
import kr.co.gitt.kworks.projects.gn.dto.CityParkChangeDetailsSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.CityParkCivilAppealSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.CityParkSearchDTO;
import kr.co.gitt.kworks.projects.gn.model.CtyParkAs;
import kr.co.gitt.kworks.projects.gn.model.CtyPksdAs;
import kr.co.gitt.kworks.projects.gn.model.CtyRegisterInquiry;
import kr.co.gitt.kworks.projects.gn.model.CtyRserMa;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class CityParkService
/// kr.co.gitt.kworks.projects.gn.service.cityPark \n
///   ㄴ CityParkService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 4. 21. 오후 5:31:09 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도시공원 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface CityParkService {
	
	/////////////////////////////////////////////
	/// @fn listCityAllPark
	/// @brief 함수 간략한 설명 : 도시 공원 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyParkAs> listCityAllPark();

	/////////////////////////////////////////////
	/// @fn listCityPark
	/// @brief 함수 간략한 설명 : 도시 공원 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cityParkSearchDTO
	/// @param paginationInfo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyParkAs> listCityPark(CityParkSearchDTO cityParkSearchDTO, PaginationInfo paginationInfo);
	
	/////////////////////////////////////////////
	/// @fn listChangeDetails
	/// @brief 함수 간략한 설명 : 변천 내역 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param paginationInfo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CityParkChangeDetailsResultDTO> listChangeDetails(CityParkChangeDetailsSearchDTO searchDTO, PaginationInfo paginationInfo);
	
	/////////////////////////////////////////////
	/// @fn listCityParkZoneScap
	/// @brief 함수 간략한 설명 : 공원구역 - 조경화단 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyPksdAs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyPksdAs> listCityParkZoneScap(CtyPksdAs ctyPksdAs);
	
	/////////////////////////////////////////////
	/// @fn listCityParkZoneAthl
	/// @brief 함수 간략한 설명 : 공원구역 - 운동시설 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyPksdAs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyPksdAs> listCityParkZoneAthl(CtyPksdAs ctyPksdAs);
	
	/////////////////////////////////////////////
	/// @fn listCityParkZonePlay
	/// @brief 함수 간략한 설명 : 공원구역 - 유희시설 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyPksdAs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyPksdAs> listCityParkZonePlay(CtyPksdAs ctyPksdAs);

	/////////////////////////////////////////////
	/// @fn listCityParkCivilAppeal
	/// @brief 함수 간략한 설명 : 도시 공원 민원 관리 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cityParkCivilAppealSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyRserMa> listCityParkCivilAppeal(CityParkCivilAppealSearchDTO cityParkCivilAppealSearchDTO, PaginationInfo paginationInfo);
	
	/////////////////////////////////////////////
	/// @fn addCivilAppeal
	/// @brief 함수 간략한 설명 : 도시 공원 민원 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRserMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addCivilAppeal(CtyRserMa ctyRserMa) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn modifyCivilAppeal
	/// @brief 함수 간략한 설명 : 도시 공원 민원 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRserMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyCivilAppeal(CtyRserMa ctyRserMa);
	
	/////////////////////////////////////////////
	/// @fn removeCivilAppeal
	/// @brief 함수 간략한 설명 : 도시 공원 민원 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeCivilAppeal(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn listCityParkAthl
	/// @brief 함수 간략한 설명 : 운동시설 목록조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRegisterInquiry
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyRegisterInquiry> listCityParkAthl(CtyRegisterInquiry ctyRegisterInquiry);
	
	/////////////////////////////////////////////
	/// @fn listCityParkPlay
	/// @brief 함수 간략한 설명 : 유희시설 목록조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRegisterInquiry
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyRegisterInquiry> listCityParkPlay(CtyRegisterInquiry ctyRegisterInquiry);
	
	/////////////////////////////////////////////
	/// @fn listCityParkRestPs
	/// @brief 함수 간략한 설명 : 휴게시설(점) 목록조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRegisterInquiry
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyRegisterInquiry> listCityParkRestPs(CtyRegisterInquiry ctyRegisterInquiry);
	
	/////////////////////////////////////////////
	/// @fn listCityParkRestLs
	/// @brief 함수 간략한 설명 : 휴게시설(선) 목록조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRegisterInquiry
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyRegisterInquiry> listCityParkRestLs(CtyRegisterInquiry ctyRegisterInquiry);
	
	/////////////////////////////////////////////
	/// @fn listCityParkRestAs
	/// @brief 함수 간략한 설명 : 휴게시설(면) 목록조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRegisterInquiry
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyRegisterInquiry> listCityParkRestAs(CtyRegisterInquiry ctyRegisterInquiry);
	
	/////////////////////////////////////////////
	/// @fn listCityParkPksdAs
	/// @brief 함수 간략한 설명 : 공원구역 목록조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRegisterInquiry
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyRegisterInquiry> listCityParkPksdAs(CtyRegisterInquiry ctyRegisterInquiry);
}
