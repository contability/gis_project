package kr.co.gitt.kworks.model;

import java.util.List;

/////////////////////////////////////////////
/// @class KwsBaseMap
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsBaseMap.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 2. 1. 오전 11:17:39 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 기본 지도 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsBaseMap {
	
	/////////////////////////////////////////////
	/// @class MapType
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsBaseMap.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2017. 2. 13. 오후 5:47:04 |
	///    | Class Version | v1.0 |
	///    | 작업자 | admin, Others... |
	/// @section 상세설명
	/// - 이 클래스는 지도 타입 입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum MapType {
		EXTERNAL("외부영상"),
		FLIGHT("항공영상");
		
		/// 지도 타입
		private String value;
		
		private MapType(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}

	/// 지도 번호
	private Long mapNo;
	
	/// 지도 타입
	private MapType mapType;
	
	/// 제목
	private String title;
	
	/// 설명
	private String description;
	
	/// 좌표계
	private String srs;
	
	/// 원점 X
	private Double originX;
	
	/// 원점 Y
	private Double originY;
	
	/// 지도 모드
	private String mapMode;
	
	/// 확장자
	private String extension;
	
	/// 높이
	private Integer height;
	
	/// 너비
	private Integer width;
	
	/// 요청 포맷
	private String requestFormat;
	
	/// 기본 여부
	private String baseYn;
	
	/// 기본 지도 호스트 목록
	private List<KwsBaseMapHost> kwsBaseMapHosts;
	
	/// 기본 지도 레벨 목록
	private List<KwsBaseMapLevel> kwsBaseMapLevels;
	
	/// 배경지도 사용여부
	private String baseMapYn;
	
	/// 레이어 사용여부
	private String layerYn;
	
	/// 촬영년도 
	private String photographyYear;
	
	/// 촬영기관
	private String photographyInstitution;
	
	/// 해상도
	private String resolution;
	
	/// 최소 X
	private Double minX;
	
	/// 최소 Y
	private Double minY;
	
	/// 최대 X
	private Double maxX;
	
	/// 최대 Y
	private Double maxY;	
	
	/// 주석
	private String cm;

	public String getCm() {
		return cm;
	}

	public void setCm(String cm) {
		this.cm = cm;
	}

	public Long getMapNo() {
		return mapNo;
	}

	public void setMapNo(Long mapNo) {
		this.mapNo = mapNo;
	}

	public MapType getMapType() {
		return mapType;
	}

	public void setMapType(MapType mapType) {
		this.mapType = mapType;
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

	public String getMapMode() {
		return mapMode;
	}

	public void setMapMode(String mapMode) {
		this.mapMode = mapMode;
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

	public String getBaseYn() {
		return baseYn;
	}

	public void setBaseYn(String baseYn) {
		this.baseYn = baseYn;
	}

	public List<KwsBaseMapHost> getKwsBaseMapHosts() {
		return kwsBaseMapHosts;
	}

	public void setKwsBaseMapHosts(List<KwsBaseMapHost> kwsBaseMapHosts) {
		this.kwsBaseMapHosts = kwsBaseMapHosts;
	}

	public List<KwsBaseMapLevel> getKwsBaseMapLevels() {
		return kwsBaseMapLevels;
	}

	public void setKwsBaseMapLevels(List<KwsBaseMapLevel> kwsBaseMapLevels) {
		this.kwsBaseMapLevels = kwsBaseMapLevels;
	}

	public String getBaseMapYn() {
		return baseMapYn;
	}

	public void setBaseMapYn(String baseMapYn) {
		this.baseMapYn = baseMapYn;
	}

	public String getLayerYn() {
		return layerYn;
	}

	public void setLayerYn(String layerYn) {
		this.layerYn = layerYn;
	}

	public String getPhotographyYear() {
		return photographyYear;
	}

	public void setPhotographyYear(String photographyYear) {
		this.photographyYear = photographyYear;
	}

	public String getPhotographyInstitution() {
		return photographyInstitution;
	}

	public void setPhotographyInstitution(String photographyInstitution) {
		this.photographyInstitution = photographyInstitution;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
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
	
}
