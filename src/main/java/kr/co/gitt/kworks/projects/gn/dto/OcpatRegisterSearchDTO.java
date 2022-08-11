package kr.co.gitt.kworks.projects.gn.dto;


/**
 * 점용대장 통합검색
 * @author kwangsu.lee
 *
 */
public class OcpatRegisterSearchDTO {
	
	/**
	 * 허가번호
	 */
	private String pmtNum;

	/**
	 * 허가일
	 */
	private String pmtYmdMin;
	
	/**
	 * 허가일
	 */
	private String pmtYmdMax;

	/**
	 * 점용위치
	 */
	private String jygLoc; 
	
	/**
	 * 점용자 성명
	 */
	private String prsNam;
	
	/**
	 * 점용기간 유형
	 */
	private Integer jygUlm;
	
	/**
	 * 시작일
	 */
	private String jysYmdMin;

	/**
	 * 시작일
	 */
	private String jysYmdMax;
	
	/**
	 * 종료일
	 */
	private String jyeYmdMin;
	
	/**
	 * 종료일
	 */
	private String jyeYmdMax;

	
	public String getPmtNum() {
		return pmtNum;
	}

	public void setPmtNum(String pmtNum) {
		this.pmtNum = pmtNum;
	}

	public String getPmtYmdMin() {
		return pmtYmdMin;
	}

	public void setPmtYmdMin(String pmtYmdMin) {
		this.pmtYmdMin = pmtYmdMin;
	}

	public String getPmtYmdMax() {
		return pmtYmdMax;
	}

	public void setPmtYmdMax(String pmtYmdMax) {
		this.pmtYmdMax = pmtYmdMax;
	}
		
	public String getJygLoc() {
		return jygLoc;
	}

	public void setJygLoc(String jygLoc) {
		this.jygLoc = jygLoc;
	}

	public String getPrsNam() {
		return prsNam;
	}

	public void setPrsNam(String prsNam) {
		this.prsNam = prsNam;
	}
	
	public Integer getJygUlm() {
		return jygUlm;
	}

	public void setJygUlm(Integer jygUlm) {
		this.jygUlm = jygUlm;
	}
	
	public String getJysYmdMin() {
		return jysYmdMin;
	}

	public void setJysYmdMin(String jysYmdMin) {
		this.jysYmdMin = jysYmdMin;
	}

	public String getJysYmdMax() {
		return jysYmdMax;
	}

	public void setJysYmdMax(String jysYmdMax) {
		this.jysYmdMax = jysYmdMax;
	}
	
	public String getJyeYmdMin() {
		return jyeYmdMin;
	}

	public void setJyeYmdMin(String jyeYmdMin) {
		this.jyeYmdMin = jyeYmdMin;
	}
	
	public String getJyeYmdMax() {
		return jyeYmdMax;
	}

	public void setJyeYmdMax(String jyeYmdMax) {
		this.jyeYmdMax = jyeYmdMax;
	}
}
