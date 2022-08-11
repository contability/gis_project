package kr.co.gitt.kworks.projects.dh.model;


/**
 * 동해시 도시계획도로 공사개요
 * 
 * @author kwangsu.lee
 *
 */
public class DhgConsHt {
	
	/**
	 * 관리번호
	 */
	private Long ftrIdn;
	
	/**
	 * 이력번호
	 */
	private Long conSeq;
	
	/**
	 * 시작일 
	 */
	private String conStr;
	
	/**
	 * 종료일
	 */
	private String conEnd;
	
	/**
	 * 공사내용
	 */
	private String conDoc;

	
	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public Long getConSeq() {
		return conSeq;
	}

	public void setConSeq(Long conSeq) {
		this.conSeq = conSeq;
	}

	public String getConStr() {
		return conStr;
	}

	public void setConStr(String conStr) {
		this.conStr = conStr;
	}

	public String getConEnd() {
		return conEnd;
	}

	public void setConEnd(String conEnd) {
		this.conEnd = conEnd;
	}

	public String getConDoc() {
		return conDoc;
	}

	public void setConDoc(String conDoc) {
		this.conDoc = conDoc;
	}
}
