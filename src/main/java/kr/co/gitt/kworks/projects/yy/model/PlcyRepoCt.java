package kr.co.gitt.kworks.projects.yy.model;

// Gitt
// create data 2020_07_23 AM 15:21:00
// create user Wongi_Jo
// @이클래스는 정책현황 모델입니다.

public class PlcyRepoCt {
	
	//보고서 관리번호
	private Long repoIdn;

	//보고서 버전
	private Integer repoVer;
	
	//보고서 내용
	private String repoPrt;
	
	//보고서 내용
	private String repoDoc;
	
	//보고서 수정자
	private String repoLst;
	
	//보고서 수정일자
	private String editYmd;

	public Long getRepoIdn() {
		return repoIdn;
	}

	public void setRepoIdn(Long repoIdn) {
		this.repoIdn = repoIdn;
	}

	public Integer getRepoVer() {
		return repoVer;
	}

	public void setRepoVer(Integer repoVer) {
		this.repoVer = repoVer;
	}

	public String getRepoPrt() {
		return repoPrt;
	}

	public void setRepoPrt(String repoPrt) {
		this.repoPrt = repoPrt;
	}

	public String getRepoDoc() {
		return repoDoc;
	}

	public void setRepoDoc(String repoDoc) {
		this.repoDoc = repoDoc;
	}

	public String getRepoLst() {
		return repoLst;
	}

	public void setRepoLst(String repoLst) {
		this.repoLst = repoLst;
	}

	public String getEditYmd() {
		return editYmd;
	}

	public void setEditYmd(String editYmd) {
		this.editYmd = editYmd;
	}

}
