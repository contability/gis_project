package kr.co.gitt.kworks.contact.kras.model;

import javax.xml.bind.annotation.XmlElement;

/////////////////////////////////////////////
/// @class Jiga
/// kworks.itf.vo \n
///   ㄴ Jiga.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 5:02:54 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 '개별공시지가확인서' 서비스 응답 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class Jiga {

	/// 기준년도
	private String baseYear;
	
	/// 기준월
	private String baseMon;
	
	/// 공시지가
	private String pannJiga;
	
	/// 공시일자
	private String pannYmd;
	
	/// 비고
	private String remark;

	@XmlElement(name = "BASE_YEAR")
	public String getBaseYear() {
		return baseYear;
	}

	public void setBaseYear(String baseYear) {
		this.baseYear = baseYear;
	}

	@XmlElement(name = "BASE_MON")
	public String getBaseMon() {
		return baseMon;
	}

	public void setBaseMon(String baseMon) {
		this.baseMon = baseMon;
	}

	@XmlElement(name = "PANN_JIGA")
	public String getPannJiga() {
		return pannJiga;
	}

	public void setPannJiga(String pannJiga) {
		this.pannJiga = pannJiga;
	}

	@XmlElement(name = "PANN_YMD")
	public String getPannYmd() {
		return pannYmd;
	}

	public void setPannYmd(String pannYmd) {
		this.pannYmd = pannYmd;
	}

	@XmlElement(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
