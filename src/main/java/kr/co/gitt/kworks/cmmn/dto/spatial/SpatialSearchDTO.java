package kr.co.gitt.kworks.cmmn.dto.spatial;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/////////////////////////////////////////////
/// @class SpatialSearchDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ SpatialSearchDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 19. 오후 2:59:02 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 공간 검색 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class SpatialSearchDTO extends SearchDTO {
	
	/// 데이터 아이디
	private String dataId;
	
	/// 데이터 아이디 목록
	private List<String> dataIds;
	
	/// 필드 값을 원래 값으로 표현 할지 여부
	private Boolean isOriginColumnValue;
	
	/////////////////////////////////////////////
	/// @class FilterType
	/// kr.co.gitt.kworks.cmmn.dto.spatial \n
	///   ㄴ SpatialSearchDTO.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 11. 21. 오후 1:56:23 |
	///    | Class Version | v1.0 |
	///    | 작업자 | admin, Others... |
	/// @section 상세설명
	/// - 이 클래스는 필터 타입입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum FilterType {
		FID,
		FIDS,
		COMPARISON,
		BBOX,
		SPATIAL,
		RELATION,
		CQL
	}
	
	/// 필터 타입
	private FilterType filterType;
	
	/// 도형 아이디 필터
	private FilterFidDTO filterFidDTO;
	
	/// 도형 아이디 목록 필터
	private FilterFidsDTO filterFidsDTO;
	
	/// 비교 필터
	private FilterComparisonDTO filterComparisonDTO;
	
	/// 영역 필터
	private FilterBboxDTO filterBboxDTO;
	
	/// 공간 필터
	private FilterSpatialDTO filterSpatialDTO;
	
	/// 연관 필터
	private FilterRelationDTO filterRelationDTO;
	
	/// CQL 필터
	private FilterCqlDTO filterCqlDTO;
	
	/// 정렬 순서
	private String sortOrdr;
	
	/// 정렬 방향
	private String sortDirection;

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

	public Boolean getIsOriginColumnValue() {
		return isOriginColumnValue;
	}

	public void setIsOriginColumnValue(Boolean isOriginColumnValue) {
		this.isOriginColumnValue = isOriginColumnValue;
	}

	public FilterType getFilterType() {
		return filterType;
	}

	public void setFilterType(FilterType filterType) {
		this.filterType = filterType;
	}

	public FilterFidDTO getFilterFidDTO() {
		return filterFidDTO;
	}

	public void setFilterFidDTO(FilterFidDTO filterFidDTO) {
		this.filterFidDTO = filterFidDTO;
	}

	public FilterFidsDTO getFilterFidsDTO() {
		return filterFidsDTO;
	}

	public void setFilterFidsDTO(FilterFidsDTO filterFidsDTO) {
		this.filterFidsDTO = filterFidsDTO;
	}

	public FilterComparisonDTO getFilterComparisonDTO() {
		return filterComparisonDTO;
	}

	public void setFilterComparisonDTO(FilterComparisonDTO filterComparisonDTO) {
		this.filterComparisonDTO = filterComparisonDTO;
	}

	public FilterBboxDTO getFilterBboxDTO() {
		return filterBboxDTO;
	}

	public void setFilterBboxDTO(FilterBboxDTO filterBboxDTO) {
		this.filterBboxDTO = filterBboxDTO;
	}

	public FilterSpatialDTO getFilterSpatialDTO() {
		return filterSpatialDTO;
	}

	public void setFilterSpatialDTO(FilterSpatialDTO filterSpatialDTO) {
		this.filterSpatialDTO = filterSpatialDTO;
	}

	public FilterRelationDTO getFilterRelationDTO() {
		return filterRelationDTO;
	}

	public void setFilterRelationDTO(FilterRelationDTO filterRelationDTO) {
		this.filterRelationDTO = filterRelationDTO;
	}

	public FilterCqlDTO getFilterCqlDTO() {
		return filterCqlDTO;
	}

	public void setFilterCqlDTO(FilterCqlDTO filterCqlDTO) {
		this.filterCqlDTO = filterCqlDTO;
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

}
