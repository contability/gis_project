package kr.co.gitt.kworks.projects.gn.dto;

import java.util.List;


public class OcpatRegisterSortDTO {

	// 점용허가대장 아이디 
	private String ocpatIdn;

	// 레이어
	private String layerId;
	
	// 관리번호 목록
	private List<String> featureIds;
	
	// 관리번호 필드
	private String idnField;

	// 정렬 필드
	private String sortField;
	
	// 정렬 방향
	private String sortDirection;

	// 도메인 적용 여부
	private Boolean isOriginValue;

	
	public String getOcpatIdn() {
		return ocpatIdn;
	}

	public void setOcpatIdn(String ocpatIdn) {
		this.ocpatIdn = ocpatIdn;
	}

	public String getLayerId() {
		return layerId;
	}

	public void setLayerId(String layerId) {
		this.layerId = layerId;
	}
	
	public List<String> getFeatureIds() {
		return featureIds;
	}

	public void setFeatureIds(List<String> featureIds) {
		this.featureIds = featureIds;
	}

	public String getIdnField() {
		return idnField;
	}

	public void setIdnField(String idnField) {
		this.idnField = idnField;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	
	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	
	public Boolean getIsOriginValue() {
		return isOriginValue;
	}

	public void setIsOriginValue(Boolean isOriginValue) {
		this.isOriginValue = isOriginValue;
	}
}