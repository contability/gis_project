package kr.co.gitt.kworks.rest.export;

import kr.co.gitt.kworks.model.KwsExport.ExportDataSe;

/////////////////////////////////////////////
/// @class ExportImageDTO
/// kr.co.gitt.kworks.rest.export \n
///   ㄴ ExportImageDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오전 11:58:57 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 이미지 내보내기 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class ExportImageDTO extends ExportCommonDTO {
	
	/// 내보내기 데이터 구분
	private ExportDataSe exportDtaSe;
	
	/// 내보내기 사유
	private String exportResn;
	
	/// 중심점 경도
	private Double centerLon;
	
	/// 중심전 위도
	private Double centerLat;
	
	/// 데이터 (Image : Base64 String) 
	private String data;
	
	/// 뷰 아이디
	private String viewId;
	
	/// TMS 타입
	private String tmsType;

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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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
	
}
