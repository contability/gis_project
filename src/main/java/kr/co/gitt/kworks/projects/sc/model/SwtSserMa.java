package kr.co.gitt.kworks.projects.sc.model;

import kr.co.gitt.kworks.projects.sc.dto.CivilAppealDTO;

/////////////////////////////////////////////
/// @class SwtSserMa
/// kr.co.gitt.kworks.projects.sc.model \n
///   ㄴ SwtSserMa.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 21. 오후 3:50:53 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 하수민원접수처리대장
///   -# 
/////////////////////////////////////////////
public class SwtSserMa extends CivilAppealDTO {
	
	///지형지물부호
	private String ftrCde;
	
	///관리번호
	private Long ftrIdn;
	
	///접수일자
	private String rcvYmd;
	
	///접수자성명
	private String rcvNam;
	
	///민원지_법정읍/면/동
	private String aplBjd;
	
	///민원지번지설명
	private String aplAdr;
	
	///민원내용
	private String aplExp;
	
	///하수민원구분
	private String aplCde;
	
	///민원인성명
	private String apmNam;
	
	///민원인주소
	private String apmAdr;
	
	///민원인전화번호
	private String apmTel;
	
	///처리상태
	private String proCde;
	
	///처리기한
	private String durYmd;
	
	///처리내용
	private String proExp;
	
	///처리완료일자
	private String proYmd;
	
	///처리자성명
	private String proNam;

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public String getRcvYmd() {
		return rcvYmd;
	}

	public void setRcvYmd(String rcvYmd) {
		this.rcvYmd = rcvYmd;
	}

	public String getRcvNam() {
		return rcvNam;
	}

	public void setRcvNam(String rcvNam) {
		this.rcvNam = rcvNam;
	}

	public String getAplBjd() {
		return aplBjd;
	}

	public void setAplBjd(String aplBjd) {
		this.aplBjd = aplBjd;
	}

	public String getAplAdr() {
		return aplAdr;
	}

	public void setAplAdr(String aplAdr) {
		this.aplAdr = aplAdr;
	}

	public String getAplExp() {
		return aplExp;
	}

	public void setAplExp(String aplExp) {
		this.aplExp = aplExp;
	}

	public String getAplCde() {
		return aplCde;
	}

	public void setAplCde(String aplCde) {
		this.aplCde = aplCde;
	}

	public String getApmNam() {
		return apmNam;
	}

	public void setApmNam(String apmNam) {
		this.apmNam = apmNam;
	}

	public String getApmAdr() {
		return apmAdr;
	}

	public void setApmAdr(String apmAdr) {
		this.apmAdr = apmAdr;
	}

	public String getApmTel() {
		return apmTel;
	}

	public void setApmTel(String apmTel) {
		this.apmTel = apmTel;
	}

	public String getProCde() {
		return proCde;
	}

	public void setProCde(String proCde) {
		this.proCde = proCde;
	}

	public String getDurYmd() {
		return durYmd;
	}

	public void setDurYmd(String durYmd) {
		this.durYmd = durYmd;
	}

	public String getProExp() {
		return proExp;
	}

	public void setProExp(String proExp) {
		this.proExp = proExp;
	}

	public String getProYmd() {
		return proYmd;
	}

	public void setProYmd(String proYmd) {
		this.proYmd = proYmd;
	}

	public String getProNam() {
		return proNam;
	}

	public void setProNam(String proNam) {
		this.proNam = proNam;
	}
}
