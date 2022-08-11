package kr.co.gitt.kworks.rest.export;

import java.util.List;

import kr.co.gitt.kworks.model.KwsExport.ExportFilterTy;

/////////////////////////////////////////////
/// @class ExportDataDTO
/// kr.co.gitt.kworks.rest.export \n
///   ㄴ ExportDataDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오전 11:58:46 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 내보내기 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class ExportDataDTO extends ExportCommonDTO {

	/// 내보내기 필터 타입
	private ExportFilterTy exportFilterTy;
	
	/// 내보내기 좌표계 아이디
	private String exportCntmId;
	
	/// xmin
	private Double minX;
	
	/// ymin
	private Double minY;
	
	/// xmax
	private Double maxX;
	
	/// ymax
	private Double maxY;
	
	/// 동 필터
	private Long dongId;
	
	/// 필드 명 표시 여부
	private Boolean fieldNmIndictAt;
	
	/// ID 목록
	private List<Long> ids;
	
	/// WKT
	private String wkt;

	public ExportFilterTy getExportFilterTy() {
		return exportFilterTy;
	}

	public void setExportFilterTy(ExportFilterTy exportFilterTy) {
		this.exportFilterTy = exportFilterTy;
	}

	public String getExportCntmId() {
		return exportCntmId;
	}

	public void setExportCntmId(String exportCntmId) {
		this.exportCntmId = exportCntmId;
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

	public Long getDongId() {
		return dongId;
	}

	public void setDongId(Long dongId) {
		this.dongId = dongId;
	}

	public Boolean getFieldNmIndictAt() {
		return fieldNmIndictAt;
	}

	public void setFieldNmIndictAt(Boolean fieldNmIndictAt) {
		this.fieldNmIndictAt = fieldNmIndictAt;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getWkt() {
		return wkt;
	}

	public void setWkt(String wkt) {
		this.wkt = wkt;
	}
	
}
