package kr.co.gitt.kworks.contact.eais.model;

/////////////////////////////////////////////
/// @class DjrFlrouln
/// kr.co.gitt.kworks.cntc.eais.model \n
///   ㄴ DjrFlrouln.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)Gitt |    
///    | Author | Gitt_JSH |
///    | Date | 2021. 01. 04. 오후 4:54:23 |
///    | Class Version | v1.0 |
///    | 작업자 | Gitt_JSH, Others... |
/// @section 상세설명
/// - 이 클래스는 건축물대장 층별 개요 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class DjrFlrouln {
	
	//층별 개요 PK
	private Integer flrOulnPk;
	
	//건축물대장 PK
	private Integer bldrgstPk;
	
	//층 구분 코드
	private String flrGbCd;
	
	//층 구분 코드명
	private String flrGbCdNm;
	
	//층 번호
	private Integer flrNo;
	
	//층 번호 명
	private String flrNoNm;
	
	//구조 코드
	private String strctCd;
	
	//구조 코드명
	private String strctCdNm;
	
	//기타 구조
	private String etcStrct;
	
	//주 용도 코드
	private String mainPurpsCd;
	
	//주 용도 코드명
	private String mainPurpsCdNm;
	
	//기타 용도
	private String etcPurps;
	
	//면적
	private Double area;
	
	//주부속 구분 코드
	private String mainAtchGbCd;
	
	//주부속 구분 코드명
	private String mainAtchGbCdNm;
	
	//주부속 일련번호
	private Integer mainAtchSeqno;
	
	//주건축물대장_PK
	private Integer mainBldrgstPk;
	
	//면적 제외 여부
	private String areaExctYn;
	
	public Integer getFlrOulnPk() {
		return flrOulnPk;
	}
	public void setFlrOulnPk(Integer flrOulnPk) {
		this.flrOulnPk = flrOulnPk;
	}
	public Integer getBldrgstPk() {
		return bldrgstPk;
	}
	public void setBldrgstPk(Integer bldrgstPk) {
		this.bldrgstPk = bldrgstPk;
	}
	public String getFlrGbCd() {
		return flrGbCd;
	}
	public void setFlrGbCd(String flrGbCd) {
		this.flrGbCd = flrGbCd;
	}
	public Integer getFlrNo() {
		return flrNo;
	}
	public void setFlrNo(Integer flrNo) {
		this.flrNo = flrNo;
	}
	public String getFlrNoNm() {
		return flrNoNm;
	}
	public void setFlrNoNm(String flrNoNm) {
		this.flrNoNm = flrNoNm;
	}
	public String getStrctCd() {
		return strctCd;
	}
	public void setStrctCd(String strctCd) {
		this.strctCd = strctCd;
	}
	public String getEtcStrct() {
		return etcStrct;
	}
	public void setEtcStrct(String etcStrct) {
		this.etcStrct = etcStrct;
	}
	public String getMainPurpsCd() {
		return mainPurpsCd;
	}
	public void setMainPurpsCd(String mainPurpsCd) {
		this.mainPurpsCd = mainPurpsCd;
	}
	public String getEtcPurps() {
		return etcPurps;
	}
	public void setEtcPurps(String etcPurps) {
		this.etcPurps = etcPurps;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public String getMainAtchGbCd() {
		return mainAtchGbCd;
	}
	public void setMainAtchGbCd(String mainAtchGbCd) {
		this.mainAtchGbCd = mainAtchGbCd;
	}
	public Integer getMainAtchSeqno() {
		return mainAtchSeqno;
	}
	public void setMainAtchSeqno(Integer mainAtchSeqno) {
		this.mainAtchSeqno = mainAtchSeqno;
	}
	public Integer getMainBldrgstPk() {
		return mainBldrgstPk;
	}
	public void setMainBldrgstPk(Integer mainBldrgstPk) {
		this.mainBldrgstPk = mainBldrgstPk;
	}
	public String getAreaExctYn() {
		return areaExctYn;
	}
	public void setAreaExctYn(String areaExctYn) {
		this.areaExctYn = areaExctYn;
	}
	public String getFlrGbCdNm() {
		return flrGbCdNm;
	}
	public void setFlrGbCdNm(String flrGbCdNm) {
		this.flrGbCdNm = flrGbCdNm;
	}
	public String getStrctCdNm() {
		return strctCdNm;
	}
	public void setStrctCdNm(String strctCdNm) {
		this.strctCdNm = strctCdNm;
	}
	public String getMainPurpsCdNm() {
		return mainPurpsCdNm;
	}
	public void setMainPurpsCdNm(String mainPurpsCdNm) {
		this.mainPurpsCdNm = mainPurpsCdNm;
	}
	public String getMainAtchGbCdNm() {
		return mainAtchGbCdNm;
	}
	public void setMainAtchGbCdNm(String mainAtchGbCdNm) {
		this.mainAtchGbCdNm = mainAtchGbCdNm;
	}
}
