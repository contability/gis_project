package kr.co.gitt.kworks.model;

import java.sql.Date;

public class KwsPledge {
	
	//유저 아이디
	private String userId;
	
	//서약 날짜
	private Date pldYmd;
	
	//부서 코드
	private String deptCode;
	
	//부서 이름
	private String deptNm;
	
	//유저 이름
	private String userNm;
	
	//직급
	private String pldClf;
	
	//직위
	private String pldOfp;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getPldYmd() {
		return pldYmd;
	}

	public void setPldYmd(Date pldYmd) {
		this.pldYmd = pldYmd;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getPldClf() {
		return pldClf;
	}

	public void setPldClf(String pldClf) {
		this.pldClf = pldClf;
	}

	public String getPldOfp() {
		return pldOfp;
	}

	public void setPldOfp(String pldOfp) {
		this.pldOfp = pldOfp;
	}
}
