package kr.co.gitt.kworks.projects.yg.dto;

import kr.co.gitt.kworks.projects.yg.model.LdlConsPs;

public class LandCenterCntrwkRegstrDTO extends LdlConsPs {
	
	//위치정보 수정여부
	private boolean lcinfoUpdtAt;
	
	//입력된 X좌표
	private String lcX;
	
	//입력된 Y좌표
	private String lcY;
	
	//토지공사위치의 지도 상 표현을 위한 wkt 값
	private String wkt;
	
	public void setLcinfoUpdtAt(boolean lcinfoUpdtAt) {
		this.lcinfoUpdtAt = lcinfoUpdtAt;
	}
	
	public boolean getLcinfoUpdtAt() {
		return lcinfoUpdtAt;
	}
	
	public void setLcX(String lcX) {
		this.lcX = lcX;
	}
	
	public String getLcX() {
		return lcX;
	}
	
	public void setLcY(String lcY) {
		this.lcY = lcY;
	}
	
	public String getLcY() {
		return lcY;
	}
	
	public void setWkt(String wkt) {
		this.wkt = wkt;
	}
	
	public String getWkt() {
		return wkt;
	}
}
