package kr.co.gitt.kworks.projects.gn.model;

import kr.co.gitt.kworks.projects.gn.dto.CntrwkRegstrDTO;

/////////////////////////////////////////////
/// @class LdConsMa
/// kr.co.gitt.kworks.projects.gn.model \n
///   ㄴ LdConsMa.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2018. 3. 21. 오후 6:20:20 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 강릉시 토지중심공사대장 모델 클레스 입니다.
///   -# 
/////////////////////////////////////////////
public class LdConsMa extends CntrwkRegstrDTO {

	//공사 번호
	private Long cntIdn;
	
	//공사 명
	private String cntNam;
	
	//도급자 명
	private String gcnNam;
	
	//대표자 명
	private String pocNam;
	
	//공사 부서
	private String cntDept;

	//계약 금액
	private Long totAmt;

	//계약 일
	private String cttYmd;
	
	//준공 기한
	private String fnsYms;
	
	//착공 일
	private String begYmd;
	
	//준공 일
	private String rfnYmd;

	//준공검사 일
	private String fchYmd;
	
	//비고
	private String remark;
	

	public Long getCntIdn() {
		return cntIdn;
	}

	public void setCntIdn(Long cntIdn) {
		this.cntIdn = cntIdn;
	}

	public String getCntNam() {
		return cntNam;
	}

	public void setCntNam(String cntNam) {
		this.cntNam = cntNam;
	}

	public String getGcnNam() {
		return gcnNam;
	}

	public void setGcnNam(String gcnNam) {
		this.gcnNam = gcnNam;
	}

	public String getPocNam() {
		return pocNam;
	}

	public void setPocNam(String pocNam) {
		this.pocNam = pocNam;
	}

	public String getCntDept() {
		return cntDept;
	}

	public void setCntDept(String cntDept) {
		this.cntDept = cntDept;
	}

	public Long getTotAmt() {
		return totAmt;
	}

	public void setTotAmt(Long totAmt) {
		this.totAmt = totAmt;
	}

	public String getCttYmd() {
		return cttYmd;
	}

	public void setCttYmd(String cttYmd) {
		this.cttYmd = cttYmd;
	}

	public String getFnsYms() {
		return fnsYms;
	}

	public void setFnsYms(String fnsYms) {
		this.fnsYms = fnsYms;
	}

	public String getBegYmd() {
		return begYmd;
	}

	public void setBegYmd(String begYmd) {
		this.begYmd = begYmd;
	}

	public String getRfnYmd() {
		return rfnYmd;
	}

	public void setRfnYmd(String rfnYmd) {
		this.rfnYmd = rfnYmd;
	}

	public String getFchYmd() {
		return fchYmd;
	}

	public void setFchYmd(String fchYmd) {
		this.fchYmd = fchYmd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
