package kr.co.gitt.kworks.projects.gn.model;

/////////////////////////////////////////////
/// @class CtyParkAs
/// kr.co.gitt.kworks.projects.gn.model \n
///   ㄴ CtyParkAs.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 4. 21. 오후 5:23:53 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도시공원 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class CtyParkAs {
	
	/// Objectid
	private String objectid;

	/// 지형지물부호
	private String ftrCde;
	
	/// 공원관리번호
	private Long ftrIdn;
	
	/// 공원종류
	private String prkCde;
	
	/// 명칭
	private String prkNam;
	
	/// 관리기관
	private String mngCde;
	
	/// 위치
	private String prkLoc;
	
	/// 관리방법
	private String pkmCde;
	
	/// 관리자 성명
	private String mngNam;
	
	/// 관리자 주소
	private String mngAdd;
	
	/// 도시계획결정일자
	private String ctpYmd;
	
	/// 조성계획결정일자
	private String mkpYmd;
	
	/// 도시계획결정지적고시
	private String ctpLaw;
	
	/// 조성계획결정지적고시
	private String mkpLaw;
	
	/// 허가일자
	private String pmsYmd;
	
	/// 착수일자
	private String rfnYmd;
	
	/// 면적
	private Double prkAra;
	
	/// 비고
	private String remark;
	
	/// 법정읍/면/동
	private String bjdCde;
	
	/// 행정읍/면/동
	private String hjdCde;
	
	/// 등록자명
	private String regNam;
	
	/// 자료등록일자
	private String lodYmd;
	
	/// 수정자명
	private String updNam;
	
	/// 자료수정일자
	private String udpYmd;
	
	/// WKT
	private String wkt;
	
	/// 중심점
	private String centroid;

	public String getObjectid() {
		return objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}

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

	public String getPrkCde() {
		return prkCde;
	}

	public void setPrkCde(String prkCde) {
		this.prkCde = prkCde;
	}

	public String getPrkNam() {
		return prkNam;
	}

	public void setPrkNam(String prkNam) {
		this.prkNam = prkNam;
	}

	public String getMngCde() {
		return mngCde;
	}

	public void setMngCde(String mngCde) {
		this.mngCde = mngCde;
	}

	public String getPrkLoc() {
		return prkLoc;
	}

	public void setPrkLoc(String prkLoc) {
		this.prkLoc = prkLoc;
	}

	public String getPkmCde() {
		return pkmCde;
	}

	public void setPkmCde(String pkmCde) {
		this.pkmCde = pkmCde;
	}

	public String getMngNam() {
		return mngNam;
	}

	public void setMngNam(String mngNam) {
		this.mngNam = mngNam;
	}

	public String getMngAdd() {
		return mngAdd;
	}

	public void setMngAdd(String mngAdd) {
		this.mngAdd = mngAdd;
	}

	public String getCtpYmd() {
		return ctpYmd;
	}

	public void setCtpYmd(String ctpYmd) {
		this.ctpYmd = ctpYmd;
	}

	public String getMkpYmd() {
		return mkpYmd;
	}

	public void setMkpYmd(String mkpYmd) {
		this.mkpYmd = mkpYmd;
	}

	public String getCtpLaw() {
		return ctpLaw;
	}

	public void setCtpLaw(String ctpLaw) {
		this.ctpLaw = ctpLaw;
	}

	public String getMkpLaw() {
		return mkpLaw;
	}

	public void setMkpLaw(String mkpLaw) {
		this.mkpLaw = mkpLaw;
	}

	public String getPmsYmd() {
		return pmsYmd;
	}

	public void setPmsYmd(String pmsYmd) {
		this.pmsYmd = pmsYmd;
	}

	public String getRfnYmd() {
		return rfnYmd;
	}

	public void setRfnYmd(String rfnYmd) {
		this.rfnYmd = rfnYmd;
	}

	public Double getPrkAra() {
		return prkAra;
	}

	public void setPrkAra(Double prkAra) {
		this.prkAra = prkAra;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBjdCde() {
		return bjdCde;
	}

	public void setBjdCde(String bjdCde) {
		this.bjdCde = bjdCde;
	}

	public String getHjdCde() {
		return hjdCde;
	}

	public void setHjdCde(String hjdCde) {
		this.hjdCde = hjdCde;
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

	public String getWkt() {
		return wkt;
	}

	public void setWkt(String wkt) {
		this.wkt = wkt;
	}

	public String getCentroid() {
		return centroid;
	}

	public void setCentroid(String centroid) {
		this.centroid = centroid;
	}
	
}
