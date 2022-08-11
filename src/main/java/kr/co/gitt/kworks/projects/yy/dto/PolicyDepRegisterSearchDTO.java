package kr.co.gitt.kworks.projects.yy.dto;

import java.util.List;

//Gitt
//create data 2020_06_10 AM 11:41:00
//create user Wongi_Jo
//@이클래스는 정책지도 통합검색 모델입니다.

public class PolicyDepRegisterSearchDTO {
	
	//정책명
	private String plcyDep;
	
	//총갯수
	private Long count;
	
	//ids
	private Integer[] ids;

	private String plcyTit;

	private String plcyAdr;

	private String cttNam;
	
	public String getPlcyTit() {
		return plcyTit;
	}

	public void setPlcyTit(String plcyTit) {
		this.plcyTit = plcyTit;
	}

	public String getPlcyAdr() {
		return plcyAdr;
	}

	public void setPlcyAdr(String plcyAdr) {
		this.plcyAdr = plcyAdr;
	}

	public String getCttNam() {
		return cttNam;
	}

	public void setCttNam(String cttNam) {
		this.cttNam = cttNam;
	}

	public String getPlcyDep() {
		return plcyDep;
	}

	public void setPlcyDep(String plcyDep) {
		this.plcyDep = plcyDep;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	


}
