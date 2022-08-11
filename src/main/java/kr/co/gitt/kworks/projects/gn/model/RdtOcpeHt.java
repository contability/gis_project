package kr.co.gitt.kworks.projects.gn.model;

/**
 * 이  Class는 변경이력 모델입니다.
 * 변경이력 
 * @author wongi.jo
 *
 */
public class RdtOcpeHt {

	/**
	 * 파일번호
	 */
	private Long histNo;
	
	/**
	 * 점용허가대장 지형지물부호 
	 */
	private String ftrCde;
	
	/**
	 * 점용허가대장 관리번호
	 */
	private Long ftrIdn;
	
	/**
	 * 부속자료 코드 
	 */
	private String histDoc;
	
	/**
	 * 부속자료 파일명 
	 */
	private String histNum;
	
	/**
	 * 작성일 
	 */
	private String rmkExp;
	
	/**
	 * Generate Getters And Setters... 
	 */
	
	public Long getHistNo() {
		return histNo;
	}

	public void setHistNo(Long histNo) {
		this.histNo = histNo;
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

	public String getHistDoc() {
		return histDoc;
	}

	public void setHistDoc(String histDoc) {
		this.histDoc = histDoc;
	}

	public String getHistNum() {
		return histNum;
	}

	public void setHistNum(String histNum) {
		this.histNum = histNum;
	}

	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}
	
	

}
