package kr.co.gitt.kworks.projects.gn.model;

/**
 * 점용대장 부속자료 다운로드 이력
 * @author kwangsu.lee
 *
 */
public class OcpatDownHi {

	/**
	 * 다운로드 이력 순번
	 */
	private Long histNo;
	
	/**
	 * 사용자 아이디
	 */
	private String userId;
	
	/**
	 *  사용자 명
	 */
	private String userNm;
	
	/**
	 * 다운로드 일시
	 */
	private String downDt;
	
	/**
	 * 대장 종류
	 */
	private String ftrCde;

	/**
	 * 대장 명칭
	 */
	private String cdeNam;
	
	/**
	 * 대장 관리번호
	 */
	private Long ftrIdn;
	
	/**
	 * 허가번호
	 */
	private String pmtNum;

	/**
	 * 자료 종류
	 */
	private String docCde;
	
	/**
	 * 파일 관리번호
	 */
	private Long fileNo;
	
	
	public Long getHistNo() {
		return histNo;
	}

	public void setHistNo(Long histNo) {
		this.histNo = histNo;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	
	public String getDownDt() {
		return downDt;
	}

	public void setDownDt(String downDt) {
		this.downDt = downDt;
	}
	
	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}
	
	public String getCdeNam() {
		return cdeNam;
	}

	public void setCdeNam(String cdeNam) {
		this.cdeNam = cdeNam;
	}
	
	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public String getPmtNum() {
		return pmtNum;
	}

	public void setPmtNum(String pmtNum) {
		this.pmtNum = pmtNum;
	}

	public String getDocCde() {
		return docCde;
	}

	public void setDocCde(String docCde) {
		this.docCde = docCde;
	}
	
	public Long getFileNo() {
		return fileNo;
	}

	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
	}
	
}
