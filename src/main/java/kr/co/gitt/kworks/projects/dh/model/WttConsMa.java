package kr.co.gitt.kworks.projects.dh.model;

import kr.co.gitt.kworks.projects.dh.dto.CntrwkRegstrDTO;

/////////////////////////////////////////////
/// @class WttConsMa
/// kr.co.gitt.kworks.projects.dh.model \n
///   ㄴ WttConsMa.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 2. 오후 6:25:48 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 강릉시 상수공사대장 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class WttConsMa extends CntrwkRegstrDTO {

	/// 지형지물부호
	private String ftrCde;
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 공사명
	private String cntNam;
	
	/// 공사구분
	private String wrkCde;
	
	/// 공사위치
	private String cntLoc;
	
	/// 설계자
	private String dsnNam;
	
	/// 공사개요
	private String cntDes;
	
	/// 설계총액
	private Long dsnAmt;
	
	/// 관급물량
	private String gvrDes;
	
	/// 설계금액-순공사비
	private Long dpcAmt;
	
	/// 착공일자
	private String begYmd;
	
	/// 설계금액-관급금액
	private Long dgcAmt;
	
	/// 설계금액-기타잡비
	private Long detAmt;
	
	/// 준공예정일자
	private String fnsYmd;
	
	/// 감독자성명
	private String svsNam;
	
	/// 국비
	private Long natAmt;
	
	/// 실준공일자
	private String rfnYmd;
	
	/// 재원-도비
	private Long couAmt;
	
	/// 준공검사일자
	private String fchYmd;
	
	/// 시비
	private Long citAmt;
	
	/// 준공검사자성명
	private String fchNam;
	
	/// 재원-기채
	private Long bndAmt;
	
	/// 재원-양여금
	private Long cssAmt;
	
	/// 지출과목-관
	private String kwnExp;
	
	/// 지출과목-항
	private String hngExp;
	
	/// 지출과목-세항
	private String shnExp;
	
	/// 지출과목-목
	private String mokExp;
	
	/// 입찰일자
	private String bidYmd;
	
	/// 예정금액
	private Long estAmt;
	
	/// 계약일자
	private String cttYmd;
	
	/// 계약방법
	private String cttCde;
	
	/// 계약총액
	private Long tctAmt;
	
	/// 계약금액-순공사비
	private Long cpcAmt;
	
	/// 계약금액-관급금액
	private Long cgvAmt;
	
	/// 계약금액-기타잡비
	private Long cetAmt;
	
	/// 도급자명
	private String gcnNam;
	
	/// 대표자성명
	private String pocNam;
	
	/// 도급자주소
	private String gcnAdr;
	
	/// 도급자전화번호
	private String gcnTel;

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

	public String getCntNam() {
		return cntNam;
	}

	public void setCntNam(String cntNam) {
		this.cntNam = cntNam;
	}

	public String getWrkCde() {
		return wrkCde;
	}

	public void setWrkCde(String wrkCde) {
		this.wrkCde = wrkCde;
	}

	public String getCntLoc() {
		return cntLoc;
	}

	public void setCntLoc(String cntLoc) {
		this.cntLoc = cntLoc;
	}

	public String getDsnNam() {
		return dsnNam;
	}

	public void setDsnNam(String dsnNam) {
		this.dsnNam = dsnNam;
	}

	public String getCntDes() {
		return cntDes;
	}

	public void setCntDes(String cntDes) {
		this.cntDes = cntDes;
	}

	public Long getDsnAmt() {
		return dsnAmt;
	}

	public void setDsnAmt(Long dsnAmt) {
		this.dsnAmt = dsnAmt;
	}

	public String getGvrDes() {
		return gvrDes;
	}

	public void setGvrDes(String gvrDes) {
		this.gvrDes = gvrDes;
	}

	public Long getDpcAmt() {
		return dpcAmt;
	}

	public void setDpcAmt(Long dpcAmt) {
		this.dpcAmt = dpcAmt;
	}

	public String getBegYmd() {
		return begYmd;
	}

	public void setBegYmd(String begYmd) {
		this.begYmd = begYmd;
	}

	public Long getDgcAmt() {
		return dgcAmt;
	}

	public void setDgcAmt(Long dgcAmt) {
		this.dgcAmt = dgcAmt;
	}

	public Long getDetAmt() {
		return detAmt;
	}

	public void setDetAmt(Long detAmt) {
		this.detAmt = detAmt;
	}

	public String getFnsYmd() {
		return fnsYmd;
	}

	public void setFnsYmd(String fnsYmd) {
		this.fnsYmd = fnsYmd;
	}

	public String getSvsNam() {
		return svsNam;
	}

	public void setSvsNam(String svsNam) {
		this.svsNam = svsNam;
	}

	public Long getNatAmt() {
		return natAmt;
	}

	public void setNatAmt(Long natAmt) {
		this.natAmt = natAmt;
	}

	public String getRfnYmd() {
		return rfnYmd;
	}

	public void setRfnYmd(String rfnYmd) {
		this.rfnYmd = rfnYmd;
	}

	public Long getCouAmt() {
		return couAmt;
	}

	public void setCouAmt(Long couAmt) {
		this.couAmt = couAmt;
	}

	public String getFchYmd() {
		return fchYmd;
	}

	public void setFchYmd(String fchYmd) {
		this.fchYmd = fchYmd;
	}

	public Long getCitAmt() {
		return citAmt;
	}

	public void setCitAmt(Long citAmt) {
		this.citAmt = citAmt;
	}

	public String getFchNam() {
		return fchNam;
	}

	public void setFchNam(String fchNam) {
		this.fchNam = fchNam;
	}

	public Long getBndAmt() {
		return bndAmt;
	}

	public void setBndAmt(Long bndAmt) {
		this.bndAmt = bndAmt;
	}

	public Long getCssAmt() {
		return cssAmt;
	}

	public void setCssAmt(Long cssAmt) {
		this.cssAmt = cssAmt;
	}

	public String getKwnExp() {
		return kwnExp;
	}

	public void setKwnExp(String kwnExp) {
		this.kwnExp = kwnExp;
	}

	public String getHngExp() {
		return hngExp;
	}

	public void setHngExp(String hngExp) {
		this.hngExp = hngExp;
	}

	public String getShnExp() {
		return shnExp;
	}

	public void setShnExp(String shnExp) {
		this.shnExp = shnExp;
	}

	public String getMokExp() {
		return mokExp;
	}

	public void setMokExp(String mokExp) {
		this.mokExp = mokExp;
	}

	public String getBidYmd() {
		return bidYmd;
	}

	public void setBidYmd(String bidYmd) {
		this.bidYmd = bidYmd;
	}

	public Long getEstAmt() {
		return estAmt;
	}

	public void setEstAmt(Long estAmt) {
		this.estAmt = estAmt;
	}

	public String getCttYmd() {
		return cttYmd;
	}

	public void setCttYmd(String cttYmd) {
		this.cttYmd = cttYmd;
	}

	public String getCttCde() {
		return cttCde;
	}

	public void setCttCde(String cttCde) {
		this.cttCde = cttCde;
	}

	public Long getTctAmt() {
		return tctAmt;
	}

	public void setTctAmt(Long tctAmt) {
		this.tctAmt = tctAmt;
	}

	public Long getCpcAmt() {
		return cpcAmt;
	}

	public void setCpcAmt(Long cpcAmt) {
		this.cpcAmt = cpcAmt;
	}

	public Long getCgvAmt() {
		return cgvAmt;
	}

	public void setCgvAmt(Long cgvAmt) {
		this.cgvAmt = cgvAmt;
	}

	public Long getCetAmt() {
		return cetAmt;
	}

	public void setCetAmt(Long cetAmt) {
		this.cetAmt = cetAmt;
	}

	public String getGcnNam() {
		return gcnNam;
	}

	public void setGcnNam(String gcnNam) {
		this.gcnNam = gcnNam;
	}

	public String getPocNam() {
		return pocNam;
	}

	public void setPocNam(String pocNam) {
		this.pocNam = pocNam;
	}

	public String getGcnAdr() {
		return gcnAdr;
	}

	public void setGcnAdr(String gcnAdr) {
		this.gcnAdr = gcnAdr;
	}

	public String getGcnTel() {
		return gcnTel;
	}

	public void setGcnTel(String gcnTel) {
		this.gcnTel = gcnTel;
	}

}
