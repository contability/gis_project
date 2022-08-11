package kr.co.gitt.kworks.projects.yg.model;

/////////////////////////////////////////////
/// @class LdConsMa
/// kr.co.gitt.kworks.projects.yg.model \n
///   ㄴ LdConsMa.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2019. 4. 18. 오후 4:21:19 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 양구 토지중심공사대장 모델 클레스 입니다.
///   -# 
/////////////////////////////////////////////
public class LdlConsPs {
	
	//ID
	private Long objectid;
	
	//공사 번호
	private Long cntIdn;
	
	//공사 명
	private String cntNam;
	
	//공사종류
	private String cntKnd;
	
	//도급자 명
	private String gcnNam;
	
	//대표자 명
	private String pocNam;
	
	//도급자 주소
	private String gcnAdr;
	
	//공사 부서
	private String cntDept;
	
	//감독자
	private String svsNam;
	
	//준공검사자
	private String fchNam;

	//계약 금액
	private Long totAmt;

	//계약 일
	private String cttYmd;
	
	//준공 기한
	private String fnsYmd;
	
	//착공 일
	private String begYmd;
	
	//준공 일
	private String rfnYmd;

	//준공검사 일
	private String fchYmd;
	
	//비고
	private String remark;
	
	/**
	 *  190418 양구테이블설계 수정
	 *  bjdCde, sanCde, facNum, fadNum, pnu, locDes 컬럼추가
	 */
	//법정읍/면/동
	private String bjdCde;
	
	//산번지여부
	private String sanCde;

	//본번
	private String facNum;
	
	//부번
	private String fadNum;
	
	//지번코드
	private String pnu;

	//지번상세
	private String locDes;
	
	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}
	
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

	public String getCntKnd() {
		return cntKnd;
	}

	public void setCntKnd(String cntKnd) {
		this.cntKnd = cntKnd;
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

	public String getGcnAdr() {
		return gcnAdr;
	}

	public void setGcnAdr(String gcnAdr) {
		this.gcnAdr = gcnAdr;
	}

	public String getCntDept() {
		return cntDept;
	}

	public void setCntDept(String cntDept) {
		this.cntDept = cntDept;
	}

	public String getSvsNam() {
		return svsNam;
	}

	public void setSvsNam(String svsNam) {
		this.svsNam = svsNam;
	}

	public String getFchNam() {
		return fchNam;
	}

	public void setFchNam(String fchNam) {
		this.fchNam = fchNam;
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

	public String getFnsYmd() {
		return fnsYmd;
	}

	public void setFnsYmd(String fnsYmd) {
		this.fnsYmd = fnsYmd;
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

	public String getBjdCde() {
		return bjdCde;
	}

	public void setBjdCde(String bjdCde) {
		this.bjdCde = bjdCde;
	}

	public String getSanCde() {
		return sanCde;
	}

	public void setSanCde(String sanCde) {
		this.sanCde = sanCde;
	}

	public String getFacNum() {
		return facNum;
	}

	public void setFacNum(String facNum) {
		this.facNum = facNum;
	}

	public String getFadNum() {
		return fadNum;
	}

	public void setFadNum(String fadNum) {
		this.fadNum = fadNum;
	}

	public String getPnu() {
		return pnu;
	}

	public void setPnu(String pnu) {
		this.pnu = pnu;
	}

	public String getLocDes() {
		return locDes;
	}

	public void setLocDes(String locDes) {
		this.locDes = locDes;
	}
}
