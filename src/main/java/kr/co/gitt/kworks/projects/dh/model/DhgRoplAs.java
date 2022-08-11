package kr.co.gitt.kworks.projects.dh.model;

/**
 * 동해시 도시계획도로 
 * 
 * @author kwangsu.lee
 *
 */
public class DhgRoplAs 
{
	/**
	 * 관리번호
	 */
	private Long ftrIdn;
	
	/**
	 * 지형지물부호
	 */
	private String ftrCde;
	
	/**
	 * 국도명
	 */
	private String uprNam;
	
	/**
	 * 규모별 구분
	 */
	private String uprGrd;
	
	/**
	 * 분류
	 */
	private String uprTyp;
	
	/**
	 * 노선번호
	 */
	private String uprNum;
	
	/**
	 * 기능별 구분
	 */
	private String uprFnc;
	
	/**
	 * 노선지정일
	 */
	private String uprYmd;
	
	/**
	 * 기점 소재지
	 */
	private String uprStr;
	
	/**
	 * 종점 소재지
	 */
	private String uprEnd;
	
	/**
	 * 포장도 1차선 연장
	 */
	private Double uprPa01;
	
	/**
	 * 포장도 2차선 연장
	 */
	private Double uprPa02;
	
	/**
	 * 포장도 4차선 연장
	 */
	private Double uprPa04;
	
	/**
	 * 포장도 6차선 연장
	 */
	private Double uprPa06;
	
	/**
	 * 포장도 8차선 연장
	 */
	private Double uprPa08;
	
	/**
	 * 포장도 10차선 이상 연장
	 */
	private Double uprPa10;

	/**
	 * 미포장도 1차선 연장
	 */
	private Double uprUn01;
	
	/**
	 * 미포장도 2차선 연장
	 */
	private Double uprUn02;
	
	/**
	 * 미포장도 4차선 연장
	 */
	private Double uprUn04;
	
	/**
	 * 미포장도 6차선 연장
	 */
	private Double uprUn06;

	/**
	 * 미포장도 8차선 연장
	 */
	private Double uprUn08;

	/**
	 * 미포장도 10차선 이상 연장
	 */
	private Double uprUn10;
	
	/**
	 * 미개통도 연장
	 */
	private Double uprUnop;

	/**
	 * 미개설도 연장
	 */
	private Double uprUnse;

	/**
	 * 보도 설치일
	 */
	private String uprWok;

	/**
	 * 보도 유효폭
	 */
	private String uprWid;

	/**
	 * 보도 횡단경사
	 */
	private Double uprSle;

	/**
	 * 보도 연석높이
	 */
	private Double uprHgt;

	/**
	 * 보도 좌측연장
	 */
	private Double uprWkl;

	/**
	 * 보도 우측연장
	 */
	private Double uprWkr;
	
	/**
	 * 교통시설 관리번호
	 */
	private String mnum;

	/**
	 * 비고
	 */
	private String remark;
	

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

	public String getUprTyp() {
		return uprTyp;
	}

	public void setUprTyp(String uprTyp) {
		this.uprTyp = uprTyp;
	}

	public String getUprNum() {
		return uprNum;
	}

	public void setUprNum(String uprNum) {
		this.uprNum = uprNum;
	}

	public String getUprFnc() {
		return uprFnc;
	}

	public void setUprFnc(String uprFnc) {
		this.uprFnc = uprFnc;
	}

	public String getUprYmd() {
		return uprYmd;
	}

	public void setUprYmd(String uprYmd) {
		this.uprYmd = uprYmd;
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

	public Double getUprPa01() {
		return uprPa01;
	}

	public void setUprPa01(Double uprPa01) {
		this.uprPa01 = uprPa01;
	}

	public Double getUprPa02() {
		return uprPa02;
	}

	public void setUprPa02(Double uprPa02) {
		this.uprPa02 = uprPa02;
	}

	public Double getUprPa04() {
		return uprPa04;
	}

	public void setUprPa04(Double uprPa04) {
		this.uprPa04 = uprPa04;
	}

	public Double getUprPa06() {
		return uprPa06;
	}

	public void setUprPa06(Double uprPa06) {
		this.uprPa06 = uprPa06;
	}

	public Double getUprPa08() {
		return uprPa08;
	}

	public void setUprPa08(Double uprPa08) {
		this.uprPa08 = uprPa08;
	}

	public Double getUprPa10() {
		return uprPa10;
	}

	public void setUprPa10(Double uprPa10) {
		this.uprPa10 = uprPa10;
	}

	public Double getUprUn01() {
		return uprUn01;
	}

	public void setUprUn01(Double uprUn01) {
		this.uprUn01 = uprUn01;
	}

	public Double getUprUn02() {
		return uprUn02;
	}

	public void setUprUn02(Double uprUn02) {
		this.uprUn02 = uprUn02;
	}

	public Double getUprUn04() {
		return uprUn04;
	}

	public void setUprUn04(Double uprUn04) {
		this.uprUn04 = uprUn04;
	}

	public Double getUprUn06() {
		return uprUn06;
	}

	public void setUprUn06(Double uprUn06) {
		this.uprUn06 = uprUn06;
	}

	public Double getUprUn08() {
		return uprUn08;
	}

	public void setUprUn08(Double uprUn08) {
		this.uprUn08 = uprUn08;
	}

	public Double getUprUn10() {
		return uprUn10;
	}

	public void setUprUn10(Double uprUn10) {
		this.uprUn10 = uprUn10;
	}

	public Double getUprUnop() {
		return uprUnop;
	}

	public void setUprUnop(Double uprUnop) {
		this.uprUnop = uprUnop;
	}

	public Double getUprUnse() {
		return uprUnse;
	}

	public void setUprUnse(Double uprUnse) {
		this.uprUnse = uprUnse;
	}

	public String getUprWok() {
		return uprWok;
	}

	public void setUprWok(String uprWok) {
		this.uprWok = uprWok;
	}

	public String getUprWid() {
		return uprWid;
	}

	public void setUprWid(String uprWid) {
		this.uprWid = uprWid;
	}

	public Double getUprSle() {
		return uprSle;
	}

	public void setUprSle(Double uprSle) {
		this.uprSle = uprSle;
	}

	public Double getUprHgt() {
		return uprHgt;
	}

	public void setUprHgt(Double uprHgt) {
		this.uprHgt = uprHgt;
	}

	public Double getUprWkl() {
		return uprWkl;
	}

	public void setUprWkl(Double uprWkl) {
		this.uprWkl = uprWkl;
	}

	public Double getUprWkr() {
		return uprWkr;
	}

	public void setUprWkr(Double uprWkr) {
		this.uprWkr = uprWkr;
	}

	public String getMnum() {
		return mnum;
	}

	public void setMnum(String mnum) {
		this.mnum = mnum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
