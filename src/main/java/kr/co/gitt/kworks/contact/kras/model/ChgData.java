package kr.co.gitt.kworks.contact.kras.model;

import javax.xml.bind.annotation.XmlElement;

/////////////////////////////////////////////
/// @class ChgData
/// kworks.itf.vo \n
///   ㄴ ChgData.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 5:06:58 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 변동현황 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class ChgData {

	/// 변동원인명
	private String chgRsnNm;
	
	/// 변동내역
	private String chgCntn;
	
	/// 변동일자
	private String chgYmd;
	
	/// 정리일자
	private String adjYmd;

	@XmlElement(name = "CHG_RSN_NM")
	public String getChgRsnNm() {
		return chgRsnNm;
	}

	public void setChgRsnNm(String chgRsnNm) {
		this.chgRsnNm = chgRsnNm;
	}

	@XmlElement(name = "CHG_CNTN")
	public String getChgCntn() {
		return chgCntn;
	}

	public void setChgCntn(String chgCntn) {
		this.chgCntn = chgCntn;
	}

	@XmlElement(name = "CHG_YMD")
	public String getChgYmd() {
		return chgYmd;
	}

	public void setChgYmd(String chgYmd) {
		this.chgYmd = chgYmd;
	}

	@XmlElement(name = "ADJ_YMD")
	public String getAdjYmd() {
		return adjYmd;
	}

	public void setAdjYmd(String adjYmd) {
		this.adjYmd = adjYmd;
	}
	
}
