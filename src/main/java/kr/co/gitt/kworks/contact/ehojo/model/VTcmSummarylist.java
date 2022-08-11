package kr.co.gitt.kworks.contact.ehojo.model;

/////////////////////////////////////////////
/// @class VTcmSummarylist
/// kr.co.gitt.kworks.model \n
///   ㄴ VTcmSummarylist.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2016. 11. 29. 오후 3:42:52 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 계약사업목록 모델 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public class VTcmSummarylist {
	
	/// 계약일자
	private String ctrtYear;
	
	/// 계약관서코드
	private String ctrtGovCd;
		
	/// 계약관서명
	private String ctrtGovNm;
	
	/// 계약관리번호
	private String ctrtAcctBookMngNo;
	
	/// 계약명
	private String ctrtNm;
	
	/// 계약금액
	private Long ctrtAmt;
	
	/// 계약시작일
	private String ctrtStartYmd;
	
	/// 계약종료일
	private String ctrtEndYmd;
	
	/// 업체명
	private String custNm;
	
	/// 계약방법코드
	private String ctrtMethCd;
	
	/// 계약방법
	private String ctrtMeth;
	
	/// 계약종류코드
	private String ctrtKindCd;
	
	/// 계약종류
	private String ctrtKind;
	
	/// 재배정계약_여부
	private String allotedYn;
	
	/// 수의계약여부
	private String optionalYn;
	
	/// 총차계약여부
	private String chasuYn;
	
	/// 총차계약번호
	private String firstChasuNo;
	
	/// 차수번호
	private String chasuNo;
	
	/// 총괄계약일자
	private String firstCtrtYmd;
	
	/// 계약목록상세
	private VTcmBizsummary vTcmBizsummary;
	
	/// 계약대장
	private VTcmCtrtbooksLink vTcmCtrtbooksLink;
	
	public String getCtrtYear() {
		return ctrtYear;
	}
	public void setCtrtYear(String ctrtYear) {
		this.ctrtYear = ctrtYear;
	}
	public String getCtrtGovCd() {
		return ctrtGovCd;
	}
	public void setCtrtGovCd(String ctrtGovCd) {
		this.ctrtGovCd = ctrtGovCd;
	}
	public String getCtrtGovNm() {
		return ctrtGovNm;
	}
	public void setCtrtGovNm(String ctrtGovNm) {
		this.ctrtGovNm = ctrtGovNm;
	}
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
	public Long getCtrtAmt() {
		return ctrtAmt;
	}
	public void setCtrtAmt(Long ctrtAmt) {
		this.ctrtAmt = ctrtAmt;
	}
	public String getCtrtStartYmd() {
		return ctrtStartYmd;
	}
	public void setCtrtStartYmd(String ctrtStartYmd) {
		this.ctrtStartYmd = ctrtStartYmd;
	}
	public String getCtrtEndYmd() {
		return ctrtEndYmd;
	}
	public void setCtrtEndYmd(String ctrtEndYmd) {
		this.ctrtEndYmd = ctrtEndYmd;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getCtrtMethCd() {
		return ctrtMethCd;
	}
	public void setCtrtMethCd(String ctrtMethCd) {
		this.ctrtMethCd = ctrtMethCd;
	}
	public String getCtrtMeth() {
		return ctrtMeth;
	}
	public void setCtrtMeth(String ctrtMeth) {
		this.ctrtMeth = ctrtMeth;
	}
	public String getCtrtKindCd() {
		return ctrtKindCd;
	}
	public void setCtrtKindCd(String ctrtKindCd) {
		this.ctrtKindCd = ctrtKindCd;
	}
	public String getCtrtKind() {
		return ctrtKind;
	}
	public void setCtrtKind(String ctrtKind) {
		this.ctrtKind = ctrtKind;
	}
	public String getAllotedYn() {
		return allotedYn;
	}
	public void setAllotedYn(String allotedYn) {
		this.allotedYn = allotedYn;
	}
	public String getOptionalYn() {
		return optionalYn;
	}
	public void setOptionalYn(String optionalYn) {
		this.optionalYn = optionalYn;
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
	public String getFirstCtrtYmd() {
		return firstCtrtYmd;
	}
	public void setFirstCtrtYmd(String firstCtrtYmd) {
		this.firstCtrtYmd = firstCtrtYmd;
	}
	public VTcmBizsummary getvTcmBizsummary() {
		return vTcmBizsummary;
	}
	public void setvTcmBizsummary(VTcmBizsummary vTcmBizsummary) {
		this.vTcmBizsummary = vTcmBizsummary;
	}
	public VTcmCtrtbooksLink getvTcmCtrtbooksLink() {
		return vTcmCtrtbooksLink;
	}
	public void setvTcmCtrtbooksLink(VTcmCtrtbooksLink vTcmCtrtbooksLink) {
		this.vTcmCtrtbooksLink = vTcmCtrtbooksLink;
	}

}
