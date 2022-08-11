package kr.co.gitt.kworks.projects.sunchang.model;

import java.sql.Date;

public class KwsIprvarDtFile {
	//번호
	private Long iprvarFileNo;
	
	//정비보류지역 관리조서 번호
	private Long iprvarNo;
	
	//파일 번호
	private Long fileNo;
	
	//제목
	private String iprvarFileSj;
	
	//작성자 아이디
	private String wrterId;
	
	//최초 등록일
	private Date frstRgsde;
	
	public Long getIprvarFileNo() {
		return iprvarFileNo;
	}

	public void setIprvarFileNo(Long iprvarFileNo) {
		this.iprvarFileNo = iprvarFileNo;
	}

	public Long getIprvarNo() {
		return iprvarNo;
	}

	public void setIprvarNo(Long iprvarNo) {
		this.iprvarNo = iprvarNo;
	}

	public Long getFileNo() {
		return fileNo;
	}

	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
	}

	public String getIprvarFileSj() {
		return iprvarFileSj;
	}

	public void setIprvarFileSj(String iprvarFileSj) {
		this.iprvarFileSj = iprvarFileSj;
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
}
