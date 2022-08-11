package kr.co.gitt.kworks.cmmn.dto.spatial;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/////////////////////////////////////////////
/// @class SpatialSearchResultDTO
/// kr.co.gitt.kworks.cmmn.dto.spatial \n
///   ㄴ SpatialSearchResultDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 1. 오후 4:28:37 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 공간 검색 결과 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class SpatialSearchResultDTO {

	/// 데이터 아이디
	private String dataId;
	
	/// 결과 목록
	private List<EgovMap> rows;

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public List<EgovMap> getRows() {
		return rows;
	}

	public void setRows(List<EgovMap> rows) {
		this.rows = rows;
	}
	
}
