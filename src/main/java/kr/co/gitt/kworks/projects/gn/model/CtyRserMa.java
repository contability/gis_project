package kr.co.gitt.kworks.projects.gn.model;

/////////////////////////////////////////////
/// @class CtyRserMa
/// kr.co.gitt.kworks.projects.gn.model \n
///   ㄴ CtyRserMa.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 5. 2. 오후 4:52:29 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도시공원 민원대장 입니다.
///   -# 
/////////////////////////////////////////////
public class CtyRserMa {
	
	/// 민원대장관리번호
	private Long ftrIdn;
	
	/// 공원관리번호
	private Long ftfIdn;
	
	/// 지형지물부호
	private String ftrCde;
	
	/// 접수일자
	private String rcvYmd;
	
	/// 접수구분
	private String rcvCde;
	
	/// 민원인성명
	private String apmNam;
	
	/// 민원인주소
	private String apmAdr;
	
	/// 민원인전화번호
	private String apmTel;
	
	/// 민원내용
	private String aplExp;
	
	/// 담당자성명
	private String proNam;
	
	/// 처리일자
	private String proYmd;
	
	/// 처리구분
	private String proCde;
	
	/// 처리내용
	private String proExp;

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

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public String getRcvYmd() {
		return rcvYmd;
	}

	public void setRcvYmd(String rcvYmd) {
		this.rcvYmd = rcvYmd;
	}

	public String getRcvCde() {
		return rcvCde;
	}

	public void setRcvCde(String rcvCde) {
		this.rcvCde = rcvCde;
	}

	public String getApmNam() {
		return apmNam;
	}

	public void setApmNam(String apmNam) {
		this.apmNam = apmNam;
	}

	public String getApmAdr() {
		return apmAdr;
	}

	public void setApmAdr(String apmAdr) {
		this.apmAdr = apmAdr;
	}

	public String getApmTel() {
		return apmTel;
	}

	public void setApmTel(String apmTel) {
		this.apmTel = apmTel;
	}

	public String getAplExp() {
		return aplExp;
	}

	public void setAplExp(String aplExp) {
		this.aplExp = aplExp;
	}

	public String getProNam() {
		return proNam;
	}

	public void setProNam(String proNam) {
		this.proNam = proNam;
	}

	public String getProYmd() {
		return proYmd;
	}

	public void setProYmd(String proYmd) {
		this.proYmd = proYmd;
	}

	public String getProCde() {
		return proCde;
	}

	public void setProCde(String proCde) {
		this.proCde = proCde;
	}

	public String getProExp() {
		return proExp;
	}

	public void setProExp(String proExp) {
		this.proExp = proExp;
	}

}
