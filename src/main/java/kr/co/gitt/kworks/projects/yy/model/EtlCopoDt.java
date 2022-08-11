package kr.co.gitt.kworks.projects.yy.model;

/**
 * 시준점
 * @author kwangsu.lee
 *
 */
public class EtlCopoDt {

	/**
	 * 번호
	 */
	private Long ecpNo;

	/**
	 * 지형지물부호
	 */
	private String ftrCde;
	
	/**
	 * 관리번호
	 */
	private Long ftrIdn;
	
	/**
	 * 시준점 명칭
	 */
	private String ecpNam;

	/**
	 * 방위각
	 */
	private String ecpDeg;
	
	/**
	 * 거리
	 */
	private Double ecpLen;
	
	/**
	 * 비고
	 */
	private String rmkDes;


	public Long getEcpNo() {
		return ecpNo;
	}

	public void setEcpNo(Long ecpNo) {
		this.ecpNo = ecpNo;
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

	public String getEcpNam() {
		return ecpNam;
	}

	public void setEcpNam(String ecpNam) {
		this.ecpNam = ecpNam;
	}

	public String getEcpDeg() {
		return ecpDeg;
	}

	public void setEcpDeg(String ecpDeg) {
		this.ecpDeg = ecpDeg;
	}

	public Double getEcpLen() {
		return ecpLen;
	}

	public void setEcpLen(Double ecpLen) {
		this.ecpLen = ecpLen;
	}

	public String getRmkDes() {
		return rmkDes;
	}

	public void setRmkDes(String rmkDes) {
		this.rmkDes = rmkDes;
	}	
}
