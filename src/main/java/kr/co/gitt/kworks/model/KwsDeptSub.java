package kr.co.gitt.kworks.model;

public class KwsDeptSub {
	
	//부서 코드
	private String deptCode;
		
	//부서 명
	private String deptNm;
	
	//부서 명
	private String deptSubNm;
	
	//부서 전체 명
	private String deptSubCode;
	
	//사용 여부
	private String useAt;
	
	

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public String getDeptSubNm() {
		return deptSubNm;
	}

	public void setDeptSubNm(String deptSubNm) {
		this.deptSubNm = deptSubNm;
	}

	public String getDeptSubCode() {
		return deptSubCode;
	}

	public void setDeptSubCode(String deptSubCode) {
		this.deptSubCode = deptSubCode;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

}
