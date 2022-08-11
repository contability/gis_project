package kr.co.gitt.kworks.projects.gn.dto;

import java.util.List;

/**
 * 도로대장 검색결과 DTO 
 * @author kwangsu.lee
 *
 */
public class RoadRegisterSearchResultDTO {
	
	/// 데이터 아이디 
	private String dataId;
	
	/// 데이터 가명 
	private String dataAlias;

	/// 도형 아이디 목록
	private List<String> ids;

	/// 관리번호 목록
	private List<String> ftrIdn;

	
	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getDataAlias() {
		return dataAlias;
	}

	public void setDataAlias(String dataAlias) {
		this.dataAlias = dataAlias;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
	public List<String> getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(List<String> ftrIdn) {
		this.ftrIdn = ftrIdn;
	}
}