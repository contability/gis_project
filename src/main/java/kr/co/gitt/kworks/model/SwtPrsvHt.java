package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class SwlPrsvHt
/// kr.co.gitt.kworks.model \n
///   ㄴ WtlPrsvHt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2016. 11. 21. 오전 10:51:22 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 관리이력 - 하수 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class SwtPrsvHt {

	/// 유지보수일련번호
	private Long shtIdn;
	
	/// 지형지물부호
	private String ftrCde;
	
	/// 지형지물부호 명
	private String ftrNam;
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 관리이력구분 코드
	private String mnhCde;
	
	/// 관리이력구분 명
	private String mnhNam;
	
	/// 공사번호
	private Long cntIdn;
	
	/// 유지보수사유
	private String sbjCde;
	
	/// 유지보수사유-명
	private String sbjNam;
	
	/// 유지보수사유상세설명
	private String rerDes;
	
	/// 보수내용
	private String repDes;
	
	/// 보수시작일
	private String sreYmd;
	
	/// 보수종료일
	private String ereYmd;
	
	/// 시공자
	private String oprNam;
	
	/// 도메인 ID
	private String domnId;
	
	/// 코드 ID
	private String codeId;
	
	/// 코드 명
	private String codeNm;
	
	/// 사용여부
	private String useAt;

	public Long getShtIdn() {
		return shtIdn;
	}

	public void setShtIdn(Long shtIdn) {
		this.shtIdn = shtIdn;
	}

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public String getFtrNam() {
		return ftrNam;
	}

	public void setFtrNam(String ftrNam) {
		this.ftrNam = ftrNam;
	}

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public String getMnhCde() {
		return mnhCde;
	}

	public void setMnhCde(String mnhCde) {
		this.mnhCde = mnhCde;
	}

	public String getMnhNam() {
		return mnhNam;
	}

	public void setMnhNam(String mnhNam) {
		this.mnhNam = mnhNam;
	}

	public Long getCntIdn() {
		return cntIdn;
	}

	public void setCntIdn(Long cntIdn) {
		this.cntIdn = cntIdn;
	}

	public String getSbjCde() {
		return sbjCde;
	}

	public void setSbjCde(String sbjCde) {
		this.sbjCde = sbjCde;
	}

	public String getSbjNam() {
		return sbjNam;
	}

	public void setSbjNam(String sbjNam) {
		this.sbjNam = sbjNam;
	}

	public String getRerDes() {
		return rerDes;
	}

	public void setRerDes(String rerDes) {
		this.rerDes = rerDes;
	}

	public String getRepDes() {
		return repDes;
	}

	public void setRepDes(String repDes) {
		this.repDes = repDes;
	}

	public String getSreYmd() {
		return sreYmd;
	}

	public void setSreYmd(String sreYmd) {
		this.sreYmd = sreYmd;
	}

	public String getEreYmd() {
		return ereYmd;
	}

	public void setEreYmd(String ereYmd) {
		this.ereYmd = ereYmd;
	}

	public String getOprNam() {
		return oprNam;
	}

	public void setOprNam(String oprNam) {
		this.oprNam = oprNam;
	}

	public String getDomnId() {
		return domnId;
	}

	public void setDomnId(String domnId) {
		this.domnId = domnId;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeNm() {
		return codeNm;
	}

	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	
}
