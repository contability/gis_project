package kr.co.gitt.kworks.model;

public class RdtPcbsDt {
	//기준점 번호
	private String serIdn;
	//지형지물부호
	private String ftrCde;
	//관리번호
	private Long ftrIdn;
	//조사년월일
	private String examinDe;
	//사유
	private String examinResn;
	//내용
	private String examinCn;
	//처리의견
	private String processOpinion;
	//보고대상년도
	private String reportTrgetYear;
	//조사결과
	private String examinResult;
	//근경사진_ID
	private String clsRngViewImgId;
	//원경사진_ID
	private String dstntViewImgId;
	//기타사진_ID
	private String etcImgId;
	//조사자 직급 및 성명
	private String exmnrInfo;
	//비고
	private String remark;
	/// 근경
	private KwsImage closeRangeViewImage;
	/// 원경
	private KwsImage distantViewImage;
	/// 기타사진
	private KwsImage tempViewImage;
	
	// 조사결과(한글)
	private String examinResultNm;
	
	public String getSerIdn() {
		return serIdn;
	}
	public void setSerIdn(String serIdn) {
		this.serIdn = serIdn;
	}
	public String getFtrCde() {
		return ftrCde;
	}
	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}
	public Long getFtrIdn() {
		return ftrIdn;
	}
	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}
	public String getExaminDe() {
		return examinDe;
	}
	public void setExaminDe(String examinDe) {
		this.examinDe = examinDe;
	}
	public String getExaminResn() {
		return examinResn;
	}
	public void setExaminResn(String examinResn) {
		this.examinResn = examinResn;
	}
	public String getExaminCn() {
		return examinCn;
	}
	public void setExaminCn(String examinCn) {
		this.examinCn = examinCn;
	}
	public String getProcessOpinion() {
		return processOpinion;
	}
	public void setProcessOpinion(String processOpinion) {
		this.processOpinion = processOpinion;
	}
	public String getReportTrgetYear() {
		return reportTrgetYear;
	}
	public void setReportTrgetYear(String reportTrgetYear) {
		this.reportTrgetYear = reportTrgetYear;
	}
	public String getExaminResult() {
		return examinResult;
	}
	public void setExaminResult(String examinResult) {
		this.examinResult = examinResult;
	}
	public String getClsRngViewImgId() {
		return clsRngViewImgId;
	}
	public void setClsRngViewImgId(String clsRngViewImgId) {
		this.clsRngViewImgId = clsRngViewImgId;
	}
	public String getDstntViewImgId() {
		return dstntViewImgId;
	}
	public void setDstntViewImgId(String dstntViewImgId) {
		this.dstntViewImgId = dstntViewImgId;
	}
	public String getEtcImgId() {
		return etcImgId;
	}
	public void setEtcImgId(String etcImgId) {
		this.etcImgId = etcImgId;
	}
	public String getExmnrInfo() {
		return exmnrInfo;
	}
	public void setExmnrInfo(String exmnrInfo) {
		this.exmnrInfo = exmnrInfo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public KwsImage getCloseRangeViewImage() {
		return closeRangeViewImage;
	}
	public void setCloseRangeViewImage(KwsImage closeRangeViewImage) {
		this.closeRangeViewImage = closeRangeViewImage;
	}
	public KwsImage getDistantViewImage() {
		return distantViewImage;
	}
	public void setDistantViewImage(KwsImage distantViewImage) {
		this.distantViewImage = distantViewImage;
	}
	public KwsImage getTempViewImage() {
		return tempViewImage;
	}
	public void setTempViewImage(KwsImage tempViewImage) {
		this.tempViewImage = tempViewImage;
	}
	public String getExaminResultNm() {
		return examinResultNm;
	}
	public void setExaminResultNm(String examinResultNm) {
		this.examinResultNm = examinResultNm;
	}
}
