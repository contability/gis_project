package kr.co.gitt.kworks.projects.yy.model;

// Gitt
// create data 2020_06_10 AM 10:47:00
// create user Wongi_Jo
// @이클래스는 정책현황 모델입니다.

public class PlcyEditHi {
	
	//이력순번
	private Long histNo;
	
	//사용자아이디
	private String userId;
	
	/**
	 *  사용자 명
	 */
	private String userNm;

	//관리번호
	private Long ftrIdn;
	
	//편집일시
	private String editDate;
	
	//편집데이터 유형
	private String editData;
	
	//편집유형
	private String editType;
	
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

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}

	public String getEditData() {
		return editData;
	}

	public void setEditData(String editData) {
		this.editData = editData;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}
	
	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	
}
