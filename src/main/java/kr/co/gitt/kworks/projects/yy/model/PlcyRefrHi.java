package kr.co.gitt.kworks.projects.yy.model;

// Gitt
// create data 2020_06_10 AM 10:47:00
// create user Wongi_Jo
// @이클래스는 정책현황 모델입니다.

public class PlcyRefrHi {
	
	//이력순번
	private Long histNo;
	
	//사용자아이디
	private String userId;
	
	//사용자명
	private String userNm;
	
	//관리번호
	private Long ftrIdn;
	
	//편집일시
	private String editDt;
	
	//편집 유형
	private String editType;
	
	//자료코드
	private String docCde;
	
	//파일번호
	private Long fileNo;
	
	//정책명
	private String plcyTit;

	public String getPlcyTit() {
		return plcyTit;
	}

	public void setPlcyTit(String plcyTit) {
		this.plcyTit = plcyTit;
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

	public String getDocCde() {
		return docCde;
	}

	public void setDocCde(String docCde) {
		this.docCde = docCde;
	}

	public Long getFileNo() {
		return fileNo;
	}

	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
	}

	
}
