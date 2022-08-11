package kr.co.gitt.kworks.model;

import java.util.List;

import kr.co.gitt.kworks.projects.yy.model.PlcyStatAs;

/////////////////////////////////////////////
/// @class KwsDept
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsDept.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 20. 오후 6:31:07 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 부서 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsDept {
	
	//부서 코드
	private String deptCode;
	
	//부서 명
	private String deptNm;
	
	//부서 전체 명
	private String deptAllNm;
	
	//부서 전화번호
	private String deptTelno;
	
	//부서 팩스번호
	private String deptFxnum;
	
	//사용 여부
	private String useAt;
	
	private List<KwsDeptSub> kwsDeptSubs;

	public List<KwsDeptSub> getKwsDeptSubs() {
		return kwsDeptSubs;
	}

	public void setKwsDeptSubs(List<KwsDeptSub> kwsDeptSubs) {
		this.kwsDeptSubs = kwsDeptSubs;
	}

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

	public String getDeptAllNm() {
		return deptAllNm;
	}

	public void setDeptAllNm(String deptAllNm) {
		this.deptAllNm = deptAllNm;
	}

	public String getDeptTelno() {
		return deptTelno;
	}

	public void setDeptTelno(String deptTelno) {
		this.deptTelno = deptTelno;
	}

	public String getDeptFxnum() {
		return deptFxnum;
	}

	public void setDeptFxnum(String deptFxnum) {
		this.deptFxnum = deptFxnum;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
}
