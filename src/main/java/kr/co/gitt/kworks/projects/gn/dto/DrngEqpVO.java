package kr.co.gitt.kworks.projects.gn.dto;

/////////////////////////////////////////////
/// @class 
/// kr.co.gitt.kworks.projects.gn.dto
///   ㄴ DrngEqpVO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | sjlee |
///    | Date | 2020. 7. 28. 오후 5:08:34 |
///    | Class Version | v1.0 |
///    | 작업자 | sjlee, Others... |
/// @section 상세설명
/// - 배수설비인허가대장과 연관된 배수설비(물받이, 하수연결관) 정보를 위한 클래스 입니다.
/// -# 
/////////////////////////////////////////////
public class DrngEqpVO {

	private String dataId;
	
	private String dataAlias;
	
	private Long objectid;
	
	private String ftrCde;
	
	private Long ftrIdn;
	
	/// 물받이 용도, 하수연결관 용도
	private String prpos;

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

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public String getPrpos() {
		return prpos;
	}

	public void setPrpos(String prpos) {
		this.prpos = prpos;
	}
}
