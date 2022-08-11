package kr.co.gitt.kworks.projects.yg.model;

import kr.co.gitt.kworks.projects.yg.dto.CntrwkRegstrDTO;

/////////////////////////////////////////////
/// @class WttSplyMa
/// kr.co.gitt.kworks.projects.yg.model \n
///   ㄴ WttSplyMa.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 13. 오후 6:28:01 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 급수공사대장 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class WttSplyMa extends CntrwkRegstrDTO {

	/// 지형지물부호
	private String ftrCde;
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 착수일자
	private String begYmd;
	
	/// 상수민원처리대장 지형지물부호
	private String ftfCde;
	
	/// 준공일자
	private String fnsYmd;
	
	/// 관급자재
	private Long gvrAmt;
	
	/// 사급자재
	private Long prvAmt;
	
	/// 설계수수료
	private Long dfeAmt;
	
	/// 부가가치세
	private Long taxAmt;
	
	/// 시설분담금
	private Long divAmt;
	
	/// 도로복구비
	private Long rorAmt;
	
	/// 공사비총액
	private Long totAmt;
	
	/// 수납일자
	private String rcpYmd;
	
	/// 시공자명
	private String oprNam;
	
	/// 자재검사수수료
	private Long gfeAmt;
	
	/// 감독자성명
	private String svsNam;
	
	/// 준공검사수수료
	private Long ffeAmt;
	
	/// 준공검사자성명
	private String fnsNam;
	
	/// 기타금액
	private Long etcAmt;
	
	/// 민원접수번호
	private Long ftfIdn;

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

	public String getBegYmd() {
		return begYmd;
	}

	public void setBegYmd(String begYmd) {
		this.begYmd = begYmd;
	}

	public String getFtfCde() {
		return ftfCde;
	}

	public void setFtfCde(String ftfCde) {
		this.ftfCde = ftfCde;
	}

	public String getFnsYmd() {
		return fnsYmd;
	}

	public void setFnsYmd(String fnsYmd) {
		this.fnsYmd = fnsYmd;
	}

	public Long getGvrAmt() {
		return gvrAmt;
	}

	public void setGvrAmt(Long gvrAmt) {
		this.gvrAmt = gvrAmt;
	}

	public Long getPrvAmt() {
		return prvAmt;
	}

	public void setPrvAmt(Long prvAmt) {
		this.prvAmt = prvAmt;
	}

	public Long getDfeAmt() {
		return dfeAmt;
	}

	public void setDfeAmt(Long dfeAmt) {
		this.dfeAmt = dfeAmt;
	}

	public Long getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(Long taxAmt) {
		this.taxAmt = taxAmt;
	}

	public Long getDivAmt() {
		return divAmt;
	}

	public void setDivAmt(Long divAmt) {
		this.divAmt = divAmt;
	}

	public Long getRorAmt() {
		return rorAmt;
	}

	public void setRorAmt(Long rorAmt) {
		this.rorAmt = rorAmt;
	}

	public Long getTotAmt() {
		return totAmt;
	}

	public void setTotAmt(Long totAmt) {
		this.totAmt = totAmt;
	}

	public String getRcpYmd() {
		return rcpYmd;
	}

	public void setRcpYmd(String rcpYmd) {
		this.rcpYmd = rcpYmd;
	}

	public String getOprNam() {
		return oprNam;
	}

	public void setOprNam(String oprNam) {
		this.oprNam = oprNam;
	}

	public Long getGfeAmt() {
		return gfeAmt;
	}

	public void setGfeAmt(Long gfeAmt) {
		this.gfeAmt = gfeAmt;
	}

	public String getSvsNam() {
		return svsNam;
	}

	public void setSvsNam(String svsNam) {
		this.svsNam = svsNam;
	}

	public Long getFfeAmt() {
		return ffeAmt;
	}

	public void setFfeAmt(Long ffeAmt) {
		this.ffeAmt = ffeAmt;
	}

	public String getFnsNam() {
		return fnsNam;
	}

	public void setFnsNam(String fnsNam) {
		this.fnsNam = fnsNam;
	}

	public Long getEtcAmt() {
		return etcAmt;
	}

	public void setEtcAmt(Long etcAmt) {
		this.etcAmt = etcAmt;
	}

	public Long getFtfIdn() {
		return ftfIdn;
	}

	public void setFtfIdn(Long ftfIdn) {
		this.ftfIdn = ftfIdn;
	}

}
