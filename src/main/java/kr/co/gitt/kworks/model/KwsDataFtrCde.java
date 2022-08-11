package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsDataFtrcdeMapper
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsDataFtrcdeMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 11. 오후 3:16:28 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터, 지형지물부호 맵퍼입니다.
///   -# 
/////////////////////////////////////////////
public class KwsDataFtrCde {

	/// 데이터 아이디
	private String dataId;
	
	/// 코드 아이디
	private String codeId;

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	
}
