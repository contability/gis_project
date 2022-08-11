package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsSysDataCtgry
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsSysDataCtgry.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 10. 31. 오후 12:02:06 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 시스템 데이터 카테고리 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsSysDataCtgry {

	/// 시스템 아이디
	private Long sysId;
	
	/// 데이터 카테고리 아이디
	private String dataCtgryId;

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public String getDataCtgryId() {
		return dataCtgryId;
	}

	public void setDataCtgryId(String dataCtgryId) {
		this.dataCtgryId = dataCtgryId;
	}
	
}
