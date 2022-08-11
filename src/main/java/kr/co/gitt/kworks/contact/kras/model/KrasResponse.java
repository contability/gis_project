package kr.co.gitt.kworks.contact.kras.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/////////////////////////////////////////////
/// @class KrasResponse
/// kworks.itf.vo \n
///   ㄴ KrasResponse.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 4:57:04 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 부동산종합공부시스템 연계 응답 클래스입니다.
///   -# 
/////////////////////////////////////////////
@XmlRootElement(name = "RESPONSE")
public class KrasResponse {

	/// 부동산종합공부시스템 연계 응답 HEADER 정보
	private KrasHeader header;

	/// 부동산종합공부시스템 연계 응답 BODY 정보
	private KrasBody body;

	@XmlElement(name = "HEADER")
	public KrasHeader getHeader() {
		return header;
	}

	public void setHeader(KrasHeader header) {
		this.header = header;
	}

	@XmlElement(name = "BODY")
	public KrasBody getBody() {
		return body;
	}

	public void setBody(KrasBody body) {
		this.body = body;
	}

}
