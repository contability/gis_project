package kr.co.gitt.kworks.contact.ehojo.model;

/////////////////////////////////////////////
/// @class VTcmBizsummary
/// kr.co.gitt.kworks.contact.ehojo.model \n
///   ㄴ VTcmBizsummary.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 2. 7. 오후 5:01:20 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 계약대장 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class VTcmBizsummary {

	/// 계약번호
	private String ctrtAcctBookMngNo;
	
	/// 계약명
	private String ctrtNm;
	
	/// 사업개요
	private String bizCont;

	/// 예정가격
	private Long schdAmt;

	/// 낙찰금액
	private Long succAmt;
	
	/// 당초(최초)계약금액
	private Long firstCtrtAmt;
	
	/// 최종계약금액
	private Long ctrtAmt;
	
	/// 계약율
	private Long ctrtRate;
	
	/// 계약연도
	private String ctrtYear;
	
	/// 계약일자
	private String ctrtYmd;
	
	/// 착공일자
	private String startWorkYmd;
	
	/// 준공예정일자
	private String compltSchdYmd;
	
	/// 준공일자
	private String compltYmd;
	
	/// 주계약자명
	private String custNm;
	
	/// (수의)계약사유
	private String ctrtRsn;
	
	/// 계약유형코드
	private String ctrtTypeCd;
	
	/// 계약유형명
	private String ctrtTypeNm;
	
	/// 총차계약여부
	private String chasuYn;
	
	/// 총차계약번호
	private String firstChasuNo;
	
	/// 차수번호
	private String chasuNo;
	
	/// 계약방법코드
	private String ctrtMethCd;
	
	/// 계약방법
	private String ctrtMethNm;
	
	/// 계약구분코드
	private String ctrtFgCd;
	
	/// 계약구분명
	private String ctrtFgNm;

	public String getCtrtAcctBookMngNo() {
		return ctrtAcctBookMngNo;
	}

	public void setCtrtAcctBookMngNo(String ctrtAcctBookMngNo) {
		this.ctrtAcctBookMngNo = ctrtAcctBookMngNo;
	}

	public String getCtrtNm() {
		return ctrtNm;
	}

	public void setCtrtNm(String ctrtNm) {
		this.ctrtNm = ctrtNm;
	}

	public String getBizCont() {
		return bizCont;
	}

	public void setBizCont(String bizCont) {
		this.bizCont = bizCont;
	}

	public Long getSchdAmt() {
		return schdAmt;
	}

	public void setSchdAmt(Long schdAmt) {
		this.schdAmt = schdAmt;
	}

	public Long getSuccAmt() {
		return succAmt;
	}

	public void setSuccAmt(Long succAmt) {
		this.succAmt = succAmt;
	}

	public Long getFirstCtrtAmt() {
		return firstCtrtAmt;
	}

	public void setFirstCtrtAmt(Long firstCtrtAmt) {
		this.firstCtrtAmt = firstCtrtAmt;
	}

	public Long getCtrtAmt() {
		return ctrtAmt;
	}

	public void setCtrtAmt(Long ctrtAmt) {
		this.ctrtAmt = ctrtAmt;
	}

	public Long getCtrtRate() {
		return ctrtRate;
	}

	public void setCtrtRate(Long ctrtRate) {
		this.ctrtRate = ctrtRate;
	}

	public String getCtrtYear() {
		return ctrtYear;
	}

	public void setCtrtYear(String ctrtYear) {
		this.ctrtYear = ctrtYear;
	}

	public String getCtrtYmd() {
		return ctrtYmd;
	}

	public void setCtrtYmd(String ctrtYmd) {
		this.ctrtYmd = ctrtYmd;
	}

	public String getStartWorkYmd() {
		return startWorkYmd;
	}

	public void setStartWorkYmd(String startWorkYmd) {
		this.startWorkYmd = startWorkYmd;
	}

	public String getCompltSchdYmd() {
		return compltSchdYmd;
	}

	public void setCompltSchdYmd(String compltSchdYmd) {
		this.compltSchdYmd = compltSchdYmd;
	}

	public String getCompltYmd() {
		return compltYmd;
	}

	public void setCompltYmd(String compltYmd) {
		this.compltYmd = compltYmd;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}

	public String getCtrtRsn() {
		return ctrtRsn;
	}

	public void setCtrtRsn(String ctrtRsn) {
		this.ctrtRsn = ctrtRsn;
	}

	public String getCtrtTypeCd() {
		return ctrtTypeCd;
	}

	public void setCtrtTypeCd(String ctrtTypeCd) {
		this.ctrtTypeCd = ctrtTypeCd;
	}

	public String getCtrtTypeNm() {
		return ctrtTypeNm;
	}

	public void setCtrtTypeNm(String ctrtTypeNm) {
		this.ctrtTypeNm = ctrtTypeNm;
	}

	public String getChasuYn() {
		return chasuYn;
	}

	public void setChasuYn(String chasuYn) {
		this.chasuYn = chasuYn;
	}

	public String getFirstChasuNo() {
		return firstChasuNo;
	}

	public void setFirstChasuNo(String firstChasuNo) {
		this.firstChasuNo = firstChasuNo;
	}

	public String getChasuNo() {
		return chasuNo;
	}

	public void setChasuNo(String chasuNo) {
		this.chasuNo = chasuNo;
	}

	public String getCtrtMethCd() {
		return ctrtMethCd;
	}

	public void setCtrtMethCd(String ctrtMethCd) {
		this.ctrtMethCd = ctrtMethCd;
	}

	public String getCtrtMethNm() {
		return ctrtMethNm;
	}

	public void setCtrtMethNm(String ctrtMethNm) {
		this.ctrtMethNm = ctrtMethNm;
	}

	public String getCtrtFgCd() {
		return ctrtFgCd;
	}

	public void setCtrtFgCd(String ctrtFgCd) {
		this.ctrtFgCd = ctrtFgCd;
	}

	public String getCtrtFgNm() {
		return ctrtFgNm;
	}

	public void setCtrtFgNm(String ctrtFgNm) {
		this.ctrtFgNm = ctrtFgNm;
	}

}
