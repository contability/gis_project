package kr.co.gitt.kworks.projects.dh.service.cntrwkRegstr;

import java.util.List;

import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.dh.dto.CntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.dh.model.RdtChngDt;
import kr.co.gitt.kworks.projects.dh.model.RdtConsMa;
import kr.co.gitt.kworks.projects.dh.model.RdtCostDt;
import kr.co.gitt.kworks.projects.dh.model.RdtFlawDt;
import kr.co.gitt.kworks.projects.dh.model.RdtSubcDt;

import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class CntrwkRegstrService
/// kr.co.gitt.kworks.projects.dh.service.cntrwkRegstr \n
///   ㄴ CntrwkRegstrService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 3. 오전 10:48:16 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 도로 공사대장 서비스 인터페이스 입니다
///   -# 
/////////////////////////////////////////////
public interface RoadCntrwkRegstrService {

	/////////////////////////////////////////////
	/// @fn listRoad
	/// @brief 함수 간략한 설명 : 도로 공사대장 리스트 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdtConsMa> listRoadCntrwkRegstr(RdtConsMa rdtConsMa);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 도로 공사대장 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public RdtConsMa selectOneRoadCntrwkRegstr(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn modify
	/// @brief 함수 간략한 설명 : 도로 공사대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyRoadCntrwkRegstr(RdtConsMa rdtConsMa);
	
	/////////////////////////////////////////////
	/// @fn addRoadCntrwkRegstr
	/// @brief 함수 간략한 설명 : 도로 공사대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addRoadCntrwkRegstr(RdtConsMa rdtConsMa) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn listRoadFlawMendngDtls
	/// @brief 함수 간략한 설명 : 도로 하자보수내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtFlawDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdtFlawDt> listRoadFlawMendngDtls(RdtFlawDt rdtFlawDt);
	
	/////////////////////////////////////////////
	/// @fn selectOneRoadFlawMendngDtls
	/// @brief 함수 간략한 설명 : 도로 하자보수내역 단 건 조회
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
	public RdtFlawDt selectOneRoadFlawMendngDtls(CntrwkRegstrDTO cntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn modifyRoadFlawMendngDtls
	/// @brief 함수 간략한 설명 : 도로 하자보수내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtFlawDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyRoadFlawMendngDtls(RdtFlawDt rdtFlawDt);
	
	/////////////////////////////////////////////
	/// @fn addRoadFlawMendngDtls
	/// @brief 함수 간략한 설명 : 도로 하자보수내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtFlawDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addRoadFlawMendngDtls(RdtFlawDt rdtFlawDt) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeRoadFlawMendngDtls
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
	public Integer removeRoadFlawMendngDtls(Long shtIdn);
	
	/////////////////////////////////////////////
	/// @fn listRoadCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 도로 공사비 지급 내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtCostDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdtCostDt> listRoadCntrwkCtPymntDtls(RdtCostDt rdtCostDt);
	
	/////////////////////////////////////////////
	/// @fn selectOneRoadCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 도로 공사비 지급 내역 단 건 조회
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
	public RdtCostDt selectOneRoadCntrwkCtPymntDtls(CntrwkRegstrDTO cntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn modifyRoadCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 도로 공사비 지급 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtCostDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyRoadCntrwkCtPymntDtls(RdtCostDt rdtCostDt);
	
	/////////////////////////////////////////////
	/// @fn addRoadCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 도로 공사비 지급 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtCostDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addRoadCntrwkCtPymntDtls(RdtCostDt rdtCostDt) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeRoadCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 도로 공사비 지급 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeRoadCntrwkCtPymntDtls(Long shtIdn);
		
	/////////////////////////////////////////////
	/// @fn listRoadDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 도로 설계 변경 내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtChngDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdtChngDt> listRoadDsgnChangeDtls(RdtChngDt rdtChngDt);
	
	/////////////////////////////////////////////
	/// @fn selectOneRoadDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 도로 설계 변경 내역 단 건 조회
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
	public RdtChngDt selectOneRoadDsgnChangeDtls(CntrwkRegstrDTO cntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn modifyRoadDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 도로 설계 변경 내역 수정 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtChngDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyRoadDsgnChangeDtls(RdtChngDt rdtChngDt);
	
	/////////////////////////////////////////////
	/// @fn addRoadDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 도로 설계 변경 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtChngDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addRoadDsgnChangeDtls(RdtChngDt rdtChngDt) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeRoadDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 도로 설계 변경 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeRoadDsgnChangeDtls(Long shtIdn);
	
	/////////////////////////////////////////////
	/// @fn listRoadSubcntrDtls
	/// @brief 함수 간략한 설명 : 도로 하도급 내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtSubcDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdtSubcDt> listRoadSubcntrDtls(RdtSubcDt rdtSubcDt);
	
	/////////////////////////////////////////////
	/// @fn selectOneRoadSubcntrDtls
	/// @brief 함수 간략한 설명 : 도로 하도급 내역 단 건 조회
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
	public RdtSubcDt selectOneRoadSubcntrDtls(CntrwkRegstrDTO cntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn modifyRoadSubcntrDtls
	/// @brief 함수 간략한 설명 : 도로 하도급 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtSubcDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyRoadSubcntrDtls(RdtSubcDt rdtSubcDt);
	
	/////////////////////////////////////////////
	/// @fn addRoadSubcntrDtls
	/// @brief 함수 간략한 설명 : 도로 하도급 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtSubcDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addRoadSubcntrDtls(RdtSubcDt rdtSubcDt) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeRoadSubcntrDtls
	/// @brief 함수 간략한 설명 : 도로 하도급 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeRoadSubcntrDtls(Long shtIdn);
	
	/////////////////////////////////////////////
	/// @fn addRoadCntrwkRegstrPhoto
	/// @brief 함수 간략한 설명 : 도로 공사대장 이미지 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsImage
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addRoadCntrwkRegstrPhoto(KwsImage kwsImage, MultipartRequest multipartRequest) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn modifyRoadCntrwkRegstrPhoto
	/// @brief 함수 간략한 설명 : 도로 공사대장 이미지 수정
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
	public Integer modifyRoadCntrwkRegstrPhoto(KwsImage kwsImage, MultipartRequest multipartRequest) throws Exception;
	
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
