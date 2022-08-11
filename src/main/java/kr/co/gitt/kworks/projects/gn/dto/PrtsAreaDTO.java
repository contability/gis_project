package kr.co.gitt.kworks.projects.gn.dto;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

public class PrtsAreaDTO extends SearchDTO{
	
	private String ftrCde;
	
	private String bjdCde;
	
	private String rodNam;
	
	private String facNum;
	
	private String fadNum;
	
	private Long ftrIdn;

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public String getBjdCde() {
		return bjdCde;
	}

	public void setBjdCde(String bjdCde) {
		this.bjdCde = bjdCde;
	}

	public String getRodNam() {
		return rodNam;
	}

	public void setRodNam(String rodNam) {
		this.rodNam = rodNam;
	}

	public String getFacNum() {
		return facNum;
	}

	public void setFacNum(String facNum) {
		this.facNum = facNum;
	}

	public String getFadNum() {
		return fadNum;
	}

	public void setFadNum(String fadNum) {
		this.fadNum = fadNum;
	}

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}
}
