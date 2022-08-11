package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsDataField
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsDataField.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 18. 오전 11:03:08 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 필드 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsDataField {
	
	/////////////////////////////////////////////
	/// @class FieldTy
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsDataField.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 8. 18. 오후 3:49:23 |
	///    | Class Version | v1.0 |
	///    | 작업자 | libraleo, Others... |
	/// @section 상세설명
	/// - 이 클래스는 필드 타입 입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum FieldTy {
		STRING,				// 문자형
		NUMBER,				// 숫자형
		DATE,				// 날짜형
		DATE_FROM_STRING,	// 문자열형 날짜
		GEOMETRY			// 공간형
	}
	
	// ///////////////////////////////////////////
	// / @class IndictTy
	// / kr.co.gitt.kworks.model \n
	// / ㄴ KwsDataField.java
	// / @section 클래스작성정보
	// / | 항 목 | 내 용 |
	// / | :-------------: | ------------- |
	// / | Company | (주)GittGDS |
	// / | Author | admin |
	// / | Date | 2016. 8. 18. 오후 3:49:26 |
	// / | Class Version | v1.0 |
	// / | 작업자 | libraleo, Others... |
	// / @section 상세설명
	// / - 이 클래스는 표시 타입 입니다.
	// / -#
	// ///////////////////////////////////////////
	public enum IndictTy {
		HIDE,				// 숨김
		BASIS, 				// 기본
		DATE_FROM_STRING, 	// 문자열형 날짜 (2016-01-01)
		DOMAIN, 			// 도메인
		CURRENCY, 			// 통화
		CUSTOM, 			// 관습
		WKT,				// WKT (공간 정보 텍스트)
		VIEW				// 뷰 전용
	}

	/// 데이터 아이디
	private String dataId;
	
	/// 필드 아이디
	private String fieldId;
	
	/// 필드 카테고리 아이디
	private String fieldCtgryId;
	
	/// 필드 가명
	private String fieldAlias;
	
	/// 필드 순서
	private Integer fieldOrdr;
	
	/// 필드 타입
	private FieldTy fieldTy;
	
	/// 필드 길이
	private Integer fieldLt;
	
	/// 소수 길이
	private Integer dcmlLt;
	
	/// PK 여부
	private String pkAt;
	
	/// NULL 여부
	private String nullAt;
	
	/// 단위
	private String unit;
	
	/// 도메인 아이디
	private String domnId;
	
	/// DEFAULT 값
	private String dfltValue;
	
	/// 표시 타입
	private IndictTy indictTy;
	
	/// 관습 SQL
	private String customSql;
	
	/// 편집 여부
	private String editAt;
	
	/// sysId에 따른 필드 구분
	private String sysTy;

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldCtgryId() {
		return fieldCtgryId;
	}

	public void setFieldCtgryId(String fieldCtgryId) {
		this.fieldCtgryId = fieldCtgryId;
	}

	public String getFieldAlias() {
		return fieldAlias;
	}

	public void setFieldAlias(String fieldAlias) {
		this.fieldAlias = fieldAlias;
	}

	public Integer getFieldOrdr() {
		return fieldOrdr;
	}

	public void setFieldOrdr(Integer fieldOrdr) {
		this.fieldOrdr = fieldOrdr;
	}

	public FieldTy getFieldTy() {
		return fieldTy;
	}

	public void setFieldTy(FieldTy fieldTy) {
		this.fieldTy = fieldTy;
	}

	public Integer getFieldLt() {
		return fieldLt;
	}

	public void setFieldLt(Integer fieldLt) {
		this.fieldLt = fieldLt;
	}

	public Integer getDcmlLt() {
		return dcmlLt;
	}

	public void setDcmlLt(Integer dcmlLt) {
		this.dcmlLt = dcmlLt;
	}

	public String getPkAt() {
		return pkAt;
	}

	public void setPkAt(String pkAt) {
		this.pkAt = pkAt;
	}

	public String getNullAt() {
		return nullAt;
	}

	public void setNullAt(String nullAt) {
		this.nullAt = nullAt;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDomnId() {
		return domnId;
	}

	public void setDomnId(String domnId) {
		this.domnId = domnId;
	}

	public String getDfltValue() {
		return dfltValue;
	}

	public void setDfltValue(String dfltValue) {
		this.dfltValue = dfltValue;
	}

	public IndictTy getIndictTy() {
		return indictTy;
	}

	public void setIndictTy(IndictTy indictTy) {
		this.indictTy = indictTy;
	}

	public String getCustomSql() {
		return customSql;
	}

	public void setCustomSql(String customSql) {
		this.customSql = customSql;
	}

	public String getEditAt() {
		return editAt;
	}

	public void setEditAt(String editAt) {
		this.editAt = editAt;
	}

	public String getSysTy() {
		return sysTy;
	}

	public void setSysTy(String sysTy) {
		this.sysTy = sysTy;
	}
	
}
