package kr.co.gitt.kworks.cmmn.dto.spatial;

import java.util.List;

/////////////////////////////////////////////
/// @class SpatialSearchSummaryDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ SpatialSearchSummaryDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 21. 오후 8:52:10 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 공간 검색 요약 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class SpatialSearchSummaryDTO {
	
	/// 데이터 아이디 
	private String dataId;
	
	/// 데이터 가명 
	private String dataAlias;
	
	/// 도형 아이디 목록
	private List<Long> ids;

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

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

}
