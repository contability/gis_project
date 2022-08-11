package kr.co.gitt.kworks.dto.cntrwkRegstr;

/////////////////////////////////////////////
/// @class FacilityResultDTO
/// kr.co.gitt.kworks.dto.cntrwkRegstr \n
///   ㄴ FacilityResultDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 7. 28. 오전 11:52:10 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 공사대장 시설물 조회 결과 입니다.
///   -# 
/////////////////////////////////////////////
public class FacilityResultDTO {

	/// 데이어 타이디 
	private String dataId;
	
	/// 시설물 아이디 
	private Long ftrIdn;

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}
	
}
