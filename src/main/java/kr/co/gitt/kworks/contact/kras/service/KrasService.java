package kr.co.gitt.kworks.contact.kras.service;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import kr.co.gitt.kworks.contact.kras.model.KrasResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

/////////////////////////////////////////////
/// @class KrasService
/// kr.co.gitt.kworks.cntc.service.kras \n
///   ㄴ KrasService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 10. 11. 오전 11:22:35 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 부동산종합공부시스템 연계 공통 서비스입니다.
///   -# 
/////////////////////////////////////////////
public interface KrasService {
	
	/////////////////////////////////////////////
	/// @fn call
	/// @brief 함수 간략한 설명 : 부동산종합공부시스템 서비스 호출
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param parameters
	/// @return
	/// @throws ClientProtocolException
	/// @throws IOException
	/// @throws JAXBException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KrasResponse call(List<NameValuePair> parameters, Integer timeout) throws ClientProtocolException, IOException, JAXBException;

}
