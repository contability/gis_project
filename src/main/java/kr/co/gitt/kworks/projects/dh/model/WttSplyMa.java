package kr.co.gitt.kworks.projects.dh.model;

import kr.co.gitt.kworks.projects.dh.dto.CntrwkRegstrDTO;

/////////////////////////////////////////////
/// @class WttSplyMa
/// kr.co.gitt.kworks.projects.dh.model \n
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
	
	/// 준공일자
	private String fnsYmd;
	
	/// 관급자재비
	private Long gvrAmt;
	
	/// 설계수수료
	private Long dfeAmt;
	
	/// 사급자재비
	private Long prvAmt;
	
	/// 시설분담금
	private Long divAmt;
	
	/// 부가가치세
	private Long taxAmt;
	
	/// 공사비총액
	private Long totAmt;
	
	/// 도로복구비
	private Long rorAmt;
	
	/// 수납일자
	private String rcpYmd;
	
	/// 자재검사수수료
	private Long gfeAmt;
	
	/// 시공자명
	private String oprNam;
	
	/// 준공검사수수료
	private Long ffeAmt;
	
	/// 감독자성명
	private String svsNam;
	
	/// 준공검사자성명
	private String fnsNam;
	
	/// 민원접수번호
	private String rcvNum;
	
	/// 기타금액
	private Long etcAmt;
	
	/// 순공사비
	private Long dpcAmt;
	
	/// 신청인
	private String apmNam;
	
	/// 신청지
	private String apmLoc;
	
	/// 주소
	private String apmAdr;
	
	/// 전화번호
	private String apmTel;
	
	/// 급수전구경
	private Long metDip;

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

	public Long getDfeAmt() {
		return dfeAmt;
	}

	public void setDfeAmt(Long dfeAmt) {
		this.dfeAmt = dfeAmt;
	}

	public Long getPrvAmt() {
		return prvAmt;
	}

	public void setPrvAmt(Long prvAmt) {
		this.prvAmt = prvAmt;
	}

	public Long getDivAmt() {
		return divAmt;
	}

	public void setDivAmt(Long divAmt) {
		this.divAmt = divAmt;
	}

	public Long getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(Long taxAmt) {
		this.taxAmt = taxAmt;
	}

	public Long getTotAmt() {
		return totAmt;
	}

	public void setTotAmt(Long totAmt) {
		this.totAmt = totAmt;
	}

	public Long getRorAmt() {
		return rorAmt;
	}

	public void setRorAmt(Long rorAmt) {
		this.rorAmt = rorAmt;
	}

	public String getRcpYmd() {
		return rcpYmd;
	}

	public void setRcpYmd(String rcpYmd) {
		this.rcpYmd = rcpYmd;
	}

	public Long getGfeAmt() {
		return gfeAmt;
	}

	public void setGfeAmt(Long gfeAmt) {
		this.gfeAmt = gfeAmt;
	}

	public String getOprNam() {
		return oprNam;
	}

	public void setOprNam(String oprNam) {
		this.oprNam = oprNam;
	}

	public Long getFfeAmt() {
		return ffeAmt;
	}

	public void setFfeAmt(Long ffeAmt) {
		this.ffeAmt = ffeAmt;
	}

	public String getSvsNam() {
		return svsNam;
	}

	public void setSvsNam(String svsNam) {
		this.svsNam = svsNam;
	}

	public String getFnsNam() {
		return fnsNam;
	}

	public void setFnsNam(String fnsNam) {
		this.fnsNam = fnsNam;
	}

	public String getRcvNum() {
		return rcvNum;
	}

	public void setRcvNum(String rcvNum) {
		this.rcvNum = rcvNum;
	}

	public Long getEtcAmt() {
		return etcAmt;
	}

	public void setEtcAmt(Long etcAmt) {
		this.etcAmt = etcAmt;
	}

	public Long getDpcAmt() {
		return dpcAmt;
	}

	public void setDpcAmt(Long dpcAmt) {
		this.dpcAmt = dpcAmt;
	}

	public String getApmNam() {
		return apmNam;
	}

	public void setApmNam(String apmNam) {
		this.apmNam = apmNam;
	}

	public String getApmLoc() {
		return apmLoc;
	}

	public void setApmLoc(String apmLoc) {
		this.apmLoc = apmLoc;
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

	public Long getMetDip() {
		return metDip;
	}

	public void setMetDip(Long metDip) {
		this.metDip = metDip;
	}
	
}
