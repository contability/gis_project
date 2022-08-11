package kr.co.gitt.kworks.projects.dh.service.cntrwkRegstr;

import java.util.List;

import org.springframework.web.multipart.MultipartRequest;

import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.dh.dto.CntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.dh.model.SwtChngDt;
import kr.co.gitt.kworks.projects.dh.model.SwtConsMa;
import kr.co.gitt.kworks.projects.dh.model.SwtCostDt;
import kr.co.gitt.kworks.projects.dh.model.SwtFlawDt;
import kr.co.gitt.kworks.projects.dh.model.SwtSubcDt;
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
/// - 이 클래스는 하수 공사대장 서비스 인터페이스 입니다
///   -# 
/////////////////////////////////////////////
public interface SwgeCntrwkRegstrService {

	/////////////////////////////////////////////
	/// @fn listSwge
	/// @brief 함수 간략한 설명 : 하수 공사대장 리스트 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SwtConsMa> listSwgeCntrwkRegstr(SwtConsMa swtConsMa);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 하수 공사대장 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public SwtConsMa selectOneSwgeCntrwkRegstr(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn modify
	/// @brief 함수 간략한 설명 : 하수 공사대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifySwgeCntrwkRegstr(SwtConsMa swtConsMa);
	
	/////////////////////////////////////////////
	/// @fn addSwgeCntrwkRegstr
	/// @brief 함수 간략한 설명 : 하수 공사대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtConsMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addSwgeCntrwkRegstr(SwtConsMa swtConsMa) throws FdlException ;
	
	/////////////////////////////////////////////
	/// @fn listSwgeFlawMendngDtls
	/// @brief 함수 간략한 설명 : 하수 하자보수내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtFlawDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SwtFlawDt> listSwgeFlawMendngDtls(SwtFlawDt swtFlawDt);
	
	/////////////////////////////////////////////
	/// @fn selectOneSwgeFlawMendngDtls
	/// @brief 함수 간략한 설명 : 하수 하자보수내역 단 건 조회
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
	public SwtFlawDt selectOneSwgeFlawMendngDtls(CntrwkRegstrDTO cntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn modifySwgeFlawMendngDtls
	/// @brief 함수 간략한 설명 : 하수 하자보수내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtFlawDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifySwgeFlawMendngDtls(SwtFlawDt swtFlawDt);
	
	/////////////////////////////////////////////
	/// @fn addSwgeFlawMendngDtls
	/// @brief 함수 간략한 설명 : 하수 하자보수내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtFlawDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addSwgeFlawMendngDtls(SwtFlawDt swtFlawDt) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeSwgeFlawMendngDtls
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
	public Integer removeSwgeFlawMendngDtls(Long shtIdn);
	
	/////////////////////////////////////////////
	/// @fn listSwgeCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 하수 공사비 지급 내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtCostDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SwtCostDt> listSwgeCntrwkCtPymntDtls(SwtCostDt swtCostDt);
	
	/////////////////////////////////////////////
	/// @fn selectOneSwgeCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 하수 공사비 지급 내역 단 건 조회
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
	public SwtCostDt selectOneSwgeCntrwkCtPymntDtls(CntrwkRegstrDTO cntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn modifySwgeCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 하수 공사비 지급 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtCostDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifySwgeCntrwkCtPymntDtls(SwtCostDt swtCostDt);
	
	/////////////////////////////////////////////
	/// @fn addSwgeCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 하수 공사비 지급 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtCostDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addSwgeCntrwkCtPymntDtls(SwtCostDt swtCostDt) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeSwgeCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 하수 공사비 지급 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeSwgeCntrwkCtPymntDtls(Long shtIdn);
		
	/////////////////////////////////////////////
	/// @fn listSwgeDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 하수 설계 변경 내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtChngDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SwtChngDt> listSwgeDsgnChangeDtls(SwtChngDt swtChngDt);
	
	/////////////////////////////////////////////
	/// @fn selectOneSwgeDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 하수 설계 변경 내역 단 건 조회
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
	public SwtChngDt selectOneSwgeDsgnChangeDtls(CntrwkRegstrDTO cntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn modifySwgeDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 하수 설계 변경 내역 수정 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtChngDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifySwgeDsgnChangeDtls(SwtChngDt swtChngDt);
	
	/////////////////////////////////////////////
	/// @fn addSwgeDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 하수 설계 변경 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtChngDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addSwgeDsgnChangeDtls(SwtChngDt swtChngDt) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeSwgeDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 하수 설계 변경 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeSwgeDsgnChangeDtls(Long shtIdn);
	
	/////////////////////////////////////////////
	/// @fn listSwgeSubcntrDtls
	/// @brief 함수 간략한 설명 : 하수 하도급 내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSubcDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SwtSubcDt> listSwgeSubcntrDtls(SwtSubcDt swtSubcDt);
	
	/////////////////////////////////////////////
	/// @fn selectOneSwgeSubcntrDtls
	/// @brief 함수 간략한 설명 : 하수 하도급 내역 단 건 조회
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
	public SwtSubcDt selectOneSwgeSubcntrDtls(CntrwkRegstrDTO cntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn modifySwgeSubcntrDtls
	/// @brief 함수 간략한 설명 : 하수 하도급 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSubcDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifySwgeSubcntrDtls(SwtSubcDt swtSubcDt);
	
	/////////////////////////////////////////////
	/// @fn addSwgeSubcntrDtls
	/// @brief 함수 간략한 설명 : 하수 하도급 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSubcDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addSwgeSubcntrDtls(SwtSubcDt swtSubcDt) throws FdlException;
	
	/////////////////////////////////////////////
	/// @fn removeSwgeSubcntrDtls
	/// @brief 함수 간략한 설명 : 하수 하도급 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeSwgeSubcntrDtls(Long shtIdn);
	
	///////////////////////////////////////////
	/// @fn addSwgeCntrwkRegstrPhoto
	/// @brief 함수 간략한 설명 : 하수 공사대장 이미지 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsImage
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addSwgeCntrwkRegstrPhoto(KwsImage kwsImage, MultipartRequest multipartRequest) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn modifySwgeCntrwkRegstrPhoto
	/// @brief 함수 간략한 설명 : 하수 공사대장 이미지 수정
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
	public Integer modifySwgeCntrwkRegstrPhoto(KwsImage kwsImage, MultipartRequest multipartRequest) throws Exception;

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
