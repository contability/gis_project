package kr.co.gitt.kworks.projects.gn.model;

//보호구역 개선사업이력
public class RdtPtimHt {
	
	//관리번호
	private Long impIdn;

	//지형지물부호
	private String ftrCde;

	//보호구역 지형지물부호
	private String ftfCde;

	//보호구역 관리번호
	private Long ftfIdn;

	//사업명
	private String prjNam;

	//계약일
	private String cttYmd;

	//착수일
	private String begYmd;

	//준공일
	private String rfnYmd;

	//공사개요
	private String cntDes;

	//작성자 아이디
	private String wrterId;

	//최초 작성일
	private String frstRgsde;

	//수정자 아이디
	private String updusrId;

	//최종 수정일
	private String lastUpdde;

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public String getFtfCde() {
		return ftfCde;
	}

	public void setFtfCde(String ftfCde) {
		this.ftfCde = ftfCde;
	}

	public String getPrjNam() {
		return prjNam;
	}

	public void setPrjNam(String prjNam) {
		this.prjNam = prjNam;
	}

	public String getCttYmd() {
		return cttYmd;
	}

	public void setCttYmd(String cttYmd) {
		this.cttYmd = cttYmd;
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

	public String getCntDes() {
		return cntDes;
	}

	public void setCntDes(String cntDes) {
		this.cntDes = cntDes;
	}

	public String getWrterId() {
		return wrterId;
	}

	public void setWrterId(String wrterId) {
		this.wrterId = wrterId;
	}

	public String getFrstRgsde() {
		return frstRgsde;
	}

	public void setFrstRgsde(String frstRgsde) {
		this.frstRgsde = frstRgsde;
	}

	public String getUpdusrId() {
		return updusrId;
	}

	public void setUpdusrId(String updusrId) {
		this.updusrId = updusrId;
	}

	public String getLastUpdde() {
		return lastUpdde;
	}

	public void setLastUpdde(String lastUpdde) {
		this.lastUpdde = lastUpdde;
	}

	public Long getImpIdn() {
		return impIdn;
	}

	public void setImpIdn(Long impIdn) {
		this.impIdn = impIdn;
	}

	public Long getFtfIdn() {
		return ftfIdn;
	}

	public void setFtfIdn(Long ftfIdn) {
		this.ftfIdn = ftfIdn;
	}
}
