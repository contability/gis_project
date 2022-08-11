package kr.co.gitt.kworks.cmmn.dto;

/////////////////////////////////////////////
/// @class CntcDataIdDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ CntcDataIdDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 19. 오후 2:44:56 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 연계 데이터 아이디 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class CntcDataDTO {

	/// 데이터 아이디
	private String dataId;
	
	/// 데이터 별칭
	private String dataAlias;
	
	/// 데이터 수
	private Long dataCo;

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

	public Long getDataCo() {
		return dataCo;
	}

	public void setDataCo(Long dataCo) {
		this.dataCo = dataCo;
	}
	
}
