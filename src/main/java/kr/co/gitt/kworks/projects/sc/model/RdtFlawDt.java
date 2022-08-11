package kr.co.gitt.kworks.projects.sc.model;

import kr.co.gitt.kworks.projects.sc.dto.CntrwkRegstrDTO;

/////////////////////////////////////////////
/// @class RdtFlawDt
/// kr.co.gitt.kworks.projects.sc.model \n
///   ㄴ RdtFlawDt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 6. 오후 3:22:28 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 도로 공사대장 하자보수내역 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class RdtFlawDt extends CntrwkRegstrDTO {

	/// 관리번호
	private Long ftrIdn;
	
	/// 지형지물부호
	private String ftrCde;
	
	/// 변경일련번호
	private Long shtIdn;
	
	/// 하자발생일자
	private String flaYmd;
	
	/// 하자보수일자
	private String rprYmd;
	
	/// 하자보수내용
	private String rprDes;

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public Long getShtIdn() {
		return shtIdn;
	}

	public void setShtIdn(Long shtIdn) {
		this.shtIdn = shtIdn;
	}

	public String getFlaYmd() {
		return flaYmd;
	}

	public void setFlaYmd(String flaYmd) {
		this.flaYmd = flaYmd;
	}

	public String getRprYmd() {
		return rprYmd;
	}

	public void setRprYmd(String rprYmd) {
		this.rprYmd = rprYmd;
	}

	public String getRprDes() {
		return rprDes;
	}

	public void setRprDes(String rprDes) {
		this.rprDes = rprDes;
	}
	
}
