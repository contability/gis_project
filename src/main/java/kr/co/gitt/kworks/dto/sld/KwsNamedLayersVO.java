package kr.co.gitt.kworks.dto.sld;

/**
 * 
 * @author kwangsu.lee
 *
 */
public class KwsNamedLayersVO {
	
	//화면표시여부
	private String visible;
	
	//layer 수정여부
	private String isEdit;
	
	// 변경 여부 (한번이라도 변경되었으면 true)
	private Boolean isChanged;
	
	// layer name
	private String name;
	
	// layer title
	private String title;

	// layer data source
	private String source;
	
	// layer dataCategory
	private String dataCategory;
	
	// layer rules name
	private KwsRulesVO[] rules;
	
	// layer text
	private KwsTextSymbolVO text;
	
	
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	
	public String getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}
	
	public Boolean getIsChanged() {
		return isChanged;
	}
	public void setIsChanged(Boolean isChanged) {
		this.isChanged = isChanged;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getDataCategory() {
		return dataCategory;
	}
	public void setDataCategory(String dataCategory) {
		this.dataCategory = dataCategory;
	}
	
	public KwsRulesVO[] getRules() {
		return rules;
	}
	public void setRules(KwsRulesVO[] rules) {
		this.rules = rules;
	}
	
	public KwsTextSymbolVO getText() {
		return text;
	}
	public void setText(KwsTextSymbolVO text) {
		this.text = text;
	}
}
