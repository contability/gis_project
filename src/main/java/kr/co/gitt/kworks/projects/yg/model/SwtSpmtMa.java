package kr.co.gitt.kworks.projects.yg.model;

import kr.co.gitt.kworks.projects.yg.dto.CivilAppealDTO;

/////////////////////////////////////////////
/// @class SwtSpmtMa
/// kr.co.gitt.kworks.projects.yg.model \n
///   ㄴ SwtSpmtMa.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 21. 오후 3:54:51 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 배수설비인허가대장 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class SwtSpmtMa extends CivilAppealDTO {

	///지형지물부호
	private String ftrCde;
	
	///관리번호
	private Long ftrIdn;
	
	///하수연결지형지물부호
	private String ftfCde;
	
	///인허가일자
	private String pmtYmd;
	
	///민원지 법정읍/면/동
	private String aplBjd;
	
	///민원지 산구분
	private String aplSan;
	
	///민원지 번반
	private String aplBon;
	
	///민원지 본번
	private String aplBub;
	
	///민원인 성명
	private String aplNam;
	
	///민원인 주소
	private String aplAdr;
	
	///민원인 전화번호
	private String aplTel;
	
	///배수설비준공일자
	private String cntYmd;
	
	///하수배출량
	private double sdrVol;
	
	///건축용도
	private String bldUse;
	
	///건축연면적
	private double bldAra;
	
	///하수연결관관리번호
	private String ftfIdn;
	
	///하수처리구분
	private String brcCde;
	
	///처리자성명
	private String proNam;
	
	///인허가 구분
	private String pmsCde;
	
	///건축구조
	private String bldStr;

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

	public String getFtfCde() {
		return ftfCde;
	}

	public void setFtfCde(String ftfCde) {
		this.ftfCde = ftfCde;
	}

	public String getPmtYmd() {
		return pmtYmd;
	}

	public void setPmtYmd(String pmtYmd) {
		this.pmtYmd = pmtYmd;
	}

	public String getAplBjd() {
		return aplBjd;
	}

	public void setAplBjd(String aplBjd) {
		this.aplBjd = aplBjd;
	}

	public String getAplSan() {
		return aplSan;
	}

	public void setAplSan(String aplSan) {
		this.aplSan = aplSan;
	}

	public String getAplBon() {
		return aplBon;
	}

	public void setAplBon(String aplBon) {
		this.aplBon = aplBon;
	}

	public String getAplBub() {
		return aplBub;
	}

	public void setAplBub(String aplBub) {
		this.aplBub = aplBub;
	}

	public String getAplNam() {
		return aplNam;
	}

	public void setAplNam(String aplNam) {
		this.aplNam = aplNam;
	}

	public String getAplAdr() {
		return aplAdr;
	}

	public void setAplAdr(String aplAdr) {
		this.aplAdr = aplAdr;
	}

	public String getAplTel() {
		return aplTel;
	}

	public void setAplTel(String aplTel) {
		this.aplTel = aplTel;
	}

	public String getCntYmd() {
		return cntYmd;
	}

	public void setCntYmd(String cntYmd) {
		this.cntYmd = cntYmd;
	}

	public double getSdrVol() {
		return sdrVol;
	}

	public void setSdrVol(double sdrVol) {
		this.sdrVol = sdrVol;
	}

	public String getBldUse() {
		return bldUse;
	}

	public void setBldUse(String bldUse) {
		this.bldUse = bldUse;
	}

	public double getBldAra() {
		return bldAra;
	}

	public void setBldAra(double bldAra) {
		this.bldAra = bldAra;
	}

	public String getFtfIdn() {
		return ftfIdn;
	}

	public void setFtfIdn(String ftfIdn) {
		this.ftfIdn = ftfIdn;
	}

	public String getBrcCde() {
		return brcCde;
	}

	public void setBrcCde(String brcCde) {
		this.brcCde = brcCde;
	}

	public String getProNam() {
		return proNam;
	}

	public void setProNam(String proNam) {
		this.proNam = proNam;
	}

	public String getPmsCde() {
		return pmsCde;
	}

	public void setPmsCde(String pmsCde) {
		this.pmsCde = pmsCde;
	}

	public String getBldStr() {
		return bldStr;
	}

	public void setBldStr(String bldStr) {
		this.bldStr = bldStr;
	}

}
