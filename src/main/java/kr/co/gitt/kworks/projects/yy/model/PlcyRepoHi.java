package kr.co.gitt.kworks.projects.yy.model;

// Gitt
// create data 2020_06_10 AM 10:47:00
// create user Wongi_Jo
// @이클래스는 정책현황 모델입니다.

public class PlcyRepoHi {
	
	//이력순번
	private Long histNo;
	
	//사용자아이디
	private String userId;
	
	//사용자명
	private String userNm;

	//관리번호
	private Long ftrIdn;

	//보고서 관리번호
	private Long repoIdn;

	//보고서 버전
	private Integer repoVer;
	
	private String repoTit;
	
	//편집일시
	private String editDt;
	
	//편집유형
	private String editType;
	
	//정책명
	private String plcyTit;

	public String getRepoTit() {
		return repoTit;
	}

	public void setRepoTit(String repoTit) {
		this.repoTit = repoTit;
	}

	public Long getHistNo() {
		return histNo;
	}

	public void setHistNo(Long histNo) {
		this.histNo = histNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
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

	public Integer getRepoVer() {
		return repoVer;
	}

	public void setRepoVer(Integer repoVer) {
		this.repoVer = repoVer;
	}

	public String getEditDt() {
		return editDt;
	}

	public void setEditDt(String editDt) {
		this.editDt = editDt;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getPlcyTit() {
		return plcyTit;
	}

	public void setPlcyTit(String plcyTit) {
		this.plcyTit = plcyTit;
	}


}
