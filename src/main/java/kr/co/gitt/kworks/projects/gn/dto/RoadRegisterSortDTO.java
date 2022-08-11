package kr.co.gitt.kworks.projects.gn.dto;

import java.util.List;


public class RoadRegisterSortDTO {

	// 도로대장 조서 아이디 
	private String regIdn;

	// 레이어 목록
	private List<String> layerIds;
	
	// 레이어 목록
	private List<List<String>> featureIds;
	
	// 관리번호 목록
	private List<List<String>> ftrIdn;

	private Boolean isOriginValue;

	// 정렬 필드
	private String sortField;
	
	// 정렬 방향
	private String sortDirection;
	
	
	public String getRegIdn() {
		return regIdn;
	}

	public void setRegIdn(String regIdn) {
		this.regIdn = regIdn;
	}
	
	public List<String> getLayerIds() {
		return layerIds;
	}

	public void setLayerIds(List<String> layerIds) {
		this.layerIds = layerIds;
	}
	
	public List<List<String>> getFeatureIds() {
		return featureIds;
	}

	public void setFeatureIds(List<List<String>> featureIds) {
		this.featureIds = featureIds;
	}

	public List<List<String>> getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(List<List<String>> ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public Boolean getIsOriginValue() {
		return isOriginValue;
	}

	public void setIsOriginValue(Boolean isOriginValue) {
		this.isOriginValue = isOriginValue;
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
}