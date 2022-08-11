package kr.co.gitt.kworks.projects.gs.dto;

import java.util.List;

/**
 * 생태현황도 통합검색 DTO
 * @author wongi.jo
 *
 */
public class EcologyAttrDTO {

	/**
	 * 데이터 목록
	 */
	private List<String> dataIds;
	
	private String dataId;
	
	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	private String pnu;

	public List<String> getDataIds() {
		return dataIds;
	}

	public void setDataIds(List<String> dataIds) {
		this.dataIds = dataIds;
	}

	public String getPnu() {
		return pnu;
	}

	public void setPnu(String pnu) {
		this.pnu = pnu;
	}
	
	
	
}
