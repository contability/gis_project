package kr.co.gitt.kworks.projects.gc.model;

import kr.co.gitt.kworks.projects.gc.dto.CntrwkRegstrDTO;

/////////////////////////////////////////////
/// @class RdtSubcDt
/// kr.co.gitt.kworks.projects.gc.model \n
///   ㄴ RdtSubcDt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 6. 오후 3:35:30 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 도로 공사대장 하도급내역 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class SwtSubcDt extends CntrwkRegstrDTO {

	/// 지형지물부호
	private String ftrCde; 
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 하도급일련번호
	private Long shtIdn;
	
	/// 상호명
	private String subNam;
	
	/// 성명
	private String psbNam;
	
	/// 주소
	private String subAdr;
	
	/// 전화번호
	private String subTel;

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

	public String getSubNam() {
		return subNam;
	}

	public void setSubNam(String subNam) {
		this.subNam = subNam;
	}

	public String getPsbNam() {
		return psbNam;
	}

	public void setPsbNam(String psbNam) {
		this.psbNam = psbNam;
	}

	public String getSubAdr() {
		return subAdr;
	}

	public void setSubAdr(String subAdr) {
		this.subAdr = subAdr;
	}

	public String getSubTel() {
		return subTel;
	}

	public void setSubTel(String subTel) {
		this.subTel = subTel;
	}
	
}
