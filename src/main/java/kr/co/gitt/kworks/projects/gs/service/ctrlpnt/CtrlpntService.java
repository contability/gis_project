package kr.co.gitt.kworks.projects.gs.service.ctrlpnt;

import java.util.List;

import kr.co.gitt.kworks.projects.gs.model.EtlRgcpPs;

import org.springframework.web.multipart.MultipartRequest;

/////////////////////////////////////////////
/// @class CtrlpntService
/// kr.co.gitt.kworks.projects.gn.service.ctrlpnt \n
///   ㄴ CtrlpntService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 14. 오후 3:51:21 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 기준점 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface CtrlpntService {
	
	/////////////////////////////////////////////
	/// @fn listCtrlpnt
	/// @brief 함수 간략한 설명 : 기준점 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param etlRgcpPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<EtlRgcpPs> listCtrlpnt(EtlRgcpPs etlRgcpPs);
	
	/////////////////////////////////////////////
	/// @fn selectOneCtrlpnt
	/// @brief 함수 간략한 설명 : 기준점 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public EtlRgcpPs selectOneCtrlpnt(EtlRgcpPs etlRgcpPs);
	
	/////////////////////////////////////////////
	/// @fn modifyRoadCtrlpnt
	/// @brief 함수 간략한 설명 : 기준점 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param etlRgcpPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyCtrlpnt(EtlRgcpPs etlRgcpPs, MultipartRequest multipartRequest) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn addRoadCtrlpnt
	/// @brief 함수 간략한 설명 : 기준점 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param etlRgcpPs
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addCtrlpnt(EtlRgcpPs etlRgcpPs, MultipartRequest multipartRequest) throws Exception;

}
