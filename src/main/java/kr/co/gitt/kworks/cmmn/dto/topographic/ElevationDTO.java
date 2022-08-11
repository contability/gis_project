package kr.co.gitt.kworks.cmmn.dto.topographic;

/*
 * 표고 데이터
 */
public class ElevationDTO {
	
	/**
	 * X 좌표
	 */
	private Double x;
	
	/**
	 * Y 좌표
	 */
	private Double y;
	
	/**
	 * 표고값
	 */
	private Double elevation;

	
	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getElevation() {
		return elevation;
	}

	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}
	
}
