package kr.co.gitt.kworks.projects.dh.model;

import kr.co.gitt.kworks.projects.dh.dto.CntrwkRegstrDTO;

/////////////////////////////////////////////
/// @class RdtCostDt
/// kr.co.gitt.kworks.projects.dh.model \n
///   ㄴ RdtCostDt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 6. 오후 3:28:14 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 도로 공사대장 공사비지급내역 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class RdtCostDt extends CntrwkRegstrDTO {

	/// 지형지물부호
	private String ftrCde;
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 지급일련번호
	private Long shtIdn;
	
	/// 지급구분
	private String payCde;
	
	/// 지급일자
	private String payYmd;
	
	/// 지급금액
	private Long payAmt;
	
	/// 내용
	private String payCon;
	
	/// 기타
	private String payEtc;
	
	/// 잔액
	private Long jamAmt;

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

	public String getPayCde() {
		return payCde;
	}

	public void setPayCde(String payCde) {
		this.payCde = payCde;
	}

	public String getPayYmd() {
		return payYmd;
	}

	public void setPayYmd(String payYmd) {
		this.payYmd = payYmd;
	}

	public Long getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(Long payAmt) {
		this.payAmt = payAmt;
	}

	public String getPayCon() {
		return payCon;
	}

	public void setPayCon(String payCon) {
		this.payCon = payCon;
	}

	public String getPayEtc() {
		return payEtc;
	}

	public void setPayEtc(String payEtc) {
		this.payEtc = payEtc;
	}

	public Long getJamAmt() {
		return jamAmt;
	}

	public void setJamAmt(Long jamAmt) {
		this.jamAmt = jamAmt;
	}
	
}
