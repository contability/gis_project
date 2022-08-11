package kr.co.gitt.kworks.model;

public class KwsAtch {
	
	// 부속자료 번호
	private Long atchNo;
	
	// 부속자료 제목
	private String atchSj;
	
	// 부속자료 내용
	private String atchCn;
	
	// 지형지물부호
	private String ftfCde;
	
	// 관리번호
	private Long ftfIdn;
	
	// 파일 번호
	private Long atchFileNo;
	
	// 작성자 아이디
	private String wrterId;
	
	// 최초 작성일
	private String frstRgsde;
	
	// 수정자 아이디
	private String updusrId;
	
	// 최종 수정일
	private String lastUpdde;
	
	// 비고
	private String rmkExp;

	public Long getAtchNo() {
		return atchNo;
	}

	public void setAtchNo(Long atchNo) {
		this.atchNo = atchNo;
	}

	public String getAtchSj() {
		return atchSj;
	}

	public void setAtchSj(String atchSj) {
		this.atchSj = atchSj;
	}

	public String getAtchCn() {
		return atchCn;
	}

	public void setAtchCn(String atchCn) {
		this.atchCn = atchCn;
	}

	public String getFtfCde() {
		return ftfCde;
	}

	public void setFtfCde(String ftfCde) {
		this.ftfCde = ftfCde;
	}

	public Long getFtfIdn() {
		return ftfIdn;
	}

	public void setFtfIdn(Long ftfIdn) {
		this.ftfIdn = ftfIdn;
	}

	public Long getAtchFileNo() {
		return atchFileNo;
	}

	public void setAtchFileNo(Long atchFileNo) {
		this.atchFileNo = atchFileNo;
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

	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}
}
