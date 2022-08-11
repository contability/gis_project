package kr.co.gitt.kworks.service.export;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/////////////////////////////////////////////
/// @class DxfExportDTO
/// kworks.map.file.vo \n
///   ㄴ DxfExportDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 6. 14. 오전 10:49:51 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 Dxf 내보내기 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class DxfExportDTO {
	
	/// 데이터 아이디 
	private String dataId;
	
	/// 데이터 목록
	private List<EgovMap> data;
	
	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public List<EgovMap> getData() {
		return data;
	}

	public void setData(List<EgovMap> data) {
		this.data = data;
	}
	
}
