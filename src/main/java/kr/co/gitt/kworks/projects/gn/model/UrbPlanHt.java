package kr.co.gitt.kworks.projects.gn.model;

/**
 * 도시계획도로 변경이력 모델
 * @author kwangsu.lee
 *
 */
public class UrbPlanHt {

	/**
	 * 관리번호
	 */
	private Long ftrIdn;
	
	/**
	 * 교통시설 관리번호
	 */
	private String uprIdn;
	
	/**
	 * 이력변호
	 */
	private Integer uprSeq;
	
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
	 * 고시근거
	 */
	private String uprNty;	
	
	/**
	 * 고시근거일
	 */
	private String uprYmd;	
	
	/**
	 * 고시구분
	 */
	private String uprCla;

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

	public String getUprIdn() {
		return uprIdn;
	}

	public void setUprIdn(String uprIdn) {
		this.uprIdn = uprIdn;
	}

	public Integer getUprSeq() {
		return uprSeq;
	}

	public void setUprSeq(Integer uprSeq) {
		this.uprSeq = uprSeq;
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

	public String getUprNty() {
		return uprNty;
	}

	public void setUprNty(String uprNty) {
		this.uprNty = uprNty;
	}

	public String getUprYmd() {
		return uprYmd;
	}

	public void setUprYmd(String uprYmd) {
		this.uprYmd = uprYmd;
	}

	public String getUprCla() {
		return uprCla;
	}

	public void setUprCla(String uprCla) {
		this.uprCla = uprCla;
	}

	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}
}
