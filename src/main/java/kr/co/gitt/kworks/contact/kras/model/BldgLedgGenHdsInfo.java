package kr.co.gitt.kworks.contact.kras.model;

import javax.xml.bind.annotation.XmlElement;

/////////////////////////////////////////////
/// @class BldgLedgGenHdsInfo
/// kworks.itf.vo \n
///   ㄴ BldgLedgGenHdsInfo.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 5:05:10 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 '건축물대장(총괄 표제부)' 서비스 응답 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class BldgLedgGenHdsInfo {
	
	/// 건물식별번호
	private String bldgGbnNo;
	
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
	
	/// 건물명
	private String bldgNm;
	
	/// 외필지수
	private Integer landCnt;
	
	/// 기타기재사항
	private String etcWrtItem;
	
	/// 양성화여부
	private String legaYn;
	
	/// 상위건물식별번호
	private Long upperBldgNo;
	
	/// 위반건축물여부
	private String vioBldgYn;
	
	/// 특수지명
	private String spcNm;
	
	/// 블록
	private String block;
	
	/// 로트
	private String lot;
	
	/// 보완구분
	private String cmplGbn;
	
	/// 특이사항
	private String spcItem;

	/// 대지면적
	private Double larea;

	/// 건축면적
	private Double barea;
	
	/// 건폐율
	private Double blr;

	/// 연면적
	private Double garea;
	
	/// 용적율
	private Double fsi;

	/// 용적율산정연면적
	private Double fsiCalcGarea;

	/// 주용도명
	private String mainUseNm;

	/// 기타용도
	private String etcUse;
	
	/// 총가구수
	private Integer totFmlyCnt;
	
	/// 총세대수
	private Integer totHehdCnt;
	
	/// 총호수
	private Integer totHoCnt;
	
	/// 총주건축물수
	private Integer totMainBldgCnt;

	/// 총부속건축물수
	private Integer subBldgCnt;

	/// 총부속건축물면적
	private Double subBldgArea;

	/// 총주차수
	private Integer totParkCnt;
	
	/// 허가일자
	private String permYmd;
	
	/// 착공일자
	private String bgconsYmd;
	
	/// 사용승인일자
	private String useAprvYmd;
	
	/// 허가번호
	private String permymdnum;

	@XmlElement(name = "BLDG_GBN_NO")
	public String getBldgGbnNo() {
		return bldgGbnNo;
	}

	public void setBldgGbnNo(String bldgGbnNo) {
		this.bldgGbnNo = bldgGbnNo;
	}

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

	@XmlElement(name = "BLDG_NM")
	public String getBldgNm() {
		return bldgNm;
	}

	public void setBldgNm(String bldgNm) {
		this.bldgNm = bldgNm;
	}

	@XmlElement(name = "LAND_CNT")
	public Integer getLandCnt() {
		return landCnt;
	}

	public void setLandCnt(Integer landCnt) {
		this.landCnt = landCnt;
	}

	@XmlElement(name = "ETC_WRT_ITEM")
	public String getEtcWrtItem() {
		return etcWrtItem;
	}

	public void setEtcWrtItem(String etcWrtItem) {
		this.etcWrtItem = etcWrtItem;
	}

	@XmlElement(name = "LEGA_YN")
	public String getLegaYn() {
		return legaYn;
	}

	public void setLegaYn(String legaYn) {
		this.legaYn = legaYn;
	}

	@XmlElement(name = "UPPER_BLDG_NO")
	public Long getUpperBldgNo() {
		return upperBldgNo;
	}

	public void setUpperBldgNo(Long upperBldgNo) {
		this.upperBldgNo = upperBldgNo;
	}

	@XmlElement(name = "VIO_BLDG_YN")
	public String getVioBldgYn() {
		return vioBldgYn;
	}

	public void setVioBldgYn(String vioBldgYn) {
		this.vioBldgYn = vioBldgYn;
	}

	@XmlElement(name = "SPC_NM")
	public String getSpcNm() {
		return spcNm;
	}

	public void setSpcNm(String spcNm) {
		this.spcNm = spcNm;
	}

	@XmlElement(name = "BLOCK")
	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	@XmlElement(name = "LOT")
	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	@XmlElement(name = "CMPL_GBN")
	public String getCmplGbn() {
		return cmplGbn;
	}

	public void setCmplGbn(String cmplGbn) {
		this.cmplGbn = cmplGbn;
	}

	@XmlElement(name = "SPC_ITEM")
	public String getSpcItem() {
		return spcItem;
	}

	public void setSpcItem(String spcItem) {
		this.spcItem = spcItem;
	}

	@XmlElement(name = "LAREA")
	public Double getLarea() {
		return larea;
	}

	public void setLarea(Double larea) {
		this.larea = larea;
	}

	@XmlElement(name = "BAREA")
	public Double getBarea() {
		return barea;
	}

	public void setBarea(Double barea) {
		this.barea = barea;
	}

	@XmlElement(name = "BLR")
	public Double getBlr() {
		return blr;
	}

	public void setBlr(Double blr) {
		this.blr = blr;
	}

	@XmlElement(name = "GAREA")
	public Double getGarea() {
		return garea;
	}

	public void setGarea(Double garea) {
		this.garea = garea;
	}

	@XmlElement(name = "FSI")
	public Double getFsi() {
		return fsi;
	}

	public void setFsi(Double fsi) {
		this.fsi = fsi;
	}

	@XmlElement(name = "FSI_CALC_GAREA")
	public Double getFsiCalcGarea() {
		return fsiCalcGarea;
	}

	public void setFsiCalcGarea(Double fsiCalcGarea) {
		this.fsiCalcGarea = fsiCalcGarea;
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

	@XmlElement(name = "TOT_FMLY_CNT")
	public Integer getTotFmlyCnt() {
		return totFmlyCnt;
	}

	public void setTotFmlyCnt(Integer totFmlyCnt) {
		this.totFmlyCnt = totFmlyCnt;
	}

	@XmlElement(name = "TOT_HEHD_CNT")
	public Integer getTotHehdCnt() {
		return totHehdCnt;
	}

	public void setTotHehdCnt(Integer totHehdCnt) {
		this.totHehdCnt = totHehdCnt;
	}

	@XmlElement(name = "TOT_HO_CNT")
	public Integer getTotHoCnt() {
		return totHoCnt;
	}

	public void setTotHoCnt(Integer totHoCnt) {
		this.totHoCnt = totHoCnt;
	}

	@XmlElement(name = "TOT_MAIN_BLDG_CNT")
	public Integer getTotMainBldgCnt() {
		return totMainBldgCnt;
	}

	public void setTotMainBldgCnt(Integer totMainBldgCnt) {
		this.totMainBldgCnt = totMainBldgCnt;
	}

	@XmlElement(name = "SUB_BLDG_CNT")
	public Integer getSubBldgCnt() {
		return subBldgCnt;
	}

	public void setSubBldgCnt(Integer subBldgCnt) {
		this.subBldgCnt = subBldgCnt;
	}

	@XmlElement(name = "SUB_BLDG_AREA")
	public Double getSubBldgArea() {
		return subBldgArea;
	}

	public void setSubBldgArea(Double subBldgArea) {
		this.subBldgArea = subBldgArea;
	}

	@XmlElement(name = "TOT_PARK_CNT")
	public Integer getTotParkCnt() {
		return totParkCnt;
	}

	public void setTotParkCnt(Integer totParkCnt) {
		this.totParkCnt = totParkCnt;
	}

	@XmlElement(name = "PERM_YMD")
	public String getPermYmd() {
		return permYmd;
	}

	public void setPermYmd(String permYmd) {
		this.permYmd = permYmd;
	}

	@XmlElement(name = "BGCONS_YMD")
	public String getBgconsYmd() {
		return bgconsYmd;
	}

	public void setBgconsYmd(String bgconsYmd) {
		this.bgconsYmd = bgconsYmd;
	}

	@XmlElement(name = "USE_APRV_YMD")
	public String getUseAprvYmd() {
		return useAprvYmd;
	}

	public void setUseAprvYmd(String useAprvYmd) {
		this.useAprvYmd = useAprvYmd;
	}

	@XmlElement(name = "PERMYMDNUM")
	public String getPermymdnum() {
		return permymdnum;
	}

	public void setPermymdnum(String permymdnum) {
		this.permymdnum = permymdnum;
	}

}
