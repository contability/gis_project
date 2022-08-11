package kr.co.gitt.kworks.projects.gn.dto;

import java.util.List;

/**
 * 도로대장 통합검색 DTO 
 * @author kwangsu.lee
 *
 */
public class RoadRegisterSearchSummaryDTO {

	// 도로대장 조서 아이디 
	private String regIdn;

	// 도로대장 조서 명칭 
	private String regAlias;
	
	// 검색 결과 목록
	private List<RoadRegisterSearchResultDTO> featureIds;
	
	private String dataId;
	
	private List<String> dataIds;
	
	public String getRegIdn() {
		return regIdn;
	}

	public void setRegIdn(String regIdn) {
		this.regIdn = regIdn;
	}

	public String getRegAlias() {
		return regAlias;
	}

	public void setRegAlias(String regAlias) {
		this.regAlias = regAlias;
	}
	
	public List<RoadRegisterSearchResultDTO> getFeatureIds() {
		return featureIds;
	}

	public void setFeatureIds(List<RoadRegisterSearchResultDTO> featureIds) {
		this.featureIds = featureIds;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public List<String> getDataIds() {
		return dataIds;
	}

	public void setDataIds(List<String> dataIds) {
		this.dataIds = dataIds;
	}	
	
}
