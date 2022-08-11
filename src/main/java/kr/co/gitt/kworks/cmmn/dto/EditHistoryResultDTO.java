package kr.co.gitt.kworks.cmmn.dto;

import java.util.Date;

import kr.co.gitt.kworks.model.KwsEditHist.EditType;

/////////////////////////////////////////////
/// @class EditHistoryResultDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ EditHistoryResultDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 22. 오후 4:47:13 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 편집 이력 결과 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class EditHistoryResultDTO {

	/// 이력 번호
	private Long histNo;
	
	/// 시스템 별칭
	private String sysAlias;
	
	/// 데이터 별칭
	private String dataAlias;
	
	/// 편집 타입
	private EditType editType;
	
	// 편집자
	private String editorNm;

	/// 법정동명
	private String bjdNam;
	
	/// 편집 일시
	private Date editDt;

	public Long getHistNo() {
		return histNo;
	}

	public void setHistNo(Long histNo) {
		this.histNo = histNo;
	}

	public String getSysAlias() {
		return sysAlias;
	}

	public void setSysAlias(String sysAlias) {
		this.sysAlias = sysAlias;
	}

	public String getDataAlias() {
		return dataAlias;
	}

	public void setDataAlias(String dataAlias) {
		this.dataAlias = dataAlias;
	}

	public EditType getEditType() {
		return editType;
	}

	public void setEditType(EditType editType) {
		this.editType = editType;
	}

	public String getBjdNam() {
		return bjdNam;
	}

	public void setBjdNam(String bjdNam) {
		this.bjdNam = bjdNam;
	}

	public Date getEditDt() {
		return editDt;
	}

	public void setEditDt(Date editDt) {
		this.editDt = editDt;
	}
	
	public String getEditorNm() {
		return editorNm;
	}

	public void setEditorNm(String editorNm) {
		this.editorNm = editorNm;
	}
	
}
