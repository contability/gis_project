package kr.co.gitt.kworks.contact.kras.model;

import javax.xml.bind.annotation.XmlElement;

/////////////////////////////////////////////
/// @class FloorInfo
/// kworks.itf.vo \n
///   ㄴ FloorInfo.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 5:06:46 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 층별현황 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class FloorInfo {

	/// 층
	private String flr;
	
	/// 기타구조
	private String etcStru;
	
	/// 주용도명
	private String mainUseNm;
	
	/// 기타용도
	private String etcUse;
	
	/// 면적
	private Double btmArea;
	
	/// 층구분코드
	private String flrGbnCd;
	
	/// 주부속구분한글
	private String mainSubGbnNm;
	
	/// 주부속일련번호
	private Integer mainSubSeqno;
	
	@XmlElement(name = "FLR")
	public String getFlr() {
		return flr;
	}

	public void setFlr(String flr) {
		this.flr = flr;
	}

	@XmlElement(name = "ETC_STRU")
	public String getEtcStru() {
		return etcStru;
	}

	public void setEtcStru(String etcStru) {
		this.etcStru = etcStru;
	}

	@XmlElement(name = "MAIN_USE_NM")
	public String getMainUseNm() {
		return mainUseNm;
	}

	public void setMainUseNm(String mainUseNm) {
		this.mainUseNm = mainUseNm;
	}

	@XmlElement(name = "ETC_USE")
	public String getEtcUse() {
		return etcUse;
	}

	public void setEtcUse(String etcUse) {
		this.etcUse = etcUse;
	}

	@XmlElement(name = "BTM_AREA")
	public Double getBtmArea() {
		return btmArea;
	}

	public void setBtmArea(Double btmArea) {
		this.btmArea = btmArea;
	}

	@XmlElement(name = "FLR_GBN_CD")
	public String getFlrGbnCd() {
		return flrGbnCd;
	}

	public void setFlrGbnCd(String flrGbnCd) {
		this.flrGbnCd = flrGbnCd;
	}

	@XmlElement(name = "MAIN_SUB_GBN_NM")
	public String getMainSubGbnNm() {
		return mainSubGbnNm;
	}

	public void setMainSubGbnNm(String mainSubGbnNm) {
		this.mainSubGbnNm = mainSubGbnNm;
	}

	@XmlElement(name = "MAIN_SUB_SEQNO")
	public Integer getMainSubSeqno() {
		return mainSubSeqno;
	}

	public void setMainSubSeqno(Integer mainSubSeqno) {
		this.mainSubSeqno = mainSubSeqno;
	}
	
}
