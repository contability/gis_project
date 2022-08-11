package kr.co.gitt.kworks.cmmn.dto;

/////////////////////////////////////////////
/// @class EhojoCntrwkRegstrDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ EhojoCntrwkRegstrDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 5. 26. 오후 3:41:26 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 ehojo 공사대장 DTO 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public class EhojoCntrwkRegstrDTO {
	
	/// ehojo 번호
	private String cttNum;
	
	/// 공사명
	private String cntNam;
		
	/// 공사위치
	private String cntLoc;
	
	/// 공사개요
	private String cntDes;	
	
	/// 감독자성명
	private String svsNam;
	
	/// 착공일자
	private String begYmd;	
	
	/// 준공검사자성명
	private String fchNam;	
	
	/// 준공예정일자
	private String fnsYmd;	
	
	/// 준공검사일자
	private String fchYmd;
	
	/// 실준공일자
	private String rfnYmd;
	
	/// 입찰일자
	private String bidYmd;	
	
	/// 계약일자
	private String cttYmd;	
	
	/// 계약방법
	private String cttCde;
	
	/// 예정금액
	private Long estAmt;	
	
	/// 계약총액
	private Long tctAmt;	
	
	/// 도급자명
	private String gcnNam;
	
	/// 대표자성명
	private String pocNam;	
	
	/// 도급자주소
	private String gcnAdr;	
	
	/// 도급자전화번호
	private String gcnTel;

	public String getCttNum() {
		return cttNum;
	}

	public void setCttNum(String cttNum) {
		this.cttNum = cttNum;
	}

	public String getCntNam() {
		return cntNam;
	}

	public void setCntNam(String cntNam) {
		this.cntNam = cntNam;
	}

	public String getCntLoc() {
		return cntLoc;
	}

	public void setCntLoc(String cntLoc) {
		this.cntLoc = cntLoc;
	}

	public String getCntDes() {
		return cntDes;
	}

	public void setCntDes(String cntDes) {
		this.cntDes = cntDes;
	}

	public String getSvsNam() {
		return svsNam;
	}

	public void setSvsNam(String svsNam) {
		this.svsNam = svsNam;
	}

	public String getBegYmd() {
		return begYmd;
	}

	public void setBegYmd(String begYmd) {
		this.begYmd = begYmd;
	}

	public String getFchNam() {
		return fchNam;
	}

	public void setFchNam(String fchNam) {
		this.fchNam = fchNam;
	}

	public String getFnsYmd() {
		return fnsYmd;
	}

	public void setFnsYmd(String fnsYmd) {
		this.fnsYmd = fnsYmd;
	}

	public String getFchYmd() {
		return fchYmd;
	}

	public void setFchYmd(String fchYmd) {
		this.fchYmd = fchYmd;
	}

	public String getRfnYmd() {
		return rfnYmd;
	}

	public void setRfnYmd(String rfnYmd) {
		this.rfnYmd = rfnYmd;
	}

	public String getBidYmd() {
		return bidYmd;
	}

	public void setBidYmd(String bidYmd) {
		this.bidYmd = bidYmd;
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

	public Long getEstAmt() {
		return estAmt;
	}

	public void setEstAmt(Long estAmt) {
		this.estAmt = estAmt;
	}

	public Long getTctAmt() {
		return tctAmt;
	}

	public void setTctAmt(Long tctAmt) {
		this.tctAmt = tctAmt;
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
