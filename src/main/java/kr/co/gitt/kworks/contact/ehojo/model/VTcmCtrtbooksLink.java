package kr.co.gitt.kworks.contact.ehojo.model;

import java.sql.Date;

/////////////////////////////////////////////
/// @class VTcmCtrtbooksLink
/// kr.co.gitt.kworks.contact.ehojo.model \n
///   ㄴ VTcmCtrtbooksLink.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 2. 7. 오후 5:05:14 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 계약대장상세 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class VTcmCtrtbooksLink {
	
	/// 계약대장관리번호
	private String ctrtAcctBookMngNo;
	
	/// 회계년도
	private String fisYear;
	
	/// 경비구분 
	private String expFg;
	
	/// 조달구분
	private String splyFg;
	
	/// 계약종류
	private String ctrtKind;
	
	/// 계약방법
	private String ctrtMeth;
	
	/// 계약방법상세
	private String ctrtMethDetl;
	
	/// 계약유형
	private String ctrtType;
	
	/// 신규/장기구분
	private String newLongPrdFg;
	
	/// 공동계약구분
	private String collabCtrtFg;
	
	/// 계약명
	private String ctrtNm;
	
	/// 계약적요
	private String ctrtOutline;
	
	/// 위치명
	private String locatNm;
	
	/// 조달수수료
	private Long splyCmmsnFee;
	
	/// 최초총괄계약금액
	private Long firstSummaryCtrtAmt;
	
	/// 총괄계약합계금액
	private Long summaryCtrtTotAmt;
	
	/// 총괄계약일자
	private String firstCtrtYmd;
	
	/// 착공일자
	private String startWorkYmd;
	
	/// 준공일자
	private String compltYmd;
	
	/// 준공예정일자
	private String compltSchdYmd;
	
	/// 개산여부
	private String rouestYn;
	
	/// 채무부담액
	private Long liabBundAmt;
	
	/// 절대공기
	private Long firstContPrd;
	
	/// 지체상금율
	private Long delayCompenRate;
	
	/// 부서코드
	private String deptCd;
	
	/// (수의)계약사유
	private String ctrtRsn;
	
	/// 수정일시
	private Date modiDate;

	/// 기지출액
	private Long beforeSummaryCtrtAmt;
	
	/// 조달관리번호
	private String splyMngNo;
	
	/// 계약사업목록
	private VTcmBizsummary vTcmBizsummary;
	
	/// 계약사업목록상세
	private VTcmSummarylist vTcmSummarylist;

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

	public String getExpFg() {
		return expFg;
	}

	public void setExpFg(String expFg) {
		this.expFg = expFg;
	}

	public String getSplyFg() {
		return splyFg;
	}

	public void setSplyFg(String splyFg) {
		this.splyFg = splyFg;
	}

	public String getCtrtKind() {
		return ctrtKind;
	}

	public void setCtrtKind(String ctrtKind) {
		this.ctrtKind = ctrtKind;
	}

	public String getCtrtMeth() {
		return ctrtMeth;
	}

	public void setCtrtMeth(String ctrtMeth) {
		this.ctrtMeth = ctrtMeth;
	}

	public String getCtrtMethDetl() {
		return ctrtMethDetl;
	}

	public void setCtrtMethDetl(String ctrtMethDetl) {
		this.ctrtMethDetl = ctrtMethDetl;
	}

	public String getCtrtType() {
		return ctrtType;
	}

	public void setCtrtType(String ctrtType) {
		this.ctrtType = ctrtType;
	}

	public String getNewLongPrdFg() {
		return newLongPrdFg;
	}

	public void setNewLongPrdFg(String newLongPrdFg) {
		this.newLongPrdFg = newLongPrdFg;
	}

	public String getCollabCtrtFg() {
		return collabCtrtFg;
	}

	public void setCollabCtrtFg(String collabCtrtFg) {
		this.collabCtrtFg = collabCtrtFg;
	}

	public String getCtrtNm() {
		return ctrtNm;
	}

	public void setCtrtNm(String ctrtNm) {
		this.ctrtNm = ctrtNm;
	}

	public String getCtrtOutline() {
		return ctrtOutline;
	}

	public void setCtrtOutline(String ctrtOutline) {
		this.ctrtOutline = ctrtOutline;
	}

	public String getLocatNm() {
		return locatNm;
	}

	public void setLocatNm(String locatNm) {
		this.locatNm = locatNm;
	}

	public Long getSplyCmmsnFee() {
		return splyCmmsnFee;
	}

	public void setSplyCmmsnFee(Long splyCmmsnFee) {
		this.splyCmmsnFee = splyCmmsnFee;
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

	public String getFirstCtrtYmd() {
		return firstCtrtYmd;
	}

	public void setFirstCtrtYmd(String firstCtrtYmd) {
		this.firstCtrtYmd = firstCtrtYmd;
	}

	public String getStartWorkYmd() {
		return startWorkYmd;
	}

	public void setStartWorkYmd(String startWorkYmd) {
		this.startWorkYmd = startWorkYmd;
	}

	public String getCompltYmd() {
		return compltYmd;
	}

	public void setCompltYmd(String compltYmd) {
		this.compltYmd = compltYmd;
	}

	public String getCompltSchdYmd() {
		return compltSchdYmd;
	}

	public void setCompltSchdYmd(String compltSchdYmd) {
		this.compltSchdYmd = compltSchdYmd;
	}

	public String getRouestYn() {
		return rouestYn;
	}

	public void setRouestYn(String rouestYn) {
		this.rouestYn = rouestYn;
	}

	public Long getLiabBundAmt() {
		return liabBundAmt;
	}

	public void setLiabBundAmt(Long liabBundAmt) {
		this.liabBundAmt = liabBundAmt;
	}

	public Long getFirstContPrd() {
		return firstContPrd;
	}

	public void setFirstContPrd(Long firstContPrd) {
		this.firstContPrd = firstContPrd;
	}

	public Long getDelayCompenRate() {
		return delayCompenRate;
	}

	public void setDelayCompenRate(Long delayCompenRate) {
		this.delayCompenRate = delayCompenRate;
	}

	public String getDeptCd() {
		return deptCd;
	}

	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}

	public String getCtrtRsn() {
		return ctrtRsn;
	}

	public void setCtrtRsn(String ctrtRsn) {
		this.ctrtRsn = ctrtRsn;
	}

	public Date getModiDate() {
		return modiDate;
	}

	public void setModiDate(Date modiDate) {
		this.modiDate = modiDate;
	}

	public Long getBeforeSummaryCtrtAmt() {
		return beforeSummaryCtrtAmt;
	}

	public void setBeforeSummaryCtrtAmt(Long beforeSummaryCtrtAmt) {
		this.beforeSummaryCtrtAmt = beforeSummaryCtrtAmt;
	}

	public String getSplyMngNo() {
		return splyMngNo;
	}

	public void setSplyMngNo(String splyMngNo) {
		this.splyMngNo = splyMngNo;
	}

	public VTcmBizsummary getvTcmBizsummary() {
		return vTcmBizsummary;
	}

	public void setvTcmBizsummary(VTcmBizsummary vTcmBizsummary) {
		this.vTcmBizsummary = vTcmBizsummary;
	}

	public VTcmSummarylist getvTcmSummarylist() {
		return vTcmSummarylist;
	}

	public void setvTcmSummarylist(VTcmSummarylist vTcmSummarylist) {
		this.vTcmSummarylist = vTcmSummarylist;
	}

}
