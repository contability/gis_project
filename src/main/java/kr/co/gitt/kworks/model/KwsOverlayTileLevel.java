package kr.co.gitt.kworks.model;

/**
 * 타일맵 레벨
 * @author kwangsu.lee
 *
 */
public class KwsOverlayTileLevel {

	/**
	 * 레벨 번호
	 */
	private Long levelNo;
	
	/**
	 * 타일맵 번호
	 */
	private Long tileNo;
	
	/**
	 * 레벨
	 */
	private Integer level;
	
	/**
	 * 해상도
	 */
	private Double resolution;

	public Long getLevelNo() {
		return levelNo;
	}

	public void setLevelNo(Long levelNo) {
		this.levelNo = levelNo;
	}

	public Long getTileNo() {
		return tileNo;
	}

	public void setTileNo(Long tileNo) {
		this.tileNo = tileNo;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Double getResolution() {
		return resolution;
	}

	public void setResolution(Double resolution) {
		this.resolution = resolution;
	}
}
