package kr.co.gitt.kworks.projects.dh.service.cntrwkRegstr;

import java.util.List;

import org.springframework.web.multipart.MultipartRequest;

import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.dh.dto.CntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.dh.model.WttChngDt;
import kr.co.gitt.kworks.projects.dh.model.WttConsMa;
import kr.co.gitt.kworks.projects.dh.model.WttCostDt;
import kr.co.gitt.kworks.projects.dh.model.WttFlawDt;
import kr.co.gitt.kworks.projects.dh.model.WttSubcDt;
import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class WrppCntrwkRegstrService
/// kr.co.gitt.kworks.projects.dh.service.cntrwkRegstr \n
///   ㄴ WrppCntrwkRegstrService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 3. 오전 10:48:16 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 상수 공사대장 서비스 인터페이스 입니다
///   -# 
/////////////////////////////////////////////
public interface WrppCntrwkRegstrService {

	/////////////////////////////////////////////
	/// @fn listWrpp
	/// @brief 함수 간략한 설명 : 상수 공사대장 리스트 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<WttConsMa> listWrppCntrwkRegstr(WttConsMa wttConsMa);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 상수 공사대장 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public WttConsMa selectOneWrppCntrwkRegstr(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn modify
	/// @brief 함수 간략한 설명 : 상수 공사대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyWrppCntrwkRegstr(WttConsMa wttConsMa);
	
	/////////////////////////////////////////////
	/// @fn addWrppCntrwkRegstr
	/// @brief 함수 간략한 설명 : 상수 공사대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addWrppCntrwkRegstr(WttConsMa wttConsMa) throws FdlException ;
	
	/////////////////////////////////////////////
	/// @fn listWrppFlawMendngDtls
	/// @brief 함수 간략한 설명 : 상수 하자보수내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttFlawDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<WttFlawDt> listWrppFlawMendngDtls(WttFlawDt wttFlawDt);
	
	/////////////////////////////////////////////
	/// @fn selectOneWrppFlawMendngDtls
	/// @brief 함수 간략한 설명 : 상수 하자보수내역 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public WttFlawDt selectOneWrppFlawMendngDtls(CntrwkRegstrDTO cntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn modifyWrppFlawMendngDtls
	/// @brief 함수 간략한 설명 : 상수 하자보수내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttFlawDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyWrppFlawMendngDtls(WttFlawDt wttFlawDt);
	
	/////////////////////////////////////////////
	/// @fn addWrppFlawMendngDtls
	/// @brief 함수 간략한 설명 : 상수 하자보수내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttFlawDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addWrppFlawMendngDtls(WttFlawDt wttFlawDt) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeWrppFlawMendngDtls
	/// @brief 함수 간략한 설명 : 하자 보수 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeWrppFlawMendngDtls(Long shtIdn);
	
	/////////////////////////////////////////////
	/// @fn listWrppCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 상수 공사비 지급 내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttCostDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<WttCostDt> listWrppCntrwkCtPymntDtls(WttCostDt wttCostDt);
	
	/////////////////////////////////////////////
	/// @fn selectOneWrppCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 상수 공사비 지급 내역 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public WttCostDt selectOneWrppCntrwkCtPymntDtls(CntrwkRegstrDTO cntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn modifyWrppCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 상수 공사비 지급 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttCostDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyWrppCntrwkCtPymntDtls(WttCostDt wttCostDt);
	
	/////////////////////////////////////////////
	/// @fn addWrppCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 상수 공사비 지급 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttCostDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addWrppCntrwkCtPymntDtls(WttCostDt wttCostDt) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeWrppCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 상수 공사비 지급 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeWrppCntrwkCtPymntDtls(Long shtIdn);
		
	/////////////////////////////////////////////
	/// @fn listWrppDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 상수 설계 변경 내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttChngDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<WttChngDt> listWrppDsgnChangeDtls(WttChngDt wttChngDt);
	
	/////////////////////////////////////////////
	/// @fn selectOneWrppDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 상수 설계 변경 내역 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public WttChngDt selectOneWrppDsgnChangeDtls(CntrwkRegstrDTO cntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn modifyWrppDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 상수 설계 변경 내역 수정 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttChngDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyWrppDsgnChangeDtls(WttChngDt wttChngDt);
	
	/////////////////////////////////////////////
	/// @fn addWrppDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 상수 설계 변경 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttChngDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addWrppDsgnChangeDtls(WttChngDt wttChngDt) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeWrppDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 상수 설계 변경 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeWrppDsgnChangeDtls(Long shtIdn);
	
	/////////////////////////////////////////////
	/// @fn listWrppSubcntrDtls
	/// @brief 함수 간략한 설명 : 상수 하도급 내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSubcDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<WttSubcDt> listWrppSubcntrDtls(WttSubcDt wttSubcDt);
	
	/////////////////////////////////////////////
	/// @fn selectOneWrppSubcntrDtls
	/// @brief 함수 간략한 설명 : 상수 하도급 내역 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public WttSubcDt selectOneWrppSubcntrDtls(CntrwkRegstrDTO cntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn modifyWrppSubcntrDtls
	/// @brief 함수 간략한 설명 : 상수 하도급 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSubcDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyWrppSubcntrDtls(WttSubcDt wttSubcDt);
	
	/////////////////////////////////////////////
	/// @fn addWrppSubcntrDtls
	/// @brief 함수 간략한 설명 : 상수 하도급 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSubcDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addWrppSubcntrDtls(WttSubcDt wttSubcDt) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeWrppSubcntrDtls
	/// @brief 함수 간략한 설명 : 상수 하도급 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeWrppSubcntrDtls(Long shtIdn);
	
	/////////////////////////////////////////////
	/// @fn addWrppCntrwkRegstrPhoto
	/// @brief 함수 간략한 설명 : 상수 공사대장 이미지 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsImage
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addWrppCntrwkRegstrPhoto(KwsImage kwsImage, MultipartRequest multipartRequest) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn modifyWrppCntrwkRegstrPhoto
	/// @brief 함수 간략한 설명 : 상수 공사대장 이미지 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsImage
	/// @param multipartRequest
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyWrppCntrwkRegstrPhoto(KwsImage kwsImage, MultipartRequest multipartRequest) throws Exception;

	/////////////////////////////////////////////
	/// @fn searchSpatial
	/// @brief 함수 간략한 설명 : 공사 위치 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<String> searchSpatial(Long ftrIdn);
	
}
