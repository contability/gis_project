package kr.co.gitt.kworks.model;

import java.util.List;

/**
 * 중첩용 타일맵 정보
 * @author kwangsu.lee
 *
 */
public class KwsOverlayTile {

	/**
	 * 타일맵 번호
	 */
	private Long tileNo;
	
	/**
	 * 제목
	 */
	private String title;
	
	/**
	 * 설명
	 */
	private String description;
	
	/**
	 * 좌표계
	 */
	private String srs;
	
	/**
	 * 원점 X
	 */
	private Double originX;
	
	/**
	 * 원점 Y
	 */
	private Double originY;
	
	/**
	 * 확장자
	 */
	private String extension;
	
	/**
	 * 높이
	 */
	private Integer height;
	
	/**
	 * 너비
	 */
	private Integer width;
	
	/**
	 * 요청 포맷
	 */
	private String requestFormat;
	
	/**
	 * 최소 X
	 */
	private Double minX;
	
	/**
	 * 최소 Y
	 */
	private Double minY;
	
	/**
	 * 최대 X
	 */
	private Double maxX;
	
	/**
	 * 최대 Y
	 */
	private Double maxY;	

	/**
	 * 호스트
	 */
	private String host;

	/**
	 * 포트
	 */
	private Long port;
	
	/**
	 * 타일 레벨 목록
	 */
	private List<KwsOverlayTileLevel> kwsOverlayTileLevels;

	public Long getTileNo() {
		return tileNo;
	}

	public void setTileNo(Long tileNo) {
		this.tileNo = tileNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSrs() {
		return srs;
	}

	public void setSrs(String srs) {
		this.srs = srs;
	}

	public Double getOriginX() {
		return originX;
	}

	public void setOriginX(Double originX) {
		this.originX = originX;
	}

	public Double getOriginY() {
		return originY;
	}

	public void setOriginY(Double originY) {
		this.originY = originY;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public String getRequestFormat() {
		return requestFormat;
	}

	public void setRequestFormat(String requestFormat) {
		this.requestFormat = requestFormat;
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Long getPort() {
		return port;
	}

	public void setPort(Long port) {
		this.port = port;
	}

	public List<KwsOverlayTileLevel> getKwsOverlayTileLevels() {
		return kwsOverlayTileLevels;
	}

	public void setKwsOverlayTileLevels(
			List<KwsOverlayTileLevel> kwsOverlayTileLevels) {
		this.kwsOverlayTileLevels = kwsOverlayTileLevels;
	}
}
