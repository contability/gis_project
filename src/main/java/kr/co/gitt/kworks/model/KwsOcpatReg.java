package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsOcpatReg
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsOcpatReg.java
/// @section 상세설명
/// - 이 클래스는 데이터 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsOcpatReg {

	///  아이디 
	private Integer ocpatIdn;
	
	///  명칭
	private String ocpatAlias;
	
	///  레이어 아이디
	private String ocpatLayerId;
	
	///  조건 필드
	private String ocpatPropField;
	
	/// 조건 값
	private String ocpatPropValue;
	
	///  점형 레이어 아이디
	private String ocpatPsLayer;

	///  선형 레이어 아이디
	private String ocpatLsLayer;
	
	///  면형 레이어 아이디
	private String ocpatAsLayer;
	
	///  지형지물부호 필드
	private String ocpatCdeField;
	
	/// 관리번호 필드
	private String ocpatIdnField;

	/// 사용여부
	private String useAt;
	

	public Integer getOcpatIdn() {
		return ocpatIdn;
	}

	public void setOcpatIdn(Integer ocpatIdn) {
		this.ocpatIdn = ocpatIdn;
	}

	public String getOcpatAlias() {
		return ocpatAlias;
	}

	public void setOcpatAlias(String ocpatAlias) {
		this.ocpatAlias = ocpatAlias;
	}

	public String getOcpatLayerId() {
		return ocpatLayerId;
	}

	public void setOcpatLayerId(String ocpatLayerId) {
		this.ocpatLayerId = ocpatLayerId;
	}

	public String getOcpatPropField() {
		return ocpatPropField;
	}

	public void setOcpatPropField(String ocpatPropField) {
		this.ocpatPropField = ocpatPropField;
	}

	public String getOcpatPropValue() {
		return ocpatPropValue;
	}

	public void setOcpatPropValue(String ocpatPropValue) {
		this.ocpatPropValue = ocpatPropValue;
	}

	public String getOcpatPsLayer() {
		return ocpatPsLayer;
	}

	public void setOcpatPsLayer(String ocpatPsLayer) {
		this.ocpatPsLayer = ocpatPsLayer;
	}

	public String getOcpatLsLayer() {
		return ocpatLsLayer;
	}

	public void setOcpatLsLayer(String ocpatLsLayer) {
		this.ocpatLsLayer = ocpatLsLayer;
	}

	public String getOcpatAsLayer() {
		return ocpatAsLayer;
	}

	public void setOcpatAsLayer(String ocpatAsLayer) {
		this.ocpatAsLayer = ocpatAsLayer;
	}
	
	public String getOcpatCdeField() {
		return ocpatCdeField;
	}

	public void setOcpatSubField(String ocpatCdeField) {
		this.ocpatCdeField = ocpatCdeField;
	}

	public String getOcpatIdnField() {
		return ocpatIdnField;
	}

	public void setOcpatSubValue(String ocpatIdnField) {
		this.ocpatIdnField = ocpatIdnField;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
}
