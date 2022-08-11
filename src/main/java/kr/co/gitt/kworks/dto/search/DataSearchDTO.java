package kr.co.gitt.kworks.dto.search;

import java.util.List;

/////////////////////////////////////////////
/// @class DataSearchDTO
/// kr.co.gitt.kworks.dto.search \n
///   ㄴ DataSearchDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 3. 15. 오전 11:22:36 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 검색 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class DataSearchDTO {
	
	/// 내보내기 권한 여부
	private String exportAuthorAt;

	/// 데이터 목록
	private List<String> dataIds;

	public String getExportAuthorAt() {
		return exportAuthorAt;
	}

	public void setExportAuthorAt(String exportAuthorAt) {
		this.exportAuthorAt = exportAuthorAt;
	}

	public List<String> getDataIds() {
		return dataIds;
	}

	public void setDataIds(List<String> dataIds) {
		this.dataIds = dataIds;
	}

	
}
