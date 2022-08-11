package kr.co.gitt.kworks.contact.kras.model;

import javax.xml.bind.annotation.XmlElement;

/////////////////////////////////////////////
/// @class LandUsePlanRestrict
/// kworks.itf.vo \n
///   ㄴ LandUsePlanRestrict.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 5. 오후 3:03:02 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 행위제한내역 입니다.
///   -# 
/////////////////////////////////////////////
public class LandUsePlanRestrict {

	/// 행위 제한 내역
	private String useRestrict;
	
	/// 용도지역지구 코드
	private String ucode;
	
	/// 행위 제한 내용
	private String lawContents;
	
	/// 행위 제한 레벨
	private String lawLevel;
	
	/// 행위 제한 코드
	private String lawFullCd;

	@XmlElement(name = "USE_RESTRICT")
	public String getUseRestrict() {
		return useRestrict;
	}

	public void setUseRestrict(String useRestrict) {
		this.useRestrict = useRestrict;
	}

	@XmlElement(name = "UCODE")
	public String getUcode() {
		return ucode;
	}

	public void setUcode(String ucode) {
		this.ucode = ucode;
	}

	@XmlElement(name = "LAW_CONTENTS")
	public String getLawContents() {
		return lawContents;
	}

	public void setLawContents(String lawContents) {
		this.lawContents = lawContents;
	}

	@XmlElement(name = "LAW_LEVEL")
	public String getLawLevel() {
		return lawLevel;
	}

	public void setLawLevel(String lawLevel) {
		this.lawLevel = lawLevel;
	}

	@XmlElement(name = "LAW_FULL_CD")
	public String getLawFullCd() {
		return lawFullCd;
	}

	public void setLawFullCd(String lawFullCd) {
		this.lawFullCd = lawFullCd;
	}
	
}
