package kr.co.gitt.kworks.rest.export;

import kr.co.gitt.kworks.model.KwsExport.ExportDataSe;

/////////////////////////////////////////////
/// @class ExportOutputDTO
/// kr.co.gitt.kworks.rest.export \n
///   ㄴ ExportOutputDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오전 11:59:45 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 출력 내보내기 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class ExportOutputDTO extends ExportCommonDTO {
	
	/// 레이아웃 아이디 
	private Long layoutId;
	
	/// 내보내기 좌표계 아이디
	private String exportCntmId;
	
	/// 내보내기 데이터 구분
	private ExportDataSe exportDtaSe;
	
	/// 내보내기 사유
	private String exportResn;
	
	/// 중심점 경도
	private Double centerLon;
	
	/// 중심전 위도
	private Double centerLat;
	
	/// 중심점 X
	private Double centerX;
	
	/// 중심점 Y
	private Double centerY;
	
	/// min X
	private Double minX;
	
	/// min Y
	private Double minY;
	
	/// max X
	private Double maxX;
	
	/// max Y
	private Double maxY;
	
	/// 축척
	private Integer sc;
	
	/// 뷰 아이디
	private String viewId;
	
	/// TMS 타입
	private String tmsType;
	
	/// SLD
	private String sld;
	
	/// 항공사진
	private String baseMaps;

	/// 사용자 그래픽
	private String userGraphics;
	
	/// Lks : 항공영상 원점 X
	private Double originX;
	
	/// Lks : 항공영상 원점 Y
	private Double originY;
	
	/// flag
	private Character flag;
	
	
	public Long getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(Long layoutId) {
		this.layoutId = layoutId;
	}

	public String getExportCntmId() {
		return exportCntmId;
	}

	public void setExportCntmId(String exportCntmId) {
		this.exportCntmId = exportCntmId;
	}

	public ExportDataSe getExportDtaSe() {
		return exportDtaSe;
	}

	public void setExportDtaSe(ExportDataSe exportDtaSe) {
		this.exportDtaSe = exportDtaSe;
	}

	public String getExportResn() {
		return exportResn;
	}

	public void setExportResn(String exportResn) {
		this.exportResn = exportResn;
	}

	public Double getCenterLon() {
		return centerLon;
	}

	public void setCenterLon(Double centerLon) {
		this.centerLon = centerLon;
	}

	public Double getCenterLat() {
		return centerLat;
	}

	public void setCenterLat(Double centerLat) {
		this.centerLat = centerLat;
	}

	public Double getCenterX() {
		return centerX;
	}

	public void setCenterX(Double centerX) {
		this.centerX = centerX;
	}

	public Double getCenterY() {
		return centerY;
	}

	public void setCenterY(Double centerY) {
		this.centerY = centerY;
	}

	public Integer getSc() {
		return sc;
	}

	public void setSc(Integer sc) {
		this.sc = sc;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public String getTmsType() {
		return tmsType;
	}

	public void setTmsType(String tmsType) {
		this.tmsType = tmsType;
	}

	public String getSld() {
		return sld;
	}

	public void setSld(String sld) {
		this.sld = sld;
	}

	public String getBaseMaps() {
		return baseMaps;
	}

	public void setBaseMaps(String baseMaps) {
		this.baseMaps = baseMaps;
	}

	public String getUserGraphics() {
		return userGraphics;
	}

	public void setUserGraphics(String userGraphics) {
		this.userGraphics = userGraphics;
	}
	
	public Double getOriginX() {
		return originX;
	}

	public void setOriginX(Double originX) {
		this.originX = originX;
	}

	public void setOriginY(Double originY) {
		this.originY = originY;
	}

	public Double getOriginY() {
		return originY;
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

	public Character getFlag() {
		return flag;
	}

	public void setFlag(Character flag) {
		this.flag = flag;
	}
	
}
