package kr.co.gitt.kworks.projects.gn.dto;

import java.util.List;

/**
 * 점용대장 통합검색 DTO 
 * @author kwangsu.lee
 *
 */
public class OcpatRegisterSearchSummaryDTO {

	// 점용대장  아이디 
	private String ocpatIdn;

	// 점용대장 명칭 
	private String ocpatAlias;
	
	// 레이어 아이디
	private String layerId;

	// 검색 결과 목록
	private List<OcpatRegisterSearchResultDTO> featureIds;
	
	
	public String getOcpatIdn() {
		return ocpatIdn;
	}

	public void setOcpatIdn(String ocpatIdn) {
		this.ocpatIdn = ocpatIdn;
	}

	public String getOcpatAlias() {
		return ocpatAlias;
	}

	public void setOcpatAlias(String ocpatAlias) {
		this.ocpatAlias = ocpatAlias;
	}

	public String getLayerId() {
		return layerId;
	}

	public void setLayerId(String layerId) {
		this.layerId = layerId;
	}
	
	public List<OcpatRegisterSearchResultDTO> getFeatureIds() {
		return featureIds;
	}

	public void setFeatureIds(List<OcpatRegisterSearchResultDTO> featureIds) {
		this.featureIds = featureIds;
	}
}
