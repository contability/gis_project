package kr.co.gitt.kworks.projects.gn.model;

/////////////////////////////////////////////
/// @class CtyGrdnAs
/// kr.co.gitt.kworks.projects.gn.model \n
///   ㄴ CtyGrdnAs.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 4. 26. 오후 3:48:02 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 관목 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class CtyGrdnAs {

	/// 지형지물부호
	private String ftrCde;
	
	/// 관목관리번호
	private Long ftrIdn;
	
	/// 공원구역관리번호
	private Long ftfIdn;
	
	/// 공원관리번호
	private Long ppkIdn;
	
	/// 설치일자
	private String sttYmd;
	
	/// 면적
	private Double grdAra;
	
	/// 식재관목종류
	private String srbCde;
	
	/// 수량
	private Integer treNum;
	
	/// 등록자명
	private String regNam;
	
	/// 자료등록일자
	private String lodYmd;
	
	/// 수정자명
	private String updNam;
	
	/// 자료수정일자
	private String udpYmd;

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

	public Long getPpkIdn() {
		return ppkIdn;
	}

	public void setPpkIdn(Long ppkIdn) {
		this.ppkIdn = ppkIdn;
	}

	public String getSttYmd() {
		return sttYmd;
	}

	public void setSttYmd(String sttYmd) {
		this.sttYmd = sttYmd;
	}

	public Double getGrdAra() {
		return grdAra;
	}

	public void setGrdAra(Double grdAra) {
		this.grdAra = grdAra;
	}

	public String getSrbCde() {
		return srbCde;
	}

	public void setSrbCde(String srbCde) {
		this.srbCde = srbCde;
	}

	public Integer getTreNum() {
		return treNum;
	}

	public void setTreNum(Integer treNum) {
		this.treNum = treNum;
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
	
}
