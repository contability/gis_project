package kr.co.gitt.kworks.projects.sc.service.mesrSgnal;

import java.util.List;

import kr.co.gitt.kworks.projects.sc.model.EttCpsvDt;

import org.springframework.web.multipart.MultipartRequest;

/////////////////////////////////////////////
/// @class MesrSgnalSttusExaminHistService
/// kr.co.gitt.kworks.projects.sc.service.mesrSgnal \n
///   ㄴ MesrSgnalSttusExaminHistService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 4. 26. 오후 12:15:06 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 측량표지현황조사 서비스 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface MesrSgnalSttusExaminHistService {
	
	/////////////////////////////////////////////
	/// @fn listMesrSgnalSttusExaminHist
	/// @brief 함수 간략한 설명 : 측량표지현황조사 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettCpsvDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<EttCpsvDt> listMesrSgnalSttusExaminHist(EttCpsvDt ettCpsvDt);
	
	/////////////////////////////////////////////
	/// @fn selectOneMesrSgnalSttusExaminHist
	/// @brief 함수 간략한 설명 : 측량표지현황조사 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettCpsvDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public EttCpsvDt selectOneMesrSgnalSttusExaminHist(EttCpsvDt ettCpsvDt);
	
	/////////////////////////////////////////////
	/// @fn addMesrSgnalSttusExaminHist
	/// @brief 함수 간략한 설명 : 측량표지현황조사 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettCpsvDt
	/// @param multipartRequest
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addMesrSgnalSttusExaminHist(EttCpsvDt ettCpsvDt, MultipartRequest multipartRequest) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn modifyMesrSgnalSttusExaminHist
	/// @brief 함수 간략한 설명 : 측량표지현황조사 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettCpsvDt
	/// @param multipartRequest
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyMesrSgnalSttusExaminHist(EttCpsvDt ettCpsvDt, MultipartRequest multipartRequest) throws Exception;
	
}
