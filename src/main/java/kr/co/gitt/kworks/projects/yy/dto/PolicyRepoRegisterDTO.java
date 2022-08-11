package kr.co.gitt.kworks.projects.yy.dto;

//Gitt
//create data 2020_07_23 15:02:00
//create user Wongi_Jo
//@이클래스는 정책지도 보고서 등록  DTO입니다.

public class PolicyRepoRegisterDTO {
	
	//정책현황 지형지물부호
	private String ftrCde;
	
	//정책현황 관리번호
	private Long ftrIdn;
	
	//보고서 관리번호
	private Long repoIdn;
	
	//보고서 제목
	private String repoTit;
	
	//보고서 버전
	private Long repoVer;
	
	//보고서 생성자
	private String repoMak;
	
	//보고서 수정자
	private String repoLst;
	
	//보고서 비고
	private String rmkExp;
	
	//보고서 내용
	private String repoDoc;
	
	//보고서 내용
	private String repoPrt;
	
	//보고서 수정일자
	private String editYmd;

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

	public Long getRepoVer() {
		return repoVer;
	}

	public void setRepoVer(Long repoVer) {
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

	public String getRepoDoc() {
		return repoDoc;
	}

	public void setRepoDoc(String repoDoc) {
		this.repoDoc = repoDoc;
	}

	public String getRepoPrt() {
		return repoPrt;
	}

	public void setRepoPrt(String repoPrt) {
		this.repoPrt = repoPrt;
	}

	public String getEditYmd() {
		return editYmd;
	}

	public void setEditYmd(String editYmd) {
		this.editYmd = editYmd;
	}


}
