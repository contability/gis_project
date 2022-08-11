package kr.co.gitt.kworks.projects.gn.dto;

import java.util.List;

public class RoadRegisterSearchDTO {
	
	/// 노선명
	private String roadNam;
	
	/// 노선번호
	private Integer roadIdn;
	
	/// 구간번호
	private Integer sectIdn; 
	
	/// 레이어 목록
	private List<String> regIdns;

	
	public String getRoadNam() {
		return roadNam;
	}

	public void setRoadNam(String roadNam) {
		this.roadNam = roadNam;
	}
	
	public Integer getRoadIdn() {
		return roadIdn;
	}

	public void setRoadIdn(Integer roadIdn) {
		this.roadIdn = roadIdn;
	}

	public Integer getSectIdn() {
		return sectIdn;
	}

	public void setSectIdn(Integer sectIdn) {
		this.sectIdn = sectIdn;
	}
	
	public List<String> getRegIdns() {
		return regIdns;
	}

	public void setRegIdns(List<String> regIdns) {
		this.regIdns = regIdns;
	}
	
}
