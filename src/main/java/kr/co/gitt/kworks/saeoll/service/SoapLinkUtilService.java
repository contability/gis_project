package kr.co.gitt.kworks.saeoll.service;

import java.util.ArrayList;

import kr.co.gitt.kworks.saeoll.model.AgrldReqstInfo;
import kr.co.gitt.kworks.saeoll.model.DfnndManageRegstr;

/////////////////////////////////////////////
/// @class SoapLinkUtilService
/// kr.co.gitt.kworks.saeoll.service \n
///   ㄴ SoapLinkUtilService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 10. 26. 오후 9:16:25 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 새올연계 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface SoapLinkUtilService {
	
	/////////////////////////////////////////////
	/// @fn soapLink
	/// @brief 함수 간략한 설명 : 지하수관정 연계
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ymdStr
	/// @param cntFirst
	/// @param cntEnd
	/// @param cndStr
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer soapLink(String ymdStr,String cntFirst, String cntEnd, String cndStr) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn soapLinkAgrldReqstInfo
	/// @brief 함수 간략한 설명 : 농지전용 연계
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ymdStr
	/// @param cntFirst
	/// @param cndStr
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public ArrayList<AgrldReqstInfo> soapLinkAgrldReqstInfo(String ymdStr,String cntFirst, String cndStr) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn soapLinkDfnndManageRegstr
	/// @brief 함수 간략한 설명 : 산지전용 연계
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ymdStr
	/// @param cntFirst
	/// @param cndStr
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public ArrayList<DfnndManageRegstr> soapLinkDfnndManageRegstr(String ymdStr,String cntFirst, String cndStr) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn makeCndLisatStr
	/// @brief 함수 간략한 설명 : 세부 조건문 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cndStr
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String makeCndLisatStr(String cndStr);
	
	public Integer soapLinkCmmnProp(String queryId) throws Exception;
}
