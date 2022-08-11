package kr.co.gitt.kworks.cmmn.dto;

/////////////////////////////////////////////
/// @class CntrctRegstrDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ CntrctRegstrDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2016. 11. 30. 오후 3:01:34 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 계약대장 DTO 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public class CntrctRegstrDTO {

	/// 계약종류 - V_TCM_SUMMARYLIST
	private String ctrtKind;

	/// 계약대장관리번호 - V_TCM_CTRTBOOKS_LINK
	private String ctrtAcctBookMngNo;
	
	/// 회계년도 - V_TCM_CTRTBOOKS_LINK
	private String fisYear;

	/// 계약명 - V_TCM_CTRTBOOKS_LINK
	private String ctrtNm;
	
	/// 계약일자 - V_TCM_BIZSUMMARY
	private String ctrtYmd;
	
	/// 최초총괄금액(최초총괄계약금액) - V_TCM_CTRTBOOKS_LINK
	private Long firstSummaryCtrtAmt;
	
	/// 총계약금액(총괄계약합계금액) - V_TCM_CTRTBOOKS_LINK
	private Long summaryCtrtTotAmt;
	
	/// 수수료(조달수수료) - V_TCM_CTRTBOOKS_LINK
	private Long splyCmmsnFee;
	
	/// 준공예정일자 - V_TCM_CTRTBOOKS_LINK
	private String compltSchdYmd;
	
	/// 계약방법 - V_TCM_BIZSUMMARY
	private String ctrtMethNm;
	
	/// 계약유형(계약유형명) - V_TCM_BIZSUMMARY
	private String ctrtTypeNm;
	
	/// 계약내용(계약적요) - V_TCM_CTRTBOOKS_LINK
	private String ctrtOutline;
	
	/// 업체명(주계약자명) - V_TCM_BIZSUMMARY
	private String custNm;
	
	/// 관서(계약관서명) - V_TCM_SUMMARYLIST
	private String ctrtGovNm;
	
	/// 계약형태(계약구분명) - V_TCM_BIZSUMMARY
	private String ctrtFgNm;
	
	/// 착공(예정)일자 - V_TCM_CTRTBOOKS_LINK
	private String startWorkYmd;
	
	/// 절대공기 - V_TCM_CTRTBOOKS_LINK
	private Long firstContPrd;
	
	/// 경비구분 - V_TCM_CTRTBOOKS_LINK
	private String expFg;
	
	/// 지체상율(지체상금율) - V_TCM_CTRTBOOKS_LINK
	private Long delayCompenRate;
	
	/// 준공/납품예정일자(준공일자) - V_TCM_CTRTBOOKS_LINK
	private String compltYmd;
	
	/// (수의)계약사유 - V_TCM_CTRTBOOKS_LINK
	private String ctrtRsn;
	
	/// 공동계약구분 - V_TCM_CTRTBOOKS_LINK
	private String collabCtrtFg;
	
	/// 채무부담행위액(채무부담액) - V_TCM_CTRTBOOKS_LINK
	private Long liabBundAmt;
	
	/// 사업개요 - V_TCM_BIZSUMMARY
	private String bizCont;
	
	/// 위치(납품장소)(위치명) - V_TCM_CTRTBOOKS_LINK
	private String locatNm;
	
	/// 개산계약여부 - V_TCM_CTRTBOOKS_LINK
	private String rouestYn;
	
	/// 조달구분 - V_TCM_CTRTBOOKS_LINK
	private String splyFg;
	
	/// 조달관리번호 - V_TCM_CTRTBOOKS_LINK
	private String splyMngNo;
	
	/// 기지출액 - V_TCM_CTRTBOOKS_LINK
	private Long beforeSummaryCtrtAmt;
	
	/// 원인행위총액(원인행위액) - V_TCM_INSPECTIONDETL_LINK
	private Long causeActAmt;
	
	public String getCtrtKind() {
		return ctrtKind;
	}

	public void setCtrtKind(String ctrtKind) {
		this.ctrtKind = ctrtKind;
	}

	public String getCtrtAcctBookMngNo() {
		return ctrtAcctBookMngNo;
	}

	public void setCtrtAcctBookMngNo(String ctrtAcctBookMngNo) {
		this.ctrtAcctBookMngNo = ctrtAcctBookMngNo;
	}

	public String getFisYear() {
		return fisYear;
	}

	public void setFisYear(String fisYear) {
		this.fisYear = fisYear;
	}

	public String getCtrtNm() {
		return ctrtNm;
	}

	public void setCtrtNm(String ctrtNm) {
		this.ctrtNm = ctrtNm;
	}

	public String getCtrtYmd() {
		return ctrtYmd;
	}

	public void setCtrtYmd(String ctrtYmd) {
		this.ctrtYmd = ctrtYmd;
	}

	public Long getFirstSummaryCtrtAmt() {
		return firstSummaryCtrtAmt;
	}

	public void setFirstSummaryCtrtAmt(Long firstSummaryCtrtAmt) {
		this.firstSummaryCtrtAmt = firstSummaryCtrtAmt;
	}

	public Long getSummaryCtrtTotAmt() {
		return summaryCtrtTotAmt;
	}

	public void setSummaryCtrtTotAmt(Long summaryCtrtTotAmt) {
		this.summaryCtrtTotAmt = summaryCtrtTotAmt;
	}

	public Long getSplyCmmsnFee() {
		return splyCmmsnFee;
	}

	public void setSplyCmmsnFee(Long splyCmmsnFee) {
		this.splyCmmsnFee = splyCmmsnFee;
	}

	public String getCompltSchdYmd() {
		return compltSchdYmd;
	}

	public void setCompltSchdYmd(String compltSchdYmd) {
		this.compltSchdYmd = compltSchdYmd;
	}

	public String getCtrtMethNm() {
		return ctrtMethNm;
	}

	public void setCtrtMethNm(String ctrtMethNm) {
		this.ctrtMethNm = ctrtMethNm;
	}

	public String getCtrtTypeNm() {
		return ctrtTypeNm;
	}

	public void setCtrtTypeNm(String ctrtTypeNm) {
		this.ctrtTypeNm = ctrtTypeNm;
	}

	public String getCtrtOutline() {
		return ctrtOutline;
	}

	public void setCtrtOutline(String ctrtOutline) {
		this.ctrtOutline = ctrtOutline;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}

	public String getCtrtGovNm() {
		return ctrtGovNm;
	}

	public void setCtrtGovNm(String ctrtGovNm) {
		this.ctrtGovNm = ctrtGovNm;
	}

	public String getCtrtFgNm() {
		return ctrtFgNm;
	}

	public void setCtrtFgNm(String ctrtFgNm) {
		this.ctrtFgNm = ctrtFgNm;
	}

	public String getStartWorkYmd() {
		return startWorkYmd;
	}

	public void setStartWorkYmd(String startWorkYmd) {
		this.startWorkYmd = startWorkYmd;
	}

	public Long getFirstContPrd() {
		return firstContPrd;
	}

	public void setFirstContPrd(Long firstContPrd) {
		this.firstContPrd = firstContPrd;
	}

	public String getExpFg() {
		return expFg;
	}

	public void setExpFg(String expFg) {
		this.expFg = expFg;
	}

	public Long getDelayCompenRate() {
		return delayCompenRate;
	}

	public void setDelayCompenRate(Long delayCompenRate) {
		this.delayCompenRate = delayCompenRate;
	}

	public String getCompltYmd() {
		return compltYmd;
	}

	public void setCompltYmd(String compltYmd) {
		this.compltYmd = compltYmd;
	}

	public String getCtrtRsn() {
		return ctrtRsn;
	}

	public void setCtrtRsn(String ctrtRsn) {
		this.ctrtRsn = ctrtRsn;
	}

	public String getCollabCtrtFg() {
		return collabCtrtFg;
	}

	public void setCollabCtrtFg(String collabCtrtFg) {
		this.collabCtrtFg = collabCtrtFg;
	}

	public Long getLiabBundAmt() {
		return liabBundAmt;
	}

	public void setLiabBundAmt(Long liabBundAmt) {
		this.liabBundAmt = liabBundAmt;
	}

	public String getBizCont() {
		return bizCont;
	}

	public void setBizCont(String bizCont) {
		this.bizCont = bizCont;
	}

	public String getLocatNm() {
		return locatNm;
	}

	public void setLocatNm(String locatNm) {
		this.locatNm = locatNm;
	}

	public String getRouestYn() {
		return rouestYn;
	}

	public void setRouestYn(String rouestYn) {
		this.rouestYn = rouestYn;
	}

	public String getSplyFg() {
		return splyFg;
	}

	public void setSplyFg(String splyFg) {
		this.splyFg = splyFg;
	}

	public String getSplyMngNo() {
		return splyMngNo;
	}

	public void setSplyMngNo(String splyMngNo) {
		this.splyMngNo = splyMngNo;
	}

	public Long getBeforeSummaryCtrtAmt() {
		return beforeSummaryCtrtAmt;
	}

	public void setBeforeSummaryCtrtAmt(Long beforeSummaryCtrtAmt) {
		this.beforeSummaryCtrtAmt = beforeSummaryCtrtAmt;
	}

	public Long getCauseActAmt() {
		return causeActAmt;
	}

	public void setCauseActAmt(Long causeActAmt) {
		this.causeActAmt = causeActAmt;
	}
	
}
