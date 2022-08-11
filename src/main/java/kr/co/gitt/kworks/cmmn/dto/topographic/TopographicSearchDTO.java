package kr.co.gitt.kworks.cmmn.dto.topographic;

import java.util.List;

/**
 * 지형정보를 검색하기 위한 모델
 * @author kwangsu.lee
 *
 */
public class TopographicSearchDTO {

	/**
	 * 지형정보 그룹명
	 */
	private String groupName;
	
	/**
	 * 레이어 목록
	 */
	private List<String> layers;
	
	/**
	 * 위치좌표(픽셀) x
	 */
	private Integer x;
	
	/**
	 * 위치좌표(픽셀) y
	 */
	private Integer y;

	/**
	 * 좌표계
	 */
	private String srs;
	
	/**
	 * 영역 최소X
	 */
	private Double minX;
	
	/**
	 * 영역 최소Y
	 */
	private Double minY;
	
	/**
	 * 영역 최대X
	 */
	private Double maxX;
	
	/**
	 * 영역 최대Y
	 */
	private Double maxY;
	
	/**
	 * 이미지 폭
	 */
	private Integer width;

	/**
	 * 이미지 높이
	 */
	private Integer height;


	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<String> getLayers() {
		return layers;
	}

	public void setLayers(List<String> layers) {
		this.layers = layers;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public String getSrs() {
		return srs;
	}

	public void setSrs(String srs) {
		this.srs = srs;
	}

	public Double getMinX() {
		return minX;
	}

	public void setMinX(Double minX) {
		this.minX = minX;
	}

	public Double getMinY() {
		return minY;
	}

	public void setMinY(Double minY) {
		this.minY = minY;
	}

	public Double getMaxX() {
		return maxX;
	}

	public void setMaxX(Double maxX) {
		this.maxX = maxX;
	}

	public Double getMaxY() {
		return maxY;
	}

	public void setMaxY(Double maxY) {
		this.maxY = maxY;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
}
