package kr.co.gitt.kworks.projects.dh.dto;

import java.util.List;

/**
 * 도시계획도로 검색
 * @author kwangsu.lee
 *
 */
public class CityPlanRoadSearchDTO {
	
	/**
	 * 국도명
	 */
	private String uprNam;

	/**
	 * 구분
	 */
	private String uprGrd;

	/**
	 * 분류
	 */
	private String uprTyp;

	/**
	 * 노선번호
	 */
	private String uprNum;

	// 관리번호 목록
	private List<String> ids;

	// 정렬 필드
	private String sortField;
	
	// 정렬 방향
	private String sortDirection;

	// 도메인 적용 여부
	private Boolean isOriginValue;
	
	
	public String getUprNam() {
		return uprNam;
	}

	public void setUprNam(String uprNam) {
		this.uprNam = uprNam;
	}

	public String getUprGrd() {
		return uprGrd;
	}

	public void setUprGrd(String uprGrd) {
		this.uprGrd = uprGrd;
	}

	public String getUprTyp() {
		return uprTyp;
	}

	public void setUprTyp(String uprTyp) {
		this.uprTyp = uprTyp;
	}

	public String getUprNum() {
		return uprNum;
	}

	public void setUprNum(String uprNum) {
		this.uprNum = uprNum;
	}
	
	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
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
