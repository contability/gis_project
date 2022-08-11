package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsData
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsRoadReg.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2019. 7. 25. 오전 11:38:10 |
///    | Class Version | v1.0 |
///    | 작업자 | lks, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsRoadReg {

	/// 도로대장 아이디 
	private Integer regIdn;
	
	/// 도로대장 명칭
	private String regAlias;
	
	/// 도로대장 레이어 아이디
	private String regLayerId;
	
	/// 도로대장 조건 필드
	private String regPropField;
	
	/// 도로대장 조건 값
	private String regPropValue;
	
	/// 도로대장 레이어 아이디
	private String regSubId;
	
	/// 도로대장 조건 필드
	private String regSubField;
	
	/// 도로대장 조건 값
	private String regSubValue;

	/// 도로대장 조서검색 사용여부
	private String useAt;
	

	public Integer getRegIdn() {
		return regIdn;
	}

	public void setRegIdn(Integer regIdn) {
		this.regIdn = regIdn;
	}

	public String getRegAlias() {
		return regAlias;
	}

	public void setRegAlias(String regAlias) {
		this.regAlias = regAlias;
	}

	public String getRegLayerId() {
		return regLayerId;
	}

	public void setRegLayerId(String regLayerId) {
		this.regLayerId = regLayerId;
	}	
	
	public String getRegPropField() {
		return regPropField;
	}

	public void setRegPropField(String regPropField) {
		this.regPropField = regPropField;
	}
	
	public String getRegPropValue() {
		return regPropValue;
	}

	public void setRegPropValue(String regPropValue) {
		this.regPropValue = regPropValue;
	}	
	
	public String getRegSubId() {
		return regSubId;
	}

	public void setRegSubId(String regSubId) {
		this.regSubId = regSubId;
	}	

	public String getRegSubField() {
		return regSubField;
	}

	public void setRegSubField(String regSubField) {
		this.regSubField = regSubField;
	}
	
	public String getRegSubValue() {
		return regSubValue;
	}

	public void setRegSubValue(String regSubValue) {
		this.regSubValue = regSubValue;
	}
	
	public String getUseAt() {
		return this.useAt;
	}
	
	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
}
