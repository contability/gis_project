package kr.co.gitt.kworks.model;

public class RdlPcbsPs {
	
	/// GEOM
	private String geom; 
	
	///  ID
	private Integer objectid;
	
	/// 지형지물부호
	private String ftrCde;
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 행정읍/면/동
	private String hjdCde;
	
	/// 도엽번호
	private String shtNum;
	
	/// 관리기관
	private String mngCde;
	
	/// 기준점구분
	private String pcbCde;
	
	/// 기준점번호
	private String serIdn;
	
	/// 매설형태
	private String pcbDes;
	
	/// 설치일자
	private String insYmd;
	
	/// 관측일자
	private String srvYmd;
	
	/// 표고
	private Double pyoGoh;
	
	/// BESSEL_X좌표
	private Double bslXco;
	
	/// BESSEL_Y좌표
	private Double bslYco;
	
	/// GRS80_X좌표
	private Double grsXco;
	
	/// GRS80_Y좌표
	private Double grsYco;
	
	/// 좌표원점
	private String srtPnt;
	
	/// 좌표원점
	private String abrDes;
	
	/// 측량여부
	private String sysChk;
	
	/// 도로구간관리번호
	private String srvChk;
	
	/// 행정동 명
	private String hjdCdeNm;
	
	/// 관리기관명
	private String mngCdeNm;
	
	/// 기준점구분명
	private String pcbCdeNm;
	
	/// 매설자
	private String insUser;
	
	/// 관측자
	private String srvUser;
	
	/// 계획기관
	private String planCde;
	
	/// 계획기관 명
	private String planCdeNm;
	
	/// 도엽명
	private String shtNumNm;
	
	/// 약도
	private KwsImage outlineMapImage;
	
	/// 관측도
	private KwsImage observationMapImage;
	
	/// 근경
	private KwsImage closeRangeViewImage;
	
	/// 원경
	private KwsImage distantViewImage;
	
	/// 검색 조건
	private String searchCondition;
	
	/// 급수
	private String grad;
	
	/// 사업명
	private String bsnsNm;
	
	/// 고시번호
	private String ntfcNo;
	
	/// 시행기관
	private String wrkOrg;
	
	/// 성과구분 - 신성과(sin) / 구성과(gu)
	private String rsltSe;
	
	/// 수준점구분 - 직접수준(direct) / 간접수준(indrt)
	private String lvlPntSe;
	
	/// 경도
	private String lo;
	
	/// 위도
	private String la;
	
	/// 설치일자
	private String insYmdDate;
	
	/// 관측일자
	private String srvYmdDate;
	
	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

	public Integer getObjectid() {
		return objectid;
	}

	public void setObjectid(Integer objectid) {
		this.objectid = objectid;
	}

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public String getHjdCde() {
		return hjdCde;
	}

	public void setHjdCde(String hjdCde) {
		this.hjdCde = hjdCde;
	}

	public String getShtNum() {
		return shtNum;
	}

	public void setShtNum(String shtNum) {
		this.shtNum = shtNum;
	}

	public String getMngCde() {
		return mngCde;
	}

	public void setMngCde(String mngCde) {
		this.mngCde = mngCde;
	}

	public String getPcbCde() {
		return pcbCde;
	}

	public void setPcbCde(String pcbCde) {
		this.pcbCde = pcbCde;
	}

	public String getSerIdn() {
		return serIdn;
	}

	public void setSerIdn(String serIdn) {
		this.serIdn = serIdn;
	}

	public String getPcbDes() {
		return pcbDes;
	}

	public void setPcbDes(String pcbDes) {
		this.pcbDes = pcbDes;
	}

	public String getInsYmd() {
		return insYmd;
	}

	public void setInsYmd(String insYmd) {
		this.insYmd = insYmd;
	}

	public String getSrvYmd() {
		return srvYmd;
	}

	public void setSrvYmd(String srvYmd) {
		this.srvYmd = srvYmd;
	}

	public Double getPyoGoh() {
		return pyoGoh;
	}

	public void setPyoGoh(Double pyoGoh) {
		this.pyoGoh = pyoGoh;
	}

	public Double getBslXco() {
		return bslXco;
	}

	public void setBslXco(Double bslXco) {
		this.bslXco = bslXco;
	}

	public Double getBslYco() {
		return bslYco;
	}

	public void setBslYco(Double bslYco) {
		this.bslYco = bslYco;
	}

	public Double getGrsXco() {
		return grsXco;
	}

