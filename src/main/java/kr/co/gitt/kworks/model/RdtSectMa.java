package kr.co.gitt.kworks.model;

public class RdtSectMa {
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 파일명
	private String docFile;
	
	/// 파일번호
	private Long fileNo;
	
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

	public String getDocFile() {
		return docFile;
	}

	public void setDocFile(String docFile) {
		this.docFile = docFile;
	}

	public Long getFileNo() {
		return fileNo;
	}

	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
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