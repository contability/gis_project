package kr.co.gitt.kworks.projects.gn.dto;

public class OcpatRegisterSpatialSearchDTO {

	private Double xmin;
	
	private Double ymin;

	private Double xmax;

	private Double ymax;
	
	private String wktArea;
	
	private String land;
	
	
	public Double getXmin() {
		return xmin;
	}

	public void setXmin(Double xmin) {
		this.xmin = xmin;
	}

	public Double getYmin() {
		return ymin;
	}

	public void setYmin(Double ymin) {
		this.ymin = ymin;
	}
	
	public Double getXmax() {
		return xmax;
	}

	public void setXmax(Double xmax) {
		this.xmax = xmax;
	}

	public Double getYmax() {
		return ymax;
	}

	public void setYmax(Double ymax) {
		this.ymax = ymax;
	}
	
	public String getWktArea() {
		return wktArea;
	}
	
	public void setWktArea(String wktArea) {
		this.wktArea = wktArea;
	}
	
	public String getLand() {
		return land;
	}
	
	public void setLand(String land) {
		this.land = land;
	}
	
}
