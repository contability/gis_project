package kr.co.gitt.kworks.projects.gc.model;

import kr.co.gitt.kworks.projects.gc.dto.CivilAppealDTO;

/////////////////////////////////////////////
/// @class RdlRserAs
/// kr.co.gitt.kworks.projects.gc.model \n
///   ㄴ RdlRserAs.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 4. 12. 오전 11:52:47 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 도로민원대장 모델 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public class RdlRserAs extends CivilAppealDTO {

	/// 지형지물부호
	private String ftrCde;
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 민원접수번호
	private String rcvNum;
	
	/// 접수일자
	private String rcvYmd;
	
	/// 접수자성명
	private String rcvNam;
	
	/// 도엽번호
	private String shtNum;
	
	/// 민원법정읍/면/동
	private String aplBjd;
	
	/// 민원지번지설명
	private String aplAdr;
	
	/// 민원내용
	private String aplExp;
	
	/// 도로민원구분
	private String aplCde;
	
	/// 민원인성명
	private String apmNam;
	
	/// 민원인주소
	private String apmAdr;
	
	/// 민원인전화번호
	private String apmTel;
	
	/// 처리기한
	private String durYmd;
	
	/// 처리상태
	private String proCde;
	
	/// 처리내용
	private String proExp;
	
	/// 처리완료일자
	private String proYmd;
	
	/// 처리자성명
	private String proNam;
	
	/// 행정읍면동
	private String hjdCde;
	
	/// 자료등록일자
	private String lodYmd;

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

	public String getRcvNum() {
		return rcvNum;
	}

	public void setRcvNum(String rcvNum) {
		this.rcvNum = rcvNum;
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

	public String getShtNum() {
		return shtNum;
	}

	public void setShtNum(String shtNum) {
		this.shtNum = shtNum;
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

	public String getDurYmd() {
		return durYmd;
	}

	public void setDurYmd(String durYmd) {
		this.durYmd = durYmd;
	}

	public String getProCde() {
		return proCde;
	}

	public void setProCde(String proCde) {
		this.proCde = proCde;
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

	public String getHjdCde() {
		return hjdCde;
	}

	public void setHjdCde(String hjdCde) {
		this.hjdCde = hjdCde;
	}

	public String getLodYmd() {
		return lodYmd;
	}

	public void setLodYmd(String lodYmd) {
		this.lodYmd = lodYmd;
	}
	
}
