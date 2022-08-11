package kr.co.gitt.kworks.projects.gn.model;

/**
 * 점용대장 편집이력
 * @author kwangsu.lee
 *
 */
public class OcpatEditHi {

	/**
	 * 편집이력 순번
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
	 * 편집 일시
	 */
	private String editDt;
	
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
	 * 편집 타입
	 */
	private String editType;
	
	
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
	
	public String getEditDt() {
		return editDt;
	}

	public void setEditDt(String editDt) {
		this.editDt = editDt;
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
	
	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}
}
