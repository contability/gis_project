package kr.co.gitt.kworks.dto.sld;

public class KWSDataFieldVO {
	
	private String dataCtgryId;
	
	private String dataId;
	
	private String dataAlias;
	
	private String fieldId;
	
	private String fieldAlias;
	
	private String fieldTy;
	
	private String domainAt;
	
	private String domnId; 
	
	private Short textFieldLt;
	
	private Short numFieldLt;
	
	private Short numDcmlpoint;

	private Integer listViewWidth;
	
	private Integer fieldOrdr;
	
	private String pkAt;
	
	private String dfltValue;
	
	public Integer getListViewWidth() {
		return listViewWidth;
	}

	public void setListViewWidth(Integer listViewWidth) {
		this.listViewWidth = listViewWidth;
	}

	public String getDataCtgryId() {
		return dataCtgryId;
	}

	public void setDataCtgryId(String dataCtgryId) {
		this.dataCtgryId = dataCtgryId;
	}

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

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldAlias() {
		return fieldAlias;
	}

	public void setFieldAlias(String fieldAlias) {
		this.fieldAlias = fieldAlias;
	}

	public String getFieldTy() {
		return fieldTy;
	}

	public void setFieldTy(String fieldTy) {
		this.fieldTy = fieldTy;
	}

	public String getDomainAt() {
		return domainAt;
	}

	public void setDomainAt(String domainAt) {
		this.domainAt = domainAt;
	}

	public String getDomnId() {
		return domnId;
	}

	public void setDomnId(String domnId) {
		this.domnId = domnId;
	}

	public Short getTextFieldLt() {
		return textFieldLt;
	}

	public void setTextFieldLt(Short textFieldLt) {
		this.textFieldLt = textFieldLt;
	}

	public Short getNumFieldLt() {
		return numFieldLt;
	}

	public void setNumFieldLt(Short numFieldLt) {
		this.numFieldLt = numFieldLt;
	}

	public Short getNumDcmlpoint() {
		return numDcmlpoint;
	}

	public void setNumDcmlpoint(Short numDcmlpoint) {
		this.numDcmlpoint = numDcmlpoint;
	}

	public Integer getFieldOrdr() {
		return fieldOrdr;
	}

	public void setFieldOrdr(Integer fieldOrdr) {
		this.fieldOrdr = fieldOrdr;
	}

	public String getPkAt() {
		return pkAt;
	}

	public void setPkAt(String pkAt) {
		this.pkAt = pkAt;
	}

	public String getDfltValue() {
		return dfltValue;
	}

	public void setDfltValue(String dfltValue) {
		this.dfltValue = dfltValue;
	}
}
