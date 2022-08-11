package kr.co.gitt.kworks.projects.yy.model;

// Gitt
// create data 2020_07_23 AM 15:21:00
// create user Wongi_Jo
// @이클래스는 정책현황 모델입니다.

public class PlcyRepoMa {
	
	//정책현황 지형지물부호
	private String ftrCde;
	
	//정책현황 관리번호
	private Long ftrIdn;
	
	//보고서 관리번호
	private Long repoIdn;
	
	//보고서 제목
	private String repoTit;
	
	//보고서 버전
	private Integer repoVer;
	
	//보고서 생성자
	private String repoMak;
	
	//보고서 수정자
	private String repoLst;
	
	//보고서 비고
	private String rmkExp;

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

	public Long getRepoIdn() {
		return repoIdn;
	}

	public void setRepoIdn(Long repoIdn) {
		this.repoIdn = repoIdn;
	}

	public String getRepoTit() {
		return repoTit;
	}

	public void setRepoTit(String repoTit) {
		this.repoTit = repoTit;
	}

	public Integer getRepoVer() {
		return repoVer;
	}

	public void setRepoVer(Integer repoVer) {
		this.repoVer = repoVer;
	}

	public String getRepoMak() {
		return repoMak;
	}

	public void setRepoMak(String repoMak) {
		this.repoMak = repoMak;
	}

	public String getRepoLst() {
		return repoLst;
	}

	public void setRepoLst(String repoLst) {
		this.repoLst = repoLst;
	}

	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}
}
