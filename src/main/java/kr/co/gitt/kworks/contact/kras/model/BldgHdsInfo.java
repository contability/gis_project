package kr.co.gitt.kworks.contact.kras.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/////////////////////////////////////////////
/// @class BldgHdsInfo
/// kworks.itf.vo \n
///   ㄴ BldgHdsInfo.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 5:06:13 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 '일반건축물' 또는 '집합건축물(표제부)' 정보 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class BldgHdsInfo {
	
	/// 건물식별번호
	private String bldgGbnNo;
	
	/// 특수지명
	private String spcNm;
	
	/// 블록
	private String block;
	
	/// 로트
	private String lot;

	/// 건물명
	private String bldgNm;

	/// 보완구분
	private String cmplGbn;

	/// 양성화여부
	private String legaYn;

	/// 위반건축물여부
	private String vioBldgYn;

	/// 외필지수
	private Integer landCnt;

	/// 기타기재사항
	private String etcWrtItem;

	/// 동
	private String dong;

	/// 주부속구분명
	private String mainSubGbnNm;

	/// 주부속일련번호
	private Integer mainSubSeqno;

	/// 구조명
	private String struNm;

	/// 기타구조
	private String etcStru;

	/// 지붕명
	private String roofNm;

	/// 기타지붕
	private String etcRoof;

	/// 대지면적
	private Double larea;

	/// 가구수
	private Integer fmlyCnt;

	/// 세대수
	private Integer  headCnt;

	/// 호수
	private Integer hoCnt;

	/// 건축면적
	private Double barea;

	/// 주용도명
	private String mainUseNm;

	/// 기타용도
	private String etcUse;

	/// 면적
	private Double garea;

	/// 총괄동연면적
	private Double totDongGarea;

	/// 동건폐율	
	private Double dongBlr;

	/// 용적율산정연면적
	private Double fsiCalcGarea;

	/// 용적율
	private Double fsi;

	/// 높이
	private Double hgt;

	/// 총부속건축물수
	private Integer subBldgCnt;

	/// 총부속건축물면적
	private Double subBldgArea;

	/// 건물종류코드
	private String bldgKindCd;
	
	/// 층별현황
	private List<FloorInfo> floorInfoList;
	
	/// 변동사항
	private List<ChgData> chgDataList;
	
	@XmlElement(name = "BLDG_GBN_NO")
	public String getBldgGbnNo() {
		return bldgGbnNo;
	}

	public void setBldgGbnNo(String bldgGbnNo) {
		this.bldgGbnNo = bldgGbnNo;
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

	@XmlElement(name = "BLDG_NM")
	public String getBldgNm() {
		return bldgNm;
	}

	public void setBldgNm(String bldgNm) {
		this.bldgNm = bldgNm;
	}

	@XmlElement(name = "CMPL_GBN")
	public String getCmplGbn() {
		return cmplGbn;
	}

	public void setCmplGbn(String cmplGbn) {
		this.cmplGbn = cmplGbn;
	}

	@XmlElement(name = "LEGA_YN")
	public String getLegaYn() {
		return legaYn;
	}

	public void setLegaYn(String legaYn) {
		this.legaYn = legaYn;
	}

	@XmlElement(name = "VIO_BLDG_YN")
	public String getVioBldgYn() {
		return vioBldgYn;
	}

	public void setVioBldgYn(String vioBldgYn) {
		this.vioBldgYn = vioBldgYn;
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

	@XmlElement(name = "DONG")
	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
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

	@XmlElement(name = "STRU_NM")
	public String getStruNm() {
		return struNm;
	}

	public void setStruNm(String struNm) {
		this.struNm = struNm;
	}

	@XmlElement(name = "ETC_STRU")
	public String getEtcStru() {
		return etcStru;
	}

	public void setEtcStru(String etcStru) {
		this.etcStru = etcStru;
	}

	@XmlElement(name = "ROOF_NM")
	public String getRoofNm() {
		return roofNm;
	}

	public void setRoofNm(String roofNm) {
		this.roofNm = roofNm;
	}

	@XmlElement(name = "ETC_ROOF")
	public String getEtcRoof() {
		return etcRoof;
	}

	public void setEtcRoof(String etcRoof) {
		this.etcRoof = etcRoof;
	}

	@XmlElement(name = "LAREA")
	public Double getLarea() {
		return larea;
	}

	public void setLarea(Double larea) {
		this.larea = larea;
	}

	@XmlElement(name = "FMLY_CNT")
	public Integer getFmlyCnt() {
		return fmlyCnt;
	}

	public void setFmlyCnt(Integer fmlyCnt) {
		this.fmlyCnt = fmlyCnt;
	}

	@XmlElement(name = "HEAD_CNT")
	public Integer getHeadCnt() {
		return headCnt;
	}

	public void setHeadCnt(Integer headCnt) {
		this.headCnt = headCnt;
	}

	@XmlElement(name = "HO_CNT")
	public Integer getHoCnt() {
		return hoCnt;
	}

	public void setHoCnt(Integer hoCnt) {
		this.hoCnt = hoCnt;
	}

	@XmlElement(name = "BAREA")
	public Double getBarea() {
		return barea;
	}

	public void setBarea(Double barea) {
		this.barea = barea;
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

	@XmlElement(name = "GAREA")
	public Double getGarea() {
		return garea;
	}

	public void setGarea(Double garea) {
		this.garea = garea;
	}

	@XmlElement(name = "TOT_DONG_GAREA")
	public Double getTotDongGarea() {
		return totDongGarea;
	}

	public void setTotDongGarea(Double totDongGarea) {
		this.totDongGarea = totDongGarea;
	}

	@XmlElement(name = "DONG_BLR")
	public Double getDongBlr() {
		return dongBlr;
	}

	public void setDongBlr(Double dongBlr) {
		this.dongBlr = dongBlr;
	}

	@XmlElement(name = "FSI_CALC_GAREA")
	public Double getFsiCalcGarea() {
		return fsiCalcGarea;
	}

	public void setFsiCalcGarea(Double fsiCalcGarea) {
		this.fsiCalcGarea = fsiCalcGarea;
	}

	@XmlElement(name = "FSI")
	public Double getFsi() {
		return fsi;
	}

	public void setFsi(Double fsi) {
		this.fsi = fsi;
	}

	@XmlElement(name = "HGT")
	public Double getHgt() {
		return hgt;
	}

	public void setHgt(Double hgt) {
		this.hgt = hgt;
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

	@XmlElement(name = "BLDG_KIND_CD")
	public String getBldgKindCd() {
		return bldgKindCd;
	}

	public void setBldgKindCd(String bldgKindCd) {
		this.bldgKindCd = bldgKindCd;
	}

	@XmlElement(name = "FLOORINFOLIST")
	public List<FloorInfo> getFloorInfoList() {
		return floorInfoList;
	}

	public void setFloorInfoList(List<FloorInfo> floorInfoList) {
		this.floorInfoList = floorInfoList;
	}

	@XmlElement(name = "CHGDATALIST")
	public List<ChgData> getChgDataList() {
		return chgDataList;
	}

	public void setChgDataList(List<ChgData> chgDataList) {
		this.chgDataList = chgDataList;
	}

}