	public void setGrsXco(Double grsXco) {
		this.grsXco = grsXco;
	}

	public Double getGrsYco() {
		return grsYco;
	}

	public void setGrsYco(Double grsYco) {
		this.grsYco = grsYco;
	}

	public String getSrtPnt() {
		return srtPnt;
	}

	public void setSrtPnt(String srtPnt) {
		this.srtPnt = srtPnt;
	}

	public String getAbrDes() {
		return abrDes;
	}

	public void setAbrDes(String abrDes) {
		this.abrDes = abrDes;
	}

	public String getSysChk() {
		return sysChk;
	}

	public void setSysChk(String sysChk) {
		this.sysChk = sysChk;
	}

	public String getSrvChk() {
		return srvChk;
	}

	public void setSrvChk(String srvChk) {
		this.srvChk = srvChk;
	}

	public String getHjdCdeNm() {
		return hjdCdeNm;
	}

	public void setHjdCdeNm(String hjdCdeNm) {
		this.hjdCdeNm = hjdCdeNm;
	}

	public String getMngCdeNm() {
		return mngCdeNm;
	}

	public void setMngCdeNm(String mngCdeNm) {
		this.mngCdeNm = mngCdeNm;
	}

	public String getPcbCdeNm() {
		return pcbCdeNm;
	}

	public void setPcbCdeNm(String pcbCdeNm) {
		this.pcbCdeNm = pcbCdeNm;
	}

	public String getInsUser() {
		return insUser;
	}

	public void setInsUser(String insUser) {
		this.insUser = insUser;
	}

	public String getSrvUser() {
		return srvUser;
	}

	public void setSrvUser(String srvUser) {
		this.srvUser = srvUser;
	}

	public String getPlanCde() {
		return planCde;
	}

	public void setPlanCde(String planCde) {
		this.planCde = planCde;
	}

	public String getPlanCdeNm() {
		return planCdeNm;
	}

	public void setPlanCdeNm(String planCdeNm) {
		this.planCdeNm = planCdeNm;
	}

	public String getShtNumNm() {
		return shtNumNm;
	}

	public void setShtNumNm(String shtNumNm) {
		this.shtNumNm = shtNumNm;
	}

	public KwsImage getOutlineMapImage() {
		return outlineMapImage;
	}

	public void setOutlineMapImage(KwsImage outlineMapImage) {
		this.outlineMapImage = outlineMapImage;
	}

	public KwsImage getObservationMapImage() {
		return observationMapImage;
	}

	public void setObservationMapImage(KwsImage observationMapImage) {
		this.observationMapImage = observationMapImage;
	}

	public KwsImage getCloseRangeViewImage() {
		return closeRangeViewImage;
	}

	public void setCloseRangeViewImage(KwsImage closeRangeViewImage) {
		this.closeRangeViewImage = closeRangeViewImage;
	}

	public KwsImage getDistantViewImage() {
		return distantViewImage;
	}

	public void setDistantViewImage(KwsImage distantViewImage) {
		this.distantViewImage = distantViewImage;
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getBsnsNm() {
		return bsnsNm;
	}

	public void setBsnsNm(String bsnsNm) {
		this.bsnsNm = bsnsNm;
	}

	public String getNtfcNo() {
		return ntfcNo;
	}

	public void setNtfcNo(String ntfcNo) {
		this.ntfcNo = ntfcNo;
	}
	
	public String getWrkOrg() {
		return wrkOrg;
	}

	public void setWrkOrg(String wrkOrg) {
		this.wrkOrg = wrkOrg;
	}

	public String getRsltSe() {
		return rsltSe;
	}

	public void setRsltSe(String rsltSe) {
		this.rsltSe = rsltSe;
	}
	
	public String getLvlPntSe() {
		return lvlPntSe;
	}

	public void setLvlPntSe(String lvlPntSe) {
		this.lvlPntSe = lvlPntSe;
	}

	public String getLo() {
		return lo;
	}

	public void setLo(String lo) {
		this.lo = lo;
	}

	public String getLa() {
		return la;
	}

	public void setLa(String la) {
		this.la = la;
	}

	public String getInsYmdDate() {
		return insYmdDate;
	}

	public void setInsYmdDate(String insYmdDate) {
		this.insYmdDate = insYmdDate;
	}

	public String getSrvYmdDate() {
		return srvYmdDate;
	}

	public void setSrvYmdDate(String srvYmdDate) {
		this.srvYmdDate = srvYmdDate;
	}
	
}
