package kr.co.gitt.kworks.model;

import java.util.List;

/////////////////////////////////////////////
/// @class RdlCtlrLs
/// kr.co.gitt.kworks.model \n
///   ㄴ RdlCtlrLs.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 3:00:31 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도로관리선 입니다.
///   -# 
/////////////////////////////////////////////
public class RdlCtlrLs {
	
	/// ID
	private Long objectid;
	
	/// 지형지물부호
	private String ftrCde;
	
	/// 도로관리선관리번호
	private Long secIdn;
	
	/// 중용여부
	private String jygCde;
	
	/// 자료등록일자
	private String lodYmd;
	
	/// 도로명 코드
	private String rdnCde;
	
	/// 도로관리선연결 목록
	private List<RdtAlcnDt> rdtAlcnDts;

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public Long getSecIdn() {
		return secIdn;
	}

	public void setSecIdn(Long secIdn) {
		this.secIdn = secIdn;
	}

	public String getJygCde() {
		return jygCde;
	}

	public void setJygCde(String jygCde) {
		this.jygCde = jygCde;
	}

	public String getLodYmd() {
		return lodYmd;
	}

	public void setLodYmd(String lodYmd) {
		this.lodYmd = lodYmd;
	}

	public String getRdnCde() {
		return rdnCde;
	}

	public void setRdnCde(String rdnCde) {
		this.rdnCde = rdnCde;
	}

	public List<RdtAlcnDt> getRdtAlcnDts() {
		return rdtAlcnDts;
	}

	public void setRdtAlcnDts(List<RdtAlcnDt> rdtAlcnDts) {
		this.rdtAlcnDts = rdtAlcnDts;
	}
	
}
