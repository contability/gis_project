package kr.co.gitt.kworks.model;

public class KwsExportFilterImage {

	/// 내보내기 번호
	private Long exportNo;
	
	/// 뷰 아이디
	private String viewId;
	
	/// TMS 타입
	private String tmsType;

	public Long getExportNo() {
		return exportNo;
	}

	public void setExportNo(Long exportNo) {
		this.exportNo = exportNo;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public String getTmsType() {
		return tmsType;
	}

	public void setTmsType(String tmsType) {
		this.tmsType = tmsType;
	}
	
}
