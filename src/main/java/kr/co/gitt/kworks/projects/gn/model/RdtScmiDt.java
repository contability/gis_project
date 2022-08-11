package kr.co.gitt.kworks.projects.gn.model;

/////////////////////////////////////////////
/// @class RdtScmiDt
/// kr.co.gitt.kworks.projects.gn.model \n
///   ㄴ RdtScmiDt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 21. 오후 3:47:22 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 보안등민원 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class RdtScmiDt {
	
	//지형지물부호
	private String ftrCde;
	
	//관리번호
	private Long ftrIdn;
	
	//접수일자
	private String recYmd;
	
	//접수자성명
	private String rcvNam;
	
	//비고
	private String rmkExp;
	
	//민원내용
	private String aplExp;
	
	//민원인성명
	private String apmNam;
	
	//민원인주소
	private String apmAdr;
	
	//민원인전화번호
	private String apmTel;
	
	//민원구분
	private String rcvGbn;
	
	//처리기한
	private String durYmd;
	
	//처리상태
	private String prsCde;
	
	//번지설명
	private String aplAdr;
	
	//처리내용
	private String proExp;
	
	//업체명
	private String depNam;
	
	//처리완료일자
	private String proYmd;
	
	//처리자성명
	private String proNam;
	
	//행정구역
	private String hjdCde;
	
	//등기구
	private String dggChk;
	
	//램프
	private String lmpChk;
	
	//안정기
	private String yzgChk;
	
	//점멸기
	private String zssChk;
	
	//전선포설
	private String zspChk;
	
	//밴드
	private String bndChk;
	
	//기타
	private String etcChk;
	
	//고장내용
	private String parExp;
	
	//현장확인(업체통보)
	private String finYnd;
	
	//대표자명
	private String manNam;
	
	//업체전화번호
	private String depTel;
	
	//관리번호(표찰번호)
	private String orgNum;
	
	//통번
	private Long tngNum;

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

	public String getRecYmd() {
		return recYmd;
	}

	public void setRecYmd(String recYmd) {
		this.recYmd = recYmd;
	}

	public String getRcvNam() {
		return rcvNam;
	}

	public void setRcvNam(String rcvNam) {
		this.rcvNam = rcvNam;
	}

	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}

	public String getAplExp() {
		return aplExp;
	}

	public void setAplExp(String aplExp) {
		this.aplExp = aplExp;
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

	public String getRcvGbn() {
		return rcvGbn;
	}

	public void setRcvGbn(String rcvGbn) {
		this.rcvGbn = rcvGbn;
	}

	public String getDurYmd() {
		return durYmd;
	}

	public void setDurYmd(String durYmd) {
		this.durYmd = durYmd;
	}

	public String getPrsCde() {
		return prsCde;
	}

	public void setPrsCde(String prsCde) {
		this.prsCde = prsCde;
	}

	public String getAplAdr() {
		return aplAdr;
	}

	public void setAplAdr(String aplAdr) {
		this.aplAdr = aplAdr;
	}

	public String getProExp() {
		return proExp;
	}

	public void setProExp(String proExp) {
		this.proExp = proExp;
	}

	public String getDepNam() {
		return depNam;
	}

	public void setDepNam(String depNam) {
		this.depNam = depNam;
	}

	public String getProYmd() {
		return proYmd;
	}

	public void setProYmd(String proYmd) {
		this.proYmd = proYmd;
	}

	public String getProNam() {
		return proNam;
	}

	public void setProNam(String proNam) {
		this.proNam = proNam;
	}

	public String getHjdCde() {
		return hjdCde;
	}

	public void setHjdCde(String hjdCde) {
		this.hjdCde = hjdCde;
	}

	public String getDggChk() {
		return dggChk;
	}

	public void setDggChk(String dggChk) {
		this.dggChk = dggChk;
	}

	public String getLmpChk() {
		return lmpChk;
	}

	public void setLmpChk(String lmpChk) {
		this.lmpChk = lmpChk;
	}

	public String getYzgChk() {
		return yzgChk;
	}

	public void setYzgChk(String yzgChk) {
		this.yzgChk = yzgChk;
	}

	public String getZssChk() {
		return zssChk;
	}

	public void setZssChk(String zssChk) {
		this.zssChk = zssChk;
	}

	public String getZspChk() {
		return zspChk;
	}

	public void setZspChk(String zspChk) {
		this.zspChk = zspChk;
	}

	public String getBndChk() {
		return bndChk;
	}

	public void setBndChk(String bndChk) {
		this.bndChk = bndChk;
	}

	public String getEtcChk() {
		return etcChk;
	}

	public void setEtcChk(String etcChk) {
		this.etcChk = etcChk;
	}

	public String getParExp() {
		return parExp;
	}

	public void setParExp(String parExp) {
		this.parExp = parExp;
	}

	public String getFinYnd() {
		return finYnd;
	}

	public void setFinYnd(String finYnd) {
		this.finYnd = finYnd;
	}

	public String getManNam() {
		return manNam;
	}

	public void setManNam(String manNam) {
		this.manNam = manNam;
	}

	public String getDepTel() {
		return depTel;
	}

	public void setDepTel(String depTel) {
		this.depTel = depTel;
	}

	public String getOrgNum() {
		return orgNum;
	}

	public void setOrgNum(String orgNum) {
		this.orgNum = orgNum;
	}

	public Long getTngNum() {
		return tngNum;
	}

	public void setTngNum(Long tngNum) {
		this.tngNum = tngNum;
	}
	
}
