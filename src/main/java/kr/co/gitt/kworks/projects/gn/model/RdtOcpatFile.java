package kr.co.gitt.kworks.projects.gn.model;

import java.sql.Date;

import kr.co.gitt.kworks.model.KwsFile;

public class RdtOcpatFile {
	
	// 점용허가파일 번호
	private Long ocpatFileNo;
	
	// 이미지 제목
	private String fileSj;
	
	// 이미지 내용
	private String fileCn;
	
	// 지형지물부호
	private String ftrCde;
	
	// 관리번호
	private Long ftrIdn;
	
	// 파일 번호
	private Long fileNo;
	
	// 작성자 아이디
	private String wrterId;
	
	// 최초 등록 일
	private Date frstRgsde;
	
	// 편집자 아이디
	private String updusrId;
	
	// 최종 수정 일
	private Date lastUpdde;
	
	// 파일
	private KwsFile kwsFile;
	
	public Long getOcpatFileNo() {
		return ocpatFileNo;
	}
	public void setOcpatFileNo(Long ocpatFileNo) {
		this.ocpatFileNo = ocpatFileNo;
	}
	public String getFileSj() {
		return fileSj;
	}
	public void setFileSj(String fileSj) {
		this.fileSj = fileSj;
	}
	public String getFileCn() {
		return fileCn;
	}
	public void setFileCn(String fileCn) {
		this.fileCn = fileCn;
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
	public Long getFileNo() {
		return fileNo;
	}
	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
	}
	public String getWrterId() {
		return wrterId;
	}
	public void setWrterId(String wrterId) {
		this.wrterId = wrterId;
	}
	public Date getFrstRgsde() {
		return frstRgsde;
	}
	public void setFrstRgsde(Date frstRgsde) {
		this.frstRgsde = frstRgsde;
	}
	public String getUpdusrId() {
		return updusrId;
	}
	public void setUpdusrId(String updusrId) {
		this.updusrId = updusrId;
	}
	public Date getLastUpdde() {
		return lastUpdde;
	}
	public void setLastUpdde(Date lastUpdde) {
		this.lastUpdde = lastUpdde;
	}
	public KwsFile getKwsFile() {
		return kwsFile;
	}
	public void setKwsFile(KwsFile kwsFile) {
		this.kwsFile = kwsFile;
	}
}
