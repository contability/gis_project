package kr.co.gitt.kworks.model;

public class RdtVideoMa {
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 상행 파일번호
	private Long upNo;
	
	/// 상행 파일명
	private String upFile;
	
	/// 하행 파일번호
	private Long downNo;
	
	/// 하행 파일명
	private String downFile;
	
	/// 노선명
	private String rotNam;
	
	/// 노선번호
	private String rotIdn;
	
	/// 구간
	private String secIdn;

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public Long getUpNo() {
		return upNo;
	}

	public void setUpNo(Long upNo) {
		this.upNo = upNo;
	}

	public String getUpFile() {
		return upFile;
	}

	public void setUpFile(String upFile) {
		this.upFile = upFile;
	}

	public Long getDownNo() {
		return downNo;
	}

	public void setDownNo(Long downNo) {
		this.downNo = downNo;
	}

	public String getDownFile() {
		return downFile;
	}

	public void setDownFile(String downFile) {
		this.downFile = downFile;
	}

	public String getRotNam() {
		return rotNam;
	}

	public void setRotNam(String rotNam) {
		this.rotNam = rotNam;
	}

	public String getRotIdn() {
		return rotIdn;
	}

	public void setRotIdn(String rotIdn) {
		this.rotIdn = rotIdn;
	}

	public String getSecIdn() {
		return secIdn;
	}

	public void setSecIdn(String secIdn) {
		this.secIdn = secIdn;
	}
	
}