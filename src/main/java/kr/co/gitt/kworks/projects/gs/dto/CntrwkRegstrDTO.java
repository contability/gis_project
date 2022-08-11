package kr.co.gitt.kworks.projects.gs.dto;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/////////////////////////////////////////////
/// @class CntrwkRegstrDTO
/// kr.co.gitt.kworks.projects.yy.dto \n
///   ㄴ CntrwkRegstrDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 2. 오후 6:26:07 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 공사대장 DTO 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class CntrwkRegstrDTO extends SearchDTO {
	
	/// 계약일 min
	private String cttYmdMin;
	
	/// 계약일 max
	private String cttYmdMax;
	
	/// 착공일 min
	private String begYmdMin;
	
	/// 착공일 max
	private String begYmdMax;
	
	/// 준공일 min
	private String rfnYmdMin;
	
	/// 준공일 max
	private String rfnYmdMax;
	
	/// 계약금액 min
	private String tctAmtMin;
	
	/// 계약금액 max
	private String tctAmtMax;
	
	/// 대장번호
	private Long ftrIdn;
	
	/// 내역번호
	private Long shtIdn;
	
	/// 공사비총액 min
	private String totAmtMin;
	
	/// 공사비총액 max
	private String totAmtMax;

	public String getCttYmdMin() {
		return cttYmdMin;
	}
	
	public void setCttYmdMin(String cttYmdMin) {
		this.cttYmdMin = cttYmdMin;
	}

	public String getCttYmdMax() {
		return cttYmdMax;
	}

	public void setCttYmdMax(String cttYmdMax) {
		this.cttYmdMax = cttYmdMax;
	}

	public String getBegYmdMin() {
		return begYmdMin;
	}

	public void setBegYmdMin(String begYmdMin) {
		this.begYmdMin = begYmdMin;
	}

	public String getBegYmdMax() {
		return begYmdMax;
	}

	public void setBegYmdMax(String begYmdMax) {
		this.begYmdMax = begYmdMax;
	}

	public String getRfnYmdMin() {
		return rfnYmdMin;
	}

	public void setRfnYmdMin(String rfnYmdMin) {
		this.rfnYmdMin = rfnYmdMin;
	}

	public String getRfnYmdMax() {
		return rfnYmdMax;
	}

	public void setRfnYmdMax(String rfnYmdMax) {
		this.rfnYmdMax = rfnYmdMax;
	}

	public String getTctAmtMin() {
		return tctAmtMin;
	}

	public void setTctAmtMin(String tctAmtMin) {
		this.tctAmtMin = tctAmtMin;
	}

	public String getTctAmtMax() {
		return tctAmtMax;
	}

	public void setTctAmtMax(String tctAmtMax) {
		this.tctAmtMax = tctAmtMax;
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

	public String getTotAmtMin() {
		return totAmtMin;
	}

	public void setTotAmtMin(String totAmtMin) {
		this.totAmtMin = totAmtMin;
	}

	public String getTotAmtMax() {
		return totAmtMax;
	}

	public void setTotAmtMax(String totAmtMax) {
		this.totAmtMax = totAmtMax;
	}
	
}
