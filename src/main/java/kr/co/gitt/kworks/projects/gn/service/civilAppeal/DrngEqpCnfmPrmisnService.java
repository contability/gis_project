package kr.co.gitt.kworks.projects.gn.service.civilAppeal;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartRequest;

import kr.co.gitt.kworks.model.LpPaCbnd;
import kr.co.gitt.kworks.projects.gn.dto.DrngEqpCnfmPrmisnAtchmnflDTO;
import kr.co.gitt.kworks.projects.gn.dto.DrngEqpCnfmPrmisnDTO;
import kr.co.gitt.kworks.projects.gn.dto.DrngEqpVO;
import kr.co.gitt.kworks.projects.gn.model.SwtSpmtCnncDt;
import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class DrngEqpCnfmPrmisnService
/// kr.co.gitt.kworks.projects.gn.service.civilAppeal \n
///   ㄴ DrngEqpCnfmPrmisnService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 23. 오후 4:53:48 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
///	   | 수정자 | 이승재, 2019.12.09
/// @section 상세설명
/// - 이 클래스는 배수설비인허가 서비스 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface DrngEqpCnfmPrmisnService {

	/////////////////////////////////////////////
	/// @fn searchLocation
	/// @brief 함수 간략한 설명 : 배수설비인허가 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// - 작성자 : 이승재, 2019.12.09
	/// @param swtSpmtMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<LpPaCbnd> searchLocation(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn selectOneDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// - 수정자 : 이승재, 2019.12.14
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public DrngEqpCnfmPrmisnDTO selectOneDrngEqpCnfmPrmisn(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn removeDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가대장 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// - 작성자 : 이승재, 2019.12.14
	/// @param ftrCde
	/// @param ftrIdn
	/// @return boolean
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public boolean removeDrngEqpCnfmPrmisn(String ftrCde, Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn modifyDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// - 수정자 : 이승재, 2019.12.15
	/// @param drngEqpCnfmPrmisnDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyDrngEqpCnfmPrmisn(DrngEqpCnfmPrmisnDTO drngEqpCnfmPrmisnDTO);
	
	/////////////////////////////////////////////
	/// @fn addDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// - 수정자 : 이승재, 2019.12.18
	/// @param swtSpmtMa
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Long addDrngEqpCnfmPrmisn(DrngEqpCnfmPrmisnDTO drngEqpCnfmPrmisnDTO) throws FdlException;
	
	public Map<String, Object> quickSearch(String dataId, String objectid);
	
	/////////////////////////////////////////////
	/// @fn listAtchmnfl
	/// @brief 함수 간략한 설명 : 배수설비인허가 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// - 작성자 : 이승재, 2019.12.17
	/// @param  ftrIdn
	/// @return swtSpmtAtchmnflDt
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<DrngEqpCnfmPrmisnAtchmnflDTO> listAtchmnfl(Long ftrIdn);
	
	public Integer addAtchmnfl(DrngEqpCnfmPrmisnAtchmnflDTO drngEqpCnfmPrmisnAtchmnflDTO, MultipartRequest multipartRequest) throws FdlException, IOException;
	
	public Integer removeAtchmnfl(Long shtIdn, Long fileNo);
	
	/////////////////////////////////////////////
	/// @fn connectDrngEqpCnfmPrmisnAndDrngEqp
	/// @brief 함수 간략한 설명 : 배수설비인허가대장과 신규 물받이, 하수연결관의 연결 테이블 작성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// - 작성자 : 이승재, 2020.01.13
	/// @param  swtSpmtCnncDt
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void connectDrngEqpCnfmPrmisnAndDrngEqp(SwtSpmtCnncDt swtSpmtCnncDt);
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief 함수 간략한 설명 : 배수설비인허가 대장과 연결된 물받이, 하수연결관 정보 합께 가져오기
	/// @remark
	/// - 이승재, 2020.07.28
	/// - 함수의 상세 설명 : 
	/// @param drngEqpCnfmPrmisnDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<DrngEqpVO> listDrngEqp(DrngEqpCnfmPrmisnDTO drngEqpCnfmPrmisnDTO);
}
