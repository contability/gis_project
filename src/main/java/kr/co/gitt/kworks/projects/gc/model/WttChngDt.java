package kr.co.gitt.kworks.projects.gc.model;

import kr.co.gitt.kworks.projects.gc.dto.CntrwkRegstrDTO;

/////////////////////////////////////////////
/// @class WttChngDt
/// kr.co.gitt.kworks.projects.gc.model \n
///   ㄴ WttChngDt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 10. 오후 5:18:33 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 상수 설계 변경 내역 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class WttChngDt extends CntrwkRegstrDTO {

	/// 설계변경내역
	private String ftrCde;
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 변경일련번호
	private Long shtIdn;
	
	/// 변경일자
	private String chgYmd;
	
	/// 증감금액
	private Long incAmt;
	
	/// 증감관급금액
	private Long igvAmt;
	
	/// 변경공사총액
	private Long chgAmt;
	
	/// 비고
	private String chgEtc;
	
	/// 변경사업량
	private String chgDes;
	
	/// 변경관급량
	private String cgvDes;
	
	/// 증감구분
	private String chgCde;

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

	public Long getShtIdn() {
		return shtIdn;
	}

	public void setShtIdn(Long shtIdn) {
		this.shtIdn = shtIdn;
	}

	public String getChgYmd() {
		return chgYmd;
	}

	public void setChgYmd(String chgYmd) {
		this.chgYmd = chgYmd;
	}

	public Long getIncAmt() {
		return incAmt;
	}

	public void setIncAmt(Long incAmt) {
		this.incAmt = incAmt;
	}

	public Long getIgvAmt() {
		return igvAmt;
	}

	public void setIgvAmt(Long igvAmt) {
		this.igvAmt = igvAmt;
	}

	public Long getChgAmt() {
		return chgAmt;
	}

	public void setChgAmt(Long chgAmt) {
		this.chgAmt = chgAmt;
	}

	public String getChgEtc() {
		return chgEtc;
	}

	public void setChgEtc(String chgEtc) {
		this.chgEtc = chgEtc;
	}

	public String getChgDes() {
		return chgDes;
	}

	public void setChgDes(String chgDes) {
		this.chgDes = chgDes;
	}

	public String getCgvDes() {
		return cgvDes;
	}

	public void setCgvDes(String cgvDes) {
		this.cgvDes = cgvDes;
	}

	public String getChgCde() {
		return chgCde;
	}

	public void setChgCde(String chgCde) {
		this.chgCde = chgCde;
	}
	
}
