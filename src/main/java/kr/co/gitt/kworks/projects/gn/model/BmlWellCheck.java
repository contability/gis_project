package kr.co.gitt.kworks.projects.gn.model;

import java.sql.Date;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/////////////////////////////////////////////
/// @class BmlWellCheck
/// kr.co.gitt.kworks.projects.gn.model \n
///   ㄴ BmlWellCheck.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 2. 27. 오전 11:23:48 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 강릉시 지하수관정 점검정비이력 모델 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public class BmlWellCheck extends SearchDTO {
	
	/// 점검정비이력
	private Long chkIdn;
	
	/// 지형지물부호
	private String ftrCde;
	
	/// 허가신고번호
	private String permNtNo;
	
	/// 구분
	private String chkCde;
	
	/// 날짜
	private String chkYmd;
	
	/// 소속성명
	private String chkUsr;
	
	/// 적요
	private String chkDes;
	
	/// 등록일자
	private Date regYmd;

	public Long getChkIdn() {
		return chkIdn;
	}

	public void setChkIdn(Long chkIdn) {
		this.chkIdn = chkIdn;
	}

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public String getPermNtNo() {
		return permNtNo;
	}

	public void setPermNtNo(String permNtNo) {
		this.permNtNo = permNtNo;
	}

	public String getChkCde() {
		return chkCde;
	}

	public void setChkCde(String chkCde) {
		this.chkCde = chkCde;
	}

	public String getChkYmd() {
		return chkYmd;
	}

	public void setChkYmd(String chkYmd) {
		this.chkYmd = chkYmd;
	}

	public String getChkUsr() {
		return chkUsr;
	}

	public void setChkUsr(String chkUsr) {
		this.chkUsr = chkUsr;
	}

	public String getChkDes() {
		return chkDes;
	}

	public void setChkDes(String chkDes) {
		this.chkDes = chkDes;
	}

	public Date getRegYmd() {
		return regYmd;
	}

	public void setRegYmd(Date regYmd) {
		this.regYmd = regYmd;
	}
	
}
