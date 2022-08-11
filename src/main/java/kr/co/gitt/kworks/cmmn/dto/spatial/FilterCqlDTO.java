package kr.co.gitt.kworks.cmmn.dto.spatial;

/////////////////////////////////////////////
/// @class FilterCqlDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ FilterCqlDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 10. 12. 오전 10:54:46 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 CQL 필터 입니다.
///   -# 
/////////////////////////////////////////////
public class FilterCqlDTO {

	/// CQL
	private String cql;

	public String getCql() {
		return cql;
	}

	public void setCql(String cql) {
		this.cql = cql;
	}
	
}
