package kr.co.gitt.kworks.projects.yy.model;

// Gitt
// create data 2020_06_10 AM 10:47:00
// create user Wongi_Jo
// @이클래스는 정책현황 모델입니다.
/// 수정자 : 이승재, 2020.12.07
///   - 계약일 cttYmd에서 ctrctDt로 변경

public class PlcyStatAs {
	
	//아이디
	private Long objectId;
	
	//공간객체
	private String geom;
	
	//관리번호
	private Long ftrIdn;
	
	//지형지물부호
	private String ftrCde;
	
	//법정/읍/면동
	private String bjdCde;
	
	//행정/읍/면동
	private String hjdCde;
	
	//자료등록일자
	private String lodYmd;
	
	//정책명
	private String plcyTit;
	
	//담당부서
	private String plcyDep;
	
	//담당자
	private String plcyMng;
	
	//작성자
	private String plcyWrt;
	
	//위치
	private String plcyAdr;
	
	//위치
	private String selDong;

	//위치
	private String isMount;
	
	//위치
	private String mainNum;
	
	//위치
	private String subNum;

	//금액
	private String ctrctamt;
	
	//계약일
	private String ctrctDt;
	
	//착공일
	private String cttBeg;
	
	//준공일
	private String cttFrn;
	
	//도급자상호
	private String cttNam;
	
	//비고
	private String rmkExp;
	
	//담당부서 서브 이름
	private String deptSbNm;
	
	//담당부서 서브 코드
	private String deptSbCd;

	public String getSelDong() {
		return selDong;
	}

	public void setSelDong(String selDong) {
		this.selDong = selDong;
	}

	public String getIsMount() {
		return isMount;
	}

	public void setIsMount(String isMount) {
		this.isMount = isMount;
	}

	public String getMainNum() {
		return mainNum;
	}

	public void setMainNum(String mainNum) {
		this.mainNum = mainNum;
	}

	public String getSubNum() {
		return subNum;
	}

	public void setSubNum(String subNum) {
		this.subNum = subNum;
	}
	
	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

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

	public String getLodYmd() {
		return lodYmd;
	}

	public void setLodYmd(String lodYmd) {
		this.lodYmd = lodYmd;
	}

	public String getPlcyTit() {
		return plcyTit;
	}

	public void setPlcyTit(String plcyTit) {
		this.plcyTit = plcyTit;
	}

	public String getPlcyDep() {
		return plcyDep;
	}

	public void setPlcyDep(String plcyDep) {
		this.plcyDep = plcyDep;
	}

	public String getPlcyMng() {
		return plcyMng;
	}

	public void setPlcyMng(String plcyMng) {
		this.plcyMng = plcyMng;
	}

	public String getPlcyWrt() {
		return plcyWrt;
	}

	public void setPlcyWrt(String plcyWrt) {
		this.plcyWrt = plcyWrt;
	}

	public String getPlcyAdr() {
		return plcyAdr;
	}

	public void setPlcyAdr(String plcyAdr) {
		this.plcyAdr = plcyAdr;
	}

	/// 금액
	public String getCtrctamt() {
		return ctrctamt;
	}

	public void setCtrctamt(String ctrctamt) {
		this.ctrctamt = ctrctamt;
	}

	public String getCtrctDt() {
		return ctrctDt;
	}

	public void setCtrctDt(String ctrctDt) {
		this.ctrctDt = ctrctDt;
	}

	public String getCttBeg() {
		return cttBeg;
	}

	public void setCttBeg(String cttBeg) {
		this.cttBeg = cttBeg;
	}

	public String getCttFrn() {
		return cttFrn;
	}

	public void setCttFrn(String cttFrn) {
		this.cttFrn = cttFrn;
	}

	public String getCttNam() {
		return cttNam;
	}

	public void setCttNam(String cttNam) {
		this.cttNam = cttNam;
	}

	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}

	public String getDeptSbNm() {
		return deptSbNm;
	}

	public void setDeptSbNm(String deptSbNm) {
		this.deptSbNm = deptSbNm;
	}

	public String getDeptSbCd() {
		return deptSbCd;
	}

	public void setDeptSbCd(String deptSbCd) {
		this.deptSbCd = deptSbCd;
	}	
}
