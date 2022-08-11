package kr.co.gitt.kworks.projects.gn.model;

/////////////////////////////////////////////
/// @class SwtSpmtAtchmnflDt
/// kr.co.gitt.kworks.projects.gn.model \n
///   ㄴ SwtSpmtAtchmnflDt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | 이승재 |
///    | Date | 2019.12.151 |
///    | Class Version | v1.0 |
/// @section 상세설명
/// - 이 클래스는 배수설비인허가대장 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class SwtSpmtAtchmnflDt {
	
	private String ftrCde;
	
	private Long ftrIdn;
	
	private Long shtIdn;
	
	private String atchflSj;
	
	private Long fileNo;

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

	public Long getShtIdn() {
		return shtIdn;
	}

	public void setShtIdn(Long shtIdn) {
		this.shtIdn = shtIdn;
	}

	public String getAtchflSj() {
		return atchflSj;
	}

	public void setAtchflSj(String atchflSj) {
		this.atchflSj = atchflSj;
	}

	public Long getFileNo() {
		return fileNo;
	}

	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
	}
}
