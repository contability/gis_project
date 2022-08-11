package kr.co.gitt.kworks.projects.gn.service.ladCntr;

import java.io.IOException;
import java.util.List;

import kr.co.gitt.kworks.model.LpPaCbnd;
import kr.co.gitt.kworks.projects.gn.model.LdConsMa;
import kr.co.gitt.kworks.projects.gn.model.LdDocMa;
import kr.co.gitt.kworks.projects.gn.model.LdUseMa;

import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class LadCntrwkRegstrService
/// kr.co.gitt.kworks.service.ladCntr \n
///   ㄴ LadCntrwkRegstrService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2018. 3. 27. 오후 6:19:43 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 토지중심공사대장 서비스 인터페이스 입니다.
///   -# 
/////////////////////////////////////////////
public interface LadCntrwkRegstrService {

	/////////////////////////////////////////////
	/// @fn listLandCenterCetrwkRegstr
	/// @brief 함수 간략한 설명 : 토지중심공사대장 리스트 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LdConsMa> listLandCenterCetrwkRegstr(LdConsMa ldConsMa);
	
	/////////////////////////////////////////////
	/// @fn selectOneLandCenterCetrwkRegstr
	/// @brief 함수 간략한 설명 : 토지중심공사대장 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public LdConsMa selectOneLandCenterCetrwkRegstr(Long cntIdn);
	
	/////////////////////////////////////////////
	/// @fn modifyLandCenterCetrwkRegstr
	/// @brief 함수 간략한 설명 : 토지중심공사대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyLandCenterCetrwkRegstr(LdConsMa ldConsMa) throws FdlException ;
	
	/////////////////////////////////////////////
	/// @fn addLandCenterCetrwkRegstr
	/// @brief 함수 간략한 설명 : 토지중심공사대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsMa
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addLandCenterCetrwkRegstr(LdConsMa ldConsMa) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeLandCenterCetrwkRegstr
	/// @brief 함수 간략한 설명 : 토지중심공사대장 삭제 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeLandCenterCetrwkRegstr(Long cntIdn) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn listAllLandCenterCetrwkRegstr
	/// @brief 함수 간략한 설명 : 공사대장명 목록 전체리스트
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LdConsMa> listAllLandCenterCetrwkRegstr(LdConsMa ldConsMa);

	/////////////////////////////////////////////
	/// @fn cntSelectOneLandCenterCetrwkRegstr
	/// @brief 함수 간략한 설명 : 공사번호, 공사명 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public LdConsMa cntSelectOneLandCenterCetrwkRegstr(Long cntIdn);
	
	/////////////////////////////////////////////
	/// @fn listLandUseInfoRegstr
	/// @brief 함수 간략한 설명 : 토지사용정보대장 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LdUseMa> listLandUseInfoRegstr(Long cntIdn);
	
	/////////////////////////////////////////////
	/// @fn listAllLandUseInfoRegstr
	/// @brief 함수 간략한 설명 : 토지사용대장정보 전체 리스트 목록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldUseMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LdUseMa> listAllLandUseInfoRegstr(LdUseMa ldUseMa);
	
	/////////////////////////////////////////////
	/// @fn selectOneLandUseInfoRegstr
	/// @brief 함수 간략한 설명 : 토지사용정보대장 단건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param luiIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public LdUseMa selectOneLandUseInfoRegstr(Long luiIdn);
	
	/////////////////////////////////////////////
	/// @fn modifyLandUseInfoRegstr
	/// @brief 함수 간략한 설명 : 토지사용정보대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldUseMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyLandUseInfoRegstr(LdUseMa ldUseMa, MultipartRequest multipartRequest) throws FdlException, IOException ;
	
	/////////////////////////////////////////////
	/// @fn addLandUseInfoRegstr
	/// @brief 함수 간략한 설명 : 토지사용정보대장, 토지사용증명서 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldUseMa
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addLandUseInfoRegstr(LdUseMa ldUseMa, MultipartRequest multipartRequest) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn removeLandUseInfoRegstr
	/// @brief 함수 간략한 설명 : 토지사용정보대장 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param luiIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeLandUseInfoRegstr(LdUseMa ldUseMa) throws Exception ;
	
	/////////////////////////////////////////////
	/// @fn searchSpatial
	/// @brief 함수 간략한 설명 : 공사 위치 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param luiIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<Integer> searchSpatial(Long luiIdn);
	
	/////////////////////////////////////////////
	/// @fn landUseInfoQuickSearch
	/// @brief 함수 간략한 설명 : 토지사용정보 퀵버튼 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param pnu
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LdUseMa> landUseInfoQuickSearch(String pnu);
	
	/////////////////////////////////////////////
	/// @fn landDocFileList
	/// @brief 함수 간략한 설명 : 토지증명서 파일조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param docFile
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LdDocMa> landDocFileList(Long cntIdn, Long luiIdn);
	
	/////////////////////////////////////////////
	/// @fn searchPnuList
	/// @brief 함수 간략한 설명 : 연속지적도 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param lpPaCbnd
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public LpPaCbnd searchPnuList(LpPaCbnd lpPaCbnd);
}
