package kr.co.gitt.kworks.cmmn.dto;

public class FileDTO {
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 파일번호
	private Long fileNo;
	
	/// 파일명
	private String docFile;
	
	/// 구간이정
	private String secDis;
	
	/// 도면
	private String lclCde;
	
	/// 작성일자
	private String frstRgsde;

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

	public String getSecDis() {
		return secDis;
	}

	public void setSecDis(String secDis) {
		this.secDis = secDis;
	}

	public String getLclCde() {
		return lclCde;
	}

	public void setLclCde(String lclCde) {
		this.lclCde = lclCde;
	}

	public String getFrstRgsde() {
		return frstRgsde;
	}

	public void setFrstRgsde(String frstRgsde) {
		this.frstRgsde = frstRgsde;
	}
	
}