package kr.co.gitt.kworks.projects.gn.model;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.dto.CityParkFacilityResultDTO;

/////////////////////////////////////////////
/// @class CtyPksdAs
/// kr.co.gitt.kworks.projects.gn.model \n
///   ㄴ CtyPksdAs.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 4. 26. 오후 3:45:02 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 공원 구역 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class CtyPksdAs {

	/// 지형지물부호
	private String ftrCde;
	
	/// 공원구역관리번호
	private Long ftrIdn;
	
	/// 공원관리번호
	private Long ftfIdn;
	
	/// 구역용도
	private String sdlCde;
	
	/// 바닥재질
	private String flrCde;
	
	/// 면적
	private Double grdAra;

	/// 펜스유무
	private String fenCde;
	
	/// 등록자명
	private String regNam;
	
	/// 자료등록일자
	private String lodYmd;
	
	/// 수정자명
	private String updNam;

	/// 자료수정일자
	private String udpYmd;
	
	/// 설치일자
	private String sttYmd;
	
	/// 비고
	private String remark;
	
	/// 관목 목록
	private List<CityParkFacilityResultDTO> cityParkFacilityResults;

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

	public Long getFtfIdn() {
		return ftfIdn;
	}

	public void setFtfIdn(Long ftfIdn) {
		this.ftfIdn = ftfIdn;
	}

	public String getSdlCde() {
		return sdlCde;
	}

	public void setSdlCde(String sdlCde) {
		this.sdlCde = sdlCde;
	}

	public String getFlrCde() {
		return flrCde;
	}

	public void setFlrCde(String flrCde) {
		this.flrCde = flrCde;
	}

	public Double getGrdAra() {
		return grdAra;
	}

	public void setGrdAra(Double grdAra) {
		this.grdAra = grdAra;
	}

	public String getFenCde() {
		return fenCde;
	}

	public void setFenCde(String fenCde) {
		this.fenCde = fenCde;
	}

	public String getRegNam() {
		return regNam;
	}

	public void setRegNam(String regNam) {
		this.regNam = regNam;
	}

	public String getLodYmd() {
		return lodYmd;
	}

	public void setLodYmd(String lodYmd) {
		this.lodYmd = lodYmd;
	}

	public String getUpdNam() {
		return updNam;
	}

	public void setUpdNam(String updNam) {
		this.updNam = updNam;
	}

	public String getUdpYmd() {
		return udpYmd;
	}

	public void setUdpYmd(String udpYmd) {
		this.udpYmd = udpYmd;
	}

	public String getSttYmd() {
		return sttYmd;
	}

	public void setSttYmd(String sttYmd) {
		this.sttYmd = sttYmd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<CityParkFacilityResultDTO> getCityParkFacilityResults() {
		return cityParkFacilityResults;
	}

	public void setCityParkFacilityResults(List<CityParkFacilityResultDTO> cityParkFacilityResults) {
		this.cityParkFacilityResults = cityParkFacilityResults;
	}

	
}
