package kr.co.gitt.kworks.contact.eais.model;

/////////////////////////////////////////////
/// @class DjrChangItem
/// kr.co.gitt.kworks.cntc.eais.model \n
///   ㄴ DjrChangItem.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)Gitt |    
///    | Author | Gitt_JSH |
///    | Date | 2021. 01. 04. 오후 4:54:23 |
///    | Class Version | v1.0 |
///    | 작업자 | Gitt_JSH, Others... |
/// @section 상세설명
/// - 이 클래스는 건축물대장 변동 사항 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class DjrChangItem {
	
	//변동사항 PK
	private Integer changItemPk;
	
	//건축물대장 PK
	private Integer bldrgstPk;
	
	//변동 원인 코드
	private String changCausCd;
	
	//변동 원인 코드명
	private String changCausCdNm;
	
	//변동 내역
	private String changDetl;
	
	//변동일
	private String changDay;
	
	//정리일
	private String adjstDay;
	
	public Integer getChangItemPk() {
		return changItemPk;
	}
	public void setChangItemPk(Integer changItemPk) {
		this.changItemPk = changItemPk;
	}
	public Integer getBldrgstPk() {
		return bldrgstPk;
	}
	public void setBldrgstPk(Integer bldrgstPk) {
		this.bldrgstPk = bldrgstPk;
	}
	public String getChangCausCd() {
		return changCausCd;
	}
	public void setChangCausCd(String changCausCd) {
		this.changCausCd = changCausCd;
	}
	public String getChangDetl() {
		return changDetl;
	}
	public void setChangDetl(String changDetl) {
		this.changDetl = changDetl;
	}
	public String getChangDay() {
		return changDay;
	}
	public void setChangDay(String changDay) {
		this.changDay = changDay;
	}
	public String getAdjstDay() {
		return adjstDay;
	}
	public void setAdjstDay(String adjstDay) {
		this.adjstDay = adjstDay;
	}
	public String getChangCausCdNm() {
		return changCausCdNm;
	}
	public void setChangCausCdNm(String changCausCdNm) {
		this.changCausCdNm = changCausCdNm;
	}
}
