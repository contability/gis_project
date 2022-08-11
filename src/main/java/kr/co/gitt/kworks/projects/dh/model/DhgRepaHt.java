package kr.co.gitt.kworks.projects.dh.model;


/**
 * 동해시 도시계획도로 정비이력
 *  
 * @author kwangsu.lee
 *
 */
public class DhgRepaHt {
	
	/**
	 * 관리번호
	 */
	private Long ftrIdn;
	
	/**
	 * 이력번호
	 */
	private Long repSeq;
	
	/**
	 * 시작일
	 */
	private String repStr;
	
	/**
	 * 종료일
	 */
	private String repEnd;
	
	/**
	 * 정비내용
	 */
	private String repDoc;

	
	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public Long getRepSeq() {
		return repSeq;
	}

	public void setRepSeq(Long repSeq) {
		this.repSeq = repSeq;
	}

	public String getRepStr() {
		return repStr;
	}

	public void setRepStr(String repStr) {
		this.repStr = repStr;
	}

	public String getRepEnd() {
		return repEnd;
	}

	public void setRepEnd(String repEnd) {
		this.repEnd = repEnd;
	}

	public String getRepDoc() {
		return repDoc;
	}

	public void setRepDoc(String repDoc) {
		this.repDoc = repDoc;
	}
}
