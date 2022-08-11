package kr.co.gitt.kworks.contact.kras.model;

import javax.xml.bind.annotation.XmlElement;

/////////////////////////////////////////////
/// @class BldgDongInfo
/// kworks.itf.vo \n
///   ㄴ BldgDongInfo.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 4:56:38 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 '건물 동 목록' 서비스 응답 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class BldgDongInfo {
	
	/// 행정구역코드
	private String admSectCd;
	
	/// 소재지코드
	private String landLocCd;
	
	/// 대장구분
	private String ledgGbn;
	
	/// 본번
	private String bobn;
	
	/// 부번 
	private String bubn;
	
	/// 건물종류명
	private String bldgKindNm;
	
	/// 건물종류코드
	private String bldgKindCd;
	
	/// 건물구분번호
	private String bldgGbnNo;
	
	/// 건물명
	private String bldgNm;
	
	/// 동명
	private String dongNm;
	
	/// 건축물 현황도 여부
	private String bmapYn;
	
	/// 연면적
	private String garea;

	@XmlElement(name = "ADM_SECT_CD")
	public String getAdmSectCd() {
		return admSectCd;
	}

	public void setAdmSectCd(String admSectCd) {
		this.admSectCd = admSectCd;
	}

	@XmlElement(name = "LAND_LOC_CD")
	public String getLandLocCd() {
		return landLocCd;
	}

	public void setLandLocCd(String landLocCd) {
		this.landLocCd = landLocCd;
	}
	
	@XmlElement(name = "LEDG_GBN")
	public String getLedgGbn() {
		return ledgGbn;
	}

	public void setLedgGbn(String ledgGbn) {
		this.ledgGbn = ledgGbn;
	}

	@XmlElement(name = "BOBN")
	public String getBobn() {
		return bobn;
	}

	public void setBobn(String bobn) {
		this.bobn = bobn;
	}

	@XmlElement(name = "BUBN")
	public String getBubn() {
		return bubn;
	}

	public void setBubn(String bubn) {
		this.bubn = bubn;
	}

	@XmlElement(name = "BLDG_KIND_NM")
	public String getBldgKindNm() {
		return bldgKindNm;
	}

	public void setBldgKindNm(String bldgKindNm) {
		this.bldgKindNm = bldgKindNm;
	}

	@XmlElement(name = "BLDG_KIND_CD")
	public String getBldgKindCd() {
		return bldgKindCd;
	}

	public void setBldgKindCd(String bldgKindCd) {
		this.bldgKindCd = bldgKindCd;
	}

	@XmlElement(name = "BLDG_GBN_NO")
	public String getBldgGbnNo() {
		return bldgGbnNo;
	}

	public void setBldgGbnNo(String bldgGbnNo) {
		this.bldgGbnNo = bldgGbnNo;
	}

	@XmlElement(name = "BLDG_NM")
	public String getBldgNm() {
		return bldgNm;
	}

	public void setBldgNm(String bldgNm) {
		this.bldgNm = bldgNm;
	}

	@XmlElement(name = "DONG_NM")
	public String getDongNm() {
		return dongNm;
	}

	public void setDongNm(String dongNm) {
		this.dongNm = dongNm;
	}

	@XmlElement(name = "BMAP_YN")
	public String getBmapYn() {
		return bmapYn;
	}

	public void setBmapYn(String bmapYn) {
		this.bmapYn = bmapYn;
	}

	@XmlElement(name = "GAREA")
	public String getGarea() {
		return garea;
	}

	public void setGarea(String garea) {
		this.garea = garea;
	}

}
