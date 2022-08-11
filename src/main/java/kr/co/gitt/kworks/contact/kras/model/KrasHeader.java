package kr.co.gitt.kworks.contact.kras.model;

import javax.xml.bind.annotation.XmlElement;

/////////////////////////////////////////////
/// @class KrasHeader
/// kworks.itf.vo \n
///   ㄴ KrasHeader.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 4:58:42 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 부동산종합공부시스템 연계 응답 HEADER 정보 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class KrasHeader {

	/// 응답코드
	private String code;
	
	/// 응답메세지
	private String message;

	@XmlElement(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@XmlElement(name = "MESSAGE")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
