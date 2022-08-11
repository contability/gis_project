package kr.co.gitt.kworks.projects.gn.model;

/**
 * 도시계획도로 모델
 * @author kwangsu.lee
 *
 */
public class UrbPlanMa {
	
	/**
	 * 관리번호
	 */
	private Long ftrIdn;
	
	/**
	 * 지형지물부호
	 */
	private String ftrCde;
	
	/**
	 * 교통시설 관리번호
	 */
	private String uprIdn;
	
	/**
	 * 도시
	 */
	private String uprBjd;
	
	/**
	 * 구역명
	 */
	private String uprNam;
	
	/**
	 * 등급
	 */
	private String uprGrd;
	
	/**
	 * 류별
	 */
	private Integer uprTyp;

	/**
	 * 번호
	 */
	private String uprNum;
	
	/**
	 * 폭원
	 */
	private Double uprWid;
	
	/**
	 * 연장
	 */
	private Double uprLen;

	/**
	 * 기점
	 */
	private String uprStr;
	
	/**
	 * 종점
	 */
	private String uprEnd;	
	
	/**
	 * 기능
	 */
	private String uprFcn;	
	
	/**
	 * 사용형태
	 */
	private String uprUty;	
	
	/**
	 * 최초고시
	 */
	private String strNtc;	
	
	/**
	 * 최초고시일
	 */
	private String strYmd;	

	/**
	 * 최종고시
	 */
	private String lstNtc;	
	
	/**
	 * 최종고시일
	 */
	private String lstYmd;	
	
	/**
	 *인가고시 
	 */
	private String pmtNtc;	
	
	/**
	 * 인가고시일
	 */
	private String pmtYmd;	
	
	/**
	 * 폐지고시
	 */
	private String disNtc;	
	
	/**
	 * 폐지고시일
	 */
	private String disYmd;	

	/**
	 * 비고
	 */
	private String rmkExp;

	
	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public String getUprIdn() {
		return uprIdn;
	}

	public void setUprIdn(String uprIdn) {
		this.uprIdn = uprIdn;
	}

	public String getUprBjd() {
		return uprBjd;
	}

	public void setUprBjd(String uprBjd) {
		this.uprBjd = uprBjd;
	}

	public String getUprNam() {
		return uprNam;
	}

	public void setUprNam(String uprNam) {
		this.uprNam = uprNam;
	}

	public String getUprGrd() {
		return uprGrd;
	}

	public void setUprGrd(String uprGrd) {
		this.uprGrd = uprGrd;
	}

	public Integer getUprTyp() {
		return uprTyp;
	}

	public void setUprTyp(Integer uprTyp) {
		this.uprTyp = uprTyp;
	}

	public String getUprNum() {
		return uprNum;
	}

	public void setUprNum(String uprNum) {
		this.uprNum = uprNum;
	}

	public Double getUprWid() {
		return uprWid;
	}

	public void setUprWid(Double uprWid) {
		this.uprWid = uprWid;
	}

	public Double getUprLen() {
		return uprLen;
	}

	public void setUprLen(Double uprLen) {
		this.uprLen = uprLen;
	}

	public String getUprStr() {
		return uprStr;
	}

	public void setUprStr(String uprStr) {
		this.uprStr = uprStr;
	}

	public String getUprEnd() {
		return uprEnd;
	}

	public void setUprEnd(String uprEnd) {
		this.uprEnd = uprEnd;
	}

	public String getUprFcn() {
		return uprFcn;
	}

	public void setUprFcn(String uprFcn) {
		this.uprFcn = uprFcn;
	}

	public String getUprUty() {
		return uprUty;
	}

	public void setUprUty(String uprUty) {
		this.uprUty = uprUty;
	}

	public String getStrNtc() {
		return strNtc;
	}

	public void setStrNtc(String strNtc) {
		this.strNtc = strNtc;
	}

	public String getStrYmd() {
		return strYmd;
	}

	public void setStrYmd(String strYmd) {
		this.strYmd = strYmd;
	}

	public String getLstNtc() {
		return lstNtc;
	}

	public void setLstNtc(String lstNtc) {
		this.lstNtc = lstNtc;
	}

	public String getLstYmd() {
		return lstYmd;
	}

	public void setLstYmd(String lstYmd) {
		this.lstYmd = lstYmd;
	}

	public String getPmtNtc() {
		return pmtNtc;
	}

	public void setPmtNtc(String pmtNtc) {
		this.pmtNtc = pmtNtc;
	}

	public String getPmtYmd() {
		return pmtYmd;
	}

	public void setPmtYmd(String pmtYmd) {
		this.pmtYmd = pmtYmd;
	}

	public String getDisNtc() {
		return disNtc;
	}

	public void setDisNtc(String disNtc) {
		this.disNtc = disNtc;
	}

	public String getDisYmd() {
		return disYmd;
	}

	public void setDisYmd(String disYmd) {
		this.disYmd = disYmd;
	}

	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}
}
