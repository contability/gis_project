package kr.co.gitt.kworks.model;

public class RdtLoclMa {
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 파일번호
	private Long fileNo;
	
	/// 파일명
	private String docFile;
	
	/// 도면
	private String lclCde;
	
	/// 구간이정
	private String secDis;
	
	/// 작성일자
	private String frstRgsde;
	
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

	public Long getFileNo() {
		return fileNo;
	}

	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
	}

	public String getDocFile() {
		return docFile;
	}

	public void setDocFile(String docFile) {
		this.docFile = docFile;
	}

	public String getLclCde() {
		return lclCde;
	}

	public void setLclCde(String lclCde) {
		this.lclCde = lclCde;
	}

	public String getSecDis() {
		return secDis;
	}

	public void setSecDis(String secDis) {
		this.secDis = secDis;
	}

	public String getFrstRgsde() {
		return frstRgsde;
	}

	public void setFrstRgsde(String frstRgsde) {
		this.frstRgsde = frstRgsde;
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