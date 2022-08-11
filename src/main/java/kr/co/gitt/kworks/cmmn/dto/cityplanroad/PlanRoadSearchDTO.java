package kr.co.gitt.kworks.cmmn.dto.cityplanroad;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/**
 * 도시계획도로 검색 DTO
 * @author kwangsu.lee
 *
 */
public class PlanRoadSearchDTO extends SearchDTO {

	/**
	 * 원래 값으로 표현 여부
	 */
	private Boolean isOriginValue;
	
	/**
	 * 정렬 순서
	 */
	private String sortOrdr;
	
	/**
	 * 정렬 방향
	 */
	private String sortDirection;
	
	/**
	 * 구역명
	 */
	private String name;
	
	/**
	 * 등급
	 */
	private String grade;
	
	/**
	 * 류별
	 */
	private Integer type;
	
	/**
	 * 번호
	 */
	private String num;
	
	
	public Boolean getIsOriginValue() {
		return isOriginValue;
	}

	public void setIsOriginValue(Boolean isOriginValue) {
		this.isOriginValue = isOriginValue;
	}
	
	public String getSortOrdr() {
		return sortOrdr;
	}

	public void setSortOrdr(String sortOrdr) {
		this.sortOrdr = sortOrdr;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGrade() {
		return grade;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}

	public String getNum() {
		return num;
	}
	
	public void setNum(String num) {
		this.num = num;
	}
}
